package de.microtema.maven.plugin;

import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@CommonsLog
@Mojo(name = "generate", defaultPhase = LifecyclePhase.COMPILE)
public class ReadmeMarkdownGeneratorMojo extends AbstractMojo {


    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    MavenProject project;

    @Parameter(property = "outputFile")
    String outputFile = "README.md";

    @Parameter(property = "inputDocDir")
    String inputDocDir = ".docs";

    @Parameter(property = "outputDocDir")
    String outputDocDir = "docs";


    public void execute() {

        File docFile = new File(inputDocDir);

        if (!docFile.exists()) {

            logMessage("Create scaffolding for " + inputDocDir);

            createDirectory(docFile);
        }

        File docDirCopy = copyDirectory(inputDocDir);

        executeImpl(docDirCopy, new File(outputFile));
    }

    void createDirectory(File docDir) {

        try {

            boolean created = docDir.mkdir();

            Validate.isTrue(created, "Should be created");

            URL resourceUrl = ReadmeMarkdownGeneratorMojo.class.getResource("/docs");

            Objects.requireNonNull(resourceUrl, "Should not be null!");

            String path = resourceUrl.getPath();

            logMessage("Resource Path: " + path);

            if (path.contains(".jar")) {

                File resourceFile = new File(path.replace("!/docs", "").replace("file:", ""));

                logMessage("Parent Path: " + resourceFile);

                JarFile jar = new JarFile(resourceFile);

                Enumeration<JarEntry> enumEntries = jar.entries();

                while (enumEntries.hasMoreElements()) {

                    JarEntry jarEntry = enumEntries.nextElement();

                    logMessage(jarEntry.getName());

                    if (!jarEntry.getName().contains("docs/")) {
                        continue;
                    }

                    File f = new File(docDir.getParentFile(), jarEntry.getName());
                    if (jarEntry.isDirectory()) { // if its a directory, create it
                        f.mkdir();
                        continue;
                    }

                    InputStream inputStream = jar.getInputStream(jarEntry); // get the input stream
                    FileOutputStream fileOutputStream = new FileOutputStream(f);

                    while (inputStream.available() > 0) {  // write contents of 'is' to 'fos'
                        fileOutputStream.write(inputStream.read());
                    }

                    fileOutputStream.close();
                    inputStream.close();
                }
                jar.close();
            }

        } catch (Exception e) {
            throw new RuntimeException("Unable to copy default docs!", e);
        }
    }

    File copyDirectory(String docDir) {

        File docDirCopy = new File(outputDocDir);

        if (docDirCopy.exists()) {
            docDirCopy.delete();
        }

        try {

            File[] files = Objects.requireNonNull(new File(docDir).listFiles(), "Should not be null!");

            for (File file : files) {
                if (file.isDirectory()) {
                    FileUtils.copyDirectoryToDirectory(file, docDirCopy);
                } else {
                    FileUtils.copyFileToDirectory(file, docDirCopy);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return docDirCopy;
    }

    void executeImpl(File inputDir, File outputFile) {

        logMessage("Merge templates from " + inputDir + " -> " + outputFile);

        String imagePath = "./" + outputDocDir + "/images/";

        TemplateGenerator templateGenerator = new TemplateGenerator(outputFile, new File(outputDocDir), imagePath);

        templateGenerator.execute();
    }

    void logMessage(String... messages) {

        Log log = getLog();

        log.info("+----------------------------------+");
        for (String message : messages) {
            log.info(message);
        }

        log.info("+----------------------------------+");
    }
}

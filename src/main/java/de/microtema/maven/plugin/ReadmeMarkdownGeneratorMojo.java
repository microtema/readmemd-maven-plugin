package de.microtema.maven.plugin;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.Validate;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import static de.microtema.maven.plugin.TemplateGeneratorUtils.copyDir;
import static de.microtema.maven.plugin.TemplateGeneratorUtils.copyFromJar;

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

        File inputDir = new File(inputDocDir);
        File outputDir = new File(outputDocDir);

        copyDir(inputDir, outputDir);

        logMessage("Merge templates from " + inputDir + " -> " + outputFile);

        String imagePath = "./" + outputDocDir + "/images/";

        TemplateGenerator templateGenerator = new TemplateGenerator(new File(outputFile), outputDir, imagePath);

        templateGenerator.execute();
    }

    private void createDirectory(File docDir) {

        try {

            boolean created = docDir.mkdir();

            Validate.isTrue(created, "Should be created");

            URL resourceUrl = ReadmeMarkdownGeneratorMojo.class.getResource("/docs");

            Objects.requireNonNull(resourceUrl, "Should not be null!");

            String path = resourceUrl.getPath();

            logMessage("Resource Path: " + path);

            if (path.contains(".jar")) {

                copyFromJar(path, docDir);

            } else {

                copyDir(new File(path), new File(inputDocDir));
            }

        } catch (Exception e) {
            throw new RuntimeException("Unable to copy default docs!", e);
        }
    }

    private void logMessage(String... messages) {

        Log log = getLog();

        log.info("+----------------------------------+");

        for (String message : messages) {
            log.info(message);
        }

        log.info("+----------------------------------+");
    }
}

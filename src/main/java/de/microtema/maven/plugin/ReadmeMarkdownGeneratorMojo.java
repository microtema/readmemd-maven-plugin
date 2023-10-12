package de.microtema.maven.plugin;

import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Mojo(name = "generate", defaultPhase = LifecyclePhase.COMPILE)
public class ReadmeMarkdownGeneratorMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    MavenProject project;

    @Parameter(property = "outputFile")
    String outputFile = "/README.md";

    @Parameter(property = "docDir")
    String docDir = "./docs";


    public void execute() {

        logMessage("Merge templates from " + docDir + " to " + outputFile);

        File inputDir = new File(docDir);

        File[] templateFiles = inputDir.listFiles((File dir, String name) -> name.endsWith(".md"));

        List<File> orderedTemplateFiles = Arrays.asList(Objects.requireNonNull(templateFiles));

        orderedTemplateFiles.sort(Comparator.comparing(o -> getFileIndex(o.getName())));

        StringBuilder stringBuilder = new StringBuilder();

        for (File templateFile : templateFiles) {

            String template = importTemplate(templateFile)
                    .replace("images/", inputDir.getName() + "/images/");

            stringBuilder.append(template).append(lineSeparator(2));
        }

        writeFile(outputFile, stringBuilder.toString());

    }

    String importTemplate(File template) {

        try {
            return FileUtils.readFileToString(template, Charset.defaultCharset());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void writeFile(String outputFilePath, String fileContent) {

        File outputFile = new File(outputFilePath);
        try {
            FileUtils.writeStringToFile(outputFile, fileContent, Charset.defaultCharset());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    String lineSeparator(int lines) {

        int index = 0;

        StringBuilder str = new StringBuilder();

        while (index++ < lines) {
            str.append(System.lineSeparator());
        }

        return str.toString();
    }

    static String getFileIndex(String fileName) {

        return fileName.split("-")[0];
    }

    void logMessage(String message) {

        Log log = getLog();

        log.info("+----------------------------------+");
        log.info(message);
        log.info("+----------------------------------+");
    }
}

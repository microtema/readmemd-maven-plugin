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
import java.util.Objects;

import static de.microtema.maven.plugin.TemplateGeneratorUtils.*;

@Mojo(name = "multi-generate", defaultPhase = LifecyclePhase.COMPILE)
public class MultiReadmeMarkdownGeneratorMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    MavenProject project;

    @Parameter(property = "inputDocDir")
    String inputDocDir = ".docs";

    @Parameter(property = "outputDocDir")
    String outputDocDir = "docs";

    public void execute() {

        logMessage("Multi markdown generator started...");

        File inputDir = new File(inputDocDir);
        File outputDir = new File(outputDocDir);

        copyDir(inputDir, outputDir);

        File[] documents = Objects.requireNonNull(outputDir.listFiles(), "Should contain documents");

        for (File document : documents) {

            String templateTitle = document.getName() + ".md";
            File outputFile = new File(outputDir, templateTitle);

            logMessage("Merge templates from " + document + " -> " + outputFile);

            String imagePath = "./images/";

            TemplateGenerator templateGenerator = new TemplateGenerator(outputFile, document, imagePath);

            templateGenerator.execute();
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

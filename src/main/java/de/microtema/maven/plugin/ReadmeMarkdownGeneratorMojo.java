package de.microtema.maven.plugin;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mojo(name = "generate", defaultPhase = LifecyclePhase.COMPILE)
public class ReadmeMarkdownGeneratorMojo extends AbstractMojo {

    // :[Template](08-Crosscutting-Concepts/Azure-Functions.md)
    private static final Pattern pattern = Pattern.compile("(:\\[.*\\]\\(([^\\)]+)\\))");

    private final List<String> possiblePaths = Arrays.asList(
            "../../../../../../images/",
            "../../../../../images/",
            "../../../../images/",
            "../../../images/",
            "../../images/",
            "../images/",
            "./images/"
    );

    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    MavenProject project;

    @Parameter(property = "outputFile")
    String outputFile = "README.md";

    @Parameter(property = "docDir")
    String docDir = "docs";

    private File docDirCopy;


    public void execute() {

        File docFile = new File(docDir);

        if (!docFile.exists()) {

            logMessage("Create scaffolding for " + docDir);

            createDirectory();
        }

        copyDirectory();

        executeImpl(docDirCopy, new File(outputFile));
    }

    List<String> readSections(File docFile) {

        File[] files = docFile.listFiles();

        Objects.requireNonNull(files, "folder should not be null!");

        return Stream.of(files)
                .map(File::getName)
                .map(it -> it.replace(".md", ""))
                .filter(it -> !it.equalsIgnoreCase("images"))
                .collect(Collectors.toSet())
                .stream()
                .sorted(Comparator.comparing(ReadmeMarkdownGeneratorMojo::getFileIndex))
                .collect(Collectors.toList());
    }

    void createDirectory() {

        try {
            FileUtils.copyDirectory(new File("docs"), new File(docDir));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void copyDirectory() {

        docDirCopy = new File("target", docDir);

        try {
            FileUtils.copyDirectory(new File(docDir), docDirCopy);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void executeImpl(File inputDir, File outputFile) {

        replaceExpressions(inputDir);

        StringBuilder stringBuilder = new StringBuilder();

        List<String> sections = readSections(inputDir);

        if (sections.isEmpty()) {
            return;
        }

        logMessage("Merge templates from " + inputDir + " -> " + outputFile);

        for (String section : sections) {

            String template = importTemplate(section, inputDir, 1);

            stringBuilder.append(template).append(lineSeparator())
                    .append("---")
                    .append(lineSeparator());
        }

        writeFile(outputFile, stringBuilder.toString());
    }

    void replaceExpressions(File file) {

        if (file.isDirectory()) {

            File[] files = file.listFiles((dir, name) -> !name.equalsIgnoreCase("images"));

            if (Objects.isNull(files)) {
                return;
            }

            Stream.of(files).forEach(this::replaceExpressions);

            return;
        }

        if (!file.getName().endsWith(".md")) {
            return;
        }

        String fileContent = getFileContent(file);

        List<Directive> directives = getDirectives(fileContent);

        fileContent = replaceImagePaths(fileContent);

        for (Directive directive : directives) {

            logMessage("Get template content from path: " + directive.path);

            String templateContent = getFileContent(new File(this.docDirCopy, directive.path));

            templateContent = replaceImagePaths(templateContent);

            fileContent = fileContent.replace(Matcher.quoteReplacement(directive.group), templateContent);
        }

        writeFile(file, fileContent);
    }

    List<Directive> getDirectives(String fileContent) {

        List<Directive> directives = new ArrayList<>();

        Matcher matcher = pattern.matcher(fileContent);

        while (matcher.find()) {

            Directive directive = new Directive();

            directive.group = matcher.group(0);
            directive.displayName = matcher.group(1);
            directive.path = matcher.group(2);

            directives.add(directive);
        }

        return directives;
    }

    static class Directive {

        String group;

        String displayName;
        String path;
    }

    String getFileContent(File file) {

        try {
            return FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Unable to get template content from file: " + file.getPath(), e);
        }
    }

    String replaceImagePaths(String tempTemplate) {

        for (String path : possiblePaths) {
            if (tempTemplate.contains(path)) {
                tempTemplate = tempTemplate.replaceAll(path, this.docDir + "/images/");
            }
        }

        return tempTemplate;
    }

    String importTemplate(String templateName, File templateParentDir, int intent) {

        File template = new File(templateParentDir, templateName + ".md");

        if (!template.exists()) {

            template = new File(templateParentDir, templateName);

            return getDefaultTemplateContent(template, intent);
        }

        try {
            return FileUtils.readFileToString(template, Charset.defaultCharset());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void writeFile(File outputFile, String fileContent) {

        try {
            FileUtils.writeStringToFile(outputFile, fileContent, Charset.defaultCharset());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    String lineSeparator() {

        return repeat(System.lineSeparator(), 2);
    }

    String repeat(String token, int times) {

        int index = 0;

        StringBuilder str = new StringBuilder();

        while (index++ < times) {
            str.append(token);
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

    String getDefaultTemplateContent(File templateFile, int intent) {

        StringBuilder stringBuilder = new StringBuilder();

        List<String> orderedTemplateSection = readSections(templateFile);

        String headLine = getTemplateTitle(templateFile.getName());

        String tokens = repeat("#", intent);

        stringBuilder.append(tokens).append(" ").append(headLine).append(lineSeparator());

        for (String section : orderedTemplateSection) {

            String template = importTemplate(section, templateFile, intent + 1);

            stringBuilder.append(template).append(lineSeparator())
                   // .append("---")
                    .append(lineSeparator());
        }

        return stringBuilder.toString();
    }

    private String getTemplateTitle(String title) {

        if (StringUtils.isNumeric(getFileIndex(title))) {
            title = title.substring(3);
        }

        return title.replace(".md", "")
                .replaceAll("-", " ");
    }
}

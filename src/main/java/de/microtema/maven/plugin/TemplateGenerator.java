package de.microtema.maven.plugin;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TemplateGenerator {

    // :[Template](08-Crosscutting-Concepts/Azure-Functions.md)
    private static final Pattern DIRECTIVE_PATTERN = Pattern.compile("(:\\[.*\\]\\(([^\\)]+)\\))");

    private final List<String> possiblePaths = Arrays.asList(
            "../../../../../../",
            "../../../../../",
            "../../../../",
            "../../../",
            "../../",
            "../",
            "./"
    );

    private final File outputFile;
    private final String rootDocsPath;
    private final File docsDirectory;
    private final String imagePath;

    public TemplateGenerator(File outputFile, File docsDirectory, String imagePath) {
        this(outputFile, docsDirectory, docsDirectory.getName(), imagePath);
    }

    public TemplateGenerator(File outputFile, File docsDirectory, String rootDocsPath, String imagePath) {
        this.outputFile = outputFile;
        this.docsDirectory = docsDirectory;
        this.rootDocsPath = rootDocsPath;
        this.imagePath = imagePath;
    }

    public void execute() {

        replaceExpressions(docsDirectory);

        String content = summarizeSections(docsDirectory);

        String filteredContent = replaceImagePaths(content, imagePath);

        writeFile(outputFile, filteredContent);
    }

    String summarizeSections(File inputDir) {

        StringBuilder stringBuilder = new StringBuilder();

        List<String> sections = readSections(inputDir);

        for (String section : sections) {

            String template = importTemplate(section, inputDir, 1);

            stringBuilder.append(template).append(lineSeparator())
                    .append("---")
                    .append(lineSeparator());
        }

        return stringBuilder.toString();
    }

    List<String> readSections(File docFile) {

        File[] files = docFile.listFiles();

        if (files == null) {
            return Collections.emptyList();
        }

        return Stream.of(files)
                .map(File::getName)
                .map(it -> it.replace(".md", ""))
                .filter(it -> !it.equalsIgnoreCase("images"))
                .collect(Collectors.toSet())
                .stream()
                .sorted(Comparator.comparing(this::getFileIndex))
                .filter(StringUtils::isNoneBlank)
                .collect(Collectors.toList());
    }

    String importTemplate(String templateName, File templateParentDir, int intent) {

        File template = new File(templateParentDir, templateName + ".md");
        File templateDir = new File(templateParentDir, templateName);

        if (!template.exists()) {

            String content = getDefaultTemplateContent(templateDir, intent);

            if (content != null) {
                writeFile(template, content);
            }
        }

        try {
            return FileUtils.readFileToString(template, Charset.defaultCharset());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    String getDefaultTemplateContent(File templateFile, int intent) {

        List<String> orderedTemplateSection = readSections(templateFile);

        if (orderedTemplateSection.isEmpty()) {
            return null;
        }

        StringBuilder stringBuilder = new StringBuilder();

        String headLine = getTemplateTitle(templateFile.getName());

        String tokens = repeatToken("#", intent);

        stringBuilder.append(tokens).append(" ").append(headLine).append(lineSeparator());

        for (String section : orderedTemplateSection) {

            String template = importTemplate(section, templateFile, intent + 1);

            stringBuilder.append(template).append(lineSeparator());
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

    private void writeFile(File file, String content) {
        try {
            FileUtils.writeStringToFile(file, content, Charset.defaultCharset());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void replaceExpressions(File file) {

        Collection<File> files = FileUtils.listFiles(file, new String[]{"md"}, true);

        files.stream().sorted((a, b) -> getLayer(b).compareTo(getLayer(a))).forEach(this::replaceExpressionsImpl);
    }

    private Integer getLayer(File file) {

        int layer = 0;

        File parent = file.getParentFile();

        while (parent != null && !parent.equals(docsDirectory)) {
            layer++;
            parent = parent.getParentFile();
        }

        return layer;
    }

    private void replaceExpressionsImpl(File targetFile) {

        String fileContent = getFileContent(targetFile);

        List<Directive> directives = getDirectives(fileContent);

        for (Directive directive : directives) {

            String templatePath = resolveTemplatePath(directive.path); // working on orig docs templates

            File directiveFile = new File(templatePath);

            String templateContent = getFileContent(directiveFile);

            // finally replace directive with template content
            fileContent = fileContent.replace(Matcher.quoteReplacement(directive.group), templateContent);
        }

        String imagePath = getImageDir(targetFile);

        fileContent = replaceImagePaths(fileContent, imagePath);

        writeFile(targetFile, fileContent);
    }

    private String getFileContent(File file) {

        try {
            return FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Unable to get template content from file: " + file.getPath(), e);
        }
    }

    private List<Directive> getDirectives(String fileContent) {

        List<Directive> directives = new ArrayList<>();

        Matcher matcher = DIRECTIVE_PATTERN.matcher(fileContent);

        while (matcher.find()) {

            Directive directive = new Directive();

            directive.group = matcher.group(0);
            directive.displayName = matcher.group(1);
            directive.path = matcher.group(2);

            directives.add(directive);
        }

        return directives;
    }

    private String resolveTemplatePath(String templatePath) {

        for (String path : possiblePaths) {

            if (templatePath.contains(path)) {

                return templatePath.replace(path, rootDocsPath + "/");
            }
        }

        return templatePath;
    }

    private String getImageDir(File templateFile) {

        int level = getLayer(templateFile);

        String imagePath = repeatToken("../", level);

        if (StringUtils.isEmpty(imagePath)) {
            imagePath = "./";
        }

        return imagePath + "images/";
    }

    private String repeatToken(String token, int times) {

        int index = 0;

        StringBuilder str = new StringBuilder();

        while (index++ < times) {
            str.append(token);
        }

        return str.toString();
    }

    private String replaceImagePaths(String tempTemplate, String docImageDir) {

        for (String path : possiblePaths) {

            String imagePath = "(" + path + "images/";

            if (tempTemplate.contains(imagePath)) {
                tempTemplate = tempTemplate.replace(imagePath, "(" + docImageDir);
            }
        }

        // fallback without dot
        return tempTemplate.replace("(images/", "(" + docImageDir);
    }

    private String getFileIndex(String fileName) {

        return fileName.split("-")[0];
    }

    private String lineSeparator() {

        return repeatToken(System.lineSeparator(), 2);
    }

}

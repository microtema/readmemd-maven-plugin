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
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@CommonsLog
@Mojo(name = "generate", defaultPhase = LifecyclePhase.COMPILE)
public class ReadmeMarkdownGeneratorMojo extends AbstractMojo {

    // :[Template](08-Crosscutting-Concepts/Azure-Functions.md)
    private static final Pattern DIRECTIVE_PATTERN = Pattern.compile("(:\\[.*\\]\\(([^\\)]+)\\))");

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

        if(docDirCopy.exists()) {
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

        replaceExpressions(inputDir);

        String content = summarizeSections(inputDir);

        writeFile(outputFile, content);
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

    void replaceExpressions(File file) {

        Collection<File> files = FileUtils.listFiles(file, new String[]{"md"}, true);

        files.stream().sorted((a, b) -> getLayer(b).compareTo(getLayer(a))).forEach(this::replaceExpressionsImpl);
    }

    File getTargetFile(File file) {

        String fileName = file.getPath();

        if (!fileName.startsWith(outputDocDir)) {

            fileName = fileName.replaceFirst(inputDocDir, outputDocDir);

            file = new File(fileName);
        }

        return file;
    }

    void replaceExpressionsImpl(File file) {

        File targetFile = getTargetFile(file);

        String fileContent = getFileContent(targetFile);

        fileContent = replaceImagePaths(fileContent);

        List<Directive> directives = getDirectives(fileContent);

        for (Directive directive : directives) {

            String templatePath = resolveTemplatePath(directive.path); // working on orig docs templates

            File directiveFile = new File(templatePath);

            String templateContent = getFileContent(directiveFile);

            // finally replace directive with template content
            fileContent = fileContent.replace(Matcher.quoteReplacement(directive.group), templateContent);
        }

        writeFile(getTargetFile(targetFile), fileContent);
    }

    Integer getLayer(File file) {
        int layer = 0;

        File parent = file.getParentFile();

        while (parent != null) {
            layer++;
            parent = parent.getParentFile();
        }

        return layer;
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

    List<Directive> getDirectives(String fileContent) {

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

    String getFileContent(File file) {

        File targetFile = getTargetFile(file);

        try {
            return FileUtils.readFileToString(targetFile, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Unable to get template content from file: " + targetFile.getPath(), e);
        }
    }

    String replaceImagePaths(String tempTemplate) {

        for (String path : possiblePaths) {
            if (tempTemplate.contains(path)) {
                tempTemplate = tempTemplate.replaceAll(path, outputDocDir + "/images/");
            }
        }

        return tempTemplate;
    }

    private String resolveTemplatePath(String templatePath) {

        for (String path : possiblePaths) {

            String fixedPath = path.replace("/images/", "/");

            if (templatePath.contains(fixedPath)) {

                return templatePath.replace(fixedPath, inputDocDir + "/");
            }
        }

        return templatePath;
    }

    String importTemplate(String templateName, File templateParentDir, int intent) {

        File template = new File(templateParentDir, templateName + ".md");
        File templateDir = new File(templateParentDir, templateName);

        if (!template.exists()) {

            return getDefaultTemplateContent(templateDir, intent);
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

    String getDefaultTemplateContent(File templateFile, int intent) {

        StringBuilder stringBuilder = new StringBuilder();

        List<String> orderedTemplateSection = readSections(templateFile);

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

    void logMessage(String... messages) {

        Log log = getLog();

        log.info("+----------------------------------+");
        for (String message : messages) {
            log.info(message);
        }

        log.info("+----------------------------------+");
    }

    static String getFileIndex(String fileName) {

        return fileName.split("-")[0];
    }

    String lineSeparator() {

        return repeatToken(System.lineSeparator(), 2);
    }

    String repeatToken(String token, int times) {

        int index = 0;

        StringBuilder str = new StringBuilder();

        while (index++ < times) {
            str.append(token);
        }

        return str.toString();
    }

}

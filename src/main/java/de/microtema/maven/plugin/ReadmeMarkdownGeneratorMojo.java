package de.microtema.maven.plugin;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mojo(name = "generate", defaultPhase = LifecyclePhase.COMPILE)
public class ReadmeMarkdownGeneratorMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    MavenProject project;

    @Parameter(property = "outputFile")
    String outputFile = "README.md";

    @Parameter(property = "docDir")
    String docDir = "docs";

    private File docDirCopy;

    private final List<String> supportedSections = Arrays.asList(
            "01-Introduction-and-Goals",
            "02-Constraints",
            "03-Context-and-Scope",
            "04-Solution-Strategy",
            "05-Building-Block-View",
            "06-Runtime-View",
            "07-Deployment-View",
            "08-Crosscutting-Concepts",
            "09-Architectural-Decisions",
            "10-Introduction-and-Goals",
            "11-Risks-and-Technical-Debt",
            "12-Glossary"
    );


    public void execute() {

        copyDirectory();

        try {

            executeImpl(docDirCopy, new File(outputFile));
        } finally {

           deleteDirectory();
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

    void deleteDirectory() {
        try {
            FileUtils.deleteDirectory(docDirCopy);
        } catch (IOException e) {
            // Ignore exception
        }
    }

    void executeImpl(File inputDir, File outputFile) {

        replaceExpressions(inputDir);

        StringBuilder stringBuilder = new StringBuilder();

        List<File> orderedTemplateFiles = listTemplateFiles(inputDir);

        if(orderedTemplateFiles.isEmpty()){
            return;
        }

        logMessage("Merge templates from " + inputDir + " -> " + outputFile);

        for (File templateFile : orderedTemplateFiles) {

            String template = importTemplate(templateFile);

            template = replaceImagePaths(template);

            stringBuilder.append(template).append(lineSeparator(2));
        }

        writeFile(outputFile, stringBuilder.toString());
    }

    void replaceExpressions(File file) {

        if(file.isDirectory()) {

            File[] files = file.listFiles();

            if(Objects.isNull(files)) {
                return;
            }

            Stream.of(files).forEach(this::replaceExpressions);

           return;
        }

        String fileContent = getFileContent(file);

        List<String> directives = getDirectives(fileContent);

        for (String directive : directives) {

            String templateContent = getFileContent(new File(this.docDirCopy, directive));

            fileContent = fileContent.replace(Matcher.quoteReplacement("[Template]("+directive+")"), templateContent);
        }

        fileContent = replaceImagePaths(fileContent);

        writeFile(file, fileContent);
    }

    List<String> getDirectives(String fileContent) {

        // :[Template](08-Crosscutting-Concepts/Azure-Functions.md)

        List<String> directives = new ArrayList<>();

        Pattern pattern = Pattern.compile("\\[Template\\]\\(([^\\)]+)\\)");

        Matcher matcher = pattern.matcher(fileContent);

        while(matcher.find()) {

            directives.add(matcher.group(1));
        }

        return directives;
    }

    String getFileContent(File file){

        try {
            return FileUtils.readFileToString(file, Charset.defaultCharset());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    String replaceImagePaths(String tempTemplate) {

        List<String> possiblePaths = Arrays.asList(
                "../../../../../../images",
                "../../../../../images",
                "../../../../images",
                "../../../images",
                "../../images",
                "../images"
        );

        for (String path : possiblePaths) {
            if (tempTemplate.contains(path)) {
                tempTemplate = tempTemplate.replaceAll(path, this.docDir + "/images");
            }
        }

        return tempTemplate;
    }

    String importTemplate(File template) {

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

    List<File> listTemplateFiles(File file) {

        File[] files =  file.listFiles((File dir, String fileName) -> fileName.endsWith(".md"));

        if(files == null) {
            return Collections.emptyList();
        }

        List<File> orderedFiles = new ArrayList<>(Arrays.asList(files));

        List<File> filteredFiles = orderedFiles.stream()
                .filter(it -> supportedSections.contains(it.getName().replace(".md", "")))
                .collect(Collectors.toList());

        List<File> missingFiles = listMissingTemplateFiles();

        missingFiles.forEach(it -> writeFile(it, getDefaultTemplateContent(it.getName())));

        filteredFiles.addAll(missingFiles);

        filteredFiles.sort(Comparator.comparing(o -> getFileIndex(o.getName())));

        return filteredFiles;
    }

    String getDefaultTemplateContent(String fileName){

        String title = getTemplateTitle(fileName.replace(".md", ""));

        return "# " +title + lineSeparator(2) + "See shared documentation";
    }

    List<File> listMissingTemplateFiles() {

        return supportedSections
                .stream()
                .map(it -> new File(this.docDirCopy, it+".md"))
                .filter(it -> !it.exists())
                .collect(Collectors.toList());
    }

    List<File> listTemplateDirs(File inputDir) {

        List<File> templateDirs = new ArrayList<>();

        File[] listFiles = inputDir.listFiles();

        if(listFiles == null) {
            return templateDirs;
        }

        for(File file : listFiles) {

            if(file.isFile()) {
                continue;
            }

            File[] files =  file.listFiles((File dir, String fileName) -> fileName.endsWith(".md"));

            if(files == null) {
                continue;
            }

            if(files.length == 0) {
                continue;
            }

            templateDirs.add(file);
        }

        templateDirs.sort(Comparator.comparing(o -> getFileIndex(o.getName())));

        List<File> orderedTemplateFiles = listTemplateFiles(inputDir);

        List<String> orderedTemplateFileNames = orderedTemplateFiles.stream().map(it -> it.getName().replace(".md", "")).collect(Collectors.toList());

        if(!inputDir.getParentFile().equals(this.docDirCopy)){
            return templateDirs;
        }

        return templateDirs.stream()
                .filter(it -> supportedSections.contains(it.getName()))
                .filter(it -> orderedTemplateFileNames.contains(it.getName()))
                .collect(Collectors.toList());
    }

    private String getTemplateTitle(String title) {

        if(StringUtils.isNumeric(getFileIndex(title))) {
            title = title.substring(3);
        }

        return title.replace(".md", "")
                .replaceAll("-", " ");
    }
}

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

    private static final List<String> supportedArch42Sections = Arrays.asList(
            "01-Introduction-and-Goals",
            "02-Constraints",
            "03-Context-and-Scope",
            "04-Solution-Strategy",
            "05-Building-Block-View",
            "06-Runtime-View",
            "07-Deployment-View",
            "08-Crosscutting-Concepts",
            "09-Architectural-Decisions",
            "10-Quality-Requirements",
            "11-Risks-and-Technical-Debt",
            "12-Glossary"
    );

    private static final List<String> supportedSddSections = Arrays.asList(
            "01-Executive-Summary",
            "02-Overview",
            "03-Current-and-Target-State-Architecture",
            "04-Solution-Architecture",
            "05-Data",
            "06-Network",
            "07-Security",
            "08-Nonfunctional-Requirements",
            "09-Quality-Assurance-Strategy",
            "10-Abbreviations",
            "11-Document-History"
    );

    private static final Map<String, List<String>> supportedSectionsMap = new HashMap<>();


    static {
        supportedSectionsMap.put("docs", supportedArch42Sections);
        supportedSectionsMap.put("sdd", supportedSddSections);
    }
    private final   List<String> possiblePaths = Arrays.asList(
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

    private List<String> supportedSections;

    public void execute() {

        File docFile = new File(docDir);

        supportedSections = supportedSectionsMap.get(docFile.getName().toLowerCase());

        if(!docFile.exists()) {

            logMessage("Create scaffolding for "+ docDir);

            docFile.mkdirs();

            supportedSections
                    .stream()
                    .map(it -> new File(docFile, it+".md"))
                    .forEach(it -> writeFile(it, getDefaultTemplateContent(it.getName())));
        }

        copyDirectory();

        executeImpl(docDirCopy, new File(outputFile));
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

            stringBuilder.append(template).append(lineSeparator());
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

        if(!file.getName().endsWith(".md")) {
            return;
        }

        String fileContent = getFileContent(file);

        List<Directive> directives = getDirectives(fileContent);

        fileContent = replaceImagePaths(fileContent);

        for (Directive directive : directives) {

            logMessage("Get template content from path: "+directive.path);

            String templateContent = getFileContent(new File(this.docDirCopy, directive.path));

            templateContent = replaceImagePaths(templateContent);

            fileContent = fileContent.replace(Matcher.quoteReplacement(directive.group), templateContent);
        }

        writeFile(file, fileContent);
    }

    List<Directive> getDirectives(String fileContent) {

        List<Directive> directives = new ArrayList<>();

        Matcher matcher = pattern.matcher(fileContent);

        while(matcher.find()) {

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

    String getFileContent(File file){

        try {
            return FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Unable to get template content from file: "+file.getPath(), e);
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

    String lineSeparator() {

        int index = 0;
        int lines = 2;

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

    String getDefaultTemplateContent(String templateName){

        try {

            InputStream inputStream = ReadmeMarkdownGeneratorMojo.class.getResourceAsStream("/" + this.docDir +"/" + templateName);

            Objects.requireNonNull(inputStream);

            return IOUtils.toString(inputStream, Charset.defaultCharset());

        } catch (Exception e) {

            String title = getTemplateTitle(templateName.replace(".md", ""));

            return "# " +title + lineSeparator() + "See shared documentation";
        }
    }

    List<File> listMissingTemplateFiles() {

        return supportedSections
                .stream()
                .map(it -> new File(this.docDirCopy, it+".md"))
                .filter(it -> !it.exists())
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

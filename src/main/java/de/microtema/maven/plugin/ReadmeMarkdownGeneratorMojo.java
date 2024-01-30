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

        logMessage("Merge templates from " + docDir + " to " + outputFile);

        List<File> templateDirs =  listTemplateDirs(inputDir);

        templateDirs.forEach(it -> executeImpl(it, new File(it.getParentFile(), it.getName()+".md")));

        StringBuilder stringBuilder = new StringBuilder();

        if(!outputFile.exists()) {

            if(!outputFile.getName().equals(new File(this.outputFile).getName())) {

                String templateTitle = getTemplateTitle(outputFile);

                stringBuilder.append(templateTitle).append(lineSeparator(2));
            }
        }

        List<File> orderedTemplateFiles = listTemplateFiles(inputDir);

        for (File templateFile : orderedTemplateFiles) {

            String template = importTemplate(templateFile);

            template = replaceImagePaths(template);

            stringBuilder.append(template).append(lineSeparator(2));
        }

        writeFile(outputFile, stringBuilder.toString());
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

    private String getTemplateTitle(File file) {

        String title = file.getName();

        if(StringUtils.isNumeric(getFileIndex(title))) {
            title = title.substring(3);
        }

        title = title.replace(".md", "")
                .replaceAll("-", " ");

        String tags = getTemplateTitlePrefix(file);

        return tags + " " + title;
    }

    private String getTemplateTitlePrefix(File file) {

        StringBuilder tags = new StringBuilder("#");

        File parentFile = file.getParentFile();

        do {
            tags.append("#");
            parentFile = parentFile.getParentFile();
        } while (Objects.nonNull(parentFile) && !Objects.equals(parentFile, docDirCopy));

        return tags.toString();
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

        List<File> orderedFiles = Arrays.asList(files);

        orderedFiles.sort(Comparator.comparing(o -> getFileIndex(o.getName())));

        return orderedFiles;
    }

    List<File> listTemplateDirs(File inputDir) {

        List<File> templateDirs = new ArrayList<File>();

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

        return templateDirs;
    }
}

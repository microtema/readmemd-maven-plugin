package de.microtema.maven.plugin;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public final class TemplateGeneratorUtils {

    public static void copyDir(File inputDir, File outputDir) {

        File[] files = Objects.requireNonNull(inputDir.listFiles(), "Should not be null!");

        try {

            FileUtils.deleteDirectory(outputDir);

            for (File file : files) {
                if (file.isDirectory()) {
                    FileUtils.copyDirectoryToDirectory(file, outputDir);
                } else {
                    FileUtils.copyFileToDirectory(file, outputDir);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void copyFromJar(String path, File docDir) throws IOException {

        File resourceFile = new File(path.replace("!/docs", "").replace("file:", ""));

        JarFile jar = new JarFile(resourceFile);

        Enumeration<JarEntry> enumEntries = jar.entries();

        while (enumEntries.hasMoreElements()) {

            JarEntry jarEntry = enumEntries.nextElement();

            if (!jarEntry.getName().contains("docs/")) {
                continue;
            }

            File f = new File(docDir.getParentFile(), jarEntry.getName());
            if (jarEntry.isDirectory()) { // if it's a directory, create it
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

    public static String getTemplateTitle(String fileName) {

        if (StringUtils.isNumeric(getFileIndex(fileName))) {
            fileName = fileName.substring(3);
        }

        return fileName
                .replace(".md", "")
                .replaceAll("-", " ");
    }

    public static String getFileIndex(String fileName) {

        return fileName.split("-")[0];
    }

    public static String repeatToken(String token, int times) {

        int index = 0;

        StringBuilder str = new StringBuilder();

        while (index++ < times) {
            str.append(token);
        }

        return str.toString();
    }

    public static String lineSeparator() {

        return repeatToken(System.lineSeparator(), 2);
    }

    public static String getFileContent(File file) {

        try {
            return FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Unable to get template content from file: " + file.getPath(), e);
        }
    }

    public static void writeTemplate(File file, String content) {
        try {
            FileUtils.writeStringToFile(file, content, Charset.defaultCharset());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String readTemplate(File file) {
        try {
            return FileUtils.readFileToString(file, Charset.defaultCharset());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}



package de.microtema.maven.plugin;

import org.apache.commons.io.FileUtils;
import org.apache.maven.project.MavenProject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReadmeMarkdownGeneratorMojoTest {

    @InjectMocks
    ReadmeMarkdownGeneratorMojo sut;

    @Mock
    MavenProject project;

    @BeforeEach
    void setUp() {

        sut.project = project;
    }

    @Test
    void executeOnNonUpdateFalse() throws Exception {

        sut.outputFile = "ARCH42_README.md";
        sut.docDir = "docs";

        sut.execute();

        File file = new File(sut.outputFile);
        assertTrue(file.exists());

        String answer = FileUtils.readFileToString(file, "UTF-8");
        assertNotNull(answer);
    }


    @Test
    void createDocTemplates() throws Exception {

        sut.outputFile = "ARCH42_DEFAULT_README.md";
        sut.docDir = "arch42_docs";

        sut.execute();

        File file = new File(sut.outputFile);
        assertTrue(file.exists());

        String answer = FileUtils.readFileToString(file, "UTF-8");
        assertNotNull(answer);
    }

    @AfterAll
    static void tearDown() {
        FileUtils.deleteQuietly(new File("arch42_docs"));
    }
}

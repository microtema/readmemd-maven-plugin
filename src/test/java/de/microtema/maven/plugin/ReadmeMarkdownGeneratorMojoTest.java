package de.microtema.maven.plugin;

import org.apache.commons.io.FileUtils;
import org.apache.maven.project.MavenProject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

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

    @AfterEach
    void tearDown() {

        FileUtils.deleteQuietly(new File(sut.outputFile));
        FileUtils.deleteQuietly(new File(sut.outputDocDir));
        FileUtils.deleteQuietly(new File(sut.inputDocDir));
    }

    @Test
    void createDefaultTemplates() throws Exception {

        sut.outputFile = ".README.md";
        sut.outputDocDir = "docs";
        sut.inputDocDir = ".docs";

        sut.execute();

        File file = new File(sut.outputFile);
        assertTrue(file.exists());

        String answer = FileUtils.readFileToString(file, "UTF-8");
        assertNotNull(answer);
    }
}

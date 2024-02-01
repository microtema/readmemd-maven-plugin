package de.microtema.maven.plugin;

import org.apache.commons.io.FileUtils;
import org.apache.maven.project.MavenProject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SDDReadmeMarkdownGeneratorMojoTest {

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

        sut.outputFile = "README.md";
        sut.docDir = "docs";

        sut.execute();

        File file = new File(sut.outputFile);
        assertTrue(file.exists());

        String answer = FileUtils.readFileToString(file, "UTF-8");
        assertNotNull(answer);

        sut.outputFile = "SDD_README.md";
        sut.docDir = "sdd";

        sut.execute();

        file = new File(sut.outputFile);
        assertTrue(file.exists());

        answer = FileUtils.readFileToString(file, "UTF-8");

        assertTrue(file.exists());

        assertNotNull(answer);
    }
}

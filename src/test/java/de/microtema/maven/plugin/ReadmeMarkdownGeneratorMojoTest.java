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
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

        sut.outputFile = "target/README.md";

        sut.execute();

        File file = new File(sut.outputFile);

        String answer = FileUtils.readFileToString(file, "UTF-8");
        assertEquals("# Glossary\n" +
                "\n" +
                "| Term      | Definition    | \n" +
                "|-----------|---------------| \n" +
                "| DX        | Data Exchange | \n" +
                "| Microtema | Micro tema    |\n" +
                "\n" +
                "\n", answer);
    }
}

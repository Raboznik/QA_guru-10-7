package ru.yandex.mkruchkov;

import com.codeborne.pdftest.PDF;
import org.junit.jupiter.api.Test;

import java.io.File;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.assertj.core.api.Assertions.assertThat;

public class FileParsingTest {

    private ClassLoader cl = SelenideFilesTest.class.getClassLoader();


    @Test
    void parsePdfTest() throws Exception {
        open("https://junit.org/junit5/docs/current/user-guide/");
        File pdf = $(byText("PDF download")).download();
        PDF parsed = new PDF(pdf);
        assertThat(parsed.author).contains("Marc Philipp");

    }
}

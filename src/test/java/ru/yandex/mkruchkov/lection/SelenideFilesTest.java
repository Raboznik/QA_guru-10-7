package ru.yandex.mkruchkov.lection;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SelenideFilesTest {


    @Test
    void downloadTest() throws IOException {
        open("https://github.com/assertj/assertj-core/blob/main/LICENSE.txt");
        File downloadedFile = $("#raw-url").download();

        try (InputStream is = new FileInputStream(downloadedFile)) {
            assertThat(new String(is.readAllBytes(), StandardCharsets.UTF_8))
                    .contains("\"Licensor\" shall mean the copyright owner or entity authorized");
        }
    }


    @Test
    void uploadFile() {
        open("https://the-internet.herokuapp.com/upload");
        $("input[type='file']").uploadFromClasspath("upload.txt");
        $("#file-submit").click();
        $("#uploaded-files").shouldHave(text("upload.txt"));
    }
}

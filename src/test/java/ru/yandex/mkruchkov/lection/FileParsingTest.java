package ru.yandex.mkruchkov.lection;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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


    @Test
    void parseXlsTest() throws Exception {
        try (InputStream stream = cl.getResourceAsStream("testers.xlsx")) {
            XLS parsed = new XLS(stream);
            assertThat(parsed.excel.getSheetAt(1).getRow(1).getCell(2)
                    .getStringCellValue()).isEqualTo("T:_53e14af47eb93f5e3d3b82cc3264da8e");
        }
    }


    @Test
    void parseCsvFile() throws Exception {

        try (InputStream stream = cl.getResourceAsStream("example.csv")) {
            CSVReader csvReader = new CSVReader(new InputStreamReader(stream));
            List<String[]> list = csvReader.readAll();
            assertThat(list).contains(new String[]{"pack_book8_ch1_q5", "book8"});
        }
    }


    @Test
    void parseZipTest() throws Exception {

        try (InputStream stream = cl.getResourceAsStream("zip.zip");
             ZipInputStream zip = new ZipInputStream(stream)) {
            ZipEntry zipEntry;
            while ((zipEntry = zip.getNextEntry()) != null) {
                assertThat(zipEntry.getName()).isEqualTo("1.txt");
            }
        }
    }
}






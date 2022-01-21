package ru.yandex.mkruchkov.hw;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static org.assertj.core.api.Assertions.assertThat;

public class ParsingFilesFromZipTest {

    @Test
    void parsingFilesFromZip() throws Exception {

        ZipFile zipFile = new ZipFile("src\\test\\resources\\files.zip");

        ZipEntry txt = zipFile.getEntry("Harry Potter.txt");
        try (InputStream stream = zipFile.getInputStream(txt)) {
            AssertionsForClassTypes.assertThat(new String(stream.readAllBytes(), StandardCharsets.UTF_8)).contains("1.  THE BOY WHO LIVED");
        }

        ZipEntry csv = zipFile.getEntry("packs.csv");
        try (InputStream stream = zipFile.getInputStream(csv)) {
            CSVReader reader = new CSVReader(new InputStreamReader(stream));
            List<String[]> list = reader.readAll();
            assertThat(list).contains(
                    new String[]{"pack_10_event_april1", "10"},
                    new String[]{"pack_book8_offer_valentine", "book8"},
                    new String[]{"pack_book4_ch11_paid", "book4"}
            );
        }

        ZipEntry pdf = zipFile.getEntry("sample.pdf");
        try (InputStream stream = zipFile.getInputStream(pdf)) {
            PDF parsed = new PDF(stream);
            assertThat(parsed.text).contains("Oh, how boring typing this stuff");
        }


        ZipEntry xls = zipFile.getEntry("testers.xlsx");
        try (InputStream stream = zipFile.getInputStream(xls)) {
            XLS parsed = new XLS(stream);
            assertThat(parsed.excel.getSheetAt(2).getRow(1).getCell(2).getStringCellValue())
                    .isEqualTo("g04570552594797740025");
        }
    }
}


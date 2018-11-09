package demo.test.tests;

import lombok.Cleanup;
import lombok.NonNull;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.io.RandomAccessBufferedFileInputStream;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.text.PDFTextStripper;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import webdriver.BaseTest;
import webdriver.resources.PropertiesResourceManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import static webdriver.resources.Constants.PROPERTIES_TEST;

public class PdfTest extends BaseTest {
    private PropertiesResourceManager testProps = new PropertiesResourceManager(PROPERTIES_TEST);
    private final String ARTIFACTS_DIR = testProps.getProperty("artifactsDir");

    @Parameters({"pdfFile"})
    @Test
    public void pdfTest(@NonNull String pdfFile) throws IOException {
        File file = Paths.get(ARTIFACTS_DIR, pdfFile).toFile();
        PDFParser pdfParser = new PDFParser(new RandomAccessBufferedFileInputStream(file));
        @Cleanup PDDocument document = pdfParser.getPDDocument();
        pdfParser.parse();
        PDFTextStripper pdfStripper = new PDFTextStripper();
        pdfStripper.setEndPage(0);
        pdfStripper.setEndPage(document.getNumberOfPages());

        for (PDPage page : document.getPages()) {
            PDResources res = page.getResources();
            for (COSName fontName : res.getFontNames()) {
                PDFont font = res.getFont(fontName);
                System.out.println(font.getName());
            }
        }
    }
}

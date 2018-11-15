package demo.test.tests;

import com.pdfunit.AssertThat;
import com.pdfunit.filter.page.PagesToUse;
import lombok.Cleanup;
import lombok.NonNull;
import org.apache.pdfbox.io.RandomAccessBufferedFileInputStream;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import webdriver.BaseTest;
import webdriver.resources.PropertiesResourceManager;
import webdriver.utils.pdf.PDFTextStripperHelper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static com.pdfunit.Constants.FIRST_PAGE;
import static webdriver.resources.Constants.PROPERTIES_TEST;

public class PdfTest extends BaseTest {
    private PropertiesResourceManager testProps = new PropertiesResourceManager(PROPERTIES_TEST);
    private final String ARTIFACTS_DIR = testProps.getProperty("artifactsDir");
    private File file;

    @Parameters({"pdfFile"})
    @BeforeClass
    public void beforeClass(@NonNull String pdfFile) {
        this.file = Paths.get(ARTIFACTS_DIR, pdfFile).toFile();
    }

    @Test
    public void pdfTest1() throws IOException {
        List<String> fonts = Arrays.asList("BCDEEE+Calibri Light", "Arial", "BCDFEE+Calibri");

        PDFParser pdfParser = new PDFParser(new RandomAccessBufferedFileInputStream(file));
        @Cleanup PDDocument document = pdfParser.getPDDocument();
        pdfParser.parse();
        PDFTextStripperHelper stripperHelper = new PDFTextStripperHelper();

        stripperHelper.setEndPage(0);
        stripperHelper.setEndPage(document.getNumberOfPages());

        LOGGER.info(stripperHelper.getText(document));
        stripperHelper.getCharactersByArticle().forEach(text ->
                text.forEach(ch -> System.out.println(ch.toString() + " " + ch.getFont().getName() + " " + (int) ch.getFontSize())));

        for (PDPage page : document.getPages()) {
            stripperHelper.processPage(page);

            PDResources res = page.getResources();

            res.getFontNames().forEach(font -> {
                try {
                    ASSERT_WRAPPER.softAssertTrue(fonts.contains(res.getFont(font).getName()),
                            String.format("List %s doesn't have font %s", fonts.toString(), font.getName()));
                } catch (IOException e) {
                    LOGGER.printStackTrace(e);
                }
            });
        }
    }

    @Test
    public void pdfTest2() {
        AssertThat.document(file)
                .hasNumberOfPages(1);

        AssertThat.document(file)
                .restrictedTo(PagesToUse.getPage(1))
                .hasText()
                .containing("Arial");

        AssertThat.document(file)
                .hasText()
                .first("Calibri Light")
                .then("Calibri Light")
                .then("Calibri");

        AssertThat.document(file)
                .restrictedTo(FIRST_PAGE)
                .hasText()
                .containing("11")
                .containing("15");

        AssertThat.document(file)
                .hasFont()
                .withNameContaining("Arial");

        ASSERT_WRAPPER.softAssertAll();
    }

}

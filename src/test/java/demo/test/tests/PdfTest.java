package demo.test.tests;

import com.pdfunit.AssertThat;
import com.pdfunit.filter.page.PagesToUse;
import de.redsix.pdfcompare.CompareResult;
import de.redsix.pdfcompare.PdfComparator;
import lombok.Cleanup;
import lombok.NonNull;
import org.apache.pdfbox.io.RandomAccessBufferedFileInputStream;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.testng.Assert;
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
import static java.lang.String.format;
import static webdriver.resources.Constants.PROPERTIES_TEST;

public class PdfTest extends BaseTest {
    private PropertiesResourceManager testProps = new PropertiesResourceManager(PROPERTIES_TEST);
    private final String ARTIFACTS_DIR = testProps.getProperty("artifactsDir");
    private File CANONICAL_FILE;

    @Parameters({"pdfFile"})
    @BeforeClass(alwaysRun = true)
    public void beforeClass(@NonNull String pdfFile) {
        this.CANONICAL_FILE = Paths.get(ARTIFACTS_DIR, pdfFile).toFile();
    }

    @Test(groups = {"pdf"})
    public void pdfTest1() throws IOException {
        List<String> fonts = Arrays.asList("BCDEEE+Calibri Light", "Arial", "BCDFEE+Calibri");

        PDFParser pdfParser = new PDFParser(new RandomAccessBufferedFileInputStream(CANONICAL_FILE));
        @Cleanup PDDocument document = pdfParser.getPDDocument();
        pdfParser.parse();
        PDFTextStripperHelper stripperHelper = new PDFTextStripperHelper();

        stripperHelper.setEndPage(0);
        stripperHelper.setEndPage(document.getNumberOfPages());

        LOGGER.info(stripperHelper.getText(document));
        stripperHelper.getCharactersByArticle().forEach(text ->
                text.forEach(ch -> LOGGER.info(
                        String.format("%s %s %d",ch.toString(), ch.getFont().getName(),(int) ch.getFontSize()))));

        for (PDPage page : document.getPages()) {
            stripperHelper.processPage(page);

            PDResources res = page.getResources();

            res.getFontNames().forEach(font -> {
                try {
                    ASSERT_WRAPPER.softAssertTrue(fonts.contains(res.getFont(font).getName()),
                            format("List %s doesn't have font %s", fonts.toString(), font.getName()));
                } catch (IOException e) {
                    LOGGER.printStackTrace(e);
                }
            });
        }
    }

    @Test(groups = {"pdf$"})
    public void pdfTest2() {
        AssertThat.document(CANONICAL_FILE)
                .hasNumberOfPages(1);

        AssertThat.document(CANONICAL_FILE)
                .restrictedTo(PagesToUse.getPage(1))
                .hasText()
                .containing("Arial");

        AssertThat.document(CANONICAL_FILE)
                .hasText()
                .first("Calibri Light")
                .then("Calibri Light")
                .then("Calibri");

        AssertThat.document(CANONICAL_FILE)
                .restrictedTo(FIRST_PAGE)
                .hasText()
                .containing("11")
                .containing("15");

        AssertThat.document(CANONICAL_FILE)
                .hasFont()
                .withNameContaining("Arial");

        ASSERT_WRAPPER.softAssertAll();
    }

    @Test(groups = {"pdf"})
    public void pdfCompareColor() throws IOException {
        File actualPdfFil = Paths.get(ARTIFACTS_DIR, "note-color.pdf").toFile();
        final CompareResult result = new PdfComparator(CANONICAL_FILE.getPath(), actualPdfFil.getPath()).compare();
        result.writeTo(Paths.get(ARTIFACTS_DIR, "note-color-diff").toAbsolutePath().toString());
        Assert.assertTrue(result.isNotEqual());
    }
}

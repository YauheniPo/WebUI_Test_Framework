package demo.test.tests;

import com.assertthat.selenium_shutterbug.core.ElementSnapshot;
import com.assertthat.selenium_shutterbug.core.Shutterbug;
import com.assertthat.selenium_shutterbug.utils.image.ImageProcessor;
import demo.test.webentities.forms.TutByHeader;
import demo.test.webentities.pages.AuthorizeEmailPage;
import demo.test.webentities.pages.TutByHomePage;
import lombok.SneakyThrows;
import org.testng.annotations.Test;
import webdriver.VisualTest;
import webdriver.driver.Browser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.FileSystems;

public class TutByShutterbugVisualTest extends VisualTest {

    @Test
    public void shutterTest() {
        LOGGER.step(1, "Opening the main page");
        TutByHomePage tutByHomePage = new TutByHomePage();

        LOGGER.step(2, "Receiving data from the sender's mail");
        tutByHomePage.getHeader().clickTopBarElement(TutByHeader.TopBar.EMAIL);

        visualChecking();
    }

    @SneakyThrows(IOException.class)
    private void visualChecking() {
        AuthorizeEmailPage authorizeEmailPage = new AuthorizeEmailPage();


        String pngExtention = ".png";
        String infoPanelSnapshotName = "info_panel";
        String checkboxMemorySnapshotName = "checkbox_memory";
        String authorizeEmailSnapshotsPath = "authorize_email/";
        String snapshotsEqualsResultsPath = "artifacts";

//        Shutterbug.shootPage(Browser.getDriver(), ScrollStrategy.WHOLE_PAGE_CHROME, 500, false).save();
        //.withThumbnail(String path, String name, double scale).
        //.withTitle("Login_Form" + new Date()).
        //.getImage();
        //.save();

        double deviation = 0.08;

        ElementSnapshot checkBoxMemoryElementSnapshot = Shutterbug.shootElement(Browser.getDriver(), authorizeEmailPage.getCheckBoxMemory().getElement());
        BufferedImage checkboxMemoryImage = ImageIO.read(Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(super.visualEnv.screenshotsDir() + authorizeEmailSnapshotsPath + checkboxMemorySnapshotName + pngExtention));
        String equalsDiffPath = String.valueOf(FileSystems.getDefault()
                .getPath(snapshotsEqualsResultsPath, checkboxMemorySnapshotName));

        ASSERT_WRAPPER.softAssertTrue(
            checkBoxMemoryElementSnapshot.equalsWithDiff(checkboxMemoryImage, equalsDiffPath, deviation), "checkBoxMemoryElementSnapshot");


        ElementSnapshot infoPanelElementSnapshot = Shutterbug.shootElement(Browser.getDriver(), authorizeEmailPage.getLabelInfoPanel().getElement());
        BufferedImage infoPanelImage = ImageIO.read(Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(super.visualEnv.screenshotsDir() + authorizeEmailSnapshotsPath + infoPanelSnapshotName + pngExtention));
        equalsDiffPath = String.valueOf(FileSystems.getDefault()
                .getPath(snapshotsEqualsResultsPath, infoPanelSnapshotName));

        ASSERT_WRAPPER.softAssertTrue(
                infoPanelElementSnapshot.equalsWithDiff(infoPanelImage, equalsDiffPath, deviation), "infoPanelElementSnapshot");


        BufferedImage loginFormImage1 = ImageIO.read(Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(super.visualEnv.screenshotsDir() + authorizeEmailSnapshotsPath + "login_form1.png"));
        BufferedImage loginFormImage2 = ImageIO.read(Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(super.visualEnv.screenshotsDir() + authorizeEmailSnapshotsPath + "login_form2.png"));
        equalsDiffPath = String.valueOf(FileSystems.getDefault()
                .getPath(snapshotsEqualsResultsPath, "login_form"));

        ASSERT_WRAPPER.softAssertTrue(
                ImageProcessor.imagesAreEqualsWithDiff(loginFormImage1, loginFormImage2, equalsDiffPath, deviation), "loginFormImage");
    }
}

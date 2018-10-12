package demo.test.tests;

import com.assertthat.selenium_shutterbug.core.ElementSnapshot;
import com.assertthat.selenium_shutterbug.core.Shutterbug;
import com.assertthat.selenium_shutterbug.utils.image.ImageProcessor;
import demo.test.webentities.forms.TutByHeader;
import demo.test.webentities.pages.AuthorizeEmailPage;
import demo.test.webentities.pages.TutByHomePage;
import lombok.SneakyThrows;
import org.testng.annotations.Test;
import webdriver.BaseTest;
import webdriver.driver.Browser;
import webdriver.utils.sikuli.SikuliConf;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class TutByScreenTest extends BaseTest {

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
        String loginFormSnapshotName = "email_login_form";
        String infoPanelSnapshotName = "info_panel";
        String checkboxMemorySnapshotName = "checkbox_memory";
        String authorizeEmailSnapshotsPath = "screenshots/authorize_email/";
        String snapshotsEqualsResultsPath = "screen_equals_results/";

//        Shutterbug.shootPage(Browser.getDriver(), ScrollStrategy.WHOLE_PAGE_CHROME, 500, false).save();
        //.withThumbnail(String path, String name, double scale).
        //.withTitle("Login_Form" + new Date()).
        //.getImage();
        //.save();

        double deviation = 0.05;

        ElementSnapshot checkBoxMemoryElementSnapshot = Shutterbug.shootElement(Browser.getDriver(), authorizeEmailPage.getCheckBoxMemory().getElement());
        BufferedImage checkboxMemoryImage = ImageIO.read(Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(authorizeEmailSnapshotsPath + checkboxMemorySnapshotName + pngExtention));
        String equalsDiffPath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource(
            authorizeEmailSnapshotsPath + snapshotsEqualsResultsPath)).getPath() + checkboxMemorySnapshotName;

        ASSERT_WRAPPER.softAssertTrue(
            checkBoxMemoryElementSnapshot.equalsWithDiff(checkboxMemoryImage, equalsDiffPath, deviation), "checkBoxMemoryElementSnapshot");


        ElementSnapshot infoPanelElementSnapshot = Shutterbug.shootElement(Browser.getDriver(), authorizeEmailPage.getLabelInfoPanel().getElement());
        BufferedImage infoPanelImage = ImageIO.read(Thread.currentThread().getContextClassLoader()
                                                        .getResourceAsStream(authorizeEmailSnapshotsPath + infoPanelSnapshotName + pngExtention));
        equalsDiffPath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource(
            authorizeEmailSnapshotsPath + snapshotsEqualsResultsPath)).getPath() +  infoPanelSnapshotName;

        ASSERT_WRAPPER.softAssertTrue(
                infoPanelElementSnapshot.equalsWithDiff(infoPanelImage, equalsDiffPath, deviation), "infoPanelElementSnapshot");


        BufferedImage loginFormImage1 = ImageIO.read(Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(authorizeEmailSnapshotsPath + "login_form1.png"));
        BufferedImage loginFormImage2 = ImageIO.read(Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(authorizeEmailSnapshotsPath + "login_form2.png"));
        equalsDiffPath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource(
            authorizeEmailSnapshotsPath + snapshotsEqualsResultsPath)).getPath() + "login_form";

        ASSERT_WRAPPER.softAssertTrue(
                ImageProcessor.imagesAreEqualsWithDiff(loginFormImage1, loginFormImage2, equalsDiffPath, deviation), "loginFormImage");


        SikuliConf sikuliConf = new SikuliConf();
        ASSERT_WRAPPER.assertTrue(sikuliConf.exists(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource(
            authorizeEmailSnapshotsPath)).getPath() +  checkboxMemorySnapshotName + pngExtention));
    }
}

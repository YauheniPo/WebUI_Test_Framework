package demo.test.tests;

import demo.test.webentities.forms.TutByHeader;
import demo.test.webentities.pages.AuthorizeEmailPage;
import demo.test.webentities.pages.TutByHomePage;
import org.testng.annotations.Test;
import webdriver.BaseTest;
import webdriver.utils.visual.SikuliConf;

import java.util.Objects;

public class TutBySikuliVisualTest extends BaseTest {

    @Test
    public void shutterTest() {
        LOGGER.step(1, "Opening the main page");
        TutByHomePage tutByHomePage = new TutByHomePage();

        LOGGER.step(2, "Receiving data from the sender's mail");
        tutByHomePage.getHeader().clickTopBarElement(TutByHeader.TopBar.EMAIL);

        visualChecking();
    }

    private void visualChecking() {
        new AuthorizeEmailPage();


        String pngExtention = ".png";
        String checkboxMemorySnapshotName = "checkbox_memory";
        String authorizeEmailSnapshotsPath = "screenshots/authorize_email/";


        SikuliConf sikuliConf = new SikuliConf();
        ASSERT_WRAPPER.assertTrue(sikuliConf.exists(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource(
                authorizeEmailSnapshotsPath)).getPath() + checkboxMemorySnapshotName + pngExtention));
    }
}

package demo.test.tests;

import demo.test.webentities.forms.TutByHeader;
import demo.test.webentities.pages.AuthorizeEmailPage;
import demo.test.webentities.pages.TutByHomePage;
import org.testng.annotations.Test;
import webdriver.VisualTest;
import webdriver.utils.visual.SikuliConf;

import java.nio.file.Paths;

public class TutBySikuliVisualTest extends VisualTest {

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

        SikuliConf sikuliConf = new SikuliConf();
        ASSERT_WRAPPER.assertTrue(sikuliConf.exists(Paths.get(super.visualEnv.screenshotsDir(),
                AuthorizeEmailPage.AUTHORIZE_EMAIL_PAGE_SNAPSHOTS_PATH,
                AuthorizeEmailPage.CHECKBOX_MEMORY_SNAPSHOT_NAME + super.visualEnv.imageExtention()).toString()));
    }
}

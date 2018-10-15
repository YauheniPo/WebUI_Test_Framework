package demo.test.tests;

import demo.test.webentities.forms.TutByHeader;
import demo.test.webentities.models.images.CheckBoxMemory;
import demo.test.webentities.pages.AuthorizeEmailPage;
import demo.test.webentities.pages.TutByHomePage;
import org.testng.annotations.Test;
import webdriver.VisualTest;
import webdriver.utils.visual.OcularConf;

import java.nio.file.Paths;

public class TutByOcularVisualTest extends VisualTest {

    @Test
    public void shutterTest() {
        LOGGER.step(1, "Opening the main page");
        TutByHomePage tutByHomePage = new TutByHomePage();

        LOGGER.step(2, "Receiving data from the sender's mail");
        tutByHomePage.getHeader().clickTopBarElement(TutByHeader.TopBar.EMAIL);

        visualChecking();
    }

    private void visualChecking() {

        new OcularConf(Paths.get(visualEnv.snapshotsEqualsResultsPath()).toAbsolutePath().toString(),
                Paths.get(visualEnv.screenshotsDir(), AuthorizeEmailPage.AUTHORIZE_EMAIL_PAGE_SNAPSHOTS_PATH).toAbsolutePath().toString())
                .saveSnapshot(true);
        ASSERT_WRAPPER.assertTrue(new CheckBoxMemory().compare().isEqualsImages());
    }
}

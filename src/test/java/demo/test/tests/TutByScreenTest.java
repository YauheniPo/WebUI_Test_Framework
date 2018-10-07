package demo.test.tests;

import com.assertthat.selenium_shutterbug.core.Shutterbug;
import com.assertthat.selenium_shutterbug.core.Snapshot;
import com.assertthat.selenium_shutterbug.utils.web.ScrollStrategy;
import demo.test.webentities.forms.TutByHeader;
import demo.test.webentities.pages.AuthorizeEmailPage;
import demo.test.webentities.pages.TutByHomePage;
import lombok.SneakyThrows;
import org.testng.annotations.Test;
import webdriver.BaseTest;
import webdriver.driver.Browser;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;

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
        String authorizeEmailSnapshotsPath = "src/test/resources/screenshots/authorize_email";
        String snapshotsEqualsResultsPath = "screen_equals_results";

        Snapshot snapshot = Shutterbug.shootPage(Browser.getDriver(), ScrollStrategy.WHOLE_PAGE_CHROME);
        snapshot.withName(loginFormSnapshotName).save(authorizeEmailSnapshotsPath);

        //.withThumbnail(String path, String name, double scale).
        //.withTitle("Login_Form" + new Date()).
        //.getImage();
        //.save();


        ASSERT_WRAPPER.assertTrue(Shutterbug.shootPage(Browser.getDriver(), ScrollStrategy.WHOLE_PAGE_CHROME)
                .equalsWithDiff(snapshot,
                        String.valueOf(FileSystems.getDefault().getPath(authorizeEmailSnapshotsPath, snapshotsEqualsResultsPath, loginFormSnapshotName + pngExtention)),
                        0.1));

        ASSERT_WRAPPER.assertTrue(Shutterbug.shootPage(Browser.getDriver(), ScrollStrategy.WHOLE_PAGE_CHROME)
                .equals(ImageIO.read(new File(String.valueOf(FileSystems.getDefault().getPath(authorizeEmailSnapshotsPath, loginFormSnapshotName + pngExtention)))),
                        0.1));

        ASSERT_WRAPPER.assertTrue(Shutterbug.shootElement(Browser.getDriver(), authorizeEmailPage.getLabelInfoPanel().getElement())
                .equalsWithDiff(ImageIO.read(new File(String.valueOf(FileSystems.getDefault().getPath(authorizeEmailSnapshotsPath, infoPanelSnapshotName + pngExtention)))),
                        String.valueOf(FileSystems.getDefault().getPath(authorizeEmailSnapshotsPath, snapshotsEqualsResultsPath, infoPanelSnapshotName))));
    }
}

package demo.test.tests;

import com.assertthat.selenium_shutterbug.core.Shutterbug;
import demo.test.webentities.forms.TutByHeader;
import demo.test.webentities.pages.AuthorizeEmailPage;
import demo.test.webentities.pages.TutByHomePage;
import org.apache.commons.io.IOUtils;
import org.im4java.core.CompareCmd;
import org.im4java.core.IMOperation;
import org.im4java.process.ProcessStarter;
import org.im4java.process.StandardStream;
import org.testng.annotations.Test;
import webdriver.VisualTest;
import webdriver.driver.Browser;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

import static demo.test.webentities.pages.AuthorizeEmailPage.CHECKBOX_MEMORY_SNAPSHOT_NAME;

public class TutByMagickVisualTest extends VisualTest {

    @Test(groups = {"visual"})
    public void shutterTest() {
        LOGGER.step(1, "Opening the main page");
        TutByHomePage tutByHomePage = new TutByHomePage();

        LOGGER.step(2, "Receiving data from the sender's mail");
        tutByHomePage.getHeader().clickTopBarElement(TutByHeader.TopBar.EMAIL);

        ASSERT_WRAPPER.softAssertTrue(visualCompare(), "Comparison Failed!");

        ASSERT_WRAPPER.softAssertAll();
    }

    private boolean visualCompare() {
        AuthorizeEmailPage authorizeEmailPage = new AuthorizeEmailPage();

        Shutterbug.shootElement(Browser.getDriver(), authorizeEmailPage.getCheckBoxMemory().getElement())
                .withName(CHECKBOX_MEMORY_SNAPSHOT_NAME + "_actual").save(super.visualEnv.snapshotsEqualsResultsPath());

        ProcessStarter.setGlobalSearchPath(super.visualEnv.magickInstallDir());

        CompareCmd compare = new CompareCmd();
        compare.setErrorConsumer(StandardStream.STDERR);
        IMOperation cmpOp = new IMOperation();
        cmpOp.addRawArgs("magick", "compare");
        cmpOp.fuzz(3.0);
        cmpOp.metric("ae");
        cmpOp.addImage(Paths.get(super.visualEnv.screenshotsDir(), AuthorizeEmailPage.AUTHORIZE_EMAIL_PAGE_SNAPSHOTS_PATH,
                CHECKBOX_MEMORY_SNAPSHOT_NAME + super.visualEnv.imageExtention()).toString());
        cmpOp.addImage(Paths.get(super.visualEnv.snapshotsEqualsResultsPath(),
                CHECKBOX_MEMORY_SNAPSHOT_NAME + "_actual" + super.visualEnv.imageExtention()).toString());
        cmpOp.addImage(Paths.get(super.visualEnv.snapshotsEqualsResultsPath(),
                CHECKBOX_MEMORY_SNAPSHOT_NAME + "_diff" + super.visualEnv.imageExtention()).toString());

//        String[] cmd = new String[] {"magick", "compare", "-metric", "ae",
//                Paths.get(super.visualEnv.screenshotsDir(), AuthorizeEmailPage.AUTHORIZE_EMAIL_PAGE_SNAPSHOTS_PATH,
//                        CHECKBOX_MEMORY_SNAPSHOT_NAME + super.visualEnv.imageExtention()).toString(),
//                Paths.get(super.visualEnv.snapshotsEqualsResultsPath(),
//                        CHECKBOX_MEMORY_SNAPSHOT_NAME + "_actual" + super.visualEnv.imageExtention()).toString(),
//                Paths.get(super.visualEnv.snapshotsEqualsResultsPath(),
//                CHECKBOX_MEMORY_SNAPSHOT_NAME + "_diff" + super.visualEnv.imageExtention()).toString()};

        try {
            Process compProcess = Runtime.getRuntime().exec(cmpOp.getCmdArgs().toArray(new String[0]), new String[0]);

            InputStream std = compProcess.getInputStream();
            InputStream err = compProcess.getErrorStream();
            String procOut = IOUtils.toString(std, StandardCharsets.UTF_8.name());
            String procErr = IOUtils.toString(err, StandardCharsets.UTF_8.name());

            return procOut.isEmpty() && Integer.valueOf(procErr) == 0;
        }
        catch (IOException ex) {
            LOGGER.printStackTrace(ex);
            return false;
        }
    }
}
package demo.test.tests;

import com.assertthat.selenium_shutterbug.core.ElementSnapshot;
import com.assertthat.selenium_shutterbug.core.Shutterbug;
import com.assertthat.selenium_shutterbug.utils.image.ImageProcessor;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import demo.test.webentities.forms.TutByHeader;
import demo.test.webentities.pages.AuthorizeEmailPage;
import demo.test.webentities.pages.TutByHomePage;
import lombok.SneakyThrows;
import org.openqa.selenium.By;
import org.testng.annotations.Test;
import webdriver.VisualTest;
import webdriver.driver.Browser;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;

import static com.codeborne.selenide.Selenide.$;

public class TutByShutterbugVisualTest extends VisualTest {

    @Test
    public void shutterTest() {
        LOGGER.step(1, "Opening the main page");
        TutByHomePage tutByHomePage = new TutByHomePage();

        LOGGER.step(2, "Receiving data from the sender's mail");
        tutByHomePage.getHeader().clickTopBarElement(TutByHeader.TopBar.EMAIL);

        visualChecking();

        ASSERT_WRAPPER.softAssertAll();
    }

    @SneakyThrows(IOException.class)
    private void visualChecking() {
        AuthorizeEmailPage authorizeEmailPage = new AuthorizeEmailPage();


//        Shutterbug.shootPage(Browser.getDriver(), ScrollStrategy.WHOLE_PAGE_CHROME, 500, false).save();
        //.withThumbnail(String path, String name, double scale).
        //.withTitle("Login_Form" + new Date()).
        //.getImage();
        //.save();

        double deviation = 0.08;

        ElementSnapshot checkBoxMemoryElementSnapshot = Shutterbug.shootElement(Browser.getDriver(),
                authorizeEmailPage.getCheckBoxMemory().getElement());
        BufferedImage checkboxMemoryImage = ImageIO.read(new File(Paths.get(super.visualEnv.screenshotsDir(),
                AuthorizeEmailPage.AUTHORIZE_EMAIL_PAGE_SNAPSHOTS_PATH,
                AuthorizeEmailPage.CHECKBOX_MEMORY_SNAPSHOT_NAME + super.visualEnv.imageExtention()).toString()));
        String equalsDiffPath = Paths.get(super.visualEnv.snapshotsEqualsResultsPath(),
                AuthorizeEmailPage.CHECKBOX_MEMORY_SNAPSHOT_NAME).toString();

        ASSERT_WRAPPER.softAssertTrue(
            checkBoxMemoryElementSnapshot.equalsWithDiff(checkboxMemoryImage, equalsDiffPath, deviation),
                "checkBoxMemoryElementSnapshot");


        ElementSnapshot infoPanelElementSnapshot = Shutterbug.shootElement(Browser.getDriver(),
                authorizeEmailPage.getLabelInfoPanel().getElement());
        BufferedImage infoPanelImage = ImageIO.read(new File(Paths.get(super.visualEnv.screenshotsDir(),
                AuthorizeEmailPage.AUTHORIZE_EMAIL_PAGE_SNAPSHOTS_PATH,
                AuthorizeEmailPage.INFO_PANEL_SNAPSHOT_NAME + super.visualEnv.imageExtention()).toString()));
        equalsDiffPath = Paths.get(super.visualEnv.snapshotsEqualsResultsPath(),
                AuthorizeEmailPage.INFO_PANEL_SNAPSHOT_NAME).toString();

        ASSERT_WRAPPER.softAssertTrue(
                infoPanelElementSnapshot.equalsWithDiff(infoPanelImage, equalsDiffPath, deviation),
                "infoPanelElementSnapshot");


        BufferedImage loginFormImage1 = ImageIO.read(new File(Paths.get(
                super.visualEnv.screenshotsDir(),
                AuthorizeEmailPage.AUTHORIZE_EMAIL_PAGE_SNAPSHOTS_PATH,
                "login_form1" + super.visualEnv.imageExtention()).toString()));
        BufferedImage loginFormImage2 = ImageIO.read(new File(Paths.get(
                super.visualEnv.screenshotsDir(),
                AuthorizeEmailPage.AUTHORIZE_EMAIL_PAGE_SNAPSHOTS_PATH,
                "login_form2" + super.visualEnv.imageExtention()).toString()));
        equalsDiffPath = Paths.get(super.visualEnv.snapshotsEqualsResultsPath(), "login_form").toString();

        ASSERT_WRAPPER.softAssertTrue(
                ImageProcessor.imagesAreEqualsWithDiff(loginFormImage1, loginFormImage2, equalsDiffPath, deviation),
                "loginFormImage");
    }

    @Test
    public void test() {
        Configuration.browser = WebDriverRunner.CHROME;
        Selenide.open("https://www.google.com/imghp");
        SelenideElement googleLogo = $(By.id("hplogo"));
        SelenideElement searchBtn = $(By.id("mKlEF"));
        SelenideElement searchBox = $(By.className("gsfi"));

        searchBox.sendKeys("McKesson");

        Shutterbug.shootPage(WebDriverRunner.getWebDriver())
//                .blur(searchBox)
                .highlight(searchBtn)
//                .monochrome(googleLogo)
                .highlightWithText(googleLogo, Color.blue, 3, "Monochromed logo", Color.blue, new Font("SansSerif", Font.BOLD, 20))
                .highlightWithText(searchBox, "Searching word")
                .withTitle("Google home page - " + new Date())
                .withName("home_page")
                .withThumbnail(super.visualEnv.screenshotsDir() + "thumbnail", "home_page", 0.7)
                .save(super.visualEnv.screenshotsDir());
    }
}

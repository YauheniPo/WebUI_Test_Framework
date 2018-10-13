package demo.test.tests;

import com.assertthat.selenium_shutterbug.core.Shutterbug;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import java.awt.*;
import java.util.Date;

import static com.codeborne.selenide.Selenide.$;

public class Test1 {

    @Test
    public void test() {
        String resources = "src/test/resources/";
        String authorizeEmailSnapshotsPath = "screenshots";

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
                .withThumbnail(resources + authorizeEmailSnapshotsPath + "thumbnail", "home_page", 0.7)
                .save(resources + authorizeEmailSnapshotsPath);
    }
}

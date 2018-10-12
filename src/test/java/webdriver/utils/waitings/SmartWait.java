package webdriver.utils.waitings;

import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;
import webdriver.driver.Browser;

/**
 * The type Smart wait.
 */
public class SmartWait {

    /**
     * Wait for page load.
     */
    public static void waitForPageLoad() {
        new WebDriverWait(Browser.getDriver(), Browser.PAGE_WAIT).until(
            webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }

    /**
     * Wait for selenide element.
     *
     * @param elementLocator the element locator
     *
     * @return the selenide element
     */
    public static SelenideElement waitFor(By elementLocator) {
        return $(elementLocator).shouldBe(Condition.exist).waitUntil(Condition.visible, Browser.TIMEOUT_FOR_CONDITION);
    }
}

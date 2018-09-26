package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import webdriver.driver.Browser;

/**
 * The type Base form.
 */
public abstract class BaseForm extends BaseEntity {

    /**
     * Instantiates a new Base form.
     *
     * @param locator   the locator
     * @param formTitle the form title
     */
    protected BaseForm(final By locator, final String formTitle) {
        waitForPageLoad();
//        PageFactory.initElements(Browser.getDriver(), this);
        checkPageMainElement(locator, formTitle);
    }

    /**
     * Wait for page load.
     */
    private void waitForPageLoad() {
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(Browser.getDriver(), Browser.getTimeoutForCondition()).withMessage("Element was not found");
        wait.until((ExpectedCondition<Boolean>) driver -> ((JavascriptExecutor) driver)
            .executeScript("return document.readyState").equals("complete"));
    }

    /**
     * Check page main element.
     *
     * @param locator   the locator
     * @param formTitle the form title
     */
    private void checkPageMainElement(final By locator, final String formTitle) {
        LOGGER.info(String.format("Initialization %s element locator", formTitle));
        if (!Browser.getDriver().findElement(locator).isDisplayed()) {
            LOGGER.error(String.format("Page %s didn't initialization", formTitle));
            throw new NoSuchElementException(formTitle);
        }
    }
}
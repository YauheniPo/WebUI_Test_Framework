package demo.test.webentities.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import webdriver.BaseForm;

/**
 * The type Authorize email form.
 */
public class AuthorizeEmailPageFactory extends BaseForm {

    private static final By MAIN_LOCATOR = By.xpath("//*[@class='bookBody']");

    @FindBy(xpath = "//*[@id='form']//input[@name='login']")
    private WebElement textBoxInputLogin;

    @FindBy(xpath = "//*[@id='form']//input[@name='password']")
    private WebElement textBoxInputPassword;

    @FindBy(xpath = "//*[@type='submit']")
    private WebElement btnSighIn;

    /**
     * Instantiates a new Authorize email form.
     */
    public AuthorizeEmailPageFactory() {
        super(MAIN_LOCATOR, "tut.by Authorize Email Form");
    }

    /**
     * Input login.
     *
     * @param login the login
     */
    public void inputLogin(String login) {
        this.textBoxInputLogin.sendKeys(login);
    }

    /**
     * Input password.
     *
     * @param password the password
     */
    public void inputPsw(String password) {
        this.textBoxInputPassword.sendKeys(password);
    }

    /**
     * Click sign in.
     */
    public void clickSignIn() {
        this.btnSighIn.click();
    }

    /**
     * Sign in email account page.
     *
     * @param login    the login
     * @param password the password
     *
     * @return the email account page
     */
    public EmailAccountPage signIn(String login, String password) {
        inputLogin(login);
        inputPsw(password);
        clickSignIn();
        return new EmailAccountPage();
    }
}
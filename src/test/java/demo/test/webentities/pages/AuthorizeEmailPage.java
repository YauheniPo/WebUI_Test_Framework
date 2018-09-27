package demo.test.webentities.pages;

import demo.test.webentities.locators.IAuthorizeEmailPage;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.By;
import webdriver.BaseForm;
import webdriver.elements.Button;
import webdriver.elements.TextBox;

/**
 * The type Authorize email form.
 */
public class AuthorizeEmailPage extends BaseForm {

    private static final IAuthorizeEmailPage authorizeEmailPageLocators = ConfigFactory.create(IAuthorizeEmailPage.class);
    private static final String MAIN_LOCATOR = authorizeEmailPageLocators.mainLocator();
    private final String textBoxInputLoginLocator = authorizeEmailPageLocators.textBoxInputLogin();
    private final String textBoxInputPasswordLocator = authorizeEmailPageLocators.textBoxInputPassword();
    private final String btnSighInLocator = authorizeEmailPageLocators.btnSighIn();

    private final TextBox textBoxInputLogin = new TextBox(By.xpath(textBoxInputLoginLocator), "Input Login");
    private final TextBox textBoxInputPassword = new TextBox(By.xpath(textBoxInputPasswordLocator), "Input Password");
    private final Button btnSighIn = new Button(By.xpath(btnSighInLocator), "Sign In");

    /**
     * Instantiates a new Authorize email form.
     */
    public AuthorizeEmailPage() {
        super(By.xpath(MAIN_LOCATOR), "tut.by Authorize Email Form");
    }

    /**
     * Input login.
     *
     * @param login the login
     */
    public void inputLogin(String login) {
        this.textBoxInputLogin.setText(login);
    }

    /**
     * Input password.
     *
     * @param password the password
     */
    public void inputPsw(String password) {
        this.textBoxInputPassword.setText(password);
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
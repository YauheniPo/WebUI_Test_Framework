package demo.test.pages;

import org.openqa.selenium.By;
import webdriver.BaseForm;
import webdriver.elements.Button;
import webdriver.elements.TextBox;

/**
 * The type Authorize email form.
 */
public class AuthorizeEmailPage extends BaseForm {
    private static final By MAIN_LOCATOR = By.xpath("//*[@class='bookBody']");
    private final TextBox textBoxInputLogin = new TextBox(By.xpath("//*[@id='form']//input[@name='login']"), "Input Login");
    private final TextBox textBoxInputPassword = new TextBox(By.xpath("//*[@id='form']//input[@name='password']"), "Input Password");
    private final Button btnSighIn = new Button(By.xpath("//*[@type='submit']"), "Sign In");

    /**
     * Instantiates a new Authorize email form.
     */
    public AuthorizeEmailPage() {
        super(MAIN_LOCATOR, "tut.by Authorize Email Form");
    }

    /**
     * Input login.
     *
     * @param login the login
     */
    public void inputLogin(String login) {
        textBoxInputLogin.setText(login);
    }

    /**
     * Input password.
     *
     * @param password the password
     */
    public void inputPsw(String password) {
        textBoxInputPassword.setText(password);
    }

    /**
     * Click sign in.
     */
    public void clickSignIn() {
        btnSighIn.click();
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
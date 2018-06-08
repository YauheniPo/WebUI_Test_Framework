package demo.test.forms;

import demo.test.pages.EmailAccountPage;
import org.openqa.selenium.By;
import webdriver.BaseForm;
import webdriver.elements.Button;
import webdriver.elements.TextBox;

public class AuthorizeEmailForm extends BaseForm {
	private static final By MAIN_LOCATOR = By.xpath("//*[@class='bookBody']");
	private final TextBox textBoxInputLogin = new TextBox(By.xpath("//*[@id='form']//input[@name='login']"), "Input Login");
	private final TextBox textBoxInputPassword = new TextBox(By.xpath("//*[@id='form']//input[@name='password']"), "Input Password");
	private final Button btnSighIn = new Button(By.xpath("//button[@type='submit']"), "Sign In");
	
	public AuthorizeEmailForm() {
		super(MAIN_LOCATOR, "tut.by Authorize Email Form");
	}

	public void inputLogin(String login) {
		textBoxInputLogin.setText(login);
	}

	public void inputPsw(String password) {
		textBoxInputPassword.setText(password);
	}

	public void clickSignIn() {
		btnSighIn.click();
	}

	public EmailAccountPage signIn(String login, String password) {
		inputLogin(login);
		inputPsw(password);
		clickSignIn();
		return new EmailAccountPage();
	}
}
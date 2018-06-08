package demo.test.pages;

import demo.test.forms.TutByHeader;
import org.openqa.selenium.By;
import webdriver.BaseForm;

public class TutByHomePage extends BaseForm {
	private static final By MAIN_LOCATOR = By.xpath("//*[@class='header-logo']");
    private final TutByHeader header = new TutByHeader();
	
	public TutByHomePage() {
		super(MAIN_LOCATOR, "tut.by Home Page ");
    }

    public TutByHeader getHeader() {
		return header;
	}
}
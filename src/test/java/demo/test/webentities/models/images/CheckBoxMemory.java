package demo.test.webentities.models.images;

import com.testautomationguru.ocular.Ocular;
import com.testautomationguru.ocular.comparator.OcularResult;
import com.testautomationguru.ocular.snapshot.Snap;
import demo.test.webentities.pages.AuthorizeEmailPage;
import webdriver.driver.Browser;

/**
 * The type Check box memory.
 */
@Snap("checkbox_memory.png")
public class CheckBoxMemory {

    /**
     * Compare ocular result.
     *
     * @return the ocular result
     */
    public OcularResult compare() {
        return Ocular.snapshot().from(this).sample()
                .using(Browser.getDriver()).element(new AuthorizeEmailPage().getCheckBoxMemory().getElement()).compare();
    }
}

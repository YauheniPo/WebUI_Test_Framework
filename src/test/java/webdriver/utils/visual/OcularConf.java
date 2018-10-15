package webdriver.utils.visual;

import com.testautomationguru.ocular.Ocular;
import com.testautomationguru.ocular.OcularConfiguration;
import org.openqa.selenium.WebElement;
import webdriver.driver.Browser;

import java.nio.file.Paths;

/**
 * The type Ocular conf.
 */
public class OcularConf {
    private OcularConfiguration ocularConfiguration = Ocular.config();

    /**
     * Instantiates a new Ocular conf.
     *
     * @param resultPath    the result path
     * @param snapshotsPath the snapshots path
     */
    public OcularConf(String resultPath, String snapshotsPath) {
        ocularConfiguration
                .resultPath(Paths.get(resultPath))
                .snapshotPath(Paths.get(snapshotsPath))
                .saveSnapshot(false);
    }

    /**
     * Global similarity ocular conf.
     *
     * @param similarity the similarity
     *
     * @return the ocular conf
     */
    public OcularConf globalSimilarity(int similarity) {
        ocularConfiguration.globalSimilarity(similarity);
        return this;
    }

    /**
     * Save snapshot ocular conf.
     *
     * @param isSave the is save
     *
     * @return the ocular conf
     */
    public OcularConf saveSnapshot(boolean isSave) {
        ocularConfiguration.saveSnapshot(isSave);
        return this;
    }

    /**
     * Compare boolean.
     *
     * @param aClass the a class
     *
     * @return the boolean
     */
    public boolean compare(Class aClass) {
        return Ocular.snapshot().from(aClass).sample().using(Browser.getDriver()).compare().isEqualsImages();
    }

    /**
     * Compare boolean.
     *
     * @param snapshotPath the snapshot path
     * @param webElement   the web element
     *
     * @return the boolean
     */
    public boolean compare(String snapshotPath, WebElement webElement) {
        return Ocular.snapshot().from(snapshotPath).sample().using(Browser.getDriver()).element(webElement).compare().isEqualsImages();
    }
}

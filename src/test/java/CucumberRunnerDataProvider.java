import cucumber.api.CucumberOptions;
import cucumber.api.testng.PickleEventWrapper;
import cucumber.api.testng.TestNGCucumberRunner;
import demo.test.utils.FactoryInitParams;
import demo.test.webentities.models.TestDataMails;
import lombok.NonNull;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import webdriver.BaseEntity;
import webdriver.common.ScenarioContext;

@CucumberOptions(
        plugin = {"html:target/cukes", "json:target/cukes/report.json", "junit:target/cukes/junit.xml"},
        features = {"src/test/features/"},
        glue = "steps",
        strict = true,
        tags = {"@check_email_data_provider"}
)
public class CucumberRunnerDataProvider extends BaseEntity {
    private static final ScenarioContext SCENARIO_CONTEXT = ScenarioContext.getInstance();
    private TestNGCucumberRunner testRunner;
    private String testData;

    @Parameters(value = {"testData"})
    @BeforeClass
    public void setUP(@NonNull String testData) {
        this.testData = testData;
        this.testRunner = new TestNGCucumberRunner(this.getClass());
    }

    @Test(dataProvider="initParams")
    public void initParams(@NonNull Object testData) {
        TestDataMails testDataMails = (TestDataMails) testData;
        SCENARIO_CONTEXT.setContext("senderMailLogin", testDataMails.getSenderMailLogin());
        SCENARIO_CONTEXT.setContext("senderMailPassword", testDataMails.getSenderMailPassword());
        SCENARIO_CONTEXT.setContext("recipientMailLogin", testDataMails.getRecipientMailLogin());
        SCENARIO_CONTEXT.setContext("recipientMailPassword", testDataMails.getRecipientMailPassword());
        for (Object[] rn : this.testRunner.provideScenarios()) {
            try {
                this.testRunner.runScenario(((PickleEventWrapper) rn[0]).getPickleEvent());
            } catch (Throwable throwable) {
                LOGGER.error(throwable.getMessage());
                throwable.printStackTrace();
            }
        }
    }

    @DataProvider(name = "initParams")
    public Object[] getParams() {
        return this.testData == null ? new String[]{null} : new FactoryInitParams().getTestData(this.testData);
    }

    @AfterClass
    public void tearDown() {
        this.testRunner.finish();
    }
}
import cucumber.api.CucumberOptions;
import cucumber.api.testng.CucumberFeatureWrapperImpl;
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
        features = {"src/test/features/"},
        glue = "steps",
        strict = true,
        format = {"pretty", "html:target/cukes", "json:target/cukes/report.json", "junit:target/cukes/junit.xml"}
        ,tags = {"@check_email_data_provider"}
)
public class CucumberRunnerDataProvider extends BaseEntity {
    private static final ScenarioContext SCENARIO_CONTEXT = ScenarioContext.getInstance();
    private TestNGCucumberRunner testRunner;
    private String testData;

    @Parameters(value = {"testData"})
    @BeforeClass
    public void setUP(@NonNull String testData) {
        this.testData = testData;
        testRunner = new TestNGCucumberRunner(this.getClass());
    }

    @Test(dataProvider="initParams")
    public void initParams(@NonNull Object testData) {
        TestDataMails testDataMails = (TestDataMails) testData;
        SCENARIO_CONTEXT.setContext("senderMailLogin", testDataMails.getSenderMailLogin());
        SCENARIO_CONTEXT.setContext("senderMailPassword", testDataMails.getSenderMailPassword());
        SCENARIO_CONTEXT.setContext("recipientMailLogin", testDataMails.getRecipientMailLogin());
        SCENARIO_CONTEXT.setContext("recipientMailPassword", testDataMails.getRecipientMailPassword());
        for (Object[] rn : testRunner.provideFeatures()) {
            for (Object r : rn) {
                testRunner.runCucumber(((CucumberFeatureWrapperImpl) r).getCucumberFeature());
            }
        }
    }

    @DataProvider(name = "initParams")
    public Object[] getParams() {
        return testData == null ? new String[]{null} : new FactoryInitParams().getTestData(testData);
    }

    @AfterClass
    public void tearDown() {
        testRunner.finish();
    }
}
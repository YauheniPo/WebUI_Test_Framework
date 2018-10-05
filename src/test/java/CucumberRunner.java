import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@CucumberOptions(
        plugin = {"pretty", "com.epam.reportportal.cucumber.StepReporter"},
        features = {"src/test/resources/features/"},
        glue = "steps",
        strict = true,
        format = {"html:target/cukes", "json:target/cukes/report.json", "junit:target/cukes/junit.xml"},
        tags = {"@check_email"} //{"@check_email"} {"@check_email_data_files"}
)
public class CucumberRunner extends AbstractTestNGCucumberTests {
}
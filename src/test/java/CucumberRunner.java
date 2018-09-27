import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@CucumberOptions(
        features = {"src/test/features/"},
        glue = "steps",
        strict = true,
        format = {"pretty", "html:target/cukes", "json:target/cukes/report.json", "junit:target/cukes/junit.xml"}
        ,tags = {"~@check_email_data_provider"}//{"@check_email"} {"@check_email_data_files"} {"~@check_email_data_provider"}
)
public class CucumberRunner extends AbstractTestNGCucumberTests {
}
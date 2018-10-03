import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@CucumberOptions(
        plugin = {"html:target/cukes", "json:target/cukes/report.json", "junit:target/cukes/junit.xml"},
        features = {"src/test/features/"},
        glue = "steps",
        strict = true,
        tags = {"@check_email"} //{"@check_email"} {"@check_email_data_files"}
)
public class CucumberRunner extends AbstractTestNGCucumberTests {
}
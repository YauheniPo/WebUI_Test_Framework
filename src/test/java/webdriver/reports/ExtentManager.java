package webdriver.reports;

import com.relevantcodes.extentreports.ExtentReports;
import lombok.Synchronized;

public class ExtentManager {
    private static ExtentReports extent;

    @Synchronized
    public static ExtentReports getReporter(){
        if(extent == null){
            String workingDir = System.getProperty("user.dir");
            extent = new ExtentReports(workingDir+"\\ExtentReports\\ExtentReportResults.html", true);
        }
        return extent;
    }
}
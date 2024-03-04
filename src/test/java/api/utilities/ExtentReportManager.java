package api.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestNGListener;
import org.testng.ITestResult;

import java.text.SimpleDateFormat;

public class ExtentReportManager implements ITestNGListener {
    public ExtentSparkReporter sparkReporter;
    public ExtentReports extent;
    public ExtentTest test;
    String repName;
    public void onStart(ITestContext testContest){
        System.out.println("In report section");
     //  String timeStamp = String.valueOf(new SimpleDateFormat("yyyy.MM>dd.HH.mm.ss"));
       repName = "Test-Report-"+".html";
       sparkReporter = new ExtentSparkReporter(".\\reports\\"+repName);
       sparkReporter.config().setDocumentTitle("RestAssureAutomation");
       sparkReporter.config().setReportName("Pet Store User");
       sparkReporter.config().setTheme(Theme.STANDARD);

       extent = new ExtentReports();
       extent.attachReporter(sparkReporter);
       extent.setSystemInfo("Application","Pest Store User");
       extent.setSystemInfo("Operating System",System.getProperty("os.name"));
       extent.setSystemInfo("User Name",System.getProperty("user.name"));
       extent.setSystemInfo("Environment","QA");
       extent.setSystemInfo("user","Anchal");
    }

     public void onTestSuccess(ITestResult result){
        test = extent.createTest(result.getName());
        test.assignCategory(result.getMethod().getGroups());
        test.createNode(result.getName());
        test.log(Status.PASS,"Test Passed");
     }

     public void onTestFailure(ITestResult result){
         test = extent.createTest(result.getName());
         test.assignCategory(result.getMethod().getGroups());
         test.createNode(result.getName());
         test.log(Status.FAIL,"Test Failed");
         test.log(Status.FAIL,result.getThrowable().getMessage());
     }

     public void onTestSkipped(ITestResult result){
         test = extent.createTest(result.getName());
         test.assignCategory(result.getMethod().getGroups());
         test.createNode(result.getName());
         test.log(Status.SKIP,"Test Skipped");
         test.log(Status.SKIP,result.getThrowable().getMessage());
     }

     public void onFinish(ITestContext testContext){
        extent.flush();
     }
}

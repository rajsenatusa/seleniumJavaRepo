package Utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.testBaseClass;

public class ExtentReportManager implements ITestListener {
    
    private ExtentSparkReporter sparkReporter;
    private ExtentReports extent;
    private ExtentTest test;
    private String reportFilePath;

    private static final String REPORTS_DIRECTORY = "./reports/";
    private static final String REPORT_TITLE = "InsuranceNow Automation Report";
    private static final String REPORT_NAME = "InsuranceNow Functional Testing";
    private static final Theme REPORT_THEME = Theme.DARK;

    @Override
    public void onStart(ITestContext context) {
        setupExtentReports(context);
    }

    private void setupExtentReports(ITestContext context) {
        reportFilePath = generateReportFilePath();

        // Configure ExtentSparkReporter
        sparkReporter = new ExtentSparkReporter(reportFilePath);
        configureSparkReporter();

        // Initialize ExtentReports
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        addSystemInformation(context);
    }

    private String generateReportFilePath() {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        return REPORTS_DIRECTORY + "Test-Report-" + timeStamp + ".html";
    }

    private void configureSparkReporter() {
        sparkReporter.config().setDocumentTitle(REPORT_TITLE);
        sparkReporter.config().setReportName(REPORT_NAME);
        sparkReporter.config().setTheme(REPORT_THEME);
    }

    private void addSystemInformation(ITestContext context) {
        extent.setSystemInfo("Application", "InsuranceNow");
        extent.setSystemInfo("Module", "Admin");
        extent.setSystemInfo("Sub Module", "Customers");
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
        extent.setSystemInfo("Environment", System.getProperty("APP_URL"));

        addTestNGParameters(context);
    }

    private void addTestNGParameters(ITestContext context) {
        String os = context.getCurrentXmlTest().getParameter("os");
        if (os != null) extent.setSystemInfo("Operating System", os);

        String browser = context.getCurrentXmlTest().getParameter("browser");
        if (browser != null) extent.setSystemInfo("Browser", browser);

        List<String> groups = context.getCurrentXmlTest().getIncludedGroups();
        if (!groups.isEmpty()) extent.setSystemInfo("Groups", String.join(", ", groups));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        createTest(result, Status.PASS, result.getName() + " executed successfully.");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest test = createTest(result, Status.FAIL, result.getName() + " failed.");
        test.log(Status.INFO, result.getThrowable().getMessage());
        attachScreenshot(result, test);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentTest test = createTest(result, Status.SKIP, result.getName() + " was skipped.");
        if (result.getThrowable() != null) test.log(Status.INFO, result.getThrowable().getMessage());
    }

    private ExtentTest createTest(ITestResult result, Status status, String message) {
        test = extent.createTest(result.getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(status, message);
        return test;
    }

    private void attachScreenshot(ITestResult result, ExtentTest test) {
        try {
            String screenshotPath = new testBaseClass().captureScreen(result.getName());
            test.addScreenCaptureFromPath(screenshotPath);
        } catch (IOException e) {
            test.log(Status.WARNING, "Failed to attach screenshot: " + e.getMessage());
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        flushReport();
        openReportInBrowser();
    }

    private void flushReport() {
        if (extent != null) {
            extent.flush();
        }
    }

    private void openReportInBrowser() {
        try {
            File reportFile = new File(reportFilePath);
            if (Desktop.isDesktopSupported() && reportFile.exists()) {
                Desktop.getDesktop().browse(reportFile.toURI());
            } else {
                System.err.println("Report file is not supported on this system or does not exist.");
            }
        } catch (IOException e) {
            System.err.println("Error opening the extent report: " + e.getMessage());
        }
    }
}

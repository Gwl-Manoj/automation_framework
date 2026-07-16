package com.automation.base;

import com.automation.utils.ConfigReader;
import com.automation.utils.EmailUtil;
import com.automation.utils.ExtentReportManager;
import com.automation.utils.ScreenshotUtil;
import com.automation.utils.WaitHelper;
import com.automation.utils.WebDriverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.*;
import java.lang.reflect.Method;

/**
 * Base Test Class
 * Provides common setup and teardown methods for all test classes
 */
public class BaseTest {
    protected static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

    /**
     * Setup method - runs before each test method
     * @param method Test method
     */
    @BeforeMethod(alwaysRun = true)
    public void setUp(Method method) {
        String methodName = method.getName();
        
        // Get test description from TestNG annotation
        String description = null;
        if (method.isAnnotationPresent(org.testng.annotations.Test.class)) {
            org.testng.annotations.Test testAnnotation = method.getAnnotation(org.testng.annotations.Test.class);
            description = testAnnotation.description();
        }
        
        logger.info("========================================");
        logger.info("Starting test: {}", methodName);
        logger.info("========================================");

        // Initialize WebDriver
        WebDriverFactory.initializeDriver();

        // Initialize wait helper
        WaitHelper.initializeWait();

        // Initialize reports
        ExtentReportManager.initializeReports();

        // Create test in report
        ExtentReportManager.createTest(
            methodName,
            description != null ? description : methodName
        );

        ExtentReportManager.logInfo("Test started: " + methodName);
    }

    /**
     * Teardown method - runs after each test method
     * @param result Test result
     */
    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        logger.info("========================================");
        logger.info("Finishing test: {}", result.getMethod().getMethodName());
        logger.info("Test Status: {}", getStatus(result.getStatus()));
        logger.info("========================================");

        // Capture screenshot on failure
        if (result.getStatus() == ITestResult.FAILURE) {
            String base64Screenshot = ScreenshotUtil.getScreenshotAsBase64(WebDriverFactory.getDriverOrNull());
            
            if (base64Screenshot != null && !base64Screenshot.isEmpty()) {
                ExtentReportManager.addScreenshotFromBase64(base64Screenshot, "Failure Screenshot");
                logger.info("Failure screenshot captured and added to report (base64)");
            } else {
                logger.warn("Failed to capture failure screenshot");
            }

            ExtentReportManager.logFail("Test Failed: " + result.getThrowable().getMessage());
            if (result.getThrowable() instanceof Exception) {
                ExtentReportManager.logException((Exception) result.getThrowable());
            } else {
                ExtentReportManager.logError(result.getThrowable().toString());
            }
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            ExtentReportManager.logPass("Test Passed Successfully");
        } else if (result.getStatus() == ITestResult.SKIP) {
            ExtentReportManager.logSkip("Test Skipped");
        }

        // Flush reports
        ExtentReportManager.flushReports();

        // Quit WebDriver
        WebDriverFactory.quitDriver();
    }

    /**
     * Setup method - runs before all tests in the class
     */
    @BeforeClass(alwaysRun = true)
    public void setUpClass() {
        logger.info("========================================");
        logger.info("Starting test class: {}", this.getClass().getName());
        logger.info("========================================");
    }

    /**
     * Teardown method - runs after all tests in the class
     */
    @AfterClass(alwaysRun = true)
    public void tearDownClass() {
        logger.info("========================================");
        logger.info("Finishing test class: {}", this.getClass().getName());
        logger.info("========================================");

        // Close reports
        ExtentReportManager.closeReports();
    }

    /**
     * Setup method - runs before all tests in the suite
     */
    @BeforeSuite(alwaysRun = true)
    public void setUpSuite() {
        logger.info("========================================");
        logger.info("Starting test suite");
        logger.info("========================================");
    }

    /**
     * Teardown method - runs after all tests in the suite
     */
    @AfterSuite(alwaysRun = true)
    public void tearDownSuite() {
        logger.info("========================================");
        logger.info("Finishing test suite");
        logger.info("========================================");
        
        // Send test report via email
        EmailUtil.sendTestReport();
    }

    /**
     * Get test status string
     * @param status Test status code
     * @return Status string
     */
    private String getStatus(int status) {
        switch (status) {
            case ITestResult.SUCCESS:
                return "PASSED";
            case ITestResult.FAILURE:
                return "FAILED";
            case ITestResult.SKIP:
                return "SKIPPED";
            default:
                return "UNKNOWN";
        }
    }

    /**
     * Navigate to base URL
     */
    protected void navigateToBaseUrl() {
        String baseUrl = ConfigReader.getBaseUrl();
        logger.info("Navigating to base URL: {}", baseUrl);
        WebDriverFactory.navigateTo(baseUrl);
        ExtentReportManager.logInfo("Navigated to: " + baseUrl);
    }

    /**
     * Navigate to specific URL
     * @param url URL to navigate to
     */
    protected void navigateTo(String url) {
        logger.info("Navigating to URL: {}", url);
        WebDriverFactory.navigateTo(url);
        ExtentReportManager.logInfo("Navigated to: " + url);
    }
}
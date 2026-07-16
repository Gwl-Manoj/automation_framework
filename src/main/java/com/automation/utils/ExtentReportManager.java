package com.automation.utils;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.xml.XmlSuite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Extent Report Manager
 * Manages Extent Reports for test execution reporting
 * Implements TestNG IReporter interface
 */
public class ExtentReportManager implements IReporter {
    private static final Logger logger = LoggerFactory.getLogger(ExtentReportManager.class);
    private static ExtentReports extentReports;
    private static ExtentTest extentTest;

    /**
     * Initialize Extent Reports
     */
    public static void initializeReports() {
        if (extentReports == null) {
            String reportPath = ConfigReader.getProperty("extent.report.path", "test-output/extent-reports/ExtentReport.html");
            
            // Create report directory if it doesn't exist
            java.io.File reportDir = new java.io.File(reportPath).getParentFile();
            if (!reportDir.exists()) {
                reportDir.mkdirs();
            }

            // Initialize Spark reporter (ExtentReports 5.x)
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
            sparkReporter.config().setDocumentTitle("Automation Test Report");
            sparkReporter.config().setReportName("UI Automation Test Results");
            sparkReporter.config().setTimeStampFormat("yyyy-MM-dd HH:mm:ss");
            // sparkReporter.config().setTheme(Theme.STANDARD); // Uncomment if Theme is available

            // Initialize ExtentReports
            extentReports = new ExtentReports();
            extentReports.attachReporter(sparkReporter);
            
            // Set system information
            extentReports.setSystemInfo("OS", System.getProperty("os.name"));
            extentReports.setSystemInfo("OS Version", System.getProperty("os.version"));
            extentReports.setSystemInfo("Java Version", System.getProperty("java.version"));
            extentReports.setSystemInfo("Browser", ConfigReader.getBrowser());
            extentReports.setSystemInfo("Environment", ConfigReader.getBaseUrl());
            extentReports.setSystemInfo("User", System.getProperty("user.name"));
            extentReports.setSystemInfo("Build", "1.0.0");

            logger.info("Extent Reports initialized at: {}", reportPath);
        }
    }

    /**
     * Create test in report
     * @param testName Test name
     * @param description Test description
     * @return ExtentTest instance
     */
    public static ExtentTest createTest(String testName, String description) {
        extentTest = extentReports.createTest(testName, description);
        logger.debug("Created test in report: {}", testName);
        return extentTest;
    }

    /**
     * Create test in report with category
     * @param testName Test name
     * @param description Test description
     * @param categories Categories for the test
     * @return ExtentTest instance
     */
    public static ExtentTest createTest(String testName, String description, String... categories) {
        extentTest = extentReports.createTest(testName, description);
        if (categories != null && categories.length > 0) {
            extentTest.assignCategory(categories);
        }
        logger.debug("Created test in report: {} with categories: {}", testName, categories);
        return extentTest;
    }

    /**
     * Get current test
     * @return ExtentTest instance
     */
    public static ExtentTest getTest() {
        return extentTest;
    }

    /**
     * Log info message
     * @param message Message to log
     */
    public static void logInfo(String message) {
        if (extentTest != null) {
            extentTest.info(message);
        }
        logger.info(message);
    }

    /**
     * Log pass message
     * @param message Message to log
     */
    public static void logPass(String message) {
        if (extentTest != null) {
            extentTest.pass(message);
        }
        logger.info("PASS: {}", message);
    }

    /**
     * Log fail message
     * @param message Message to log
     */
    public static void logFail(String message) {
        if (extentTest != null) {
            extentTest.fail(message);
        }
        logger.error("FAIL: {}", message);
    }

    /**
     * Log error message
     * @param message Message to log
     */
    public static void logError(String message) {
        if (extentTest != null) {
            extentTest.fail(message);
        }
        logger.error("ERROR: {}", message);
    }

    /**
     * Log warning message
     * @param message Message to log
     */
    public static void logWarning(String message) {
        if (extentTest != null) {
            extentTest.warning(message);
        }
        logger.warn("WARNING: {}", message);
    }

    /**
     * Log skip message
     * @param message Message to log
     */
    public static void logSkip(String message) {
        if (extentTest != null) {
            extentTest.skip(message);
        }
        logger.warn("SKIP: {}", message);
    }

    /**
     * Log exception with details
     * @param exception Exception to log
     */
    public static void logException(Exception exception) {
        if (extentTest != null && exception != null) {
            extentTest.fail(exception);
        }
        logger.error("Exception occurred", exception);
    }

    /**
     * Add screenshot to report
     * @param screenshotPath Path to screenshot
     * @param title Screenshot title
     */
    public static void addScreenshot(String screenshotPath, String title) {
        if (extentTest != null && screenshotPath != null && !screenshotPath.isEmpty()) {
            try {
                // Convert absolute path to relative path for HTML report compatibility
                String relativePath = screenshotPath;
                String projectDir = System.getProperty("user.dir");
                
                // Convert to relative path if absolute path is provided
                if (screenshotPath.startsWith(projectDir)) {
                    relativePath = screenshotPath.substring(projectDir.length() + 1);
                }
                
                // Ensure forward slashes for HTML compatibility
                relativePath = relativePath.replace("\\", "/");
                
                // Verify file exists before adding
                File screenshotFile = new File(screenshotPath);
                if (!screenshotFile.exists()) {
                    logger.error("Screenshot file does not exist: {}", screenshotPath);
                    extentTest.fail(title + " - Screenshot not found: " + relativePath);
                    return;
                }
                
                if (screenshotFile.length() == 0) {
                    logger.error("Screenshot file is empty: {}", screenshotPath);
                    extentTest.fail(title + " - Screenshot is empty: " + relativePath);
                    return;
                }
                
                extentTest.addScreenCaptureFromPath(relativePath, title);
                logger.debug("Screenshot added to report: {}", relativePath);
            } catch (Exception e) {
                logger.error("Failed to add screenshot to report: {}", e.getMessage());
                extentTest.fail(title + " - Failed to attach screenshot: " + e.getMessage());
            }
        }
    }

    /**
     * Add screenshot to report
     * @param screenshotPath Path to screenshot
     */
    public static void addScreenshot(String screenshotPath) {
        addScreenshot(screenshotPath, "Screenshot");
    }

    /**
     * Add screenshot from base64 string
     * @param base64Screenshot Base64 encoded screenshot
     * @param title Screenshot title
     */
    public static void addScreenshotFromBase64(String base64Screenshot, String title) {
        if (extentTest != null && base64Screenshot != null && !base64Screenshot.isEmpty()) {
            try {
                extentTest.addScreenCaptureFromBase64String(base64Screenshot, title);
                logger.debug("Base64 screenshot added to report");
            } catch (Exception e) {
                logger.error("Failed to add base64 screenshot to report: {}", e.getMessage());
            }
        }
    }

    /**
     * Assign category to current test
     * @param categories Categories to assign
     */
    public static void assignCategory(String... categories) {
        if (extentTest != null && categories != null) {
            extentTest.assignCategory(categories);
        }
    }

    /**
     * Assign author to current test
     * @param authors Authors to assign
     */
    public static void assignAuthor(String... authors) {
        if (extentTest != null && authors != null) {
            extentTest.assignAuthor(authors);
        }
    }

    /**
     * Assign device to current test
     * @param devices Devices to assign
     */
    public static void assignDevice(String... devices) {
        if (extentTest != null && devices != null) {
            extentTest.assignDevice(devices);
        }
    }

    /**
     * Flush reports (write to file)
     */
    public static void flushReports() {
        if (extentReports != null) {
            extentReports.flush();
            logger.info("Extent Reports flushed successfully");
        }
    }

    /**
     * Remove test from report
     */
    public static void removeTest() {
        extentTest = null;
    }

    /**
     * Close reports
     */
    public static void closeReports() {
        if (extentReports != null) {
            flushReports();
            // Don't set extentReports to null here - it will be called by generateReport()
            // Just clear the current test reference
            extentTest = null;
            logger.info("Extent Reports closed");
        }
    }

    /**
     * TestNG Reporter method - generates report
     * @param xmlSuites XML suites
     * @param suites Suites
     * @param outputDirectory Output directory
     */
    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        logger.info("Generating TestNG report via IReporter");
        
        // Initialize reports if not already initialized
        initializeReports();
        
        // Process suite results
        for (ISuite suite : suites) {
            String suiteName = suite.getName();
            ExtentReportManager.logInfo("Processing suite: " + suiteName);
            
            Map<String, ISuiteResult> suiteResults = suite.getResults();
            for (Map.Entry<String, ISuiteResult> result : suiteResults.entrySet()) {
                ITestContext testContext = result.getValue().getTestContext();
                
                // Log suite results
                ExtentReportManager.logInfo("Test Context: " + testContext.getName());
                ExtentReportManager.logInfo("Passed: " + testContext.getPassedTests().size());
                ExtentReportManager.logInfo("Failed: " + testContext.getFailedTests().size());
                ExtentReportManager.logInfo("Skipped: " + testContext.getSkippedTests().size());
            }
        }
        
        // Flush reports
        flushReports();
    }
}

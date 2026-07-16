# Reporting Strategy

## Overview
This document defines the reporting standards and strategies for the automation framework. Comprehensive reporting is essential for tracking test execution, analyzing failures, and sharing results with stakeholders.

## Reporting Tools

### Extent Reports
- **Primary reporting tool** for test execution
- Generates HTML reports with rich formatting
- Includes screenshots, logs, and test steps
- Supports multiple environments and browsers

### Email Reports
- Automated email notifications after test execution
- Zip file containing HTML report, screenshots, and logs
- Configurable recipients and content

### Console Output
- Real-time test execution logs
- SLF4J logging with Log4j2 backend
- Color-coded log levels

## Extent Reports Configuration

### Report Structure
```
test-output/
├── extent-reports/
│   └── ExtentReport.html          # Main HTML report
├── screenshots/
│   └── screenshot_*.png           # Failure screenshots
├── test-report-*.zip              # Email attachment
└── logs/
    └── automation.log             # Application logs
```

### Report Features
- **Test Summary**: Pass/Fail/Skip counts
- **Test Details**: Individual test results with steps
- **Screenshots**: Embedded on failure
- **Logs**: Test execution logs
- **Duration**: Test execution time
- **Environment**: Browser, OS, environment details

### Configuration
```java
// ExtentReportManager.java
public class ExtentReportManager {
    private static ExtentReports extent;
    private static ExtentTest test;
    
    public static void initializeReports() {
        extent = new ExtentReports();
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(REPORT_PATH);
        htmlReporter.config().setDocumentTitle("Automation Test Report");
        htmlReporter.config().setReportName("UI Automation Results");
        extent.attachReporter(htmlReporter);
    }
    
    public static void createTest(String testName, String description) {
        test = extent.createTest(testName, description);
    }
}
```

## Report Content

### Test Information
```java
// Logged automatically for each test
- Test name
- Test description
- Start time
- End time
- Duration
- Status (Pass/Fail/Skip)
- Environment details
```

### Test Steps
```java
// Available log levels
test.info("Information message");
test.pass("Test passed");
test.fail("Test failed");
test.skip("Test skipped");
test.warning("Warning message");
test.error("Error message");
```

### Screenshots
```java
// Screenshots captured on failure
// Stored as base64 in report (no file path issues)
String base64Screenshot = captureScreenshot();
test.addScreenCaptureFromBase64(base64Screenshot, "Failure Screenshot");
```

### Logs
```java
// Application logs
logger.info("Test step information");
logger.error("Error occurred", exception);
logger.warn("Warning message");
```

## Email Reporting

### Email Configuration
```properties
# config.properties
email.enabled=false
email.smtp.host=smtp.gmail.com
email.smtp.port=587
email.username=your-email@gmail.com
email.password=your-app-password
email.from=your-email@gmail.com
email.to=recipient@example.com
email.subject=Automation Test Report
email.body=Please find the attached automation test report.
```

### Email Trigger
```java
// Sent automatically after test suite execution
@AfterSuite
public void tearDownSuite() {
    EmailUtil.sendTestReport();
}
```

### Email Content
- **Subject**: Automation Test Report - [Date]
- **Body**: Summary of test execution
- **Attachment**: test-report-YYYYMMDD_HHMMSS.zip
  - ExtentReport.html
  - Screenshots (max 10 most recent)
  - Test logs

## Report Generation

### Automatic Generation
```java
// Reports generated automatically during test execution
@BeforeMethod
public void setUp(Method method) {
    ExtentReportManager.initializeReports();
    ExtentReportManager.createTest(methodName, description);
}

@AfterMethod
public void tearDown(ITestResult result) {
    // Capture screenshot on failure
    if (result.getStatus() == ITestResult.FAILURE) {
        String screenshot = captureScreenshot();
        ExtentReportManager.addScreenshotFromBase64(screenshot, "Failure");
    }
    
    // Log test status
    ExtentReportManager.logPass/LogFail/LogSkip();
    
    // Flush reports
    ExtentReportManager.flushReports();
}
```

### Manual Generation
```java
// Generate report on demand
ExtentReportManager.flushReports();
String reportPath = ExtentReportManager.getReportPath();
```

## Report Analysis

### Key Metrics
- **Pass Rate**: Percentage of passed tests
- **Failure Rate**: Percentage of failed tests
- **Execution Time**: Total and per-test duration
- **Flaky Tests**: Tests with inconsistent results
- **Coverage**: Feature coverage

### Report Sections
1. **Executive Summary**: High-level overview
2. **Test Results**: Detailed test results
3. **Failure Analysis**: Failed tests with screenshots
4. **Environment Details**: Browser, OS, version info
5. **Timeline**: Test execution timeline

## Screenshot Management

### Capture Strategy
```java
// Screenshots captured on:
1. Test failure (automatic)
2. Manual trigger (test step)
3. Before/after critical actions
```

### Storage Strategy
```java
// Base64 encoding (no file path issues)
String base64 = captureScreenshot();
test.addScreenCaptureFromBase64(base64, "Screenshot Name");

// Limited to 10 most recent screenshots in zip
// Prevents large zip files
```

### Naming Convention
```
screenshot_YYYYMMDD_HHMMSS_testName_status.png
Example: screenshot_20260101_120000_loginSuccess_pass.png
```

## Log Management

### Log Levels
```java
// ERROR - Application errors
logger.error("Failed to initialize WebDriver", e);

// WARN - Recoverable issues
logger.warn("Element not found, retrying...");

// INFO - Test progress
logger.info("Starting test: {}", testName);

// DEBUG - Detailed debugging
logger.debug("Waiting for element: {}", locator);
```

### Log Configuration
```xml
<!-- log4j2.xml -->
<Configuration>
    <Appenders>
        <Console>
            <PatternLayout pattern="%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n"/>
        </Console>
        <File>
            <PatternLayout pattern="%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n"/>
        </File>
    </Appenders>
</Configuration>
```

### Log Files
```
logs/
├── automation.log              # Current log
├── automation.log.1            # Archived logs
└── automation.log.2
```

## Report Customization

### Custom Themes
```java
// ExtentReports theme configuration
htmlReporter.config().setTheme(Theme.STANDARD);
// Available: STANDARD, DARK
```

### Custom CSS
```java
// Add custom styling
htmlReporter.setCSS(".test-name { font-weight: bold; }");
```

### Custom Filters
```java
// Filter tests by status
extent.filter().statusFilter().statuses(Status.PASS).apply();
```

## Report Distribution

### Email Distribution
```java
// Automatic email after suite
@AfterSuite
public void sendReport() {
    EmailUtil.sendTestReport();
}

// Manual email trigger
EmailUtil.sendTestReport();
```

### Report Sharing
- **Email**: Automated with zip attachment
- **CI/CD**: Published to Jenkins/Artifactory
- **Dashboard**: Integrated with monitoring tools
- **Local**: Available in test-output folder

## Report Retention

### Retention Policy
```java
// Keep reports for 30 days
// Delete older reports automatically
// Keep last 10 test runs
```

### Cleanup Strategy
```java
// Delete old reports
File reportsDir = new File("test-output");
File[] oldReports = reportsDir.listFiles();
// Delete files older than 30 days
```

## Best Practices

### Report Content
✓ Include meaningful test descriptions
✓ Add screenshots for failures
✓ Log all test steps
✓ Include environment details
✓ Show execution time

✓ Don't clutter reports with unnecessary logs
✓ Don't include sensitive data (passwords, tokens)
✓ Don't create huge screenshots (resize if needed)

### Performance
✓ Limit screenshots to 10 per run
✓ Compress zip files
✓ Use base64 for screenshots (no file path issues)
✓ Flush reports after each test

### Accessibility
✓ Use clear test names
✓ Add descriptive assertions
✓ Include failure reasons
✓ Provide context for failures

## Troubleshooting

### Report Not Generated
1. Check file permissions
2. Verify report path exists
3. Check disk space
4. Verify ExtentReports version

### Screenshots Not Appearing
1. Check screenshot capture method
2. Verify base64 encoding
3. Check report file size
4. Verify browser permissions

### Email Not Sent
1. Check email configuration
2. Verify SMTP settings
3. Check firewall/antivirus
4. Verify credentials

## Report Examples

### Sample Report Structure
```
Automation Test Report
├── Summary
│   ├── Total Tests: 10
│   ├── Passed: 8
│   ├── Failed: 1
│   └── Skipped: 1
├── Environment
│   ├── Browser: Chrome 149
│   ├── OS: Windows 10
│   └── Environment: QA
├── Test Results
│   ├── testSuccessfulLogin (PASS)
│   ├── testInvalidLogin (FAIL)
│   └── testContactUsForm (PASS)
└── Timeline
    ├── Start: 2026-01-01 10:00:00
    └── End: 2026-01-01 10:05:30
```

### Sample Email
```
Subject: Automation Test Report - 2026-01-01

Body:
Test execution completed.
Total Tests: 10
Passed: 8
Failed: 1
Skipped: 1

Please find the detailed report attached.

Automation Team
```

## Integration

### CI/CD Integration
```yaml
# Jenkins pipeline
post {
    always {
        publishHTML([
            reportDir: 'test-output/extent-reports',
            reportFiles: 'ExtentReport.html',
            reportName: 'Automation Report'
        ])
        archiveArtifacts artifacts: 'test-output/test-report-*.zip', fingerprint: true
    }
}
```

### Slack/Teams Notification
```java
// Send notification with report link
SlackUtil.sendNotification("Test execution completed", reportUrl);
```

## Summary

### Key Features
✓ Automatic report generation
✓ Rich HTML reports with ExtentReports
✓ Screenshot capture on failure
✓ Email distribution with zip attachment
✓ Real-time logging
✓ Environment details included
✓ Test timeline and metrics

### Report Types
1. **HTML Report**: Detailed test results
2. **Email Report**: Summary with attachment
3. **Console Report**: Real-time execution logs
4. **Log Files**: Detailed application logs
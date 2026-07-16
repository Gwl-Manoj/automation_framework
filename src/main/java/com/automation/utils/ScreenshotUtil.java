package com.automation.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Screenshot Utility
 * Captures and manages screenshots for test reporting
 */
public class ScreenshotUtil {
    private static final Logger logger = LoggerFactory.getLogger(ScreenshotUtil.class);
    private static final String SCREENSHOT_PATH = ConfigReader.getProperty("screenshot.path", "test-output/screenshots");

    /**
     * Capture screenshot and save to file
     * @param driver WebDriver instance
     * @param screenshotName Name for the screenshot
     * @return Relative path of the screenshot (using forward slashes for HTML compatibility)
     */
    public static String captureScreenshot(WebDriver driver, String screenshotName) {
        try {
            // Create screenshot directory if it doesn't exist
            File screenshotDir = new File(SCREENSHOT_PATH);
            if (!screenshotDir.exists()) {
                screenshotDir.mkdirs();
            }

            // Generate timestamp for unique filename
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS").format(new Date());
            String fileName = screenshotName + "_" + timestamp + ".png";
            
            // Use forward slashes for cross-platform compatibility (especially for HTML reports)
            String filePath = SCREENSHOT_PATH.replace("\\", "/") + "/" + fileName;

            // Capture screenshot
            TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
            File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
            File destinationFile = new File(filePath);

            // Copy file to destination
            FileUtils.copyFile(sourceFile, destinationFile);

            // Verify file was created successfully
            if (!destinationFile.exists() || destinationFile.length() == 0) {
                logger.error("Screenshot file was not created or is empty: {}", filePath);
                return null;
            }

            logger.info("Screenshot captured successfully: {} (size: {} bytes)", filePath, destinationFile.length());
            return filePath;

        } catch (IOException e) {
            logger.error("Failed to capture screenshot: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Capture screenshot with TestNG test method name
     * @param driver WebDriver instance
     * @param testMethodName Test method name
     * @return Relative path of the screenshot
     */
    public static String captureScreenshotForTest(WebDriver driver, String testMethodName) {
        return captureScreenshot(driver, testMethodName);
    }

    /**
     * Capture screenshot on test failure
     * @param driver WebDriver instance
     * @param testName Test name
     * @return Relative path of the screenshot
     */
    public static String captureFailureScreenshot(WebDriver driver, String testName) {
        String failureScreenshotName = "FAIL_" + testName;
        return captureScreenshot(driver, failureScreenshotName);
    }

    /**
     * Get screenshot as byte array (for embedding in reports)
     * @param driver WebDriver instance
     * @return Screenshot as byte array
     */
    public static byte[] getScreenshotAsBytes(WebDriver driver) {
        try {
            TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
            return takesScreenshot.getScreenshotAs(OutputType.BYTES);
        } catch (Exception e) {
            logger.error("Failed to capture screenshot as bytes: {}", e.getMessage());
            return new byte[0];
        }
    }

    /**
     * Get screenshot as base64 string (for embedding in HTML reports)
     * @param driver WebDriver instance
     * @return Screenshot as base64 string
     */
    public static String getScreenshotAsBase64(WebDriver driver) {
        try {
            TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
            return takesScreenshot.getScreenshotAs(OutputType.BASE64);
        } catch (Exception e) {
            logger.error("Failed to capture screenshot as base64: {}", e.getMessage());
            return "";
        }
    }

    /**
     * Clean old screenshots (older than specified days)
     * @param daysOld Delete screenshots older than this many days
     */
    public static void cleanOldScreenshots(int daysOld) {
        try {
            File screenshotDir = new File(SCREENSHOT_PATH);
            if (!screenshotDir.exists() || !screenshotDir.isDirectory()) {
                return;
            }

            long cutoffTime = System.currentTimeMillis() - (daysOld * 24L * 60 * 60 * 1000);
            File[] files = screenshotDir.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.lastModified() < cutoffTime) {
                        if (file.delete()) {
                            logger.info("Deleted old screenshot: {}", file.getName());
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Failed to clean old screenshots: {}", e.getMessage());
        }
    }
}
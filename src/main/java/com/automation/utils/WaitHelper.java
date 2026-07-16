package com.automation.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.function.Function;

/**
 * Wait Helper Utility
 * Provides explicit wait methods for various conditions
 */
public class WaitHelper {
    private static final Logger logger = LoggerFactory.getLogger(WaitHelper.class);
    private static WebDriverWait webDriverWait;

    /**
     * Initialize WebDriverWait with explicit timeout
     */
    public static void initializeWait() {
        WebDriver driver = WebDriverFactory.getDriver();
        int timeout = ConfigReader.getExplicitWait();
        
        
        if (driver == null) {
            logger.error("Cannot initialize WaitHelper - WebDriver is null");
            throw new IllegalStateException("WebDriver is not initialized. Call WebDriverFactory.initializeDriver() first.");
        }
        
        webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        logger.info("WebDriverWait initialized with timeout: {} seconds", timeout);
    }

    /**
     * Ensure WebDriverWait is initialized
     */
    private static void ensureInitialized() {
        if (webDriverWait == null) {
            logger.warn("WebDriverWait not initialized. Initializing now...");
            initializeWait();
        }
    }

    /**
     * Wait for element to be visible
     * @param element WebElement to wait for
     * @return Visible WebElement
     */
    public static WebElement waitForElementVisible(WebElement element) {
        logger.debug("Waiting for element to be visible");
        ensureInitialized();
        return webDriverWait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Wait for element to be visible by locator
     * @param locator By locator
     * @return Visible WebElement
     */
    public static WebElement waitForElementVisible(By locator) {
        logger.debug("Waiting for element to be visible: {}", locator);
        ensureInitialized();
        return webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Wait for element to be clickable
     * @param element WebElement to wait for
     * @return Clickable WebElement
     */
    public static WebElement waitForElementClickable(WebElement element) {
        logger.debug("Waiting for element to be clickable");
        ensureInitialized();
        return webDriverWait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Wait for element to be clickable by locator
     * @param locator By locator
     * @return Clickable WebElement
     */
    public static WebElement waitForElementClickable(By locator) {
        logger.debug("Waiting for element to be clickable: {}", locator);
        
        if (webDriverWait == null) {
            logger.error("WebDriverWait is not initialized. Calling initializeWait()");
            initializeWait();
        }
        
        try {
            return webDriverWait.until(ExpectedConditions.elementToBeClickable(locator));
        } catch (Exception e) {
            logger.error("Element not clickable: {}", locator, e);
            throw e;
        }
    }

    /**
     * Wait for element to be present
     * @param locator By locator
     * @return Present WebElement
     */
    public static WebElement waitForElementPresent(By locator) {
        logger.debug("Waiting for element to be present: {}", locator);
        ensureInitialized();
        return webDriverWait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /**
     * Wait for element to disappear
     * @param locator By locator
     */
    public static void waitForElementToDisappear(By locator) {
        logger.debug("Waiting for element to disappear: {}", locator);
        ensureInitialized();
        webDriverWait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    /**
     * Wait for element to disappear
     * @param element WebElement to wait for
     */
    public static void waitForElementToDisappear(WebElement element) {
        logger.debug("Waiting for element to disappear");
        ensureInitialized();
        webDriverWait.until(ExpectedConditions.invisibilityOf(element));
    }

    /**
     * Wait for text to be present in element
     * @param locator By locator
     * @param text Text to wait for
     * @return true if text is present
     */
    public static boolean waitForTextPresent(By locator, String text) {
        logger.debug("Waiting for text '{}' to be present in element: {}", text, locator);
        ensureInitialized();
        return webDriverWait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    /**
     * Wait for text to be present in element
     * @param element WebElement
     * @param text Text to wait for
     * @return true if text is present
     */
    public static boolean waitForTextPresent(WebElement element, String text) {
        logger.debug("Waiting for text '{}' to be present in element", text);
        ensureInitialized();
        return webDriverWait.until(ExpectedConditions.textToBePresentInElement(element, text));
    }

    /**
     * Wait for page title to contain text
     * @param title Title text to wait for
     * @return true if title contains text
     */
    public static boolean waitForPageTitle(String title) {
        logger.debug("Waiting for page title to contain: {}", title);
        ensureInitialized();
        return webDriverWait.until(ExpectedConditions.titleContains(title));
    }

    /**
     * Wait for page URL to contain text
     * @param url URL text to wait for
     * @return true if URL contains text
     */
    public static boolean waitForUrlContains(String url) {
        logger.debug("Waiting for URL to contain: {}", url);
        ensureInitialized();
        return webDriverWait.until(ExpectedConditions.urlContains(url));
    }

    /**
     * Wait for alert to be present
     * @return Alert instance
     */
    public static Alert waitForAlert() {
        logger.debug("Waiting for alert to be present");
        ensureInitialized();
        return webDriverWait.until(ExpectedConditions.alertIsPresent());
    }

    /**
     * Wait for frame to be available and switch to it
     * @param frameLocator Frame locator
     */
    public static void waitForFrameAndSwitch(By frameLocator) {
        logger.debug("Waiting for frame and switching to it: {}", frameLocator);
        ensureInitialized();
        webDriverWait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
    }

    /**
     * Wait for frame to be available and switch to it
     * @param frameIndex Frame index
     */
    public static void waitForFrameAndSwitch(int frameIndex) {
        logger.debug("Waiting for frame and switching to it by index: {}", frameIndex);
        ensureInitialized();
        webDriverWait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
    }

    /**
     * Wait for custom condition
     * @param condition Custom condition to wait for
     * @param <T> Return type
     * @return Result of the condition
     */
    public static <T> T waitForCondition(Function<WebDriver, T> condition) {
        logger.debug("Waiting for custom condition");
        ensureInitialized();
        return webDriverWait.until(condition);
    }

    /**
     * Wait for JavaScript condition
     * @param jsCondition JavaScript condition to evaluate
     * @return true if condition is met
     */
    public static boolean waitForJavaScriptCondition(String jsCondition) {
        logger.debug("Waiting for JavaScript condition: {}", jsCondition);
        ensureInitialized();
        return webDriverWait.until((Function<WebDriver, Boolean>) driver -> 
            (Boolean) ((JavascriptExecutor) driver).executeScript("return " + jsCondition));
    }

    /**
     * Wait for specified time in milliseconds
     * @param milliseconds Time to wait in milliseconds
     */
    public static void waitFor(long milliseconds) {
        logger.debug("Waiting for {} milliseconds", milliseconds);
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Wait interrupted", e);
        }
    }

    /**
     * Wait for specified time in seconds
     * @param seconds Time to wait in seconds
     */
    public static void waitForSeconds(long seconds) {
        waitFor(seconds * 1000);
    }
}
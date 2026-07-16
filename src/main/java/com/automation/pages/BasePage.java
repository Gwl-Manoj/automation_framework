package com.automation.pages;

import com.automation.utils.ExtentReportManager;
import com.automation.utils.WaitHelper;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Base Page Class
 * Provides comprehensive utility methods for all page classes
 * All page classes should extend this base page
 */
public class BasePage {
    protected WebDriver driver;
    protected static final Logger logger = LoggerFactory.getLogger(BasePage.class);
    protected Actions actions;
    protected WaitHelper wait;

    /**
     * Constructor
     * @param driver WebDriver instance
     */
    public BasePage(WebDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("WebDriver cannot be null. Ensure WebDriverFactory.initializeDriver() is called in @BeforeMethod before creating page objects.");
        }
        this.driver = driver;
        // Don't initialize Actions here - driver might be null during test class instantiation
        // Actions will be initialized lazily when needed
        logger.debug("BasePage initialized");
    }

    // ==================== Browser Methods ====================

    /**
     * Launch browser (already initialized in BaseTest)
     */
    public void launchBrowser() {
        logger.info("Browser launched");
    }

    /**
     * Open URL
     * @param url URL to navigate to
     */
    public void openUrl(String url) {
        if (driver == null) {
            throw new IllegalStateException("WebDriver is not initialized. Call WebDriverFactory.initializeDriver() first.");
        }
        logger.info("Opening URL: {}", url);
        driver.get(url);
        ExtentReportManager.logInfo("Opened URL: " + url);
    }

    /**
     * Get current URL
     * @return Current URL
     */
    public String getCurrentUrl() {
        String url = driver.getCurrentUrl();
        logger.info("Current URL: {}", url);
        return url;
    }

    /**
     * Get page title
     * @return Page title
     */
    public String getPageTitle() {
        String title = driver.getTitle();
        logger.info("Page title: {}", title);
        return title;
    }

    /**
     * Navigate back
     */
    public void navigateBack() {
        logger.info("Navigating back");
        driver.navigate().back();
    }

    /**
     * Navigate forward
     */
    public void navigateForward() {
        logger.info("Navigating forward");
        driver.navigate().forward();
    }

    /**
     * Refresh page
     */
    public void refreshPage() {
        logger.info("Refreshing page");
        driver.navigate().refresh();
    }

    /**
     * Maximize window
     */
    public void maximizeWindow() {
        logger.info("Maximizing window");
        driver.manage().window().maximize();
    }

    /**
     * Minimize window
     */
    public void minimizeWindow() {
        logger.info("Minimizing window");
        driver.manage().window().minimize();
    }

    /**
     * Close browser (current window)
     */
    public void closeBrowser() {
        logger.info("Closing browser");
        driver.close();
    }

    /**
     * Quit browser (all windows)
     */
    public void quitBrowser() {
        logger.info("Quitting browser");
        driver.quit();
    }

    // ==================== Element Interaction Methods ====================

    /**
     * Click on element by locator
     * @param locator By locator
     */
    public void click(By locator) {
        click(locator, locator.toString());
    }

    /**
     * Click on element by locator with element name
     * @param locator By locator
     * @param elementName Name of the element for logging
     */
    public void click(By locator, String elementName) {
        logger.info("Clicking on element: {}", elementName);
        WebElement element = waitForElementClickable(locator);
        element.click();
        ExtentReportManager.logInfo("Clicked on: " + elementName);
    }

    /**
     * Click on WebElement
     * @param element WebElement to click
     */
    public void click(WebElement element) {
        logger.info("Clicking on WebElement");
        waitForElementClickable(element);
        element.click();
        ExtentReportManager.logInfo("Clicked on WebElement");
    }

    /**
     * JavaScript click by locator
     * @param locator By locator
     */
    public void jsClick(By locator) {
        logger.info("JavaScript clicking on element: {}", locator);
        WebElement element = driver.findElement(locator);
        jsClick(element);
    }

    /**
     * JavaScript click on WebElement
     * @param element WebElement to click
     */
    public void jsClick(WebElement element) {
        logger.info("JavaScript clicking on WebElement");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
        ExtentReportManager.logInfo("JavaScript clicked on element");
    }

    /**
     * Enter text in element by locator
     * @param locator By locator
     * @param text Text to enter
     */
    public void enterText(By locator, String text) {
        enterText(locator, text, locator.toString());
    }

    /**
     * Enter text in element by locator with field name
     * @param locator By locator
     * @param text Text to enter
     * @param fieldName Name of the field for logging
     */
    public void enterText(By locator, String text, String fieldName) {
        logger.info("Entering text in: {}", fieldName);
        WebElement element = waitForElementVisible(locator);
        element.clear();
        element.sendKeys(text);
        ExtentReportManager.logInfo("Entered text in: " + fieldName + " - Value: " + text);
    }

    /**
     * Enter text in WebElement
     * @param element WebElement
     * @param text Text to enter
     */
    public void enterText(WebElement element, String text) {
        logger.info("Entering text in WebElement");
        waitForElementVisible(element);
        element.clear();
        element.sendKeys(text);
        ExtentReportManager.logInfo("Entered text: " + text);
    }

    /**
     * Clear element by locator
     * @param locator By locator
     */
    public void clear(By locator) {
        logger.info("Clearing element: {}", locator);
        WebElement element = driver.findElement(locator);
        element.clear();
    }

    /**
     * Clear WebElement
     * @param element WebElement to clear
     */
    public void clear(WebElement element) {
        logger.info("Clearing WebElement");
        element.clear();
    }

    /**
     * Submit form by locator
     * @param locator By locator
     */
    public void submit(By locator) {
        logger.info("Submitting form: {}", locator);
        WebElement element = driver.findElement(locator);
        element.submit();
    }

    /**
     * Send keys to element by locator
     * @param locator By locator
     * @param key Keys to send
     */
    public void sendKeys(By locator, Keys key) {
        logger.info("Sending keys to element: {}", locator);
        WebElement element = driver.findElement(locator);
        element.sendKeys(key);
    }

    /**
     * Press Enter key by locator
     * @param locator By locator
     */
    public void pressEnter(By locator) {
        logger.info("Pressing Enter key");
        sendKeys(locator, Keys.ENTER);
    }

    /**
     * Press Tab key by locator
     * @param locator By locator
     */
    public void pressTab(By locator) {
        logger.info("Pressing Tab key");
        sendKeys(locator, Keys.TAB);
    }

    // ==================== Get Methods ====================

    /**
     * Get text from element by locator
     * @param locator By locator
     * @return Element text
     */
    public String getText(By locator) {
        return getText(locator, locator.toString());
    }

    /**
     * Get text from element by locator with element name
     * @param locator By locator
     * @param elementName Name of the element for logging
     * @return Element text
     */
    public String getText(By locator, String elementName) {
        logger.info("Getting text from: {}", elementName);
        WebElement element = waitForElementVisible(locator);
        String text = element.getText();
        ExtentReportManager.logInfo("Text from " + elementName + ": " + text);
        return text;
    }

    /**
     * Get text from WebElement
     * @param element WebElement
     * @return Element text
     */
    public String getText(WebElement element) {
        logger.info("Getting text from WebElement");
        waitForElementVisible(element);
        String text = element.getText();
        ExtentReportManager.logInfo("Text: " + text);
        return text;
    }

    /**
     * Get attribute value by locator
     * @param locator By locator
     * @param attributeName Attribute name
     * @return Attribute value
     */
    public String getAttribute(By locator, String attributeName) {
        logger.info("Getting attribute '{}' from element: {}", attributeName, locator);
        WebElement element = driver.findElement(locator);
        String value = element.getAttribute(attributeName);
        return value;
    }

    /**
     * Get attribute value from WebElement
     * @param element WebElement
     * @param attributeName Attribute name
     * @return Attribute value
     */
    public String getAttribute(WebElement element, String attributeName) {
        logger.info("Getting attribute '{}' from WebElement", attributeName);
        return element.getAttribute(attributeName);
    }

    /**
     * Get CSS value by locator
     * @param locator By locator
     * @param property CSS property name
     * @return CSS property value
     */
    public String getCssValue(By locator, String property) {
        logger.info("Getting CSS value '{}' from element: {}", property, locator);
        WebElement element = driver.findElement(locator);
        return element.getCssValue(property);
    }

    /**
     * Get tag name by locator
     * @param locator By locator
     * @return Tag name
     */
    public String getTagName(By locator) {
        logger.info("Getting tag name from element: {}", locator);
        WebElement element = driver.findElement(locator);
        return element.getTagName();
    }

    /**
     * Get element count
     * @param locator By locator
     * @return Number of elements found
     */
    public int getElementCount(By locator) {
        logger.info("Getting element count: {}", locator);
        List<WebElement> elements = driver.findElements(locator);
        return elements.size();
    }

    /**
     * Get all elements by locator
     * @param locator By locator
     * @return List of WebElements
     */
    public List<WebElement> getElements(By locator) {
        logger.info("Getting all elements: {}", locator);
        return driver.findElements(locator);
    }

    /**
     * Get single element by locator
     * @param locator By locator
     * @return WebElement
     */
    public WebElement getElement(By locator) {
        logger.info("Getting element: {}", locator);
        return driver.findElement(locator);
    }

    // ==================== Validation Methods ====================

    /**
     * Check if element is displayed by locator
     * @param locator By locator
     * @return true if displayed
     */
    public boolean isDisplayed(By locator) {
        return isDisplayed(locator, locator.toString());
    }

    /**
     * Check if element is displayed by locator with element name
     * @param locator By locator
     * @param elementName Name of the element for logging
     * @return true if displayed
     */
    public boolean isDisplayed(By locator, String elementName) {
        try {
            logger.info("Checking if element is displayed: {}", elementName);
            WebElement element = driver.findElement(locator);
            return element.isDisplayed();
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Check if element is enabled by locator
     * @param locator By locator
     * @return true if enabled
     */
    public boolean isEnabled(By locator) {
        try {
            logger.info("Checking if element is enabled: {}", locator);
            WebElement element = driver.findElement(locator);
            return element.isEnabled();
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Check if element is selected by locator
     * @param locator By locator
     * @return true if selected
     */
    public boolean isSelected(By locator) {
        try {
            logger.info("Checking if element is selected: {}", locator);
            WebElement element = driver.findElement(locator);
            return element.isSelected();
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Check if element is clickable by locator
     * @param locator By locator
     * @return true if clickable
     */
    public boolean isClickable(By locator) {
        try {
            logger.info("Checking if element is clickable: {}", locator);
            waitForElementClickable(locator);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Verify text matches expected text
     * @param locator By locator
     * @param expectedText Expected text
     * @return true if text matches
     */
    public boolean verifyText(By locator, String expectedText) {
        logger.info("Verifying text '{}' for element: {}", expectedText, locator);
        String actualText = getText(locator);
        boolean matches = actualText.equals(expectedText);
        if (matches) {
            ExtentReportManager.logPass("Text verified: " + expectedText);
        } else {
            ExtentReportManager.logFail("Text mismatch. Expected: " + expectedText + ", Actual: " + actualText);
        }
        return matches;
    }

    /**
     * Verify attribute value
     * @param locator By locator
     * @param attribute Attribute name
     * @param expectedValue Expected value
     * @return true if attribute matches
     */
    public boolean verifyAttribute(By locator, String attribute, String expectedValue) {
        logger.info("Verifying attribute '{}' = '{}' for element: {}", attribute, expectedValue, locator);
        String actualValue = getAttribute(locator, attribute);
        boolean matches = actualValue.equals(expectedValue);
        if (matches) {
            ExtentReportManager.logPass("Attribute verified: " + attribute + " = " + expectedValue);
        } else {
            ExtentReportManager.logFail("Attribute mismatch. Expected: " + expectedValue + ", Actual: " + actualValue);
        }
        return matches;
    }

    // ==================== Wait Methods ====================

    /**
     * Wait for element to be visible
     * @param locator By locator
     * @return Visible WebElement
     */
    public WebElement waitForElementVisible(By locator) {
        logger.debug("Waiting for element to be visible: {}", locator);
        return WaitHelper.waitForElementVisible(locator);
    }

    /**
     * Wait for element to be clickable
     * @param locator By locator
     * @return Clickable WebElement
     */
    public WebElement waitForElementClickable(By locator) {
        logger.debug("Waiting for element to be clickable: {}", locator);
        return WaitHelper.waitForElementClickable(locator);
    }

    /**
     * Wait for element to be present
     * @param locator By locator
     * @return Present WebElement
     */
    public WebElement waitForElementPresent(By locator) {
        logger.debug("Waiting for element to be present: {}", locator);
        return WaitHelper.waitForElementPresent(locator);
    }

    /**
     * Wait for element to be invisible
     * @param locator By locator
     */
    public void waitForElementInvisible(By locator) {
        logger.debug("Waiting for element to be invisible: {}", locator);
        WaitHelper.waitForElementToDisappear(locator);
    }

    /**
     * Wait for text to be present in element
     * @param locator By locator
     * @param text Text to wait for
     * @return true if text is present
     */
    public boolean waitForTextToBePresent(By locator, String text) {
        logger.debug("Waiting for text '{}' to be present in element: {}", text, locator);
        return WaitHelper.waitForTextPresent(locator, text);
    }

    /**
     * Wait for page to load completely
     */
    public void waitForPageLoad() {
        logger.info("Waiting for page to load");
        WaitHelper.waitForJavaScriptCondition("document.readyState == 'complete'");
        ExtentReportManager.logInfo("Page loaded completely");
    }

    /**
     * Wait for AJAX to complete
     */
    public void waitForAjaxToComplete() {
        logger.info("Waiting for AJAX to complete");
        WaitHelper.waitForJavaScriptCondition("jQuery.active == 0");
        ExtentReportManager.logInfo("AJAX completed");
    }

    /**
     * Wait until URL contains specific text
     * @param urlPart URL part to wait for
     */
    public void waitUntilUrlContains(String urlPart) {
        logger.info("Waiting until URL contains: {}", urlPart);
        WaitHelper.waitForUrlContains(urlPart);
    }

    /**
     * Wait until title contains specific text
     * @param title Title part to wait for
     */
    public void waitUntilTitleContains(String title) {
        logger.info("Waiting until title contains: {}", title);
        WaitHelper.waitForPageTitle(title);
    }

    // ==================== Dropdown Methods ====================

    /**
     * Select dropdown option by visible text
     * @param locator By locator
     * @param text Visible text to select
     */
    public void selectByVisibleText(By locator, String text) {
        logger.info("Selecting dropdown option by visible text: {}", text);
        WebElement element = driver.findElement(locator);
        Select select = new Select(element);
        select.selectByVisibleText(text);
        ExtentReportManager.logInfo("Selected: " + text);
    }

    /**
     * Select dropdown option by value
     * @param locator By locator
     * @param value Option value
     */
    public void selectByValue(By locator, String value) {
        logger.info("Selecting dropdown option by value: {}", value);
        WebElement element = driver.findElement(locator);
        Select select = new Select(element);
        select.selectByValue(value);
        ExtentReportManager.logInfo("Selected value: " + value);
    }

    /**
     * Select dropdown option by index
     * @param locator By locator
     * @param index Option index
     */
    public void selectByIndex(By locator, int index) {
        logger.info("Selecting dropdown option by index: {}", index);
        WebElement element = driver.findElement(locator);
        Select select = new Select(element);
        select.selectByIndex(index);
        ExtentReportManager.logInfo("Selected index: " + index);
    }

    /**
     * Get selected option from dropdown
     * @param locator By locator
     * @return Selected option text
     */
    public String getSelectedOption(By locator) {
        logger.info("Getting selected option from dropdown: {}", locator);
        WebElement element = driver.findElement(locator);
        Select select = new Select(element);
        String selectedText = select.getFirstSelectedOption().getText();
        return selectedText;
    }

    /**
     * Get all dropdown options
     * @param locator By locator
     * @return List of all option texts
     */
    public List<String> getAllDropdownOptions(By locator) {
        logger.info("Getting all dropdown options: {}", locator);
        WebElement element = driver.findElement(locator);
        Select select = new Select(element);
        List<WebElement> options = select.getOptions();
        List<String> optionTexts = new ArrayList<>();
        for (WebElement option : options) {
            optionTexts.add(option.getText());
        }
        return optionTexts;
    }

    /**
     * Deselect all options from multi-select dropdown
     * @param locator By locator
     */
    public void deselectAll(By locator) {
        logger.info("Deselecting all options from dropdown: {}", locator);
        WebElement element = driver.findElement(locator);
        Select select = new Select(element);
        select.deselectAll();
    }

    // ==================== Mouse Actions ====================

    /**
     * Get Actions instance (lazy initialization)
     * @return Actions instance
     */
    private Actions getActions() {
        if (actions == null) {
            actions = new Actions(driver);
        }
        return actions;
    }

    /**
     * Hover over element
     * @param locator By locator
     */
    public void hover(By locator) {
        logger.info("Hovering over element: {}", locator);
        WebElement element = driver.findElement(locator);
        getActions().moveToElement(element).perform();
        ExtentReportManager.logInfo("Hovered over element");
    }

    /**
     * Double click on element
     * @param locator By locator
     */
    public void doubleClick(By locator) {
        logger.info("Double clicking on element: {}", locator);
        WebElement element = driver.findElement(locator);
        getActions().doubleClick(element).perform();
        ExtentReportManager.logInfo("Double clicked on element");
    }

    /**
     * Right click on element
     * @param locator By locator
     */
    public void rightClick(By locator) {
        logger.info("Right clicking on element: {}", locator);
        WebElement element = driver.findElement(locator);
        getActions().contextClick(element).perform();
        ExtentReportManager.logInfo("Right clicked on element");
    }

    /**
     * Drag and drop from source to target
     * @param sourceLocator Source element locator
     * @param targetLocator Target element locator
     */
    public void dragAndDrop(By sourceLocator, By targetLocator) {
        logger.info("Dragging and dropping from {} to {}", sourceLocator, targetLocator);
        WebElement source = driver.findElement(sourceLocator);
        WebElement target = driver.findElement(targetLocator);
        getActions().dragAndDrop(source, target).perform();
        ExtentReportManager.logInfo("Drag and drop completed");
    }

    /**
     * Drag and drop from source WebElement to target WebElement
     * @param source Source WebElement
     * @param target Target WebElement
     */
    public void dragAndDrop(WebElement source, WebElement target) {
        logger.info("Dragging and dropping WebElements");
        getActions().dragAndDrop(source, target).perform();
        ExtentReportManager.logInfo("Drag and drop completed");
    }

    /**
     * Move to element
     * @param locator By locator
     */
    public void moveToElement(By locator) {
        logger.info("Moving to element: {}", locator);
        WebElement element = driver.findElement(locator);
        getActions().moveToElement(element).perform();
    }

    // ==================== Keyboard Actions ====================

    /**
     * Press Enter key
     */
    public void pressEnter() {
        logger.info("Pressing Enter key");
        getActions().sendKeys(Keys.ENTER).perform();
    }

    /**
     * Press Escape key
     */
    public void pressEscape() {
        logger.info("Pressing Escape key");
        getActions().sendKeys(Keys.ESCAPE).perform();
    }

    /**
     * Press Tab key
     */
    public void pressTab() {
        logger.info("Pressing Tab key");
        getActions().sendKeys(Keys.TAB).perform();
    }

    /**
     * Key down
     * @param key Key to press
     */
    public void keyDown(Keys key) {
        logger.info("Key down: {}", key);
        getActions().keyDown(key).perform();
    }

    /**
     * Key up
     * @param key Key to release
     */
    public void keyUp(Keys key) {
        logger.info("Key up: {}", key);
        getActions().keyUp(key).perform();
    }

    /**
     * Copy text (Ctrl+C)
     */
    public void copyText() {
        logger.info("Copying text");
        getActions().keyDown(Keys.CONTROL).sendKeys("c").keyUp(Keys.CONTROL).perform();
    }

    /**
     * Paste text (Ctrl+V)
     */
    public void pasteText() {
        logger.info("Pasting text");
        getActions().keyDown(Keys.CONTROL).sendKeys("v").keyUp(Keys.CONTROL).perform();
    }

    // ==================== Scroll Methods ====================

    /**
     * Scroll to element
     * @param locator By locator
     */
    public void scrollToElement(By locator) {
        logger.info("Scrolling to element: {}", locator);
        WebElement element = driver.findElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    /**
     * Scroll to top of page
     */
    public void scrollToTop() {
        logger.info("Scrolling to top");
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
    }

    /**
     * Scroll to bottom of page
     */
    public void scrollToBottom() {
        logger.info("Scrolling to bottom");
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    /**
     * Scroll by pixels
     * @param x X coordinate
     * @param y Y coordinate
     */
    public void scrollBy(int x, int y) {
        logger.info("Scrolling by x: {}, y: {}", x, y);
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(" + x + ", " + y + ");");
    }

    /**
     * Scroll element into view
     * @param locator By locator
     */
    public void scrollIntoView(By locator) {
        logger.info("Scrolling element into view: {}", locator);
        WebElement element = driver.findElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
    }

    // ==================== JavaScript Methods ====================

    /**
     * Execute JavaScript
     * @param script JavaScript script to execute
     * @return Result of script execution
     */
    public Object executeJavaScript(String script) {
        logger.info("Executing JavaScript: {}", script);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return js.executeScript(script);
    }

    /**
     * JavaScript enter Text in element
     * @param locator By locator
     * @param text Text to enter
     */
    public void jsenterText(By locator, String text) {
        logger.info("JavaScript entering text in element: {}", locator);
        WebElement element = driver.findElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + text + "';", element);
    }

    /**
     * Highlight element
     * @param locator By locator
     */
    public void highlightElement(By locator) {
        logger.info("Highlighting element: {}", locator);
        WebElement element = driver.findElement(locator);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].style.border='3px solid red'", element);
    }

    /**
     * Remove attribute from element
     * @param locator By locator
     * @param attribute Attribute to remove
     */
    public void removeAttribute(By locator, String attribute) {
        logger.info("Removing attribute '{}' from element: {}", attribute, locator);
        WebElement element = driver.findElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('" + attribute + "');", element);
    }

    // ==================== Alert Methods ====================

    /**
     * Accept alert
     */
    public void acceptAlert() {
        logger.info("Accepting alert");
        Alert alert = waitForAlert();
        alert.accept();
        ExtentReportManager.logInfo("Alert accepted");
    }

    /**
     * Dismiss alert
     */
    public void dismissAlert() {
        logger.info("Dismissing alert");
        Alert alert = waitForAlert();
        alert.dismiss();
        ExtentReportManager.logInfo("Alert dismissed");
    }

    /**
     * Get alert text
     * @return Alert text
     */
    public String getAlertText() {
        logger.info("Getting alert text");
        Alert alert = waitForAlert();
        String text = alert.getText();
        ExtentReportManager.logInfo("Alert text: " + text);
        return text;
    }

    /**
     * Send keys to alert
     * @param text Text to send
     */
    public void sendKeysToAlert(String text) {
        logger.info("Sending keys to alert: {}", text);
        Alert alert = waitForAlert();
        alert.sendKeys(text);
    }

    /**
     * Check if alert is present
     * @return true if alert is present
     */
    public boolean isAlertPresent() {
        try {
            logger.info("Checking if alert is present");
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    /**
     * Wait for alert to be present
     * @return Alert instance
     */
    public Alert waitForAlert() {
        logger.debug("Waiting for alert to be present");
        return WaitHelper.waitForAlert();
    }

    // ==================== Window & Tab Methods ====================

    /**
     * Switch to window by index
     * @param index Window index
     */
    public void switchToWindow(int index) {
        logger.info("Switching to window index: {}", index);
        Set<String> windows = driver.getWindowHandles();
        int i = 0;
        for (String window : windows) {
            if (i == index) {
                driver.switchTo().window(window);
                break;
            }
            i++;
        }
    }

    /**
     * Switch to window by title
     * @param title Window title
     */
    public void switchToWindow(String title) {
        logger.info("Switching to window with title: {}", title);
        Set<String> windows = driver.getWindowHandles();
        for (String window : windows) {
            driver.switchTo().window(window);
            if (driver.getTitle().equals(title)) {
                break;
            }
        }
    }

    /**
     * Switch to new window
     */
    public void switchToNewWindow() {
        logger.info("Switching to new window");
        Set<String> windows = driver.getWindowHandles();
        List<String> windowList = new ArrayList<>(windows);
        driver.switchTo().window(windowList.get(windowList.size() - 1));
    }

    /**
     * Close current window
     */
    public void closeCurrentWindow() {
        logger.info("Closing current window");
        driver.close();
    }

    /**
     * Switch to parent window
     */
    public void switchToParentWindow() {
        logger.info("Switching to parent window");
        Set<String> windows = driver.getWindowHandles();
        List<String> windowList = new ArrayList<>(windows);
        driver.switchTo().window(windowList.get(0));
    }

    /**
     * Get window handles count
     * @return Number of windows
     */
    public int getWindowHandlesCount() {
        logger.info("Getting window handles count");
        return driver.getWindowHandles().size();
    }

    // ==================== Frame Methods ====================

    /**
     * Switch to frame by locator
     * @param locator By locator
     */
    public void switchToFrame(By locator) {
        logger.info("Switching to frame: {}", locator);
        WebElement element = driver.findElement(locator);
        driver.switchTo().frame(element);
    }

    /**
     * Switch to frame by index
     * @param index Frame index
     */
    public void switchToFrame(int index) {
        logger.info("Switching to frame index: {}", index);
        driver.switchTo().frame(index);
    }

    /**
     * Switch to frame by name or ID
     * @param nameOrId Frame name or ID
     */
    public void switchToFrame(String nameOrId) {
        logger.info("Switching to frame: {}", nameOrId);
        driver.switchTo().frame(nameOrId);
    }

    /**
     * Switch to default content
     */
    public void switchToDefaultContent() {
        logger.info("Switching to default content");
        driver.switchTo().defaultContent();
    }

    /**
     * Switch to parent frame
     */
    public void switchToParentFrame() {
        logger.info("Switching to parent frame");
        driver.switchTo().parentFrame();
    }

    // ==================== Screenshot Methods ====================

    /**
     * Take screenshot
     * @param fileName Screenshot file name
     * @return Screenshot file path
     */
    public String takeScreenshot(String fileName) {
        logger.info("Taking screenshot: {}", fileName);
        return com.automation.utils.ScreenshotUtil.captureScreenshot(driver, fileName);
    }

    /**
     * Take element screenshot
     * @param locator Element locator
     * @param fileName Screenshot file name
     * @return Screenshot file path
     */
    public String takeElementScreenshot(By locator, String fileName) {
        logger.info("Taking element screenshot: {}", locator);
        WebElement element = driver.findElement(locator);
        return takeElementScreenshot(element, fileName);
    }

    /**
     * Take element screenshot
     * @param element WebElement
     * @param fileName Screenshot file name
     * @return Screenshot file path
     */
    public String takeElementScreenshot(WebElement element, String fileName) {
        logger.info("Taking element screenshot");
        try {
            String screenshot = element.getScreenshotAs(OutputType.BASE64);
            // Save to file logic can be added here
            return screenshot;
        } catch (Exception e) {
            logger.error("Failed to capture element screenshot", e);
            return null;
        }
    }

    /**
     * Capture failure screenshot
     * @return Screenshot file path
     */
    public String captureFailureScreenshot() {
        logger.info("Capturing failure screenshot");
        return com.automation.utils.ScreenshotUtil.captureFailureScreenshot(driver, 
            new Throwable().getStackTrace()[1].getMethodName());
    }

    // ==================== File Upload/Download ====================

    /**
     * Upload file
     * @param locator File input locator
     * @param filePath File path to upload
     */
    public void uploadFile(By locator, String filePath) {
        logger.info("Uploading file: {}", filePath);
        WebElement element = driver.findElement(locator);
        element.sendKeys(filePath);
        ExtentReportManager.logInfo("File uploaded: " + filePath);
    }

    /**
     * Check if file is downloaded
     * @param fileName File name to check
     * @return true if file exists in download directory
     */
    public boolean isFileDownloaded(String fileName) {
        logger.info("Checking if file is downloaded: {}", fileName);
        String downloadPath = System.getProperty("user.home") + "/Downloads";
        File file = new File(downloadPath, fileName);
        return file.exists();
    }

    // ==================== Table Methods ====================

    /**
     * Get table row count
     * @param tableLocator Table locator
     * @return Number of rows
     */
    public int getTableRowCount(By tableLocator) {
        logger.info("Getting table row count: {}", tableLocator);
        WebElement table = driver.findElement(tableLocator);
        List<WebElement> rows = table.findElements(By.tagName("tr"));
        return rows.size();
    }

    /**
     * Get table column count
     * @param tableLocator Table locator
     * @return Number of columns
     */
    public int getTableColumnCount(By tableLocator) {
        logger.info("Getting table column count: {}", tableLocator);
        WebElement table = driver.findElement(tableLocator);
        WebElement firstRow = table.findElement(By.tagName("tr"));
        List<WebElement> columns = firstRow.findElements(By.tagName("td"));
        if (columns.isEmpty()) {
            columns = firstRow.findElements(By.tagName("th"));
        }
        return columns.size();
    }

    /**
     * Get cell data from table
     * @param tableLocator Table locator
     * @param row Row index (0-based)
     * @param column Column index (0-based)
     * @return Cell text
     */
    public String getCellData(By tableLocator, int row, int column) {
        logger.info("Getting cell data from table: {}, row: {}, column: {}", tableLocator, row, column);
        WebElement table = driver.findElement(tableLocator);
        List<WebElement> rows = table.findElements(By.tagName("tr"));
        WebElement targetRow = rows.get(row + 1); // +1 to skip header row
        List<WebElement> cells = targetRow.findElements(By.tagName("td"));
        return cells.get(column).getText();
    }

    /**
     * Click on table cell
     * @param tableLocator Table locator
     * @param row Row index (0-based)
     * @param column Column index (0-based)
     */
    public void clickTableCell(By tableLocator, int row, int column) {
        logger.info("Clicking on table cell: {}, row: {}, column: {}", tableLocator, row, column);
        WebElement table = driver.findElement(tableLocator);
        List<WebElement> rows = table.findElements(By.tagName("tr"));
        WebElement targetRow = rows.get(row + 1);
        List<WebElement> cells = targetRow.findElements(By.tagName("td"));
        cells.get(column).click();
    }

    // ==================== Utility Methods ====================

    /**
     * Sleep for specified milliseconds
     * @param milliseconds Time to sleep
     */
    public void sleep(long milliseconds) {
        logger.debug("Sleeping for {} milliseconds", milliseconds);
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Sleep interrupted", e);
        }
    }

    /**
     * Generate random string
     * @param length Length of string
     * @return Random string
     */
    public String generateRandomString(int length) {
        logger.debug("Generating random string of length: {}", length);
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }

    /**
     * Generate random number
     * @param digits Number of digits
     * @return Random number
     */
    public int generateRandomNumber(int digits) {
        logger.debug("Generating random number with {} digits", digits);
        int min = (int) Math.pow(10, digits - 1);
        int max = (int) Math.pow(10, digits) - 1;
        return new Random().nextInt(max - min + 1) + min;
    }

    /**
     * Generate random email
     * @return Random email address
     */
    public String generateRandomEmail() {
        logger.debug("Generating random email");
        return "test" + generateRandomNumber(6) + "@yopmail.com";
    }

    /**
     * Get current date
     * @return Current date as string
     */
    public String getCurrentDate() {
        logger.debug("Getting current date");
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    /**
     * Get current time
     * @return Current time as string
     */
    public String getCurrentTime() {
        logger.debug("Getting current time");
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }

    /**
     * Format date
     * @param pattern Date pattern
     * @return Formatted date
     */
    public String formatDate(String pattern) {
        logger.debug("Formatting date with pattern: {}", pattern);
        return new SimpleDateFormat(pattern).format(new Date());
    }

    // ==================== Logging & Reporting ====================

    /**
     * Log info message
     * @param message Message to log
     */
    public void logInfo(String message) {
        ExtentReportManager.logInfo(message);
    }

    /**
     * Log pass message
     * @param message Message to log
     */
    public void logPass(String message) {
        ExtentReportManager.logPass(message);
    }

    /**
     * Log fail message
     * @param message Message to log
     */
    public void logFail(String message) {
        ExtentReportManager.logFail(message);
    }

    /**
     * Attach screenshot to report
     * @param fileName Screenshot file name
     */
    public void attachScreenshotToReport(String fileName) {
        logger.info("Attaching screenshot to report: {}", fileName);
        String screenshotPath = takeScreenshot(fileName);
        if (screenshotPath != null) {
            ExtentReportManager.addScreenshot(screenshotPath, fileName);
        }
    }

    // ==================== Retry & Exception Handling ====================

    /**
     * Retry click with multiple attempts
     * @param locator By locator
     * @param maxAttempts Maximum retry attempts
     */
    public void retryClick(By locator, int maxAttempts) {
        logger.info("Retrying click on element: {}, max attempts: {}", locator, maxAttempts);
        int attempts = 0;
        while (attempts < maxAttempts) {
            try {
                click(locator);
                return;
            } catch (Exception e) {
                attempts++;
                logger.warn("Click attempt {} failed, retrying...", attempts);
                if (attempts >= maxAttempts) {
                    throw e;
                }
                sleep(1000);
            }
        }
    }

    /**
     * Safe click with exception handling
     * @param locator By locator
     */
    public void safeClick(By locator) {
        try {
            logger.info("Safe clicking on element: {}", locator);
            click(locator);
        } catch (Exception e) {
            logger.error("Failed to click on element: {}", locator, e);
            ExtentReportManager.logFail("Failed to click on element: " + locator);
        }
    }

    /**
     * Safe enter Text with exception handling
     * @param locator By locator
     * @param text Text to enter
     */
    public void safeEnterText(By locator, String text) {
        try {
            logger.info("Safe entering text in element: {}", locator);
            enterText(locator, text);
        } catch (Exception e) {
            logger.error("Failed to enter text in element: {}", locator, e);
            ExtentReportManager.logFail("Failed to enter text in element: " + locator);
        }
    }

    /**
     * Safe get text with exception handling
     * @param locator By locator
     * @return Element text or empty string if failed
     */
    public String safeGetText(By locator) {
        try {
            logger.info("Safe getting text from element: {}", locator);
            return getText(locator);
        } catch (Exception e) {
            logger.error("Failed to get text from element: {}", locator, e);
            ExtentReportManager.logFail("Failed to get text from element: " + locator);
            return "";
        }
    }

    // ==================== Wait Helper Methods (Overloaded) ====================

    /**
     * Wait for element to be visible (WebElement overload)
     * @param element WebElement
     * @return Visible WebElement
     */
    public WebElement waitForElementVisible(WebElement element) {
        logger.debug("Waiting for WebElement to be visible");
        return WaitHelper.waitForElementVisible(element);
    }

    /**
     * Wait for element to be clickable (WebElement overload)
     * @param element WebElement
     * @return Clickable WebElement
     */
    public WebElement waitForElementClickable(WebElement element) {
        logger.debug("Waiting for WebElement to be clickable");
        return WaitHelper.waitForElementClickable(element);
    }
}
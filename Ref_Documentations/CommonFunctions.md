# Common Functions and Utilities

## Overview
This document describes the common functions and utilities available in the automation framework. These reusable components promote code reusability, maintainability, and consistency across the framework.

## BasePage Common Functions

### Element Interaction Functions

#### Click Operations
```java
/**
 * Click on element by locator
 */
public void click(By locator)

/**
 * Click on element with custom element name for logging
 */
public void click(By locator, String elementName)

/**
 * Click on WebElement
 */
public void click(WebElement element)

/**
 * JavaScript click (bypasses element interception)
 */
public void jsClick(By locator)

/**
 * JavaScript click on WebElement
 */
public void jsClick(WebElement element)
```

**Usage Examples:**
```java
// Simple click
click(loginButton);

// Click with custom name for better logging
click(submitButton, "Submit Button on Contact Form");

// JavaScript click for intercepted elements
jsClick(submitButton);
```

#### Text Input Operations
```java
/**
 * Enter text in element
 */
public void enterText(By locator, String text)

/**
 * Enter text with custom field name
 */
public void enterText(By locator, String text, String fieldName)

/**
 * Enter text in WebElement
 */
public void enterText(WebElement element, String text)
```

**Usage Examples:**
```java
// Simple text entry
enterText(usernameField, "test@example.com");

// With custom field name for logging
enterText(passwordField, "password123", "Password Field");

// Clear and enter text (automatic)
enterText(searchField, "search query");
```

#### Clear Operations
```java
/**
 * Clear element by locator
 */
public void clear(By locator)

/**
 * Clear WebElement
 */
public void clear(WebElement element)
```

#### Submit Operations
```java
/**
 * Submit form by locator
 */
public void submit(By locator)
```

### Get Information Functions

#### Get Text
```java
/**
 * Get text from element
 */
public String getText(By locator)

/**
 * Get text with custom element name
 */
public String getText(By locator, String elementName)

/**
 * Get text from WebElement
 */
public String getText(WebElement element)
```

**Usage Examples:**
```java
// Get text
String errorMessage = getText(errorMessageLocator);

// With custom name for logging
String title = getText(pageTitle, "Page Heading");
```

#### Get Attributes
```java
/**
 * Get attribute value
 */
public String getAttribute(By locator, String attributeName)

/**
 * Get attribute from WebElement
 */
public String getAttribute(WebElement element, String attributeName)

/**
 * Get CSS value
 */
public String getCssValue(By locator, String property)

/**
 * Get tag name
 */
public String getTagName(By locator)
```

**Usage Examples:**
```java
// Get href attribute
String href = getAttribute(link, "href");

// Get CSS property
String color = getCssValue(button, "background-color");

// Get tag name
String tag = getTagName(element);
```

#### Get Page Information
```java
/**
 * Get current URL
 */
public String getCurrentUrl()

/**
 * Get page title
 */
public String getPageTitle()
```

### Validation Functions

#### Element State Validation
```java
/**
 * Check if element is displayed
 */
public boolean isDisplayed(By locator)

/**
 * Check if element is displayed with custom name
 */
public boolean isDisplayed(By locator, String elementName)

/**
 * Check if element is enabled
 */
public boolean isEnabled(By locator)

/**
 * Check if element is selected
 */
public boolean isSelected(By locator)

/**
 * Check if element is clickable
 */
public boolean isClickable(By locator)
```

**Usage Examples:**
```java
// Validate element is displayed
if (isDisplayed(errorMessage)) {
    // Handle error
}

// Validate element is enabled
Assert.assertTrue(isEnabled(submitButton), "Submit button should be enabled");
```

#### Text Validation
```java
/**
 * Verify text matches expected
 */
public boolean verifyText(By locator, String expectedText)

/**
 * Verify attribute value
 */
public boolean verifyAttribute(By locator, String attribute, String expectedValue)
```

**Usage Examples:**
```java
// Verify text
boolean matches = verifyText(welcomeMessage, "Welcome, User!");

// Verify attribute
boolean isActive = verifyAttribute(tab, "class", "active");
```

### Wait Functions

#### Explicit Wait Functions
```java
/**
 * Wait for element to be visible
 */
public WebElement waitForElementVisible(By locator)

/**
 * Wait for element to be clickable
 */
public WebElement waitForElementClickable(By locator)

/**
 * Wait for element to be present
 */
public WebElement waitForElementPresent(By locator)

/**
 * Wait for element to be invisible
 */
public void waitForElementInvisible(By locator)

/**
 * Wait for text to be present
 */
public boolean waitForTextToBePresent(By locator, String text)
```

**Usage Examples:**
```java
// Wait for element
WebElement element = waitForElementVisible(submitButton);

// Wait for text
boolean hasText = waitForTextToBePresent(message, "Success");
```

#### Page Load Wait
```java
/**
 * Wait for page to load completely
 */
public void waitForPageLoad()

/**
 * Wait for AJAX to complete
 */
public void waitForAjaxToComplete()

/**
 * Wait until URL contains specific text
 */
public void waitUntilUrlContains(String urlPart)

/**
 * Wait until title contains specific text
 */
public void waitUntilTitleContains(String title)
```

### Dropdown Functions
```java
/**
 * Select by visible text
 */
public void selectByVisibleText(By locator, String text)

/**
 * Select by value
 */
public void selectByValue(By locator, String value)

/**
 * Select by index
 */
public void selectByIndex(By locator, int index)

/**
 * Get selected option
 */
public String getSelectedOption(By locator)

/**
 * Get all dropdown options
 */
public List<String> getAllDropdownOptions(By locator)

/**
 * Deselect all (multi-select)
 */
public void deselectAll(By locator)
```

**Usage Examples:**
```java
// Select by visible text
selectByVisibleText(countryDropdown, "United States");

// Select by value
selectByValue(countryDropdown, "US");

// Get selected option
String selected = getSelectedOption(countryDropdown);
```

### Mouse Actions
```java
/**
 * Hover over element
 */
public void hover(By locator)

/**
 * Double click on element
 */
public void doubleClick(By locator)

/**
 * Right click on element
 */
public void rightClick(By locator)

/**
 * Drag and drop
 */
public void dragAndDrop(By source, By target)

/**
 * Drag and drop WebElements
 */
public void dragAndDrop(WebElement source, WebElement target)

/**
 * Move to element
 */
public void moveToElement(By locator)
```

### Keyboard Actions
```java
/**
 * Press Enter key
 */
public void pressEnter()

/**
 * Press Escape key
 */
public void pressEscape()

/**
 * Press Tab key
 */
public void pressTab()

/**
 * Key down
 */
public void keyDown(Keys key)

/**
 * Key up
 */
public void keyUp(Keys key)

/**
 * Copy text (Ctrl+C)
 */
public void copyText()

/**
 * Paste text (Ctrl+V)
 */
public void pasteText()
```

### Scroll Functions
```java
/**
 * Scroll to element
 */
public void scrollToElement(By locator)

/**
 * Scroll to top of page
 */
public void scrollToTop()

/**
 * Scroll to bottom of page
 */
public void scrollToBottom()

/**
 * Scroll by pixels
 */
public void scrollBy(int x, int y)

/**
 * Scroll element into view
 */
public void scrollIntoView(By locator)
```

### JavaScript Functions
```java
/**
 * Execute JavaScript
 */
public Object executeJavaScript(String script)

/**
 * JavaScript enter text
 */
public void jsenterText(By locator, String text)

/**
 * Highlight element
 */
public void highlightElement(By locator)

/**
 * Remove attribute
 */
public void removeAttribute(By locator, String attribute)
```

**Usage Examples:**
```java
// Execute JavaScript
Long scrollPosition = (Long) executeJavaScript("return window.pageYOffset;");

// Highlight element
highlightElement(errorMessage);

// Remove attribute
removeAttribute(readonlyField, "readonly");
```

### Alert Functions
```java
/**
 * Accept alert
 */
public void acceptAlert()

/**
 * Dismiss alert
 */
public void dismissAlert()

/**
 * Get alert text
 */
public String getAlertText()

/**
 * Send keys to alert
 */
public void sendKeysToAlert(String text)

/**
 * Check if alert is present
 */
public boolean isAlertPresent()

/**
 * Wait for alert
 */
public Alert waitForAlert()
```

### Window/Tab Functions
```java
/**
 * Switch to window by index
 */
public void switchToWindow(int index)

/**
 * Switch to window by title
 */
public void switchToWindow(String title)

/**
 * Switch to new window
 */
public void switchToNewWindow()

/**
 * Close current window
 */
public void closeCurrentWindow()

/**
 * Switch to parent window
 */
public void switchToParentWindow()

/**
 * Get window handles count
 */
public int getWindowHandlesCount()
```

### Frame Functions
```java
/**
 * Switch to frame by locator
 */
public void switchToFrame(By locator)

/**
 * Switch to frame by index
 */
public void switchToFrame(int index)

/**
 * Switch to frame by name/ID
 */
public void switchToFrame(String nameOrId)

/**
 * Switch to default content
 */
public void switchToDefaultContent()

/**
 * Switch to parent frame
 */
public void switchToParentFrame()
```

### Screenshot Functions
```java
/**
 * Take screenshot
 */
public String takeScreenshot(String fileName)

/**
 * Take element screenshot
 */
public String takeElementScreenshot(By locator, String fileName)

/**
 * Take element screenshot (WebElement)
 */
public String takeElementScreenshot(WebElement element, String fileName)

/**
 * Capture failure screenshot
 */
public String captureFailureScreenshot()
```

### File Operations
```java
/**
 * Upload file
 */
public void uploadFile(By locator, String filePath)

/**
 * Check if file is downloaded
 */
public boolean isFileDownloaded(String fileName)
```

### Table Functions
```java
/**
 * Get table row count
 */
public int getTableRowCount(By tableLocator)

/**
 * Get table column count
 */
public int getTableColumnCount(By tableLocator)

/**
 * Get cell data
 */
public String getCellData(By tableLocator, int row, int column)

/**
 * Click on table cell
 */
public void clickTableCell(By tableLocator, int row, int column)
```

## Utility Functions

### WaitHelper Functions
```java
/**
 * Wait for element visible
 */
public static WebElement waitForElementVisible(By locator)

/**
 * Wait for element clickable
 */
public static WebElement waitForElementClickable(By locator)

/**
 * Wait for element present
 */
public static WebElement waitForElementPresent(By locator)

/**
 * Wait for element to disappear
 */
public static void waitForElementToDisappear(By locator)

/**
 * Wait for text present
 */
public static boolean waitForTextPresent(By locator, String text)

/**
 * Wait for alert
 */
public static Alert waitForAlert()

/**
 * Wait for JavaScript condition
 */
public static void waitForJavaScriptCondition(String script)
```

### WebDriverFactory Functions
```java
/**
 * Initialize driver
 */
public static WebDriver initializeDriver()

/**
 * Get current driver
 */
public static WebDriver getDriver()

/**
 * Quit driver
 */
public static void quitDriver()

/**
 * Navigate to URL
 */
public static void navigateTo(String url)
```

### ConfigReader Functions
```java
/**
 * Get browser name
 */
public static String getBrowser()

/**
 * Get base URL
 */
public static String getBaseUrl()

/**
 * Get username
 */
public static String getUsername()

/**
 * Get password
 */
public static String getPassword()

/**
 * Get implicit wait
 */
public static int getImplicitWait()

/**
 * Get explicit wait
 */
public static int getExplicitWait()

/**
 * Get page load timeout
 */
public static int getPageLoadTimeout()

/**
 * Check if headless mode
 */
public static boolean isHeadless()

/**
 * Get property value
 */
public static String getProperty(String key)

/**
 * Get boolean property
 */
public static boolean getBooleanProperty(String key, boolean defaultValue)
```

### ScreenshotUtil Functions
```java
/**
 * Capture screenshot
 */
public static String captureScreenshot(WebDriver driver, String fileName)

/**
 * Capture failure screenshot
 */
public static String captureFailureScreenshot(WebDriver driver, String testName)

/**
 * Get screenshot as base64
 */
public static String getScreenshotAsBase64(WebDriver driver)
```

### ExtentReportManager Functions
```java
/**
 * Initialize reports
 */
public static void initializeReports()

/**
 * Create test
 */
public static void createTest(String testName, String description)

/**
 * Log info
 */
public static void logInfo(String message)

/**
 * Log pass
 */
public static void logPass(String message)

/**
 * Log fail
 */
public static void logFail(String message)

/**
 * Log skip
 */
public static void logSkip(String message)

/**
 * Log warning
 */
public static void logWarning(String message)

/**
 * Log error
 */
public static void logError(String message)

/**
 * Add screenshot from base64
 */
public static void addScreenshotFromBase64(String base64, String title)

/**
 * Add screenshot from file
 */
public static void addScreenshot(String filePath, String title)

/**
 * Log exception
 */
public static void logException(Exception e)

/**
 * Flush reports
 */
public static void flushReports()

/**
 * Close reports
 */
public static void closeReports()
```

### EmailUtil Functions
```java
/**
 * Send test report email
 */
public static void sendTestReport()

/**
 * Send email with attachment
 */
public static void sendEmail(String to, String subject, String body, String attachmentPath)
```

## Common Test Patterns

### Pattern 1: Simple Form Submission
```java
@Test
public void testFormSubmission() {
    // Navigate
    navigateToBaseUrl();
    
    // Create page object
    ContactPage contactPage = new ContactPage(WebDriverFactory.getDriver());
    
    // Fill form
    contactPage.enterName("John Doe");
    contactPage.enterEmail("john@example.com");
    contactPage.enterSubject("Test Subject");
    contactPage.enterMessage("Test message");
    
    // Submit
    contactPage.clickSubmitButton();
    
    // Verify
    Assert.assertTrue(contactPage.isSuccessMessageVisible(), 
        "Success message should be displayed");
}
```

### Pattern 2: Login Test
```java
@Test
public void testLogin() {
    // Navigate
    navigateToBaseUrl();
    
    // Create page object
    LoginPage loginPage = new LoginPage(WebDriverFactory.getDriver());
    
    // Perform login
    loginPage.login(ConfigReader.getUsername(), ConfigReader.getPassword());
    
    // Verify
    Assert.assertTrue(loginPage.isLoginSuccessful(), 
        "Login should be successful");
}
```

### Pattern 3: Navigation Test
```java
@Test
public void testNavigation() {
    // Navigate
    navigateToBaseUrl();
    
    // Create page object
    HomePage homePage = new HomePage(WebDriverFactory.getDriver());
    
    // Navigate to page
    homePage.clickContactUsLink();
    
    // Verify navigation
    ContactPage contactPage = new ContactPage(WebDriverFactory.getDriver());
    Assert.assertTrue(contactPage.isGetInTouchHeadingVisible(), 
        "Should navigate to contact page");
}
```

### Pattern 4: Validation Test
```java
@Test
public void testValidation() {
    // Navigate
    navigateToBaseUrl();
    
    // Create page object
    LoginPage loginPage = new LoginPage(WebDriverFactory.getDriver());
    
    // Try invalid login
    loginPage.performInvalidLogin("invalid", "invalid");
    
    // Verify error
    Assert.assertTrue(loginPage.isErrorMessageDisplayed(), 
        "Error message should be displayed");
    
    String error = loginPage.getErrorMessage();
    Assert.assertTrue(error.contains("Invalid credentials"), 
        "Error message should mention invalid credentials");
}
```

### Pattern 5: Data-Driven Test
```java
@Test(dataProvider = "loginData")
public void testLoginWithMultipleUsers(String username, String password, boolean shouldSucceed) {
    navigateToBaseUrl();
    
    LoginPage loginPage = new LoginPage(WebDriverFactory.getDriver());
    loginPage.login(username, password);
    
    if (shouldSucceed) {
        Assert.assertTrue(loginPage.isLoginSuccessful(), 
            "Login should be successful for valid credentials");
    } else {
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), 
            "Error message should be displayed for invalid credentials");
    }
}

@DataProvider(name = "loginData")
public Object[][] getLoginData() {
    return new Object[][]{
        {"valid_user", "valid_pass", true},
        {"invalid_user", "invalid_pass", false},
        {"", "password", false}
    };
}
```

## Helper Functions

### Random Data Generation
```java
/**
 * Generate random string
 */
public String generateRandomString(int length)

/**
 * Generate random number
 */
public int generateRandomNumber(int digits)

/**
 * Generate random email
 */
public String generateRandomEmail()
```

**Usage Examples:**
```java
// Generate random user
String username = generateRandomString(8);
String email = generateRandomEmail();
String phone = generateRandomNumber(10);
```

### Date/Time Functions
```java
/**
 * Get current date
 */
public String getCurrentDate()

/**
 * Get current time
 */
public String getCurrentTime()

/**
 * Format date
 */
public String formatDate(String pattern)
```

**Usage Examples:**
```java
// Get current date
String today = getCurrentDate(); // "2026-01-01"

// Format date
String timestamp = formatDate("yyyyMMdd_HHmmss");
```

### Sleep/Wait Functions
```java
/**
 * Sleep for milliseconds
 */
public void sleep(long milliseconds)
```

**Usage Examples:**
```java
// Wait for 2 seconds
sleep(2000);
```

## Safe Operations

### Safe Click
```java
/**
 * Safe click with exception handling
 */
public void safeClick(By locator)
```

**Usage:**
```java
// Won't throw exception if element not found
safeClick(submitButton);
```

### Safe Text Entry
```java
/**
 * Safe enter text with exception handling
 */
public void safeEnterText(By locator, String text)
```

### Safe Get Text
```java
/**
 * Safe get text with exception handling
 */
public String safeGetText(By locator)
```

**Usage:**
```java
// Returns empty string if element not found
String text = safeGetText(errorMessage);
```

### Retry Operations
```java
/**
 * Retry click with multiple attempts
 */
public void retryClick(By locator, int maxAttempts)
```

**Usage:**
```java
// Retry click up to 3 times
retryClick(submitButton, 3);
```

## Logging Functions

### Log to Report
```java
/**
 * Log info message
 */
public void logInfo(String message)

/**
 * Log pass message
 */
public void logPass(String message)

/**
 * Log fail message
 */
public void logFail(String message)
```

**Usage Examples:**
```java
// Log test steps
logInfo("Entering username: " + username);
logPass("Login successful");
logFail("Invalid credentials provided");
```

## Best Practices

### Function Usage
✓ Use BasePage functions instead of direct WebDriver calls
✓ Use safe operations for non-critical elements
✓ Use explicit waits instead of Thread.sleep
✓ Use JavaScript click for intercepted elements
✓ Use meaningful parameters

✗ Don't create duplicate functions
✗ Don't use Thread.sleep (use wait functions)
✗ Don't use direct WebDriver calls in tests
✗ Don't catch generic exceptions

### Function Organization
- Common functions in BasePage
- Page-specific functions in page classes
- Utility functions in utility classes
- Reusable components in separate classes

### Performance Tips
✓ Use lazy initialization for expensive resources
✓ Cache locators in locator classes
✓ Use implicit wait wisely (prefer explicit)
✓ Clean up resources in @AfterMethod

## Summary

### Most Used Functions
```java
// Navigation
navigateToBaseUrl()
navigateTo(url)

// Element interaction
click(locator)
enterText(locator, text)
clear(locator)

// Validation
isDisplayed(locator)
getText(locator)
verifyText(locator, expected)

// Wait
waitForElementVisible(locator)
waitForPageLoad()

// Screenshot
takeScreenshot(fileName)
captureFailureScreenshot()

// Reporting
logInfo(message)
logPass(message)
logFail(message)
```

### Quick Reference
| Function | Purpose | Example |
|----------|---------|---------|
| `click()` | Click element | `click(loginButton)` |
| `enterText()` | Enter text | `enterText(usernameField, "user")` |
| `getText()` | Get element text | `String text = getText(message)` |
| `isDisplayed()` | Check visibility | `boolean visible = isDisplayed(element)` |
| `waitForElementVisible()` | Wait for element | `WebElement el = waitForElementVisible(locator)` |
| `selectByVisibleText()` | Select dropdown | `selectByVisibleText(dropdown, "Option")` |
| `jsClick()` | JavaScript click | `jsClick(interceptedButton)` |
| `takeScreenshot()` | Capture screenshot | `String path = takeScreenshot("test")` |
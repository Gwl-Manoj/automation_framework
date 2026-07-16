# Naming Conventions

## Overview
This document defines the naming conventions used throughout the automation framework to ensure consistency and readability.

## General Principles

### Clarity and Readability
- Names should be descriptive and convey purpose
- Avoid abbreviations unless universally understood
- Use full words instead of acronyms
- Names should be pronounceable

### Consistency
- Follow conventions consistently across the project
- Use the same pattern for similar elements
- Document any exceptions to the rules

## Package Naming

### Structure
```
com.automation.[layer].[module]
```

### Examples
```java
// Layer-based organization
com.automation.base          // Base classes
com.automation.pages         // Page objects
com.automation.tests         // Test classes
com.automation.utils         // Utility classes
com.automation.locators      // Locator classes
com.automation.constants     // Constants

// Module-based sub-packages (if needed)
com.automation.pages.login
com.automation.pages.contact
com.automation.tests.ui
com.automation.tests.api
```

### Rules
- All lowercase letters
- No underscores or hyphens
- Maximum 3-4 levels deep
- Use meaningful module names

## Class Naming

### Page Objects
```java
// Pattern: [PageName]Page
LoginPage
ContactPage
HomePage
DashboardPage
RegistrationPage
```

### Test Classes
```java
// Pattern: [Feature]Test
LoginTest
ContactTest
HomePageTest
DashboardTest
```

### Base Classes
```java
// Pattern: Base[Type]
BasePage
BaseTest
BaseComponent
```

### Utility Classes
```java
// Pattern: [Purpose]Util or [Purpose]Helper
WebDriverFactory
ConfigReader
ExtentReportManager
ScreenshotUtil
WaitHelper
```

### Locator Classes
```java
// Pattern: [PageName]Locators
LoginPageLocators
ContactPageLocators
HomePageLocators
```

### Listener Classes
```java
// Pattern: [Purpose]Listener
TestListener
RetryListener
ReportListener
```

## Method Naming

### Action Methods (Page Objects)
```java
// Pattern: [action][Element]
// Examples:
enterUsername(String username)
enterPassword(String password)
clickLoginButton()
clickSubmitButton()
selectCountry(String country)
uploadFile(String filePath)
scrollToElement(By locator)
hoverOverMenu()

// Navigation methods
navigateToLoginPage()
navigateToDashboard()
goBack()
goForward()
```

### Verification Methods (Page Objects)
```java
// Pattern: is[State]/has[Attribute]/can[Action]
// Examples:
isLoginPageLoaded()
isErrorMessageDisplayed()
isSuccessMessageVisible()
hasValidationError()
canClickSubmitButton()
isUserLoggedIn()
isElementEnabled(By locator)
```

### Getter Methods
```java
// Pattern: get[Attribute]
// Examples:
getUsername()
getErrorMessage()
getCurrentUrl()
getPageTitle()
getSelectedOption()
getAttributeValue(String attribute)
```

### Test Methods
```java
// Pattern: test[Scenario][Condition]
// Examples:
testSuccessfulLogin()
testInvalidLogin()
testLoginWithEmptyFields()
testContactUsFormSubmission()
testNavigationToHomePage()
testUserRegistrationWithValidData()
testLoginWithInvalidCredentials()

// Use descriptive names that explain what is being tested
```

### Utility Methods
```java
// Pattern: [action][Object]
// Examples:
initializeDriver()
quitDriver()
waitForElementVisible(By locator)
captureScreenshot(String fileName)
generateRandomEmail()
formatDate(Date date, String pattern)
```

## Variable Naming

### Instance Variables
```java
// Pattern: [descriptor][Type]
// Examples:
private String usernameField;
private By passwordField;
private WebElement loginButton;
private List<WebElement> dropdownOptions;
private int maxRetryCount;
private boolean isLoggedIn;
```

### Local Variables
```java
// Same pattern as instance variables
String username = "test@example.com";
By submitButton = By.id("submit");
WebElement element = driver.findElement(locator);
int retryCount = 0;
boolean isVisible = element.isDisplayed();
```

### Constants
```java
// Pattern: UPPER_SNAKE_CASE
// Examples:
private static final int MAX_RETRY_COUNT = 3;
private static final String DEFAULT_URL = "https://example.com";
private static final long IMPLICIT_WAIT = 10;
private static final String BROWSER_CHROME = "chrome";
```

### Collection Variables
```java
// Pattern: [descriptor]s or [descriptor]List/Set/Map
List<WebElement> menuItems;
Set<String> windowHandles;
Map<String, String> userCredentials;
List<String> errorMessages;
```

## Parameter Naming

### Method Parameters
```java
// Pattern: descriptive names matching variable conventions
public void enterUsername(String username)
public void clickElement(By elementLocator)
public void waitForSeconds(int timeoutInSeconds)
public void navigateToUrl(String url)
```

### Avoid Generic Names
```java
// Bad
public void enterData(String s, By e)
public void click(String x)

// Good
public void enterUsername(String username)
public void clickLoginButton(By loginButtonLocator)
```

## Locator Naming

### Locator Variables
```java
// Pattern: [descriptor]Field/[descriptor]Button/etc
// Examples:
private By usernameField;
private By passwordField;
private By loginButton;
private By submitButton;
private By errorMessage;
private By successMessage;
private By dropdownOptions;
private By searchInput;
```

### Locator Classes
```java
// All locators in one class per page
public class LoginPageLocators {
    public By usernameField = By.id("username");
    public By passwordField = By.id("password");
    public By loginButton = By.xpath("//button[text()='Login']");
    public By errorMessage = By.cssSelector(".error-message");
}
```

## Test Data Naming

### Test Data Variables
```java
// Pattern: [purpose]Data or [purpose][Type]
String testUsername = "test@example.com";
String testPassword = "Test@123";
String invalidEmail = "invalid-email";
int expectedItemCount = 5;
```

### Test Data Files
```
test-data/
├── login-test-data.xlsx
├── contact-test-data.csv
├── user-profiles.json
└── test-config.properties
```

## Annotation Naming

### Custom Annotations
```java
// Pattern: [Purpose]Annotation
@RetryOnFailure
@TakeScreenshotOnFailure
@FlakyTest
@SmokeTest
@RegressionTest
```

## Package-Private Naming

### Package-Private Methods
```java
// Methods visible only within package - no special prefix
void initializePageLocators() {
    // Implementation
}

By getLocatorByName(String name) {
    // Implementation
}
```

## Acronyms and Abbreviations

### Capitalization Rules
```java
// Two-letter acronyms - both uppercase
URLHelper
IOStream
TCPConnection

// Three+ letter acronyms - first letter uppercase only
XmlParser
JsonReader
HtmlElement
SeleniumWebDriver
```

### Common Acronyms
```java
// Use these standard forms
URL, URI, HTML, XML, JSON, CSV, API, UI, UX, DB, ID, IO, SSL, TLS
// Not: url, uri, html, xml, json, csv, api, ui, ux, db, id, io, ssl, tls
```

## File Naming

### Java Files
```
LoginPage.java          // Page object
LoginTest.java          // Test class
LoginPageLocators.java  // Locator class
WebDriverFactory.java   // Utility class
BaseTest.java          // Base class
```

### Resource Files
```
config.properties           // Configuration
test-data.xlsx              // Test data
testng.xml                  // TestNG suite
log4j2.xml                  // Logging config
extent-config.xml           // Report config
```

### Screenshot Files
```
screenshot_20260101_120000_login_success.png
screenshot_20260101_120500_login_failure.png
```

## Enum Naming

### Enum Constants
```java
// Pattern: UPPER_SNAKE_CASE
public enum BrowserType {
    CHROME,
    FIREFOX,
    EDGE,
    SAFARI
}

public enum TestStatus {
    PASSED,
    FAILED,
    SKIPPED,
    UNKNOWN
}
```

### Enum Classes
```java
// Pattern: PascalCase with 'Enum' suffix (optional)
public enum BrowserType { }
public enum TestStatus { }
```

## Interface Naming

### Interface Names
```java
// Pattern: PascalCase, optionally with 'I' prefix (team preference)
// Option 1: Without prefix (preferred)
public interface WebDriverManager { }
public interface TestReporter { }

// Option 2: With prefix (if preferred)
public interface IWebDriverManager { }
public interface ITestReporter { }
```

## Exception Naming

### Custom Exceptions
```java
// Pattern: [Purpose]Exception
public class ElementNotFoundException extends RuntimeException { }
public class TestDataException extends Exception { }
public class ConfigurationException extends RuntimeException { }
```

## Thread Naming

### Thread Names
```java
// Pattern: [Purpose]-[Identifier]
Thread loginTestThread = new Thread(() -> { }, "LoginTest-Thread-1");
Thread parallelTestThread = new Thread(() -> { }, "ParallelTest-Thread-2");
```

## Summary Table

| Type | Convention | Example |
|------|-----------|---------|
| Package | lowercase | `com.automation.pages` |
| Class | PascalCase | `LoginPage` |
| Interface | PascalCase | `WebDriverFactory` |
| Method | camelCase | `enterUsername()` |
| Variable | camelCase | `usernameField` |
| Constant | UPPER_SNAKE_CASE | `MAX_RETRY_COUNT` |
| Enum | PascalCase | `BrowserType` |
| Enum Value | UPPER_SNAKE_CASE | `CHROME` |
| Parameter | camelCase | `username` |
| Locator | camelCase with type | `loginButton` |

## Best Practices

### Do's
✓ Use descriptive names that explain purpose
✓ Follow conventions consistently
✓ Use standard Java naming patterns
✓ Keep names concise but meaningful
✓ Use team-agreed abbreviations only

### Don'ts
✗ Use single-letter variables (except loop counters)
✗ Use Hungarian notation (strUsername, intCount)
✗ Use underscores in variable/method names
✗ Use abbreviations that are not universally understood
✗ Mix naming conventions in the same project
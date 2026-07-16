# Coding Guidelines

## Java Coding Standards

### Code Organization
- **Package Structure**: Follow the standard package hierarchy
- **Class Length**: Keep classes under 500 lines; split if larger
- **Method Length**: Keep methods under 50 lines; extract complex logic
- **Single Responsibility**: Each class/method should have one purpose

### Formatting Rules
- **Indentation**: 4 spaces (no tabs)
- **Line Length**: Maximum 120 characters
- **Braces**: Opening brace on same line for classes/methods
- **Blank Lines**: One blank line between methods, two between classes
- **Imports**: Organized by IDE (group static, then third-party, then project)

### Naming Conventions

#### Classes and Interfaces
```java
// Classes - PascalCase
public class LoginPage { }
public class ContactTest { }
public interface WebDriverFactory { }

// Abstract classes - PascalCase with 'Abstract' prefix
public abstract class BasePage { }
```

#### Methods
```java
// camelCase for methods
public void enterUsername(String username) { }
public boolean isLoginSuccessful() { }
public String getErrorMessage() { }

// Boolean methods should start with 'is', 'has', 'can', 'should'
public boolean isDisplayed() { }
public boolean hasError() { }
public boolean canSubmit() { }
```

#### Variables
```java
// camelCase for variables
private String usernameField;
private By loginButton;
private int maxRetryCount;

// Constants - UPPER_SNAKE_CASE
private static final int MAX_RETRY_COUNT = 3;
private static final String DEFAULT_URL = "https://example.com";
```

#### Packages
```java
// All lowercase, no underscores
package com.automation.pages;
package com.automation.utils;
package com.automation.tests;
```

### Comments and Documentation

#### Javadoc Requirements
```java
/**
 * Enter text in element by locator
 * @param locator By locator
 * @param text Text to enter
 */
public void enterText(By locator, String text) {
    // Implementation
}

/**
 * Check if login is successful
 * @return true if login is successful
 * @throws IllegalStateException if driver is null
 */
public boolean isLoginSuccessful() {
    // Implementation
}
```

#### Inline Comments
```java
// Use inline comments sparingly - only for complex logic
// Bad - obvious comment
// Increment counter
counter++;

// Good - explains why
// Use explicit wait instead of implicit for better reliability
waitForElementVisible(locator);
```

### Exception Handling

#### Checked Exceptions
```java
// Declare checked exceptions in method signature
public void uploadFile(String filePath) throws IOException {
    // Implementation
}
```

#### Runtime Exceptions
```java
// Use runtime exceptions for programming errors
if (driver == null) {
    throw new IllegalArgumentException("WebDriver cannot be null");
}

// Always provide meaningful error messages
throw new IllegalStateException("Failed to initialize WebDriver after 3 attempts");
```

#### Try-Catch Best Practices
```java
// Good - specific exception handling
try {
    alert.accept();
} catch (NoAlertPresentException e) {
    logger.warn("No alert present to accept", e);
}

// Bad - catching generic Exception
try {
    alert.accept();
} catch (Exception e) {
    // Too generic
}
```

### Logging Standards

#### Log Levels
```java
// ERROR - Application errors that need immediate attention
logger.error("Failed to initialize WebDriver", e);

// WARN - Warning messages for recoverable issues
logger.warn("Element not found, retrying...");

// INFO - Informational messages for test progress
logger.info("Starting test: {}", testName);

// DEBUG - Detailed information for debugging
logger.debug("Waiting for element: {}", locator);
```

#### Log Message Format
```java
// Good - descriptive with context
logger.info("Login successful for user: {}", username);
logger.error("Failed to click element: {}", locator, e);

// Bad - vague or no context
logger.info("Success");
logger.error("Error");
```

### Code Reusability

#### DRY Principle (Don't Repeat Yourself)
```java
// Bad - duplicate code
public void enterUsername(String username) {
    driver.findElement(usernameField).clear();
    driver.findElement(usernameField).sendKeys(username);
}

public void enterPassword(String password) {
    driver.findElement(passwordField).clear();
    driver.findElement(passwordField).sendKeys(password);
}

// Good - reusable method
public void enterText(By locator, String text) {
    WebElement element = waitForElementVisible(locator);
    element.clear();
    element.sendKeys(text);
}
```

#### Utility Methods
- Common operations go in `BasePage`
- Create utility classes for cross-cutting concerns
- Use inheritance for shared functionality

### Best Practices

#### Avoid Hardcoding
```java
// Bad - hardcoded values
driver.get("https://example.com/login");
Thread.sleep(5000);

// Good - use constants or config
driver.get(ConfigReader.getBaseUrl() + "/login");
waitForPageLoad();
```

#### Use Meaningful Variable Names
```java
// Bad
String s = "test";
int x = 10;
By e = By.id("username");

// Good
String testUsername = "test";
int maxRetryCount = 10;
By usernameField = By.id("username");
```

#### Method Parameters
```java
// Limit to 5 parameters max
// If more needed, use a parameter object
public void login(String username, String password) { }

// For many parameters, create a data class
public class LoginCredentials {
    private String username;
    private String password;
    private boolean rememberMe;
    // getters and setters
}
```

### Thread Safety

#### ThreadLocal Usage
```java
// Use ThreadLocal for WebDriver in multi-threaded tests
private static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

public static WebDriver getDriver() {
    return driverThreadLocal.get();
}

public static void setDriver(WebDriver driver) {
    driverThreadLocal.set(driver);
}
```

#### Avoid Shared State
```java
// Bad - shared mutable state
public static WebDriver driver;

// Good - thread-local instance
private static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
```

### Performance Considerations

#### Lazy Initialization
```java
// Initialize expensive resources only when needed
private Actions actions;

private Actions getActions() {
    if (actions == null) {
        actions = new Actions(driver);
    }
    return actions;
}
```

#### Resource Cleanup
```java
// Always clean up resources in @AfterMethod or @AfterSuite
@AfterMethod
public void tearDown() {
    WebDriverFactory.quitDriver();
}
```

### Testing Best Practices

#### Test Independence
```java
// Each test should be independent
@Test
public void testLogin() {
    // Setup
    // Execute
    // Assert
    // Teardown (automatic via @AfterMethod)
}

// Tests should not depend on execution order
```

#### Assertion Best Practices
```java
// Good - descriptive assertion messages
Assert.assertTrue(loginPage.isLoginPageLoaded(), 
    "Login page should be loaded with username and password fields");

// Bad - no message
Assert.assertTrue(loginPage.isLoginPageLoaded());
```

#### Test Data Management
```java
// Use test data from external sources
String username = ConfigReader.getUsername();
String password = ConfigReader.getPassword();

// Or use test data factories
User testUser = TestDataFactory.createRandomUser();
```

## Code Review Checklist

### Before Submitting Code
- [ ] Code follows Java naming conventions
- [ ] Methods are under 50 lines
- [ ] Classes are under 500 lines
- [ ] Javadoc comments for all public methods
- [ ] Meaningful variable and method names
- [ ] No hardcoded values (use constants/config)
- [ ] Proper exception handling
- [ ] Appropriate logging statements
- [ ] No duplicate code (DRY principle)
- [ ] Thread-safe implementation
- [ ] Resources properly cleaned up
- [ ] Tests are independent and idempotent
- [ ] Assertions have descriptive messages
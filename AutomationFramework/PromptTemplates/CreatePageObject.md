# Create Page Object

## Role
You are an expert test automation engineer specializing in Page Object Model (POM) design pattern with Selenium WebDriver and Java.

## Task
Create a comprehensive Page Object class following framework standards and best practices.

## Context
- **Framework**: Selenium WebDriver + TestNG + Java 11
- **Pattern**: Page Object Model (POM)
- **Base Class**: All page objects extend `BasePage`
- **Locators**: Stored in separate locator classes
- **Design**: Encapsulate all page-specific logic and element interactions

## Requirements

### Page Information
- **Page Name**: [e.g., LoginPage, ContactPage, DashboardPage]
- **Page URL**: [Base URL or relative path]
- **Page Description**: [Brief description of the page purpose]

### Page Elements
[List all elements on the page:]
- **Form Fields**: [username, password, email, etc.]
- **Buttons**: [login, submit, cancel, etc.]
- **Links**: [forgot password, home, etc.]
- **Messages**: [error, success, warning messages]
- **Dropdowns**: [country, state, etc.]
- **Other Elements**: [checkboxes, radio buttons, tables, etc.]

### Page Actions
[List all user actions on the page:]
- [Enter username]
- [Click login button]
- [Submit form]
- [Navigate to another page]

### Page Verifications
[List all verifications/validations:]
- [Check if login successful]
- [Verify error message displayed]
- [Check if page loaded]

## Instructions

### 1. Page Class Structure
Create a page class that:
- Extends `BasePage`
- Is placed in `src/main/java/com/automation/pages/` package
- Follows naming convention: `[PageName]Page.java`
- Has a corresponding locator class: `[PageName]Locators.java`

### 2. Locator Class
Create a separate locator class with:
- All element locators as public By variables
- Organized by element type (fields, buttons, messages, etc.)
- Comments for each locator group

```java
package com.automation.locators;

import org.openqa.selenium.By;

public class [PageName]Locators {
    // Form Fields
    public By usernameField = By.id("username");
    public By passwordField = By.id("password");
    
    // Buttons
    public By loginButton = By.cssSelector(".btn-login");
    public By submitButton = By.id("submit");
    
    // Messages
    public By errorMessage = By.cssSelector(".error-message");
    public By successMessage = By.cssSelector(".success-message");
    
    // Links
    public By forgotPasswordLink = By.linkText("Forgot Password?");
}
```

### 3. Page Class Structure
```java
package com.automation.pages;

import com.automation.locators.[PageName]Locators;
import com.automation.utils.ExtentReportManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * [Page Name] - Page Object Model
 * Represents the [page description] and its elements
 */
public class [PageName]Page extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger([PageName]Page.class);
    private [PageName]Locators locators;

    /**
     * Constructor
     * @param driver WebDriver instance
     */
    public [PageName]Page(WebDriver driver) {
        super(driver);
        this.locators = new [PageName]Locators();
        logger.info("[PageName]Page initialized");
    }

    // Page actions (business logic)
    // Page verifications
}
```

### 4. Page Actions (Business Logic)
Create methods for user actions:
- Use `enterText()`, `click()`, `selectByVisibleText()` from BasePage
- Use locators from locator class
- Add logging for each action
- Chain related actions where appropriate

```java
/**
 * Enter username
 * @param username Username to enter
 */
public void enterUsername(String username) {
    logger.info("Entering username: {}", username);
    enterText(locators.usernameField, username, "Username Field");
}

/**
 * Click login button
 */
public void clickLoginButton() {
    logger.info("Clicking login button");
    click(locators.loginButton, "Login Button");
}

/**
 * Perform login
 * @param username Username
 * @param password Password
 * @return Next page object
 */
public DashboardPage login(String username, String password) {
    logger.info("Performing login with username: {}", username);
    enterUsername(username);
    enterPassword(password);
    clickLoginButton();
    waitForPageLoad();
    return new DashboardPage(driver);
}
```

### 5. Page Verifications
Create methods to verify page state:
- Return boolean values
- Use `isDisplayed()`, `getText()` from BasePage
- Add logging
- Provide meaningful method names

```java
/**
 * Check if login page is loaded
 * @return true if login page is loaded
 */
public boolean isLoginPageLoaded() {
    logger.info("Checking if login page is loaded");
    return isDisplayed(locators.usernameField, "Username Field") && 
           isDisplayed(locators.passwordField, "Password Field") &&
           isDisplayed(locators.loginButton, "Login Button");
}

/**
 * Check if login is successful
 * @return true if login is successful
 */
public boolean isLoginSuccessful() {
    logger.info("Checking if login is successful");
    String currentUrl = driver.getCurrentUrl();
    return !currentUrl.contains("login");
}

/**
 * Get error message
 * @return Error message text
 */
public String getErrorMessage() {
    logger.info("Getting error message");
    return getText(locators.errorMessage, "Error Message");
}
```

### 6. Method Organization
Organize methods in this order:
1. **Constructor**
2. **Navigation methods** (if any)
3. **Action methods** (user interactions)
4. **Verification methods** (page state checks)
5. **Getter methods** (retrieve data)
6. **Utility methods** (page-specific helpers)

## Code Template

```java
package com.automation.pages;

import com.automation.locators.[PageName]Locators;
import com.automation.utils.ExtentReportManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * [Page Name] - Page Object Model
 * Represents the [page description] and its elements
 */
public class [PageName]Page extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger([PageName]Page.class);
    private [PageName]Locators locators;

    /**
     * Constructor
     * @param driver WebDriver instance
     */
    public [PageName]Page(WebDriver driver) {
        super(driver);
        this.locators = new [PageName]Locators();
        logger.info("[PageName]Page initialized");
    }

    // ==================== Action Methods ====================

    /**
     * [Action description]
     * @param [param] [Parameter description]
     */
    public void [actionMethod]([paramType] [paramName]) {
        logger.info("[Action description]");
        [implementation];
    }

    // ==================== Verification Methods ====================

    /**
     * [Verification description]
     * @return [Return value description]
     */
    public boolean [isVerification]() {
        logger.info("[Verification description]");
        return [implementation];
    }

    // ==================== Getter Methods ====================

    /**
     * [Getter description]
     * @return [Return value description]
     */
    public [returnType] [getMethod]() {
        logger.info("[Getter description]");
        return [implementation];
    }
}
```

## Best Practices

### DO's
✓ Extend BasePage for all page objects
✓ Use separate locator classes
✓ Use BasePage methods for element interactions
✓ Add logging for all actions
✓ Return page objects for navigation (method chaining)
✓ Keep methods focused on single responsibility
✓ Add Javadoc for all public methods
✓ Use descriptive method names

### DON'Ts
✗ Don't include test logic in page objects
✗ Don't use Thread.sleep()
✗ Don't hardcode locators in page methods
✗ Don't create dependencies between page objects
✗ Don't include assertions in page objects
✗ Don't expose WebDriver directly
✗ Don't create overly complex methods

## Example

### Input
```
Page Name: LoginPage
Page URL: /login
Elements: username field, password field, login button, error message, forgot password link
Actions: enter username, enter password, click login, click forgot password
Verifications: is login page loaded, is login successful, is error displayed
```

### Output
```java
package com.automation.pages;

import com.automation.locators.LoginPageLocators;
import com.automation.utils.ExtentReportManager;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Login Page - Page Object Model
 * Represents the login page and its elements
 */
public class LoginPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(LoginPage.class);
    private LoginPageLocators locators;

    /**
     * Constructor
     * @param driver WebDriver instance
     */
    public LoginPage(WebDriver driver) {
        super(driver);
        this.locators = new LoginPageLocators();
        logger.info("LoginPage initialized");
    }

    // ==================== Action Methods ====================

    /**
     * Enter username
     * @param username Username to enter
     */
    public void enterUsername(String username) {
        logger.info("Entering username: {}", username);
        enterText(locators.usernameField, username, "Username Field");
    }

    /**
     * Enter password
     * @param password Password to enter
     */
    public void enterPassword(String password) {
        logger.info("Entering password");
        enterText(locators.passwordField, password, "Password Field");
    }

    /**
     * Click login button
     */
    public void clickLoginButton() {
        logger.info("Clicking login button");
        click(locators.loginButton, "Login Button");
    }

    /**
     * Perform login
     * @param username Username
     * @param password Password
     * @return DashboardPage instance
     */
    public DashboardPage login(String username, String password) {
        logger.info("Performing login with username: {}", username);
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
        waitForPageLoad();
        return new DashboardPage(driver);
    }

    // ==================== Verification Methods ====================

    /**
     * Check if login page is loaded
     * @return true if login page is loaded
     */
    public boolean isLoginPageLoaded() {
        logger.info("Checking if login page is loaded");
        return isDisplayed(locators.usernameField, "Username Field") && 
               isDisplayed(locators.passwordField, "Password Field") &&
               isDisplayed(locators.loginButton, "Login Button");
    }

    /**
     * Check if login is successful
     * @return true if login is successful
     */
    public boolean isLoginSuccessful() {
        logger.info("Checking if login is successful");
        String currentUrl = driver.getCurrentUrl();
        return !currentUrl.contains("login");
    }

    /**
     * Get error message
     * @return Error message text
     */
    public String getErrorMessage() {
        logger.info("Getting error message");
        return getText(locators.errorMessage, "Error Message");
    }
}
```

## Additional Notes
- Always create both Page class and Locator class
- Use BasePage methods instead of direct WebDriver calls
- Keep page objects focused on single page functionality
- Return next page object for navigation methods
- Add comprehensive Javadoc comments
- Follow framework coding standards
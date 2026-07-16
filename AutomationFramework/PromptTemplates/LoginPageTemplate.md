# Login Page Object Template

## Role
You are an expert test automation engineer specializing in Page Object Model (POM) design pattern with Selenium WebDriver and Java.

## Task
Create a comprehensive Login Page Object class following framework standards.

## Context
- **Framework**: Selenium WebDriver + TestNG + Java 11
- **Pattern**: Page Object Model (POM)
- **Base Class**: Extends `BasePage`
- **Locators**: Stored in separate `LoginPageLocators` class
- **Package**: `com.automation.pages`

## Requirements

### Page Information
- **Page Name**: LoginPage
- **Page URL**: /login or base URL
- **Page Description**: Login page for user authentication

### Page Elements
- **Form Fields**: Username/Email field, Password field
- **Buttons**: Login button, Forgot password link
- **Messages**: Error message, Success message
- **Links**: Forgot password, Sign up link
- **Checkboxes**: Remember me checkbox

### Page Actions
- Enter username
- Enter password
- Click login button
- Click forgot password link
- Check remember me checkbox
- Perform complete login

### Page Verifications
- Check if login page is loaded
- Check if login is successful
- Check if error message is displayed
- Check if validation error is displayed

## Instructions

### 1. Create Locator Class First
Create `LoginPageLocators.java` in `src/main/java/com/automation/locators/`

```java
package com.automation.locators;

import org.openqa.selenium.By;

/**
 * Login Page Locators
 * All locators for LoginPage elements
 */
public class LoginPageLocators {
    
    // Form Fields
    public By usernameField = By.id("username");
    public By emailField = By.id("email");
    public By passwordField = By.id("password");
    
    // Buttons
    public By loginButton = By.cssSelector("button[type='submit']");
    public By forgotPasswordLink = By.linkText("Forgot Password?");
    
    // Checkboxes
    public By rememberMeCheckbox = By.id("remember-me");
    
    // Messages
    public By errorMessage = By.cssSelector(".error-message, .alert-danger");
    public By successMessage = By.cssSelector(".success-message, .alert-success");
    public By validationError = By.cssSelector(".validation-error, .field-error");
    
    // Links
    public By signUpLink = By.linkText("Sign Up");
    
    // Page Indicators
    public By loginForm = By.id("login-form");
    public By pageHeader = By.cssSelector("h1, h2");
}
```

### 2. Create Page Object Class
Create `LoginPage.java` in `src/main/java/com/automation/pages/`

## Complete Template

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
 * Handles all login-related operations
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
     * Enter username/email
     * @param username Username or email to enter
     */
    public void enterUsername(String username) {
        logger.info("Entering username: {}", username);
        enterText(locators.usernameField, username, "Username Field");
    }

    /**
     * Enter email
     * @param email Email to enter
     */
    public void enterEmail(String email) {
        logger.info("Entering email: {}", email);
        enterText(locators.emailField, email, "Email Field");
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
     * Click forgot password link
     */
    public void clickForgotPasswordLink() {
        logger.info("Clicking forgot password link");
        click(locators.forgotPasswordLink, "Forgot Password Link");
    }

    /**
     * Check remember me checkbox
     */
    public void checkRememberMe() {
        logger.info("Checking remember me checkbox");
        if (!isSelected(locators.rememberMeCheckbox, "Remember Me Checkbox")) {
            click(locators.rememberMeCheckbox, "Remember Me Checkbox");
        }
    }

    /**
     * Uncheck remember me checkbox
     */
    public void uncheckRememberMe() {
        logger.info("Unchecking remember me checkbox");
        if (isSelected(locators.rememberMeCheckbox, "Remember Me Checkbox")) {
            click(locators.rememberMeCheckbox, "Remember Me Checkbox");
        }
    }

    /**
     * Perform login with username and password
     * @param username Username or email
     * @param password Password
     * @return HomePage instance after successful login
     */
    public HomePage login(String username, String password) {
        logger.info("Performing login with username: {}", username);
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
        waitForPageLoad();
        return new HomePage(driver);
    }

    /**
     * Perform login with email and password
     * @param email Email
     * @param password Password
     * @return HomePage instance after successful login
     */
    public HomePage loginWithEmail(String email, String password) {
        logger.info("Performing login with email: {}", email);
        enterEmail(email);
        enterPassword(password);
        clickLoginButton();
        waitForPageLoad();
        return new HomePage(driver);
    }

    // ==================== Verification Methods ====================

    /**
     * Check if login page is loaded
     * @return true if login page is loaded
     */
    public boolean isLoginPageLoaded() {
        logger.info("Checking if login page is loaded");
        return isDisplayed(locators.loginForm, "Login Form") && 
               isDisplayed(locators.usernameField, "Username Field") && 
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
        return !currentUrl.contains("login") && !currentUrl.contains("signin");
    }

    /**
     * Check if error message is displayed
     * @return true if error message is displayed
     */
    public boolean isErrorMessageDisplayed() {
        logger.info("Checking if error message is displayed");
        return isDisplayed(locators.errorMessage, "Error Message");
    }

    /**
     * Check if validation error is displayed
     * @return true if validation error is displayed
     */
    public boolean isValidationErrorDisplayed() {
        logger.info("Checking if validation error is displayed");
        return isDisplayed(locators.validationError, "Validation Error");
    }

    /**
     * Check if forgot password page is loaded
     * @return true if forgot password page is loaded
     */
    public boolean isForgotPasswordPageLoaded() {
        logger.info("Checking if forgot password page is loaded");
        String currentUrl = driver.getCurrentUrl();
        return currentUrl.contains("forgot-password") || currentUrl.contains("reset-password");
    }

    // ==================== Getter Methods ====================

    /**
     * Get error message text
     * @return Error message text
     */
    public String getErrorMessage() {
        logger.info("Getting error message");
        return getText(locators.errorMessage, "Error Message");
    }

    /**
     * Get validation error text
     * @return Validation error text
     */
    public String getValidationError() {
        logger.info("Getting validation error");
        return getText(locators.validationError, "Validation Error");
    }

    /**
     * Get page header text
     * @return Page header text
     */
    public String getPageHeader() {
        logger.info("Getting page header");
        return getText(locators.pageHeader, "Page Header");
    }

    // ==================== Utility Methods ====================

    /**
     * Clear all fields
     */
    public void clearAllFields() {
        logger.info("Clearing all fields");
        clearField(locators.usernameField, "Username Field");
        clearField(locators.passwordField, "Password Field");
    }

    /**
     * Check if remember me is checked
     * @return true if remember me is checked
     */
    public boolean isRememberMeChecked() {
        logger.info("Checking if remember me is checked");
        return isSelected(locators.rememberMeCheckbox, "Remember Me Checkbox");
    }
}
```

## Usage Instructions

### How to Use This Template
1. Create the locator class first (`LoginPageLocators.java`)
2. Copy the complete page object code
3. Update locators to match your application's actual locators
4. Add/remove methods based on your application's functionality
5. Ensure all methods have proper logging and error handling

### Customization Points
- **Locators**: Update all By locators to match your application
- **Methods**: Add/remove action methods based on your page functionality
- **Verifications**: Add page state verification methods
- **Return Types**: Modify return types for navigation methods
- **Logging**: Adjust logging messages as needed

## Best Practices
- Extend BasePage for all page objects
- Use separate locator classes
- Use BasePage methods for element interactions
- Add logging for all actions
- Return page objects for navigation (method chaining)
- Keep methods focused on single responsibility
- Add Javadoc for all public methods
- Use descriptive method names
- Don't include test logic in page objects
- Don't use Thread.sleep()
- Don't hardcode locators in page methods
- Don't include assertions in page objects
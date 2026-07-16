# Login Feature Templates

## Role
You are an expert test automation engineer specializing in authentication and login functionality testing.

## Task
Generate complete test automation components for login functionality following the automation workflow.

## Context
- **Feature**: User Authentication - Login
- **Pages**: Login Page, Home Page (after login)
- **Scenarios**: Valid login, invalid login, empty fields, remember me
- **Framework**: Selenium + TestNG + POM

## Generated Components

### 1. Page Object: LoginPage.java

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
     * Click remember me checkbox
     */
    public void clickRememberMeCheckbox() {
        logger.info("Clicking remember me checkbox");
        click(locators.rememberMeCheckbox, "Remember Me Checkbox");
    }

    /**
     * Click forgot password link
     */
    public void clickForgotPasswordLink() {
        logger.info("Clicking forgot password link");
        click(locators.forgotPasswordLink, "Forgot Password Link");
    }

    /**
     * Perform login
     * @param username Username
     * @param password Password
     * @return HomePage instance
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
     * Perform login with remember me
     * @param username Username
     * @param password Password
     * @param rememberMe Remember me flag
     * @return HomePage instance
     */
    public HomePage login(String username, String password, boolean rememberMe) {
        logger.info("Performing login with username: {}, rememberMe: {}", username, rememberMe);
        enterUsername(username);
        enterPassword(password);
        if (rememberMe) {
            clickRememberMeCheckbox();
        }
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
     * Check if error message is displayed
     * @return true if error message is displayed
     */
    public boolean isErrorMessageDisplayed() {
        logger.info("Checking if error message is displayed");
        return isDisplayed(locators.errorMessage, "Error Message");
    }

    /**
     * Get error message
     * @return Error message text
     */
    public String getErrorMessage() {
        logger.info("Getting error message");
        return getText(locators.errorMessage, "Error Message");
    }

    /**
     * Check if remember me checkbox is checked
     * @return true if checkbox is checked
     */
    public boolean isRememberMeChecked() {
        logger.info("Checking if remember me checkbox is checked");
        return locators.rememberMeCheckbox.isSelected();
    }

    /**
     * Check if forgot password link is displayed
     * @return true if link is displayed
     */
    public boolean isForgotPasswordLinkDisplayed() {
        logger.info("Checking if forgot password link is displayed");
        return isDisplayed(locators.forgotPasswordLink, "Forgot Password Link");
    }
}
```

### 2. Locator Class: LoginPageLocators.java

```java
package com.automation.locators;

import org.openqa.selenium.By;

/**
 * Login Page Locators
 * Contains all element locators for the login page
 */
public class LoginPageLocators {
    
    // ==================== Form Fields ====================
    public By usernameField = By.id("username");
    public By passwordField = By.id("password");
    
    // ==================== Buttons ====================
    public By loginButton = By.cssSelector(".btn-login");
    public By submitButton = By.xpath("//button[text()='Login']");
    
    // ==================== Checkboxes ====================
    public By rememberMeCheckbox = By.id("remember-me");
    
    // ==================== Links ====================
    public By forgotPasswordLink = By.linkText("Forgot Password?");
    
    // ==================== Messages ====================
    public By errorMessage = By.cssSelector(".alert-danger");
    public By successMessage = By.cssSelector(".alert-success");
    
    // ==================== Other Elements ====================
    public By loginForm = By.id("login-form");
    public By pageHeading = By.xpath("//h2[text()='Login to your account']");
}
```

### 3. Test Class: LoginTest.java

```java
package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.pages.LoginPage;
import com.automation.utils.ConfigReader;
import com.automation.utils.WebDriverFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Login Test Class
 * Test cases for login functionality
 * Following Page Object Model - Test class only contains test logic
 */
public class LoginTest extends BaseTest {

    /**
     * Test successful login with valid credentials
     */
    @Test(description = "Verify successful login with valid credentials", 
          groups = {"smoke", "login"})
    public void testSuccessfulLogin() {
        logger.info("Starting test: Successful Login");

        // Navigate to login page
        navigateToBaseUrl();

        // Create page object
        LoginPage loginPage = new LoginPage(WebDriverFactory.getDriver());

        // Verify login page is loaded
        Assert.assertTrue(loginPage.isLoginPageLoaded(), 
            "Login page should be loaded");

        // Perform login
        HomePage homePage = loginPage.login(
            ConfigReader.getUsername(), 
            ConfigReader.getPassword()
        );

        // Verify login success
        Assert.assertTrue(loginPage.isLoginSuccessful(), 
            "Login should be successful with valid credentials");
        
        // Verify home page is loaded
        Assert.assertTrue(homePage.isHomePageLoaded(), 
            "Home page should be loaded after login");
    }

    /**
     * Test login with remember me option
     */
    @Test(description = "Verify login with remember me option", 
          groups = {"regression", "login"})
    public void testLoginWithRememberMe() {
        logger.info("Starting test: Login with Remember Me");

        // Navigate to login page
        navigateToBaseUrl();

        // Create page object
        LoginPage loginPage = new LoginPage(WebDriverFactory.getDriver());

        // Perform login with remember me
        HomePage homePage = loginPage.login(
            ConfigReader.getUsername(), 
            ConfigReader.getPassword(), 
            true
        );

        // Verify login success
        Assert.assertTrue(loginPage.isLoginSuccessful(), 
            "Login should be successful");
    }

    /**
     * Test login with invalid credentials
     */
    @Test(description = "Verify login fails with invalid credentials", 
          groups = {"regression", "login"})
    public void testInvalidLogin() {
        logger.info("Starting test: Invalid Login");

        // Navigate to login page
        navigateToBaseUrl();

        // Create page object
        LoginPage loginPage = new LoginPage(WebDriverFactory.getDriver());

        // Perform login with invalid credentials
        loginPage.login("invaliduser", "wrongpassword");

        // Verify error message
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), 
            "Error message should be displayed for invalid credentials");
        
        String errorMessage = loginPage.getErrorMessage();
        Assert.assertTrue(errorMessage.contains("Invalid credentials"), 
            "Error message should mention invalid credentials. Actual: " + errorMessage);
    }

    /**
     * Test login with empty username
     */
    @Test(description = "Verify login validation with empty username", 
          groups = {"regression", "login"})
    public void testLoginWithEmptyUsername() {
        logger.info("Starting test: Login with Empty Username");

        // Navigate to login page
        navigateToBaseUrl();

        // Create page object
        LoginPage loginPage = new LoginPage(WebDriverFactory.getDriver());

        // Try login with empty username
        loginPage.enterUsername("");
        loginPage.enterPassword("password123");
        loginPage.clickLoginButton();

        // Verify error or still on login page
        Assert.assertTrue(loginPage.isLoginPageLoaded(), 
            "Should remain on login page with empty username");
    }

    /**
     * Test login with empty password
     */
    @Test(description = "Verify login validation with empty password", 
          groups = {"regression", "login"})
    public void testLoginWithEmptyPassword() {
        logger.info("Starting test: Login with Empty Password");

        // Navigate to login page
        navigateToBaseUrl();

        // Create page object
        LoginPage loginPage = new LoginPage(WebDriverFactory.getDriver());

        // Try login with empty password
        loginPage.enterUsername("validuser");
        loginPage.enterPassword("");
        loginPage.clickLoginButton();

        // Verify error or still on login page
        Assert.assertTrue(loginPage.isLoginPageLoaded(), 
            "Should remain on login page with empty password");
    }

    /**
     * Test login with both fields empty
     */
    @Test(description = "Verify login validation with both fields empty", 
          groups = {"regression", "login"})
    public void testLoginWithBothFieldsEmpty() {
        logger.info("Starting test: Login with Both Fields Empty");

        // Navigate to login page
        navigateToBaseUrl();

        // Create page object
        LoginPage loginPage = new LoginPage(WebDriverFactory.getDriver());

        // Try login with both fields empty
        loginPage.enterUsername("");
        loginPage.enterPassword("");
        loginPage.clickLoginButton();

        // Verify error or still on login page
        Assert.assertTrue(loginPage.isLoginPageLoaded(), 
            "Should remain on login page with both fields empty");
    }

    /**
     * Test forgot password link navigation
     */
    @Test(description = "Verify forgot password link navigation", 
          groups = {"regression", "login"})
    public void testForgotPasswordLink() {
        logger.info("Starting test: Forgot Password Link");

        // Navigate to login page
        navigateToBaseUrl();

        // Create page object
        LoginPage loginPage = new LoginPage(WebDriverFactory.getDriver());

        // Verify forgot password link is displayed
        Assert.assertTrue(loginPage.isForgotPasswordLinkDisplayed(), 
            "Forgot password link should be displayed");

        // Click forgot password link
        loginPage.clickForgotPasswordLink();

        // Verify navigation (you can add more specific assertions)
        Assert.assertTrue(loginPage.isLoginPageLoaded(), 
            "Should navigate to forgot password page");
    }
}
```

### 4. Test Data: login-test-data.csv

```csv
username,password,expected_result,test_type,description
testuser,Test@123,success,positive,Valid credentials
invaliduser,wrongpass,failure,negative,Invalid password
,,failure,negative,Empty username
user1,,failure,negative,Empty password
test@example.com,pass123,success,positive,Valid email format
invalid-email,pass123,failure,negative,Invalid email format
user@test.com,short,failure,negative,Password too short
user@test.com,verylongpasswordthatexceedsmaximumlength,failure,negative,Password too long
```

### 5. Test Data: login-test-data.json

```json
{
  "validCredentials": [
    {
      "username": "testuser",
      "password": "Test@123",
      "expectedResult": "success",
      "description": "Standard valid credentials"
    },
    {
      "username": "user@example.com",
      "password": "P@ssw0rd!",
      "expectedResult": "success",
      "description": "Email as username with special characters"
    }
  ],
  "invalidCredentials": [
    {
      "username": "invaliduser",
      "password": "wrongpass",
      "expectedResult": "failure",
      "expectedError": "Invalid credentials",
      "description": "Invalid password"
    },
    {
      "username": "nonexistent@example.com",
      "password": "password123",
      "expectedResult": "failure",
      "expectedError": "Invalid credentials",
      "description": "Non-existent user"
    }
  ],
  "emptyFields": [
    {
      "username": "",
      "password": "password123",
      "expectedResult": "failure",
      "expectedError": "Username is required",
      "description": "Empty username"
    },
    {
      "username": "testuser",
      "password": "",
      "expectedResult": "failure",
      "expectedError": "Password is required",
      "description": "Empty password"
    },
    {
      "username": "",
      "password": "",
      "expectedResult": "failure",
      "expectedError": "Both fields are required",
      "description": "Both fields empty"
    }
  ],
  "edgeCases": [
    {
      "username": "user@test.com",
      "password": "P@1",
      "expectedResult": "failure",
      "description": "Very short password"
    },
    {
      "username": "a",
      "password": "password123",
      "expectedResult": "failure",
      "description": "Very short username"
    },
    {
      "username": "user_with_underscore",
      "password": "Pass@123",
      "expectedResult": "success",
      "description": "Username with underscore"
    }
  ]
}
```

### 6. Test Data: test-users.csv (for data-driven testing)

```csv
username,password,role,expected_result
admin,Admin@123,admin,success
user1,User@123,user,success
user2,User@456,user,success
testuser,Test@123,user,success
invalid,wrongpass,user,failure
```

## Usage Instructions

### Quick Start
1. Copy LoginPage.java to `src/main/java/com/automation/pages/`
2. Copy LoginPageLocators.java to `src/main/java/com/automation/locators/`
3. Copy LoginTest.java to `src/test/java/com/automation/tests/`
4. Copy test data files to `src/test/resources/test-data/`
5. Update locators based on your application
6. Run tests: `mvn test -Dtest=LoginTest`

### Customization
- Update locators in `LoginPageLocators.java` to match your application
- Update test data in CSV/JSON files
- Add more test methods as needed
- Update assertions based on your application's behavior

## Test Coverage

### Scenarios Covered
- ✓ Valid login
- ✓ Invalid login (wrong password)
- ✓ Empty username
- ✓ Empty password
- ✓ Both fields empty
- ✓ Remember me functionality
- ✓ Forgot password link

### Test Types
- Smoke tests: `testSuccessfulLogin`
- Regression tests: All other tests

## Integration

### Automatic Integration
- **Extent Reports**: Automatic via BaseTest
- **Screenshots**: Automatic on failure
- **Logging**: Automatic via SLF4J
- **WebDriver Management**: Automatic via WebDriverFactory

### Manual Configuration
- Update `config.properties` with valid credentials
- Update base URL in `config.properties`

## Best Practices

### DO's
✓ Test all login scenarios
✓ Use data-driven testing for multiple credentials
✓ Verify both positive and negative cases
✓ Check error messages
✓ Test edge cases
✓ Use descriptive test names
✓ Add meaningful assertions

### DON'Ts
✗ Don't hardcode credentials in tests
✗ Don't skip negative test cases
✗ Don't ignore error message validation
✗ Don't create dependencies between tests
✗ Don't use Thread.sleep()

## Additional Notes
- All tests are independent and can run in any order
- Tests automatically clean up WebDriver instances
- Screenshots are captured on failure
- Reports are generated automatically
- Test data can be easily extended
# Login Test Template

## Role
You are an expert test automation engineer specializing in Selenium WebDriver with TestNG and Java.

## Task
Create a comprehensive Login test following the Page Object Model (POM) pattern.

## Context
- **Framework**: Selenium WebDriver + TestNG + Java 11
- **Pattern**: Page Object Model (POM)
- **Base Class**: Extends `BaseTest`
- **Page Object**: Uses `LoginPage`

## Requirements

### Test Scenarios to Cover
1. **Positive Test**: Successful login with valid credentials
2. **Negative Test**: Login with invalid credentials
3. **Negative Test**: Login with empty credentials
4. **Edge Case**: Login with special characters in password

### Test Data
- Valid credentials from ConfigReader
- Invalid test data for negative scenarios
- Edge case data for boundary testing

## Instructions

### Test Class Structure
Create test class in `src/test/java/com/automation/tests/LoginTest.java`

### Test Methods to Implement

#### 1. testSuccessfulLogin()
```java
@Test(description = "Verify successful login with valid credentials", 
      groups = {"smoke", "login", "positive"}, 
      priority = 1)
public void testSuccessfulLogin() {
    logger.info("Starting test: Successful Login");
    
    // Navigate to login page
    navigateToBaseUrl();
    
    // Create page object
    LoginPage loginPage = new LoginPage(WebDriverFactory.getDriver());
    
    // Verify login page is loaded
    Assert.assertTrue(loginPage.isLoginPageLoaded(), 
        "Login page should be loaded");
    
    // Perform login with valid credentials
    loginPage.login(ConfigReader.getUsername(), ConfigReader.getPassword());
    
    // Verify login success
    Assert.assertTrue(loginPage.isLoginSuccessful(), 
        "Login should be successful with valid credentials");
    
    // Verify user is redirected to dashboard/home
    HomePage homePage = new HomePage(WebDriverFactory.getDriver());
    Assert.assertTrue(homePage.isHomePageLoaded(), 
        "User should be redirected to home page after login");
    
    logger.info("Test completed: Successful Login");
}
```

#### 2. testLoginWithInvalidCredentials()
```java
@Test(description = "Verify login fails with invalid credentials", 
      groups = {"regression", "login", "negative"}, 
      priority = 2)
public void testLoginWithInvalidCredentials() {
    logger.info("Starting test: Login with Invalid Credentials");
    
    // Navigate to login page
    navigateToBaseUrl();
    
    // Create page object
    LoginPage loginPage = new LoginPage(WebDriverFactory.getDriver());
    
    // Verify login page is loaded
    Assert.assertTrue(loginPage.isLoginPageLoaded(), 
        "Login page should be loaded");
    
    // Perform login with invalid credentials
    loginPage.login("invalid@example.com", "WrongPassword123");
    
    // Verify login failed
    Assert.assertFalse(loginPage.isLoginSuccessful(), 
        "Login should fail with invalid credentials");
    
    // Verify error message is displayed
    Assert.assertTrue(loginPage.isErrorMessageDisplayed(), 
        "Error message should be displayed for invalid credentials");
    
    String errorMessage = loginPage.getErrorMessage();
    Assert.assertNotNull(errorMessage, "Error message should not be null");
    Assert.assertFalse(errorMessage.isEmpty(), "Error message should not be empty");
    
    logger.info("Test completed: Login with Invalid Credentials");
}
```

#### 3. testLoginWithEmptyCredentials()
```java
@Test(description = "Verify login fails with empty credentials", 
      groups = {"regression", "login", "negative"}, 
      priority = 3)
public void testLoginWithEmptyCredentials() {
    logger.info("Starting test: Login with Empty Credentials");
    
    // Navigate to login page
    navigateToBaseUrl();
    
    // Create page object
    LoginPage loginPage = new LoginPage(WebDriverFactory.getDriver());
    
    // Verify login page is loaded
    Assert.assertTrue(loginPage.isLoginPageLoaded(), 
        "Login page should be loaded");
    
    // Try to login with empty credentials
    loginPage.clickLoginButton();
    
    // Verify login failed (stayed on login page)
    Assert.assertTrue(loginPage.isLoginPageLoaded(), 
        "Should remain on login page with empty credentials");
    
    // Verify validation messages or error indicators
    Assert.assertTrue(loginPage.isValidationErrorDisplayed(), 
        "Validation error should be displayed for empty fields");
    
    logger.info("Test completed: Login with Empty Credentials");
}
```

#### 4. testLoginWithSpecialCharacters()
```java
@Test(description = "Verify login handles special characters in password", 
      groups = {"regression", "login", "edgecase"}, 
      priority = 4)
public void testLoginWithSpecialCharacters() {
    logger.info("Starting test: Login with Special Characters");
    
    // Navigate to login page
    navigateToBaseUrl();
    
    // Create page object
    LoginPage loginPage = new LoginPage(WebDriverFactory.getDriver());
    
    // Verify login page is loaded
    Assert.assertTrue(loginPage.isLoginPageLoaded(), 
        "Login page should be loaded");
    
    // Perform login with special characters in password
    String specialPassword = "P@ssw0rd!#$%^&*()";
    loginPage.login("test@example.com", specialPassword);
    
    // Verify behavior (should fail or handle gracefully)
    // Adjust assertion based on application behavior
    Assert.assertFalse(loginPage.isLoginSuccessful(), 
        "Login should fail with invalid special character password");
    
    logger.info("Test completed: Login with Special Characters");
}
```

## Complete Template

```java
package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.pages.LoginPage;
import com.automation.pages.HomePage;
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
          groups = {"smoke", "login", "positive"}, 
          priority = 1)
    public void testSuccessfulLogin() {
        logger.info("Starting test: Successful Login");
        
        // Navigate to login page
        navigateToBaseUrl();
        
        // Create page object
        LoginPage loginPage = new LoginPage(WebDriverFactory.getDriver());
        
        // Verify login page is loaded
        Assert.assertTrue(loginPage.isLoginPageLoaded(), 
            "Login page should be loaded");
        
        // Perform login with valid credentials
        loginPage.login(ConfigReader.getUsername(), ConfigReader.getPassword());
        
        // Verify login success
        Assert.assertTrue(loginPage.isLoginSuccessful(), 
            "Login should be successful with valid credentials");
        
        // Verify user is redirected to dashboard/home
        HomePage homePage = new HomePage(WebDriverFactory.getDriver());
        Assert.assertTrue(homePage.isHomePageLoaded(), 
            "User should be redirected to home page after login");
        
        logger.info("Test completed: Successful Login");
    }

    /**
     * Test login with invalid credentials
     */
    @Test(description = "Verify login fails with invalid credentials", 
          groups = {"regression", "login", "negative"}, 
          priority = 2)
    public void testLoginWithInvalidCredentials() {
        logger.info("Starting test: Login with Invalid Credentials");
        
        // Navigate to login page
        navigateToBaseUrl();
        
        // Create page object
        LoginPage loginPage = new LoginPage(WebDriverFactory.getDriver());
        
        // Verify login page is loaded
        Assert.assertTrue(loginPage.isLoginPageLoaded(), 
            "Login page should be loaded");
        
        // Perform login with invalid credentials
        loginPage.login("invalid@example.com", "WrongPassword123");
        
        // Verify login failed
        Assert.assertFalse(loginPage.isLoginSuccessful(), 
            "Login should fail with invalid credentials");
        
        // Verify error message is displayed
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), 
            "Error message should be displayed for invalid credentials");
        
        String errorMessage = loginPage.getErrorMessage();
        Assert.assertNotNull(errorMessage, "Error message should not be null");
        Assert.assertFalse(errorMessage.isEmpty(), "Error message should not be empty");
        
        logger.info("Test completed: Login with Invalid Credentials");
    }

    /**
     * Test login with empty credentials
     */
    @Test(description = "Verify login fails with empty credentials", 
          groups = {"regression", "login", "negative"}, 
          priority = 3)
    public void testLoginWithEmptyCredentials() {
        logger.info("Starting test: Login with Empty Credentials");
        
        // Navigate to login page
        navigateToBaseUrl();
        
        // Create page object
        LoginPage loginPage = new LoginPage(WebDriverFactory.getDriver());
        
        // Verify login page is loaded
        Assert.assertTrue(loginPage.isLoginPageLoaded(), 
            "Login page should be loaded");
        
        // Try to login with empty credentials
        loginPage.clickLoginButton();
        
        // Verify login failed (stayed on login page)
        Assert.assertTrue(loginPage.isLoginPageLoaded(), 
            "Should remain on login page with empty credentials");
        
        // Verify validation messages or error indicators
        Assert.assertTrue(loginPage.isValidationErrorDisplayed(), 
            "Validation error should be displayed for empty fields");
        
        logger.info("Test completed: Login with Empty Credentials");
    }

    /**
     * Test login with special characters in password
     */
    @Test(description = "Verify login handles special characters in password", 
          groups = {"regression", "login", "edgecase"}, 
          priority = 4)
    public void testLoginWithSpecialCharacters() {
        logger.info("Starting test: Login with Special Characters");
        
        // Navigate to login page
        navigateToBaseUrl();
        
        // Create page object
        LoginPage loginPage = new LoginPage(WebDriverFactory.getDriver());
        
        // Verify login page is loaded
        Assert.assertTrue(loginPage.isLoginPageLoaded(), 
            "Login page should be loaded");
        
        // Perform login with special characters in password
        String specialPassword = "P@ssw0rd!#$%^&*()";
        loginPage.login("test@example.com", specialPassword);
        
        // Verify behavior (should fail or handle gracefully)
        // Adjust assertion based on application behavior
        Assert.assertFalse(loginPage.isLoginSuccessful(), 
            "Login should fail with invalid special character password");
        
        logger.info("Test completed: Login with Special Characters");
    }
}
```

## Usage Instructions

### How to Use This Template
1. Copy the complete template code
2. Replace placeholder values with actual values:
   - Update package name if different
   - Modify test data as needed
   - Adjust assertions based on application behavior
3. Ensure corresponding `LoginPage` and `HomePage` page objects exist
4. Update test groups and priorities as per your test strategy
5. Add/remove test methods based on your requirements

### Customization Points
- **Test Data**: Replace hardcoded values with ConfigReader or test data files
- **Assertions**: Adjust based on actual application behavior
- **Test Groups**: Modify groups to match your test management strategy
- **Priorities**: Set priorities based on test execution order requirements
- **Additional Tests**: Add more test methods for other scenarios (e.g., remember me, forgot password)

## Best Practices
- Keep tests independent and idempotent
- Use meaningful test method names
- Add descriptive assertion messages
- Log key test steps
- Group tests appropriately for execution
- Follow the Page Object Model pattern strictly
- Don't include element locators or WebDriver calls in test classes
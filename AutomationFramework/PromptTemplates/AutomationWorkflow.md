# Automation Generation Workflow

## Role
You are an expert test automation architect specializing in end-to-end test automation workflow design and generation.

## Task
Create a complete, automated workflow for generating all necessary test automation components for a given feature/scenario.

## Context
- **Framework**: Selenium WebDriver + TestNG + Java 11
- **Pattern**: Page Object Model (POM)
- **Goal**: Generate complete test automation suite for a feature
- **Output**: Page Objects, Locators, Tests, Test Data, and Reporting

## Workflow Overview

### Complete Feature Automation Workflow
When given a feature to automate (e.g., "Test login functionality"), generate:

1. **Page Object Class** - Encapsulates page logic and element interactions
2. **Locator Class** - Contains all element locators for the page
3. **Test Class** - Contains test methods with assertions
4. **Test Data** - Test data files (CSV/JSON) for data-driven testing
5. **Extent Reporting** - Automatic reporting integration (built into BaseTest)

## Workflow Steps

### Step 1: Analyze the Feature
**Input**: Feature description (e.g., "Test login functionality")

**Analysis**:
- Identify pages involved (Login Page, Home Page, etc.)
- Identify user actions (enter username, enter password, click login)
- Identify verifications (success message, error message, navigation)
- Identify test scenarios (valid login, invalid login, empty fields)

**Output**: Feature analysis document

### Step 2: Generate Page Object
**Template**: Use `CreatePageObject.md`

**Generate**:
- Page class with action methods
- Page class with verification methods
- Proper logging and error handling

**Example Output**: `LoginPage.java`

### Step 3: Generate Locator Class
**Template**: Use `CreateLocatorClass.md`

**Generate**:
- Locator class with all element locators
- Organized by element type
- Using best practices (ID > Name > CSS > XPath)

**Example Output**: `LoginPageLocators.java`

### Step 4: Generate Test Class
**Template**: Use `CreateSeleniumTest.md`

**Generate**:
- Test class extending BaseTest
- Multiple test methods for different scenarios
- Proper assertions with descriptive messages
- TestNG annotations with groups

**Example Output**: `LoginTest.java`

### Step 5: Generate Test Data
**Template**: Use `CreateTestData.md`

**Generate**:
- Test data files (CSV/JSON)
- Valid test data
- Invalid test data
- Edge cases

**Example Output**: `login-test-data.csv`

### Step 6: Integration
**Automatic**:
- Extent Reports (automatic via BaseTest)
- Screenshots on failure (automatic via BaseTest)
- Logging (automatic via SLF4J)
- WebDriver management (automatic via WebDriverFactory)

## Complete Workflow Example

### Input
```
Feature: Test login functionality
Pages: Login Page
Scenarios:
1. Valid login with correct credentials
2. Invalid login with wrong password
3. Login with empty username
4. Login with empty password
```

### Generated Output

#### 1. Page Object: LoginPage.java
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

    /**
     * Check if error message is displayed
     * @return true if error message is displayed
     */
    public boolean isErrorMessageDisplayed() {
        logger.info("Checking if error message is displayed");
        return isDisplayed(locators.errorMessage, "Error Message");
    }
}
```

#### 2. Locator Class: LoginPageLocators.java
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
    public By forgotPasswordLink = By.linkText("Forgot Password?");
    
    // ==================== Messages ====================
    public By errorMessage = By.cssSelector(".alert-danger");
    public By successMessage = By.cssSelector(".alert-success");
}
```

#### 3. Test Class: LoginTest.java
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

        // Perform login
        HomePage homePage = loginPage.login(
            ConfigReader.getUsername(), 
            ConfigReader.getPassword()
        );

        // Verify login success
        Assert.assertTrue(loginPage.isLoginSuccessful(), 
            "Login should be successful with valid credentials");
    }

    /**
     * Test login with invalid password
     */
    @Test(description = "Verify login fails with invalid password", 
          groups = {"regression", "login"})
    public void testInvalidLogin() {
        logger.info("Starting test: Invalid Login");

        // Navigate to login page
        navigateToBaseUrl();

        // Create page object
        LoginPage loginPage = new LoginPage(WebDriverFactory.getDriver());

        // Perform login with invalid credentials
        loginPage.login("validuser", "wrongpassword");

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
}
```

#### 4. Test Data: login-test-data.csv
```csv
username,password,expected_result,test_type
valid_user,ValidPass123,success,positive
invalid_user,wrongpass,failure,negative
,,failure,empty_username
user1,,failure,empty_password
test@example.com,pass123,success,positive
invalid-email,pass123,failure,invalid_email
```

#### 5. Test Data: login-test-data.json
```json
{
  "validCredentials": {
    "username": "testuser",
    "password": "Test@123",
    "expectedResult": "success"
  },
  "invalidCredentials": [
    {
      "username": "invaliduser",
      "password": "wrongpass",
      "expectedResult": "failure",
      "expectedError": "Invalid credentials"
    },
    {
      "username": "",
      "password": "pass",
      "expectedResult": "failure",
      "expectedError": "Username is required"
    },
    {
      "username": "user",
      "password": "",
      "expectedResult": "failure",
      "expectedError": "Password is required"
    }
  ],
  "edgeCases": [
    {
      "username": "user@example.com",
      "password": "P@ssw0rd!",
      "description": "Special characters in password"
    }
  ]
}
```

## Automated Generation Script

### Master Generation Script
```java
package com.automation.generator;

import java.io.File;
import java.io.FileWriter;

/**
 * Test Automation Generator
 * Generates complete test automation suite for a feature
 */
public class TestAutomationGenerator {
    
    private String featureName;
    private String packageName;
    private String outputDir;
    
    public TestAutomationGenerator(String featureName, String basePackage) {
        this.featureName = featureName;
        this.packageName = basePackage + "." + featureName.toLowerCase();
        this.outputDir = "src/main/java/com/automation/" + 
                        (featureName.equals("UI") ? "" : featureName.toLowerCase() + "/");
    }
    
    /**
     * Generate complete test automation suite
     */
    public void generateCompleteSuite() {
        String pageName = capitalizeFirst(featureName);
        
        // 1. Generate Page Object
        generatePageObject(pageName);
        
        // 2. Generate Locator Class
        generateLocatorClass(pageName);
        
        // 3. Generate Test Class
        generateTestClass(pageName);
        
        // 4. Generate Test Data
        generateTestData(pageName);
        
        System.out.println("Complete test suite generated for: " + featureName);
    }
    
    /**
     * Generate Page Object class
     */
    private void generatePageObject(String pageName) {
        String content = generatePageObjectContent(pageName);
        writeToFile(outputDir + "pages/" + pageName + "Page.java", content);
    }
    
    /**
     * Generate Locator class
     */
    private void generateLocatorClass(String pageName) {
        String content = generateLocatorClassContent(pageName);
        writeToFile(outputDir + "locators/" + pageName + "Locators.java", content);
    }
    
    /**
     * Generate Test class
     */
    private void generateTestClass(String pageName) {
        String content = generateTestClassContent(pageName);
        writeToFile("src/test/java/com/automation/tests/" + pageName + "Test.java", content);
    }
    
    /**
     * Generate Test Data
     */
    private void generateTestData(String pageName) {
        // Generate CSV
        String csvContent = generateCsvTestData(pageName);
        writeToFile("src/test/resources/test-data/" + 
                    pageName.toLowerCase() + "-test-data.csv", csvContent);
        
        // Generate JSON
        String jsonContent = generateJsonTestData(pageName);
        writeToFile("src/test/resources/test-data/" + 
                    pageName.toLowerCase() + "-test-data.json", jsonContent);
    }
    
    // Helper methods for content generation...
    private String generatePageObjectContent(String pageName) {
        return "// Page Object content generated here";
    }
    
    private String generateLocatorClassContent(String pageName) {
        return "// Locator class content generated here";
    }
    
    private String generateTestClassContent(String pageName) {
        return "// Test class content generated here";
    }
    
    private String generateCsvTestData(String pageName) {
        return "// CSV test data content generated here";
    }
    
    private String generateJsonTestData(String pageName) {
        return "// JSON test data content generated here";
    }
    
    private void writeToFile(String filePath, String content) {
        try {
            File file = new File(filePath);
            file.getParentFile().mkdirs();
            FileWriter writer = new FileWriter(file);
            writer.write(content);
            writer.close();
            System.out.println("Generated: " + filePath);
        } catch (Exception e) {
            System.err.println("Error generating file: " + filePath);
            e.printStackTrace();
        }
    }
    
    private String capitalizeFirst(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
```

### Usage Example
```java
// Generate complete login test suite
public class GenerateLoginTests {
    public static void main(String[] args) {
        TestAutomationGenerator generator = 
            new TestAutomationGenerator("Login", "com.automation");
        
        generator.generateCompleteSuite();
        
        // Output:
        // Generated: src/main/java/com/automation/pages/LoginPage.java
        // Generated: src/main/java/com/automation/locators/LoginPageLocators.java
        // Generated: src/test/java/com/automation/tests/LoginTest.java
        // Generated: src/test/resources/test-data/login-test-data.csv
        // Generated: src/test/resources/test-data/login-test-data.json
    }
}
```

## Workflow Templates by Feature Type

### Template 1: Authentication Feature
**Pages**: Login, Logout, Forgot Password, Reset Password

**Generate**:
- LoginPage.java + LoginPageLocators.java
- LogoutPage.java + LogoutPageLocators.java
- ForgotPasswordPage.java + ForgotPasswordPageLocators.java
- LoginTest.java
- LogoutTest.java
- ForgotPasswordTest.java
- Test data: credentials, test users

### Template 2: E-commerce Feature
**Pages**: Home, Product, Cart, Checkout, Order Confirmation

**Generate**:
- HomePage.java + HomePageLocators.java
- ProductPage.java + ProductPageLocators.java
- CartPage.java + CartPageLocators.java
- CheckoutPage.java + CheckoutPageLocators.java
- HomeTest.java
- ProductTest.java
- CartTest.java
- CheckoutTest.java
- Test data: products, users, payment methods

### Template 3: User Management Feature
**Pages**: User List, User Profile, User Registration

**Generate**:
- UserListPage.java + UserListPageLocators.java
- UserProfilePage.java + UserProfilePageLocators.java
- UserRegistrationPage.java + UserRegistrationPageLocators.java
- UserManagementTest.java
- Test data: user profiles, permissions

### Template 4: Search Feature
**Pages**: Home, Search Results

**Generate**:
- HomePage.java + HomePageLocators.java
- SearchResultsPage.java + SearchResultsPageLocators.java
- SearchTest.java
- Test data: search terms, expected results

## Complete Feature Generation Checklist

### For Any Feature, Generate:

- [ ] **Page Objects**
  - [ ] One Page class per page
  - [ ] One Locator class per page
  - [ ] Action methods for all user interactions
  - [ ] Verification methods for all validations
  - [ ] Proper logging in all methods

- [ ] **Test Classes**
  - [ ] One Test class per feature
  - [ ] Test methods for all scenarios
  - [ ] Positive test cases
  - [ ] Negative test cases
  - [ ] Edge cases
  - [ ] Proper TestNG annotations
  - [ ] Descriptive assertion messages

- [ ] **Test Data**
  - [ ] CSV file with test data
  - [ ] JSON file with test data
  - [ ] Valid test data
  - [ ] Invalid test data
  - [ ] Edge case data

- [ ] **Integration**
  - [ ] Extent Reports (automatic)
  - [ ] Screenshots on failure (automatic)
  - [ ] Logging (automatic)
  - [ ] WebDriver management (automatic)

## Best Practices

### DO's
✓ Follow the workflow systematically
✓ Generate all components together
✓ Use templates for consistency
✓ Include comprehensive test data
✓ Add proper logging
✓ Include both positive and negative tests
✓ Follow naming conventions
✓ Document generated code

### DON'Ts
✗ Don't generate components individually (use workflow)
✗ Don't skip test data generation
✗ Don't forget edge cases
✗ Don't hardcode values
✗ Don't skip verification methods
✗ Don't create dependencies between tests

## Quick Start

### Generate Complete Feature in 3 Steps

1. **Analyze**: Identify pages, actions, and verifications
2. **Generate**: Use generator script or templates
3. **Verify**: Run generated tests and validate

### Example: Generate Login Feature
```bash
# Step 1: Analyze feature
Feature: Login
Pages: Login Page
Actions: enter username, enter password, click login
Verifications: success, error message

# Step 2: Generate
Use CreatePageObject.md → LoginPage.java
Use CreateLocatorClass.md → LoginPageLocators.java
Use CreateSeleniumTest.md → LoginTest.java
Use CreateTestData.md → login-test-data.csv

# Step 3: Verify
mvn test -Dtest=LoginTest
```

## Additional Notes
- This workflow ensures consistency across all features
- All generated code follows framework standards
- Templates can be customized for specific needs
- Generator script can be enhanced with more features
- Consider adding code generation tools like JHipster, Telosys, or custom templates
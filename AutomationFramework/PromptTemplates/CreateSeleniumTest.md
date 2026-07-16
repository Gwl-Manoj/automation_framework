# Create Selenium Test

## Role
You are an expert test automation engineer specializing in Selenium WebDriver with TestNG and Java.

## Task
Create a comprehensive Selenium test following the Page Object Model (POM) pattern and framework standards.

## Context
- **Framework**: Selenium WebDriver + TestNG + Java 11
- **Pattern**: Page Object Model (POM)
- **Base Class**: All tests extend `BaseTest`
- **Page Objects**: All interactions through page object methods
- **Assertions**: Use TestNG assertions with descriptive messages

## Requirements

### Test Information
- **Test Name**: [Provide test name]
- **Test Description**: [Provide description for TestNG @Test annotation]
- **Test Groups**: [e.g., smoke, regression, ui, login, contact]
- **Priority**: [1-10, if applicable]

### Test Scenario
[Describe the test scenario in detail, including:]
1. Preconditions
2. Test steps
3. Expected results
4. Any special conditions or edge cases

### Page Objects Involved
[List the page objects needed, e.g., LoginPage, ContactPage, DashboardPage]

### Test Data Required
[List any test data needed, e.g., username, password, email, etc.]

## Instructions

### 1. Test Class Structure
Create a test class that:
- Extends `BaseTest`
- Is placed in `src/test/java/com/automation/tests/` package
- Follows naming convention: `[Feature]Test.java`
- Contains only test methods (no implementation logic)

### 2. Test Method Structure
Each test method should:
- Have clear descriptive name: `test[Scenario][Condition]()`
- Include TestNG annotations: `@Test(description = "...", groups = {...})`
- Follow this pattern:
  ```java
  @Test(description = "...", groups = {...})
  public void testMethodName() {
      // 1. Navigate/Setup
      navigateToBaseUrl();
      
      // 2. Create page objects
      LoginPage loginPage = new LoginPage(WebDriverFactory.getDriver());
      
      // 3. Execute actions using page objects
      loginPage.enterUsername("test@example.com");
      loginPage.enterPassword("password123");
      loginPage.clickLoginButton();
      
      // 4. Assert results
      Assert.assertTrue(loginPage.isLoginSuccessful(), 
          "Login should be successful");
  }
  ```

### 3. Page Object Usage
- Create page object instances using `WebDriverFactory.getDriver()`
- Call only page object methods (no direct element interactions)
- Use page object methods for all actions and verifications
- Chain methods where appropriate (e.g., `loginPage.login(username, password)`)

### 4. Assertions
- Use TestNG assertions: `Assert.assertTrue()`, `Assert.assertEquals()`, etc.
- Always provide descriptive assertion messages
- Verify both positive and negative scenarios
- Include multiple assertions for comprehensive validation

### 5. Logging
- Use `logger.info()` for test progress
- Log key actions and decisions
- Don't over-log (avoid clutter)

### 6. Error Handling
- Use try-catch for expected exceptions
- Use safe operations for non-critical elements
- Let critical failures fail the test

## Code Template

```java
package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.pages.[PageName];
import com.automation.utils.ConfigReader;
import com.automation.utils.WebDriverFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * [Feature] Test Class
 * Test cases for [feature] functionality
 * Following Page Object Model - Test class only contains test logic
 */
public class [Feature]Test extends BaseTest {

    /**
     * Test [scenario description]
     */
    @Test(description = "[Detailed test description]", 
          groups = {"[group1]", "[group2]"}, 
          priority = [1-10])
    public void test[Scenario]() {
        logger.info("Starting test: [Test Name]");

        // Step 1: Navigate to URL
        navigateToBaseUrl();

        // Step 2: Create page object
        [PageName] page = new [PageName](WebDriverFactory.getDriver());

        // Step 3: Execute test steps
        // [Add your test steps here]

        // Step 4: Verify results
        Assert.assertTrue([condition], 
            "[Descriptive assertion message]");
    }
}
```

## Best Practices

### DO's
✓ Follow POM pattern strictly
✓ Use page object methods only
✓ Write descriptive test method names
✓ Add meaningful assertion messages
✓ Log important test steps
✓ Keep tests independent
✓ Use test data from config or test data files
✓ Group tests appropriately

### DON'Ts
✗ Don't include element interaction logic in tests
✗ Don't use Thread.sleep()
✗ Don't hardcode URLs or credentials
✗ Don't create dependencies between tests
✗ Don't use generic assertion messages
✗ Don't over-log
✗ Don't catch generic exceptions unnecessarily

## Example

### Input
```
Test Name: LoginTest
Scenario: Test successful login with valid credentials
Page Objects: LoginPage
Test Data: username=test@example.com, password=Test@123
Groups: smoke, login
```

### Output
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
        loginPage.login(ConfigReader.getUsername(), ConfigReader.getPassword());

        // Verify login success
        Assert.assertTrue(loginPage.isLoginSuccessful(), 
            "Login should be successful with valid credentials");
    }
}
```

## Additional Notes
- Ensure all imports are correct
- Follow the framework's coding standards
- Use meaningful variable names
- Keep methods focused and concise
- Add Javadoc comments for public methods
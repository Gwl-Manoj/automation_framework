# User Registration Feature Templates

## Role
You are an expert test automation engineer specializing in user registration and account creation testing.

## Task
Generate complete test automation components for user registration functionality following the automation workflow.

## Context
- **Feature**: User Registration and Account Creation
- **Pages**: Registration Page, Login Page, Home Page
- **Scenarios**: Valid registration, invalid email, password mismatch, duplicate email, empty fields
- **Framework**: Selenium + TestNG + POM

## Generated Components

### 1. Page Object: RegistrationPage.java

```java
package com.automation.pages;

import com.automation.locators.RegistrationPageLocators;
import com.automation.utils.ExtentReportManager;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Registration Page - Page Object Model
 * Represents the user registration page and its elements
 */
public class RegistrationPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(RegistrationPage.class);
    private RegistrationPageLocators locators;

    /**
     * Constructor
     * @param driver WebDriver instance
     */
    public RegistrationPage(WebDriver driver) {
        super(driver);
        this.locators = new RegistrationPageLocators();
        logger.info("RegistrationPage initialized");
    }

    // ==================== Action Methods ====================

    /**
     * Enter first name
     * @param firstName First name
     */
    public void enterFirstName(String firstName) {
        logger.info("Entering first name: {}", firstName);
        enterText(locators.firstNameField, firstName, "First Name Field");
    }

    /**
     * Enter last name
     * @param lastName Last name
     */
    public void enterLastName(String lastName) {
        logger.info("Entering last name: {}", lastName);
        enterText(locators.lastNameField, lastName, "Last Name Field");
    }

    /**
     * Enter email
     * @param email Email address
     */
    public void enterEmail(String email) {
        logger.info("Entering email: {}", email);
        enterText(locators.emailField, email, "Email Field");
    }

    /**
     * Enter password
     * @param password Password
     */
    public void enterPassword(String password) {
        logger.info("Entering password");
        enterText(locators.passwordField, password, "Password Field");
    }

    /**
     * Enter confirm password
     * @param confirmPassword Confirm password
     */
    public void enterConfirmPassword(String confirmPassword) {
        logger.info("Entering confirm password");
        enterText(locators.confirmPasswordField, confirmPassword, "Confirm Password Field");
    }

    /**
     * Enter phone number
     * @param phone Phone number
     */
    public void enterPhone(String phone) {
        logger.info("Entering phone: {}", phone);
        enterText(locators.phoneField, phone, "Phone Field");
    }

    /**
     * Check newsletter checkbox
     * @param check Whether to check or not
     */
    public void checkNewsletter(boolean check) {
        logger.info("Checking newsletter: {}", check);
        if (check) {
            if (!locators.newsletterCheckbox.isSelected()) {
                click(locators.newsletterCheckbox, "Newsletter Checkbox");
            }
        } else {
            if (locators.newsletterCheckbox.isSelected()) {
                click(locators.newsletterCheckbox, "Newsletter Checkbox");
            }
        }
    }

    /**
     * Check terms checkbox
     * @param check Whether to check or not
     */
    public void checkTerms(boolean check) {
        logger.info("Checking terms: {}", check);
        if (check) {
            if (!locators.termsCheckbox.isSelected()) {
                click(locators.termsCheckbox, "Terms Checkbox");
            }
        } else {
            if (locators.termsCheckbox.isSelected()) {
                click(locators.termsCheckbox, "Terms Checkbox");
            }
        }
    }

    /**
     * Click sign up button
     * @return HomePage instance
     */
    public HomePage clickSignUpButton() {
        logger.info("Clicking sign up button");
        click(locators.signUpButton, "Sign Up Button");
        waitForPageLoad();
        return new HomePage(driver);
    }

    /**
     * Click login link
     * @return LoginPage instance
     */
    public LoginPage clickLoginLink() {
        logger.info("Clicking login link");
        click(locators.loginLink, "Login Link");
        waitForPageLoad();
        return new LoginPage(driver);
    }

    /**
     * Perform registration
     * @param firstName First name
     * @param lastName Last name
     * @param email Email
     * @param password Password
     * @param confirmPassword Confirm password
     * @return HomePage instance
     */
    public HomePage register(String firstName, String lastName, String email, 
                            String password, String confirmPassword) {
        logger.info("Registering user: {}", email);
        enterFirstName(firstName);
        enterLastName(lastName);
        enterEmail(email);
        enterPassword(password);
        enterConfirmPassword(confirmPassword);
        checkTerms(true);
        clickSignUpButton();
        return new HomePage(driver);
    }

    /**
     * Perform registration with all fields
     */
    public HomePage register(String firstName, String lastName, String email, 
                            String password, String confirmPassword, 
                            String phone, boolean newsletter) {
        logger.info("Registering user with all fields: {}", email);
        enterFirstName(firstName);
        enterLastName(lastName);
        enterEmail(email);
        enterPhone(phone);
        enterPassword(password);
        enterConfirmPassword(confirmPassword);
        checkNewsletter(newsletter);
        checkTerms(true);
        clickSignUpButton();
        return new HomePage(driver);
    }

    // ==================== Verification Methods ====================

    /**
     * Check if registration page is loaded
     * @return true if registration page is loaded
     */
    public boolean isRegistrationPageLoaded() {
        logger.info("Checking if registration page is loaded");
        return isDisplayed(locators.signUpButton, "Sign Up Button") &&
               isDisplayed(locators.firstNameField, "First Name Field");
    }

    /**
     * Check if registration is successful
     * @return true if registration is successful
     */
    public boolean isRegistrationSuccessful() {
        logger.info("Checking if registration is successful");
        String currentUrl = driver.getCurrentUrl();
        return !currentUrl.contains("signup") && !currentUrl.contains("register");
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
     * Check if email already exists error is displayed
     * @return true if email exists error is displayed
     */
    public boolean isEmailExistsErrorDisplayed() {
        logger.info("Checking if email already exists error is displayed");
        String errorMessage = getErrorMessage();
        return errorMessage.toLowerCase().contains("already") || 
               errorMessage.toLowerCase().contains("exists");
    }

    /**
     * Check if password mismatch error is displayed
     * @return true if password mismatch error is displayed
     */
    public boolean isPasswordMismatchErrorDisplayed() {
        logger.info("Checking if password mismatch error is displayed");
        String errorMessage = getErrorMessage();
        return errorMessage.toLowerCase().contains("password") && 
               errorMessage.toLowerCase().contains("match");
    }

    /**
     * Check if terms checkbox is displayed
     * @return true if terms checkbox is displayed
     */
    public boolean isTermsCheckboxDisplayed() {
        logger.info("Checking if terms checkbox is displayed");
        return isDisplayed(locators.termsCheckbox, "Terms Checkbox");
    }

    /**
     * Check if newsletter checkbox is displayed
     * @return true if newsletter checkbox is displayed
     */
    public boolean isNewsletterCheckboxDisplayed() {
        logger.info("Checking if newsletter checkbox is displayed");
        return isDisplayed(locators.newsletterCheckbox, "Newsletter Checkbox");
    }
}
```

### 2. Locator Class: RegistrationPageLocators.java

```java
package com.automation.locators;

import org.openqa.selenium.By;

/**
 * Registration Page Locators
 * Contains all element locators for the registration page
 */
public class RegistrationPageLocators {
    
    // ==================== Form Fields ====================
    public By firstNameField = By.id("first-name");
    public By lastNameField = By.id("last-name");
    public By emailField = By.id("email");
    public By passwordField = By.id("password");
    public By confirmPasswordField = By.id("confirm-password");
    public By phoneField = By.id("phone");
    
    // ==================== Checkboxes ====================
    public By newsletterCheckbox = By.id("newsletter");
    public By termsCheckbox = By.id("terms");
    
    // ==================== Buttons ====================
    public By signUpButton = By.cssSelector(".btn-signup");
    public By submitButton = By.xpath("//button[text()='Sign Up']");
    
    // ==================== Links ====================
    public By loginLink = By.linkText("Login");
    public By alreadyHaveAccountLink = By.linkText("Already have an account?");
    
    // ==================== Messages ====================
    public By errorMessage = By.cssSelector(".alert-danger");
    public By successMessage = By.cssSelector(".alert-success");
    public By emailExistsError = By.cssSelector(".email-exists-error");
    
    // ==================== Other Elements ====================
    public By registrationForm = By.id("registration-form");
    public By pageHeading = By.xpath("//h2[text()='Sign Up']");
    public By passwordStrengthIndicator = By.cssSelector(".password-strength");
}
```

### 3. Test Class: UserRegistrationTest.java

```java
package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.pages.HomePage;
import com.automation.pages.LoginPage;
import com.automation.pages.RegistrationPage;
import com.automation.utils.WebDriverFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * User Registration Test Class
 * Test cases for user registration functionality
 * Following Page Object Model - Test class only contains test logic
 */
public class UserRegistrationTest extends BaseTest {

    /**
     * Test successful user registration
     */
    @Test(description = "Verify successful user registration with valid data", 
          groups = {"smoke", "registration"})
    public void testSuccessfulRegistration() {
        logger.info("Starting test: Successful User Registration");

        // Navigate to registration page
        navigateToBaseUrl();

        // Create page object
        RegistrationPage registrationPage = new RegistrationPage(WebDriverFactory.getDriver());

        // Verify registration page is loaded
        Assert.assertTrue(registrationPage.isRegistrationPageLoaded(), 
            "Registration page should be loaded");

        // Perform registration
        String firstName = "John";
        String lastName = "Doe";
        String email = "testuser" + System.currentTimeMillis() + "@example.com";
        String password = "Test@123";
        String confirmPassword = "Test@123";

        HomePage homePage = registrationPage.register(
            firstName, lastName, email, password, confirmPassword
        );

        // Verify registration successful
        Assert.assertTrue(registrationPage.isRegistrationSuccessful(), 
            "Registration should be successful");

        // Verify home page is loaded
        Assert.assertTrue(homePage.isHomePageLoaded(), 
            "Home page should be loaded after registration");
    }

    /**
     * Test registration with existing email
     */
    @Test(description = "Verify registration fails with existing email", 
          groups = {"regression", "registration"})
    public void testRegistrationWithExistingEmail() {
        logger.info("Starting test: Registration with Existing Email");

        // Navigate to registration page
        navigateToBaseUrl();

        // Create page object
        RegistrationPage registrationPage = new RegistrationPage(WebDriverFactory.getDriver());

        // Use a pre-existing email (you need to have a test user created)
        String existingEmail = "existinguser@example.com";

        // Try to register with existing email
        registrationPage.register(
            "John", "Doe", existingEmail, "Test@123", "Test@123"
        );

        // Verify error message
        Assert.assertTrue(registrationPage.isErrorMessageDisplayed(), 
            "Error message should be displayed for existing email");
        
        Assert.assertTrue(registrationPage.isEmailExistsErrorDisplayed(), 
            "Email already exists error should be displayed");
    }

    /**
     * Test registration with password mismatch
     */
    @Test(description = "Verify registration fails with password mismatch", 
          groups = {"regression", "registration"})
    public void testRegistrationWithPasswordMismatch() {
        logger.info("Starting test: Registration with Password Mismatch");

        // Navigate to registration page
        navigateToBaseUrl();

        // Create page object
        RegistrationPage registrationPage = new RegistrationPage(WebDriverFactory.getDriver());

        // Try to register with mismatched passwords
        registrationPage.register(
            "John", "Doe", "testuser@example.com", "Test@123", "Test@456"
        );

        // Verify error message
        Assert.assertTrue(registrationPage.isErrorMessageDisplayed(), 
            "Error message should be displayed for password mismatch");
        
        Assert.assertTrue(registrationPage.isPasswordMismatchErrorDisplayed(), 
            "Password mismatch error should be displayed");
    }

    /**
     * Test registration with empty required fields
     */
    @Test(description = "Verify registration validation with empty required fields", 
          groups = {"regression", "registration"})
    public void testRegistrationWithEmptyFields() {
        logger.info("Starting test: Registration with Empty Fields");

        // Navigate to registration page
        navigateToBaseUrl();

        // Create page object
        RegistrationPage registrationPage = new RegistrationPage(WebDriverFactory.getDriver());

        // Try to register with empty fields
        registrationPage.register("", "", "", "", "");

        // Should remain on registration page
        Assert.assertTrue(registrationPage.isRegistrationPageLoaded(), 
            "Should remain on registration page with empty fields");
    }

    /**
     * Test registration with invalid email format
     */
    @Test(description = "Verify registration validation with invalid email format", 
          groups = {"regression", "registration"})
    public void testRegistrationWithInvalidEmail() {
        logger.info("Starting test: Registration with Invalid Email");

        // Navigate to registration page
        navigateToBaseUrl();

        // Create page object
        RegistrationPage registrationPage = new RegistrationPage(WebDriverFactory.getDriver());

        // Try to register with invalid email
        registrationPage.register(
            "John", "Doe", "invalid-email", "Test@123", "Test@123"
        );

        // Verify error or validation
        Assert.assertTrue(registrationPage.isRegistrationPageLoaded(), 
            "Should remain on registration page with invalid email");
    }

    /**
     * Test registration with weak password
     */
    @Test(description = "Verify registration validation with weak password", 
          groups = {"regression", "registration"})
    public void testRegistrationWithWeakPassword() {
        logger.info("Starting test: Registration with Weak Password");

        // Navigate to registration page
        navigateToBaseUrl();

        // Create page object
        RegistrationPage registrationPage = new RegistrationPage(WebDriverFactory.getDriver());

        // Try to register with weak password
        registrationPage.register(
            "John", "Doe", "testuser@example.com", "123", "123"
        );

        // Verify error or validation
        Assert.assertTrue(registrationPage.isRegistrationPageLoaded(), 
            "Should remain on registration page with weak password");
    }

    /**
     * Test registration without accepting terms
     */
    @Test(description = "Verify registration fails without accepting terms", 
          groups = {"regression", "registration"})
    public void testRegistrationWithoutAcceptingTerms() {
        logger.info("Starting test: Registration without Accepting Terms");

        // Navigate to registration page
        navigateToBaseUrl();

        // Create page object
        RegistrationPage registrationPage = new RegistrationPage(WebDriverFactory.getDriver());

        // Try to register without accepting terms
        registrationPage.enterFirstName("John");
        registrationPage.enterLastName("Doe");
        registrationPage.enterEmail("testuser@example.com");
        registrationPage.enterPassword("Test@123");
        registrationPage.enterConfirmPassword("Test@123");
        // Don't check terms
        registrationPage.clickSignUpButton();

        // Verify error or validation
        Assert.assertTrue(registrationPage.isRegistrationPageLoaded(), 
            "Should remain on registration page without accepting terms");
    }

    /**
     * Test navigation to login page
     */
    @Test(description = "Verify navigation to login page from registration", 
          groups = {"regression", "registration"})
    public void testNavigationToLogin() {
        logger.info("Starting test: Navigation to Login Page");

        // Navigate to registration page
        navigateToBaseUrl();

        // Create page object
        RegistrationPage registrationPage = new RegistrationPage(WebDriverFactory.getDriver());

        // Click login link
        LoginPage loginPage = registrationPage.clickLoginLink();

        // Verify navigation to login page
        Assert.assertTrue(loginPage.isLoginPageLoaded(), 
            "Should navigate to login page");
    }

    /**
     * Test registration with newsletter subscription
     */
    @Test(description = "Verify registration with newsletter subscription", 
          groups = {"regression", "registration"})
    public void testRegistrationWithNewsletter() {
        logger.info("Starting test: Registration with Newsletter Subscription");

        // Navigate to registration page
        navigateToBaseUrl();

        // Create page object
        RegistrationPage registrationPage = new RegistrationPage(WebDriverFactory.getDriver());

        // Register with newsletter subscription
        String email = "testuser" + System.currentTimeMillis() + "@example.com";
        HomePage homePage = registrationPage.register(
            "John", "Doe", email, "Test@123", "Test@123", 
            "1234567890", true
        );

        // Verify registration successful
        Assert.assertTrue(registrationPage.isRegistrationSuccessful(), 
            "Registration should be successful");
    }
}
```

### 4. Test Data: registration-test-data.csv

```csv
first_name,last_name,email,password,confirm_password,phone,newsletter,expected_result,test_type
John,Doe,testuser1@example.com,Test@123,Test@123,1234567890,true,success,positive
Jane,Smith,testuser2@example.com,Pass@456,Pass@456,9876543210,false,success,positive
,,testuser3@example.com,Test@123,Test@123,1234567890,false,failure,empty_fields
John,Doe,invalid-email,Test@123,Test@123,1234567890,false,failure,invalid_email
John,Doe,testuser4@example.com,123,123,1234567890,false,failure,weak_password
John,Doe,testuser5@example.com,Test@123,Test@456,1234567890,false,failure,password_mismatch
John,Doe,existing@example.com,Test@123,Test@123,1234567890,false,failure,email_exists
```

### 5. Test Data: registration-test-data.json

```json
{
  "validUsers": [
    {
      "firstName": "John",
      "lastName": "Doe",
      "email": "john.doe@example.com",
      "password": "Test@123",
      "confirmPassword": "Test@123",
      "phone": "1234567890",
      "newsletter": true,
      "expectedResult": "success"
    },
    {
      "firstName": "Jane",
      "lastName": "Smith",
      "email": "jane.smith@example.com",
      "password": "Pass@456",
      "confirmPassword": "Pass@456",
      "phone": "9876543210",
      "newsletter": false,
      "expectedResult": "success"
    }
  ],
  "invalidEmails": [
    "invalid-email",
    "test@",
    "@example.com",
    "test.example.com",
    "test@.com"
  ],
  "weakPasswords": [
    "123",
    "password",
    "12345678",
    "abcdefgh"
  ],
  "edgeCases": [
    {
      "firstName": "A",
      "lastName": "B",
      "email": "a@b.c",
      "password": "P@1",
      "description": "Minimum length values"
    },
    {
      "firstName": "VeryLongFirstNameThatExceedsMaximumLengthAllowed",
      "lastName": "VeryLongLastNameThatExceedsMaximumLengthAllowed",
      "email": "verylongemail@example.com",
      "password": "VeryLongPassword@123",
      "description": "Maximum length values"
    }
  ]
}
```

## Usage Instructions

### Quick Start
1. Copy RegistrationPage.java to `src/main/java/com/automation/pages/`
2. Copy RegistrationPageLocators.java to `src/main/java/com/automation/locators/`
3. Copy UserRegistrationTest.java to `src/test/java/com/automation/tests/`
4. Copy test data files to `src/test/resources/test-data/`
5. Update locators based on your application
6. Run tests: `mvn test -Dtest=UserRegistrationTest`

## Test Coverage

### Scenarios Covered
- ✓ Successful registration with valid data
- ✓ Registration with existing email
- ✓ Password mismatch
- ✓ Empty required fields
- ✓ Invalid email format
- ✓ Weak password
- ✓ Terms and conditions acceptance
- ✓ Newsletter subscription
- ✓ Navigation to login page

### Test Types
- Smoke tests: `testSuccessfulRegistration`
- Regression tests: All other tests

## Best Practices

### DO's
✓ Test all validation scenarios
✓ Use unique email for each test run
✓ Verify both positive and negative cases
✓ Check error messages
✓ Test password strength validation
✓ Verify email confirmation if applicable

### DON'Ts
✗ Don't use hardcoded emails (use timestamps for uniqueness)
✗ Don't skip validation testing
✗ Don't ignore password requirements
✗ Don't forget to test edge cases
✗ Don't use real user data

## Additional Notes
- Generate unique email addresses for each test (use timestamp)
- Consider testing email verification flow if applicable
- Test password strength requirements (length, special characters, etc.)
- Verify CAPTCHA if present (may need special handling)
- Test with various name formats (special characters, unicode, etc.)
- Consider testing phone number validation if required
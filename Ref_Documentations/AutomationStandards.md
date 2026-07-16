# Automation Framework Standards

## Overview
This document outlines the standards and best practices for the UI Automation Framework built with Selenium, TestNG, and Java.

## Framework Architecture

### Design Patterns
- **Page Object Model (POM)**: All page classes extend `BasePage` and encapsulate page-specific logic
- **Singleton Pattern**: WebDriver instances managed via `WebDriverFactory` using ThreadLocal
- **Factory Pattern**: Page objects created through page-specific factory methods
- **Builder Pattern**: Used for complex test data creation

### Project Structure
```
AutomationFramework/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/automation/
│   │   │       ├── base/           # Base test classes
│   │   │       ├── pages/          # Page Object classes
│   │   │       ├── locators/       # Page locator classes
│   │   │       ├── utils/          # Utility classes
│   │   │       └── constants/      # Constants and enums
│   │   └── resources/
│   │       ├── config/             # Configuration files
│   │       └── test-data/          # Test data files
│   └── test/
│       ├── java/
│       │   └── com/automation/
│       │       ├── tests/          # Test classes
│       │       └── listeners/      # Test listeners
│       └── resources/
├── TestSuites/                     # TestNG XML files
├── test-output/                    # Test reports and screenshots
└── logs/                           # Application logs
```

## Test Execution Standards

### TestNG Configuration
- **Parallel Execution**: Tests run in parallel at test level (`parallel="tests"`)
- **Thread Count**: 2 threads for parallel execution
- **Retry Logic**: Failed tests retried 2 times with 1-second delay
- **Groups**: Tests organized into groups (smoke, regression, ui, contact, login)

### Test Dependencies
- Tests should be **independent** and **idempotent**
- No dependencies between test methods
- Each test should set up its own preconditions
- Use `@BeforeMethod` for test-level setup
- Use `@BeforeClass` for class-level setup

## Page Object Model Standards

### BasePage Class
All page classes must:
- Extend `BasePage`
- Accept WebDriver in constructor
- Call `super(driver)` in constructor
- Use methods from `BasePage` for common operations

### Page Class Structure
```java
public class LoginPage extends BasePage {
    // Locators
    private LoginPageLocators locators;
    
    // Constructor
    public LoginPage(WebDriver driver) {
        super(driver);
        this.locators = new LoginPageLocators();
    }
    
    // Page actions (business logic)
    public void enterUsername(String username) { }
    public void clickLoginButton() { }
    
    // Page verifications
    public boolean isLoginPageLoaded() { }
}
```

### Locator Classes
- All locators stored in separate locator classes
- Locator classes named as `[PageName]Locators.java`
- Use `By` class for all locators
- Prefer stable locators (ID, name) over XPath

## Test Class Standards

### Test Class Structure
```java
public class LoginTest extends BaseTest {
    @Test(description = "...", groups = {...})
    public void testMethodName() {
        // 1. Navigate/Setup
        // 2. Execute actions using page objects
        // 3. Assert results
    }
}
```

### Test Method Requirements
- **Single Responsibility**: Each test method tests one scenario
- **Clear Naming**: Use descriptive names like `testSuccessfulLogin()`
- **Assertions**: Use TestNG assertions with descriptive messages
- **No Implementation**: Test classes should NOT contain element interaction logic
- **Page Objects Only**: All interactions through page object methods

## Code Quality Standards

### Java Standards
- **Java Version**: 11
- **Code Formatting**: Follow IDE auto-formatting
- **Comments**: Javadoc for all public methods
- **Logging**: Use SLF4J logger, not System.out.println
- **Exception Handling**: Proper exception handling with meaningful messages

### Naming Conventions
- **Classes**: PascalCase (e.g., `LoginPage`, `ContactTest`)
- **Methods**: camelCase (e.g., `enterUsername()`, `clickLoginButton()`)
- **Variables**: camelCase (e.g., `usernameField`, `loginButton`)
- **Constants**: UPPER_SNAKE_CASE (e.g., `MAX_RETRY_COUNT`)
- **Packages**: lowercase (e.g., `com.automation.pages`)

## Reporting Standards

### Extent Reports
- All tests automatically logged to Extent Reports
- Screenshots captured on test failure (base64 format)
- Test steps logged with INFO level
- Pass/Fail/Skip status automatically updated

### Email Reports
- Test reports zipped and emailed after suite execution
- Zip contains: HTML report, screenshots, logs
- Email enabled via config.properties (`email.enabled=true`)

## Configuration Management

### Config Properties
- All configuration in `config.properties`
- Sensitive data use environment variables
- Browser, URL, timeouts configurable
- Email settings configurable

### Environment Support
- Multiple environments supported via profiles
- Environment-specific config files
- Base URL configurable per environment

## Browser Compatibility

### Supported Browsers
- Chrome (primary)
- Firefox
- Edge
- Safari (Mac only)

### Browser Configuration
- Headless mode supported
- Incognito/Private mode supported
- Window size configurable
- Chrome options configurable via properties

## Wait Strategies

### Implicit Wait
- Configured globally (default: 10 seconds)
- Applied to all element searches

### Explicit Wait
- Preferred over implicit wait
- Used for specific conditions
- Maximum wait time: 20 seconds (configurable)

### Page Load Wait
- Wait for `document.readyState == 'complete'`
- Applied after navigation

## Error Handling

### Test Failures
- Screenshot captured automatically
- Error logged to Extent Report
- Stack trace included in report

### Element Not Found
- Explicit wait with timeout
- Meaningful error messages
- Retry mechanism for flaky elements

## Maintenance Guidelines

### When to Update
- Update page objects when UI changes
- Update locators when element attributes change
- Update tests when business logic changes
- Update config when environment changes

### Code Review Checklist
- [ ] Test follows POM pattern
- [ ] No hardcoded waits (Thread.sleep)
- [ ] Proper assertions with messages
- [ ] No duplicate code
- [ ] Locators are stable
- [ ] Test is independent
- [ ] Proper error handling
- [ ] Logging is appropriate

## Performance Standards

### Execution Time
- Smoke tests: < 5 minutes
- Regression tests: < 30 minutes
- Individual test: < 2 minutes

### Resource Management
- WebDriver quit after each test
- No memory leaks
- Screenshots limited to 10 per test run
- Zip file size limited to 50MB
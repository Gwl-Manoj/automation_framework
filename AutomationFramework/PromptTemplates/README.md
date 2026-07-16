# Prompt Templates

## Overview
This folder contains reusable prompt templates for common automation tasks. These templates help generate consistent, high-quality automation code following framework standards and best practices.

## Template Index

### Generic Templates

#### 1. [CreateSeleniumTest.md](CreateSeleniumTest.md)
**Purpose**: Create Selenium UI tests with TestNG

**Use When**:
- Creating new UI test classes
- Adding test methods to existing test classes
- Need a template for test structure

**Includes**:
- Test class structure
- Test method pattern
- Page object usage
- Assertions and logging
- Complete working example

#### 2. [CreatePageObject.md](CreatePageObject.md)
**Purpose**: Create Page Object classes following POM pattern

**Use When**:
- Creating new page objects
- Refactoring existing page objects
- Need to encapsulate page logic

**Includes**:
- Page class structure
- Locator class creation
- Action methods
- Verification methods
- Complete working example

#### 3. [CreateLocatorClass.md](CreateLocatorClass.md)
**Purpose**: Create locator classes for page objects

**Use When**:
- Creating locators for new pages
- Organizing element locators
- Need locator strategy guidance

**Includes**:
- Locator class structure
- Locator priority (ID > Name > CSS > XPath)
- Best practices
- Advanced locator examples
- Dynamic element handling

#### 4. [CreateRestAssuredTest.md](CreateRestAssuredTest.md)
**Purpose**: Create API tests with Rest Assured

**Use When**:
- Creating API test classes
- Testing REST endpoints
- Need API test structure

**Includes**:
- API test structure
- Given-when-then pattern
- Request/response validation
- Authentication patterns
- Common API test patterns

#### 5. [CreateJMeterScript.md](CreateJMeterScript.md)
**Purpose**: Create JMeter performance test scripts

**Use When**:
- Creating load tests
- Creating stress tests
- Creating performance test plans

**Includes**:
- JMeter test plan structure
- Thread group configuration
- HTTP request setup
- Test types (load, stress, soak, spike)
- Command line execution

#### 6. [CreateTestData.md](CreateTestData.md)
**Purpose**: Create test data for automation tests

**Use When**:
- Creating test data files
- Setting up data-driven tests
- Organizing test data

**Includes**:
- Data formats (Excel, CSV, JSON, Properties)
- Test data organization
- Data generation strategies
- Data management best practices
- Complete examples

#### 7. [CreateAssertions.md](CreateAssertions.md)
**Purpose**: Create comprehensive test assertions

**Use When**:
- Writing test validations
- Need assertion strategies
- Creating custom assertions

**Includes**:
- TestNG assertions
- Hamcrest matchers
- UI assertions
- API assertions
- Database assertions
- Soft assertions
- Custom assertion patterns

#### 8. [CreateSQLValidation.md](CreateSQLValidation.md)
**Purpose**: Create database validation tests

**Use When**:
- Validating database operations
- Testing data integrity
- Creating database tests

**Includes**:
- JDBC connection setup
- SQL validation patterns
- Database utility class
- Transaction management
- Common validation scenarios

#### 9. [CreatePerformanceScenario.md](CreatePerformanceScenario.md)
**Purpose**: Design performance test scenarios

**Use When**:
- Planning performance tests
- Designing load scenarios
- Defining performance metrics

**Includes**:
- Test types (load, stress, soak, spike)
- Scenario design
- User journey mapping
- Performance metrics
- Monitoring strategies
- Complete scenario templates

#### 10. [AutomationWorkflow.md](AutomationWorkflow.md)
**Purpose**: Complete end-to-end automation generation workflow

**Use When**:
- Starting a new feature automation
- Need to understand complete workflow
- Generating complete test suite

**Includes**:
- Workflow steps
- Component generation order
- Master generation script
- Feature-specific workflows
- Complete examples

### Feature-Specific Templates

#### 11. [LoginFeatureTemplates.md](LoginFeatureTemplates.md)
**Purpose**: Complete templates for login functionality

**Use When**:
- Automating login functionality
- Need authentication test templates
- Building login page objects and tests

**Includes**:
- LoginPage.java (Page Object)
- LoginPageLocators.java (Locators)
- LoginTest.java (Test class with 7 test methods)
- Test data (CSV and JSON)
- Complete test coverage

#### 12. [SearchFeatureTemplates.md](SearchFeatureTemplates.md)
**Purpose**: Complete templates for search functionality

**Use When**:
- Automating search functionality
- Need search test templates
- Building search page objects and tests

**Includes**:
- HomePage.java (with search)
- HomePageLocators.java
- SearchResultsPage.java
- SearchResultsPageLocators.java
- SearchTest.java (Test class with 8 test methods)
- Test data (CSV and JSON)

#### 13. [CheckoutFeatureTemplates.md](CheckoutFeatureTemplates.md)
**Purpose**: Complete templates for checkout functionality

**Use When**:
- Automating e-commerce checkout
- Need payment test templates
- Building checkout page objects and tests

**Includes**:
- CartPage.java
- CartPageLocators.java
- CheckoutPage.java
- CheckoutPageLocators.java
- OrderConfirmationPage.java
- OrderConfirmationPageLocators.java
- CheckoutTest.java (Test class with 5 test methods)
- Test data (CSV and JSON)

#### 14. [UserRegistrationFeatureTemplates.md](UserRegistrationFeatureTemplates.md)
**Purpose**: Complete templates for user registration

**Use When**:
- Automating user registration
- Need registration test templates
- Building registration page objects and tests

**Includes**:
- RegistrationPage.java
- RegistrationPageLocators.java
- UserRegistrationTest.java (Test class with 9 test methods)
- Test data (CSV and JSON)
- Validation scenarios

## How to Use These Templates

### For New Team Members
1. Start with **CreateSeleniumTest.md** to understand test structure
2. Use **CreatePageObject.md** when creating page objects
3. Reference **CreateLocatorClass.md** for element locators
4. Use other templates as needed for specific tasks

### For Creating Tests
1. Choose the appropriate template
2. Replace placeholder values (in brackets) with actual values
3. Follow the code templates provided
4. Refer to examples for guidance

### Template Format
Each template includes:
- **Role**: Expert persona for the task
- **Task**: What to accomplish
- **Context**: Framework and tool information
- **Requirements**: Input needed
- **Instructions**: Step-by-step guidance
- **Code Templates**: Reusable code patterns
- **Best Practices**: DO's and DON'Ts
- **Examples**: Complete working examples

## Quick Reference

### Generic Templates
| Task | Template | Output |
|------|----------|--------|
| Create UI test | CreateSeleniumTest.md | Test class with test methods |
| Create page object | CreatePageObject.md | Page class + Locator class |
| Create locators | CreateLocatorClass.md | Locator class with By variables |
| Create API test | CreateRestAssuredTest.md | API test class |
| Create JMeter script | CreateJMeterScript.md | JMX test plan |
| Create test data | CreateTestData.md | Test data files (CSV, JSON, Excel) |
| Write assertions | CreateAssertions.md | Assertion code patterns |
| Database validation | CreateSQLValidation.md | Database utility + tests |
| Performance scenario | CreatePerformanceScenario.md | Performance test plan |
| Complete workflow | AutomationWorkflow.md | Full test suite generation |

### Feature-Specific Templates
| Feature | Template | Output |
|---------|----------|--------|
| Login | LoginFeatureTemplates.md | LoginPage, LoginTest (7 tests) |
| Search | SearchFeatureTemplates.md | HomePage, SearchResultsPage, SearchTest (8 tests) |
| Checkout | CheckoutFeatureTemplates.md | CartPage, CheckoutPage, OrderConfirmationPage, CheckoutTest (5 tests) |
| User Registration | UserRegistrationFeatureTemplates.md | RegistrationPage, UserRegistrationTest (9 tests) |
| Home Page/Subscription | HomePageTemplate.md | HomePage, HomePageLocators, HomePageTest (3 tests) |

## Best Practices

### Using Templates
✓ Read the entire template first
✓ Understand the context and requirements
✓ Replace all placeholder values
✓ Follow the code structure
✓ Adapt examples to your needs
✓ Maintain consistency across templates

✓ Don't skip sections
✓ Don't ignore best practices
✓ Don't copy-paste without understanding
✓ Don't modify template structure unnecessarily

### Template Maintenance
- Templates should be updated when framework changes
- Add new templates for new test types
- Keep examples current and relevant
- Document any deviations from standards

## Contributing

### Adding New Templates
1. Follow the existing template structure
2. Include all required sections
3. Provide complete working examples
4. Test the template output
5. Add entry to this README

### Template Structure
```markdown
# Template Name

## Role
## Task
## Context
## Requirements
## Instructions
## Code Template
## Best Practices
## Example
## Additional Notes
```

## Version History

| Version | Date | Changes | Author |
|---------|------|---------|--------|
| 1.0 | 2026-07-10 | Initial set of 9 templates | Automation Team |
| 1.1 | 2026-07-13 | Added feature-specific templates (Login, Search, Checkout, Registration) | Automation Team |
| 1.2 | 2026-07-13 | Added One-Click Generation and Master Generation Prompt | Automation Team |

---

## Quick Start - One-Click Generation

### How to Generate Complete Test Suite in One Step

Simply provide a feature description like:
- "Test login functionality"
- "Test search feature"
- "Test checkout process"
- "Test user registration"

**Use the MasterGenerationPrompt.md template** which will automatically generate:
1. ✅ Page Object class
2. ✅ Locator class
3. ✅ Test class (5-7 test methods)
4. ✅ Test data (CSV and JSON)
5. ✅ Extent Reports integration (automatic)

### Example Usage

**You say**: "Test login functionality"

**Cline generates**:
- LoginPage.java (complete page object)
- LoginPageLocators.java (all locators)
- LoginTest.java (7 test methods)
- login-test-data.csv
- login-test-data.json

All with complete, production-ready code - no placeholders, no TODOs!

### Supported Features

The one-click generation supports these common features:
- **Login/Authentication** - "Test login functionality"
- **Search** - "Test search feature"
- **Checkout/Payment** - "Test checkout process"
- **User Registration** - "Test user registration"
- **Contact Form** - "Test contact form"
- **Shopping Cart** - "Test shopping cart"
- **Home Page/Subscription** - "Test home page subscription"

---

**Note**: These templates are living documents. Update them as the framework evolves and new patterns emerge.

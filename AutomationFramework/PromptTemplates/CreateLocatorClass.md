# Create Locator Class

## Role
You are an expert test automation engineer specializing in element locator strategies and Selenium WebDriver.

## Task
Create a comprehensive locator class for a page following best practices and framework standards.

## Context
- **Framework**: Selenium WebDriver + TestNG + Java 11
- **Pattern**: Page Object Model (POM)
- **Organization**: One locator class per page
- **Location**: `src/main/java/com/automation/locators/` package
- **Naming**: `[PageName]Locators.java`

## Requirements

### Page Information
- **Page Name**: [e.g., LoginPage, ContactPage, DashboardPage]
- **Page URL**: [Page URL or path]
- **Page Description**: [Brief description of the page]

### Elements to Locate
[List all elements that need locators:]
- **Form Fields**: [username, password, email, etc.]
- **Buttons**: [login, submit, cancel, etc.]
- **Links**: [navigation links, forgot password, etc.]
- **Messages**: [error, success, warning messages]
- **Dropdowns**: [country, state, etc.]
- **Checkboxes/Radios**: [remember me, terms, etc.]
- **Tables**: [data tables, lists, etc.]
- **Other**: [images, icons, etc.]

## Instructions

### 1. Locator Class Structure
Create a locator class that:
- Is placed in `src/main/java/com/automation/locators/` package
- Follows naming convention: `[PageName]Locators.java`
- Contains only public By variables
- Is well-organized with comments

### 2. Locator Priority
Follow this priority order when selecting locator type:
1. **ID** - Most stable and preferred
2. **Name** - Second best option
3. **CSS Selector** - Fast and flexible
4. **XPath** - Most powerful but least stable
5. **Class Name** - Use only when unique
6. **Tag Name** - Use only for generic elements
7. **Link Text** - Only for links
8. **Partial Link Text** - Avoid if possible

### 3. Locator Organization
Organize locators by element type with clear comments:

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
    public By emailField = By.name("email");
    
    // ==================== Buttons ====================
    public By loginButton = By.cssSelector(".btn-login");
    public By submitButton = By.id("submit-btn");
    public By cancelButton = By.xpath("//button[text()='Cancel']");
    
    // ==================== Links ====================
    public By forgotPasswordLink = By.linkText("Forgot Password?");
    public By homeLink = By.cssSelector("a[href='/home']");
    
    // ==================== Messages ====================
    public By errorMessage = By.cssSelector(".alert-danger");
    public By successMessage = By.cssSelector(".alert-success");
    public By warningMessage = By.cssSelector(".alert-warning");
    
    // ==================== Checkboxes ====================
    public By rememberMeCheckbox = By.id("remember-me");
    public By termsCheckbox = By.cssSelector("input[type='checkbox'][name='terms']");
    
    // ==================== Dropdowns ====================
    public By countryDropdown = By.id("country");
    public By stateDropdown = By.name("state");
    
    // ==================== Tables ====================
    public By usersTable = By.id("users-table");
    public By tableRows = By.cssSelector("#users-table tbody tr");
    
    // ==================== Other Elements ====================
    public By logo = By.cssSelector(".logo img");
    public By loadingSpinner = By.id("loading");
}
```

### 4. Locator Best Practices

#### DO's
✓ **Use stable attributes** (ID, name, stable classes)
✓ **Use relative locators** (never absolute XPath)
✓ **Use CSS over XPath** when possible (faster)
✓ **Combine attributes** for uniqueness
✓ **Use meaningful variable names**
✓ **Group related locators** with comments
✓ **Test locators** across environments

#### DON'Ts
✗ **Don't use absolute XPath** (`/html/body/div[2]/...`)
✗ **Don't use dynamic attributes** that change (`id="user_12345"`)
✗ **Don't use index-based locators** unless necessary (`(//div)[3]`)
✗ **Don't use complex XPath** when CSS works
✗ **Don't use generic names** (`element1`, `button2`)

### 5. Locator Naming Convention
Use descriptive names that indicate the element type:

```java
// Form fields
public By usernameField = By.id("username");
public By passwordField = By.id("password");
public By emailField = By.name("email");
public By searchInput = By.cssSelector("input[type='search']");

// Buttons
public By loginButton = By.cssSelector(".btn-login");
public By submitButton = By.id("submit");
public By cancelButton = By.xpath("//button[text()='Cancel']");

// Links
public By forgotPasswordLink = By.linkText("Forgot Password?");
public By homeLink = By.cssSelector("a[href='/home']");

// Messages
public By errorMessage = By.cssSelector(".alert-danger");
public By successMessage = By.cssSelector(".alert-success");

// Checkboxes
public By rememberMeCheckbox = By.id("remember-me");

// Dropdowns
public By countryDropdown = By.id("country");

// Tables
public By usersTable = By.id("users-table");

// Other
public By logo = By.cssSelector(".logo img");
public By loadingSpinner = By.id("loading");
```

### 6. Advanced Locator Examples

#### CSS Selectors
```java
// ID selector
public By usernameField = By.cssSelector("#username");

// Class selector
public By submitButton = By.cssSelector(".btn-submit");

// Attribute selector
public By placeholder = By.cssSelector("[placeholder='Enter username']");

// Starts with
public By usernameField = By.cssSelector("[id^='username']");

// Ends with
public By submitButton = By.cssSelector("[id$='-submit']");

// Contains
public By activeTab = By.cssSelector("[class*='active']");

// Multiple attributes
public By requiredField = By.cssSelector("input[type='text'][required]");

// Child combinator
public By menuItem = By.cssSelector("ul.menu > li");

// Descendant combinator
public By tableCell = By.cssSelector("table#users tbody tr td");

// Pseudo-classes
public By firstItem = By.cssSelector("ul li:first-child");
public By lastItem = By.cssSelector("ul li:last-child");
public By evenRow = By.cssSelector("tr:nth-child(even)");
```

#### XPath
```java
// Text match (exact)
public By loginButton = By.xpath("//button[text()='Login']");

// Text match (contains)
public By welcomeMessage = By.xpath("//h1[contains(text(), 'Welcome')]");

// Attribute match
public By usernameField = By.xpath("//input[@id='username']");

// Multiple conditions (AND)
public By submitButton = By.xpath("//button[@type='submit' and @class='btn-primary']");

// Multiple conditions (OR)
public By button = By.xpath("//button[@type='submit' or @type='button']");

// Parent/Child
public By childElement = By.xpath("//div[@id='form']//input");

// Following sibling
public By input = By.xpath("//label[text()='Username']/following-sibling::input");

// Contains attribute
public By button = By.xpath("//button[contains(@class, 'btn-primary')]");

// Starts with attribute
public By usernameField = By.xpath("//input[starts-with(@id, 'username')]");
```

### 7. Handling Dynamic Elements

#### Dynamic IDs
```java
// Problem: id="username_12345" (changes every run)
// Solution 1: Use starts-with
public By usernameField = By.cssSelector("[id^='username']");

// Solution 2: Use contains
public By usernameField = By.cssSelector("[id*='username']");

// Solution 3: Use different attribute
public By usernameField = By.name("username");
public By usernameField = By.cssSelector("[placeholder='Enter username']");
```

#### Dynamic Classes
```java
// Problem: class="btn btn-primary btn-12345"
// Solution 1: Use contains for class
public By button = By.cssSelector("[class*='btn-primary']");

// Solution 2: Use multiple classes
public By button = By.cssSelector(".btn.btn-primary");

// Solution 3: Use tag with class
public By button = By.cssSelector("button.btn-primary");
```

#### Dynamic Text
```java
// Problem: "Welcome, John Doe" or "Total: $123.45"
// Solution 1: Use contains
public By welcomeMsg = By.xpath("//div[contains(text(), 'Welcome')]");
public By totalAmount = By.xpath("//div[contains(text(), 'Total:')]");

// Solution 2: Use starts-with
public By welcomeMsg = By.xpath("//div[starts-with(text(), 'Welcome')]");
```

### 8. Common Locator Patterns

#### Form Fields
```java
public By usernameField = By.id("username");
public By emailField = By.name("email");
public By passwordField = By.cssSelector("input[type='password']");
public By searchField = By.cssSelector("input[type='search']");
public By textarea = By.tagName("textarea");
```

#### Buttons
```java
public By submitButton = By.cssSelector("button[type='submit']");
public By primaryButton = By.cssSelector(".btn-primary");
public By iconButton = By.cssSelector("button[aria-label='Close']");
```

#### Links
```java
public By forgotPassword = By.linkText("Forgot Password?");
public By homeLink = By.cssSelector("a[href*='home']");
```

#### Tables
```java
public By usersTable = By.id("users-table");
public By tableRows = By.cssSelector("#users-table tbody tr");
public By tableCells = By.cssSelector("#users-table td");
```

#### Dropdowns
```java
public By countryDropdown = By.id("country");
public By options = By.cssSelector("#country option");
```

#### Messages
```java
public By errorMessage = By.cssSelector(".alert-danger");
public By successMessage = By.cssSelector(".alert-success");
public By warningMessage = By.cssSelector(".alert-warning");
```

## Code Template

```java
package com.automation.locators;

import org.openqa.selenium.By;

/**
 * [Page Name] Locators
 * Contains all element locators for the [page name] page
 */
public class [PageName]Locators {
    
    // ==================== Form Fields ====================
    public By [fieldName] = By.[type]("[locator-value]");
    
    // ==================== Buttons ====================
    public By [buttonName] = By.[type]("[locator-value]");
    
    // ==================== Links ====================
    public By [linkName] = By.[type]("[locator-value]");
    
    // ==================== Messages ====================
    public By [messageName] = By.[type]("[locator-value]");
    
    // ==================== [Other Category] ====================
    public By [elementName] = By.[type]("[locator-value]");
}
```

## Best Practices

### DO's
✓ Use ID locators when available
✓ Use CSS selectors over XPath for performance
✓ Use relative locators (never absolute XPath)
✓ Group locators by category with comments
✓ Use descriptive variable names
✓ Test locators across browsers
✓ Keep locators simple and maintainable
✓ Document complex locators

### DON'Ts
✗ Don't use absolute XPath
✗ Don't use dynamic attributes without stabilization
✗ Don't use index-based locators unnecessarily
✗ Don't create overly complex locators
✗ Don't use generic names (element1, field2)
✗ Don't mix locator types randomly
✗ Don't forget to test locators

## Example

### Input
```
Page Name: ContactPage
Elements:
- Contact Us button
- Get In Touch heading
- Name field
- Email field
- Subject field
- Message field
- File upload input
- Submit button
- Success message
- Home button
```

### Output
```java
package com.automation.locators;

import org.openqa.selenium.By;

/**
 * Contact Page Locators
 * Contains all element locators for the contact page
 */
public class ContactPageLocators {
    
    // ==================== Navigation ====================
    public By contactUsButton = By.cssSelector("a[href='/contact_us']");
    public By homeButton = By.cssSelector("a[href='/']");
    
    // ==================== Headings ====================
    public By getInTouchHeading = By.xpath("//h2[text()='Get In Touch']");
    
    // ==================== Form Fields ====================
    public By nameField = By.cssSelector("input[data-qa='name']");
    public By emailField = By.cssSelector("input[data-qa='email']");
    public By subjectField = By.cssSelector("input[data-qa='subject']");
    public By messageField = By.cssSelector("textarea[data-qa='message']");
    
    // ==================== File Upload ====================
    public By uploadFileInput = By.cssSelector("input[type='file']");
    
    // ==================== Buttons ====================
    public By submitButton = By.cssSelector("input[data-qa='submit-button']");
    
    // ==================== Messages ====================
    public By successMessage = By.cssSelector(".alert-success");
    
    // ==================== Other Elements ====================
    public By contactForm = By.id("contact-us-form");
}
```

## Validation Checklist
- [ ] All page elements have locators
- [ ] Locators use appropriate type (ID > Name > CSS > XPath)
- [ ] No absolute XPath used
- [ ] Locators are organized by category
- [ ] Variable names are descriptive
- [ ] Locators tested and working
- [ ] No dynamic attributes without stabilization
- [ ] Comments added for each category

## Additional Notes
- Keep locators simple and maintainable
- Test locators across different browsers
- Update locators when UI changes
- Use browser DevTools to verify locators
- Consider using data-qa attributes for better stability
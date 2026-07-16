# Locator Strategy

## Overview
This document defines the best practices and strategies for locating web elements in the automation framework. Proper locator selection is critical for test stability and maintainability.

## Locator Priority

### Priority Order
Always use locators in this order of preference:

1. **ID** - Most stable and preferred
2. **Name** - Second best option
3. **CSS Selector** - Fast and flexible
4. **XPath** - Most powerful but least stable
5. **Class Name** - Use only when unique
6. **Tag Name** - Use only for generic elements
7. **Link Text** - Only for links
8. **Partial Link Text** - Avoid if possible

### Rationale
- **ID**: Unique, fast, rarely changes
- **Name**: Usually unique, stable
- **CSS Selector**: Fast, readable, good for complex patterns
- **XPath**: Powerful but brittle, breaks easily with UI changes

## Locator Types

### 1. ID Locator
```java
// Best choice - unique and stable
By usernameField = By.id("username");
By passwordField = By.id("password");
By submitButton = By.id("submit-btn");

// Usage
WebElement element = driver.findElement(usernameField);
```

**Pros:**
- Unique in the DOM
- Fastest locator
- Most stable

**Cons:**
- Not always available
- May be dynamic

### 2. Name Locator
```java
// Good alternative to ID
By emailField = By.name("email");
By passwordField = By.name("password");

// Usage
WebElement element = driver.findElement(emailField);
```

**Pros:**
- Usually unique
- Stable
- Semantic meaning

**Cons:**
- Not always present
- May not be unique

### 3. CSS Selector
```java
// Class selector
By submitButton = By.cssSelector(".submit-button");
By errorMessage = By.cssSelector(".error-message");

// ID selector
By usernameField = By.cssSelector("#username");

// Attribute selector
By placeholder = By.cssSelector("[placeholder='Enter username']");

// Child combinator
By menuItem = By.cssSelector("ul.menu > li.active");

// Descendant combinator
By tableCell = By.cssSelector("table#users tbody tr td");

// Pseudo-classes
By firstItem = By.cssSelector("ul li:first-child");
By lastItem = By.cssSelector("ul li:last-child");
By evenRow = By.cssSelector("tr:nth-child(even)");
```

**Pros:**
- Fast execution
- Readable syntax
- Powerful selection options
- Better performance than XPath

**Cons:**
- Complex selectors can be hard to read
- Limited compared to XPath

### 4. XPath
```java
// Absolute XPath (AVOID - brittle)
By element = By.xpath("/html/body/div[2]/div[1]/form/input");

// Relative XPath (PREFERRED)
By loginButton = By.xpath("//button[text()='Login']");
By usernameField = By.xpath("//input[@id='username']");

// Contains text
By heading = By.xpath("//h1[contains(text(), 'Welcome')]");

// Contains attribute
By button = By.xpath("//button[contains(@class, 'submit')]");

// AND/OR conditions
By element = By.xpath("//input[@type='text' and @id='username']");
By element = By.xpath("//button[@type='submit' or @type='button']");

// Parent/Child/Sibling
By childElement = By.xpath("//div[@id='form']//input");
By parentElement = By.xpath("//span[text()='Username']/parent::div");
By siblingElement = By.xpath("//label[text()='Username']/following-sibling::input");

// Multiple conditions
By element = By.xpath("//input[@id='username' or @name='user' or @placeholder='Enter username']");

// Index-based
By firstItem = By.xpath("(//li)[1]");
By secondItem = By.xpath("(//li)[2]");
```

**Pros:**
- Very powerful
- Can navigate DOM structure
- Supports text matching
- Can use axes (parent, child, sibling)

**Cons:**
- Slower than CSS
- Brittle - breaks with UI changes
- Harder to read
- Can be complex

## Locator Best Practices

### DO's

✓ **Use stable attributes**
```java
// Good - stable ID
By usernameField = By.id("username");

// Good - stable name
By emailField = By.name("email");

// Good - stable CSS class
By submitButton = By.cssSelector(".btn-submit");
```

✓ **Use relative locators**
```java
// Good - relative XPath
By loginButton = By.xpath("//button[text()='Login']");

// Bad - absolute XPath
By loginButton = By.xpath("/html/body/div[2]/div[1]/form/button");
```

✓ **Use text() for exact matches**
```java
// Good - exact text match
By heading = By.xpath("//h1[text()='Welcome']");

// Good - contains for partial match
By heading = By.xpath("//h1[contains(text(), 'Welcome')]");
```

✓ **Combine attributes for uniqueness**
```java
// Good - multiple attributes
By button = By.cssSelector("button[type='submit'][class*='primary']");
By input = By.xpath("//input[@type='email' and @id='email']");
```

✓ **Use meaningful locator variable names**
```java
// Good
By usernameField = By.id("username");
By submitButton = By.cssSelector(".submit-btn");

// Bad
By field1 = By.id("username");
By button = By.cssSelector(".submit-btn");
```

### DON'Ts

✗ **Don't use absolute XPath**
```java
// Bad - breaks with any UI change
By element = By.xpath("/html/body/div[3]/div[2]/form/input[1]");

// Good - relative XPath
By element = By.xpath("//input[@id='username']");
```

✗ **Don't use dynamic attributes**
```java
// Bad - dynamic ID that changes
By element = By.id("username_12345"); // Changes every run

// Good - stable part of ID or different locator
By element = By.cssSelector("[id^='username']"); // Starts with
By element = By.cssSelector("[id*='username']"); // Contains
```

✗ **Don't use index-based locators unless necessary**
```java
// Bad - fragile
By element = By.xpath("(//div)[3]");

// Good - use attributes
By element = By.cssSelector("div.active");
```

✗ **Don't use complex XPath when CSS works**
```java
// Bad - complex XPath
By element = By.xpath("//div[@class='container']//div[@class='row']//div[@class='col']//input");

// Good - simple CSS
By element = By.cssSelector(".container .row .col input");
```

## Locator Organization

### Locator Classes
All locators for a page should be in a separate locator class:

```java
// LoginPageLocators.java
package com.automation.locators;

import org.openqa.selenium.By;

public class LoginPageLocators {
    // Form fields
    public By usernameField = By.id("username");
    public By passwordField = By.id("password");
    
    // Buttons
    public By loginButton = By.cssSelector(".btn-login");
    public By forgotPasswordLink = By.linkText("Forgot Password?");
    
    // Messages
    public By errorMessage = By.cssSelector(".error-message");
    public By successMessage = By.cssSelector(".success-message");
    
    // Checkboxes
    public By rememberMeCheckbox = By.id("remember-me");
}
```

### Usage in Page Classes
```java
// LoginPage.java
public class LoginPage extends BasePage {
    private LoginPageLocators locators;
    
    public LoginPage(WebDriver driver) {
        super(driver);
        this.locators = new LoginPageLocators();
    }
    
    public void enterUsername(String username) {
        enterText(locators.usernameField, username);
    }
    
    public void clickLoginButton() {
        click(locators.loginButton);
    }
}
```

## Advanced Locator Strategies

### CSS Selectors Advanced

#### Attribute Selectors
```java
// Exact match
By element = By.cssSelector("[type='submit']");

// Starts with
By element = By.cssSelector("[id^='user_']");

// Ends with
By element = By.cssSelector("[id$='_field']");

// Contains
By element = By.cssSelector("[class*='active']");

// Multiple attributes
By element = By.cssSelector("input[type='text'][required]");
```

#### Pseudo-classes
```java
// First child
By firstItem = By.cssSelector("ul li:first-child");

// Last child
By lastItem = By.cssSelector("ul li:last-child");

// Nth child
By thirdItem = By.cssSelector("ul li:nth-child(3)");

// Even/Odd
By evenRow = By.cssSelector("tr:nth-child(even)");
By oddRow = By.cssSelector("tr:nth-child(odd)");

// Hover state
By hoverItem = By.cssSelector("li:hover");
```

#### Combinators
```java
// Descendant (space)
By element = By.cssSelector("div p"); // p inside div

// Child (>)
By element = By.cssSelector("ul > li"); // direct child li

// Adjacent sibling (+)
By element = By.cssSelector("h1 + p"); // p immediately after h1

// General sibling (~)
By element = By.cssSelector("h1 ~ p"); // all p siblings after h1
```

### XPath Advanced

#### Axes
```java
// Parent axis
By parent = By.xpath("//span[text()='Username']/parent::div");

// Child axis
By children = By.xpath("//ul/child::li");

// Following-sibling
By nextSibling = By.xpath("//label[text()='Username']/following-sibling::input");

// Preceding-sibling
By prevSibling = By.xpath("//input[@id='username']/preceding-sibling::label");

// Ancestor
By ancestor = By.xpath("//span[text()='Username']/ancestor::form");

// Descendant
By descendant = By.xpath("//form//input");
```

#### Functions
```java
// text() - exact text match
By element = By.xpath("//button[text()='Submit']");

// contains() - partial text match
By element = By.xpath("//button[contains(text(), 'Submit')]");

// starts-with() - text starts with
By element = By.xpath("//button[starts-with(text(), 'Sub')]");

// last() - last element
By lastItem = By.xpath("(//li)[last()]");

// position() - element at position
By firstItem = By.xpath("(//li)[1]");
```

## Dynamic Elements

### Handling Dynamic IDs
```java
// Problem: ID changes every run
// <input id="username_12345">

// Solution 1: Use starts-with
By usernameField = By.cssSelector("[id^='username']");

// Solution 2: Use contains
By usernameField = By.cssSelector("[id*='username']");

// Solution 3: Use different attribute
By usernameField = By.name("username");
By usernameField = By.cssSelector("input[placeholder='Enter username']");
```

### Handling Dynamic Classes
```java
// Problem: Class changes dynamically
// <button class="btn btn-primary btn-12345">

// Solution 1: Use contains for class
By button = By.cssSelector("[class*='btn-primary']");

// Solution 2: Use multiple classes
By button = By.cssSelector(".btn.btn-primary");

// Solution 3: Use tag with class
By button = By.cssSelector("button.btn-primary");
```

### Handling Dynamic Text
```java
// Problem: Text contains dynamic values
// <div>Welcome, John Doe</div>
// <div>Total: $123.45</div>

// Solution 1: Use contains
By welcomeMsg = By.xpath("//div[contains(text(), 'Welcome')]");
By totalAmount = By.xpath("//div[contains(text(), 'Total:')]");

// Solution 2: Use starts-with
By welcomeMsg = By.xpath("//div[starts-with(text(), 'Welcome')]");
```

## Locator Maintenance

### When to Update Locators
- Element attribute changes (id, class, name)
- Page structure changes
- Application UI changes
- Tests fail with "element not found"

### How to Update
1. Update locator in locator class
2. Run affected tests
3. Verify all tests pass
4. Update documentation if needed

### Version Control
```java
// Keep locators in separate classes for easy updates
// One locator class per page
// Version control locator changes separately from test logic
```

## Performance Considerations

### Locator Speed (Fastest to Slowest)
1. ID
2. Name
3. CSS Selector
4. XPath

### Optimization Tips
```java
// Use ID when possible (fastest)
By element = By.id("username");

// Use CSS over XPath when equivalent
By element = By.cssSelector(".btn-primary"); // Fast
By element = By.xpath("//button[contains(@class, 'btn-primary')]"); // Slower

// Cache locators (already done in locator classes)
public class LoginPageLocators {
    public By usernameField = By.id("username"); // Created once
}
```

## Common Patterns

### Form Fields
```java
// Text input
By usernameField = By.id("username");
By emailField = By.name("email");
By searchField = By.cssSelector("input[type='search']");

// Password field
By passwordField = By.id("password");
By passwordField = By.cssSelector("input[type='password']");

// Textarea
By messageField = By.tagName("textarea");
By messageField = By.cssSelector("textarea.message");
```

### Buttons
```java
// Submit button
By submitButton = By.cssSelector("button[type='submit']");
By submitButton = By.xpath("//button[text()='Submit']");

// Primary button
By primaryButton = By.cssSelector(".btn-primary");
By primaryButton = By.cssSelector("button.btn-primary");

// Icon button
By iconButton = By.cssSelector("button[aria-label='Close']");
```

### Links
```java
// By link text
By forgotPassword = By.linkText("Forgot Password?");

// By partial link text (avoid if possible)
By forgotPassword = By.partialLinkText("Forgot");

// Better: use other attributes
By forgotPassword = By.cssSelector("a[href*='forgot-password']");
```

### Tables
```java
// Table element
By usersTable = By.id("users-table");

// Table rows
By tableRows = By.cssSelector("#users-table tbody tr");

// Table cells
By tableCells = By.cssSelector("#users-table td");

// Specific cell
By cell = By.xpath("//table[@id='users-table']//tr[2]//td[3]");
```

### Dropdowns
```java
// Select element
By countryDropdown = By.id("country");
By countryDropdown = By.tagName("select");

// Options
By options = By.cssSelector("#country option");
```

### Alerts and Messages
```java
// Error message
By errorMessage = By.cssSelector(".alert-danger");
By errorMessage = By.xpath("//div[contains(@class, 'error')]");

// Success message
By successMessage = By.cssSelector(".alert-success");

// Warning message
By warningMessage = By.cssSelector(".alert-warning");
```

## Troubleshooting

### Element Not Found
1. Check if locator is correct
2. Check if element is in iframe
3. Check if element is visible
4. Add explicit wait
5. Check for dynamic content

### Element Not Clickable
1. Element might be covered by another element
2. Use JavaScript click: `jsClick(locator)`
3. Scroll to element first
4. Add wait for element to be clickable

### Locator Finds Wrong Element
1. Make locator more specific
2. Use additional attributes
3. Use parent-child relationship
4. Use index (last resort)

## Summary

### Key Takeaways
- Always prefer ID > Name > CSS > XPath
- Use relative locators, never absolute XPath
- Keep locators in separate classes
- Use meaningful locator variable names
- Test locator stability across environments
- Document complex locators with comments

### Quick Reference
```java
// Best practices in one view
By id = By.id("element-id");              // Best
By name = By.name("element-name");        // Good
By css = By.cssSelector(".class-name");   // Good
By xpath = By.xpath("//div[@id='id']");   // Acceptable
By text = By.xpath("//button[text()='OK']"); // Acceptable
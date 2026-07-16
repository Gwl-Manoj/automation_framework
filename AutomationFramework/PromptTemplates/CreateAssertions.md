# Create Assertions

## Role
You are an expert test automation engineer specializing in test assertions and validation strategies.

## Task
Create comprehensive assertion strategies and implementations for automation tests following best practices.

## Context
- **Framework**: Selenium WebDriver + TestNG + Java 11
- **Assertion Libraries**: TestNG Assert, Hamcrest Matchers, JSON Schema Validator
- **Purpose**: Validate test expectations and verify application behavior
- **Scope**: UI tests, API tests, database tests

## Requirements

### Assertion Information
- **Test Type**: [UI, API, Database, Integration]
- **Validation Points**: [What needs to be validated]
- **Assertion Types**: [Equality, Comparison, Collection, Custom]
- **Error Messages**: [Descriptive messages for failures]

### Validation Categories
[List what needs to be validated:]
- **UI Elements**: Visibility, text, attributes, state
- **API Responses**: Status codes, response body, headers
- **Database**: Data integrity, record counts, field values
- **Business Logic**: Calculations, workflows, rules
- **Performance**: Response times, throughput

## Instructions

### 1. Assertion Principles

#### Assert Early, Assert Often
- Validate preconditions first
- Check each step of the test
- Fail fast to save time

#### Descriptive Messages
- Always provide meaningful error messages
- Include expected vs actual values
- Add context for debugging

#### Comprehensive Validation
- Validate all aspects of the requirement
- Don't assume anything
- Check both positive and negative cases

### 2. TestNG Assertions

#### Basic Assertions
```java
// assertEquals - Verify equality
Assert.assertEquals(actual, expected, "Message");
Assert.assertEquals(actual, expected, "Expected {} but found {}", expected, actual);

// assertNotEquals - Verify inequality
Assert.assertNotEquals(actual, notExpected, "Message");

// assertTrue - Verify condition is true
Assert.assertTrue(condition, "Message");

// assertFalse - Verify condition is false
Assert.assertFalse(condition, "Message");

// assertNull - Verify value is null
Assert.assertNull(value, "Message");

// assertNotNull - Verify value is not null
Assert.assertNotNull(value, "Message");

// assertSame - Verify same object instance
Assert.assertSame(expected, actual, "Message");

// assertNotSame - Verify different object instances
Assert.assertNotSame(expected, actual, "Message");
```

#### Array Assertions
```java
// assertEquals for arrays
Assert.assertEquals(expectedArray, actualArray, "Arrays should match");

// Verify array contains element
Assert.assertTrue(Arrays.asList(actualArray).contains(expectedElement), 
    "Array should contain element");

// Verify array length
Assert.assertEquals(actualArray.length, expectedLength, "Array length should match");
```

#### Collection Assertions
```java
// Verify collection size
Assert.assertEquals(list.size(), expectedSize, "List size should match");

// Verify collection contains element
Assert.assertTrue(list.contains(expectedElement), "List should contain element");

// Verify collection is empty
Assert.assertTrue(list.isEmpty(), "List should be empty");

// Verify collection is not empty
Assert.assertFalse(list.isEmpty(), "List should not be empty");
```

### 3. Hamcrest Matchers

#### Basic Matchers
```java
import static org.hamcrest.Matchers.*;

// equalTo
assertThat(actual, equalTo(expected));

// not
assertThat(actual, not(equalTo(unexpected)));

// nullValue
assertThat(value, nullValue());

// notNullValue
assertThat(value, notNullValue());

// sameInstance
assertThat(actual, sameInstance(expected));

// instanceOf
assertThat(value, instanceOf(String.class));
```

#### Number Matchers
```java
// closeTo (with delta)
assertThat(actual, closeTo(expected, 0.01));

// greaterThan
assertThat(actual, greaterThan(expected));

// greaterThanOrEqualTo
assertThat(actual, greaterThanOrEqualTo(expected));

// lessThan
assertThat(actual, lessThan(expected));

// lessThanOrEqualTo
assertThat(actual, lessThanOrEqualTo(expected));
```

#### String Matchers
```java
// containsString
assertThat(text, containsString("substring"));

// startsWith
assertThat(text, startsWith("prefix"));

// endsWith
assertThat(text, endsWith("suffix"));

// matchesPattern (regex)
assertThat(text, matchesPattern("\\d{3}-\\d{3}-\\d{4}"));

// isEmptyString
assertThat(text, isEmptyString());

// isString
assertThat(text, isString());
```

#### Collection Matchers
```java
// hasSize
assertThat(list, hasSize(5));

// contains
assertThat(list, contains("a", "b", "c"));

// containsInAnyOrder
assertThat(list, containsInAnyOrder("c", "a", "b"));

// hasItem
assertThat(list, hasItem("item"));

// hasItems
assertThat(list, hasItems("item1", "item2"));

// empty
assertThat(list, empty());

// not(empty())
assertThat(list, not(empty()));
```

#### Logical Matchers
```java
// allOf (AND)
assertThat(value, allOf(notNullValue(), greaterThan(0), lessThan(100)));

// anyOf (OR)
assertThat(value, anyOf(nullValue(), equalTo("N/A")));

// not
assertThat(value, not(equalTo("invalid")));
```

### 4. UI Assertions

#### Element Visibility
```java
// Verify element is displayed
Assert.assertTrue(loginPage.isUsernameFieldDisplayed(), 
    "Username field should be displayed");

// Verify element is not displayed
Assert.assertFalse(loginPage.isErrorMessageDisplayed(), 
    "Error message should not be displayed");

// Using Hamcrest
assertThat(loginPage.isUsernameFieldDisplayed(), is(true));
```

#### Element Text
```java
// Verify exact text
Assert.assertEquals(loginPage.getPageTitle(), "Login", 
    "Page title should be 'Login'");

// Verify text contains substring
Assert.assertTrue(loginPage.getWelcomeMessage().contains("Welcome"), 
    "Welcome message should contain 'Welcome'");

// Using Hamcrest
assertThat(loginPage.getPageTitle(), equalTo("Login"));
assertThat(loginPage.getWelcomeMessage(), containsString("Welcome"));
```

#### Element Attributes
```java
// Verify attribute value
Assert.assertEquals(loginPage.getAttribute("placeholder"), "Enter username", 
    "Placeholder should be 'Enter username'");

// Verify CSS property
Assert.assertEquals(loginPage.getCssValue("color"), "rgb(255, 0, 0)", 
    "Error message should be red");
```

#### Element State
```java
// Verify element is enabled
Assert.assertTrue(loginPage.isLoginButtonEnabled(), 
    "Login button should be enabled");

// Verify element is selected
Assert.assertTrue(loginPage.isRememberMeChecked(), 
    "Remember me checkbox should be checked");

// Verify element is clickable
Assert.assertTrue(loginPage.isLoginButtonClickable(), 
    "Login button should be clickable");
```

#### Page Navigation
```java
// Verify URL
Assert.assertTrue(driver.getCurrentUrl().contains("/dashboard"), 
    "Should navigate to dashboard");

// Verify page title
Assert.assertEquals(driver.getTitle(), "Dashboard", 
    "Page title should be 'Dashboard'");

// Verify page loaded
Assert.assertTrue(dashboardPage.isPageLoaded(), 
    "Dashboard page should be loaded");
```

### 5. API Assertions

#### Status Code Assertions
```java
// Verify status code
given()
    .header("Authorization", "Bearer " + token)
.when()
    .get(BASE_URL + "/api/users/1")
.then()
    .assertThat()
    .statusCode(200);

// Verify status code range
.then()
    .assertThat()
    .statusCode(allOf(greaterThanOrEqualTo(200), lessThan(300)));
```

#### Response Body Assertions
```java
// Verify field value
.then()
    .assertThat()
    .body("name", equalTo("John Doe"))
    .body("email", equalTo("john@example.com"));

// Verify field exists
.then()
    .assertThat()
    .body("id", notNullValue())
    .body("name", notNullValue());

// Verify array size
.then()
    .assertThat()
    .body("users.size()", equalTo(10));

// Verify array contains
.then()
    .assertThat()
    .body("users.name", hasItem("John"));

// Verify field type
.then()
    .assertThat()
    .body("age", instanceOf(Integer.class));
```

#### Response Header Assertions
```java
// Verify header exists
.then()
    .assertThat()
    .header("Content-Type", containsString("application/json"));

// Verify header value
.then()
    .assertThat()
    .header("X-Request-ID", notNullValue());
```

#### JSON Schema Validation
```java
// Validate against JSON schema
.then()
    .assertThat()
    .body(matchesJsonSchemaInClasspath("schemas/user-schema.json"));

// Validate with custom schema
String schema = "{"
    + "\"type\": \"object\","
    + "\"properties\": {"
    + "  \"id\": {\"type\": \"number\"},"
    + "  \"name\": {\"type\": \"string\"}"
    + "},"
    + "\"required\": [\"id\", \"name\"]"
    + "}";
.then()
    .assertThat()
    .body(matchesJsonSchema(schema));
```

### 6. Database Assertions

#### Record Count Assertions
```java
// Verify record count
int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users", Integer.class);
Assert.assertEquals(count, expectedCount, "User count should match");

// Verify record exists
Assert.assertTrue(userExists(userId), "User should exist in database");
```

#### Field Value Assertions
```java
// Verify field value
String email = jdbcTemplate.queryForObject(
    "SELECT email FROM users WHERE id = ?", 
    new Object[]{userId}, 
    String.class
);
Assert.assertEquals(email, "john@example.com", "Email should match");

// Verify multiple fields
User user = getUserFromDb(userId);
Assert.assertAll(
    "User validation",
    () -> Assert.assertEquals(user.getName(), "John", "Name should match"),
    () -> Assert.assertEquals(user.getEmail(), "john@example.com", "Email should match"),
    () -> Assert.assertEquals(user.getAge(), 30, "Age should match")
);
```

### 7. Soft Assertions

#### Using SoftAssert
```java
// Soft assertions - collect all failures
SoftAssert softAssert = new SoftAssert();

softAssert.assertEquals(actual1, expected1, "First assertion");
softAssert.assertTrue(condition2, "Second assertion");
softAssert.assertNotNull(value3, "Third assertion");

// Report all failures at once
softAssert.assertAll();

// Usage in test
@Test
public void testMultipleAssertions() {
    SoftAssert softAssert = new SoftAssert();
    
    softAssert.assertEquals(user.getName(), "John", "Name should match");
    softAssert.assertEquals(user.getEmail(), "john@example.com", "Email should match");
    softAssert.assertTrue(user.isActive(), "User should be active");
    
    softAssert.assertAll(); // Reports all failures
}
```

### 8. Custom Assertions

#### Custom Assertion Methods
```java
// Custom assertion for email validation
public static void assertValidEmail(String email) {
    Assert.assertNotNull(email, "Email should not be null");
    Assert.assertTrue(email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"), 
        "Email should be valid: " + email);
}

// Custom assertion for phone number
public static void assertValidPhone(String phone) {
    Assert.assertNotNull(phone, "Phone should not be null");
    Assert.assertTrue(phone.matches("\\d{10}"), 
        "Phone should be 10 digits: " + phone);
}

// Usage
assertValidEmail(user.getEmail());
assertValidPhone(user.getPhone());
```

#### Assertion Utility Class
```java
public class TestAssertions {
    
    public static void assertUserValid(User user) {
        Assert.assertAll("User validation",
            () -> Assert.assertNotNull(user.getId(), "ID should not be null"),
            () -> Assert.assertNotNull(user.getName(), "Name should not be null"),
            () -> Assert.assertNotNull(user.getEmail(), "Email should not be null"),
            () -> assertValidEmail(user.getEmail()),
            () -> Assert.assertTrue(user.getAge() > 0, "Age should be positive")
        );
    }
    
    public static void assertResponseValid(Response response) {
        Assert.assertAll("Response validation",
            () -> Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200"),
            () -> Assert.assertNotNull(response.getBody(), "Response body should not be null"),
            () -> Assert.assertTrue(response.getTime() < 2000, "Response time should be < 2s")
        );
    }
}

// Usage
TestAssertions.assertUserValid(user);
TestAssertions.assertResponseValid(response);
```

### 9. Assertion Best Practices

#### DO's
✓ Always provide descriptive error messages
✓ Assert early and often
✓ Use soft assertions for multiple validations
✓ Validate both positive and negative cases
✓ Include expected vs actual values in messages
✓ Use appropriate assertion type (assertEquals vs assertTrue)
✓ Group related assertions with assertAll
✓ Create custom assertions for reusable validations

#### DON'Ts
✗ Don't use empty assertion messages
✗ Don't assert on non-deterministic values (timestamps, random)
✗ Don't use assertions for control flow
✗ Don't skip assertions to make tests pass
✗ Don't use generic messages ("Test failed")
✗ Don't assert on implementation details
✗ Don't forget to call assertAll() for soft assertions

## Code Templates

### Template 1: UI Test Assertions
```java
@Test
public void testLoginPage() {
    // Navigate to login page
    navigateToBaseUrl();
    LoginPage loginPage = new LoginPage(WebDriverFactory.getDriver());
    
    // Assert page loaded
    Assert.assertTrue(loginPage.isLoginPageLoaded(), 
        "Login page should be loaded with all elements");
    
    // Assert element visibility
    Assert.assertTrue(loginPage.isUsernameFieldDisplayed(), 
        "Username field should be displayed");
    Assert.assertTrue(loginPage.isPasswordFieldDisplayed(), 
        "Password field should be displayed");
    Assert.assertTrue(loginPage.isLoginButtonDisplayed(), 
        "Login button should be displayed");
    
    // Assert page title
    Assert.assertEquals(loginPage.getPageTitle(), "Login", 
        "Page title should be 'Login'");
}
```

### Template 2: API Test Assertions
```java
@Test
public void testGetUser() {
    given()
        .header("Authorization", "Bearer " + token)
    .when()
        .get(BASE_URL + "/api/users/1")
    .then()
        .assertThat()
        .statusCode(200)
        .body("id", equalTo(1))
        .body("name", notNullValue())
        .body("email", matchesPattern("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"))
        .body("age", greaterThan(0))
        .body("status", equalTo("active"));
}
```

### Template 3: Soft Assertions
```java
@Test
public void testUserProfile() {
    SoftAssert softAssert = new SoftAssert();
    
    // Navigate to profile
    navigateToBaseUrl();
    ProfilePage profilePage = new ProfilePage(WebDriverFactory.getDriver());
    
    // Multiple assertions
    softAssert.assertTrue(profilePage.isProfilePageLoaded(), 
        "Profile page should be loaded");
    softAssert.assertEquals(profilePage.getUserName(), "John Doe", 
        "User name should match");
    softAssert.assertTrue(profilePage.getUserEmail().contains("@"), 
        "Email should be valid");
    softAssert.assertTrue(profilePage.isProfilePictureDisplayed(), 
        "Profile picture should be displayed");
    
    // Report all failures
    softAssert.assertAll();
}
```

### Template 4: Custom Assertions
```java
@Test
public void testUserRegistration() {
    // Register user
    User user = registerUser(testData);
    
    // Custom assertions
    TestAssertions.assertUserValid(user);
    TestAssertions.assertEmailSent(user.getEmail());
    
    // Verify in database
    User dbUser = getUserFromDatabase(user.getId());
    TestAssertions.assertUserValid(dbUser);
}
```

### Template 5: Database Assertions
```java
@Test
public void testUserCreation() {
    // Create user
    int userId = createUser(testData);
    
    // Verify in database
    User user = getUserFromDatabase(userId);
    
    Assert.assertAll("User creation validation",
        () -> Assert.assertNotNull(user, "User should exist in database"),
        () -> Assert.assertEquals(user.getName(), testData.getName(), "Name should match"),
        () -> Assert.assertEquals(user.getEmail(), testData.getEmail(), "Email should match"),
        () -> Assert.assertEquals(user.getStatus(), "active", "Status should be active"),
        () -> Assert.assertNotNull(user.getCreatedAt(), "Created timestamp should exist")
    );
}
```

## Common Assertion Patterns

### Pattern 1: String Validation
```java
// Exact match
Assert.assertEquals(actual, expected);

// Contains
Assert.assertTrue(actual.contains(expected));

// Starts with
Assert.assertTrue(actual.startsWith(prefix));

// Ends with
Assert.assertTrue(actual.endsWith(suffix));

// Regex match
Assert.assertTrue(actual.matches(regex));

// Case insensitive
Assert.assertEqualsIgnoreCase(actual, expected);
```

### Pattern 2: Numeric Validation
```java
// Equality
Assert.assertEquals(actual, expected);

// Range
Assert.assertTrue(actual >= min && actual <= max);

// Greater than
Assert.assertTrue(actual > expected);

// Comparison
Assert.assertTrue(actual > expected);
```

### Pattern 3: Collection Validation
```java
// Size
Assert.assertEquals(list.size(), expectedSize);

// Contains
Assert.assertTrue(list.contains(item));

// Empty
Assert.assertTrue(list.isEmpty());

// Not empty
Assert.assertFalse(list.isEmpty());
```

### Pattern 4: Object Validation
```java
// Not null
Assert.assertNotNull(object);

// Equals
Assert.assertEquals(expected, actual);

// Same instance
Assert.assertSame(expected, actual);

// Type
Assert.assertTrue(object instanceof ExpectedClass);
```

## Assertion Messages Best Practices

### Good Messages
```java
// Include expected and actual
Assert.assertEquals(actual, expected, 
    "Expected price to be {} but found {}", expected, actual);

// Include context
Assert.assertTrue(user.isActive(), 
    "User should be active after registration. User ID: " + user.getId());

// Include values
Assert.assertEquals(order.getStatus(), "shipped", 
    "Order status should be 'shipped'. Actual status: " + order.getStatus());
```

### Bad Messages
```java
// Too vague
Assert.assertEquals(actual, expected, "Test failed");

// No message
Assert.assertEquals(actual, expected);

// Unhelpful
Assert.assertTrue(condition, "Assertion failed");
```

## Troubleshooting

### Common Issues
1. **Flaky Assertions**: Use explicit waits before asserting
2. **Timing Issues**: Add appropriate waits
3. **Null Values**: Check for null before asserting
4. **Wrong Assertion Type**: Use appropriate assertion method
5. **Poor Error Messages**: Make messages descriptive

### Debugging Failed Assertions
```java
// Log values before assertion
logger.info("Expected: {}, Actual: {}", expected, actual);

// Use descriptive messages
Assert.assertEquals(actual, expected, 
    "Detailed error message with context");

// Use soft assertions to see all failures
SoftAssert softAssert = new SoftAssert();
// ... multiple assertions
softAssert.assertAll();
```

## Additional Notes
- Choose the right assertion type for the validation
- Always provide meaningful error messages
- Use soft assertions for multiple related validations
- Create custom assertions for reusable validations
- Keep assertions focused and specific
- Don't over-assert (focus on critical validations)
- Make tests fail fast with early assertions
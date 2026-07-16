# Create Test Data

## Role
You are an expert test automation engineer specializing in test data management and generation.

## Task
Create comprehensive test data for automation tests following best practices and framework standards.

## Context
- **Framework**: Selenium WebDriver + TestNG + Java 11
- **Purpose**: Provide test data for UI and API tests
- **Formats**: Excel, CSV, JSON, Properties
- **Location**: `src/test/resources/test-data/` directory

## Requirements

### Test Data Information
- **Data Type**: [Excel, CSV, JSON, Properties]
- **Test Scenario**: [Login, Registration, Contact Form, etc.]
- **Data Volume**: [Number of test cases/data rows]
- **Data Types**: [Valid, Invalid, Edge Cases, Boundary Values]

### Data Categories
[List the types of test data needed:]
- **Valid Data**: Normal, expected inputs
- **Invalid Data**: Invalid formats, out of range values
- **Edge Cases**: Boundary values, special characters
- **Negative Data**: Error scenarios
- **Dynamic Data**: Random data, timestamps

## Instructions

### 1. Test Data Organization
Organize test data by:
- **Feature/Module**: Separate files for each feature
- **Test Type**: Valid, invalid, edge cases
- **Data Format**: Choose appropriate format (Excel for complex, CSV for simple, JSON for nested)

### 2. File Naming Convention
```
test-data/
├── login-test-data.xlsx          # Excel for complex data
├── login-test-data.csv           # CSV for simple data
├── contact-test-data.json        # JSON for nested data
├── user-profiles.json            # JSON for complex objects
├── test-config.properties        # Configuration data
└── invalid-credentials.csv       # Negative test data
```

### 3. Data Formats

#### Excel Format (.xlsx)
Best for:
- Multiple test scenarios
- Complex data structures
- Multiple sheets for different test types
- Data-driven tests with TestNG DataProvider

**Structure:**
```
Sheet: ValidLogin
| username | password | expected_result |
|----------|----------|-----------------|
| user1    | pass1    | success         |
| user2    | pass2    | success         |

Sheet: InvalidLogin
| username | password | expected_error |
|----------|----------|----------------|
| invalid  | wrong    | Invalid credentials |
| empty    | pass     | Username required  |
```

#### CSV Format (.csv)
Best for:
- Simple tabular data
- Large datasets
- Easy to edit
- Quick data entry

**Structure:**
```csv
username,password,email,expected_result
user1,pass1,user1@example.com,success
user2,pass2,user2@example.com,success
invalid,wrong,invalid@example.com,failure
```

#### JSON Format (.json)
Best for:
- Complex nested data
- API test data
- Configuration data
- Hierarchical structures

**Structure:**
```json
{
  "validUsers": [
    {
      "username": "user1",
      "password": "pass1",
      "email": "user1@example.com",
      "profile": {
        "firstName": "John",
        "lastName": "Doe",
        "age": 30
      }
    }
  ],
  "invalidUsers": [
    {
      "username": "",
      "password": "pass",
      "expectedError": "Username is required"
    }
  ]
}
```

#### Properties Format (.properties)
Best for:
- Configuration data
- Simple key-value pairs
- Environment-specific data

**Structure:**
```properties
# Valid credentials
valid.username=testuser
valid.password=Test@123
valid.email=test@example.com

# Invalid credentials
invalid.username=invaliduser
invalid.password=wrongpass

# Test configuration
test.retry.count=3
test.timeout=30
```

### 4. Test Data Design Principles

#### Valid Data
- **Normal Cases**: Typical, expected inputs
- **Happy Path**: Most common user scenarios
- **Examples**:
  - Valid username/password combinations
  - Valid email formats
  - Valid form inputs within range

#### Invalid Data
- **Invalid Formats**: Wrong data types, formats
- **Out of Range**: Values beyond acceptable limits
- **Empty Values**: Null, empty strings
- **Special Characters**: SQL injection, XSS attempts
- **Examples**:
  - Invalid email formats
  - Passwords too short/long
  - Special characters in name fields

#### Edge Cases
- **Boundary Values**: Min/max values
- **Empty Strings**: Zero-length inputs
- **Very Long Strings**: Maximum length testing
- **Special Characters**: Unicode, emojis, etc.
- **Examples**:
  - Password with exactly 8 characters (min)
  - Username with 255 characters (max)
  - Email with +, -, . characters

#### Negative Data
- **Error Scenarios**: Expected failures
- **Security Testing**: Injection attacks
- **Examples**:
  - SQL injection attempts
  - XSS payloads
  - Invalid authentication

### 5. Data Generation Strategies

#### Static Data
- Predefined, fixed values
- Used for regression tests
- Stored in files

#### Dynamic Data
- Generated at runtime
- Random values
- Timestamps
- Unique identifiers

**Example Dynamic Data Generation:**
```java
// Random email
String email = "test" + System.currentTimeMillis() + "@example.com";

// Random phone number
String phone = "9" + new Random().nextInt(1000000000);

// Current timestamp
String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

// Random string
String randomStr = UUID.randomUUID().toString().substring(0, 8);
```

### 6. Test Data Management

#### Data Storage
- **External Files**: Excel, CSV, JSON, XML
- **Database**: Test database with test data
- **APIs**: Generate data via API calls
- **Properties**: Configuration data

#### Data Maintenance
- **Version Control**: Keep test data in Git
- **Data Refresh**: Regularly update test data
- **Data Cleanup**: Clean up after test execution
- **Data Isolation**: Separate data per environment

#### Data Security
- **No Production Data**: Never use real user data
- **Masking**: Mask sensitive information
- **Rotation**: Regularly rotate credentials
- **Environment Specific**: Different data per environment

## Code Templates

### Excel Data Provider
```java
@DataProvider(name = "excelData")
public Object[][] getExcelData(String fileName, String sheetName) {
    return ExcelReader.getData(fileName, sheetName);
}

@Test(dataProvider = "excelData")
public void testWithExcelData(String username, String password, String expectedResult) {
    // Test implementation
}
```

### CSV Data Provider
```java
@DataProvider(name = "csvData")
public Object[][] getCsvData(String fileName) {
    return CsvReader.readCsv(fileName);
}

@Test(dataProvider = "csvData")
public void testWithCsvData(String username, String password, String expectedResult) {
    // Test implementation
}
```

### JSON Data Provider
```java
@DataProvider(name = "jsonData")
public Object[][] getJsonData(String fileName, String key) {
    return JsonReader.getData(fileName, key);
}

@Test(dataProvider = "jsonData")
public void testWithJsonData(String username, String password) {
    // Test implementation
}
```

### Properties Data
```java
// Read from properties file
String username = ConfigReader.getProperty("valid.username");
String password = ConfigReader.getProperty("valid.password");
```

## Examples

### Example 1: Login Test Data (CSV)
**File**: `login-test-data.csv`
```csv
username,password,expected_result,test_type
valid_user,ValidPass123,success,positive
invalid_user,wrongpass,failure,negative
,,failure,empty_username
user1,,failure,empty_password
user1@example.com,pass123,success,positive
user1@invalid,pass123,failure,invalid_email
```

### Example 2: User Registration Data (JSON)
**File**: `registration-test-data.json`
```json
{
  "validUsers": [
    {
      "firstName": "John",
      "lastName": "Doe",
      "email": "john.doe@example.com",
      "password": "SecurePass123!",
      "phone": "1234567890"
    }
  ],
  "invalidUsers": [
    {
      "firstName": "",
      "lastName": "Doe",
      "email": "john@example.com",
      "password": "pass",
      "expectedError": "First name is required"
    },
    {
      "firstName": "John",
      "lastName": "Doe",
      "email": "invalid-email",
      "password": "pass",
      "expectedError": "Invalid email format"
    }
  ],
  "edgeCases": [
    {
      "firstName": "A",
      "lastName": "B",
      "email": "a@b.c",
      "password": "P@1",
      "description": "Minimum length values"
    }
  ]
}
```

### Example 3: Contact Form Data (Excel)
**File**: `contact-test-data.xlsx`

**Sheet: ValidSubmissions**
| name | email | subject | message | expected_result |
|------|-------|---------|---------|-----------------|
| John Doe | john@example.com | Test Subject | Test message | success |
| Jane Smith | jane@example.com | Inquiry | Hello | success |

**Sheet: InvalidSubmissions**
| name | email | subject | message | expected_error |
|------|-------|---------|---------|----------------|
| | test@example.com | Subject | Message | Name is required |
| John | invalid-email | Subject | Message | Invalid email |

### Example 4: API Test Data (JSON)
**File**: `api-test-data.json`
```json
{
  "users": {
    "valid": {
      "name": "John Doe",
      "email": "john@example.com",
      "age": 30
    },
    "invalid": {
      "name": "",
      "email": "invalid",
      "age": -5
    }
  },
  "endpoints": {
    "getUser": {
      "url": "/api/users/{id}",
      "validIds": [1, 2, 3],
      "invalidIds": [99999, -1, 0]
    }
  }
}
```

### Example 5: Configuration Data (Properties)
**File**: `test-config.properties`
```properties
# Test environment
test.environment=qa
test.base.url=https://qa.example.com
test.api.url=https://qa-api.example.com

# Test credentials
test.user.username=testuser
test.user.password=Test@123
test.user.email=test@example.com

# Test settings
test.browser=chrome
test.timeout=30
test.retry.count=2
test.headless=false

# API configuration
api.auth.token=Bearer abc123xyz
api.content.type=application/json
```

## Best Practices

### DO's
✓ Use meaningful data that represents real scenarios
✓ Include both positive and negative test cases
✓ Include edge cases and boundary values
✓ Keep test data separate from test logic
✓ Use external files for large datasets
✓ Version control test data
✓ Document data purpose and usage
✓ Use environment-specific data
✓ Clean up test data after execution
✓ Generate unique data for each test run

### DON'Ts
✗ Don't use production data
✗ Don't hardcode test data in test methods
✗ Don't reuse test data across environments
✗ Don't include sensitive data in version control
✗ Don't create dependencies between test data
✗ Don't use predictable data (same email, username)
✗ Don't forget to handle data cleanup

## Test Data Patterns

### Pattern 1: Data-Driven Testing
```java
@DataProvider(name = "loginData")
public Object[][] getLoginData() {
    return CsvReader.readCsv("test-data/login-test-data.csv");
}

@Test(dataProvider = "loginData")
public void testLogin(String username, String password, String expectedResult) {
    // Test implementation
}
```

### Pattern 2: Dynamic Data Generation
```java
public String generateUniqueEmail() {
    return "test" + System.currentTimeMillis() + "@example.com";
}

public String generateRandomPhone() {
    return "9" + new Random().nextInt(1000000000);
}
```

### Pattern 3: Data Builder Pattern
```java
public class UserTestDataBuilder {
    private String username;
    private String email;
    private String password;
    
    public UserTestDataBuilder withUsername(String username) {
        this.username = username;
        return this;
    }
    
    public UserTestDataBuilder withEmail(String email) {
        this.email = email;
        return this;
    }
    
    public UserTestDataBuilder withRandomData() {
        this.username = generateRandomString(8);
        this.email = generateRandomEmail();
        this.password = generateRandomString(12);
        return this;
    }
    
    public User build() {
        return new User(username, email, password);
    }
}

// Usage
User user = new UserTestDataBuilder()
    .withRandomData()
    .build();
```

### Pattern 4: Test Data Factory
```java
public class TestDataFactory {
    
    public static User createValidUser() {
        return new User("testuser", "test@example.com", "Test@123");
    }
    
    public static User createInvalidUser() {
        return new User("", "invalid-email", "123");
    }
    
    public static List<User> createMultipleUsers(int count) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            users.add(createValidUser());
        }
        return users;
    }
}
```

## Data Validation

### Validate Test Data
- Check data format (email, phone, etc.)
- Verify data ranges (min/max values)
- Ensure data uniqueness
- Validate required fields
- Check data types

### Data Cleanup
```java
@AfterMethod
public void cleanupTestData(ITestResult result) {
    // Clean up created test data
    if (result.getStatus() == ITestResult.FAILURE) {
        // Capture data for debugging
    }
    // Delete test user
    apiClient.deleteUser(testUserId);
}
```

## File Structure

### Recommended Structure
```
src/test/resources/test-data/
├── ui/
│   ├── login/
│   │   ├── valid-login.csv
│   │   ├── invalid-login.csv
│   │   └── edge-cases.csv
│   ├── contact/
│   │   └── contact-form.xlsx
│   └── registration/
│       └── user-registration.json
├── api/
│   ├── users/
│   │   ├── valid-users.json
│   │   └── invalid-users.json
│   └── products/
│       └── products.csv
└── config/
    ├── test-config.properties
    └── environment.properties
```

## Additional Notes
- Choose appropriate format based on data complexity
- Keep test data maintainable and organized
- Use meaningful file and sheet names
- Document test data purpose
- Regularly review and update test data
- Consider using test data management tools
- Implement data generation utilities for dynamic data
- Ensure test data is environment-agnostic
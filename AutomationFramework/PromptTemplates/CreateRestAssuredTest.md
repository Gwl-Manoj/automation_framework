# Create Rest Assured Test

## Role
You are an expert test automation engineer specializing in API testing with Rest Assured and TestNG.

## Task
Create a comprehensive API test using Rest Assured following framework standards and best practices.

## Context
- **Framework**: Rest Assured + TestNG + Java 11
- **Base Class**: All API tests extend `BaseTest` or `BaseApiTest`
- **Assertions**: Use TestNG assertions with JSON schema validation
- **Reporting**: Integrated with Extent Reports

## Requirements

### Test Information
- **Test Name**: [Provide test name]
- **Test Description**: [Provide description for TestNG @Test annotation]
- **Test Groups**: [e.g., smoke, regression, api, users]
- **Priority**: [1-10, if applicable]
- **API Endpoint**: [Full URL or relative path]
- **HTTP Method**: [GET, POST, PUT, DELETE, PATCH]

### Test Scenario
[Describe the API test scenario in detail, including:]
1. Preconditions (authentication, test data, etc.)
2. Request details (headers, body, parameters)
3. Expected response (status code, response body, headers)
4. Validation points

### Request Details
- **Headers**: [Content-Type, Authorization, etc.]
- **Request Body**: [JSON payload, form data, etc.]
- **Query Parameters**: [Any query parameters]
- **Path Parameters**: [Any path variables]
- **Authentication**: [Bearer token, Basic Auth, API Key, etc.]

### Expected Response
- **Status Code**: [200, 201, 400, 401, 404, 500, etc.]
- **Response Body**: [Expected JSON structure]
- **Response Headers**: [Expected headers]
- **Schema Validation**: [JSON schema if applicable]

## Instructions

### 1. Test Class Structure
Create an API test class that:
- Extends `BaseTest` or `BaseApiTest`
- Is placed in `src/test/java/com/automation/tests/api/` package
- Follows naming convention: `[Resource]ApiTest.java`
- Contains only test methods

### 2. Test Method Structure
Each test method should:
- Have clear descriptive name: `test[Scenario][Condition]()`
- Include TestNG annotations
- Follow this pattern:
  ```java
  @Test(description = "...", groups = {...})
  public void testMethodName() {
      // 1. Setup (auth, base URL, etc.)
      String baseUrl = ConfigReader.getApiBaseUrl();
      String token = ConfigReader.getApiToken();
      
      // 2. Build request
      given()
          .header("Authorization", "Bearer " + token)
          .contentType(ContentType.JSON)
          .body(requestBody)
      .when()
          .get(baseUrl + "/endpoint")
      .then()
          .assertThat()
          .statusCode(200)
          .body("name", equalTo("John"));
  }
  ```

### 3. Request Building
Use Rest Assured's given-when-then pattern:
- **given()**: Set up request (headers, params, body)
- **when()**: Execute request (get, post, put, delete)
- **then()**: Validate response (status, body, headers)

### 4. Assertions
- Validate status code
- Validate response body fields
- Validate response headers
- Use JSON schema validation when applicable
- Use TestNG assertions for complex validations

### 5. Logging
- Log request details
- Log response details
- Log validation results

## Code Template

```java
package com.automation.tests.api;

import com.automation.base.BaseTest;
import com.automation.utils.ConfigReader;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

/**
 * [Resource] API Test Class
 * API tests for [resource] endpoints
 */
public class [Resource]ApiTest extends BaseTest {

    private static final String BASE_URL = ConfigReader.getApiBaseUrl();
    private static final String AUTH_TOKEN = ConfigReader.getApiToken();

    /**
     * Test [scenario description]
     */
    @Test(description = "[Detailed test description]", 
          groups = {"api", "[group]"})
    public void test[Scenario]() {
        logger.info("Starting API test: [Test Name]");

        // Build and execute request
        given()
            .header("Authorization", "Bearer " + AUTH_TOKEN)
            .contentType(ContentType.JSON)
            .body("[request body]")
        .when()
            .[method]("[endpoint]")
        .then()
            .assertThat()
            .statusCode([expected-status-code])
            .body("[field]", equalTo("[expected-value]"));
    }
}
```

## Best Practices

### DO's
✓ Use Rest Assured's given-when-then pattern
✓ Store base URL and credentials in config
✓ Validate status code, body, and headers
✓ Use JSON schema validation
✓ Log request and response details
✓ Handle authentication properly
✓ Use environment-specific configs
✓ Clean up test data after tests

### DON'Ts
✗ Don't hardcode URLs or credentials
✗ Don't skip status code validation
✗ Don't ignore response validation
✗ Don't use Thread.sleep()
✗ Don't create dependencies between tests
✗ Don't log sensitive data (passwords, tokens)

## Example

### Input
```
Test Name: UserApiTest
Endpoint: GET /api/users/{id}
Method: GET
Authentication: Bearer token
Expected: Status 200, user object with name, email
Groups: api, users, smoke
```

### Output
```java
package com.automation.tests.api;

import com.automation.base.BaseTest;
import com.automation.utils.ConfigReader;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

/**
 * User API Test Class
 * API tests for user endpoints
 */
public class UserApiTest extends BaseTest {

    private static final String BASE_URL = ConfigReader.getApiBaseUrl();
    private static final String AUTH_TOKEN = ConfigReader.getApiToken();

    /**
     * Test get user by ID
     */
    @Test(description = "Verify get user by ID returns valid user data", 
          groups = {"api", "users", "smoke"})
    public void testGetUserById() {
        logger.info("Starting API test: Get User By ID");

        int userId = 1;

        given()
            .header("Authorization", "Bearer " + AUTH_TOKEN)
            .contentType(ContentType.JSON)
        .when()
            .get(BASE_URL + "/api/users/{id}", userId)
        .then()
            .assertThat()
            .statusCode(200)
            .body("id", equalTo(userId))
            .body("name", notNullValue())
            .body("email", notNullValue())
            .body("email", matchesPattern("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"));
    }
}
```

## Common Patterns

### Pattern 1: GET Request
```java
@Test
public void testGetRequest() {
    given()
        .header("Authorization", "Bearer " + token)
    .when()
        .get(BASE_URL + "/api/users")
    .then()
        .assertThat()
        .statusCode(200)
        .body("users", notNullValue());
}
```

### Pattern 2: POST Request
```java
@Test
public void testPostRequest() {
    Map<String, Object> requestBody = new HashMap<>();
    requestBody.put("name", "John Doe");
    requestBody.put("email", "john@example.com");

    given()
        .header("Authorization", "Bearer " + token)
        .contentType(ContentType.JSON)
        .body(requestBody)
    .when()
        .post(BASE_URL + "/api/users")
    .then()
        .assertThat()
        .statusCode(201)
        .body("id", notNullValue())
        .body("name", equalTo("John Doe"));
}
```

### Pattern 3: PUT Request
```java
@Test
public void testPutRequest() {
    Map<String, Object> requestBody = new HashMap<>();
    requestBody.put("name", "Jane Doe");

    given()
        .header("Authorization", "Bearer " + token)
        .contentType(ContentType.JSON)
        .body(requestBody)
    .when()
        .put(BASE_URL + "/api/users/{id}", 1)
    .then()
        .assertThat()
        .statusCode(200)
        .body("name", equalTo("Jane Doe"));
}
```

### Pattern 4: DELETE Request
```java
@Test
public void testDeleteRequest() {
    given()
        .header("Authorization", "Bearer " + token)
    .when()
        .delete(BASE_URL + "/api/users/{id}", 1)
    .then()
        .assertThat()
        .statusCode(204);
}
```

### Pattern 5: Query Parameters
```java
@Test
public void testQueryParameters() {
    given()
        .header("Authorization", "Bearer " + token)
        .queryParam("page", 1)
        .queryParam("limit", 10)
        .queryParam("sort", "name")
    .when()
        .get(BASE_URL + "/api/users")
    .then()
        .assertThat()
        .statusCode(200)
        .body("users.size()", equalTo(10));
}
```

### Pattern 6: Path Parameters
```java
@Test
public void testPathParameters() {
    given()
        .header("Authorization", "Bearer " + token)
        .pathParam("id", 1)
    .when()
        .get(BASE_URL + "/api/users/{id}")
    .then()
        .assertThat()
        .statusCode(200);
}
```

### Pattern 7: Form Data
```java
@Test
public void testFormData() {
    given()
        .header("Authorization", "Bearer " + token)
        .contentType(ContentType.URLENC)
        .formParam("username", "testuser")
        .formParam("password", "testpass")
    .when()
        .post(BASE_URL + "/api/auth/login")
    .then()
        .assertThat()
        .statusCode(200);
}
```

### Pattern 8: File Upload
```java
@Test
public void testFileUpload() {
    given()
        .header("Authorization", "Bearer " + token)
        .multiPart("file", new File("test-data/sample.pdf"))
    .when()
        .post(BASE_URL + "/api/upload")
    .then()
        .assertThat()
        .statusCode(200);
}
```

### Pattern 9: JSON Schema Validation
```java
@Test
public void testJsonSchemaValidation() {
    given()
        .header("Authorization", "Bearer " + token)
    .when()
        .get(BASE_URL + "/api/users/1")
    .then()
        .assertThat()
        .statusCode(200)
        .body(matchesJsonSchemaInClasspath("schemas/user-schema.json"));
}
```

### Pattern 10: Error Handling
```java
@Test
public void testErrorResponse() {
    given()
        .header("Authorization", "Bearer " + token)
    .when()
        .get(BASE_URL + "/api/users/99999")
    .then()
        .assertThat()
        .statusCode(404)
        .body("error", equalTo("User not found"))
        .body("message", notNullValue());
}
```

## Request/Response Logging

### Log Request and Response
```java
@Test
public void testWithLogging() {
    given()
        .header("Authorization", "Bearer " + token)
        .log().all()  // Log request
    .when()
        .get(BASE_URL + "/api/users")
    .then()
        .log().all()  // Log response
        .assertThat()
        .statusCode(200);
}
```

### Conditional Logging
```java
@Test
public void testWithConditionalLogging() {
    given()
        .header("Authorization", "Bearer " + token)
        .log().ifValidationFails()  // Log only if validation fails
    .when()
        .get(BASE_URL + "/api/users")
    .then()
        .log().ifValidationFails()
        .assertThat()
        .statusCode(200);
}
```

## Authentication Patterns

### Bearer Token
```java
given()
    .header("Authorization", "Bearer " + token)
```

### Basic Auth
```java
given()
    .auth().preemptive().basic("username", "password")
```

### API Key
```java
given()
    .header("X-API-Key", apiKey)
```

### OAuth 2.0
```java
given()
    .auth().oauth2(accessToken)
```

## Configuration

### Config Properties
```properties
# config.properties
api.base.url=https://api.example.com
api.auth.token=your-bearer-token
api.username=testuser
api.password=testpass
api.timeout=30
```

### ConfigReader Methods
```java
ConfigReader.getApiBaseUrl()
ConfigReader.getApiToken()
ConfigReader.getApiUsername()
ConfigReader.getApiPassword()
ConfigReader.getApiTimeout()
```

## Additional Notes
- Always validate status code first
- Use JSON schema for complex response validation
- Handle authentication properly
- Clean up test data after tests
- Use environment-specific configurations
- Log request/response for debugging
- Handle rate limiting and timeouts
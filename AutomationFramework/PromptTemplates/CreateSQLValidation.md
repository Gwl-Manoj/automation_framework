# Create SQL Validation

## Role
You are an expert test automation engineer specializing in database testing and SQL validation.

## Task
Create comprehensive SQL validation strategies and implementations for database testing following best practices.

## Context
- **Framework**: Selenium WebDriver + TestNG + Java 11
- **Database**: [MySQL, PostgreSQL, Oracle, SQL Server, etc.]
- **Purpose**: Validate data integrity, business logic, and database operations
- **Scope**: UI tests, API tests, database tests, ETL validation

## Requirements

### Validation Information
- **Database Type**: [MySQL, PostgreSQL, Oracle, etc.]
- **Validation Type**: [Data Integrity, Business Logic, ETL, Migration]
- **Test Scenario**: [User creation, Order processing, etc.]
- **Tables Involved**: [List of tables to validate]

### Validation Points
[List what needs to be validated:]
- **Data Insert**: Verify records inserted correctly
- **Data Update**: Verify records updated correctly
- **Data Delete**: Verify records deleted correctly
- **Data Integrity**: Foreign keys, constraints, triggers
- **Business Logic**: Stored procedures, functions, views
- **Data Consistency**: Cross-table validation

## Instructions

### 1. Database Connection Setup

#### JDBC Connection
```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static Connection connection;
    
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            String url = ConfigReader.getDbUrl();
            String username = ConfigReader.getDbUsername();
            String password = ConfigReader.getDbPassword();
            String driver = ConfigReader.getDbDriver();
            
            try {
                Class.forName(driver);
                connection = DriverManager.getConnection(url, username, password);
            } catch (ClassNotFoundException e) {
                throw new SQLException("Database driver not found: " + driver, e);
            }
        }
        return connection;
    }
    
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("Failed to close database connection", e);
            }
        }
    }
}
```

#### Configuration Properties
```properties
# config.properties
db.driver=com.mysql.cj.jdbc.Driver
db.url=jdbc:mysql://localhost:3306/testdb
db.username=testuser
db.password=testpass
db.pool.size=10
db.connection.timeout=30
```

### 2. SQL Validation Patterns

#### Pattern 1: Record Count Validation
```java
/**
 * Validate record count in table
 */
public int getUserCount() throws SQLException {
    String query = "SELECT COUNT(*) FROM users";
    try (Connection conn = DatabaseConnection.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(query)) {
        
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }
}

// Usage in test
@Test
public void testUserCreation() {
    int initialCount = getUserCount();
    
    // Create user via UI/API
    createUser(testData);
    
    // Verify count increased
    int finalCount = getUserCount();
    Assert.assertEquals(finalCount, initialCount + 1, 
        "User count should increase by 1");
}
```

#### Pattern 2: Field Value Validation
```java
/**
 * Validate specific field value
 */
public String getUserEmail(int userId) throws SQLException {
    String query = "SELECT email FROM users WHERE id = ?";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query)) {
        
        pstmt.setInt(1, userId);
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getString("email");
            }
            return null;
        }
    }
}

// Usage in test
@Test
public void testUserEmailUpdate() {
    int userId = createUser(testData);
    
    // Update email via UI/API
    updateUserEmail(userId, "newemail@example.com");
    
    // Verify in database
    String email = getUserEmail(userId);
    Assert.assertEquals(email, "newemail@example.com", 
        "Email should be updated in database");
}
```

#### Pattern 3: Complex Query Validation
```java
/**
 * Validate complex query result
 */
public List<User> getActiveUsers() throws SQLException {
    String query = "SELECT id, name, email, created_at FROM users " +
                   "WHERE status = 'active' AND created_at >= ? " +
                   "ORDER BY created_at DESC";
    
    List<User> users = new ArrayList<>();
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query)) {
        
        pstmt.setDate(1, new Date(System.currentTimeMillis() - 86400000)); // Last 24 hours
        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setCreatedAt(rs.getTimestamp("created_at"));
                users.add(user);
            }
        }
    }
    return users;
}

// Usage in test
@Test
public void testActiveUsersQuery() {
    List<User> activeUsers = getActiveUsers();
    
    Assert.assertFalse(activeUsers.isEmpty(), 
        "Should have active users in last 24 hours");
    Assert.assertTrue(activeUsers.size() > 0, 
        "Active users count should be greater than 0");
}
```

#### Pattern 4: Data Integrity Validation
```java
/**
 * Validate foreign key constraint
 */
public boolean hasOrders(int userId) throws SQLException {
    String query = "SELECT COUNT(*) FROM orders WHERE user_id = ?";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query)) {
        
        pstmt.setInt(1, userId);
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        }
    }
}

// Usage in test
@Test
public void testUserDeletion() {
    int userId = createUserWithOrders();
    
    // Try to delete user
    deleteUser(userId);
    
    // Verify user deleted but orders remain (or cascade delete)
    User user = getUser(userId);
    Assert.assertNull(user, "User should be deleted");
    
    // Verify orders handled correctly
    List<Order> orders = getOrdersByUserId(userId);
    // Assert based on business logic (cascade delete or set null)
}
```

#### Pattern 5: Stored Procedure Validation
```java
/**
 * Execute stored procedure and validate result
 */
public int calculateUserAge(int userId) throws SQLException {
    String callProcedure = "{CALL calculate_user_age(?)}";
    try (Connection conn = DatabaseConnection.getConnection();
         CallableStatement cstmt = conn.prepareCall(callProcedure)) {
        
        cstmt.setInt(1, userId);
        try (ResultSet rs = cstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
            return -1;
        }
    }
}

// Usage in test
@Test
public void testCalculateUserAge() {
    int userId = createUserWithBirthDate();
    int expectedAge = 30;
    
    int actualAge = calculateUserAge(userId);
    Assert.assertEquals(actualAge, expectedAge, 
        "Calculated age should match expected");
}
```

### 3. Database Utility Class

#### Complete Database Utility
```java
package com.automation.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;
import java.util.*;

public class DatabaseUtils {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseUtils.class);
    
    /**
     * Execute SELECT query and return single value
     */
    public static Object executeQueryForSingleValue(String query, Object... params) 
            throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            setParameters(pstmt, params);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getObject(1);
                }
                return null;
            }
        }
    }
    
    /**
     * Execute SELECT query and return ResultSet as List of Maps
     */
    public static List<Map<String, Object>> executeQuery(String query, Object... params) 
            throws SQLException {
        List<Map<String, Object>> results = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            setParameters(pstmt, params);
            try (ResultSet rs = pstmt.executeQuery()) {
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();
                
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    for (int i = 1; i <= columnCount; i++) {
                        row.put(metaData.getColumnName(i), rs.getObject(i));
                    }
                    results.add(row);
                }
            }
        }
        return results;
    }
    
    /**
     * Execute UPDATE/INSERT/DELETE query
     */
    public static int executeUpdate(String query, Object... params) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            setParameters(pstmt, params);
            return pstmt.executeUpdate();
        }
    }
    
    /**
     * Execute batch update
     */
    public static int[] executeBatch(String query, List<Object[]> batchParams) 
            throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            for (Object[] params : batchParams) {
                setParameters(pstmt, params);
                pstmt.addBatch();
            }
            return pstmt.executeBatch();
        }
    }
    
    /**
     * Set parameters for PreparedStatement
     */
    private static void setParameters(PreparedStatement pstmt, Object... params) 
            throws SQLException {
        for (int i = 0; i < params.length; i++) {
            pstmt.setObject(i + 1, params[i]);
        }
    }
    
    /**
     * Validate record exists
     */
    public static boolean recordExists(String table, String condition, Object... params) 
            throws SQLException {
        String query = "SELECT COUNT(*) FROM " + table + " WHERE " + condition;
        int count = (Integer) executeQueryForSingleValue(query, params);
        return count > 0;
    }
    
    /**
     * Get record count
     */
    public static int getRecordCount(String table, String condition, Object... params) 
            throws SQLException {
        String query = "SELECT COUNT(*) FROM " + table;
        if (condition != null && !condition.isEmpty()) {
            query += " WHERE " + condition;
        }
        Object result = executeQueryForSingleValue(query, params);
        return result != null ? ((Number) result).intValue() : 0;
    }
}
```

### 4. Test Data Management

#### Insert Test Data
```java
/**
 * Insert test user
 */
public int insertTestUser(String username, String email, String password) 
        throws SQLException {
    String query = "INSERT INTO users (username, email, password, created_at) " +
                   "VALUES (?, ?, ?, NOW())";
    
    DatabaseUtils.executeUpdate(query, username, email, password);
    
    // Get last insert ID
    String idQuery = "SELECT LAST_INSERT_ID()";
    Object id = DatabaseUtils.executeQueryForSingleValue(idQuery);
    return (Integer) id;
}

/**
 * Clean up test data
 */
public void deleteTestUser(int userId) throws SQLException {
    String query = "DELETE FROM users WHERE id = ?";
    DatabaseUtils.executeUpdate(query, userId);
}
```

#### Transaction Management
```java
/**
 * Execute test with transaction rollback
 */
public void executeInTransaction(Consumer<Connection> testLogic) {
    try (Connection conn = DatabaseConnection.getConnection()) {
        conn.setAutoCommit(false);
        
        try {
            testLogic.accept(conn);
            conn.rollback(); // Always rollback in tests
        } catch (Exception e) {
            conn.rollback();
            throw e;
        }
    } catch (SQLException e) {
        logger.error("Transaction failed", e);
        throw new RuntimeException(e);
    }
}

// Usage
@Test
public void testUserCreation() {
    executeInTransaction(conn -> {
        // Insert test data
        int userId = insertUser(conn, testData);
        
        // Validate
        Assert.assertTrue(userExists(conn, userId));
        
        // Transaction will be rolled back automatically
    });
}
```

### 5. Common SQL Validations

#### Validate User Creation
```java
@Test
public void testUserCreationInDatabase() {
    logger.info("Testing user creation in database");
    
    // Get initial count
    int initialCount = DatabaseUtils.getRecordCount("users", null);
    logger.info("Initial user count: {}", initialCount);
    
    // Create user via UI/API
    String username = "testuser_" + System.currentTimeMillis();
    String email = username + "@example.com";
    createUserViaUI(username, email, "password123");
    
    // Validate in database
    int finalCount = DatabaseUtils.getRecordCount("users", null);
    Assert.assertEquals(finalCount, initialCount + 1, 
        "User count should increase by 1");
    
    // Validate user details
    Map<String, Object> user = DatabaseUtils.executeQuery(
        "SELECT * FROM users WHERE username = ?", username
    ).get(0);
    
    Assert.assertAll("User validation",
        () -> Assert.assertEquals(user.get("username"), username, "Username should match"),
        () -> Assert.assertEquals(user.get("email"), email, "Email should match"),
        () -> Assert.assertNotNull(user.get("created_at"), "Created timestamp should exist"),
        () -> Assert.assertEquals(user.get("status"), "active", "Status should be active")
    );
    
    logger.info("User creation validated successfully");
}
```

#### Validate Data Update
```java
@Test
public void testUserUpdateInDatabase() {
    logger.info("Testing user update in database");
    
    // Create user
    int userId = insertTestUser("testuser", "old@example.com", "pass123");
    
    // Update via UI/API
    updateUserEmail(userId, "new@example.com");
    
    // Validate in database
    String email = (String) DatabaseUtils.executeQueryForSingleValue(
        "SELECT email FROM users WHERE id = ?", userId
    );
    
    Assert.assertEquals(email, "new@example.com", 
        "Email should be updated in database");
    
    // Clean up
    deleteTestUser(userId);
}
```

#### Validate Data Deletion
```java
@Test
public void testUserDeletionInDatabase() {
    logger.info("Testing user deletion in database");
    
    // Create user
    int userId = insertTestUser("testuser", "test@example.com", "pass123");
    
    // Verify user exists
    Assert.assertTrue(DatabaseUtils.recordExists("users", "id = ?", userId), 
        "User should exist before deletion");
    
    // Delete via UI/API
    deleteUserViaUI(userId);
    
    // Verify deleted
    Assert.assertFalse(DatabaseUtils.recordExists("users", "id = ?", userId), 
        "User should not exist after deletion");
}
```

#### Validate Business Logic
```java
@Test
public void testOrderTotalCalculation() {
    logger.info("Testing order total calculation");
    
    // Create order with items
    int orderId = createOrderWithItems(testData);
    
    // Get calculated total from database
    double dbTotal = (Double) DatabaseUtils.executeQueryForSingleValue(
        "SELECT total_amount FROM orders WHERE id = ?", orderId
    );
    
    // Calculate expected total
    double expectedTotal = calculateExpectedTotal(testData.getItems());
    
    Assert.assertEquals(dbTotal, expectedTotal, 0.01, 
        "Order total should match calculated value");
}
```

#### Validate Referential Integrity
```java
@Test
public void testReferentialIntegrity() {
    logger.info("Testing referential integrity");
    
    // Create user
    int userId = insertTestUser("testuser", "test@example.com", "pass123");
    
    // Create order for user
    int orderId = createOrderForUser(userId);
    
    // Verify foreign key relationship
    int orderUserId = (Integer) DatabaseUtils.executeQueryForSingleValue(
        "SELECT user_id FROM orders WHERE id = ?", orderId
    );
    
    Assert.assertEquals(orderUserId, userId, 
        "Order should reference correct user");
    
    // Verify user has order
    int userOrderCount = DatabaseUtils.getRecordCount(
        "orders", "user_id = ?", userId
    );
    Assert.assertTrue(userOrderCount > 0, 
        "User should have at least one order");
}
```

### 6. Advanced SQL Validations

#### Compare Two Tables
```java
@Test
public void testDataSyncBetweenTables() {
    logger.info("Testing data synchronization between tables");
    
    // Get data from source table
    List<Map<String, Object>> sourceData = DatabaseUtils.executeQuery(
        "SELECT id, name, email FROM users WHERE status = 'active'"
    );
    
    // Get data from target table
    List<Map<String, Object>> targetData = DatabaseUtils.executeQuery(
        "SELECT user_id, name, email FROM active_users"
    );
    
    // Compare
    Assert.assertEquals(sourceData.size(), targetData.size(), 
        "Record count should match");
    
    for (int i = 0; i < sourceData.size(); i++) {
        Map<String, Object> source = sourceData.get(i);
        Map<String, Object> target = targetData.get(i);
        
        Assert.assertEquals(source.get("name"), target.get("name"), 
            "Name should match for record " + i);
        Assert.assertEquals(source.get("email"), target.get("email"), 
            "Email should match for record " + i);
    }
}
```

#### Validate Aggregations
```java
@Test
public void testOrderSummary() {
    logger.info("Testing order summary aggregations");
    
    // Get summary from database
    Map<String, Object> summary = DatabaseUtils.executeQuery(
        "SELECT COUNT(*) as total_orders, " +
        "SUM(amount) as total_amount, " +
        "AVG(amount) as avg_amount " +
        "FROM orders WHERE user_id = ?", 
        userId
    ).get(0);
    
    // Validate
    Assert.assertNotNull(summary.get("total_orders"), "Total orders should not be null");
    Assert.assertNotNull(summary.get("total_amount"), "Total amount should not be null");
    Assert.assertNotNull(summary.get("avg_amount"), "Average amount should not be null");
    
    int totalOrders = ((Number) summary.get("total_orders")).intValue();
    Assert.assertTrue(totalOrders >= 0, "Total orders should be non-negative");
}
```

#### Validate Data Migration
```java
@Test
public void testDataMigration() {
    logger.info("Testing data migration");
    
    // Get count from source
    int sourceCount = DatabaseUtils.getRecordCount("old_table", null);
    
    // Get count from target
    int targetCount = DatabaseUtils.getRecordCount("new_table", null);
    
    // Validate counts match
    Assert.assertEquals(sourceCount, targetCount, 
        "Record count should match after migration");
    
    // Validate sample records
    List<Map<String, Object>> sourceRecords = DatabaseUtils.executeQuery(
        "SELECT * FROM old_table LIMIT 10"
    );
    
    for (Map<String, Object> source : sourceRecords) {
        int id = (Integer) source.get("id");
        Map<String, Object> target = DatabaseUtils.executeQuery(
            "SELECT * FROM new_table WHERE old_id = ?", id
        ).get(0);
        
        Assert.assertAll("Record " + id + " validation",
            () -> Assert.assertEquals(target.get("name"), source.get("name")),
            () -> Assert.assertEquals(target.get("email"), source.get("email")),
            () -> Assert.assertNotNull(target.get("migrated_at"), "Migration timestamp should exist")
        );
    }
}
```

### 7. Database Test Best Practices

#### DO's
✓ Use transactions and rollback in tests
✓ Clean up test data after tests
✓ Use parameterized queries (PreparedStatement)
✓ Validate both positive and negative cases
✓ Test edge cases and boundary values
✓ Use meaningful assertion messages
✓ Log SQL queries for debugging
✓ Close database resources properly
✓ Use connection pooling for performance
✓ Test data integrity constraints

#### DON'Ts
✗ Don't use production database for testing
✗ Don't hardcode SQL queries in tests
✗ Don't forget to close connections
✗ Don't use SELECT * in production queries
✗ Don't ignore SQL exceptions
✗ Don't create dependencies between tests
✗ Don't leave test data in database
✗ Don't use string concatenation for queries (SQL injection risk)

## Code Templates

### Template 1: Basic Database Test
```java
@Test
public void testDatabaseValidation() {
    // Setup
    int initialCount = DatabaseUtils.getRecordCount("users", null);
    
    // Execute action (UI/API)
    createUser(testData);
    
    // Validate
    int finalCount = DatabaseUtils.getRecordCount("users", null);
    Assert.assertEquals(finalCount, initialCount + 1, 
        "User count should increase by 1");
    
    // Verify user details
    Map<String, Object> user = DatabaseUtils.executeQuery(
        "SELECT * FROM users WHERE username = ?", testData.getUsername()
    ).get(0);
    
    Assert.assertAll("User validation",
        () -> Assert.assertEquals(user.get("username"), testData.getUsername()),
        () -> Assert.assertEquals(user.get("email"), testData.getEmail())
    );
}
```

### Template 2: Transaction Rollback Test
```java
@Test
public void testWithTransactionRollback() {
    executeInTransaction(conn -> {
        // Insert test data
        int userId = insertUser(conn, testData);
        
        // Validate
        Assert.assertTrue(userExists(conn, userId));
        
        // Transaction will rollback automatically
    });
    
    // Verify data not persisted
    Assert.assertFalse(DatabaseUtils.recordExists("users", "username = ?", 
        testData.getUsername()));
}
```

### Template 3: Data Comparison Test
```java
@Test
public void testDataComparison() {
    // Get data from source
    List<Map<String, Object>> sourceData = DatabaseUtils.executeQuery(
        "SELECT id, name, email FROM users WHERE status = 'active'"
    );
    
    // Get data from target
    List<Map<String, Object>> targetData = getDataFromApi();
    
    // Compare
    Assert.assertEquals(sourceData.size(), targetData.size(), 
        "Record count should match");
    
    for (int i = 0; i < sourceData.size(); i++) {
        Assert.assertEquals(sourceData.get(i).get("email"), 
            targetData.get(i).get("email"), 
            "Email should match for record " + i);
    }
}
```

## SQL Query Best Practices

### SELECT Queries
```sql
-- Good - specific columns
SELECT id, name, email FROM users WHERE id = ?

-- Bad - select all
SELECT * FROM users WHERE id = ?
```

### WHERE Clauses
```sql
-- Good - use indexes
SELECT * FROM users WHERE email = ? AND status = 'active'

-- Bad - function on column (prevents index usage)
SELECT * FROM users WHERE LOWER(email) = 'test@example.com'
```

### JOINs
```sql
-- Good - explicit JOIN
SELECT u.id, u.name, o.order_id
FROM users u
INNER JOIN orders o ON u.id = o.user_id
WHERE u.status = 'active'

-- Bad - implicit JOIN
SELECT u.id, u.name, o.order_id
FROM users u, orders o
WHERE u.id = o.user_id AND u.status = 'active'
```

### Indexes
```sql
-- Create index for frequently queried columns
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_orders_user_id ON orders(user_id);
CREATE INDEX idx_orders_created_at ON orders(created_at);
```

## Additional Notes
- Always use transactions in database tests
- Clean up test data after each test
- Use connection pooling for better performance
- Validate both positive and negative cases
- Test database constraints and triggers
- Monitor query performance
- Use meaningful table and column names
- Document complex queries
- Consider using database migration tools (Flyway, Liquibase)
- Implement proper error handling
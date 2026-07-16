# Search Test Template

## Role
You are an expert test automation engineer specializing in Selenium WebDriver with TestNG and Java.

## Task
Create a comprehensive Search test following the Page Object Model (POM) pattern.

## Context
- **Framework**: Selenium WebDriver + TestNG + Java 11
- **Pattern**: Page Object Model (POM)
- **Base Class**: Extends `BaseTest`
- **Page Objects**: Uses `HomePage` and `SearchResultsPage`

## Requirements

### Test Scenarios to Cover
1. **Positive Test**: Search with valid keyword and verify results
2. **Positive Test**: Search with partial keyword and verify results
3. **Negative Test**: Search with no results
4. **Edge Case**: Search with special characters
5. **Edge Case**: Search with very long query
6. **Functional Test**: Verify search filters work correctly

### Test Data
- Valid search keywords
- Invalid/non-existent search terms
- Special characters for edge cases
- Filter criteria (price range, category, etc.)

## Instructions

### Test Class Structure
Create test class in `src/test/java/com/automation/tests/SearchTest.java`

### Test Methods to Implement

#### 1. testSearchWithValidKeyword()
```java
@Test(description = "Verify search functionality with valid keyword", 
      groups = {"smoke", "search", "positive"}, 
      priority = 1)
public void testSearchWithValidKeyword() {
    logger.info("Starting test: Search with Valid Keyword");
    
    // Navigate to home page
    navigateToBaseUrl();
    
    // Create page object
    HomePage homePage = new HomePage(WebDriverFactory.getDriver());
    
    // Verify home page is loaded
    Assert.assertTrue(homePage.isHomePageLoaded(), 
        "Home page should be loaded");
    
    // Perform search
    String searchKeyword = "laptop";
    homePage.searchFor(searchKeyword);
    
    // Create search results page object
    SearchResultsPage searchResultsPage = new SearchResultsPage(WebDriverFactory.getDriver());
    
    // Verify search results page is loaded
    Assert.assertTrue(searchResultsPage.isSearchResultsPageLoaded(), 
        "Search results page should be loaded");
    
    // Verify search keyword is displayed
    Assert.assertTrue(searchResultsPage.getSearchKeyword().contains(searchKeyword), 
        "Search keyword should be displayed in results");
    
    // Verify results are displayed
    Assert.assertTrue(searchResultsPage.getResultsCount() > 0, 
        "Search should return results");
    
    // Verify results are relevant
    Assert.assertTrue(searchResultsPage.areResultsRelevant(searchKeyword), 
        "Search results should be relevant to the keyword");
    
    logger.info("Test completed: Search with Valid Keyword");
}
```

#### 2. testSearchWithPartialKeyword()
```java
@Test(description = "Verify search with partial keyword returns relevant results", 
      groups = {"regression", "search", "positive"}, 
      priority = 2)
public void testSearchWithPartialKeyword() {
    logger.info("Starting test: Search with Partial Keyword");
    
    // Navigate to home page
    navigateToBaseUrl();
    
    // Create page object
    HomePage homePage = new HomePage(WebDriverFactory.getDriver());
    
    // Verify home page is loaded
    Assert.assertTrue(homePage.isHomePageLoaded(), 
        "Home page should be loaded");
    
    // Perform search with partial keyword
    String partialKeyword = "lap";
    homePage.searchFor(partialKeyword);
    
    // Create search results page object
    SearchResultsPage searchResultsPage = new SearchResultsPage(WebDriverFactory.getDriver());
    
    // Verify search results page is loaded
    Assert.assertTrue(searchResultsPage.isSearchResultsPageLoaded(), 
        "Search results page should be loaded");
    
    // Verify results are displayed
    Assert.assertTrue(searchResultsPage.getResultsCount() > 0, 
        "Search should return results for partial keyword");
    
    // Verify results contain the partial keyword
    Assert.assertTrue(searchResultsPage.areResultsRelevant(partialKeyword), 
        "Search results should be relevant to partial keyword");
    
    logger.info("Test completed: Search with Partial Keyword");
}
```

#### 3. testSearchWithNoResults()
```java
@Test(description = "Verify search with non-existent keyword shows no results message", 
      groups = {"regression", "search", "negative"}, 
      priority = 3)
public void testSearchWithNoResults() {
    logger.info("Starting test: Search with No Results");
    
    // Navigate to home page
    navigateToBaseUrl();
    
    // Create page object
    HomePage homePage = new HomePage(WebDriverFactory.getDriver());
    
    // Verify home page is loaded
    Assert.assertTrue(homePage.isHomePageLoaded(), 
        "Home page should be loaded");
    
    // Perform search with non-existent keyword
    String nonExistentKeyword = "xyznonexistent123";
    homePage.searchFor(nonExistentKeyword);
    
    // Create search results page object
    SearchResultsPage searchResultsPage = new SearchResultsPage(WebDriverFactory.getDriver());
    
    // Verify search results page is loaded
    Assert.assertTrue(searchResultsPage.isSearchResultsPageLoaded(), 
        "Search results page should be loaded");
    
    // Verify no results message is displayed
    Assert.assertTrue(searchResultsPage.isNoResultsMessageDisplayed(), 
        "No results message should be displayed");
    
    // Verify results count is zero
    Assert.assertEquals(searchResultsPage.getResultsCount(), 0, 
        "Results count should be zero");
    
    // Verify no results message text
    String noResultsMessage = searchResultsPage.getNoResultsMessage();
    Assert.assertNotNull(noResultsMessage, "No results message should not be null");
    Assert.assertFalse(noResultsMessage.isEmpty(), "No results message should not be empty");
    
    logger.info("Test completed: Search with No Results");
}
```

#### 4. testSearchWithSpecialCharacters()
```java
@Test(description = "Verify search handles special characters gracefully", 
      groups = {"regression", "search", "edgecase"}, 
      priority = 4)
public void testSearchWithSpecialCharacters() {
    logger.info("Starting test: Search with Special Characters");
    
    // Navigate to home page
    navigateToBaseUrl();
    
    // Create page object
    HomePage homePage = new HomePage(WebDriverFactory.getDriver());
    
    // Verify home page is loaded
    Assert.assertTrue(homePage.isHomePageLoaded(), 
        "Home page should be loaded");
    
    // Perform search with special characters
    String specialCharKeyword = "laptop@#$%";
    homePage.searchFor(specialCharKeyword);
    
    // Create search results page object
    SearchResultsPage searchResultsPage = new SearchResultsPage(WebDriverFactory.getDriver());
    
    // Verify search results page is loaded
    Assert.assertTrue(searchResultsPage.isSearchResultsPageLoaded(), 
        "Search results page should be loaded");
    
    // Verify either results or no results message is shown (should not crash)
    boolean hasResults = searchResultsPage.getResultsCount() > 0;
    boolean hasNoResultsMessage = searchResultsPage.isNoResultsMessageDisplayed();
    
    Assert.assertTrue(hasResults || hasNoResultsMessage, 
        "Search should either return results or show no results message");
    
    logger.info("Test completed: Search with Special Characters");
}
```

#### 5. testSearchWithLongQuery()
```java
@Test(description = "Verify search handles very long query strings", 
      groups = {"regression", "search", "edgecase"}, 
      priority = 5)
public void testSearchWithLongQuery() {
    logger.info("Starting test: Search with Long Query");
    
    // Navigate to home page
    navigateToBaseUrl();
    
    // Create page object
    HomePage homePage = new HomePage(WebDriverFactory.getDriver());
    
    // Verify home page is loaded
    Assert.assertTrue(homePage.isHomePageLoaded(), 
        "Home page should be loaded");
    
    // Perform search with very long query
    String longQuery = "this is a very long search query that tests the application's ability to handle lengthy input strings without crashing or truncating the search term";
    homePage.searchFor(longQuery);
    
    // Create search results page object
    SearchResultsPage searchResultsPage = new SearchResultsPage(WebDriverFactory.getDriver());
    
    // Verify search results page is loaded
    Assert.assertTrue(searchResultsPage.isSearchResultsPageLoaded(), 
        "Search results page should be loaded");
    
    // Verify either results or no results message is shown (should not crash)
    boolean hasResults = searchResultsPage.getResultsCount() > 0;
    boolean hasNoResultsMessage = searchResultsPage.isNoResultsMessageDisplayed();
    
    Assert.assertTrue(hasResults || hasNoResultsMessage, 
        "Search should handle long queries gracefully");
    
    logger.info("Test completed: Search with Long Query");
}
```

#### 6. testSearchWithFilters()
```java
@Test(description = "Verify search filters work correctly", 
      groups = {"regression", "search", "functional"}, 
      priority = 6)
public void testSearchWithFilters() {
    logger.info("Starting test: Search with Filters");
    
    // Navigate to home page
    navigateToBaseUrl();
    
    // Create page object
    HomePage homePage = new HomePage(WebDriverFactory.getDriver());
    
    // Verify home page is loaded
    Assert.assertTrue(homePage.isHomePageLoaded(), 
        "Home page should be loaded");
    
    // Perform search
    String searchKeyword = "laptop";
    homePage.searchFor(searchKeyword);
    
    // Create search results page object
    SearchResultsPage searchResultsPage = new SearchResultsPage(WebDriverFactory.getDriver());
    
    // Verify search results page is loaded
    Assert.assertTrue(searchResultsPage.isSearchResultsPageLoaded(), 
        "Search results page should be loaded");
    
    // Get initial results count
    int initialResultsCount = searchResultsPage.getResultsCount();
    Assert.assertTrue(initialResultsCount > 0, 
        "Initial search should return results");
    
    // Apply price filter (e.g., price range: $500 - $1000)
    searchResultsPage.applyPriceFilter(500, 1000);
    
    // Wait for results to update
    searchResultsPage.waitForResultsToUpdate();
    
    // Get filtered results count
    int filteredResultsCount = searchResultsPage.getResultsCount();
    
    // Verify filtered results are less than or equal to initial results
    Assert.assertTrue(filteredResultsCount <= initialResultsCount, 
        "Filtered results should be less than or equal to initial results");
    
    // Verify all results are within price range
    Assert.assertTrue(searchResultsPage.areAllResultsInPriceRange(500, 1000), 
        "All results should be within the selected price range");
    
    // Clear filters
    searchResultsPage.clearAllFilters();
    searchResultsPage.waitForResultsToUpdate();
    
    // Verify results count returns to initial count
    Assert.assertEquals(searchResultsPage.getResultsCount(), initialResultsCount, 
        "Results count should return to initial count after clearing filters");
    
    logger.info("Test completed: Search with Filters");
}
```

## Complete Template

```java
package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.pages.HomePage;
import com.automation.pages.SearchResultsPage;
import com.automation.utils.WebDriverFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Search Test Class
 * Test cases for search functionality
 * Following Page Object Model - Test class only contains test logic
 */
public class SearchTest extends BaseTest {

    /**
     * Test search functionality with valid keyword
     */
    @Test(description = "Verify search functionality with valid keyword", 
          groups = {"smoke", "search", "positive"}, 
          priority = 1)
    public void testSearchWithValidKeyword() {
        logger.info("Starting test: Search with Valid Keyword");
        
        // Navigate to home page
        navigateToBaseUrl();
        
        // Create page object
        HomePage homePage = new HomePage(WebDriverFactory.getDriver());
        
        // Verify home page is loaded
        Assert.assertTrue(homePage.isHomePageLoaded(), 
            "Home page should be loaded");
        
        // Perform search
        String searchKeyword = "laptop";
        homePage.searchFor(searchKeyword);
        
        // Create search results page object
        SearchResultsPage searchResultsPage = new SearchResultsPage(WebDriverFactory.getDriver());
        
        // Verify search results page is loaded
        Assert.assertTrue(searchResultsPage.isSearchResultsPageLoaded(), 
            "Search results page should be loaded");
        
        // Verify search keyword is displayed
        Assert.assertTrue(searchResultsPage.getSearchKeyword().contains(searchKeyword), 
            "Search keyword should be displayed in results");
        
        // Verify results are displayed
        Assert.assertTrue(searchResultsPage.getResultsCount() > 0, 
            "Search should return results");
        
        // Verify results are relevant
        Assert.assertTrue(searchResultsPage.areResultsRelevant(searchKeyword), 
            "Search results should be relevant to the keyword");
        
        logger.info("Test completed: Search with Valid Keyword");
    }

    /**
     * Test search with partial keyword returns relevant results
     */
    @Test(description = "Verify search with partial keyword returns relevant results", 
          groups = {"regression", "search", "positive"}, 
          priority = 2)
    public void testSearchWithPartialKeyword() {
        logger.info("Starting test: Search with Partial Keyword");
        
        // Navigate to home page
        navigateToBaseUrl();
        
        // Create page object
        HomePage homePage = new HomePage(WebDriverFactory.getDriver());
        
        // Verify home page is loaded
        Assert.assertTrue(homePage.isHomePageLoaded(), 
            "Home page should be loaded");
        
        // Perform search with partial keyword
        String partialKeyword = "lap";
        homePage.searchFor(partialKeyword);
        
        // Create search results page object
        SearchResultsPage searchResultsPage = new SearchResultsPage(WebDriverFactory.getDriver());
        
        // Verify search results page is loaded
        Assert.assertTrue(searchResultsPage.isSearchResultsPageLoaded(), 
            "Search results page should be loaded");
        
        // Verify results are displayed
        Assert.assertTrue(searchResultsPage.getResultsCount() > 0, 
            "Search should return results for partial keyword");
        
        // Verify results contain the partial keyword
        Assert.assertTrue(searchResultsPage.areResultsRelevant(partialKeyword), 
            "Search results should be relevant to partial keyword");
        
        logger.info("Test completed: Search with Partial Keyword");
    }

    /**
     * Test search with non-existent keyword shows no results message
     */
    @Test(description = "Verify search with non-existent keyword shows no results message", 
          groups = {"regression", "search", "negative"}, 
          priority = 3)
    public void testSearchWithNoResults() {
        logger.info("Starting test: Search with No Results");
        
        // Navigate to home page
        navigateToBaseUrl();
        
        // Create page object
        HomePage homePage = new HomePage(WebDriverFactory.getDriver());
        
        // Verify home page is loaded
        Assert.assertTrue(homePage.isHomePageLoaded(), 
            "Home page should be loaded");
        
        // Perform search with non-existent keyword
        String nonExistentKeyword = "xyznonexistent123";
        homePage.searchFor(nonExistentKeyword);
        
        // Create search results page object
        SearchResultsPage searchResultsPage = new SearchResultsPage(WebDriverFactory.getDriver());
        
        // Verify search results page is loaded
        Assert.assertTrue(searchResultsPage.isSearchResultsPageLoaded(), 
            "Search results page should be loaded");
        
        // Verify no results message is displayed
        Assert.assertTrue(searchResultsPage.isNoResultsMessageDisplayed(), 
            "No results message should be displayed");
        
        // Verify results count is zero
        Assert.assertEquals(searchResultsPage.getResultsCount(), 0, 
            "Results count should be zero");
        
        // Verify no results message text
        String noResultsMessage = searchResultsPage.getNoResultsMessage();
        Assert.assertNotNull(noResultsMessage, "No results message should not be null");
        Assert.assertFalse(noResultsMessage.isEmpty(), "No results message should not be empty");
        
        logger.info("Test completed: Search with No Results");
    }

    /**
     * Test search handles special characters gracefully
     */
    @Test(description = "Verify search handles special characters gracefully", 
          groups = {"regression", "search", "edgecase"}, 
          priority = 4)
    public void testSearchWithSpecialCharacters() {
        logger.info("Starting test: Search with Special Characters");
        
        // Navigate to home page
        navigateToBaseUrl();
        
        // Create page object
        HomePage homePage = new HomePage(WebDriverFactory.getDriver());
        
        // Verify home page is loaded
        Assert.assertTrue(homePage.isHomePageLoaded(), 
            "Home page should be loaded");
        
        // Perform search with special characters
        String specialCharKeyword = "laptop@#$%";
        homePage.searchFor(specialCharKeyword);
        
        // Create search results page object
        SearchResultsPage searchResultsPage = new SearchResultsPage(WebDriverFactory.getDriver());
        
        // Verify search results page is loaded
        Assert.assertTrue(searchResultsPage.isSearchResultsPageLoaded(), 
            "Search results page should be loaded");
        
        // Verify either results or no results message is shown (should not crash)
        boolean hasResults = searchResultsPage.getResultsCount() > 0;
        boolean hasNoResultsMessage = searchResultsPage.isNoResultsMessageDisplayed();
        
        Assert.assertTrue(hasResults || hasNoResultsMessage, 
            "Search should either return results or show no results message");
        
        logger.info("Test completed: Search with Special Characters");
    }

    /**
     * Test search handles very long query strings
     */
    @Test(description = "Verify search handles very long query strings", 
          groups = {"regression", "search", "edgecase"}, 
          priority = 5)
    public void testSearchWithLongQuery() {
        logger.info("Starting test: Search with Long Query");
        
        // Navigate to home page
        navigateToBaseUrl();
        
        // Create page object
        HomePage homePage = new HomePage(WebDriverFactory.getDriver());
        
        // Verify home page is loaded
        Assert.assertTrue(homePage.isHomePageLoaded(), 
            "Home page should be loaded");
        
        // Perform search with very long query
        String longQuery = "this is a very long search query that tests the application's ability to handle lengthy input strings without crashing or truncating the search term";
        homePage.searchFor(longQuery);
        
        // Create search results page object
        SearchResultsPage searchResultsPage = new SearchResultsPage(WebDriverFactory.getDriver());
        
        // Verify search results page is loaded
        Assert.assertTrue(searchResultsPage.isSearchResultsPageLoaded(), 
            "Search results page should be loaded");
        
        // Verify either results or no results message is shown (should not crash)
        boolean hasResults = searchResultsPage.getResultsCount() > 0;
        boolean hasNoResultsMessage = searchResultsPage.isNoResultsMessageDisplayed();
        
        Assert.assertTrue(hasResults || hasNoResultsMessage, 
            "Search should handle long queries gracefully");
        
        logger.info("Test completed: Search with Long Query");
    }

    /**
     * Test search filters work correctly
     */
    @Test(description = "Verify search filters work correctly", 
          groups = {"regression", "search", "functional"}, 
          priority = 6)
    public void testSearchWithFilters() {
        logger.info("Starting test: Search with Filters");
        
        // Navigate to home page
        navigateToBaseUrl();
        
        // Create page object
        HomePage homePage = new HomePage(WebDriverFactory.getDriver());
        
        // Verify home page is loaded
        Assert.assertTrue(homePage.isHomePageLoaded(), 
            "Home page should be loaded");
        
        // Perform search
        String searchKeyword = "laptop";
        homePage.searchFor(searchKeyword);
        
        // Create search results page object
        SearchResultsPage searchResultsPage = new SearchResultsPage(WebDriverFactory.getDriver());
        
        // Verify search results page is loaded
        Assert.assertTrue(searchResultsPage.isSearchResultsPageLoaded(), 
            "Search results page should be loaded");
        
        // Get initial results count
        int initialResultsCount = searchResultsPage.getResultsCount();
        Assert.assertTrue(initialResultsCount > 0, 
            "Initial search should return results");
        
        // Apply price filter (e.g., price range: $500 - $1000)
        searchResultsPage.applyPriceFilter(500, 1000);
        
        // Wait for results to update
        searchResultsPage.waitForResultsToUpdate();
        
        // Get filtered results count
        int filteredResultsCount = searchResultsPage.getResultsCount();
        
        // Verify filtered results are less than or equal to initial results
        Assert.assertTrue(filteredResultsCount <= initialResultsCount, 
            "Filtered results should be less than or equal to initial results");
        
        // Verify all results are within price range
        Assert.assertTrue(searchResultsPage.areAllResultsInPriceRange(500, 1000), 
            "All results should be within the selected price range");
        
        // Clear filters
        searchResultsPage.clearAllFilters();
        searchResultsPage.waitForResultsToUpdate();
        
        // Verify results count returns to initial count
        Assert.assertEquals(searchResultsPage.getResultsCount(), initialResultsCount, 
            "Results count should return to initial count after clearing filters");
        
        logger.info("Test completed: Search with Filters");
    }
}
```

## Usage Instructions

### How to Use This Template
1. Copy the complete template code
2. Replace placeholder values with actual values:
   - Update search keywords to match your application
   - Modify filter criteria based on your application's filters
   - Adjust assertions based on actual application behavior
3. Ensure corresponding `HomePage` and `SearchResultsPage` page objects exist
4. Update test groups and priorities as per your test strategy
5. Add/remove test methods based on your requirements

### Customization Points
- **Search Keywords**: Replace with actual product/search terms from your application
- **Filters**: Modify filter types (price, category, brand, etc.) based on your application
- **Assertions**: Adjust based on actual application behavior
- **Test Groups**: Modify groups to match your test management strategy
- **Priorities**: Set priorities based on test execution order requirements
- **Additional Tests**: Add more test methods for other scenarios (e.g., search suggestions, search history)

## Best Practices
- Keep tests independent and idempotent
- Use meaningful test method names
- Add descriptive assertion messages
- Log key test steps
- Group tests appropriately for execution
- Follow the Page Object Model pattern strictly
- Test both positive and negative scenarios
- Include edge cases for comprehensive coverage
- Verify search relevance and accuracy
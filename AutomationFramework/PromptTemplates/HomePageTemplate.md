# Home Page Object Template

## Role
You are an expert test automation engineer specializing in Page Object Model (POM) design pattern with Selenium WebDriver and Java.

## Task
Create a comprehensive Home Page Object class following framework standards.

## Context
- **Framework**: Selenium WebDriver + TestNG + Java 11
- **Pattern**: Page Object Model (POM)
- **Base Class**: Extends `BasePage`
- **Locators**: Stored in separate `HomePageLocators` class
- **Package**: `com.automation.pages`

## Requirements

### Page Information
- **Page Name**: HomePage
- **Page URL**: / or /home (base URL)
- **Page Description**: Home page with search, navigation, and product listings

### Page Elements
- **Search**: Search input field, Search button
- **Navigation**: Menu items, Categories, User menu
- **Products**: Product cards, Product images, Product names, Prices
- **Cart**: Cart icon, Cart count badge
- **Header**: Logo, User greeting, Notifications
- **Footer**: Links, Social media icons

### Page Actions
- Search for products
- Navigate to categories
- Add items to cart
- Navigate to cart
- Navigate to login page
- Logout
- View product details
- Apply filters

### Page Verifications
- Check if home page is loaded
- Check if search results are displayed
- Check if cart has items
- Check if user is logged in
- Check if products are displayed

## Instructions

### 1. Create Locator Class First
Create `HomePageLocators.java` in `src/main/java/com/automation/locators/`

```java
package com.automation.locators;

import org.openqa.selenium.By;

/**
 * Home Page Locators
 * All locators for HomePage elements
 */
public class HomePageLocators {
    
    // Header Elements
    public By logo = By.cssSelector(".logo, #logo");
    public By userMenu = By.cssSelector(".user-menu, .user-dropdown");
    public By logoutLink = By.linkText("Logout");
    public By cartIcon = By.cssSelector(".cart-icon, #cart");
    public By cartCount = By.cssSelector(".cart-count, .cart-badge");
    public By notificationsIcon = By.cssSelector(".notifications-icon");
    
    // Search Elements
    public By searchInput = By.id("search-input, .search-box");
    public By searchButton = By.cssSelector(".search-button, button[type='submit']");
    public By searchSuggestions = By.cssSelector(".search-suggestions, .autocomplete");
    
    // Navigation Menu
    public By menuItems = By.cssSelector(".menu-items, .nav-menu");
    public By categoriesLink = By.linkText("Categories");
    public By homeLink = By.linkText("Home");
    public By aboutLink = By.linkText("About");
    public By contactLink = By.linkText("Contact");
    
    // Product Elements
    public By productCards = By.cssSelector(".product-card, .product-item");
    public By productNames = By.cssSelector(".product-name, .product-title");
    public By productPrices = By.cssSelector(".product-price, .price");
    public By productImages = By.cssSelector(".product-image, img");
    public By addToCartButtons = By.cssSelector(".add-to-cart, button[class*='cart']");
    public By productDetailsLinks = By.cssSelector(".product-details, a[class*='product']");
    
    // Filters
    public By filterSection = By.cssSelector(".filters, .filter-section");
    public By priceFilter = By.id("price-filter");
    public By categoryFilter = By.id("category-filter");
    public By applyFilterButton = By.cssSelector(".apply-filter, button[class*='filter']");
    
    // Page Indicators
    public By pageHeader = By.cssSelector("h1, .page-title");
    public By welcomeMessage = By.cssSelector(".welcome-message, .greeting");
    public By footer = By.cssSelector("footer, .footer");
    
    // Loading Elements
    public By loadingSpinner = By.cssSelector(".loading, .spinner");
}
```

### 2. Create Page Object Class
Create `HomePage.java` in `src/main/java/com/automation/pages/`

## Complete Template

```java
package com.automation.pages;

import com.automation.locators.HomePageLocators;
import com.automation.utils.ExtentReportManager;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Home Page - Page Object Model
 * Represents the home page and its elements
 * Handles navigation, search, and product operations
 */
public class HomePage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(HomePage.class);
    private HomePageLocators locators;

    /**
     * Constructor
     * @param driver WebDriver instance
     */
    public HomePage(WebDriver driver) {
        super(driver);
        this.locators = new HomePageLocators();
        logger.info("HomePage initialized");
    }

    // ==================== Navigation Methods ====================

    /**
     * Navigate to home page
     */
    public void navigateToHome() {
        logger.info("Navigating to home page");
        navigateToBaseUrl();
        waitForPageLoad();
    }

    /**
     * Navigate to login page
     * @return LoginPage instance
     */
    public LoginPage navigateToLogin() {
        logger.info("Navigating to login page");
        // Implementation depends on your application
        // click(locators.loginLink, "Login Link");
        // waitForPageLoad();
        return new LoginPage(driver);
    }

    /**
     * Navigate to cart
     * @return CartPage instance
     */
    public CartPage navigateToCart() {
        logger.info("Navigating to cart");
        click(locators.cartIcon, "Cart Icon");
        waitForPageLoad();
        return new CartPage(driver);
    }

    /**
     * Navigate to category
     * @param categoryName Category name
     * @return HomePage instance
     */
    public HomePage navigateToCategory(String categoryName) {
        logger.info("Navigating to category: {}", categoryName);
        // Implementation depends on your application structure
        // click(By.linkText(categoryName), "Category: " + categoryName);
        waitForPageLoad();
        return this;
    }

    // ==================== Search Methods ====================

    /**
     * Search for a product
     * @param searchKeyword Search keyword
     * @return SearchResultsPage instance
     */
    public SearchResultsPage searchFor(String searchKeyword) {
        logger.info("Searching for: {}", searchKeyword);
        enterText(locators.searchInput, searchKeyword, "Search Input");
        click(locators.searchButton, "Search Button");
        waitForPageLoad();
        return new SearchResultsPage(driver);
    }

    /**
     * Enter search keyword without submitting
     * @param searchKeyword Search keyword
     */
    public void enterSearchKeyword(String searchKeyword) {
        logger.info("Entering search keyword: {}", searchKeyword);
        enterText(locators.searchInput, searchKeyword, "Search Input");
    }

    /**
     * Clear search input
     */
    public void clearSearchInput() {
        logger.info("Clearing search input");
        clearField(locators.searchInput, "Search Input");
    }

    // ==================== Product Methods ====================

    /**
     * Add item to cart by product name
     * @param productName Product name
     * @return HomePage instance
     */
    public HomePage addItemToCart(String productName) {
        logger.info("Adding item to cart: {}", productName);
        
        // Find product by name and click add to cart
        // Implementation depends on your application structure
        // This is a generic approach
        click(By.xpath("//*[contains(text(), '" + productName + "')]/following::button[contains(text(), 'Add to Cart')]"), 
              "Add to Cart Button for: " + productName);
        
        // Wait for cart count to update
        waitForElementToBeVisible(locators.cartCount, "Cart Count");
        
        return this;
    }

    /**
     * Add item to cart with specific quantity
     * @param productName Product name
     * @param quantity Quantity to add
     * @return HomePage instance
     */
    public HomePage addItemToCartWithQuantity(String productName, int quantity) {
        logger.info("Adding item to cart: {} with quantity: {}", productName, quantity);
        
        // Find product and set quantity
        // Implementation depends on your application
        // click(By.xpath("//*[contains(text(), '" + productName + "')]/following::input[@type='number']"), "Quantity Input");
        // clearField(By.xpath("//*[contains(text(), '" + productName + "')]/following::input[@type='number']"), "Quantity Input");
        // enterText(By.xpath("//*[contains(text(), '" + productName + "')]/following::input[@type='number']"), String.valueOf(quantity), "Quantity Input");
        // click(By.xpath("//*[contains(text(), '" + productName + "')]/following::button[contains(text(), 'Add to Cart')]"), "Add to Cart Button");
        
        waitForElementToBeVisible(locators.cartCount, "Cart Count");
        return this;
    }

    /**
     * View product details
     * @param productName Product name
     * @return ProductPage instance
     */
    public ProductPage viewProductDetails(String productName) {
        logger.info("Viewing product details: {}", productName);
        click(By.xpath("//*[contains(text(), '" + productName + "')]/ancestor::a"), 
              "Product Link: " + productName);
        waitForPageLoad();
        return new ProductPage(driver);
    }

    /**
     * Get product count on home page
     * @return Number of products displayed
     */
    public int getProductCount() {
        logger.info("Getting product count");
        return getElementCount(locators.productCards, "Product Cards");
    }

    /**
     * Get cart item count
     * @return Number of items in cart
     */
    public int getCartItemCount() {
        logger.info("Getting cart item count");
        String cartCountText = getText(locators.cartCount, "Cart Count");
        if (cartCountText != null && !cartCountText.isEmpty()) {
            return Integer.parseInt(cartCountText);
        }
        return 0;
    }

    // ==================== Filter Methods ====================

    /**
     * Apply price filter
     * @param minPrice Minimum price
     * @param maxPrice Maximum price
     * @return HomePage instance
     */
    public HomePage applyPriceFilter(double minPrice, double maxPrice) {
        logger.info("Applying price filter: {} - {}", minPrice, maxPrice);
        
        // Implementation depends on your application
        // enterText(locators.minPriceInput, String.valueOf(minPrice), "Min Price");
        // enterText(locators.maxPriceInput, String.valueOf(maxPrice), "Max Price");
        // click(locators.applyFilterButton, "Apply Filter Button");
        // waitForPageLoad();
        
        return this;
    }

    /**
     * Apply category filter
     * @param category Category name
     * @return HomePage instance
     */
    public HomePage applyCategoryFilter(String category) {
        logger.info("Applying category filter: {}", category);
        
        // Implementation depends on your application
        // selectByVisibleText(locators.categoryFilter, category, "Category Filter");
        // click(locators.applyFilterButton, "Apply Filter Button");
        // waitForPageLoad();
        
        return this;
    }

    /**
     * Clear all filters
     * @return HomePage instance
     */
    public HomePage clearAllFilters() {
        logger.info("Clearing all filters");
        
        // Implementation depends on your application
        // click(locators.clearFiltersButton, "Clear Filters Button");
        // waitForPageLoad();
        
        return this;
    }

    // ==================== User Actions ====================

    /**
     * Logout from application
     * @return LoginPage instance
     */
    public LoginPage logout() {
        logger.info("Logging out");
        click(locators.userMenu, "User Menu");
        waitForElementToBeClickable(locators.logoutLink, "Logout Link");
        click(locators.logoutLink, "Logout Link");
        waitForPageLoad();
        return new LoginPage(driver);
    }

    /**
     * Check if user is logged in
     * @return true if user is logged in
     */
    public boolean isUserLoggedIn() {
        logger.info("Checking if user is logged in");
        return isDisplayed(locators.userMenu, "User Menu") && 
               isDisplayed(locators.logoutLink, "Logout Link");
    }

    // ==================== Verification Methods ====================

    /**
     * Check if home page is loaded
     * @return true if home page is loaded
     */
    public boolean isHomePageLoaded() {
        logger.info("Checking if home page is loaded");
        return isDisplayed(locators.searchInput, "Search Input") && 
               isDisplayed(locators.logo, "Logo") &&
               isDisplayed(locators.productCards, "Product Cards");
    }

    /**
     * Check if search results are displayed
     * @return true if search results are displayed
     */
    public boolean isSearchResultsDisplayed() {
        logger.info("Checking if search results are displayed");
        return getElementCount(locators.productCards, "Product Cards") > 0;
    }

    /**
     * Check if cart has items
     * @return true if cart has items
     */
    public boolean isCartHasItems() {
        logger.info("Checking if cart has items");
        String cartCountText = getText(locators.cartCount, "Cart Count");
        return cartCountText != null && !cartCountText.isEmpty() && Integer.parseInt(cartCountText) > 0;
    }

    /**
     * Check if welcome message is displayed
     * @return true if welcome message is displayed
     */
    public boolean isWelcomeMessageDisplayed() {
        logger.info("Checking if welcome message is displayed");
        return isDisplayed(locators.welcomeMessage, "Welcome Message");
    }

    // ==================== Getter Methods ====================

    /**
     * Get welcome message text
     * @return Welcome message text
     */
    public String getWelcomeMessage() {
        logger.info("Getting welcome message");
        return getText(locators.welcomeMessage, "Welcome Message");
    }

    /**
     * Get cart count
     * @return Cart item count
     */
    public int getCartCount() {
        logger.info("Getting cart count");
        return getCartItemCount();
    }

    /**
     * Get product names
     * @return Array of product names
     */
    public String[] getProductNames() {
        logger.info("Getting product names");
        return getTexts(locators.productNames, "Product Names");
    }

    /**
     * Get product prices
     * @return Array of product prices
     */
    public String[] getProductPrices() {
        logger.info("Getting product prices");
        return getTexts(locators.productPrices, "Product Prices");
    }

    // ==================== Utility Methods ====================

    /**
     * Wait for page to load completely
     */
    public void waitForPageToLoad() {
        logger.info("Waiting for page to load");
        waitForElementToBeVisible(locators.searchInput, "Search Input");
        waitForElementToBeVisible(locators.logo, "Logo");
    }

    /**
     * Scroll to bottom of page
     */
    public void scrollToBottom() {
        logger.info("Scrolling to bottom of page");
        scrollToBottomOfPage();
    }

    /**
     * Scroll to top of page
     */
    public void scrollToTop() {
        logger.info("Scrolling to top of page");
        scrollToTopOfPage();
    }

    /**
     * Check if page has products
     * @return true if page has products
     */
    public boolean hasProducts() {
        logger.info("Checking if page has products");
        return getProductCount() > 0;
    }

    /**
     * Get page title
     * @return Page title
     */
    public String getPageTitle() {
        logger.info("Getting page title");
        return driver.getTitle();
    }

    /**
     * Get current URL
     * @return Current URL
     */
    public String getCurrentUrl() {
        logger.info("Getting current URL");
        return driver.getCurrentUrl();
    }
}
```

## Usage Instructions

### How to Use This Template
1. Create the locator class first (`HomePageLocators.java`)
2. Copy the complete page object code
3. Update locators to match your application's actual locators
4. Add/remove methods based on your application's functionality
5. Ensure all methods have proper logging and error handling

### Customization Points
- **Locators**: Update all By locators to match your application
- **Navigation Methods**: Add navigation methods for your specific menu items
- **Product Methods**: Customize product interaction methods
- **Filter Methods**: Implement filter logic based on your application
- **Return Types**: Modify return types for navigation methods
- **Logging**: Adjust logging messages as needed

## Best Practices
- Extend BasePage for all page objects
- Use separate locator classes
- Use BasePage methods for element interactions
- Add logging for all actions
- Return page objects for navigation (method chaining)
- Keep methods focused on single responsibility
- Add Javadoc for all public methods
- Use descriptive method names
- Don't include test logic in page objects
- Don't use Thread.sleep()
- Don't hardcode locators in page methods
- Don't include assertions in page objects
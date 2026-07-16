# Cart Page Object Template

## Role
You are an expert test automation engineer specializing in Page Object Model (POM) design pattern with Selenium WebDriver and Java.

## Task
Create a comprehensive Cart Page Object class following framework standards.

## Context
- **Framework**: Selenium WebDriver + TestNG + Java 11
- **Pattern**: Page Object Model (POM)
- **Base Class**: Extends `BasePage`
- **Locators**: Stored in separate `CartPageLocators` class
- **Package**: `com.automation.pages`

## Requirements

### Page Information
- **Page Name**: CartPage
- **Page URL**: /cart or /shopping-cart
- **Page Description**: Shopping cart page for managing items before checkout

### Page Elements
- **Cart Items**: Product names, images, quantities, prices, remove buttons
- **Quantity Controls**: Increment/decrement buttons, quantity input fields
- **Cart Summary**: Subtotal, tax, shipping, total
- **Action Buttons**: Proceed to checkout, Continue shopping, Update cart
- **Empty Cart Message**: Message when cart is empty
- **Promo Code**: Input field and apply button for discount codes
- **Save for Later**: Option to move items to wishlist

### Page Actions
- Update item quantity
- Remove item from cart
- Apply promo code
- Proceed to checkout
- Continue shopping
- Save item for later
- Move item to wishlist
- Clear entire cart

### Page Verifications
- Check if cart page is loaded
- Check if cart has items
- Check if cart is empty
- Verify cart totals
- Verify item details
- Check if promo code is applied

## Instructions

### 1. Create Locator Class First
Create `CartPageLocators.java` in `src/main/java/com/automation/locators/`

```java
package com.automation.locators;

import org.openqa.selenium.By;

/**
 * Cart Page Locators
 * All locators for CartPage elements
 */
public class CartPageLocators {
    
    // Page Header
    public By pageTitle = By.cssSelector("h1, .page-title, .cart-title");
    public By cartSummary = By.cssSelector(".cart-summary, .cart-overview");
    
    // Cart Items
    public By cartItems = By.cssSelector(".cart-item, .cart-product, tr.cart-item");
    public By productNames = By.cssSelector(".product-name, .item-name, td.product-name");
    public By productImages = By.cssSelector(".product-image, img.cart-image");
    public By productPrices = By.cssSelector(".product-price, .item-price, td.price");
    public By quantityInputs = By.cssSelector(".quantity-input, input[name*='quantity'], input[type='number']");
    public By incrementButtons = By.cssSelector(".quantity-increase, .qty-increase, button[class*='increase']");
    public By decrementButtons = By.cssSelector(".quantity-decrease, .qty-decrease, button[class*='decrease']");
    public By removeButtons = By.cssSelector(".remove-item, .delete-item, button[class*='remove']");
    public By saveForLaterButtons = By.cssSelector(".save-for-later, .move-to-wishlist");
    
    // Cart Summary
    public By subtotalLabel = By.xpath("//*[contains(text(), 'Subtotal')]");
    public By subtotalValue = By.cssSelector(".subtotal-amount, .cart-subtotal");
    public By taxLabel = By.xpath("//*[contains(text(), 'Tax')]");
    public By taxValue = By.cssSelector(".tax-amount, .cart-tax");
    public By shippingLabel = By.xpath("//*[contains(text(), 'Shipping')]");
    public By shippingValue = By.cssSelector(".shipping-amount, .cart-shipping");
    public By discountLabel = By.xpath("//*[contains(text(), 'Discount')]");
    public By discountValue = By.cssSelector(".discount-amount, .cart-discount");
    public By totalLabel = By.xpath("//*[contains(text(), 'Total')]");
    public By totalValue = By.cssSelector(".total-amount, .cart-total");
    
    // Action Buttons
    public By proceedToCheckoutButton = By.cssSelector(".checkout-button, button[class*='checkout'], a[href*='checkout']");
    public By continueShoppingButton = By.cssSelector(".continue-shopping, button[class*='continue']");
    public By updateCartButton = By.cssSelector(".update-cart, button[class*='update']");
    public By clearCartButton = By.cssSelector(".clear-cart, button[class*='clear']");
    
    // Promo Code
    public By promoCodeInput = By.cssSelector(".promo-code-input, input[name*='promo'], input[name*='coupon']");
    public By applyPromoButton = By.cssSelector(".apply-promo, button[class*='promo'], button[class*='coupon']");
    public By promoMessage = By.cssSelector(".promo-message, .coupon-message");
    
    // Empty Cart
    public By emptyCartMessage = By.cssSelector(".empty-cart, .cart-empty, .no-items");
    public By emptyCartImage = By.cssSelector(".empty-cart-image, .cart-empty-image");
    public By continueShoppingLink = By.cssSelector(".continue-shopping-link, a[href*='shop']");
    
    // Item Count
    public By cartItemCount = By.cssSelector(".cart-count, .item-count, .cart-items-count");
    
    // Page Indicators
    public By cartTable = By.cssSelector(".cart-table, table.cart");
    public By cartSection = By.cssSelector(".cart-section, .shopping-cart");
}
```

### 2. Create Page Object Class
Create `CartPage.java` in `src/main/java/com/automation/pages/`

## Complete Template

```java
package com.automation.pages;

import com.automation.locators.CartPageLocators;
import com.automation.utils.ExtentReportManager;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Cart Page - Page Object Model
 * Represents the shopping cart page and its elements
 * Handles cart management operations
 */
public class CartPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(CartPage.class);
    private CartPageLocators locators;

    /**
     * Constructor
     * @param driver WebDriver instance
     */
    public CartPage(WebDriver driver) {
        super(driver);
        this.locators = new CartPageLocators();
        logger.info("CartPage initialized");
    }

    // ==================== Navigation Methods ====================

    /**
     * Proceed to checkout
     * @return CheckoutPage instance
     */
    public CheckoutPage proceedToCheckout() {
        logger.info("Proceeding to checkout");
        click(locators.proceedToCheckoutButton, "Proceed to Checkout Button");
        waitForPageLoad();
        return new CheckoutPage(driver);
    }

    /**
     * Continue shopping
     * @return HomePage instance
     */
    public HomePage continueShopping() {
        logger.info("Continuing shopping");
        click(locators.continueShoppingButton, "Continue Shopping Button");
        waitForPageLoad();
        return new HomePage(driver);
    }

    // ==================== Cart Item Methods ====================

    /**
     * Update item quantity
     * @param productName Product name
     * @param quantity New quantity
     * @return CartPage instance
     */
    public CartPage updateItemQuantity(String productName, int quantity) {
        logger.info("Updating quantity for {} to {}", productName, quantity);
        
        // Find the product row and update quantity
        // Implementation depends on your application structure
        // By productRow = By.xpath("//*[contains(text(), '" + productName + "')]/ancestor::tr");
        // By quantityInput = By.xpath("//*[contains(text(), '" + productName + "')]/following::input[@type='number']");
        // clearField(quantityInput, "Quantity Input for " + productName);
        // enterText(quantityInput, String.valueOf(quantity), "Quantity Input for " + productName);
        // click(locators.updateCartButton, "Update Cart Button");
        // waitForPageLoad();
        
        return this;
    }

    /**
     * Increment item quantity
     * @param productName Product name
     * @return CartPage instance
     */
    public CartPage incrementItemQuantity(String productName) {
        logger.info("Incrementing quantity for {}", productName);
        
        // Implementation depends on your application
        // By incrementButton = By.xpath("//*[contains(text(), '" + productName + "')]/following::button[contains(@class, 'increase')]");
        // click(incrementButton, "Increment Button for " + productName);
        // waitForPageLoad();
        
        return this;
    }

    /**
     * Decrement item quantity
     * @param productName Product name
     * @return CartPage instance
     */
    public CartPage decrementItemQuantity(String productName) {
        logger.info("Decrementing quantity for {}", productName);
        
        // Implementation depends on your application
        // By decrementButton = By.xpath("//*[contains(text(), '" + productName + "')]/following::button[contains(@class, 'decrease')]");
        // click(decrementButton, "Decrement Button for " + productName);
        // waitForPageLoad();
        
        return this;
    }

    /**
     * Remove item from cart
     * @param productName Product name
     * @return CartPage instance
     */
    public CartPage removeItem(String productName) {
        logger.info("Removing item from cart: {}", productName);
        
        // Implementation depends on your application
        // By removeButton = By.xpath("//*[contains(text(), '" + productName + "')]/following::button[contains(@class, 'remove')]");
        // click(removeButton, "Remove Button for " + productName);
        // waitForPageLoad();
        
        return this;
    }

    /**
     * Save item for later
     * @param productName Product name
     * @return CartPage instance
     */
    public CartPage saveForLater(String productName) {
        logger.info("Saving item for later: {}", productName);
        
        // Implementation depends on your application
        // By saveForLaterButton = By.xpath("//*[contains(text(), '" + productName + "')]/following::button[contains(text(), 'Save for Later')]");
        // click(saveForLaterButton, "Save for Later Button for " + productName);
        // waitForPageLoad();
        
        return this;
    }

    /**
     * Clear entire cart
     * @return CartPage instance
     */
    public CartPage clearCart() {
        logger.info("Clearing entire cart");
        click(locators.clearCartButton, "Clear Cart Button");
        waitForPageLoad();
        return this;
    }

    // ==================== Promo Code Methods ====================

    /**
     * Apply promo code
     * @param promoCode Promo code to apply
     * @return CartPage instance
     */
    public CartPage applyPromoCode(String promoCode) {
        logger.info("Applying promo code: {}", promoCode);
        enterText(locators.promoCodeInput, promoCode, "Promo Code Input");
        click(locators.applyPromoButton, "Apply Promo Button");
        waitForPageLoad();
        return this;
    }

    /**
     * Check if promo code is applied
     * @return true if promo code is applied
     */
    public boolean isPromoCodeApplied() {
        logger.info("Checking if promo code is applied");
        return isDisplayed(locators.discountValue, "Discount Value") && 
               getCartDiscount() > 0;
    }

    // ==================== Verification Methods ====================

    /**
     * Check if cart page is loaded
     * @return true if cart page is loaded
     */
    public boolean isCartPageLoaded() {
        logger.info("Checking if cart page is loaded");
        return isDisplayed(locators.pageTitle, "Page Title") && 
               (isDisplayed(locators.cartItems, "Cart Items") || 
                isDisplayed(locators.emptyCartMessage, "Empty Cart Message"));
    }

    /**
     * Check if cart has items
     * @return true if cart has items
     */
    public boolean isCartHasItems() {
        logger.info("Checking if cart has items");
        return getElementCount(locators.cartItems, "Cart Items") > 0;
    }

    /**
     * Check if cart is empty
     * @return true if cart is empty
     */
    public boolean isCartEmpty() {
        logger.info("Checking if cart is empty");
        return isDisplayed(locators.emptyCartMessage, "Empty Cart Message");
    }

    /**
     * Check if product is in cart
     * @param productName Product name
     * @return true if product is in cart
     */
    public boolean isProductInCart(String productName) {
        logger.info("Checking if product is in cart: {}", productName);
        // Implementation depends on your application
        // return isDisplayed(By.xpath("//*[contains(text(), '" + productName + "')]"), "Product: " + productName);
        return false;
    }

    // ==================== Getter Methods ====================

    /**
     * Get cart item count
     * @return Number of items in cart
     */
    public int getCartItemCount() {
        logger.info("Getting cart item count");
        String itemCountText = getText(locators.cartItemCount, "Cart Item Count");
        if (itemCountText != null && !itemCountText.isEmpty()) {
            // Extract number from text (e.g., "3 items" -> 3)
            return Integer.parseInt(itemCountText.replaceAll("[^0-9]", ""));
        }
        return 0;
    }

    /**
     * Get item quantity
     * @param productName Product name
     * @return Quantity of the item
     */
    public int getItemQuantity(String productName) {
        logger.info("Getting quantity for: {}", productName);
        
        // Implementation depends on your application
        // By quantityInput = By.xpath("//*[contains(text(), '" + productName + "')]/following::input[@type='number']");
        // String quantityText = getAttribute(quantityInput, "value", "Quantity Input");
        // return Integer.parseInt(quantityText);
        
        return 1; // Placeholder
    }

    /**
     * Get cart subtotal
     * @return Cart subtotal amount
     */
    public double getCartSubtotal() {
        logger.info("Getting cart subtotal");
        String subtotalText = getText(locators.subtotalValue, "Subtotal Value");
        return parsePrice(subtotalText);
    }

    /**
     * Get cart tax
     * @return Cart tax amount
     */
    public double getCartTax() {
        logger.info("Getting cart tax");
        String taxText = getText(locators.taxValue, "Tax Value");
        return parsePrice(taxText);
    }

    /**
     * Get cart shipping cost
     * @return Cart shipping cost
     */
    public double getCartShipping() {
        logger.info("Getting cart shipping cost");
        String shippingText = getText(locators.shippingValue, "Shipping Value");
        return parsePrice(shippingText);
    }

    /**
     * Get cart discount
     * @return Cart discount amount
     */
    public double getCartDiscount() {
        logger.info("Getting cart discount");
        String discountText = getText(locators.discountValue, "Discount Value");
        return parsePrice(discountText);
    }

    /**
     * Get cart total
     * @return Cart total amount
     */
    public double getCartTotal() {
        logger.info("Getting cart total");
        String totalText = getText(locators.totalValue, "Total Value");
        return parsePrice(totalText);
    }

    /**
     * Get product price in cart
     * @param productName Product name
     * @return Product price
     */
    public double getProductPrice(String productName) {
        logger.info("Getting price for: {}", productName);
        
        // Implementation depends on your application
        // By priceElement = By.xpath("//*[contains(text(), '" + productName + "')]/following::td[contains(@class, 'price')]");
        // String priceText = getText(priceElement, "Price for " + productName);
        // return parsePrice(priceText);
        
        return 0.0; // Placeholder
    }

    /**
     * Get empty cart message
     * @return Empty cart message text
     */
    public String getEmptyCartMessage() {
        logger.info("Getting empty cart message");
        return getText(locators.emptyCartMessage, "Empty Cart Message");
    }

    /**
     * Get promo message
     * @return Promo message text
     */
    public String getPromoMessage() {
        logger.info("Getting promo message");
        return getText(locators.promoMessage, "Promo Message");
    }

    // ==================== Utility Methods ====================

    /**
     * Parse price string to double
     * @param priceText Price text (e.g., "$100.00", "100.00")
     * @return Parsed price as double
     */
    private double parsePrice(String priceText) {
        if (priceText == null || priceText.isEmpty()) {
            return 0.0;
        }
        // Remove currency symbols and parse
        String cleanPrice = priceText.replaceAll("[^0-9.]", "");
        return Double.parseDouble(cleanPrice);
    }

    /**
     * Verify cart totals calculation
     * @return true if totals are calculated correctly
     */
    public boolean verifyCartTotals() {
        logger.info("Verifying cart totals calculation");
        
        double subtotal = getCartSubtotal();
        double tax = getCartTax();
        double shipping = getCartShipping();
        double discount = getCartDiscount();
        double total = getCartTotal();
        
        // Calculate expected total: subtotal + tax + shipping - discount
        double expectedTotal = subtotal + tax + shipping - discount;
        
        // Allow small difference due to rounding
        return Math.abs(total - expectedTotal) < 0.01;
    }

    /**
     * Wait for cart to update
     */
    public void waitForCartToUpdate() {
        logger.info("Waiting for cart to update");
        // Wait for cart to refresh after updates
        waitForElementToBeVisible(locators.cartSection, "Cart Section");
    }

    /**
     * Get number of unique products in cart
     * @return Number of unique products
     */
    public int getUniqueProductCount() {
        logger.info("Getting unique product count");
        return getElementCount(locators.productNames, "Product Names");
    }

    /**
     * Get all product names in cart
     * @return Array of product names
     */
    public String[] getProductNamesInCart() {
        logger.info("Getting all product names in cart");
        return getTexts(locators.productNames, "Product Names");
    }

    /**
     * Check if cart summary is displayed
     * @return true if cart summary is displayed
     */
    public boolean isCartSummaryDisplayed() {
        logger.info("Checking if cart summary is displayed");
        return isDisplayed(locators.cartSummary, "Cart Summary");
    }

    /**
     * Get page title
     * @return Page title
     */
    public String getPageTitle() {
        logger.info("Getting page title");
        return getText(locators.pageTitle, "Page Title");
    }
}
```

## Usage Instructions

### How to Use This Template
1. Create the locator class first (`CartPageLocators.java`)
2. Copy the complete page object code
3. Update locators to match your application's actual locators
4. Add/remove methods based on your application's functionality
5. Implement the commented methods with actual locators
6. Ensure all methods have proper logging and error handling

### Customization Points
- **Locators**: Update all By locators to match your application
- **Item Methods**: Customize item manipulation methods based on your cart structure
- **Promo Code**: Implement promo code logic specific to your application
- **Calculations**: Adjust total calculation logic if needed
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
- Implement proper price parsing for different currencies
- Handle edge cases (zero quantity, negative values, etc.)
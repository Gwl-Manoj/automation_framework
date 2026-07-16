# Checkout Test Template

## Role
You are an expert test automation engineer specializing in Selenium WebDriver with TestNG and Java.

## Task
Create a comprehensive Checkout test following the Page Object Model (POM) pattern.

## Context
- **Framework**: Selenium WebDriver + TestNG + Java 11
- **Pattern**: Page Object Model (POM)
- **Base Class**: Extends `BaseTest`
- **Page Objects**: Uses `CartPage`, `CheckoutPage`, `PaymentPage`, `OrderConfirmationPage`

## Requirements

### Test Scenarios to Cover
1. **Positive Test**: Complete checkout with valid information
2. **Positive Test**: Checkout with saved payment method
3. **Negative Test**: Checkout with invalid payment details
4. **Negative Test**: Checkout with empty required fields
5. **Functional Test**: Verify shipping address validation
6. **Functional Test**: Verify order summary calculations
7. **Edge Case**: Checkout with maximum quantity items

### Test Data
- Valid shipping addresses
- Valid payment information (test card numbers)
- Invalid payment details for negative tests
- Promo codes for discount testing

## Instructions

### Test Class Structure
Create test class in `src/test/java/com/automation/tests/CheckoutTest.java`

### Test Methods to Implement

#### 1. testCompleteCheckoutWithValidInformation()
```java
@Test(description = "Verify complete checkout flow with valid information", 
      groups = {"smoke", "checkout", "positive"}, 
      priority = 1)
public void testCompleteCheckoutWithValidInformation() {
    logger.info("Starting test: Complete Checkout with Valid Information");
    
    // Precondition: User is logged in and has items in cart
    navigateToBaseUrl();
    LoginPage loginPage = new LoginPage(WebDriverFactory.getDriver());
    loginPage.login(ConfigReader.getUsername(), ConfigReader.getPassword());
    
    // Add items to cart (if not already present)
    HomePage homePage = new HomePage(WebDriverFactory.getDriver());
    homePage.addItemToCart("laptop");
    homePage.addItemToCart("mouse");
    
    // Navigate to cart
    CartPage cartPage = new CartPage(WebDriverFactory.getDriver());
    homePage.navigateToCart();
    
    // Verify cart has items
    Assert.assertTrue(cartPage.getCartItemCount() > 0, 
        "Cart should have items");
    
    // Proceed to checkout
    cartPage.proceedToCheckout();
    
    // Create checkout page object
    CheckoutPage checkoutPage = new CheckoutPage(WebDriverFactory.getDriver());
    
    // Verify checkout page is loaded
    Assert.assertTrue(checkoutPage.isCheckoutPageLoaded(), 
        "Checkout page should be loaded");
    
    // Fill shipping information
    checkoutPage.enterShippingAddress(
        "John Doe",
        "123 Main Street",
        "New York",
        "NY",
        "10001",
        "USA"
    );
    
    // Select shipping method
    checkoutPage.selectShippingMethod("standard");
    
    // Proceed to payment
    checkoutPage.proceedToPayment();
    
    // Create payment page object
    PaymentPage paymentPage = new PaymentPage(WebDriverFactory.getDriver());
    
    // Verify payment page is loaded
    Assert.assertTrue(paymentPage.isPaymentPageLoaded(), 
        "Payment page should be loaded");
    
    // Enter payment details
    paymentPage.enterCardDetails(
        "4111111111111111", // Valid test card number
        "John Doe",
        "12/25",
        "123"
    );
    
    // Review order summary
    Assert.assertTrue(paymentPage.isOrderSummaryDisplayed(), 
        "Order summary should be displayed");
    
    // Verify order total
    double orderTotal = paymentPage.getOrderTotal();
    Assert.assertTrue(orderTotal > 0, "Order total should be greater than zero");
    
    // Place order
    paymentPage.placeOrder();
    
    // Create order confirmation page object
    OrderConfirmationPage confirmationPage = new OrderConfirmationPage(WebDriverFactory.getDriver());
    
    // Verify order is placed successfully
    Assert.assertTrue(confirmationPage.isOrderConfirmed(), 
        "Order should be confirmed");
    
    // Verify order number is generated
    String orderNumber = confirmationPage.getOrderNumber();
    Assert.assertNotNull(orderNumber, "Order number should not be null");
    Assert.assertFalse(orderNumber.isEmpty(), "Order number should not be empty");
    
    // Verify confirmation message
    Assert.assertTrue(confirmationPage.getConfirmationMessage().contains("Thank you"), 
        "Confirmation message should be displayed");
    
    logger.info("Test completed: Complete Checkout with Valid Information");
}
```

#### 2. testCheckoutWithSavedPaymentMethod()
```java
@Test(description = "Verify checkout with saved payment method", 
      groups = {"regression", "checkout", "positive"}, 
      priority = 2)
public void testCheckoutWithSavedPaymentMethod() {
    logger.info("Starting test: Checkout with Saved Payment Method");
    
    // Precondition: User is logged in with saved payment method
    navigateToBaseUrl();
    LoginPage loginPage = new LoginPage(WebDriverFactory.getDriver());
    loginPage.login(ConfigReader.getUsername(), ConfigReader.getPassword());
    
    // Add item to cart
    HomePage homePage = new HomePage(WebDriverFactory.getDriver());
    homePage.addItemToCart("laptop");
    
    // Navigate to cart and proceed to checkout
    CartPage cartPage = new CartPage(WebDriverFactory.getDriver());
    homePage.navigateToCart();
    cartPage.proceedToCheckout();
    
    // Create checkout page object
    CheckoutPage checkoutPage = new CheckoutPage(WebDriverFactory.getDriver());
    
    // Verify checkout page is loaded
    Assert.assertTrue(checkoutPage.isCheckoutPageLoaded(), 
        "Checkout page should be loaded");
    
    // Fill shipping information
    checkoutPage.enterShippingAddress(
        "John Doe",
        "123 Main Street",
        "New York",
        "NY",
        "10001",
        "USA"
    );
    
    // Select shipping method
    checkoutPage.selectShippingMethod("standard");
    
    // Proceed to payment
    checkoutPage.proceedToPayment();
    
    // Create payment page object
    PaymentPage paymentPage = new PaymentPage(WebDriverFactory.getDriver());
    
    // Verify payment page is loaded
    Assert.assertTrue(paymentPage.isPaymentPageLoaded(), 
        "Payment page should be loaded");
    
    // Select saved payment method
    paymentPage.selectSavedPaymentMethod("Visa ending in 1111");
    
    // Verify card details are displayed
    Assert.assertTrue(paymentPage.isCardDetailsDisplayed(), 
        "Saved card details should be displayed");
    
    // Place order
    paymentPage.placeOrder();
    
    // Verify order confirmation
    OrderConfirmationPage confirmationPage = new OrderConfirmationPage(WebDriverFactory.getDriver());
    Assert.assertTrue(confirmationPage.isOrderConfirmed(), 
        "Order should be confirmed");
    
    logger.info("Test completed: Checkout with Saved Payment Method");
}
```

#### 3. testCheckoutWithInvalidPaymentDetails()
```java
@Test(description = "Verify checkout fails with invalid payment details", 
      groups = {"regression", "checkout", "negative"}, 
      priority = 3)
public void testCheckoutWithInvalidPaymentDetails() {
    logger.info("Starting test: Checkout with Invalid Payment Details");
    
    // Precondition: User is logged in and has items in cart
    navigateToBaseUrl();
    LoginPage loginPage = new LoginPage(WebDriverFactory.getDriver());
    loginPage.login(ConfigReader.getUsername(), ConfigReader.getPassword());
    
    // Add item to cart
    HomePage homePage = new HomePage(WebDriverFactory.getDriver());
    homePage.addItemToCart("laptop");
    
    // Navigate to cart and proceed to checkout
    CartPage cartPage = new CartPage(WebDriverFactory.getDriver());
    homePage.navigateToCart();
    cartPage.proceedToCheckout();
    
    // Create checkout page object
    CheckoutPage checkoutPage = new CheckoutPage(WebDriverFactory.getDriver());
    
    // Fill shipping information
    checkoutPage.enterShippingAddress(
        "John Doe",
        "123 Main Street",
        "New York",
        "NY",
        "10001",
        "USA"
    );
    
    // Select shipping method
    checkoutPage.selectShippingMethod("standard");
    
    // Proceed to payment
    checkoutPage.proceedToPayment();
    
    // Create payment page object
    PaymentPage paymentPage = new PaymentPage(WebDriverFactory.getDriver());
    
    // Enter invalid payment details
    paymentPage.enterCardDetails(
        "4111111111111112", // Invalid test card number
        "John Doe",
        "12/25",
        "123"
    );
    
    // Try to place order
    paymentPage.placeOrder();
    
    // Verify error message is displayed
    Assert.assertTrue(paymentPage.isPaymentErrorDisplayed(), 
        "Payment error should be displayed");
    
    // Verify error message content
    String errorMessage = paymentPage.getPaymentErrorMessage();
    Assert.assertNotNull(errorMessage, "Error message should not be null");
    Assert.assertTrue(errorMessage.toLowerCase().contains("declined") || 
        errorMessage.toLowerCase().contains("invalid"), 
        "Error message should indicate payment failure");
    
    // Verify user remains on payment page
    Assert.assertTrue(paymentPage.isPaymentPageLoaded(), 
        "User should remain on payment page after failed payment");
    
    logger.info("Test completed: Checkout with Invalid Payment Details");
}
```

#### 4. testCheckoutWithEmptyRequiredFields()
```java
@Test(description = "Verify checkout validation with empty required fields", 
      groups = {"regression", "checkout", "negative"}, 
      priority = 4)
public void testCheckoutWithEmptyRequiredFields() {
    logger.info("Starting test: Checkout with Empty Required Fields");
    
    // Precondition: User is logged in and has items in cart
    navigateToBaseUrl();
    LoginPage loginPage = new LoginPage(WebDriverFactory.getDriver());
    loginPage.login(ConfigReader.getUsername(), ConfigReader.getPassword());
    
    // Add item to cart
    HomePage homePage = new HomePage(WebDriverFactory.getDriver());
    homePage.addItemToCart("laptop");
    
    // Navigate to cart and proceed to checkout
    CartPage cartPage = new CartPage(WebDriverFactory.getDriver());
    homePage.navigateToCart();
    cartPage.proceedToCheckout();
    
    // Create checkout page object
    CheckoutPage checkoutPage = new CheckoutPage(WebDriverFactory.getDriver());
    
    // Try to proceed without filling shipping address
    checkoutPage.proceedToPayment();
    
    // Verify validation errors are displayed
    Assert.assertTrue(checkoutPage.isValidationErrorDisplayed(), 
        "Validation error should be displayed");
    
    // Verify specific field errors
    Assert.assertTrue(checkoutPage.isFieldErrorDisplayed("firstName"), 
        "First name field error should be displayed");
    Assert.assertTrue(checkoutPage.isFieldErrorDisplayed("address"), 
        "Address field error should be displayed");
    Assert.assertTrue(checkoutPage.isFieldErrorDisplayed("city"), 
        "City field error should be displayed");
    Assert.assertTrue(checkoutPage.isFieldErrorDisplayed("zipCode"), 
        "Zip code field error should be displayed");
    
    // Verify user remains on checkout page
    Assert.assertTrue(checkoutPage.isCheckoutPageLoaded(), 
        "User should remain on checkout page");
    
    logger.info("Test completed: Checkout with Empty Required Fields");
}
```

#### 5. testShippingAddressValidation()
```java
@Test(description = "Verify shipping address validation", 
      groups = {"regression", "checkout", "functional"}, 
      priority = 5)
public void testShippingAddressValidation() {
    logger.info("Starting test: Shipping Address Validation");
    
    // Precondition: User is logged in and has items in cart
    navigateToBaseUrl();
    LoginPage loginPage = new LoginPage(WebDriverFactory.getDriver());
    loginPage.login(ConfigReader.getUsername(), ConfigReader.getPassword());
    
    // Add item to cart
    HomePage homePage = new HomePage(WebDriverFactory.getDriver());
    homePage.addItemToCart("laptop");
    
    // Navigate to cart and proceed to checkout
    CartPage cartPage = new CartPage(WebDriverFactory.getDriver());
    homePage.navigateToCart();
    cartPage.proceedToCheckout();
    
    // Create checkout page object
    CheckoutPage checkoutPage = new CheckoutPage(WebDriverFactory.getDriver());
    
    // Test invalid zip code format
    checkoutPage.enterShippingAddress(
        "John Doe",
        "123 Main Street",
        "New York",
        "NY",
        "ABC", // Invalid zip code
        "USA"
    );
    
    checkoutPage.proceedToPayment();
    
    // Verify zip code validation error
    Assert.assertTrue(checkoutPage.isFieldErrorDisplayed("zipCode"), 
        "Zip code validation error should be displayed");
    
    // Test valid zip code
    checkoutPage.enterZipCode("10001");
    checkoutPage.proceedToPayment();
    
    // Verify no validation errors
    Assert.assertFalse(checkoutPage.isValidationErrorDisplayed(), 
        "No validation errors should be displayed with valid zip code");
    
    // Verify shipping cost is calculated
    Assert.assertTrue(checkoutPage.getShippingCost() >= 0, 
        "Shipping cost should be calculated");
    
    logger.info("Test completed: Shipping Address Validation");
}
```

#### 6. testOrderSummaryCalculations()
```java
@Test(description = "Verify order summary calculations are correct", 
      groups = {"regression", "checkout", "functional"}, 
      priority = 6)
public void testOrderSummaryCalculations() {
    logger.info("Starting test: Order Summary Calculations");
    
    // Precondition: User is logged in and has items in cart
    navigateToBaseUrl();
    LoginPage loginPage = new LoginPage(WebDriverFactory.getDriver());
    loginPage.login(ConfigReader.getUsername(), ConfigReader.getPassword());
    
    // Add specific items with known prices
    HomePage homePage = new HomePage(WebDriverFactory.getDriver());
    homePage.addItemToCart("laptop"); // Assume $1000
    homePage.addItemToCart("mouse"); // Assume $25
    
    // Navigate to cart
    CartPage cartPage = new CartPage(WebDriverFactory.getDriver());
    homePage.navigateToCart();
    
    // Verify cart subtotal
    double cartSubtotal = cartPage.getCartSubtotal();
    Assert.assertTrue(cartSubtotal > 0, "Cart subtotal should be greater than zero");
    
    // Proceed to checkout
    cartPage.proceedToCheckout();
    
    // Create checkout page object
    CheckoutPage checkoutPage = new CheckoutPage(WebDriverFactory.getDriver());
    
    // Fill shipping information
    checkoutPage.enterShippingAddress(
        "John Doe",
        "123 Main Street",
        "New York",
        "NY",
        "10001",
        "USA"
    );
    
    // Select shipping method
    checkoutPage.selectShippingMethod("standard");
    
    // Verify shipping cost
    double shippingCost = checkoutPage.getShippingCost();
    Assert.assertTrue(shippingCost >= 0, "Shipping cost should be non-negative");
    
    // Proceed to payment
    checkoutPage.proceedToPayment();
    
    // Create payment page object
    PaymentPage paymentPage = new PaymentPage(WebDriverFactory.getDriver());
    
    // Verify order summary calculations
    double orderSubtotal = paymentPage.getOrderSubtotal();
    double orderShipping = paymentPage.getOrderShipping();
    double orderTax = paymentPage.getOrderTax();
    double orderTotal = paymentPage.getOrderTotal();
    
    // Verify calculations
    Assert.assertEquals(orderSubtotal, cartSubtotal, 0.01, 
        "Order subtotal should match cart subtotal");
    
    Assert.assertEquals(orderShipping, shippingCost, 0.01, 
        "Order shipping should match selected shipping cost");
    
    Assert.assertTrue(orderTax >= 0, "Order tax should be non-negative");
    
    // Verify total calculation: subtotal + shipping + tax = total
    double calculatedTotal = orderSubtotal + orderShipping + orderTax;
    Assert.assertEquals(orderTotal, calculatedTotal, 0.01, 
        "Order total should equal subtotal + shipping + tax");
    
    logger.info("Test completed: Order Summary Calculations");
}
```

#### 7. testCheckoutWithMaximumQuantity()
```java
@Test(description = "Verify checkout with maximum quantity items", 
      groups = {"regression", "checkout", "edgecase"}, 
      priority = 7)
public void testCheckoutWithMaximumQuantity() {
    logger.info("Starting test: Checkout with Maximum Quantity");
    
    // Precondition: User is logged in
    navigateToBaseUrl();
    LoginPage loginPage = new LoginPage(WebDriverFactory.getDriver());
    loginPage.login(ConfigReader.getUsername(), ConfigReader.getPassword());
    
    // Add item with maximum quantity
    HomePage homePage = new HomePage(WebDriverFactory.getDriver());
    homePage.addItemToCartWithQuantity("laptop", 99);
    
    // Navigate to cart
    CartPage cartPage = new CartPage(WebDriverFactory.getDriver());
    homePage.navigateToCart();
    
    // Verify quantity in cart
    Assert.assertEquals(cartPage.getItemQuantity("laptop"), 99, 
        "Item quantity should be 99");
    
    // Verify cart total
    double cartTotal = cartPage.getCartTotal();
    Assert.assertTrue(cartTotal > 0, "Cart total should be greater than zero");
    
    // Proceed to checkout
    cartPage.proceedToCheckout();
    
    // Create checkout page object
    CheckoutPage checkoutPage = new CheckoutPage(WebDriverFactory.getDriver());
    
    // Fill shipping information
    checkoutPage.enterShippingAddress(
        "John Doe",
        "123 Main Street",
        "New York",
        "NY",
        "10001",
        "USA"
    );
    
    // Select shipping method
    checkoutPage.selectShippingMethod("standard");
    
    // Proceed to payment
    checkoutPage.proceedToPayment();
    
    // Create payment page object
    PaymentPage paymentPage = new PaymentPage(WebDriverFactory.getDriver());
    
    // Verify order summary shows correct quantity
    Assert.assertEquals(paymentPage.getItemQuantity("laptop"), 99, 
        "Order summary should show correct quantity");
    
    // Enter payment details
    paymentPage.enterCardDetails(
        "4111111111111111",
        "John Doe",
        "12/25",
        "123"
    );
    
    // Place order
    paymentPage.placeOrder();
    
    // Verify order confirmation
    OrderConfirmationPage confirmationPage = new OrderConfirmationPage(WebDriverFactory.getDriver());
    Assert.assertTrue(confirmationPage.isOrderConfirmed(), 
        "Order should be confirmed");
    
    // Verify order details
    Assert.assertEquals(confirmationPage.getItemQuantity("laptop"), 99, 
        "Order confirmation should show correct quantity");
    
    logger.info("Test completed: Checkout with Maximum Quantity");
}
```

## Complete Template

```java
package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.pages.*;
import com.automation.utils.ConfigReader;
import com.automation.utils.WebDriverFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Checkout Test Class
 * Test cases for checkout functionality
 * Following Page Object Model - Test class only contains test logic
 */
public class CheckoutTest extends BaseTest {

    /**
     * Test complete checkout flow with valid information
     */
    @Test(description = "Verify complete checkout flow with valid information", 
          groups = {"smoke", "checkout", "positive"}, 
          priority = 1)
    public void testCompleteCheckoutWithValidInformation() {
        logger.info("Starting test: Complete Checkout with Valid Information");
        
        // Precondition: User is logged in and has items in cart
        navigateToBaseUrl();
        LoginPage loginPage = new LoginPage(WebDriverFactory.getDriver());
        loginPage.login(ConfigReader.getUsername(), ConfigReader.getPassword());
        
        // Add items to cart (if not already present)
        HomePage homePage = new HomePage(WebDriverFactory.getDriver());
        homePage.addItemToCart("laptop");
        homePage.addItemToCart("mouse");
        
        // Navigate to cart
        CartPage cartPage = new CartPage(WebDriverFactory.getDriver());
        homePage.navigateToCart();
        
        // Verify cart has items
        Assert.assertTrue(cartPage.getCartItemCount() > 0, 
            "Cart should have items");
        
        // Proceed to checkout
        cartPage.proceedToCheckout();
        
        // Create checkout page object
        CheckoutPage checkoutPage = new CheckoutPage(WebDriverFactory.getDriver());
        
        // Verify checkout page is loaded
        Assert.assertTrue(checkoutPage.isCheckoutPageLoaded(), 
            "Checkout page should be loaded");
        
        // Fill shipping information
        checkoutPage.enterShippingAddress(
            "John Doe",
            "123 Main Street",
            "New York",
            "NY",
            "10001",
            "USA"
        );
        
        // Select shipping method
        checkoutPage.selectShippingMethod("standard");
        
        // Proceed to payment
        checkoutPage.proceedToPayment();
        
        // Create payment page object
        PaymentPage paymentPage = new PaymentPage(WebDriverFactory.getDriver());
        
        // Verify payment page is loaded
        Assert.assertTrue(paymentPage.isPaymentPageLoaded(), 
            "Payment page should be loaded");
        
        // Enter payment details
        paymentPage.enterCardDetails(
            "4111111111111111", // Valid test card number
            "John Doe",
            "12/25",
            "123"
        );
        
        // Review order summary
        Assert.assertTrue(paymentPage.isOrderSummaryDisplayed(), 
            "Order summary should be displayed");
        
        // Verify order total
        double orderTotal = paymentPage.getOrderTotal();
        Assert.assertTrue(orderTotal > 0, "Order total should be greater than zero");
        
        // Place order
        paymentPage.placeOrder();
        
        // Create order confirmation page object
        OrderConfirmationPage confirmationPage = new OrderConfirmationPage(WebDriverFactory.getDriver());
        
        // Verify order is placed successfully
        Assert.assertTrue(confirmationPage.isOrderConfirmed(), 
            "Order should be confirmed");
        
        // Verify order number is generated
        String orderNumber = confirmationPage.getOrderNumber();
        Assert.assertNotNull(orderNumber, "Order number should not be null");
        Assert.assertFalse(orderNumber.isEmpty(), "Order number should not be empty");
        
        // Verify confirmation message
        Assert.assertTrue(confirmationPage.getConfirmationMessage().contains("Thank you"), 
            "Confirmation message should be displayed");
        
        logger.info("Test completed: Complete Checkout with Valid Information");
    }

    /**
     * Test checkout with saved payment method
     */
    @Test(description = "Verify checkout with saved payment method", 
          groups = {"regression", "checkout", "positive"}, 
          priority = 2)
    public void testCheckoutWithSavedPaymentMethod() {
        logger.info("Starting test: Checkout with Saved Payment Method");
        
        // Precondition: User is logged in with saved payment method
        navigateToBaseUrl();
        LoginPage loginPage = new LoginPage(WebDriverFactory.getDriver());
        loginPage.login(ConfigReader.getUsername(), ConfigReader.getPassword());
        
        // Add item to cart
        HomePage homePage = new HomePage(WebDriverFactory.getDriver());
        homePage.addItemToCart("laptop");
        
        // Navigate to cart and proceed to checkout
        CartPage cartPage = new CartPage(WebDriverFactory.getDriver());
        homePage.navigateToCart();
        cartPage.proceedToCheckout();
        
        // Create checkout page object
        CheckoutPage checkoutPage = new CheckoutPage(WebDriverFactory.getDriver());
        
        // Verify checkout page is loaded
        Assert.assertTrue(checkoutPage.isCheckoutPageLoaded(), 
            "Checkout page should be loaded");
        
        // Fill shipping information
        checkoutPage.enterShippingAddress(
            "John Doe",
            "123 Main Street",
            "New York",
            "NY",
            "10001",
            "USA"
        );
        
        // Select shipping method
        checkoutPage.selectShippingMethod("standard");
        
        // Proceed to payment
        checkoutPage.proceedToPayment();
        
        // Create payment page object
        PaymentPage paymentPage = new PaymentPage(WebDriverFactory.getDriver());
        
        // Verify payment page is loaded
        Assert.assertTrue(paymentPage.isPaymentPageLoaded(), 
            "Payment page should be loaded");
        
        // Select saved payment method
        paymentPage.selectSavedPaymentMethod("Visa ending in 1111");
        
        // Verify card details are displayed
        Assert.assertTrue(paymentPage.isCardDetailsDisplayed(), 
            "Saved card details should be displayed");
        
        // Place order
        paymentPage.placeOrder();
        
        // Verify order confirmation
        OrderConfirmationPage confirmationPage = new OrderConfirmationPage(WebDriverFactory.getDriver());
        Assert.assertTrue(confirmationPage.isOrderConfirmed(), 
            "Order should be confirmed");
        
        logger.info("Test completed: Checkout with Saved Payment Method");
    }

    /**
     * Test checkout fails with invalid payment details
     */
    @Test(description = "Verify checkout fails with invalid payment details", 
          groups = {"regression", "checkout", "negative"}, 
          priority = 3)
    public void testCheckoutWithInvalidPaymentDetails() {
        logger.info("Starting test: Checkout with Invalid Payment Details");
        
        // Precondition: User is logged in and has items in cart
        navigateToBaseUrl();
        LoginPage loginPage = new LoginPage(WebDriverFactory.getDriver());
        loginPage.login(ConfigReader.getUsername(), ConfigReader.getPassword());
        
        // Add item to cart
        HomePage homePage = new HomePage(WebDriverFactory.getDriver());
        homePage.addItemToCart("laptop");
        
        // Navigate to cart and proceed to checkout
        CartPage cartPage = new CartPage(WebDriverFactory.getDriver());
        homePage.navigateToCart();
        cartPage.proceedToCheckout();
        
        // Create checkout page object
        CheckoutPage checkoutPage = new CheckoutPage(WebDriverFactory.getDriver());
        
        // Fill shipping information
        checkoutPage.enterShippingAddress(
            "John Doe",
            "123 Main Street",
            "New York",
            "NY",
            "10001",
            "USA"
        );
        
        // Select shipping method
        checkoutPage.selectShippingMethod("standard");
        
        // Proceed to payment
        checkoutPage.proceedToPayment();
        
        // Create payment page object
        PaymentPage paymentPage = new PaymentPage(WebDriverFactory.getDriver());
        
        // Enter invalid payment details
        paymentPage.enterCardDetails(
            "4111111111111112", // Invalid test card number
            "John Doe",
            "12/25",
            "123"
        );
        
        // Try to place order
        paymentPage.placeOrder();
        
        // Verify error message is displayed
        Assert.assertTrue(paymentPage.isPaymentErrorDisplayed(), 
            "Payment error should be displayed");
        
        // Verify error message content
        String errorMessage = paymentPage.getPaymentErrorMessage();
        Assert.assertNotNull(errorMessage, "Error message should not be null");
        Assert.assertTrue(errorMessage.toLowerCase().contains("declined") || 
            errorMessage.toLowerCase().contains("invalid"), 
            "Error message should indicate payment failure");
        
        // Verify user remains on payment page
        Assert.assertTrue(paymentPage.isPaymentPageLoaded(), 
            "User should remain on payment page after failed payment");
        
        logger.info("Test completed: Checkout with Invalid Payment Details");
    }

    /**
     * Test checkout validation with empty required fields
     */
    @Test(description = "Verify checkout validation with empty required fields", 
          groups = {"regression", "checkout", "negative"}, 
          priority = 4)
    public void testCheckoutWithEmptyRequiredFields() {
        logger.info("Starting test: Checkout with Empty Required Fields");
        
        // Precondition: User is logged in and has items in cart
        navigateToBaseUrl();
        LoginPage loginPage = new LoginPage(WebDriverFactory.getDriver());
        loginPage.login(ConfigReader.getUsername(), ConfigReader.getPassword());
        
        // Add item to cart
        HomePage homePage = new HomePage(WebDriverFactory.getDriver());
        homePage.addItemToCart("laptop");
        
        // Navigate to cart and proceed to checkout
        CartPage cartPage = new CartPage(WebDriverFactory.getDriver());
        homePage.navigateToCart();
        cartPage.proceedToCheckout();
        
        // Create checkout page object
        CheckoutPage checkoutPage = new CheckoutPage(WebDriverFactory.getDriver());
        
        // Try to proceed without filling shipping address
        checkoutPage.proceedToPayment();
        
        // Verify validation errors are displayed
        Assert.assertTrue(checkoutPage.isValidationErrorDisplayed(), 
            "Validation error should be displayed");
        
        // Verify specific field errors
        Assert.assertTrue(checkoutPage.isFieldErrorDisplayed("firstName"), 
            "First name field error should be displayed");
        Assert.assertTrue(checkoutPage.isFieldErrorDisplayed("address"), 
            "Address field error should be displayed");
        Assert.assertTrue(checkoutPage.isFieldErrorDisplayed("city"), 
            "City field error should be displayed");
        Assert.assertTrue(checkoutPage.isFieldErrorDisplayed("zipCode"), 
            "Zip code field error should be displayed");
        
        // Verify user remains on checkout page
        Assert.assertTrue(checkoutPage.isCheckoutPageLoaded(), 
            "User should remain on checkout page");
        
        logger.info("Test completed: Checkout with Empty Required Fields");
    }

    /**
     * Test shipping address validation
     */
    @Test(description = "Verify shipping address validation", 
          groups = {"regression", "checkout", "functional"}, 
          priority = 5)
    public void testShippingAddressValidation() {
        logger.info("Starting test: Shipping Address Validation");
        
        // Precondition: User is logged in and has items in cart
        navigateToBaseUrl();
        LoginPage loginPage = new LoginPage(WebDriverFactory.getDriver());
        loginPage.login(ConfigReader.getUsername(), ConfigReader.getPassword());
        
        // Add item to cart
        HomePage homePage = new HomePage(WebDriverFactory.getDriver());
        homePage.addItemToCart("laptop");
        
        // Navigate to cart and proceed to checkout
        CartPage cartPage = new CartPage(WebDriverFactory.getDriver());
        homePage.navigateToCart();
        cartPage.proceedToCheckout();
        
        // Create checkout page object
        CheckoutPage checkoutPage = new CheckoutPage(WebDriverFactory.getDriver());
        
        // Test invalid zip code format
        checkoutPage.enterShippingAddress(
            "John Doe",
            "123 Main Street",
            "New York",
            "NY",
            "ABC", // Invalid zip code
            "USA"
        );
        
        checkoutPage.proceedToPayment();
        
        // Verify zip code validation error
        Assert.assertTrue(checkoutPage.isFieldErrorDisplayed("zipCode"), 
            "Zip code validation error should be displayed");
        
        // Test valid zip code
        checkoutPage.enterZipCode("10001");
        checkoutPage.proceedToPayment();
        
        // Verify no validation errors
        Assert.assertFalse(checkoutPage.isValidationErrorDisplayed(), 
            "No validation errors should be displayed with valid zip code");
        
        // Verify shipping cost is calculated
        Assert.assertTrue(checkoutPage.getShippingCost() >= 0, 
            "Shipping cost should be calculated");
        
        logger.info("Test completed: Shipping Address Validation");
    }

    /**
     * Test order summary calculations are correct
     */
    @Test(description = "Verify order summary calculations are correct", 
          groups = {"regression", "checkout", "functional"}, 
          priority = 6)
    public void testOrderSummaryCalculations() {
        logger.info("Starting test: Order Summary Calculations");
        
        // Precondition: User is logged in and has items in cart
        navigateToBaseUrl();
        LoginPage loginPage = new LoginPage(WebDriverFactory.getDriver());
        loginPage.login(ConfigReader.getUsername(), ConfigReader.getPassword());
        
        // Add specific items with known prices
        HomePage homePage = new HomePage(WebDriverFactory.getDriver());
        homePage.addItemToCart("laptop"); // Assume $1000
        homePage.addItemToCart("mouse"); // Assume $25
        
        // Navigate to cart
        CartPage cartPage = new CartPage(WebDriverFactory.getDriver());
        homePage.navigateToCart();
        
        // Verify cart subtotal
        double cartSubtotal = cartPage.getCartSubtotal();
        Assert.assertTrue(cartSubtotal > 0, "Cart subtotal should be greater than zero");
        
        // Proceed to checkout
        cartPage.proceedToCheckout();
        
        // Create checkout page object
        CheckoutPage checkoutPage = new CheckoutPage(WebDriverFactory.getDriver());
        
        // Fill shipping information
        checkoutPage.enterShippingAddress(
            "John Doe",
            "123 Main Street",
            "New York",
            "NY",
            "10001",
            "USA"
        );
        
        // Select shipping method
        checkoutPage.selectShippingMethod("standard");
        
        // Verify shipping cost
        double shippingCost = checkoutPage.getShippingCost();
        Assert.assertTrue(shippingCost >= 0, "Shipping cost should be non-negative");
        
        // Proceed to payment
        checkoutPage.proceedToPayment();
        
        // Create payment page object
        PaymentPage paymentPage = new PaymentPage(WebDriverFactory.getDriver());
        
        // Verify order summary calculations
        double orderSubtotal = paymentPage.getOrderSubtotal();
        double orderShipping = paymentPage.getOrderShipping();
        double orderTax = paymentPage.getOrderTax();
        double orderTotal = paymentPage.getOrderTotal();
        
        // Verify calculations
        Assert.assertEquals(orderSubtotal, cartSubtotal, 0.01, 
            "Order subtotal should match cart subtotal");
        
        Assert.assertEquals(orderShipping, shippingCost, 0.01, 
            "Order shipping should match selected shipping cost");
        
        Assert.assertTrue(orderTax >= 0, "Order tax should be non-negative");
        
        // Verify total calculation: subtotal + shipping + tax = total
        double calculatedTotal = orderSubtotal + orderShipping + orderTax;
        Assert.assertEquals(orderTotal, calculatedTotal, 0.01, 
            "Order total should equal subtotal + shipping + tax");
        
        logger.info("Test completed: Order Summary Calculations");
    }

    /**
     * Test checkout with maximum quantity items
     */
    @Test(description = "Verify checkout with maximum quantity items", 
          groups = {"regression", "checkout", "edgecase"}, 
          priority = 7)
    public void testCheckoutWithMaximumQuantity() {
        logger.info("Starting test: Checkout with Maximum Quantity");
        
        // Precondition: User is logged in
        navigateToBaseUrl();
        LoginPage loginPage = new LoginPage(WebDriverFactory.getDriver());
        loginPage.login(ConfigReader.getUsername(), ConfigReader.getPassword());
        
        // Add item with maximum quantity
        HomePage homePage = new HomePage(WebDriverFactory.getDriver());
        homePage.addItemToCartWithQuantity("laptop", 99);
        
        // Navigate to cart
        CartPage cartPage = new CartPage(WebDriverFactory.getDriver());
        homePage.navigateToCart();
        
        // Verify quantity in cart
        Assert.assertEquals(cartPage.getItemQuantity("laptop"), 99, 
            "Item quantity should be 99");
        
        // Verify cart total
        double cartTotal = cartPage.getCartTotal();
        Assert.assertTrue(cartTotal > 0, "Cart total should be greater than zero");
        
        // Proceed to checkout
        cartPage.proceedToCheckout();
        
        // Create checkout page object
        CheckoutPage checkoutPage = new CheckoutPage(WebDriverFactory.getDriver());
        
        // Fill shipping information
        checkoutPage.enterShippingAddress(
            "John Doe",
            "123 Main Street",
            "New York",
            "NY",
            "10001",
            "USA"
        );
        
        // Select shipping method
        checkoutPage.selectShippingMethod("standard");
        
        // Proceed to payment
        checkoutPage.proceedToPayment();
        
        // Create payment page object
        PaymentPage paymentPage = new PaymentPage(WebDriverFactory.getDriver());
        
        // Verify order summary shows correct quantity
        Assert.assertEquals(paymentPage.getItemQuantity("laptop"), 99, 
            "Order summary should show correct quantity");
        
        // Enter payment details
        paymentPage.enterCardDetails(
            "4111111111111111",
            "John Doe",
            "12/25",
            "123"
        );
        
        // Place order
        paymentPage.placeOrder();
        
        // Verify order confirmation
        OrderConfirmationPage confirmationPage = new OrderConfirmationPage(WebDriverFactory.getDriver());
        Assert.assertTrue(confirmationPage.isOrderConfirmed(), 
            "Order should be confirmed");
        
        // Verify order details
        Assert.assertEquals(confirmationPage.getItemQuantity("laptop"), 99, 
            "Order confirmation should show correct quantity");
        
        logger.info("Test completed: Checkout with Maximum Quantity");
    }
}
```

## Usage Instructions

### How to Use This Template
1. Copy the complete template code
2. Replace placeholder values with actual values:
   - Update shipping addresses to match your test data
   - Modify payment details (use test card numbers)
   - Adjust assertions based on actual application behavior
3. Ensure corresponding page objects exist:
   - `CartPage`
   - `CheckoutPage`
   - `PaymentPage`
   - `OrderConfirmationPage`
4. Update test groups and priorities as per your test strategy
5. Add/remove test methods based on your requirements

### Customization Points
- **Shipping Addresses**: Replace with actual test data or use test data files
- **Payment Details**: Use test card numbers provided by payment gateway
- **Shipping Methods**: Modify based on available shipping options in your application
- **Assertions**: Adjust based on actual application behavior
- **Test Groups**: Modify groups to match your test management strategy
- **Priorities**: Set priorities based on test execution order requirements
- **Additional Tests**: Add more test methods for other scenarios (e.g., promo codes, gift wrapping, different currencies)

## Best Practices
- Keep tests independent and idempotent
- Use meaningful test method names
- Add descriptive assertion messages
- Log key test steps
- Group tests appropriately for execution
- Follow the Page Object Model pattern strictly
- Test both positive and negative scenarios
- Include edge cases for comprehensive coverage
- Verify financial calculations (totals, taxes, discounts)
- Use test payment card numbers (never real card numbers)
- Clean up test data after test execution
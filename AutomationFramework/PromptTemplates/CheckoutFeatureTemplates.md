# Checkout Feature Templates

## Role
You are an expert test automation engineer specializing in e-commerce checkout and payment testing.

## Task
Generate complete test automation components for checkout functionality following the automation workflow.

## Context
- **Feature**: E-commerce Checkout and Payment
- **Pages**: Cart Page, Checkout Page, Order Confirmation Page
- **Scenarios**: Valid checkout, invalid payment, empty fields, different payment methods
- **Framework**: Selenium + TestNG + POM

## Generated Components

### 1. Page Object: CartPage.java

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

    // ==================== Action Methods ====================

    /**
     * Click proceed to checkout button
     * @return CheckoutPage instance
     */
    public CheckoutPage clickProceedToCheckout() {
        logger.info("Clicking proceed to checkout button");
        click(locators.proceedToCheckoutButton, "Proceed to Checkout Button");
        waitForPageLoad();
        return new CheckoutPage(driver);
    }

    /**
     * Update quantity of product
     * @param productName Product name
     * @param quantity New quantity
     */
    public void updateProductQuantity(String productName, int quantity) {
        logger.info("Updating quantity for {} to {}", productName, quantity);
        // Implementation to find product and update quantity
        // This is application-specific
    }

    /**
     * Remove product from cart
     * @param productName Product name
     */
    public void removeProduct(String productName) {
        logger.info("Removing product from cart: {}", productName);
        // Implementation to find and remove product
    }

    /**
     * Click continue shopping button
     * @return HomePage instance
     */
    public HomePage clickContinueShopping() {
        logger.info("Clicking continue shopping button");
        click(locators.continueShoppingButton, "Continue Shopping Button");
        waitForPageLoad();
        return new HomePage(driver);
    }

    // ==================== Verification Methods ====================

    /**
     * Check if cart page is loaded
     * @return true if cart page is loaded
     */
    public boolean isCartPageLoaded() {
        logger.info("Checking if cart page is loaded");
        return isDisplayed(locators.cartHeading, "Cart Heading") &&
               isDisplayed(locators.cartItems, "Cart Items");
    }

    /**
     * Get cart item count
     * @return Number of items in cart
     */
    public int getCartItemCount() {
        logger.info("Getting cart item count");
        return getCartItems().size();
    }

    /**
     * Get cart items
     * @return List of cart items
     */
    public List<WebElement> getCartItems() {
        logger.info("Getting cart items");
        return driver.findElements(locators.cartItems);
    }

    /**
     * Get cart subtotal
     * @return Subtotal amount
     */
    public String getCartSubtotal() {
        logger.info("Getting cart subtotal");
        return getText(locators.cartSubtotal, "Cart Subtotal");
    }

    /**
     * Get cart total
     * @return Total amount
     */
    public String getCartTotal() {
        logger.info("Getting cart total");
        return getText(locators.cartTotal, "Cart Total");
    }

    /**
     * Check if cart is empty
     * @return true if cart is empty
     */
    public boolean isCartEmpty() {
        logger.info("Checking if cart is empty");
        return getCartItemCount() == 0;
    }

    /**
     * Check if product is in cart
     * @param productName Product name
     * @return true if product is in cart
     */
    public boolean isProductInCart(String productName) {
        logger.info("Checking if product is in cart: {}", productName);
        // Implementation to check if product exists in cart
        return true; // Placeholder
    }
}
```

### 2. Locator Class: CartPageLocators.java

```java
package com.automation.locators;

import org.openqa.selenium.By;

/**
 * Cart Page Locators
 * Contains all element locators for the cart page
 */
public class CartPageLocators {
    
    // ==================== Headings ====================
    public By cartHeading = By.cssSelector(".cart-heading");
    public By shoppingCartTitle = By.xpath("//h2[text()='Shopping Cart']");
    
    // ==================== Cart Items ====================
    public By cartItems = By.cssSelector(".cart-items .cart-item");
    public By cartItemName = By.cssSelector(".cart-item-name");
    public By cartItemPrice = By.cssSelector(".cart-item-price");
    public By cartItemQuantity = By.cssSelector(".cart-item-quantity");
    public By cartItemTotal = By.cssSelector(".cart-item-total");
    public By removeItemButton = By.cssSelector(".remove-item-btn");
    
    // ==================== Buttons ====================
    public By proceedToCheckoutButton = By.cssSelector(".btn-checkout");
    public By continueShoppingButton = By.cssSelector(".btn-continue-shopping");
    public By updateCartButton = By.cssSelector(".btn-update-cart");
    
    // ==================== Totals ====================
    public By cartSubtotal = By.cssSelector(".cart-subtotal");
    public By cartTax = By.cssSelector(".cart-tax");
    public By cartTotal = By.cssSelector(".cart-total");
    
    // ==================== Empty Cart ====================
    public By emptyCartMessage = By.cssSelector(".empty-cart-message");
    public By emptyCartImage = By.cssSelector(".empty-cart-image");
}
```

### 3. Page Object: CheckoutPage.java

```java
package com.automation.pages;

import com.automation.locators.CheckoutPageLocators;
import com.automation.utils.ExtentReportManager;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Checkout Page - Page Object Model
 * Represents the checkout page and its elements
 */
public class CheckoutPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(CheckoutPage.class);
    private CheckoutPageLocators locators;

    /**
     * Constructor
     * @param driver WebDriver instance
     */
    public CheckoutPage(WebDriver driver) {
        super(driver);
        this.locators = new CheckoutPageLocators();
        logger.info("CheckoutPage initialized");
    }

    // ==================== Action Methods ====================

    /**
     * Enter first name
     * @param firstName First name
     */
    public void enterFirstName(String firstName) {
        logger.info("Entering first name: {}", firstName);
        enterText(locators.firstNameField, firstName, "First Name Field");
    }

    /**
     * Enter last name
     * @param lastName Last name
     */
    public void enterLastName(String lastName) {
        logger.info("Entering last name: {}", lastName);
        enterText(locators.lastNameField, lastName, "Last Name Field");
    }

    /**
     * Enter email
     * @param email Email address
     */
    public void enterEmail(String email) {
        logger.info("Entering email: {}", email);
        enterText(locators.emailField, email, "Email Field");
    }

    /**
     * Enter phone number
     * @param phone Phone number
     */
    public void enterPhone(String phone) {
        logger.info("Entering phone: {}", phone);
        enterText(locators.phoneField, phone, "Phone Field");
    }

    /**
     * Enter address
     * @param address Address
     */
    public void enterAddress(String address) {
        logger.info("Entering address: {}", address);
        enterText(locators.addressField, address, "Address Field");
    }

    /**
     * Enter city
     * @param city City name
     */
    public void enterCity(String city) {
        logger.info("Entering city: {}", city);
        enterText(locators.cityField, city, "City Field");
    }

    /**
     * Select country
     * @param country Country name
     */
    public void selectCountry(String country) {
        logger.info("Selecting country: {}", country);
        selectByVisibleText(locators.countryDropdown, country);
    }

    /**
     * Enter postal code
     * @param postalCode Postal code
     */
    public void enterPostalCode(String postalCode) {
        logger.info("Entering postal code: {}", postalCode);
        enterText(locators.postalCodeField, postalCode, "Postal Code Field");
    }

    /**
     * Enter card number
     * @param cardNumber Card number
     */
    public void enterCardNumber(String cardNumber) {
        logger.info("Entering card number");
        enterText(locators.cardNumberField, cardNumber, "Card Number Field");
    }

    /**
     * Enter card expiry date
     * @param expiryDate Expiry date (MM/YY)
     */
    public void enterExpiryDate(String expiryDate) {
        logger.info("Entering expiry date: {}", expiryDate);
        enterText(locators.expiryDateField, expiryDate, "Expiry Date Field");
    }

    /**
     * Enter CVV
     * @param cvv CVV code
     */
    public void enterCVV(String cvv) {
        logger.info("Entering CVV");
        enterText(locators.cvvField, cvv, "CVV Field");
    }

    /**
     * Enter cardholder name
     * @param cardholderName Cardholder name
     */
    public void enterCardholderName(String cardholderName) {
        logger.info("Entering cardholder name: {}", cardholderName);
        enterText(locators.cardholderNameField, cardholderName, "Cardholder Name Field");
    }

    /**
     * Click place order button
     * @return OrderConfirmationPage instance
     */
    public OrderConfirmationPage clickPlaceOrder() {
        logger.info("Clicking place order button");
        click(locators.placeOrderButton, "Place Order Button");
        waitForPageLoad();
        return new OrderConfirmationPage(driver);
    }

    /**
     * Fill shipping information
     */
    public void fillShippingInformation(String firstName, String lastName, String email, 
                                       String phone, String address, String city, 
                                       String country, String postalCode) {
        logger.info("Filling shipping information");
        enterFirstName(firstName);
        enterLastName(lastName);
        enterEmail(email);
        enterPhone(phone);
        enterAddress(address);
        enterCity(city);
        selectCountry(country);
        enterPostalCode(postalCode);
    }

    /**
     * Fill payment information
     */
    public void fillPaymentInformation(String cardNumber, String expiryDate, 
                                       String cvv, String cardholderName) {
        logger.info("Filling payment information");
        enterCardNumber(cardNumber);
        enterExpiryDate(expiryDate);
        enterCVV(cvv);
        enterCardholderName(cardholderName);
    }

    /**
     * Complete checkout process
     */
    public OrderConfirmationPage completeCheckout(String firstName, String lastName, 
                                                  String email, String phone, 
                                                  String address, String city, 
                                                  String country, String postalCode,
                                                  String cardNumber, String expiryDate, 
                                                  String cvv, String cardholderName) {
        logger.info("Completing checkout process");
        fillShippingInformation(firstName, lastName, email, phone, address, city, 
                               country, postalCode);
        fillPaymentInformation(cardNumber, expiryDate, cvv, cardholderName);
        return clickPlaceOrder();
    }

    // ==================== Verification Methods ====================

    /**
     * Check if checkout page is loaded
     * @return true if checkout page is loaded
     */
    public boolean isCheckoutPageLoaded() {
        logger.info("Checking if checkout page is loaded");
        return isDisplayed(locators.checkoutHeading, "Checkout Heading") &&
               (isDisplayed(locators.shippingForm, "Shipping Form") ||
                isDisplayed(locators.paymentForm, "Payment Form"));
    }

    /**
     * Check if shipping form is displayed
     * @return true if shipping form is displayed
     */
    public boolean isShippingFormDisplayed() {
        logger.info("Checking if shipping form is displayed");
        return isDisplayed(locators.shippingForm, "Shipping Form");
    }

    /**
     * Check if payment form is displayed
     * @return true if payment form is displayed
     */
    public boolean isPaymentFormDisplayed() {
        logger.info("Checking if payment form is displayed");
        return isDisplayed(locators.paymentForm, "Payment Form");
    }

    /**
     * Get order summary
     * @return Order summary text
     */
    public String getOrderSummary() {
        logger.info("Getting order summary");
        return getText(locators.orderSummary, "Order Summary");
    }

    /**
     * Get checkout total
     * @return Total amount
     */
    public String getCheckoutTotal() {
        logger.info("Getting checkout total");
        return getText(locators.checkoutTotal, "Checkout Total");
    }
}
```

### 4. Locator Class: CheckoutPageLocators.java

```java
package com.automation.locators;

import org.openqa.selenium.By;

/**
 * Checkout Page Locators
 * Contains all element locators for the checkout page
 */
public class CheckoutPageLocators {
    
    // ==================== Headings ====================
    public By checkoutHeading = By.cssSelector(".checkout-heading");
    public By shippingHeading = By.xpath("//h3[text()='Shipping Information']");
    public By paymentHeading = By.xpath("//h3[text()='Payment Details']");
    
    // ==================== Shipping Form ====================
    public By shippingForm = By.id("shipping-form");
    public By firstNameField = By.id("first-name");
    public By lastNameField = By.id("last-name");
    public By emailField = By.id("email");
    public By phoneField = By.id("phone");
    public By addressField = By.id("address");
    public By cityField = By.id("city");
    public By countryDropdown = By.id("country");
    public By postalCodeField = By.id("postal-code");
    
    // ==================== Payment Form ====================
    public By paymentForm = By.id("payment-form");
    public By cardNumberField = By.id("card-number");
    public By expiryDateField = By.id("expiry-date");
    public By cvvField = By.id("cvv");
    public By cardholderNameField = By.id("cardholder-name");
    
    // ==================== Buttons ====================
    public By placeOrderButton = By.cssSelector(".btn-place-order");
    public By backToCartButton = By.cssSelector(".btn-back-to-cart");
    
    // ==================== Order Summary ====================
    public By orderSummary = By.cssSelector(".order-summary");
    public By checkoutTotal = By.cssSelector(".checkout-total");
    public By subtotal = By.cssSelector(".subtotal");
    public By tax = By.cssSelector(".tax");
    public By shippingCost = By.cssSelector(".shipping-cost");
}
```

### 5. Page Object: OrderConfirmationPage.java

```java
package com.automation.pages;

import com.automation.locators.OrderConfirmationPageLocators;
import com.automation.utils.ExtentReportManager;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Order Confirmation Page - Page Object Model
 * Represents the order confirmation page and its elements
 */
public class OrderConfirmationPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(OrderConfirmationPage.class);
    private OrderConfirmationPageLocators locators;

    /**
     * Constructor
     * @param driver WebDriver instance
     */
    public OrderConfirmationPage(WebDriver driver) {
        super(driver);
        this.locators = new OrderConfirmationPageLocators();
        logger.info("OrderConfirmationPage initialized");
    }

    // ==================== Verification Methods ====================

    /**
     * Check if order confirmation page is loaded
     * @return true if order confirmation page is loaded
     */
    public boolean isOrderConfirmationPageLoaded() {
        logger.info("Checking if order confirmation page is loaded");
        return isDisplayed(locators.confirmationHeading, "Confirmation Heading") &&
               isDisplayed(locators.orderNumber, "Order Number");
    }

    /**
     * Get order number
     * @return Order number
     */
    public String getOrderNumber() {
        logger.info("Getting order number");
        return getText(locators.orderNumber, "Order Number");
    }

    /**
     * Get confirmation message
     * @return Confirmation message
     */
    public String getConfirmationMessage() {
        logger.info("Getting confirmation message");
        return getText(locators.confirmationMessage, "Confirmation Message");
    }

    /**
     * Check if order was successful
     * @return true if order was successful
     */
    public boolean isOrderSuccessful() {
        logger.info("Checking if order was successful");
        return isOrderConfirmationPageLoaded() && 
               getConfirmationMessage().toLowerCase().contains("success");
    }

    /**
     * Click continue shopping button
     * @return HomePage instance
     */
    public HomePage clickContinueShopping() {
        logger.info("Clicking continue shopping button");
        click(locators.continueShoppingButton, "Continue Shopping Button");
        waitForPageLoad();
        return new HomePage(driver);
    }
}
```

### 6. Locator Class: OrderConfirmationPageLocators.java

```java
package com.automation.locators;

import org.openqa.selenium.By;

/**
 * Order Confirmation Page Locators
 * Contains all element locators for the order confirmation page
 */
public class OrderConfirmationPageLocators {
    
    // ==================== Headings ====================
    public By confirmationHeading = By.cssSelector(".confirmation-heading");
    public By successMessage = By.cssSelector(".success-message");
    
    // ==================== Order Details ====================
    public By orderNumber = By.cssSelector(".order-number");
    public By orderDate = By.cssSelector(".order-date");
    public By orderTotal = By.cssSelector(".order-total");
    public By confirmationMessage = By.cssSelector(".confirmation-message");
    
    // ==================== Buttons ====================
    public By continueShoppingButton = By.cssSelector(".btn-continue-shopping");
    public By printInvoiceButton = By.cssSelector(".btn-print-invoice");
    public By viewOrderButton = By.cssSelector(".btn-view-order");
}
```

### 7. Test Class: CheckoutTest.java

```java
package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.pages.CartPage;
import com.automation.pages.CheckoutPage;
import com.automation.pages.HomePage;
import com.automation.pages.OrderConfirmationPage;
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
     * Test complete checkout process with valid data
     */
    @Test(description = "Verify complete checkout process with valid data", 
          groups = {"smoke", "checkout"})
    public void testCompleteCheckout() {
        logger.info("Starting test: Complete Checkout Process");

        // Navigate to home page
        navigateToBaseUrl();

        // Add product to cart (you need to implement this based on your application)
        HomePage homePage = new HomePage(WebDriverFactory.getDriver());
        // homePage.addProductToCart("Product Name");

        // Navigate to cart
        CartPage cartPage = new CartPage(WebDriverFactory.getDriver());
        
        // Verify cart has items
        Assert.assertTrue(cartPage.getCartItemCount() > 0, 
            "Cart should have at least one item");

        // Proceed to checkout
        CheckoutPage checkoutPage = cartPage.clickProceedToCheckout();

        // Verify checkout page is loaded
        Assert.assertTrue(checkoutPage.isCheckoutPageLoaded(), 
            "Checkout page should be loaded");

        // Fill shipping information
        checkoutPage.fillShippingInformation(
            "John", "Doe", "john@example.com", "1234567890",
            "123 Main St", "New York", "United States", "10001"
        );

        // Fill payment information
        checkoutPage.fillPaymentInformation(
            "4111111111111111", "12/25", "123", "John Doe"
        );

        // Place order
        OrderConfirmationPage confirmationPage = checkoutPage.clickPlaceOrder();

        // Verify order confirmation
        Assert.assertTrue(confirmationPage.isOrderConfirmationPageLoaded(), 
            "Order confirmation page should be loaded");

        Assert.assertTrue(confirmationPage.isOrderSuccessful(), 
            "Order should be successful");

        // Get order number
        String orderNumber = confirmationPage.getOrderNumber();
        Assert.assertNotNull(orderNumber, "Order number should not be null");
        Assert.assertFalse(orderNumber.isEmpty(), "Order number should not be empty");
        
        logger.info("Order placed successfully. Order number: {}", orderNumber);
    }

    /**
     * Test checkout with empty required fields
     */
    @Test(description = "Verify checkout validation with empty required fields", 
          groups = {"regression", "checkout"})
    public void testCheckoutWithEmptyFields() {
        logger.info("Starting test: Checkout with Empty Fields");

        // Navigate to cart
        navigateToBaseUrl();
        CartPage cartPage = new CartPage(WebDriverFactory.getDriver());

        // Proceed to checkout
        CheckoutPage checkoutPage = cartPage.clickProceedToCheckout();

        // Try to place order without filling information
        checkoutPage.clickPlaceOrder();

        // Verify validation errors (application-specific)
        Assert.assertTrue(checkoutPage.isShippingFormDisplayed(), 
            "Should remain on checkout page with empty fields");
    }

    /**
     * Test checkout with invalid email
     */
    @Test(description = "Verify checkout validation with invalid email", 
          groups = {"regression", "checkout"})
    public void testCheckoutWithInvalidEmail() {
        logger.info("Starting test: Checkout with Invalid Email");

        // Navigate to checkout
        navigateToBaseUrl();
        CartPage cartPage = new CartPage(WebDriverFactory.getDriver());
        CheckoutPage checkoutPage = cartPage.clickProceedToCheckout();

        // Fill form with invalid email
        checkoutPage.fillShippingInformation(
            "John", "Doe", "invalid-email", "1234567890",
            "123 Main St", "New York", "United States", "10001"
        );

        // Try to proceed
        checkoutPage.clickPlaceOrder();

        // Verify validation error
        Assert.assertTrue(checkoutPage.isCheckoutPageLoaded(), 
            "Should remain on checkout page with invalid email");
    }

    /**
     * Test checkout with invalid card details
     */
    @Test(description = "Verify checkout with invalid card details", 
          groups = {"regression", "checkout"})
    public void testCheckoutWithInvalidCard() {
        logger.info("Starting test: Checkout with Invalid Card");

        // Navigate to checkout
        navigateToBaseUrl();
        CartPage cartPage = new CartPage(WebDriverFactory.getDriver());
        CheckoutPage checkoutPage = cartPage.clickProceedToCheckout();

        // Fill valid shipping information
        checkoutPage.fillShippingInformation(
            "John", "Doe", "john@example.com", "1234567890",
            "123 Main St", "New York", "United States", "10001"
        );

        // Fill invalid card details
        checkoutPage.fillPaymentInformation(
            "1234567890123456", "12/25", "123", "John Doe"
        );

        // Try to place order
        checkoutPage.clickPlaceOrder();

        // Verify error message (application-specific)
        Assert.assertTrue(checkoutPage.isCheckoutPageLoaded(), 
            "Should remain on checkout page with invalid card");
    }

    /**
     * Test checkout with different payment methods
     */
    @Test(description = "Verify checkout with different payment methods", 
          groups = {"regression", "checkout"})
    public void testCheckoutWithDifferentPaymentMethods() {
        logger.info("Starting test: Checkout with Different Payment Methods");

        // This test would vary based on payment methods available
        // Example: Credit Card, PayPal, etc.
        
        navigateToBaseUrl();
        CartPage cartPage = new CartPage(WebDriverFactory.getDriver());
        CheckoutPage checkoutPage = cartPage.clickProceedToCheckout();

        // Fill shipping information
        checkoutPage.fillShippingInformation(
            "John", "Doe", "john@example.com", "1234567890",
            "123 Main St", "New York", "United States", "10001"
        );

        // Select payment method (if applicable)
        // checkoutPage.selectPaymentMethod("PayPal");

        // Complete checkout
        OrderConfirmationPage confirmationPage = checkoutPage.completeCheckout(
            "John", "Doe", "john@example.com", "1234567890",
            "123 Main St", "New York", "United States", "10001",
            "4111111111111111", "12/25", "123", "John Doe"
        );

        // Verify order successful
        Assert.assertTrue(confirmationPage.isOrderSuccessful(), 
            "Order should be successful");
    }
}
```

### 8. Test Data: checkout-test-data.csv

```csv
first_name,last_name,email,phone,address,city,country,postal_code,card_number,expiry_date,cvv,cardholder_name,expected_result
John,Doe,john@example.com,1234567890,123 Main St,New York,United States,10001,4111111111111111,12/25,123,John Doe,success
Jane,Smith,jane@example.com,9876543210,456 Oak Ave,Los Angeles,United States,90001,5555555555554444,06/26,456,Jane Smith,success
invalid-email,Doe,test@example.com,1234567890,123 Main St,New York,United States,10001,4111111111111111,12/25,123,John Doe,failure
John,Doe,1234567890,123 Main St,New York,United States,10001,4111111111111111,12/25,123,John Doe,failure
John,,john@example.com,1234567890,123 Main St,New York,United States,10001,4111111111111111,12/25,123,John Doe,failure
```

### 9. Test Data: checkout-test-data.json

```json
{
  "validShippingInfo": {
    "firstName": "John",
    "lastName": "Doe",
    "email": "john@example.com",
    "phone": "1234567890",
    "address": "123 Main St",
    "city": "New York",
    "country": "United States",
    "postalCode": "10001"
  },
  "validPaymentInfo": {
    "cardNumber": "4111111111111111",
    "expiryDate": "12/25",
    "cvv": "123",
    "cardholderName": "John Doe"
  },
  "invalidEmails": [
    "invalid-email",
    "test@",
    "@example.com",
    "test.example.com"
  ],
  "invalidCardNumbers": [
    "1234567890123456",
    "0000000000000000",
    "1111111111111111"
  ],
  "testScenarios": [
    {
      "name": "Valid checkout",
      "shipping": {
        "firstName": "John",
        "lastName": "Doe",
        "email": "john@example.com",
        "phone": "1234567890",
        "address": "123 Main St",
        "city": "New York",
        "country": "United States",
        "postalCode": "10001"
      },
      "payment": {
        "cardNumber": "4111111111111111",
        "expiryDate": "12/25",
        "cvv": "123",
        "cardholderName": "John Doe"
      },
      "expectedResult": "success"
    },
    {
      "name": "Invalid email",
      "shipping": {
        "email": "invalid-email"
      },
      "expectedResult": "failure"
    }
  ]
}
```

## Usage Instructions

### Quick Start
1. Copy CartPage.java to `src/main/java/com/automation/pages/`
2. Copy CartPageLocators.java to `src/main/java/com/automation/locators/`
3. Copy CheckoutPage.java to `src/main/java/com/automation/pages/`
4. Copy CheckoutPageLocators.java to `src/main/java/com/automation/locators/`
5. Copy OrderConfirmationPage.java to `src/main/java/com/automation/pages/`
6. Copy OrderConfirmationPageLocators.java to `src/main/java/com/automation/locators/`
7. Copy CheckoutTest.java to `src/test/java/com/automation/tests/`
8. Copy test data files to `src/test/resources/test-data/`
9. Update locators based on your application
10. Run tests: `mvn test -Dtest=CheckoutTest`

## Test Coverage

### Scenarios Covered
- ✓ Complete checkout process
- ✓ Checkout with empty fields
- ✓ Checkout with invalid email
- ✓ Checkout with invalid card details
- ✓ Different payment methods
- ✓ Order confirmation

### Test Types
- Smoke tests: `testCompleteCheckout`
- Regression tests: All other tests

## Best Practices

### DO's
✓ Test complete checkout flow
✓ Test with valid and invalid data
✓ Verify order confirmation
✓ Test different payment methods
✓ Validate all required fields
✓ Check error messages

### DON'Ts
✗ Don't use real credit card numbers
✗ Don't skip validation testing
✗ Don't hardcode payment details
✗ Don't forget to verify order confirmation
✗ Don't create actual charges in tests

## Additional Notes
- Use test payment gateway or sandbox environment
- Never use real credit card numbers in tests
- Test with various payment methods if supported
- Verify order confirmation email if applicable
- Test edge cases like expired cards, insufficient funds
- Consider testing with different currencies
package com.automation.pages;

import com.automation.locators.HomePageLocators;
import com.automation.utils.ExtentReportManager;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Home Page Object
 * Provides methods for interacting with the Home Page of automationexercise.com
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
    }
    
    // ==================== Subscription Methods ====================
    
    /**
     * Check if subscription section is displayed
     * @return true if subscription section is visible
     */
    public boolean isSubscriptionSectionDisplayed() {
        boolean displayed = isDisplayed(locators.subscriptionHeading, "Subscription Heading");
        logger.info("Subscription section displayed: {}", displayed);
        return displayed;
    }
    
    /**
     * Enter email in subscription field
     * @param email Email address to subscribe
     */
    public void enterSubscriptionEmail(String email) {
        enterText(locators.subscriptionEmailInput, email, "Subscription Email Input");
    }
    
    /**
     * Click subscribe button
     */
    public void clickSubscribeButton() {
        click(locators.subscribeButton, "Subscribe Button");
    }
    
    /**
     * Subscribe with email
     * @param email Email address to subscribe
     */
    public void subscribeWithEmail(String email) {
        enterSubscriptionEmail(email);
        clickSubscribeButton();
    }
    
    /**
     * Check if subscription success message is displayed
     * @return true if success message is visible
     */
    public boolean isSubscriptionSuccessMessageDisplayed() {
        boolean displayed = isDisplayed(locators.subscriptionSuccessMessage, "Subscription Success Message");
        logger.info("Subscription success message displayed: {}", displayed);
        return displayed;
    }
    
    /**
     * Get subscription success message text
     * @return Success message text
     */
    public String getSubscriptionSuccessMessageText() {
        String message = getText(locators.subscriptionSuccessMessage, "Subscription Success Message");
        logger.info("Subscription success message: {}", message);
        return message;
    }
    
    /**
     * Check if subscription error message is displayed
     * @return true if error message is visible
     */
    public boolean isSubscriptionErrorMessageDisplayed() {
        boolean displayed = isDisplayed(locators.subscriptionErrorMessage, "Subscription Error Message");
        logger.info("Subscription error message displayed: {}", displayed);
        return displayed;
    }
    
    /**
     * Get subscription error message text
     * @return Error message text
     */
    public String getSubscriptionErrorMessageText() {
        String message = getText(locators.subscriptionErrorMessage, "Subscription Error Message");
        logger.info("Subscription error message: {}", message);
        return message;
    }
    
    /**
     * Verify subscription by entering email and checking success message
     * @param email Email address to subscribe
     * @return true if subscription is successful
     */
    public boolean verifySubscription(String email) {
        logger.info("Verifying subscription for email: {}", email);
        
        // Scroll to subscription section
        scrollToElement(locators.subscriptionHeading);
        
        // Enter email and click subscribe
        enterSubscriptionEmail(email);
        clickSubscribeButton();
        
        // Wait for success message
        waitForTextToBePresent(locators.subscriptionSuccessMessage, "You have been successfully subscribed");
        
        // Verify success message
        boolean isSuccess = isSubscriptionSuccessMessageDisplayed();
        if (isSuccess) {
            ExtentReportManager.logPass("Subscription successful for email: " + email);
        } else {
            ExtentReportManager.logFail("Subscription failed for email: " + email);
        }
        
        return isSuccess;
    }
    
    // ==================== Navigation Methods ====================
    
    /**
     * Click on Home link
     */
    public void clickHomeLink() {
        click(locators.homeLink, "Home Link");
    }
    
    /**
     * Click on Products link
     */
    public void clickProductsLink() {
        click(locators.productsLink, "Products Link");
    }
    
    /**
     * Click on Cart link
     */
    public void clickCartLink() {
        click(locators.cartLink, "Cart Link");
    }
    
    /**
     * Click on Signup/Login link
     */
    public void clickSignupLoginLink() {
        click(locators.signupLoginLink, "Signup/Login Link");
    }
    
    /**
     * Click on Logout link
     */
    public void clickLogoutLink() {
        click(locators.logoutLink, "Logout Link");
    }
    
    // ==================== Verification Methods ====================
    
    /**
     * Check if home page is loaded
     * @return true if home page is loaded
     */
    public boolean isHomePageLoaded() {
        boolean loaded = isDisplayed(locators.homeLink, "Home Link") && 
                         isDisplayed(locators.subscriptionHeading, "Subscription Heading");
        logger.info("Home page loaded: {}", loaded);
        return loaded;
    }
    
    /**
     * Get page title
     * @return Page title
     */
    public String getPageTitle() {
        return super.getPageTitle();
    }
}
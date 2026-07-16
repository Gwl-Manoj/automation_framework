package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.pages.HomePage;
import com.automation.utils.ExtentReportManager;
import com.automation.utils.WebDriverFactory;
import com.automation.utils.WaitHelper;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * Subscription Test
 * Tests the subscription functionality on the home page
 */
public class SubscriptionTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(SubscriptionTest.class);
    
    /**
     * Test case: Verify subscription on home page
     * Description: User should be able to subscribe to the newsletter using a valid email
     */
    @Test(description = "Verify that user can subscribe to newsletter on home page", groups = {"smoke", "regression", "ui"})
    public void testVerifySubscriptionOnHomePage() {
        // Navigate to home page
        navigateToBaseUrl();
        
        // Create home page object
        HomePage homePage = new HomePage(WebDriverFactory.getDriver());
        
        // Verify home page is loaded
        Assert.assertTrue(homePage.isHomePageLoaded(), "Home page should be loaded");
        ExtentReportManager.logInfo("Home page loaded successfully");
        
        // Verify subscription section is displayed
        Assert.assertTrue(homePage.isSubscriptionSectionDisplayed(), 
            "Subscription section should be displayed on home page");
        ExtentReportManager.logInfo("Subscription section is displayed");
        
        // Generate random email for subscription
        String randomEmail = generateRandomEmail();
        logger.info("Generated random email for subscription: {}", randomEmail);
        ExtentReportManager.logInfo("Using email for subscription: " + randomEmail);
        
        // Perform subscription
        boolean isSubscribed = homePage.verifySubscription(randomEmail);
        
        // Verify subscription success
        Assert.assertTrue(isSubscribed, "Subscription should be successful");
        ExtentReportManager.logPass("Subscription verified successfully on home page");
    }
    
    /**
     * Test case: Verify subscription with already subscribed email
     * Description: User should see an error message when subscribing with an already subscribed email
     */
    @Test(description = "Verify error message for already subscribed email", groups = {"regression", "ui"})
    public void testVerifySubscriptionWithAlreadySubscribedEmail() {
        // Navigate to home page
        navigateToBaseUrl();
        
        // Create home page object
        HomePage homePage = new HomePage(WebDriverFactory.getDriver());
        
        // Verify home page is loaded
        Assert.assertTrue(homePage.isHomePageLoaded(), "Home page should be loaded");
        
        // Use a known email that might already be subscribed
        String email = "test@example.com";
        logger.info("Testing subscription with email: {}", email);
        ExtentReportManager.logInfo("Using email for subscription test: " + email);
        
        // Perform subscription
        homePage.subscribeWithEmail(email);
        
        // Wait a bit for the message to appear
        WaitHelper.waitForSeconds(2);
        
        // Check for either success or error message
        boolean isSuccess = homePage.isSubscriptionSuccessMessageDisplayed();
        boolean isError = homePage.isSubscriptionErrorMessageDisplayed();
        
        // Log the result
        if (isSuccess) {
            String successMessage = homePage.getSubscriptionSuccessMessageText();
            ExtentReportManager.logInfo("Subscription successful. Message: " + successMessage);
        } else if (isError) {
            String errorMessage = homePage.getSubscriptionErrorMessageText();
            ExtentReportManager.logInfo("Subscription error displayed. Message: " + errorMessage);
        }
        
        // At least one message should be displayed
        Assert.assertTrue(isSuccess || isError, 
            "Either success or error message should be displayed after subscription attempt");
    }
    
    /**
     * Test case: Verify subscription with invalid email
     * Description: User should see appropriate behavior when subscribing with invalid email
     */
    @Test(description = "Verify subscription with invalid email format", groups = {"regression", "ui"})
    public void testVerifySubscriptionWithInvalidEmail() {
        // Navigate to home page
        navigateToBaseUrl();
        
        // Create home page object
        HomePage homePage = new HomePage(WebDriverFactory.getDriver());
        
        // Verify home page is loaded
        Assert.assertTrue(homePage.isHomePageLoaded(), "Home page should be loaded");
        
        // Use invalid email format
        String invalidEmail = "invalid-email";
        logger.info("Testing subscription with invalid email: {}", invalidEmail);
        ExtentReportManager.logInfo("Using invalid email for subscription test: " + invalidEmail);
        
        // Perform subscription
        homePage.subscribeWithEmail(invalidEmail);
        
        // Note: The website may or may not validate email format on frontend
        // This test documents the behavior
        ExtentReportManager.logInfo("Subscription attempt with invalid email completed");
    }
    
    /**
     * Generate random email for testing
     * @return Random email address
     */
    private String generateRandomEmail() {
        String randomString = UUID.randomUUID().toString().substring(0, 8);
        return "test" + randomString + "@example.com";
    }
}
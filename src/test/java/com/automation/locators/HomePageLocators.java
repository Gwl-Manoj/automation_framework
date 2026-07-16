package com.automation.locators;

import org.openqa.selenium.By;

/**
 * HomePage Locators
 * Contains all locators for the Home Page of automationexercise.com
 */
public class HomePageLocators {
    
    // Header elements
    public By homeLink = By.xpath("//a[@href='/'][contains(text(), 'Home')]");
    public By productsLink = By.xpath("//a[@href='/products'][contains(text(), 'Products')]");
    public By cartLink = By.xpath("//a[@href='/view_cart'][contains(text(), 'Cart')]");
    public By signupLoginLink = By.xpath("//a[@href='/login'][contains(text(), 'Signup / Login')]");
    public By logoutLink = By.xpath("//a[@href='/logout'][contains(text(), 'Logout')]");
    public By deleteAccountLink = By.xpath("//a[@href='/delete_account'][contains(text(), 'Delete Account')]");
    
    // Subscription section
    public By subscriptionHeading = By.xpath("//h2[contains(text(), 'Subscription')]");
    public By subscriptionEmailInput = By.id("susbscribe_email");
    public By subscribeButton = By.cssSelector("input#susbscribe_email + button");
    public By subscriptionSuccessMessage = By.cssSelector(".alert-success.alert");
    public By subscriptionErrorMessage = By.cssSelector(".alert-danger.alert");
    
    // Footer
    public By footerSection = By.id("footer");
    
    // Carousel/Slider
    public By carouselIndicators = By.className("carousel-indicators");
    public By carouselItems = By.className("carousel-item");
    
    // Features section
    public By featuresItems = By.className("features_items");
    public By addToCartButtons = By.xpath("//div[@class='product-image-wrapper']//a[@data-product-id]");
}
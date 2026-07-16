package com.automation.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * WebDriver Factory - Creates and manages WebDriver instances
 * Supports Chrome, Firefox, Edge, and Safari browsers
 */
public class WebDriverFactory {
    private static final Logger logger = LoggerFactory.getLogger(WebDriverFactory.class);
    private static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    /**
     * Initialize WebDriver based on configuration
     * @return WebDriver instance
     */
    public static WebDriver initializeDriver() {
        String browser = ConfigReader.getBrowser().toLowerCase();
        boolean headless = ConfigReader.isHeadless();
        boolean incognito = ConfigReader.getBooleanProperty("incognito", false);

        logger.info("Initializing {} browser. Headless: {}, Incognito: {}", browser, headless, incognito);

        // Check if driver is already initialized
        if (getDriverOrNull() != null) {
            logger.warn("WebDriver is already initialized. Quitting existing driver first.");
            quitDriver();
        }

        WebDriver driver;

        switch (browser) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                configureChromeOptions(chromeOptions, headless, incognito);
                driver = new ChromeDriver(chromeOptions);
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                configureFirefoxOptions(firefoxOptions, headless, incognito);
                driver = new FirefoxDriver(firefoxOptions);
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                configureEdgeOptions(edgeOptions, headless, incognito);
                driver = new EdgeDriver(edgeOptions);
                break;

            case "safari":
                driver = new SafariDriver();
                break;

            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(ConfigReader.getImplicitWait()));
        driver.manage().timeouts().pageLoadTimeout(java.time.Duration.ofSeconds(ConfigReader.getPageLoadTimeout()));
        driver.manage().timeouts().scriptTimeout(java.time.Duration.ofSeconds(ConfigReader.getScriptTimeout()));

        driverThreadLocal.set(driver);
        logger.info("WebDriver initialized successfully. Driver instance: {}", driver);
        
        // Verify driver was set
        WebDriver verifyDriver = getDriverOrNull();
        if (verifyDriver == null) {
            throw new IllegalStateException("Failed to store WebDriver in ThreadLocal");
        }
        
        return driver;
    }

    /**
     * Configure Chrome options
     */
    private static void configureChromeOptions(ChromeOptions options, boolean headless, boolean incognito) {
        if (headless) {
            options.addArguments("--headless=new");
        }
        if (incognito) {
            options.addArguments("--incognito");
        }
        if (ConfigReader.getBooleanProperty("chrome.start.maximized", true)) {
            options.addArguments("--start-maximized");
        }
        if (ConfigReader.getBooleanProperty("chrome.disable.infobars", true)) {
            options.addArguments("--disable-infobars");
        }
        if (ConfigReader.getBooleanProperty("chrome.disable.extensions", true)) {
            options.addArguments("--disable-extensions");
        }
        if (ConfigReader.getBooleanProperty("chrome.disable.notifications", true)) {
            options.addArguments("--disable-notifications");
        }
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--remote-allow-origins=*");
    }

    /**
     * Configure Firefox options
     */
    private static void configureFirefoxOptions(FirefoxOptions options, boolean headless, boolean incognito) {
        if (headless) {
            options.addArguments("--headless");
        }
        if (incognito) {
            options.addArguments("-private");
        }
        options.addArguments("--width=1920");
        options.addArguments("--height=1080");
    }

    /**
     * Configure Edge options
     */
    private static void configureEdgeOptions(EdgeOptions options, boolean headless, boolean incognito) {
        if (headless) {
            options.addArguments("--headless=new");
        }
        if (incognito) {
            options.addArguments("--inprivate");
        }
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
    }

    /**
     * Get current WebDriver instance
     * @return WebDriver instance
     * @throws IllegalStateException if WebDriver is not initialized
     */
    public static WebDriver getDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver == null) {
            throw new IllegalStateException(
                "WebDriver is not initialized. Ensure WebDriverFactory.initializeDriver() is called in @BeforeMethod before using the driver. " +
                "Check that BaseTest.setUp() is being executed properly."
            );
        }
        return driver;
    }

    /**
     * Get current WebDriver instance without throwing exception
     * @return WebDriver instance or null if not initialized
     */
    public static WebDriver getDriverOrNull() {
        return driverThreadLocal.get();
    }

    /**
     * Quit WebDriver instance
     */
    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            driver.quit();
            driverThreadLocal.remove();
            logger.info("WebDriver quit successfully");
        }
    }

    /**
     * Navigate to URL
     * @param url URL to navigate
     * @throws IllegalStateException if WebDriver is not initialized
     */
    public static void navigateTo(String url) {
        WebDriver driver = getDriver();
        logger.info("Navigating to: {}", url);
        driver.get(url);
    }
}
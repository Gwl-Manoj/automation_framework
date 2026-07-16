package com.automation.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Configuration Reader Utility
 * Reads configuration from config.properties.
 *
 * <p>Resolution order (so the framework works both standalone and as a Maven dependency):
 * <ol>
 *   <li>External file path supplied via the {@code config.file} system property.</li>
 *   <li>A file at {@code config/config.properties} relative to the working directory.</li>
 *   <li>The bundled {@code config/config.properties} resource on the classpath (inside the jar).</li>
 * </ol>
 * Consumers of this framework can therefore override defaults by providing their own
 * {@code config/config.properties} on the classpath or via the {@code config.file} system property,
 * without needing access to the framework's internal source.
 */
public class ConfigReader {
    private static Properties properties;
    private static final String CONFIG_RESOURCE = "config/config.properties";

    static {
        properties = new Properties();
        loadConfiguration();
    }

    /**
     * Loads configuration following the resolution order documented on the class.
     */
    private static void loadConfiguration() {
        InputStream inputStream = null;
        String source = null;

        // 1. External file via system property (highest priority)
        String externalPath = System.getProperty("config.file");
        if (externalPath != null && !externalPath.trim().isEmpty()) {
            File externalFile = new File(externalPath.trim());
            if (externalFile.exists()) {
                try {
                    inputStream = new FileInputStream(externalFile);
                    source = externalFile.getAbsolutePath();
                } catch (IOException e) {
                    System.err.println("Unable to read config from system property path: " + externalPath);
                }
            }
        }

        // 2. File relative to the working directory
        if (inputStream == null) {
            File localFile = new File(CONFIG_RESOURCE);
            if (localFile.exists()) {
                try {
                    inputStream = new FileInputStream(localFile);
                    source = localFile.getAbsolutePath();
                } catch (IOException e) {
                    System.err.println("Unable to read config from: " + localFile.getAbsolutePath());
                }
            }
        }

        // 3. Classpath resource bundled inside the jar
        if (inputStream == null) {
            inputStream = ConfigReader.class.getClassLoader().getResourceAsStream(CONFIG_RESOURCE);
            if (inputStream != null) {
                source = "classpath:" + CONFIG_RESOURCE;
            }
        }

        if (inputStream == null) {
            throw new RuntimeException(
                "Failed to load configuration '" + CONFIG_RESOURCE + "'. Provide it via the 'config.file' " +
                "system property, place it at 'config/config.properties' in the working directory, " +
                "or ensure it is on the classpath."
            );
        }

        try {
            properties.load(inputStream);
            inputStream.close();
            System.out.println("AutomationFramework: configuration loaded from " + source);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration file: " + source, e);
        }
    }

    /**
     * Get property value by key
     * @param key Property key
     * @return Property value
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * Get property value by key with default value
     * @param key Property key
     * @param defaultValue Default value if key not found
     * @return Property value or default value
     */
    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    /**
     * Get integer property value
     * @param key Property key
     * @param defaultValue Default value if key not found
     * @return Integer value
     */
    public static int getIntProperty(String key, int defaultValue) {
        String value = properties.getProperty(key);
        if (value != null) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                System.err.println("Invalid integer value for key: " + key);
            }
        }
        return defaultValue;
    }

    /**
     * Get boolean property value
     * @param key Property key
     * @param defaultValue Default value if key not found
     * @return Boolean value
     */
    public static boolean getBooleanProperty(String key, boolean defaultValue) {
        String value = properties.getProperty(key);
        if (value != null) {
            return Boolean.parseBoolean(value);
        }
        return defaultValue;
    }

    /**
     * Get browser type
     * @return Browser name (chrome, firefox, edge)
     */
    public static String getBrowser() {
        return getProperty("browser", "chrome");
    }

    /**
     * Get headless mode
     * @return true if headless mode is enabled
     */
    public static boolean isHeadless() {
        return getBooleanProperty("headless", false);
    }

    /**
     * Get implicit wait timeout
     * @return Timeout in seconds
     */
    public static int getImplicitWait() {
        return getIntProperty("implicit.wait", 10);
    }

    /**
     * Get explicit wait timeout
     * @return Timeout in seconds
     */
    public static int getExplicitWait() {
        return getIntProperty("explicit.wait", 20);
    }

    /**
     * Get page load timeout
     * @return Timeout in seconds
     */
    public static int getPageLoadTimeout() {
        return getIntProperty("page.load.timeout", 30);
    }

    /**
     * Get base URL
     * @return Base URL
     */
    public static String getBaseUrl() {
        return getProperty("base.url");
    }

    /**
     * Get username
     * @return Username
     */
    public static String getUsername() {
        return getProperty("username");
    }

    /**
     * Get password
     * @return Password
     */
    public static String getPassword() {
        return getProperty("password");
    }

    /**
     * Get retry count
     * @return Retry count
     */
    public static int getRetryCount() {
        return getIntProperty("retry.count", 2);
    }

    /**
     * Get retry delay in milliseconds
     * @return Retry delay
     */
    public static int getRetryDelay() {
        return getIntProperty("retry.delay", 1000);
    }

    /**
     * Get script timeout
     * @return Timeout in seconds
     */
    public static int getScriptTimeout() {
        return getIntProperty("script.timeout", 30);
    }
}

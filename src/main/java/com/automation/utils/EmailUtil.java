package com.automation.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.activation.*;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.*;

/**
 * Email Utility
 * Sends test reports via email with zip attachment
 */
public class EmailUtil {
    private static final Logger logger = LoggerFactory.getLogger(EmailUtil.class);
    
    // Email configuration from config.properties
    private static final String SMTP_HOST = ConfigReader.getProperty("email.smtp.host", "smtp.gmail.com");
    private static final String SMTP_PORT = ConfigReader.getProperty("email.smtp.port", "587");
    private static final String EMAIL_USERNAME = ConfigReader.getProperty("email.username");
    private static final String EMAIL_PASSWORD = ConfigReader.getProperty("email.password");
    private static final String EMAIL_FROM = ConfigReader.getProperty("email.from", EMAIL_USERNAME);
    private static final String EMAIL_TO = ConfigReader.getProperty("email.to");
    private static final String EMAIL_SUBJECT = ConfigReader.getProperty("email.subject", "Automation Test Report");
    private static final String EMAIL_BODY = ConfigReader.getProperty("email.body", "Please find the attached automation test report.");
    private static final boolean EMAIL_ENABLED = ConfigReader.getBooleanProperty("email.enabled", false);
    
    private static final String REPORT_PATH = ConfigReader.getProperty("extent.report.path", "test-output/extent-reports/ExtentReport.html");
    private static final String SCREENSHOT_PATH = ConfigReader.getProperty("screenshot.path", "test-output/screenshots");

    /**
     * Create zip file of test reports
     * @return Path to the created zip file
     */
    public static String createReportZip() {
        logger.info("Creating zip file of test reports...");
        
        try {
            String zipFileName = "test-report-" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date()) + ".zip";
            String zipFilePath = "test-output/" + zipFileName;
            
            // Create zip file
            try (FileOutputStream fos = new FileOutputStream(zipFilePath);
                 ZipOutputStream zos = new ZipOutputStream(fos)) {
                
                // Add Extent Report HTML file
                File reportFile = new File(REPORT_PATH);
                if (reportFile.exists() && reportFile.length() > 0) {
                    addFileToZip(zos, reportFile, "report/");
                    logger.info("Added Extent Report to zip: {}", reportFile.getName());
                } else {
                    logger.warn("Extent Report file not found or empty: {}", REPORT_PATH);
                }
                
                // Add screenshots folder (only PNG files, limit to 10 most recent)
                File screenshotDir = new File(SCREENSHOT_PATH);
                if (screenshotDir.exists() && screenshotDir.isDirectory()) {
                    addScreenshotsToZip(zos, screenshotDir, "screenshots/");
                    logger.info("Added screenshots to zip");
                } else {
                    logger.warn("Screenshots directory not found: {}", SCREENSHOT_PATH);
                }
            }
            
            // Verify zip file was created and is not too large
            File zipFile = new File(zipFilePath);
            if (zipFile.exists()) {
                long zipSize = zipFile.length();
                long maxSize = 50 * 1024 * 1024; // 50 MB limit
                
                if (zipSize > maxSize) {
                    logger.warn("Zip file is too large ({} MB), deleting...", zipSize / (1024 * 1024));
                    zipFile.delete();
                    return null;
                }
                
                logger.info("Zip file created successfully: {} ({} KB)", zipFilePath, zipSize / 1024);
                return zipFilePath;
            }
            
            return null;
            
        } catch (Exception e) {
            logger.error("Failed to create zip file: {}", e.getMessage());
            return null;
        }
    }
    
    /**
     * Add screenshots to zip (limited to most recent files)
     */
    private static void addScreenshotsToZip(ZipOutputStream zos, File screenshotDir, String folderPath) throws IOException {
        File[] files = screenshotDir.listFiles();
        if (files == null || files.length == 0) {
            return;
        }
        
        // Filter only PNG files and sort by last modified (most recent first)
        List<File> pngFiles = new ArrayList<>();
        for (File file : files) {
            if (file.isFile() && file.getName().toLowerCase().endsWith(".png")) {
                pngFiles.add(file);
            }
        }
        
        // Sort by last modified (most recent first)
        pngFiles.sort((f1, f2) -> Long.compare(f2.lastModified(), f1.lastModified()));
        
        // Limit to 10 most recent screenshots
        int maxFiles = Math.min(pngFiles.size(), 10);
        logger.info("Adding {} most recent screenshots to zip", maxFiles);
        
        for (int i = 0; i < maxFiles; i++) {
            File file = pngFiles.get(i);
            addFileToZip(zos, file, folderPath);
        }
    }

    /**
     * Add file to zip
     */
    private static void addFileToZip(ZipOutputStream zos, File file, String folderName) throws IOException {
        String zipEntryName = folderName + file.getName();
        ZipEntry zipEntry = new ZipEntry(zipEntryName);
        zos.putNextEntry(zipEntry);
        
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                zos.write(buffer, 0, length);
            }
        }
        
        zos.closeEntry();
    }

    /**
     * Add folder to zip recursively (with safety checks)
     */
    private static void addFolderToZip(ZipOutputStream zos, File folder, String folderPath) throws IOException {
        File[] files = folder.listFiles();
        if (files == null || files.length == 0) {
            return;
        }
        
        // Limit to prevent infinite loops and large files
        int maxFiles = 50;
        int fileCount = 0;
        
        for (File file : files) {
            if (fileCount >= maxFiles) {
                logger.warn("Reached maximum file limit ({}) for folder: {}", maxFiles, folderPath);
                break;
            }
            
            // Skip directories and non-files
            if (file.isDirectory() || !file.isFile()) {
                continue;
            }
            
            // Skip files larger than 10 MB
            if (file.length() > 10 * 1024 * 1024) {
                logger.warn("Skipping large file ({} MB): {}", file.length() / (1024 * 1024), file.getName());
                continue;
            }
            
            // Skip zip files to avoid recursion
            if (file.getName().toLowerCase().endsWith(".zip")) {
                continue;
            }
            
            addFileToZip(zos, file, folderPath);
            fileCount++;
        }
    }

    /**
     * Send email with report attachment
     * @param zipFilePath Path to the zip file
     * @return true if email sent successfully
     */
    public static boolean sendEmailWithReport(String zipFilePath) {
        if (!EMAIL_ENABLED) {
            logger.info("Email sending is disabled. Set email.enabled=true in config.properties to enable.");
            return false;
        }
        
        if (EMAIL_USERNAME == null || EMAIL_PASSWORD == null || EMAIL_TO == null) {
            logger.error("Email configuration is incomplete. Please set email.username, email.password, and email.to in config.properties");
            return false;
        }
        
        logger.info("Sending email with report...");
        
        try {
            // Create zip file
            if (zipFilePath == null || zipFilePath.isEmpty()) {
                zipFilePath = createReportZip();
            }
            
            if (zipFilePath == null || !new File(zipFilePath).exists()) {
                logger.error("Zip file not found: {}", zipFilePath);
                return false;
            }
            
            // Setup mail properties
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", SMTP_HOST);
            props.put("mail.smtp.port", SMTP_PORT);
            props.put("mail.smtp.ssl.trust", SMTP_HOST);
            
            // Create session
            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(EMAIL_USERNAME, EMAIL_PASSWORD);
                }
            });
            
            // Create message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_FROM));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(EMAIL_TO));
            message.setSubject(EMAIL_SUBJECT);
            
            // Create multipart message for attachment
            Multipart multipart = new MimeMultipart();
            
            // Add message body
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(EMAIL_BODY);
            multipart.addBodyPart(messageBodyPart);
            
            // Add attachment
            MimeBodyPart attachmentBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(zipFilePath);
            attachmentBodyPart.setDataHandler(new DataHandler(source));
            attachmentBodyPart.setFileName(new File(zipFilePath).getName());
            multipart.addBodyPart(attachmentBodyPart);
            
            // Set content
            message.setContent(multipart);
            
            // Send message
            Transport.send(message);
            
            logger.info("Email sent successfully to: {}", EMAIL_TO);
            logger.info("Attachment: {}", zipFilePath);
            
            return true;
            
        } catch (Exception e) {
            logger.error("Failed to send email: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Send simple email without attachment
     * @return true if email sent successfully
     */
    public static boolean sendSimpleEmail() {
        if (!EMAIL_ENABLED) {
            logger.info("Email sending is disabled. Set email.enabled=true in config.properties to enable.");
            return false;
        }
        
        if (EMAIL_USERNAME == null || EMAIL_PASSWORD == null || EMAIL_TO == null) {
            logger.error("Email configuration is incomplete. Please set email.username, email.password, and email.to in config.properties");
            return false;
        }
        
        logger.info("Sending simple email...");
        
        try {
            // Setup mail properties
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", SMTP_HOST);
            props.put("mail.smtp.port", SMTP_PORT);
            props.put("mail.smtp.ssl.trust", SMTP_HOST);
            
            // Create session
            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(EMAIL_USERNAME, EMAIL_PASSWORD);
                }
            });
            
            // Create message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_FROM));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(EMAIL_TO));
            message.setSubject(EMAIL_SUBJECT);
            message.setText(EMAIL_BODY);
            
            // Send message
            Transport.send(message);
            
            logger.info("Email sent successfully to: {}", EMAIL_TO);
            return true;
            
        } catch (Exception e) {
            logger.error("Failed to send email: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Send test report email with zip attachment
     * This is the main method to be called after test execution
     */
    public static void sendTestReport() {
        logger.info("=== Sending Test Report Email ===");
        
        try {
            // Create zip file
            String zipFilePath = createReportZip();
            
            if (zipFilePath != null) {
                // Send email with attachment
                boolean sent = sendEmailWithReport(zipFilePath);
                
                if (sent) {
                    logger.info("Test report email sent successfully");
                } else {
                    logger.warn("Failed to send test report email");
                }
            } else {
                logger.error("Failed to create zip file for email attachment");
            }
            
        } catch (Exception e) {
            logger.error("Error sending test report email: {}", e.getMessage());
        }
        
        logger.info("=== Email Sending Completed ===");
    }
}
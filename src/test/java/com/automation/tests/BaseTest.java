package com.automation.tests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import com.automation.drivers.DriverManager;
import com.automation.listeners.TestListeners;
import com.automation.utils.ConfigReader;

@Listeners(TestListeners.class)
public class BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

    @BeforeMethod
    public void setup() {
        logger.info("Setting up test browser session.");
        DriverManager.initDriver();
        String url = ConfigReader.getProperty("url");
        logger.info("Navigating to application URL: {}", url);
        DriverManager.getDriver().get(url);
        logger.info("Application opened successfully.");
    }

    @AfterMethod
    public void teardown() {
        logger.info("Tearing down test browser session.");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.warn("Teardown wait was interrupted.", e);
        }
        DriverManager.quitDriver();
        logger.info("Test browser session teardown completed.");
    }
}
package com.automation.drivers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.automation.utils.ConfigReader;

public class DriverManager {

    private static final Logger logger = LoggerFactory.getLogger(DriverManager.class);
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static void initDriver() {

        ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless=new", "--no-sandbox",
                    "--disable-dev-shm-usage", "--window-size=1920,1080");

        String browser = ConfigReader.getProperty("browser");
        if (browser == null) {
            browser = "firefox";
            logger.warn("Browser name was not found in the configuration. Using Firefox by default.");
        }

        logger.info("Initializing {} browser driver.", browser);
        switch (browser) {
            case "chrome": driver.set(new ChromeDriver(options)); break;
            case "firefox": driver.set(new FirefoxDriver()); break;
            case "edge": driver.set(new EdgeDriver()); break;
            default: driver.set(new ChromeDriver(options)); break;
        }

        driver.get().manage().window().maximize();
        logger.info("{} browser driver initialized and window maximized.", browser);
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {
        WebDriver currentDriver = driver.get();
        if (currentDriver != null) {
            logger.info("Closing browser driver.");
            currentDriver.quit();
            driver.remove();
            logger.info("Browser driver closed.");
        } else {
            logger.warn("No browser driver was available to close.");
        }
    }
}
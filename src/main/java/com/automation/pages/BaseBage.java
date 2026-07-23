package com.automation.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.automation.drivers.DriverManager;

public class BaseBage {

    private static final Logger logger = LoggerFactory.getLogger(BaseBage.class);
    private final WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(10));

    public void click(By locator) {
        logger.info("Clicking element: {}", locator);
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        logger.info("Clicked element: {}", locator);
    }

    public void type(By locator, String txt) {
        logger.info("Entering text into element: {}", locator);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.click();
        element.sendKeys(txt);
        logger.info("Text entered into element: {}", locator);
    }
}
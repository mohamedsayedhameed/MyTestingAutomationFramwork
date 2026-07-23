package com.automation.pages;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginPage extends BaseBage {

    private static final Logger logger = LoggerFactory.getLogger(LoginPage.class);
    private final By usernameInputField = By.id("user-name");
    private final By passwordInputField = By.id("password");
    private final By loginButton = By.id("login-button");
// this is comment for new commit
    public void enterUsername(String username) {
        logger.info("Entering username.");
        type(usernameInputField, username);
    }

    public void enterPassword(String password) {
        logger.info("Entering password.");
        type(passwordInputField, password);
    }

    public void clickLogin() {
        logger.info("Submitting login form.");
        click(loginButton);
    }

    public void login(String username, String password) {
        logger.info("Starting login workflow.");
        enterUsername(username);
        enterPassword(password);
        clickLogin();
        logger.info("Login form submitted.");
    }
}
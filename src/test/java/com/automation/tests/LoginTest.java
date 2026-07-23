package com.automation.tests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import com.automation.pages.LoginPage;
import com.automation.utils.ExcelUtil;

public class LoginTest extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(LoginTest.class);

    
    
    @Test(dataProvider = "excelLoginData", dataProviderClass =  ExcelUtil.class)
    public void loginTest1(String username, String password) {
        logger.info("Starting login test.");
        LoginPage loginPage = new LoginPage();
        loginPage.login(username, password);
        logger.info("Login test completed.");
    }
   
}
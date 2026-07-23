package com.automation.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.automation.utils.ExtentManager;
import com.automation.utils.TakeScreenShotUtil;
import com.aventstack.extentreports.ExtentTest;

public class TestListeners implements ITestListener {

    private static final Logger logger = LoggerFactory.getLogger(TestListeners.class);
    ThreadLocal<ExtentTest> extentTestThread = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("Test started: {}", result.getName());
        ExtentTest extentTest = ExtentManager.getExtent().createTest(result.getName());
        extentTestThread.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("Test passed: {}", result.getName());
        TakeScreenShotUtil.takeScreenShot(result.getName());
        extentTestThread.get().pass("Test Pass");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("Test failed: {}", result.getName(), result.getThrowable());
        TakeScreenShotUtil.takeScreenShot(result.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        logger.info("Test context finished: {}", context.getName());
        ExtentManager.getExtent().flush();
    }
}
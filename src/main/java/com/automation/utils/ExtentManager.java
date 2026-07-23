package com.automation.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExtentManager {

    private static final Logger logger = LoggerFactory.getLogger(ExtentManager.class);
    private static final String REPORT_PATH = "TestOutput/Reports/extent-report.html";

    private static ExtentReports extent;

    public static ExtentReports getExtent() {
        logger.info("Initializing Extent report at {}", REPORT_PATH);

        ExtentSparkReporter reporter = new ExtentSparkReporter(REPORT_PATH);
        extent = new ExtentReports();
        extent.attachReporter(reporter);

        logger.info("Extent report initialized successfully");
        return extent;
    }
}
package com.automation.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.automation.drivers.DriverManager;

public class TakeScreenShotUtil {

    private static final Logger logger = LoggerFactory.getLogger(TakeScreenShotUtil.class);

    public static String takeScreenShot(String testname) {
        logger.info("Capturing screenshot for test: {}", testname);

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        TakesScreenshot ts = (TakesScreenshot) DriverManager.getDriver();
        File src = ts.getScreenshotAs(OutputType.FILE);

        String dirPath = "TestOutput/ScreenShots/";
        String filePath = dirPath + testname + "_" + timestamp + ".png";

        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File des = new File(filePath);

        try {
            Files.copy(src.toPath(), des.toPath(), StandardCopyOption.REPLACE_EXISTING);
            logger.info("Screenshot saved to: {}", des.getAbsolutePath());
        } catch (IOException e) {
            logger.error("Unable to save screenshot for test: {}", testname, e);
        }

        return des.getAbsolutePath();
    }
}
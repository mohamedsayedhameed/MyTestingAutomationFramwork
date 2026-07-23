package com.automation.utils;

//import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigReader {

    private static final Logger logger = LoggerFactory.getLogger(ConfigReader.class);
    private static final Properties prop = new Properties();

    static {
        String env = System.getProperty("environment", "qa");
        String fileName ="config-" + env + ".features";
        //String path = "src/main/resources/config-" + env + ".features";

        logger.info("Loading {} environment configuration from {}.", env, fileName);
        try (
            //FileInputStream fis = new FileInputStream(path)
            InputStream input = ConfigReader.class
            .getClassLoader()
            .getResourceAsStream(fileName)
        ) {

            if (input == null) {
                throw new IllegalStateException("Configuration file not found: " + fileName);
            }
            prop.load(input);
            logger.info("Configuration loaded successfully for {} environment.", env);
        } catch (IOException e) {
            logger.error("Unable to load configuration from {}.", fileName, e);
        }
    }

    public static String getProperty(String key) {
        String value = prop.getProperty(key);
        if (value == null) {
            logger.warn("Configuration key '{}' was not found.", key);
        }
        return value;
    }
}
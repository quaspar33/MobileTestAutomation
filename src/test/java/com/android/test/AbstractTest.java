package com.android.test;

import io.appium.java_client.android.AndroidDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

public abstract class AbstractTest {
    public static AndroidDriver driver;

    @BeforeClass
    public void beforeClass() {
        driver = BaseTest.driver;
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            try {
                throw new RuntimeException(String.format("Test %s zakończony niepowodzeniem.", result.getMethod().getMethodName()));
            } finally {
                if (driver != null) {
                    driver.quit();
                }
            }
        }
    }
 }

package com.android.test;

import io.appium.java_client.android.AndroidDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

public abstract class AbstractTest {
    public static AndroidDriver driver;

    @BeforeClass
    public void beforeClass() {
        driver = BaseTest.driver;
    }
}

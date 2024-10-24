package com.android.test.tests;

import com.android.test.BaseTest;
import com.android.test.pages.RegisterPage;
import io.appium.java_client.android.AndroidDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDateTime;

public class RegisterTest {
    private RegisterPage registerPage;
    private AndroidDriver driver;

    @BeforeClass
    public void beforeClass() {
        driver = BaseTest.driver;
        registerPage = new RegisterPage(driver);
    }

    @Test(groups = "register")
    public void registerTest() {
        BaseTest.setRegisterTime(LocalDateTime.now());
        registerPage.clickRegisterButton();
        registerPage.enterPhoneNumber();
        registerPage.enterPostalCode();
        registerPage.clickAgreementCheckbox();
        registerPage.clickCreateAccountButton();
    }
}

package com.android.test.tests;

import com.android.test.BaseTest;
import com.android.test.pages.LoginPage;
import io.appium.java_client.android.AndroidDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LoginTest {
    private AndroidDriver driver;
    private LoginPage loginPage;

    @BeforeClass
    public void beforeClass() {
        driver = BaseTest.driver;
        loginPage = new LoginPage(driver);
    }

    @Test(groups = "login", dependsOnGroups = "register")
    public void test() {
        loginPage.enterPhoneNumber();
        loginPage.enterPassword(BaseTest.getRegisterTime());
        loginPage.clickLoginButton();
    }
}

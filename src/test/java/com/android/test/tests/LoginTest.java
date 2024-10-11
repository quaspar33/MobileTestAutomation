package com.android.test.tests;

import com.android.test.pages.LoginPage;
import io.appium.java_client.android.AndroidDriver;


public class LoginTest {
    private LoginPage loginPage;

    public LoginTest(AndroidDriver driver) {
        this.loginPage = new LoginPage(driver);
    }

    public void testLogin() {
        loginPage.enterPhoneNumber();
        loginPage.enterPassword();
        loginPage.clickLoginButton();
    }
}

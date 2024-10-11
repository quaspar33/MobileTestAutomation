package com.android.test.tests;

import com.android.test.pages.RegisterPage;
import io.appium.java_client.android.AndroidDriver;

public class RegisterTest{
    private RegisterPage registerPage;

    public RegisterTest(AndroidDriver driver) {
        this.registerPage = new RegisterPage(driver);
    }

    public void testRegister() {
        registerPage.clickRegisterButton();
        registerPage.enterPhoneNumber();
        registerPage.enterPostalCode();
        registerPage.clickAgreementCheckbox();
        registerPage.clickCreateAccountButton();
    }
}

package com.android.test.tests;

import com.android.test.BaseTest;
import com.android.test.pages.RegisterPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;

public class RegisterTest extends BaseTest {
    private RegisterPage registerPage;

    @BeforeMethod
    public void setupApp() throws MalformedURLException {
        super.setupApp();
        registerPage = new RegisterPage(driver);
    }

    @Test
    public void testClickRegisterButton() {
        registerPage.clickRegisterButton();
        registerPage.enterPhoneNumber();
        registerPage.enterPostalCode();
        registerPage.clickAgreementCheckbox();
        registerPage.clickCreateAccountButton();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

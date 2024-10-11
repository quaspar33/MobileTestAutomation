package com.android.test.tests;

import com.android.test.AppSetup;
import com.android.test.pages.LoginPage;
import com.android.test.pages.RegisterPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;

public class PartnerProcessTest extends AppSetup {
    private RegisterPage registerPage;
    private LoginPage loginPage;

    @BeforeMethod
    @Override
    public void setupApp() throws MalformedURLException {
        super.setupApp();
        registerPage = new RegisterPage(driver);
        loginPage = new LoginPage(driver);
    }

    @Test
    public void testRegister() {
        registerPage.clickRegisterButton();
        registerPage.enterPhoneNumber();
        registerPage.enterPostalCode();
        registerPage.clickAgreementCheckbox();
        registerPage.clickCreateAccountButton();
        registerPage.clickComebackButton();
    }

    @Test(dependsOnMethods = {"testRegister"})
    public void testLogin() {
        loginPage.enterPhoneNumber();
        loginPage.enterPassword();
        loginPage.clickLoginButton();
    }
}

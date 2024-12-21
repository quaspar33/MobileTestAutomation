package com.android.test.tests;

import com.android.test.AbstractTest;
import com.android.test.pages.RegisterPage;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class RegisterTest extends AbstractTest {
    private RegisterPage registerPage;

    @BeforeClass
    @Override
    public void beforeClass() {
        super.beforeClass();
        registerPage = new RegisterPage(driver);
    }

    @Test
    public void registerTest() {
        registerPage.clickRegisterButton();
        registerPage.enterPhoneNumber();
        registerPage.enterPostalCode();
        registerPage.clickAgreementCheckbox();
        registerPage.clickCreateAccountButton();
    }
}

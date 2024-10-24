package com.android.test.tests;

import com.android.test.AbstractTest;
import com.android.test.BaseTest;
import com.android.test.pages.LoginPage;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LoginTest extends AbstractTest {
    private LoginPage loginPage;

    @BeforeClass
    @Override
    public void beforeClass() {
        super.beforeClass();
        loginPage = new LoginPage(driver);
    }

    @Test(groups = "login", dependsOnGroups = "register")
    public void loginTest() {
        loginPage.enterPhoneNumber();
        loginPage.enterPassword(BaseTest.getRegisterTime());
        loginPage.clickLoginButton();
    }
}

package com.android.test.tests;

import com.android.test.BaseTest;
import com.android.test.pages.LoginPage;
import com.android.test.pages.QuestionnaireFirstPage;
import com.android.test.pages.RegisterPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.time.LocalDateTime;

public class PartnerProcessTest extends BaseTest {
    private RegisterPage registerPage;
    private LoginPage loginPage;
    private QuestionnaireFirstPage questionnaireFirstPage;

    @BeforeMethod
    @Override
    public void setupApp() throws MalformedURLException {
        super.setupApp();
        registerPage = new RegisterPage(driver);
        loginPage = new LoginPage(driver);
        questionnaireFirstPage = new QuestionnaireFirstPage(driver);
    }

    @Test
    public void test() {
        registerPage.clickRegisterButton();
        registerPage.enterPhoneNumber();
        registerPage.enterPostalCode();
        registerPage.clickAgreementCheckbox();
        registerPage.clickCreateAccountButton();
        setRegisterTime(LocalDateTime.now());
        loginPage.enterPhoneNumber();
        loginPage.enterPassword(getRegisterTime());
        loginPage.clickLoginButton();
        questionnaireFirstPage.clickFillQuestionnaire();
        questionnaireFirstPage.enterBirthDate();
        questionnaireFirstPage.enterCountry();
        questionnaireFirstPage.enterPesel();
        questionnaireFirstPage.enterName();
        questionnaireFirstPage.enterSurname();
    }
}

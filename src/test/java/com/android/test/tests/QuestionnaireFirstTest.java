package com.android.test.tests;

import com.android.test.BaseTest;
import com.android.test.pages.QuestionnaireFirstPage;
import io.appium.java_client.android.AndroidDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class QuestionnaireFirstTest {
    private AndroidDriver driver;
    private QuestionnaireFirstPage questionnaireFirstPage;

    @BeforeClass
    public void beforeClass() {
        driver = BaseTest.driver;
        questionnaireFirstPage = new QuestionnaireFirstPage(driver);
    }

    @Test(groups = "questionnaireFirst", dependsOnGroups = "login")
    public void questionnaireFirstTest() {
        questionnaireFirstPage.clickFillQuestionnaire();
        questionnaireFirstPage.enterBirthDate();
        questionnaireFirstPage.enterCountry();
        questionnaireFirstPage.enterPesel();
        questionnaireFirstPage.enterName();
        questionnaireFirstPage.enterSurname();
        questionnaireFirstPage.enterEmail();
        questionnaireFirstPage.enterPhoneNumber();
        questionnaireFirstPage.enterTaxOffice();
        questionnaireFirstPage.enterBankNumber();
        questionnaireFirstPage.enterAddress();
        questionnaireFirstPage.setYesCheckbox();
        questionnaireFirstPage.enterNextPage();
    }
}

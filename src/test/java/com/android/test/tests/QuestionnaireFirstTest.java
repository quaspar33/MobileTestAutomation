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
    public void test() {
        questionnaireFirstPage.clickFillQuestionnaire();
        questionnaireFirstPage.enterBirthDate();
        questionnaireFirstPage.enterCountry();
        questionnaireFirstPage.enterPesel();
        questionnaireFirstPage.enterName();
        questionnaireFirstPage.enterSurname();
    }
}

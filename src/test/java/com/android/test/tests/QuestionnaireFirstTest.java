package com.android.test.tests;

import com.android.test.AbstractTest;
import com.android.test.pages.QuestionnaireFirstPage;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class QuestionnaireFirstTest extends AbstractTest {
    private QuestionnaireFirstPage questionnaireFirstPage;

    @BeforeClass
    @Override
    public void beforeClass() {
        super.beforeClass();
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
        questionnaireFirstPage.enterIban();
        questionnaireFirstPage.enterAddress();
        questionnaireFirstPage.setYesCheckbox();
        questionnaireFirstPage.enterNextPage();
    }
}

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

    @Test
    public void questionnaireFirstTest() {
        questionnaireFirstPage.clickQuestionnaire();
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
        questionnaireFirstPage.setCheckbox();
        questionnaireFirstPage.enterNextPage();
    }
}

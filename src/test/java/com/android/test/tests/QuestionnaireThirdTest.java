package com.android.test.tests;

import com.android.test.AbstractTest;
import com.android.test.pages.QuestionnaireThirdPage;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class QuestionnaireThirdTest extends AbstractTest {
    private QuestionnaireThirdPage questionnaireThirdPage;

    @BeforeClass
    @Override
    public void beforeClass() {
        super.beforeClass();
        questionnaireThirdPage = new QuestionnaireThirdPage(driver);
    }

    @Test
    public void questionnaireThirdTest() {
        questionnaireThirdPage.enterConfirmation();
        questionnaireThirdPage.enterEndQuestionnaire();
    }
}

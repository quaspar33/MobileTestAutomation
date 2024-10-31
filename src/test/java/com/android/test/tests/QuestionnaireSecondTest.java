package com.android.test.tests;

import com.android.test.AbstractTest;
import com.android.test.pages.QuestionnaireSecondPage;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class QuestionnaireSecondTest extends AbstractTest {
    private QuestionnaireSecondPage questionnaireSecondPage;

    @BeforeClass
    @Override
    public void beforeClass() {
        super.beforeClass();
        questionnaireSecondPage = new QuestionnaireSecondPage(driver);
    }

    @Test(groups = "questionnaireSecond", dependsOnGroups = "questionnaireFirst")
    public void questionnaireSecondTest() {
        questionnaireSecondPage.enterUnemployedCheckbox();
        questionnaireSecondPage.enterNextPage();
    }
}

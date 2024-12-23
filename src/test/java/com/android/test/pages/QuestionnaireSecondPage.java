package com.android.test.pages;

import com.android.test.AbstractPage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class QuestionnaireSecondPage extends AbstractPage {
    public QuestionnaireSecondPage(AndroidDriver driver) {
        super(driver);
        System.out.println("Rozpoczynam wypełnianie drugiej strony kwestionariusza!");
    }

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Bezrobotny/a\")")
    private WebElement unemployedText;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Dalej\")")
    private WebElement nextPage;

    public void enterUnemployedCheckbox() {
        wait.until(ExpectedConditions.visibilityOf(unemployedText)).click();
    }

    public void enterNextPage() {
        implicitWait(2000);
        nextPage.click();
    }
}

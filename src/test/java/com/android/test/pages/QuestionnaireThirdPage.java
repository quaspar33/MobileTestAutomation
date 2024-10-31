package com.android.test.pages;

import com.android.test.AbstractPage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class QuestionnaireThirdPage extends AbstractPage {
    public QuestionnaireThirdPage(AndroidDriver driver) {
        super(driver);
    }

    @AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.view.ViewGroup\").instance(37)")
    private WebElement confirmation;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Zakończ\")")
    private WebElement endQuestionnaire;

    public void enterConfirmation() {
        System.out.println("Rozpoczynam wypełnianie trzeciej strony kwestionariusza!");
        wait.until(ExpectedConditions.visibilityOf(confirmation));
        confirmation.click();
    }

    public void enterEndQuestionnaire() {
        endQuestionnaire.click();
    }
}

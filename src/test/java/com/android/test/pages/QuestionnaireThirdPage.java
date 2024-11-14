package com.android.test.pages;

import com.android.test.AbstractPage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class QuestionnaireThirdPage extends AbstractPage {
    public QuestionnaireThirdPage(AndroidDriver driver) {
        super(driver);
        System.out.println("Rozpoczynam wypełnianie trzeciej strony kwestionariusza!");
    }

    @AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.view.ViewGroup\").instance(34)")
    private WebElement confirmation;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Zakończ\")")
    private WebElement endQuestionnaire;

    public void enterConfirmation() {
        wait.until(ExpectedConditions.visibilityOf(confirmation)).click();
    }

    public void enterEndQuestionnaire() {
        implicitWait(2000);
        endQuestionnaire.click();
    }
}

package com.android.test.pages;

import com.android.test.AbstractPage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class QuestionnaireSecondPage extends AbstractPage {
    public QuestionnaireSecondPage(AndroidDriver driver) {
        super(driver);
        System.out.println("Rozpoczynam wypełnianie drugiej strony kwestionariusza!");
    }

    @AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.view.ViewGroup\").instance(38)")
    private WebElement unemployedCheckbox;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Dalej\")")
    private WebElement nextPage;

    public void enterUnemployedCheckbox() {
        wait.until(ExpectedConditions.visibilityOf(unemployedCheckbox)).click();
    }

    public void enterNextPage() {
        implicitWait(2000);
        nextPage.click();
    }
}

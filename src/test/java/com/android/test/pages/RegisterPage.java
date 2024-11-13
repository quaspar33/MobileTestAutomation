package com.android.test.pages;

import com.android.test.AbstractPage;
import com.android.test.JsonHandler;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.format.DateTimeFormatter;

public class RegisterPage extends AbstractPage {
    private JsonHandler jsonHandler;

    public RegisterPage(AndroidDriver driver) {
        super(driver);
        jsonHandler = new JsonHandler("register.json");
        System.out.println("Rozpoczynam test rejestracji!");
    }

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Załóż konto\")")
    private WebElement registerButton;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Numer telefonu komórkowego\")")
    private WebElement phoneNumberField;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Kod pocztowy\")")
    private WebElement postalCodeField;

    @AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.view.ViewGroup\").instance(9)")
    private WebElement agreementCheckbox;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Załóż konto\").instance(1)")
    private WebElement createAccountButton;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Wróć\")")
    private WebElement comebackButton;

    public void clickRegisterButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOf(registerButton));
        registerButton.click();
    }

    public void enterPhoneNumber() {
        wait.until(ExpectedConditions.visibilityOf(phoneNumberField)).sendKeys(jsonHandler.getStrFromJson("login"));
    }

    public void enterPostalCode() {
        postalCodeField.sendKeys(jsonHandler.getStrFromJson("postalCode"));
    }

    public void clickAgreementCheckbox() {
        agreementCheckbox.click();
    }

    public void clickCreateAccountButton() {
        createAccountButton.click();
    }
}

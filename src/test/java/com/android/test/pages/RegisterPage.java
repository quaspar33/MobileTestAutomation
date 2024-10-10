package com.android.test.pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegisterPage {
    private AndroidDriver driver;

    public RegisterPage(AndroidDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
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
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(registerButton));
        registerButton.click();
    }

    public void enterPhoneNumber() {
        phoneNumberField.clear();
        phoneNumberField.sendKeys("503168221");
    }

    public void enterPostalCode() {
        postalCodeField.clear();
        postalCodeField.sendKeys("05-825");
    }

    public void clickAgreementCheckbox() {
        agreementCheckbox.click();
    }

    public void clickCreateAccountButton() {
        createAccountButton.click();
    }
}

package com.android.test.pages;

import com.android.test.Database;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicReference;

public class LoginPage {
    private AndroidDriver driver;
    private Database database;
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public LoginPage(AndroidDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        database = new Database();
    }

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Numer telefonu\")")
    private WebElement phoneNumberField;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Hasło\")")
    private WebElement passwordField;

    @AndroidFindBy(uiAutomator = "new UiSelector().description(\"Zaloguj się\")")
    private WebElement loginButton;

    public void enterPhoneNumber() {
        System.out.println("Rozpoczynam test logowania");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(phoneNumberField));
        phoneNumberField.sendKeys("503168221");
    }

    public void enterPassword(LocalDateTime registerTime) {
        database.connect();
        AtomicReference<String> queryForTempPassword = new AtomicReference<>("");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(driver -> {
            queryForTempPassword.set(database.queryForTempPassword("SELECT * FROM tikrow_qa.notificationsSmsHistory ORDER BY sendDate DESC LIMIT 1"));
            return LocalDateTime.parse(queryForTempPassword.get().split(";")[1], dateFormatter).isAfter(registerTime);
        });

        String[] parts = queryForTempPassword.get().split(";");

        String password = parts[0].replace("Czesc! Twoje haslo do Tikrow to: ", "");

        database.disconnect();
        passwordField.sendKeys(password);
    }

    public void clickLoginButton() {
        loginButton.click();
    }
}

package com.android.test.pages;

import com.android.test.AbstractPage;
import com.android.test.JsonHandler;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class LoginPage extends AbstractPage {
    private JsonHandler jsonHandler;

    public LoginPage(AndroidDriver driver) {
        super(driver);
        jsonHandler = new JsonHandler("login.json");
        System.out.println("Rozpoczynam test logowania!");
    }

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Numer telefonu\")")
    private WebElement phoneNumberField;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Hasło\")")
    private WebElement passwordField;

    @AndroidFindBy(uiAutomator = "new UiSelector().description(\"Zaloguj się\")")
    private WebElement loginButton;

    public void enterPhoneNumber() {
        new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions.visibilityOf(phoneNumberField)).sendKeys(jsonHandler.getStrFromJson("phoneNumber"));
    }

    public void enterPassword(LocalDateTime registerTime) {
        database.connect();
        AtomicReference<String> atomicPassword = new AtomicReference<>("");
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(driver -> {
            List<String> recentSms = database.queryForTempPassword("SELECT * FROM tikrow_dev.notificationsSmsHistory ORDER BY sendDate DESC LIMIT 5");

            for (String sms : recentSms) {
                String[] parts = sms.split(";");
                if (parts.length < 3) continue;

                LocalDateTime smsDate = LocalDateTime.parse(parts[1], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                String phoneNumber = "48" + jsonHandler.getStrFromJson("phoneNumber");

                if (registerTime.isBefore(smsDate) && parts[2].equals(phoneNumber)) {
                    String password = parts[0].replace("[DEV] Czesc! Twoje haslo do Tikrow to: ", "");
                    atomicPassword.set(password);
                    return true;
                }
            }
            return false;
        });
        System.out.println("Udało się pobrać hasło!");
        passwordField.sendKeys(atomicPassword.get());
        database.disconnect();
    }

    public void clickLoginButton() {
        loginButton.click();
    }
}

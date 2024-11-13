package com.android.test.pages;

import com.android.test.AbstractPage;
import com.android.test.Database;
import com.android.test.JsonHandler;
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
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
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
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOf(phoneNumberField)).sendKeys(jsonHandler.getStrFromJson("phoneNumber"));
    }

    public void enterPassword(LocalDateTime registerTime) {
        database.connect();

        AtomicReference<List<String>> queryForTempPassword = new AtomicReference<>(new ArrayList<>());
        AtomicReference<String> passwordRef = new AtomicReference<>("");
        System.out.println("Czas rozpoczęcia testu rejestracji: " + registerTime.toString());

        boolean passwordFound = false;
        int maxAttempts = 20;
        int attempts = 0;

        while (!passwordFound && attempts < maxAttempts) {
            queryForTempPassword.set(database.queryForTempPassword("SELECT * FROM tikrow_dev.notificationsSmsHistory ORDER BY sendDate DESC LIMIT 5"));
            List<String> tempList = queryForTempPassword.get();

            for (String s : tempList) {
                String[] tempParts = s.split(";");
                System.out.println("Wynik zapytania o hasło: " + tempParts[0]);
                if (tempParts.length < 3) continue;

                String tempDate = tempParts[1];
                String tempNumber = tempParts[2];

                try {
                    LocalDateTime parsedDate = LocalDateTime.parse(tempDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    if (parsedDate.isAfter(registerTime) && tempNumber.equals("48".concat(jsonHandler.getStrFromJson("phoneNumber")))) {
                        passwordRef.set(tempParts[0].replace("[DEV] Czesc! Twoje haslo do Tikrow to: ", ""));
                        System.out.println("Znaleziono dopasowanie!");
                        passwordFound = true;
                        break;
                    }
                } catch (DateTimeParseException e) {
                    e.printStackTrace();
                }
            }

            if (!passwordFound) {
                System.out.println("Nie udało się znaleźć dopasowania. Próba: " + (attempts + 1));
                attempts++;

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }

        if (passwordFound) {
            String password = passwordRef.get();
            passwordField.sendKeys(password);
        } else {
            System.out.println("Przekroczono limit prób. Nie znaleziono hasła.");
        }

        database.disconnect();
    }


    public void clickLoginButton() {
        loginButton.click();
    }
}

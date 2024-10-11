package com.android.test.pages;

import com.android.test.BaseTest;
import com.android.test.Database;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class LoginPage {
    private AndroidDriver driver;
    private Database database;

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
        phoneNumberField.clear();
        phoneNumberField.sendKeys("503168221");
    }

    public void enterPassword() {
        passwordField.clear();
        database.connect();
        String password = null;
        boolean conditionsMet = false;
        ResultSet output;
        int count = 0;

        while (!conditionsMet || count == 9) {
            output = database.queryForTempPassword("SELECT * FROM tikrow_qa.notificationsSmsHistory ORDER BY sendDate DESC LIMIT 10");
            try {
                String number = output.getString("number");
                Timestamp sendDate = output.getTimestamp("sendDate");
                String text = output.getString("text");

                if (number.equals("48503168221") && !sendDate.before(BaseTest.getStartTestTime())) {
                    conditionsMet = true;
                    password = text.replace("Czesc! Twoje haslo do Tikrow to: ", "");
                } else {
                    count++;
                }

            } catch (SQLException e) {
                e.printStackTrace();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        passwordField.sendKeys(password);
    }


    public void clickLoginButton() {
        loginButton.click();
    }
}

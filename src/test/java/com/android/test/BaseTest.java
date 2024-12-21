package com.android.test;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.MutableCapabilities;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Random;

public class BaseTest {
    public static AndroidDriver driver;
    private Database database;
    private JsonHandler jsonHandlerDatabase;
    private static LocalDateTime registerTime;

    @BeforeSuite
    public void setupApp() throws MalformedURLException {
        databaseSetup();
        MutableCapabilities capabilities = new UiAutomator2Options();
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), capabilities);
    }

    @AfterSuite
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void databaseSetup() {
        jsonHandlerDatabase = new JsonHandler("base.json");
        database = new Database();
        String login = jsonHandlerDatabase.getStrFromJson("login");
        database.connect();

        String checkDatabaseLogin = database.queryForLogin("select * from tikrow_dev.user where login like '" + login + "'");

        if (!checkDatabaseLogin.isEmpty()) {
            System.out.println("Znaleziono dopasowanie: " + checkDatabaseLogin);
            String newLogin = login + randomLoginEnd(3, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789");
            int rowsAffected = database.executeUpdate("update tikrow_dev.user set login = '" + newLogin + "' where login like '" + login + "'");
            System.out.println("Zmodyfikowano wiersze w liczbie: " + rowsAffected);
        } else {
            System.out.println("Brak loginu w bazie");
        }

        database.disconnect();
    }

    private String randomLoginEnd(int length, String chars) {
        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(chars.length());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
    }

    public static void setRegisterTime(LocalDateTime registerTime) {
        BaseTest.registerTime = registerTime;
    }

    public static LocalDateTime getRegisterTime() {
        return registerTime;
    }
}

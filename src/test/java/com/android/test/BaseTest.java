package com.android.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Random;

public class BaseTest {
    protected AndroidDriver driver;
    protected Database database;
    protected static Timestamp startTestTime;

    @BeforeMethod
    public void setupApp() throws MalformedURLException {
        databaseSetup();

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "15.0");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator-5554");
        capabilities.setCapability("autoGrantPermissions", true);


        String patch = "/Users/kacperziebacz/Desktop/AutomationTests/app_versions/android_qa/application-b9fe7b6e-d154-4eaa-8fef-f6c168315f27.apk";
        capabilities.setCapability(MobileCapabilityType.APP, patch);
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), capabilities);

        String filePath = "/Users/kacperziebacz/Desktop/AutomationTests/patch.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {
            writer.write(patch);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterSuite
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    protected void databaseSetup() {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(new File("src/test/java/com/android/test/login.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert jsonNode != null;
        String login = jsonNode.get("login").asText();
        database = new Database();
        database.connect();

        String checkDatabaseLogin = database.queryForLogin("select * from tikrow_qa.user where login like '" + login + "'");

        System.out.println(checkDatabaseLogin);

        if (checkDatabaseLogin != null && !checkDatabaseLogin.isEmpty()) {
            String newLogin = login + generateRandomString();
            int rowsAffected = database.executeUpdate("update tikrow_qa.user set login = '" + newLogin + "' where login like '" + login + "'");
            System.out.println("Zmodyfikowano wiersze w liczbie: " + rowsAffected);
        }

        database.disconnect();
    }

    protected String generateRandomString() {
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        StringBuilder result = new StringBuilder(3);

        for (int i = 0; i < 3; i++) {
            int index = random.nextInt(letters.length());
            result.append(letters.charAt(index));
        }

        return result.toString();
    }

    public static Timestamp getStartTestTime() {
        return startTestTime;
    }
}

package com.android.test;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeMethod;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;

public class BaseTest {
    public AndroidDriver driver;
    private Database database;
    private JsonHandler jsonHandler;
    private LocalDateTime registerTime;
    private Services services;

    @BeforeMethod
    public void setupApp() throws MalformedURLException {
        databaseSetup();

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "15.0");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator-5554");
        capabilities.setCapability("autoGrantPermissions", true);
        capabilities.setCapability(MobileCapabilityType.APP, System.getProperty("user.home") + "/Desktop/AutomationTests/app_versions/android_qa/application-b9fe7b6e-d154-4eaa-8fef-f6c168315f27.apk");
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), capabilities);
    }

//    @AfterMethod
//    public void tearDown() {
//        if (driver != null) {
//            driver.quit();
//        }
//    }

    private void databaseSetup() {
        jsonHandler = new JsonHandler("src/test/java/com/android/test/login.json");
        database = new Database();
        services = new Services();
        String login = jsonHandler.getStrFromJson("login");
        database.connect();

        String checkDatabaseLogin = database.queryForLogin("select * from tikrow_qa.user where login like '" + login + "'");

        System.out.println(checkDatabaseLogin);

        if (checkDatabaseLogin != null && !checkDatabaseLogin.isEmpty()) {
            String newLogin = login + services.generateRandomString(3, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789");
            int rowsAffected = database.executeUpdate("update tikrow_qa.user set login = '" + newLogin + "' where login like '" + login + "'");
            System.out.println("Zmodyfikowano wiersze w liczbie: " + rowsAffected);
        }

        database.disconnect();
    }

    public void setRegisterTime(LocalDateTime registerTime) {
        this.registerTime = registerTime;
    }

    public LocalDateTime getRegisterTime() {
        return registerTime;
    }
}

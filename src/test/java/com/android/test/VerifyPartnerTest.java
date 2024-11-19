package com.android.test;

import io.appium.java_client.gecko.GeckoDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class VerifyPartnerTest {
    WebDriver driver;
    WebDriverWait wait;
    JsonHandler jsonHandler;

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.gecko.driver", "src/test/java/com/android/test/resources/geckodriver");
        jsonHandler = new JsonHandler("verify_partner.json");
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get(jsonHandler.getStrFromJson("url"));
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        System.out.println("Rozpoczynam test zaakceptowania kwestionariusza!");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void verifyPartner() {
        driver.findElement(By.name("login")).sendKeys(jsonHandler.getStrFromJson("login"));
        driver.findElement(By.name("password")).sendKeys(jsonHandler.getStrFromJson("password"));
        driver.findElement(By.name("send")).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("menu-136")))).click();
        driver.findElement(By.name("login")).sendKeys(jsonHandler.getStrFromJson("filterLogin"));
        new Select(driver.findElement(By.name("processStatus"))).selectByValue("4");
        driver.findElement(By.name("search")).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("/html/body/div[4]/div/div[5]/table/tbody/tr[1]/td[18]/a[1]/span")))).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("/html/body/div[4]/div/ul/li[2]/a")))).click();
        new Select(wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.name("accept-last-data"))))).selectByValue("1");
        driver.findElement(By.name("Zapisz")).click();
    }
}

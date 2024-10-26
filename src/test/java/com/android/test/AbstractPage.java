package com.android.test;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class AbstractPage {
    public static AndroidDriver driver;
    public static WebDriverWait wait;
    public static Services services;
    public static Actions actions;
    public static Database database;

    public AbstractPage(AndroidDriver driver) {
        AbstractPage.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        services = new Services();
        actions = new Actions(driver);
        database = new Database();
    }

    public void slide(int xOffset, int yOffset, WebElement webElement) {
        actions.moveToElement(webElement)
                .clickAndHold()
                .moveByOffset(xOffset, yOffset)
                .release()
                .perform();
    }
}

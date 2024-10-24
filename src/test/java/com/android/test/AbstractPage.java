package com.android.test;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class AbstractPage {
    public AndroidDriver driver;
    public JsonHandler jsonHandler;
    public WebDriverWait wait;
    public Services services;
    public Actions actions;
    public Database database;

    public AbstractPage(AndroidDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        this.jsonHandler = new JsonHandler("");
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.services = new Services();
        this.actions = new Actions(driver);
        this.database = new Database();
    }
}

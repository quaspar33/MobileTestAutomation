package com.android.test;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

public abstract class AbstractPage {
    public static AndroidDriver driver;
    public static WebDriverWait wait;
    public static Actions actions;
    public static Database database;
    public static ApiHandler apiHandler;

    public AbstractPage(AndroidDriver driver) {
        AbstractPage.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        actions = new Actions(driver);
        database = new Database();
        apiHandler = new ApiHandler();
    }

    public void slideFromElement(WebElement element, int xOffset, int yOffset) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 0);

        swipe.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), element.getLocation().x, element.getLocation().y));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(500), PointerInput.Origin.viewport(), element.getLocation().x + xOffset, element.getLocation().y + yOffset));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(swipe));
    }

    public void implicitWait(int count, TimeUnit timeUnit) {
        driver.manage().timeouts().implicitlyWait(count, timeUnit);
    }
}

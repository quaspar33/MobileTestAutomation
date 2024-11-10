package com.android.test;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public abstract class AbstractPage {
    public static AndroidDriver driver;
    public static WebDriverWait wait;
    public static Actions actions;
    public static Database database;
    public static ApiHandler apiHandler;

    private HashMap<String, AndroidKey> keyMap = new HashMap<>() {{
        put("0", AndroidKey.DIGIT_0);
        put("1", AndroidKey.DIGIT_1);
        put("2", AndroidKey.DIGIT_2);
        put("3", AndroidKey.DIGIT_3);
        put("4", AndroidKey.DIGIT_4);
        put("5", AndroidKey.DIGIT_5);
        put("6", AndroidKey.DIGIT_6);
        put("7", AndroidKey.DIGIT_7);
        put("8", AndroidKey.DIGIT_8);
        put("9", AndroidKey.DIGIT_9);
        put("a", AndroidKey.A);
        put("b", AndroidKey.B);
        put("c", AndroidKey.C);
        put("d", AndroidKey.D);
        put("e", AndroidKey.E);
        put("f", AndroidKey.F);
        put("g", AndroidKey.G);
        put("h", AndroidKey.H);
        put("i", AndroidKey.I);
        put("j", AndroidKey.J);
        put("k", AndroidKey.K);
        put("l", AndroidKey.L);
        put("m", AndroidKey.M);
        put("n", AndroidKey.N);
        put("o", AndroidKey.O);
        put("p", AndroidKey.P);
        put("q", AndroidKey.Q);
        put("r", AndroidKey.R);
        put("s", AndroidKey.S);
        put("t", AndroidKey.T);
        put("u", AndroidKey.U);
        put("v", AndroidKey.V);
        put("w", AndroidKey.W);
        put("x", AndroidKey.X);
        put("y", AndroidKey.Y);
        put("z", AndroidKey.Z);
        put("A", AndroidKey.A);
        put("B", AndroidKey.B);
        put("C", AndroidKey.C);
        put("D", AndroidKey.D);
        put("E", AndroidKey.E);
        put("F", AndroidKey.F);
        put("G", AndroidKey.G);
        put("H", AndroidKey.H);
        put("I", AndroidKey.I);
        put("J", AndroidKey.J);
        put("K", AndroidKey.K);
        put("L", AndroidKey.L);
        put("M", AndroidKey.M);
        put("N", AndroidKey.N);
        put("O", AndroidKey.O);
        put("P", AndroidKey.P);
        put("Q", AndroidKey.Q);
        put("R", AndroidKey.R);
        put("S", AndroidKey.S);
        put("T", AndroidKey.T);
        put("U", AndroidKey.U);
        put("V", AndroidKey.V);
        put("W", AndroidKey.W);
        put("X", AndroidKey.X);
        put("Y", AndroidKey.Y);
        put("Z", AndroidKey.Z);
        put(".", AndroidKey.PERIOD);
        put("@", AndroidKey.AT);
        put("-", AndroidKey.MINUS);
    }};

    public AbstractPage(AndroidDriver driver) {
        AbstractPage.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
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

    public void realTyping(String text) {
        for (char c : text.toCharArray()) {
            AndroidKey key = keyMap.get(String.valueOf(c));
            try {
                driver.pressKey(new KeyEvent(key));
            } catch (NullPointerException e) {
                System.out.println(e.getMessage() + ", for char = " + c);
            }
        }
        driver.pressKey(new KeyEvent(AndroidKey.ENTER));
    }
}

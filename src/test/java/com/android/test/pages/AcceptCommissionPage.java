package com.android.test.pages;

import com.android.test.AbstractPage;
import com.android.test.JsonHandler;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;

public class AcceptCommissionPage extends AbstractPage {
    private JsonHandler jsonHandler;
    private int commissionDay;

    @AndroidFindBy(uiAutomator = "new UiSelector().className(\"com.horcrux.svg.PathView\").instance(18)")
    private WebElement startButton;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Zlecenie 2024\")")
    private WebElement commission;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Przyjmuję zlecenie\")")
    private WebElement acceptCommission;

    @AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.view.ViewGroup\").instance(20)")
    private WebElement agreement1;

    @AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.view.ViewGroup\").instance(21)")
    private WebElement agreement2;

    @AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.view.ViewGroup\").instance(22)")
    private WebElement agreement3;

    @AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.view.ViewGroup\").instance(23)")
    private WebElement agreement4;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Potwierdzam\")")
    private WebElement confirmation;

    private HashMap<DayOfWeek, String> dayMap = new HashMap<>() {{
        put(DayOfWeek.MONDAY, "pon.");
        put(DayOfWeek.TUESDAY, "wt.");
        put(DayOfWeek.WEDNESDAY, "śr.");
        put(DayOfWeek.THURSDAY, "czw.");
        put(DayOfWeek.FRIDAY, "pt.");
        put(DayOfWeek.SATURDAY, "sob.");
        put(DayOfWeek.SUNDAY, "ndz.");
    }};

    public AcceptCommissionPage(AndroidDriver driver) {
        super(driver);
        jsonHandler = new JsonHandler("accept_commission.json");
        commissionDay = currentDay + 2;
        apiHandler.POST(
                jsonHandler.getStrFromJson("uri"),
                String.format(
                        "{\"employees_per_day\":1,\"hours\":1,\"start_time\":\"08:00\",\"dates\":[\"%d-%02d-%02d\"],\"region\":\"4809\",\"location\":\"460\",\"commission\":\"1463\"}",
                        currentYear,
                        currentMonth,
                        commissionDay),
                jsonHandler.getStrFromJson("auth"));
        System.out.println("Wystawiono zlecenie na: " + LocalDate.of(currentYear, currentMonth, commissionDay));
        System.out.println("Rozpoczynam test przyjęcia zlecenia!");
    }

    public void clickCommission() {
        wait.until(ExpectedConditions.visibilityOf(startButton)).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
                String.format("//android.view.ViewGroup[@content-desc=\"%02d, %s\"]/android.view.ViewGroup",
                        commissionDay,
                        dayMap.get(currentDate.getDayOfWeek().plus(2)))
        ))).click();

        wait.until(ExpectedConditions.visibilityOf(commission)).click();
    }

    public void acceptCommission() {
        wait.until(ExpectedConditions.visibilityOf(acceptCommission)).click();
    }

    public void setAgreements() {
        wait.until(ExpectedConditions.visibilityOf(agreement1)).click();
        agreement2.click();
        agreement3.click();
        agreement4.click();
        confirmation.click();
    }
}

package com.android.test.pages;

import com.android.test.AbstractPage;
import com.android.test.JsonHandler;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.DayOfWeek;
import java.time.Duration;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public class AcceptCommissionPage extends AbstractPage {
    private JsonHandler jsonHandler;
    private int commissionDay;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Start\")")
    private WebElement startButton;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Zlecenie 2024\")")
    private WebElement commission;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Zlecenie 2024\").instance(0)")
    private WebElement commissionInstance;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Sprawdź jak dojechać\")")
    private WebElement slideElement;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Przyjmuję zlecenie\")")
    private WebElement acceptCommission;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Potwierdzam, że moje dane w Kwestionariuszu osobowym są aktualne. *\")")
    private WebElement agreement1;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Potwierdzam zapoznanie się ze szczegółami zlecenia. *\")")
    private WebElement agreement2;

    @AndroidFindBy(xpath = "//android.widget.CheckBox[@content-desc=\"Potwierdzam zapoznanie się z Ogólnymi Warunkami Realizacji zleceń. Przeczytaj OWU *\"]/android.widget.CheckBox/android.view.ViewGroup")
    private WebElement agreement3;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Potwierdzam, że nie jestem pracownikiem tego Klienta. *\")")
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
        System.out.println("Rozpoczynam test przyjęcia zlecenia!");
    }

    public void clickCommission() {
        wait.until(ExpectedConditions.visibilityOf(startButton)).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format("//android.view.ViewGroup[@content-desc=\"%02d, %s\"]/android.view.ViewGroup", commissionDay, dayMap.get(currentDate.getDayOfWeek().plus(2)))))).click();
        AtomicReference<WebElement> atomicCommission = new AtomicReference<>(null);
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(driver -> {
            apiHandler.POST(
                    jsonHandler.getStrFromJson("uri"),
                    String.format(
                            "{\"employees_per_day\":1,\"hours\":1,\"start_time\":\"08:00\",\"dates\":[\"%d-%02d-%02d\"],\"region\":\"4809\",\"location\":\"460\",\"commission\":\"1463\"}",
                            currentYear,
                            currentMonth,
                            commissionDay
                    ),
                    jsonHandler.getStrFromJson("auth")
            );
            System.out.println("Wystawiono zlecenie!");
            refreshApp();
            if (commission.isDisplayed()) {
                atomicCommission.set(commission);
                return true;
            } else if (commissionInstance.isDisplayed()) {
                atomicCommission.set(commissionInstance);
                return true;
            } else {
                return false;
            }
        });
        atomicCommission.get().click();
    }

    public void clickAcceptCommission() {
        slideFromElement(wait.until(ExpectedConditions.visibilityOf(slideElement)), 0, -1500);
        wait.until(ExpectedConditions.visibilityOf(acceptCommission)).click();
    }

    public void setAgreements() {
        wait.until(ExpectedConditions.visibilityOf(agreement1)).click();
        agreement2.click();
        agreement3.click();
        agreement4.click();
        confirmation.click();
        implicitWait(3000);
    }
}

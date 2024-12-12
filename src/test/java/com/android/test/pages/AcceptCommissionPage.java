package com.android.test.pages;

import com.android.test.AbstractPage;
import com.android.test.JsonHandler;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public class AcceptCommissionPage extends AbstractPage {
    private JsonHandler jsonHandler;
    private int commissionDay;
    private int commissionsCount;
    private WebElement commission;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Start\")")
    private WebElement startButton;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Lokalizacja\")")
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
        commissionDay = currentDate.plusDays(2).getDayOfMonth();
        database.connect();
        commissionsCount = database.queryForCommission(String.format("select count(*) as 'commissions' from tikrow_dev.commissions where startDate like '2024-%02d-%02d %%' and taken = 0", currentMonth, commissionDay));
        System.out.println(String.format("Liczba zleceń na dzień 2024-%02d-%02d: %s", currentMonth, commissionDay, commissionsCount));
        database.disconnect();
        System.out.println("Rozpoczynam test przyjęcia zlecenia!");
    }

    public void clickCommission() {
        wait.until(ExpectedConditions.visibilityOf(startButton)).click();
        By dateFilter = By.xpath(String.format("//android.view.ViewGroup[@content-desc=\"%d, %s\"]/android.view.ViewGroup", commissionDay, dayMap.get(currentDate.getDayOfWeek().plus(2))));
        wait.until(ExpectedConditions.presenceOfElementLocated(dateFilter)).click();
        if (commissionsCount > 0) {
            touchFromElement(wait.until(ExpectedConditions.presenceOfElementLocated(dateFilter)), 0, 1200);
        } else {
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
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"Zlecenie 2024\")")))).click();
        }
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

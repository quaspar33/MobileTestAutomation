package com.android.test.pages;

import com.android.test.Services;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class QuestionnaireFirstPage {
    private AndroidDriver driver;
    WebDriverWait wait;
    private int currentMonth;
    private int currentDay;
    private int currentYear;
    private Services services;


    public QuestionnaireFirstPage(AndroidDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        services = new Services();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MM");
        currentMonth = currentDate.getMonthValue();
        currentDay = currentDate.getDayOfMonth();
        currentYear = currentDate.getYear();
    }

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Wypełnij kwestionariusz\")")
    private WebElement fillQuestionnaire;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Data urodzenia\")")
    private WebElement birthDate;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/button1\")")
    private WebElement birthDateOkButton;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Obywatelstwo\")")
    private WebElement country;

    @AndroidFindBy(uiAutomator = "new UiSelector().description(\"Polska\")")
    private WebElement polska;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"PESEL\")")
    private WebElement pesel;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Imię\")")
    private WebElement name;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Nazwisko\")")
    private WebElement surname;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"E-mail\")")
    private WebElement email;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"123456789\")")
    private WebElement phoneNumber;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Rodzaj dokumentu\")")
    private WebElement documentType;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Seria i numer dokumentu\")")
    private WebElement documentSeriesAndType;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Data ważności dokumentu\")")
    private WebElement documentDate;

    @AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.EditText\").instance(3)")
    private WebElement taxOffice;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Nr rachunku bankowego\")")
    private WebElement bankNumber;

    @AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.EditText\").instance(5)")
    private WebElement postalCode;

    @AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.view.ViewGroup\").instance(47)")
    private WebElement yesCheckbox;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Dalej\")")
    private WebElement nextPage;

    public void clickFillQuestionnaire() {
        System.out.println("Rozpoczynam wypełnianie kwestionariusza!");
        wait.until(ExpectedConditions.visibilityOf(fillQuestionnaire));
        fillQuestionnaire.click();
    }

    public void enterBirthDate() {
        wait.until(ExpectedConditions.visibilityOf(birthDate));
        birthDate.click();
        Actions actions = new Actions(driver);
        WebElement yearPicker;
        for (int i = 0; i < 20; i++) {
            yearPicker = driver.findElement(By.xpath("//android.widget.EditText[@resource-id=\"android:id/numberpicker_input\" and @text=\"" + (currentYear - i) + "\"]"));
            wait.until(ExpectedConditions.visibilityOf(yearPicker));
            actions.moveToElement(yearPicker)
                    .clickAndHold()
                    .moveByOffset(0, 120)
                    .release()
                    .perform();
        }
        birthDateOkButton.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    public void enterCountry() {
        wait.until(ExpectedConditions.visibilityOf(country));
        country.click();
        wait.until(ExpectedConditions.visibilityOf(polska));
        polska.click();
    }

    public void enterPesel() {
        String generatedPesel = services.generatePesel(LocalDate.of(currentYear, currentMonth, currentDay), 'm');
        System.out.printf("Wygenerowano pesel dla daty {} {} {}: {}%n", currentYear, currentDay, currentMonth, generatedPesel);
        pesel.sendKeys(generatedPesel);
    }

    public void enterName() {
        name.sendKeys("Java");
    }

    public void enterSurname() {
        surname.sendKeys("Appium");
    }
}

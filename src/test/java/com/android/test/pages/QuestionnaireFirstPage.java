package com.android.test.pages;

import com.android.test.JsonHandler;
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
import java.util.HashMap;
import java.util.Map;

public class QuestionnaireFirstPage {
    private AndroidDriver driver;
    WebDriverWait wait;
    private int currentMonth;
    private int currentDay;
    private int currentYear;
    private Services services;
    private JsonHandler jsonHandler;
    Actions actions;


    public QuestionnaireFirstPage(AndroidDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
        services = new Services();
        jsonHandler = new JsonHandler("src/test/java/com/android/test/questionnaire_first.json");
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
    private WebElement chooseCountry;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"PESEL\")")
    private WebElement pesel;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Imię\")")
    private WebElement name;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Nazwisko\")")
    private WebElement surname;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Appium\")")
    private WebElement surnameFilled;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"E-mail\")")
    private WebElement email;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"123456789\")")
    private WebElement phoneNumber;

    @AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.EditText\").instance(3)")
    private WebElement taxOffice;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Urząd Skarbowy Poznań-Wilda 61-558, Dolna Wilda 80\").instance(0)")
    private WebElement taxOfficeName;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Nr rachunku bankowego\")")
    private WebElement bankNumber;

    @AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.EditText\").instance(4)")
    private WebElement postalCode;

    @AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.EditText\").instance(5)")
    private WebElement cityName;

    @AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.EditText\").instance(6)")
    private WebElement streetName;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Nr budynku\")")
    private WebElement buildingNumber;

    @AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.view.ViewGroup\").instance(52)")
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
    }

    public void enterCountry() {
        wait.until(ExpectedConditions.visibilityOf(country));
        country.click();
        wait.until(ExpectedConditions.visibilityOf(chooseCountry));
        chooseCountry.click();
    }

    public void enterPesel() {
        System.out.printf("Generuje pesel dla daty {} {} {}%n", currentYear, currentMonth, currentDay);
        String generatedPesel = services.generatePesel(LocalDate.of(currentYear, currentMonth, currentDay), 'm');
        System.out.printf("Wygenerowano pesel: {}%n", generatedPesel);
        pesel.sendKeys(generatedPesel);
    }

    public void enterName() {
        wait.until(ExpectedConditions.visibilityOf(name));
        name.sendKeys("Java");
    }

    public void enterSurname() {
        wait.until(ExpectedConditions.visibilityOf(surname));
        surname.sendKeys("Appium");
        wait.until(ExpectedConditions.visibilityOf(surnameFilled));
        actions.moveToElement(surnameFilled)
                .clickAndHold()
                .moveByOffset(0, -1500)
                .release()
                .perform();
    }

    public void enterEmail() {
        wait.until(ExpectedConditions.visibilityOf(email));
        email.sendKeys("test@test.com");
    }

    public void enterPhoneNumber() {
        phoneNumber.sendKeys(jsonHandler.getStrFromJson("phoneNumber"));
    }

    public void enterTaxOffice() {
        taxOffice.click();
        wait.until(ExpectedConditions.visibilityOf(taxOfficeName));
        taxOfficeName.click();
    }

    public void enterBankNumber() {
        String bankNr = services.generateRandomBankAccount();
        System.out.println("Wygenerowano numer bankowy: " + bankNr);
        bankNumber.sendKeys(bankNr);
    }

    public void enterAddress() {
        Map<WebElement, String> adressMap = new HashMap<>() {{
           put(postalCode, "postalCode");
           put(cityName, "cityName");
           put(streetName, "streetName");
//           put(buildingNumber, "buildingNumber");
        }};

        for (Map.Entry<WebElement, String> entry : adressMap.entrySet()) {
            String jsonOut = jsonHandler.getStrFromJson(entry.getValue());
            wait.until(ExpectedConditions.visibilityOf(entry.getKey()));
            entry.getKey().sendKeys(jsonOut);
            WebElement chooseElement = driver.findElement(By.xpath("//android.widget.TextView[@text=\"" + jsonOut +"\"]"));
            wait.until(ExpectedConditions.visibilityOf(chooseElement));
            chooseElement.click();
        }

        wait.until(ExpectedConditions.visibilityOf(buildingNumber));
        buildingNumber.sendKeys(jsonHandler.getStrFromJson("buildingNumber"));
    }

    public void setYesCheckbox() {
        yesCheckbox.click();
    }

    public void enterNextPage() {
        nextPage.click();
    }
}

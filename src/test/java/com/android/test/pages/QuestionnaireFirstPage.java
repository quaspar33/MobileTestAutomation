package com.android.test.pages;

import com.android.test.AbstractPage;
import com.android.test.JsonHandler;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;
import java.util.Map;

public class QuestionnaireFirstPage extends AbstractPage {
    private JsonHandler jsonHandler;
    private int currentMonth;
    private int currentDay;
    private int currentYear;


    public QuestionnaireFirstPage(AndroidDriver driver) {
        super(driver);
        jsonHandler = new JsonHandler("questionnaire_first.json");
        LocalDate currentDate = LocalDate.now();
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

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"E-mail\")")
    private WebElement email;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"123456789\")")
    private WebElement phoneNumber;

    @AndroidFindBy(uiAutomator = "new UiSelector().className(\"com.horcrux.svg.SvgView\").instance(2)")
    private WebElement taxOffice;

    @AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.view.ViewGroup\").instance(62)")
    private WebElement taxOfficeName;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Nr rachunku bankowego\")")
    private WebElement iban;

    @AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.EditText\").instance(2)")
    private WebElement postalCode;

    @AndroidFindBy(uiAutomator = "new UiSelector().className(\"com.horcrux.svg.SvgView\").instance(4)")
    private WebElement postalCodeScroll;

    @AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.EditText\").instance(3)")
    private WebElement cityName;

    @AndroidFindBy(uiAutomator = "new UiSelector().className(\"com.horcrux.svg.SvgView\").instance(6)")
    private WebElement cityNameScroll;

    @AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.EditText\").instance(4)")
    private WebElement streetName;

    @AndroidFindBy(uiAutomator = "new UiSelector().className(\"com.horcrux.svg.SvgView\").instance(8)")
    private WebElement streetNameScroll;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Nr budynku\")")
    private WebElement buildingNumber;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"12\")")
    WebElement buildingNumberScroll;

    @AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.view.ViewGroup\").instance(51)")
    private WebElement yesCheckbox;

    @AndroidFindBy(uiAutomator = "new UiSelector().className(\"com.horcrux.svg.PathView\").instance(0)")
    private WebElement goBackButton;

    @AndroidFindBy(uiAutomator = "new UiSelector().className(\"com.horcrux.svg.PathView\").instance(17)")
    private WebElement fillQuestionnaireFromProfile;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Dalej\")")
    private WebElement nextPage;

    public void clickFillQuestionnaire() {
        System.out.println("Rozpoczynam wypełnianie pierwszej strony kwestionariusza!");
        wait.until(ExpectedConditions.visibilityOf(fillQuestionnaire));
        fillQuestionnaire.click();
    }

    public void enterBirthDate() {
        wait.until(ExpectedConditions.visibilityOf(birthDate));
        birthDate.click();
        By element;
        for (int i = 0; i < 20; i++) {
            element = By.xpath(String.format("//android.widget.EditText[@text=\"" + "%d" + "\"]", currentYear));
            WebElement webElement = wait.until(ExpectedConditions.presenceOfElementLocated(element));
            slideFromElement(webElement, 0, 140);
            currentYear--;
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
        String peselStr = apiHandler.GET(String.format("https://generator.avris.it/api/PL/pesel?birthdate=%d-%02d-%02d&gender=m", currentYear, currentMonth, currentDay));
        wait.until(ExpectedConditions.visibilityOf(pesel));
        pesel.click();
        realTyping(peselStr.substring(1, peselStr.length() - 1));
    }

    public void enterName() {
        wait.until(ExpectedConditions.visibilityOf(name));
        name.click();
        realTyping("Test");
    }

    public void enterSurname() {
        wait.until(ExpectedConditions.visibilityOf(surname));
        slideFromElement(surname, 0, -500);
        surname.click();
        realTyping("Appium");
    }

    public void enterEmail() {
        wait.until(ExpectedConditions.visibilityOf(email));
        email.click();
        realTyping(jsonHandler.getStrFromJson("email"));
    }

    public void enterPhoneNumber() {
        phoneNumber.click();
        realTyping(jsonHandler.getStrFromJson("phoneNumber"));
    }

    public void enterTaxOffice() {
        wait.until(ExpectedConditions.visibilityOf(taxOffice));
        slideFromElement(taxOffice, 0, -1500);
        taxOffice.click();
        wait.until(ExpectedConditions.visibilityOf(taxOfficeName));
        taxOfficeName.click();
    }

    public void enterIban() {
        String ibanStr = apiHandler.GET("https://generator.avris.it/api/_/iban?country=PL");
        iban.click();
        realTyping(ibanStr.substring(3, ibanStr.length() - 1));
    }

    public void enterAddress() {
        LinkedHashMap<WebElement[], String> adresMap = new LinkedHashMap<>() {{
            put(new WebElement[]{postalCode, postalCodeScroll}, "postalCode");
            put(new WebElement[]{cityName, cityNameScroll}, "cityName");
            put(new WebElement[]{streetName, streetNameScroll}, "streetName");
        }};

        for (Map.Entry<WebElement[], String> entry : adresMap.entrySet()) {
            wait.until(ExpectedConditions.visibilityOf(entry.getKey()[0]));
            entry.getKey()[0].sendKeys(jsonHandler.getStrFromJson(entry.getValue()));
            entry.getKey()[1].click();

            By currentElement = By.xpath("//android.widget.TextView[@text=\"" + jsonHandler.getStrFromJson(entry.getValue()) + "\"]");
            WebElement chooseElement = wait.until(ExpectedConditions.presenceOfElementLocated(currentElement));
            chooseElement.click();

            slideFromElement(chooseElement, 0, -600);
        }

        wait.until(ExpectedConditions.visibilityOf(buildingNumber));
        slideFromElement(buildingNumber, 0, -600);
        buildingNumber.sendKeys(jsonHandler.getStrFromJson("buildingNumber"));
    }

    public void setYesCheckbox() {
        wait.until(ExpectedConditions.visibilityOf(yesCheckbox));
        yesCheckbox.click();
    }

    public void enterNextPage() {
        try {
            Thread.sleep(10000);
            System.out.println("Śpię...");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        nextPage.click();
    }
}

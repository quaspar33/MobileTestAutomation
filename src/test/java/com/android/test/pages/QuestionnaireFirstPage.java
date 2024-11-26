package com.android.test.pages;

import com.android.test.AbstractPage;
import com.android.test.JsonHandler;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.LinkedHashMap;
import java.util.Map;

public class QuestionnaireFirstPage extends AbstractPage {
    private JsonHandler jsonHandler;
    private int birthYear;

    public QuestionnaireFirstPage(AndroidDriver driver) {
        super(driver);
        jsonHandler = new JsonHandler("questionnaire_first.json");
        birthYear = currentYear;
        System.out.println("Rozpoczynam wypełnianie pierwszej strony kwestionariusza!");
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

    @AndroidFindBy(uiAutomator = "new UiSelector().className(\"com.horcrux.svg.PathView\").instance(2)")
    private WebElement taxOffice;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Urząd Skarbowy Poznań-Wilda 61-558, Dolna Wilda 80\").instance(0)")
    private WebElement taxOfficeName;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Nr rachunku bankowego\")")
    private WebElement iban;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Kod pocztowy *\")")
    private WebElement postalCode;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Miasto *\")")
    private WebElement cityName;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Ulica *\")")
    private WebElement streetName;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Nr budynku *\")")
    private WebElement buildingNumber;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Tak\")")
    private WebElement yesCheckbox;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Dalej\")")
    private WebElement nextPage;

    Map<WebElement, String> addressMap = new LinkedHashMap<>() {{
        put(postalCode, "postalCode");
        put(cityName, "cityName");
        put(streetName, "streetName");
        put(buildingNumber, "buildingNumber");
    }};

    public void clickQuestionnaire() {
        wait.until(ExpectedConditions.visibilityOf(fillQuestionnaire)).click();
    }

    public void enterBirthDate() {
        wait.until(ExpectedConditions.visibilityOf(birthDate)).click();
        while (currentDate.getYear() - birthYear < 18) {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format("//android.widget.EditText[@text=\"%d\"]", birthYear))));
            slideFromElement(element, 0, 180);
            birthYear--;
        }
        birthDateOkButton.click();
    }

    public void enterCountry() {
        wait.until(ExpectedConditions.visibilityOf(country)).click();
        wait.until(ExpectedConditions.visibilityOf(chooseCountry)).click();
    }

    public void enterPesel() {
        String peselStr = apiHandler.GET(String.format("https://generator.avris.it/api/PL/pesel?birthdate=%d-%02d-%02d&gender=m", birthYear, currentMonth, currentDay));
        wait.until(ExpectedConditions.elementToBeClickable(pesel)).click();
        realTyping(peselStr.substring(1, peselStr.length() - 1));
    }

    public void enterName() {
        wait.until(ExpectedConditions.visibilityOf(name)).click();
        realTyping("Test");
    }

    public void enterSurname() {
        wait.until(ExpectedConditions.visibilityOf(surname));
        slideFromElement(surname, 0, -500);
        surname.click();
        realTyping("Appium");
    }

    public void enterEmail() {
        wait.until(ExpectedConditions.visibilityOf(email)).click();
        realTyping(jsonHandler.getStrFromJson("email"));
    }

    public void enterPhoneNumber() {
        slideFromElement(phoneNumber, 0, -200);
        phoneNumber.click();
        realTyping(jsonHandler.getStrFromJson("phoneNumber"));
    }

    public void enterTaxOffice() {
        wait.until(ExpectedConditions.visibilityOf(taxOffice));
        slideFromElement(taxOffice, 0, -500);
        taxOffice.click();
        wait.until(ExpectedConditions.visibilityOf(taxOfficeName)).click();
    }

    public void enterIban() {
        String ibanStr = apiHandler.GET("https://generator.avris.it/api/_/iban?country=PL");
        wait.until(ExpectedConditions.visibilityOf(iban)).click();
        String ibanStrParsed = ibanStr.substring(3, ibanStr.length() - 1).replace(" ", "");
        realTyping(ibanStrParsed);
        slideFromElement(wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//android.widget.EditText[@text=\"" + ibanStrParsed + "\"]"))), 0, -500);
    }

    public void enterAddress() {
        addressMap.forEach((key, value) -> {
            touchFromElement(wait.until(ExpectedConditions.visibilityOf(key)), 0, 60);
            realTyping(jsonHandler.getStrFromJson(value));
            if (!value.equals("buildingNumber")) {
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//android.widget.TextView[@text=\"" + jsonHandler.getStrFromJson(value) + "\"]"))).click();
            }
            slideFromElement(wait.until(ExpectedConditions.visibilityOf(key)), 0, -600);
        });
    }

    public void setCheckbox() {
        wait.until(ExpectedConditions.visibilityOf(yesCheckbox)).click();
    }

    public void enterNextPage() {
        implicitWait(2000);
        nextPage.click();
    }
}

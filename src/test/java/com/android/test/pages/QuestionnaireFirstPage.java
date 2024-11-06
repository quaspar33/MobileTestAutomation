package com.android.test.pages;

import com.android.test.AbstractPage;
import com.android.test.JsonHandler;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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
        currentYear = currentDate.getYear() - 20;
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
        implicitWait(500, TimeUnit.MILLISECONDS);
        for (int i = 0; i < 20; i++) {
            slideFromPoint(745, 1118, 745, 1249);
            implicitWait(200, TimeUnit.MILLISECONDS);
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
        System.out.println(String.format("Generuje pesel dla daty: %d-%d-%d", currentYear, currentMonth, currentDay));
        String generatedPesel = apiHandler.GET(String.format("https://generator.avris.it/api/PL/pesel?birthdate=%d-%02d-%02d&gender=m", currentYear-20, currentMonth, currentDay));
        wait.until(ExpectedConditions.visibilityOf(pesel));
        pesel.sendKeys(generatedPesel.substring(1, generatedPesel.length() - 1));
    }

    public void enterName() {
        wait.until(ExpectedConditions.visibilityOf(name));
        name.sendKeys("Java");
    }

    public void enterSurname() {
        wait.until(ExpectedConditions.visibilityOf(surname));
        surname.sendKeys("Appium");
    }

    public void enterEmail() {
        slideFromPoint(550, 999, 550, 500);
        wait.until(ExpectedConditions.visibilityOf(email));
        email.sendKeys(jsonHandler.getStrFromJson("email"));
    }

    public void enterPhoneNumber() {
        phoneNumber.sendKeys(jsonHandler.getStrFromJson("phoneNumber"));
        slideFromPoint(516, 1913, 512, 471);
    }

    public void enterTaxOffice() {
        wait.until(ExpectedConditions.visibilityOf(taxOffice));
        taxOffice.click();
        wait.until(ExpectedConditions.visibilityOf(taxOfficeName));
        taxOfficeName.click();
    }

    public void enterIban() {
        String ibanStr = apiHandler.GET("https://generator.avris.it/api/_/iban?country=PL");
        iban.sendKeys(ibanStr.substring(3, ibanStr.length() - 1));
    }

    public void enterAddress() {
        Map<WebElement[], String> adressMap = new LinkedHashMap<>() {{
           put(new WebElement[]{postalCode, postalCodeScroll}, "postalCode");
           put(new WebElement[]{cityName, cityNameScroll}, "cityName");
           put(new WebElement[]{streetName, streetNameScroll}, "streetName");
        }};

        WebElement element;
        WebElement elementScroll;
        String xPathText;
        By currentElement;
        WebElement chooseElement;

        for (Map.Entry<WebElement[], String> entry : adressMap.entrySet()) {
            element = entry.getKey()[0];
            elementScroll = entry.getKey()[1];
            xPathText = jsonHandler.getStrFromJson(entry.getValue());
            wait.until(ExpectedConditions.visibilityOf(element));
            element.sendKeys(xPathText);
            elementScroll.click();
            currentElement = By.xpath("//android.widget.TextView[@text=\"" + xPathText +"\"]");
            wait.until(ExpectedConditions.presenceOfElementLocated(currentElement));
            chooseElement = driver.findElement(currentElement);
            chooseElement.click();
            slideFromPoint(491, 1380, 471, 676);
        }

        wait.until(ExpectedConditions.visibilityOf(buildingNumber));
        buildingNumber.sendKeys(jsonHandler.getStrFromJson("buildingNumber"));
        slideFromPoint(491, 1380, 471, 676);
    }

    public void setYesCheckbox() {
        wait.until(ExpectedConditions.visibilityOf(yesCheckbox));
        yesCheckbox.click();
    }

    public void enterNextPage() {
        implicitWait(10000, TimeUnit.MILLISECONDS);
        nextPage.click();
    }
}

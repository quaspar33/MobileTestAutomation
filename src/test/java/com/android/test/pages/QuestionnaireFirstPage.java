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
    private WebElement bankNumber;

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
        String generatedPesel = generatePesel(LocalDate.of(currentYear-20, currentMonth, currentDay), 'm');
        System.out.println(String.format("Wygenerowano pesel: %s", generatedPesel));
        wait.until(ExpectedConditions.visibilityOf(pesel));
        pesel.sendKeys(generatedPesel);
    }

    private String generatePesel(LocalDate birthDate, char gender) {
        StringBuilder pesel = new StringBuilder();

        int year = birthDate.getYear();
        int month = birthDate.getMonthValue();
        int day = birthDate.getDayOfMonth();

        if (year >= 2000) {
            month += 20;
        }

        pesel.append(String.format("%02d%02d%02d", year % 100, month, day));

        Random random = new Random();
        int start = (gender == 'M' || gender == 'm') ? 1 : 0;
        int serialNumber = random.nextInt(500) * 10 + start;

        pesel.append(String.format("%03d", serialNumber));

        int[] weights = {1, 3, 7, 9, 1, 3, 7, 9, 1, 3};
        int sum = 0;

        for (int i = 0; i < pesel.length(); i++) {
            sum += Character.getNumericValue(pesel.charAt(i)) * weights[i];
        }

        int remainder = sum % 10;
        int checksum = (10 - remainder) % 10;

        pesel.append((char) (checksum + '0'));

        return pesel.toString();
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

    public void enterBankNumber() {
        String bankNr = generateRandomBankAccount();
        System.out.println("Wygenerowano numer bankowy: " + bankNr);
        bankNumber.sendKeys(bankNr);
    }

    private String generateRandomBankAccount() {
        StringBuilder accountNumber = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 24; i++) {
            accountNumber.append(random.nextInt(10));
        }

        String ibanWithoutChecksum = "PL00" + accountNumber;

        String rearranged = ibanWithoutChecksum.substring(4) + "252100";

        BigInteger ibanAsNumber = new BigInteger(rearranged);

        BigInteger remainder = ibanAsNumber.mod(BigInteger.valueOf(97));
        BigInteger checksum = BigInteger.valueOf(98).subtract(remainder);

        return "PL" + String.format("%02d", checksum) + accountNumber;
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

    public void enableNextPageButton() {
        goBackButton.click();
        wait.until(ExpectedConditions.visibilityOf(fillQuestionnaireFromProfile));
        fillQuestionnaireFromProfile.click();
    }

    public void enterNextPage() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        nextPage.click();
    }
}

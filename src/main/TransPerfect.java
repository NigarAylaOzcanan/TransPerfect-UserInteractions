package main;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;
import utilities.BaseDriver;
import utilities.ConfigReader;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class TransPerfect extends BaseDriver {
    @Test
    public void transPerfect() {

        int length = 10;
        String typedText;
        String firstName = "Mario";
        String soluzioniURL;
        Actions action = new Actions(driver);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String randomPhoneNumber = RandomStringUtils.randomNumeric(length);

        // Go to www.transperfect.com
        driver.navigate().to(ConfigReader.getProperty("URL"));
        wait.until(ExpectedConditions.urlToBe(ConfigReader.getProperty("URL")));

        // Click on Industries in the top navigation bar
        WebElement industriesButton = driver.findElement(By.linkText("Industries"));
        industriesButton.click();

        // Close the popup if needed
        action.scrollByAmount(0, 500).perform();
        List<WebElement> cookies = driver.findElements(By.cssSelector("[class='cookiesjsr-banner--action'] button"));
        if (cookies.size() > 0) {
            cookies.get(1).click();
        }

        // Click on Retail & E-commerce
        WebElement retailButton = driver.findElement(By.xpath("(//div[@class='t-page-cards-item-content'])[2]"));
        wait.until(ExpectedConditions.elementToBeClickable(retailButton));
        action.click(retailButton).perform();

        // Wait for 5 seconds
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Scroll down/move the screen until Client Stories are visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='text-left']>div")));
        WebElement clientStories = driver.findElement(By.cssSelector("[class='text-left']>div"));
        action.scrollByAmount(0, 3500).perform();

        // Click on the search engine icon in the top navigation bar
        WebElement searchIcon = driver.findElement(By.cssSelector("[class='menu-top-wrapper'] span[class='t-search-link']"));
        searchIcon.click();

        // Enter text "translation" in the Search text... textbox
        WebElement searchBox = driver.findElement(By.xpath("//input[@id='edit-search-api-fulltext']"));
        searchBox.sendKeys("translation");

        // Delete the text you just entered
        typedText = searchBox.getAttribute("value");
        for (int i = 0; i < typedText.length(); i++) {
            action.sendKeys(Keys.BACK_SPACE).perform();
        }

        // Enter "quote" in the Search text... textbox
        searchBox.sendKeys("quotes" + Keys.ENTER);

        // Wait for the "Request a Free Quote" search result to appear
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Request a Free Quote']")));
        WebElement requestQuoteResult = driver.findElement(By.xpath("//a[text()='Request a Free Quote']"));

        // Click on Request a Free Quote
        requestQuoteResult.click();

        // Hover the mouse button over Website Localization to cause the popup with the description to appear
        WebElement websiteLocalization = driver.findElement(By.xpath("(//label[@class='form-check-label'])[2]"));
        action.moveToElement(websiteLocalization).perform();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='tooltipster-content'] [id='edit-your-interests-website--description']")));
        WebElement descriptionPopup = driver.findElement(By.cssSelector("[class='tooltipster-content'] [id='edit-your-interests-website--description']"));

        // Tick the boxes for Translation Services and Legal Services
        WebElement translationServices = driver.findElement(By.xpath("//input[@id='edit-your-interests-translation']"));
        WebElement legalServices = driver.findElement(By.xpath("//input[@id='edit-your-interests-legal']"));

        if (!translationServices.isSelected()) {
            translationServices.click();
        }
        if (!legalServices.isSelected()) {
            legalServices.click();
        }

        // Enter text into First Name text box
        action.scrollByAmount(0, 500).perform();

        WebElement firstNameField = driver.findElement(By.cssSelector("[id='edit-first-name']"));
        firstNameField.sendKeys(firstName);

        // Generate a random number and enter it into Telephone text box
        WebElement phoneNumberField = driver.findElement(By.cssSelector("[id='edit-phone-work']"));
        phoneNumberField.sendKeys(randomPhoneNumber);

        // Take a screenshot and save it to your desktop
        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String desktopPath = System.getProperty("user.home") + "/Desktop";

        try {
            FileUtils.copyFile(screenshotFile, new File(desktopPath + "\\screenshot.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //  Change the website language from English to Italian
        WebElement languageButton = driver.findElement(By.cssSelector("[class='lang-region']"));
        languageButton.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Italiano']")));
        WebElement italianLanguage = driver.findElement(By.xpath("//a[text()='Italiano']"));
        italianLanguage.click();

        // Open the Solutions (Soluzioni) page in a new tab
        WebElement soluzioni = driver.findElement(By.xpath("//a[contains(text(),'Soluzioni')]"));
        soluzioniURL = soluzioni.getAttribute("href");
        js.executeScript("window.open(arguments[0])", soluzioniURL);


        // Switch to new opened TAB
        Set<String> allWindows = driver.getWindowHandles();
        for (String window : allWindows) {
            driver.switchTo().window(window);
        }

        // Close the browser
        driver.quit();
    }
}


package main;

import org.testng.Assert;
import utilities.BaseDriver;
import org.openqa.selenium.*;
import utilities.ConfigReader;
import org.testng.annotations.Test;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.interactions.Actions;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.File;
import java.util.Set;
import java.util.List;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TransPerfect extends BaseDriver {

    @Test
    public void transPerfect() {
        int length = 8;
        String typedText;
        String timestamp;
        String soluzioniURL;
        String randomPhoneNumber;
        String firstName = "Mario";
        String firstText="translation";
        String secondText="quotes";
        DateTimeFormatter formatter;
        Actions action = new Actions(driver);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Go to www.transperfect.com
        driver.navigate().to(ConfigReader.getProperty("URL"));
        wait.until(ExpectedConditions.urlToBe(ConfigReader.getProperty("URL")));

        // Close the popup if needed
        action.scrollByAmount(0, 500).perform();
        List<WebElement> cookie = driver.findElements(By.cssSelector("[class='cookiesjsr-banner--action'] button"));
        if (cookie.size() > 0) {
            cookie.get(1).click();
        }

        // Click on Industries in the top navigation bar
        WebElement industriesButton = driver.findElement(By.linkText("Industries"));
        industriesButton.click();

        // Close the popup if needed (The pop-up reappeared after step 3, so the process of closing was repeated.)
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
        searchBox.sendKeys(firstText);

        // Delete the text you just entered
        searchBox.sendKeys(Keys.CONTROL + "a" + Keys.DELETE);

        // Enter "quote" in the Search text... textbox
        searchBox.sendKeys(secondText + Keys.ENTER);

        // Wait for the "Request a Free Quote" search result to appear
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Request a Free Quote']")));
        WebElement requestQuoteResult = driver.findElement(By.xpath("//a[text()='Request a Free Quote']"));
        Assert.assertTrue(requestQuoteResult.isDisplayed(), "Request a Free Quote search result is not displayed!");

        // Click on Request a Free Quote
        requestQuoteResult.click();

        // Hover the mouse button over Website Localization to cause the popup with the description to appear
        WebElement websiteLocalization = driver.findElement(By.xpath("(//label[@class='form-check-label'])[2]"));
        action.moveToElement(websiteLocalization).perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='tooltipster-content'] [id='edit-your-interests-website--description']")));
        WebElement descriptionPopup = driver.findElement(By.cssSelector("[class='tooltipster-content'] [id='edit-your-interests-website--description']"));
        Assert.assertTrue(descriptionPopup.isDisplayed(), "No Pop-up is displayed!");

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
        randomPhoneNumber = RandomStringUtils.randomNumeric(length);
        phoneNumberField.sendKeys(randomPhoneNumber);

        // Take a screenshot and save it to your desktop
        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String desktopPath = System.getProperty("user.home") + "/Desktop";
        formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        timestamp = LocalDateTime.now().format(formatter);

        try {
            FileUtils.copyFile(screenshotFile, new File(desktopPath + "/Screenshot_" + timestamp + ".png"));
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
        tearDown();
    }
}


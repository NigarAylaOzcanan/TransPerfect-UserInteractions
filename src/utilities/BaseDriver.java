package utilities;

import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BaseDriver {
    public static WebDriver driver;
    public static WebDriverWait wait;
    static int xPosition = ConfigReader.getIntProperty("windowPositionX");
    static int yPosition = ConfigReader.getIntProperty("windowPositionY");
    static {
        driver=new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().window().setPosition(new Point(xPosition, yPosition));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(ConfigReader.getIntProperty("pageLoadTimeout")));
        wait=new WebDriverWait(driver,Duration.ofSeconds(ConfigReader.getIntProperty("explicit.wait")));
    }

    public static void tearDown(){
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        driver.quit();
    }
}

package utilities;

import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BaseDriver {
    public static WebDriver driver;
    public static WebDriverWait wait;

    static {
        driver=new ChromeDriver();
        driver.manage().window().setPosition(new Point(1500,0));
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
        wait=new WebDriverWait(driver,Duration.ofSeconds(20));
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

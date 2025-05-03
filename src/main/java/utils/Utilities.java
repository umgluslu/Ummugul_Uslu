package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebDriver;

public class Utilities {

    public static class DriverFactory {
        public  WebDriver driver;

        public  WebDriver getDriver() {
            if (driver == null) {
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                driver.manage().window().maximize();
            }
            return driver;
        }

        public  void quitDriver() {
            if (driver != null) {
                driver.quit();
                driver = null;
            }
        }
    }

}


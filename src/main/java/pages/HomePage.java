package pages;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.BaseMethod;

import java.time.Duration;

public class HomePage  extends BaseMethod {

    private WebDriver driver;

    // Locators
    private By companyMenu = By.xpath("//a[contains(text(), 'Company')]");
    private By careersLink = By.xpath("//a[contains(@href, '/careers') and text()='Careers']");

    private By INSIDERLOGO= By.xpath("//div[@class='container-fluid']/a/img[@alt='insider_logo']");
    // Constructor
    public HomePage(WebDriver driver, ExtentTest test) {
        super(driver, test);
    }

    // Actions

    // 1. Ana sayfanın doğru açıldığını kontrol eder
    public boolean isHomePageLoaded() {
        WebElement companyMenuElement = waitForElement(INSIDERLOGO, 10);
        test.log(Status.INFO, "'Insider' logosu bulundu");
        return companyMenuElement.isDisplayed();
    }
    public void hoverOverCompanyMenu() {
        try {
            System.out.println("➡️ Hover metoduna girildi.");

            WebElement companyElement = waitForElement(companyMenu, 10);

            if (companyElement == null) {
                System.out.println("❌ Element null döndü!");
                test.log(Status.FAIL, "Company elementi bulunamadı — null");
                throw new NullPointerException("companyElement is null");
            }

            System.out.println("✅ Element bulundu: " + companyElement.getText());

            // Element görünmüyorsa JS ile düzelt
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", companyElement);
            ((JavascriptExecutor) driver).executeScript("arguments[0].style.opacity='1'; arguments[0].style.visibility='visible';", companyElement);
            Thread.sleep(500);

            // Hover
            Actions actions = new Actions(driver);
            actions.moveToElement(companyElement).pause(Duration.ofSeconds(1)).perform();

            test.log(Status.INFO, "'Company' menüsüne başarıyla hover yapıldı");

        } catch (Exception e) {
            test.log(Status.FAIL, "Hover işlemi başarısız: " + e.getMessage());
            System.out.println("❌ Hata: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    // Hover sonrası açılan "Careers" bağlantısına tıklanır
    public void clickCareersLink() {
        try {
            WebElement careersElement = waitForElement(careersLink, 10);
            careersElement.click();
            test.log(Status.INFO, "'Careers' bağlantısına tıklandı");
        } catch (Exception e) {
            test.log(Status.FAIL, "'Careers' bağlantısı bulunamadı: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    // 2. Company → Careers menüsüne gider
    public void navigateToCareersPage() {
        Actions actions = new Actions(driver);
        WebElement company = driver.findElement(companyMenu);
        actions.moveToElement(company).perform();

        WebElement careers = driver.findElement(careersLink);
        careers.click();
    }
    public void clickCompany(){
        click(companyMenu,"companymenu");
    }


}

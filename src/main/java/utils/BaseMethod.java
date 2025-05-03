package utils;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BaseMethod {
    protected WebDriver driver;
    protected ExtentTest test;

    public BaseMethod(WebDriver driver, ExtentTest test) {
        this.driver = driver;
        this.test = test;
    }
    protected WebElement waitForElement(By locator, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    protected void click(By locator, String stepDescription) {
        try {
            WebElement element = driver.findElement(locator);
            element.click();
            test.log(Status.INFO, "Tıklandı: " + stepDescription);
        } catch (Exception e) {
            test.log(Status.FAIL, "Tıklama hatası: " + stepDescription + " → " + e.getMessage());
            throw e;
        }
    }


    protected boolean isDisplayed(By locator, String stepDescription) {
        try {
            boolean result = driver.findElement(locator).isDisplayed();
            test.log(Status.INFO, "Görünür mü: " + stepDescription + " → " + result);
            return result;
        } catch (Exception e) {
            test.log(Status.FAIL, "Görünürlük hatası: " + stepDescription + " → " + e.getMessage());
            return false;
        }
    }
    protected boolean isElementVisible(By locator, String elementDescription) {
        try {
            WebElement element = waitForElement(locator, 10);
            boolean visible = element.isDisplayed();

            // Log hem rapora hem terminale gider
            String message = "🔍 " + elementDescription + " görünür mü? → " + visible;
            if (test != null) {
                test.log(visible ? Status.PASS : Status.FAIL, message);
            }
            System.out.println(message);

            return visible;

        } catch (Exception e) {
            String error = "❌ " + elementDescription + " görünürlük kontrolü başarısız: " + e.getMessage();
            if (test != null) {
                test.log(Status.FAIL, error);
            }
            System.out.println(error);
            return false;
        }
    }

    protected void scrollToBottomThenScrollUpUntilVisible(By locator, int maxTries) {
        //   Sayfanın en altına in — tüm içerikler yükle
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
        try {
            Thread.sleep(2000); // Lazy load için bekle
        } catch (InterruptedException ignored) {}

        //   Şimdi yukarı çık ve elementi görünür yap
        int scrolls = 0;
        while (scrolls < maxTries) {
            try {
                WebElement element = driver.findElement(locator);
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);

                // kısa bekleme
                Thread.sleep(700);

                if (element.isDisplayed() && element.getSize().getHeight() > 0) {
                    test.log(Status.INFO, "✅ Element görünür oldu: " + locator.toString());
                    break;
                }

            } catch (Exception e) {
                test.log(Status.INFO, "⬆️ Yukarı çıkılıyor, deneme: " + scrolls);
            }

            // yukarı scroll (manuel güvence)
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, -200);");
            scrolls++;
            try { Thread.sleep(400); } catch (Exception ignored) {}
        }
    }
    protected void acceptCookiesIfVisible() {
        try {
            By cookieAcceptButton = By.id("wt-cli-accept-all-btn"); // Insider'ın gerçek ID'si

            WebElement acceptBtn = driver.findElement(cookieAcceptButton);

            if (acceptBtn.isDisplayed()) {
                acceptBtn.click();
                test.log(Status.INFO, "🍪 Çerez popup'ı kapatıldı.");
                Thread.sleep(1000); // animasyonla kapanıyorsa bekle
            }
        } catch (Exception e) {
            test.log(Status.INFO, "ℹ️ Çerez popup'ı zaten kapalı veya görünmedi.");
        }
    }
    protected void waitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
            test.log(Status.INFO, "⏱ Bekleme süresi: " + seconds + " saniye");
        } catch (InterruptedException e) {
            test.log(Status.WARNING, "⚠️ Bekleme sırasında hata: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
    protected WebElement scrollToElementAfterPageBottom(By locator, int maxTries) {
        try {
            // 1️⃣ Önce sayfanın en altına in
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
            Thread.sleep(1000); // Lazy-load beklemesi

            // 2️⃣ Şimdi yukarıdan aşağı hedef elementi arayarak yavaşça görünür hale getir
            int scrolls = 0;
            while (scrolls < maxTries) {
                try {
                    WebElement element = driver.findElement(locator);
                    if (element.isDisplayed() && element.getSize().getHeight() > 0) {
                        test.log(Status.INFO, "✅ Element görünür hale geldi (aşağıdan yukarıya): " + locator);
                        return element;
                    }
                } catch (Exception ignored) {}

                ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, -200);");
                scrolls++;
                Thread.sleep(300);
            }

            test.log(Status.FAIL, "❌ Element görünür olmadı: " + locator);
            throw new RuntimeException("Görünür scroll başarısız.");

        } catch (Exception e) {
            test.log(Status.FAIL, "❌ Scroll işlemi başarısız: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    protected WebElement scrollUntilVisible(By locator, int maxScrolls) {
        int scrolls = 0;

        while (scrolls < maxScrolls) {
            try {
                WebElement element = driver.findElement(locator);
                if (element.isDisplayed() && element.getSize().getHeight() > 0) {
                    //  Elementi tam ortalamaya çalış
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", element);
                    Thread.sleep(500);
                    test.log(Status.INFO, "✅ Element görünür oldu: " + locator);
                    return element;
                }
            } catch (Exception ignored) {}

            //  Her scroll döngüsünde biraz aşağı kaydır
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 300);");
            scrolls++;
            try { Thread.sleep(400); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }

        test.log(Status.FAIL, "❌ Element görünür hale gelemedi: " + locator);
        throw new RuntimeException("Element scroll ile bulunamadı: " + locator);
    }
    protected void scrollAndHover(By locator) {
        try {
            WebElement element = scrollUntilVisible(locator, 15);  //  önce scroll yap
            Actions actions = new Actions(driver);
            actions.moveToElement(element).perform();              //  sonra hover yap
            test.log(Status.INFO, "✅ Scroll + Hover başarıyla yapıldı: " + locator);
        } catch (Exception e) {
            test.log(Status.FAIL, "❌ Scroll veya Hover başarısız: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}


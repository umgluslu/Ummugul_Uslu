package pages;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.BaseMethod;

import java.time.Duration;
import java.util.List;

public class QAJobsPage extends BaseMethod {
     private By locationDropdownVisible = By.xpath("(//span[contains(@class,'select2-container--default')])[2]");
    private By seeAllQaJobsButton = By.xpath("//a[contains(text(), 'See all QA jobs')]");
    private By departmentDropdownVisible = By.cssSelector("span.select2.select2-container--default");
    private By jobListId=By.xpath("//div[@class='position-list col-12 d-flex flex-wrap mt-5 pl-2 pr-2 pl-lg-0 pr-lg-0 pt-4']");
    private By jobListItems = By.cssSelector("div.position-list-item");
    private By positionTitle = By.className("position-title");
    private By positionDepartment = By.className("position-department");
    private By positionLocation = By.className("position-location");
    private By viewRoleButton=By.xpath("//div[@class='position-list col-12 d-flex flex-wrap mt-5 pl-2 pr-2 pl-lg-0 pr-lg-0 pt-4']/div[1]//a[@class='btn btn-navy rounded pt-2 pr-5 pb-2 pl-5']");
    private By jobCardTitle=By.xpath("//div[@id='jobs-list']/div[1]/div[@class='position-list-item-wrapper bg-light']/p[@class='position-title font-weight-bold']");
    public QAJobsPage(WebDriver driver, ExtentTest test) {
        super(driver, test);
    }
    public void preparePage() {
        acceptCookiesIfVisible(); // tüm işlemlerden önce engeli kaldır
    }
    public void clickSeeAllQaJobs() {
    int maxScrolls = 15;
    int scrolls = 0;
    boolean clicked = false;

    while (scrolls < maxScrolls) {
        try {
            WebElement button = driver.findElement(seeAllQaJobsButton);
            if (button.isDisplayed()) {
                button.click();
                test.log(Status.INFO, "✅ 'See all QA jobs' butonuna tıklandı.");
                clicked = true;
                break;
            }
        } catch (Exception e) {
            // Hata loglamak istersen:
            // test.xml.log(Status.INFO, "Buton henüz görünür değil: " + e.getMessage());
        }
        // Sayfayı aşağıya kaydır
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 200);");
        scrolls++;
        try { Thread.sleep(400); } catch (Exception ignored) {}
    }

    if (!clicked) {
        test.log(Status.FAIL, "❌ Buton görünür hale gelemedi, tıklanamadı.");
        throw new RuntimeException("See all QA jobs butonu görünür olmadı.");
    }
waitForSeconds(5);
}

    public void selectLocationViaMouseMovement() {
        try {

            Thread.sleep(2500); // Menü yüklemesi için bekleme

            WebElement menu = driver.findElement(By.className("select2-results__options"));
            Actions actions = new Actions(driver);

            boolean found = false;

            for (int attempt = 0; attempt < 20; attempt++) {
                List<WebElement> options = driver.findElements(By.cssSelector("li.select2-results__option"));

                for (WebElement option : options) {
                    String text = option.getText().trim();
                    actions.moveToElement(option).perform(); // 👈 Mouse ile üzerine git
                    Thread.sleep(300); // Hover yüklemeyi tetikler

                    if (text.equalsIgnoreCase("Istanbul, Turkiye")) {
                        actions.moveToElement(option).click().perform(); // 👈 Hover + Click
                        test.log(Status.INFO, "📍 'Istanbul, Turkey' mouse ile seçildi.");
                        found = true;
                        break;
                    }
                }

                if (found) break;

                // Menü aşağı doğru kaydırılmaya devam etsin
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollTop += 100", menu);
                Thread.sleep(400);
            }

            if (!found) {
                test.log(Status.FAIL, "❌ 'Istanbul, Turkey' mouse ile bulunamadı.");
                throw new RuntimeException("Seçenek hover ile görünmedi.");
            }

        } catch (Exception e) {
            test.log(Status.FAIL, "❌ Mouse hareketi ile lokasyon seçimi hatası: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    public void selectDepartmentViaMouseMovement() {

        try {
            Thread.sleep(1500); // Menü yüklemesi için bekleme

            WebElement menu = driver.findElement(By.className("select2-results__options"));
            Actions actions = new Actions(driver);

            boolean found = false;

            for (int attempt = 0; attempt < 20; attempt++) {
                List<WebElement> options = driver.findElements(By.cssSelector("li.select2-results__option"));

                for (WebElement option : options) {
                    String text = option.getText().trim();
                    actions.moveToElement(option).perform(); // 👈 Mouse ile üzerine git
                    Thread.sleep(300); // Hover yüklemeyi tetikler

                    if (text.equalsIgnoreCase("Quality Assurance")) {
                        actions.moveToElement(option).click().perform(); // 👈 Hover + Click
                        test.log(Status.INFO, "🏢 'Quality Assurance' mouse ile seçildi.");
                        found = true;
                        break;
                    }
                }

                if (found) break;

                // Menü aşağı doğru kaydırılmaya devam etsin
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollTop += 100", menu);
                Thread.sleep(400);
            }

            if (!found) {
                test.log(Status.FAIL, "❌ 'Quality Assurance' mouse ile bulunamadı.");
                throw new RuntimeException("Seçenek hover ile görünmedi.");
            }

        } catch (Exception e) {
            test.log(Status.FAIL, "❌ Mouse hareketi ile departman seçimi hatası: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    public void openLocationDropdownWithScrollAndJS() {
        int maxScrolls = 15;
        int scrolls = 0;
        boolean opened = false;
        waitForSeconds(2);
        while (scrolls < maxScrolls) {

            try {
                WebElement dropdown = driver.findElement(locationDropdownVisible);
                Thread.sleep(300);
                if (dropdown.isDisplayed()) {
                    // 1️⃣ Kutuyu merkeze al
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", dropdown);
                    Thread.sleep(300);

                    // 2️⃣ JavaScript ile Select2 kutusunu aç
                    ((JavascriptExecutor) driver).executeScript("$('#filter-by-location').select2('open');");
                    test.log(Status.INFO, "✅ Lokasyon dropdown JS ile açıldı.");
                    Thread.sleep(300);
                    // 3️⃣ Menü DOM'a yüklendi mi?
                    new WebDriverWait(driver, Duration.ofSeconds(10))
                            .until(ExpectedConditions.visibilityOfElementLocated(By.className("select2-results__options")));

                    opened = true;
                    break;
                }

            } catch (Exception e) {
                // test.xml.log(Status.INFO, "Deneme başarısız: " + e.getMessage());
            }

            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 200);");
            scrolls++;
            try { Thread.sleep(400); } catch (Exception ignored) {}
        }

        if (!opened) {
            test.log(Status.FAIL, "❌ Lokasyon kutusu scroll + JS ile açılamadı.");
            throw new RuntimeException("Dropdown görünür olmadı veya açılamadı.");
        }
    }
    public void openDepartmentDropdownWithScrollAndJS() {
        int maxScrolls = 15;
        int scrolls = 0;
        boolean opened = false;
        waitForSeconds(2);
        while (scrolls < maxScrolls) {
            try {
                WebElement dropdown = driver.findElement(departmentDropdownVisible);
                Thread.sleep(300);
                if (dropdown.isDisplayed()) {
                    // 1️⃣ Kutuyu merkeze al
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", dropdown);
                    Thread.sleep(300);

                    // 2️⃣ JavaScript ile Select2 kutusunu aç
                    ((JavascriptExecutor) driver).executeScript("$('#filter-by-department').select2('open');");
                    test.log(Status.INFO, "✅ Department dropdown JS ile açıldı.");
                    Thread.sleep(300);

                    // 3️⃣ Menü DOM'a yüklendi mi?
                    new WebDriverWait(driver, Duration.ofSeconds(10))
                            .until(ExpectedConditions.visibilityOfElementLocated(By.className("select2-results__options")));

                    opened = true;
                    break;
                }

            } catch (Exception e) {
                // test.xml.log(Status.INFO, "Deneme başarısız: " + e.getMessage());
            }

            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 200);");
            scrolls++;
            try { Thread.sleep(400); } catch (Exception ignored) {}
        }

        if (!opened) {
            test.log(Status.FAIL, "❌ Department kutusu scroll + JS ile açılamadı.");
            throw new RuntimeException("Dropdown görünür olmadı veya açılamadı.");
        }
    }

    public boolean isJobListVisible() {
        scrollUntilVisible(jobListItems,10);
        try {
            return isElementVisible(jobListItems, "İş ilanları listesi");
        } catch (Exception e) {
            test.log(Status.FAIL, "❌ İş ilanları listesi görünmedi: " + e.getMessage());
            return false;
        }
    }
    public boolean verifyFilteredJobResults() {
        boolean allValid = true;
        scrollUntilVisible(jobListId,10);
        try {
            List<WebElement> jobItems = driver.findElements(jobListId);

            if (jobItems.isEmpty()) {
                test.log(Status.FAIL, "❌ Hiç iş ilanı bulunamadı, liste boş.");
                return false;
            }

            for (WebElement job : jobItems) {
                String position = job.findElement(positionTitle).getText();
                String department = job.findElement(positionDepartment).getText();
                String location = job.findElement(positionLocation).getText();

                boolean valid = true;

                if (!position.contains("Quality Assurance")) {
                    test.log(Status.FAIL, "❌ Pozisyon hatalı: " + position);
                    valid = false;
                }
                if (!department.contains("Quality Assurance")) {
                    test.log(Status.FAIL, "❌ Departman hatalı: " + department);
                    valid = false;
                }
                if (!location.contains("Istanbul, Turkiye")) {
                    test.log(Status.FAIL, "❌ Lokasyon hatalı: " + location);
                    valid = false;
                }

                if (valid) {
                    test.log(Status.PASS, "✅ Uyumlu ilan: " + position + " | " + department + " | " + location);
                } else {
                    allValid = false;
                }
            }

        } catch (Exception e) {
            test.log(Status.FAIL, "❌ Liste kontrolü sırasında hata: " + e.getMessage());
            return false;
        }

        return allValid;
    }
    public void clickFirstViewRoleButton() {
        try {
            //  İlk job başlığının olduğu alana scroll + hover yap
            scrollAndHover(jobCardTitle); // ️ BasePage metodu
            //  Hover sonrası görünür olan "View Role" butonunu tıkla
            WebElement button = waitForElement(viewRoleButton, 10);
            button.click();

            test.log(Status.INFO, "✅ 'View Role' butonuna tıklandı.");

        } catch (Exception e) {
            test.log(Status.FAIL, "❌ 'View Role' butonuna tıklanamadı: " + e.getMessage());
            throw new RuntimeException(e);
        }
        waitForSeconds(10);
    }
    public boolean switchToLeverTabAndVerifyUrl() {
        String originalTab = driver.getWindowHandle();
        boolean switched = false;

        for (String tab : driver.getWindowHandles()) {
            if (!tab.equals(originalTab)) {
                driver.switchTo().window(tab);
                switched = true;
                break;
            }
        }

        if (!switched) {
            test.log(Status.FAIL, "❌ Yeni sekmeye geçilemedi.");
            return false;
        }

        String url = driver.getCurrentUrl();
        if (url.startsWith("https://jobs.lever.co/useinsider/")) {
            test.log(Status.PASS, "✅ Lever sayfasına başarıyla yönlendirildi → " + url);
            return true;
        } else {
            test.log(Status.FAIL, "❌ URL Lever sayfası değil → " + url);
            return false;
        }
    }


}

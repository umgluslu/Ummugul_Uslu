package pages;

import utils.BaseMethod;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CareersPage extends BaseMethod {

    private By locationsSection = By.id("career-our-location");
    private By teamsSection = By.xpath("//div[@class='col-12 d-flex flex-wrap p-0 career-load-more']");
    private By lifeAtInsiderSection = By.xpath("//div[@class='elementor-widget-wrap elementor-element-populated e-swiper-container']/div/div[@class='elementor-widget-container']/h2");

    public CareersPage(WebDriver driver, ExtentTest test) {
        super(driver, test);
    }

    public boolean isCareersPageLoaded() {
        String url = driver.getCurrentUrl();
        test.log(Status.INFO, "Careers page URL: " + url);
        return url.contains("/careers");
    }

    public boolean isLocationsVisible() {
        scrollToBottomThenScrollUpUntilVisible(locationsSection,10);
        return isElementVisible(locationsSection, "Locations bölümü");
    }

public boolean isTeamsVisible() {
    try {
        scrollToBottomThenScrollUpUntilVisible(teamsSection,10);

        return isElementVisible(teamsSection, "Teams bölümü");
    } catch (Exception e) {
        test.log(Status.FAIL, "❌ Teams bloğu kontrolünde hata: " + e.getMessage());
        return false;
    }
}

    public boolean isLifeAtInsiderVisible() {

        try {
            scrollToBottomThenScrollUpUntilVisible(lifeAtInsiderSection,10);
            return isDisplayed(lifeAtInsiderSection, "Life at Insider bölümü");
        } catch (Exception e) {
            test.log(Status.FAIL, "InsiderLıfe bloğu kontrolünde hata: \" + e.getMessage()");
            return false;
        }
    }
}
package tests;

import pages.CareersPage;
import pages.QAJobsPage;
import testBase.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;

public class InsiderTest extends BaseTest {

    HomePage homePage;
    CareersPage careersPage;

    @Test
    public void test01_homepageLoads() {
        test = extent.createTest("01 - Ana sayfa yüklendi mi?");
        driver.get("https://useinsider.com/");
        homePage = new HomePage(driver, test);
        Assert.assertTrue(homePage.isHomePageLoaded(), "❌ Ana sayfa yüklenemedi");
    }

    @Test
    public void test02_navigateToCareersPage() {
        test = extent.createTest("02 - Company menüsünden Careers sayfasına git");
        driver.get("https://useinsider.com/");
        homePage = new HomePage(driver, test);
        homePage.hoverOverCompanyMenu();
        homePage.clickCareersLink();
        careersPage = new CareersPage(driver, test);
        Assert.assertTrue(careersPage.isCareersPageLoaded(), "❌ Careers sayfası yüklenemedi");
    }

    @Test
    public void test03_checkCareersPageBlocks() {
        test = extent.createTest("03 - Careers sayfasındaki bloklar kontrol ediliyor");
        System.out.println("Driver null mu? " + (driver == null));
        driver.get("https://useinsider.com/");
        homePage = new HomePage(driver, test);
        homePage.clickCompany();
        homePage.clickCareersLink();
        careersPage = new CareersPage(driver, test);
        Assert.assertTrue(careersPage.isLocationsVisible(), "❌ Locations bölümü görünmüyor");
       Assert.assertTrue(careersPage.isTeamsVisible(), "❌ Teams bölümü görünmüyor");
       Assert.assertTrue(careersPage.isLifeAtInsiderVisible(), "❌ Life at Insider bölümü görünmüyor");
    }
    @Test
    public void test04_filterQaJobsInIstanbul() {
        test = extent.createTest("04 - QA iş ilanlarını filtrele (Istanbul & QA)");
        System.out.println("🔍 Testteki driver null mu?: " + (driver == null));
        driver.get("https://useinsider.com/careers/quality-assurance/");
        QAJobsPage qaJobsPagePage = new QAJobsPage(driver, test);
        qaJobsPagePage.preparePage();
        qaJobsPagePage.clickSeeAllQaJobs();
        qaJobsPagePage.openLocationDropdownWithScrollAndJS();
        qaJobsPagePage.selectLocationViaMouseMovement();
        qaJobsPagePage.openDepartmentDropdownWithScrollAndJS();
        qaJobsPagePage.selectDepartmentViaMouseMovement();
        Assert.assertTrue(qaJobsPagePage.verifyFilteredJobResults());
    }
    @Test
    public void test05_clickViewRoleAndVerifyRedirect() {
        test = extent.createTest("04 - QA iş ilanlarını filtrele (Istanbul & QA)");
        System.out.println("🔍 Testteki driver null mu?: " + (driver == null));
        driver.get("https://useinsider.com/careers/quality-assurance/");
        QAJobsPage qaJobsPagePage = new QAJobsPage(driver, test);
        qaJobsPagePage.preparePage();
        qaJobsPagePage.clickSeeAllQaJobs();
        qaJobsPagePage.openLocationDropdownWithScrollAndJS();
        qaJobsPagePage.selectLocationViaMouseMovement();
        qaJobsPagePage.openDepartmentDropdownWithScrollAndJS();
        qaJobsPagePage.selectDepartmentViaMouseMovement();
        qaJobsPagePage.clickFirstViewRoleButton();
        Assert.assertTrue(qaJobsPagePage.switchToLeverTabAndVerifyUrl());

    }

}










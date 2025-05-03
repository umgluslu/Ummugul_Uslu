package testBase;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import utils.DriverFactory;
import utils.ExtentReportManager;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;


public class BaseTest {

    protected WebDriver driver;
    protected ExtentReports extent;
    protected ExtentTest test;

    @BeforeMethod
    public void setUpBase() {
        System.out.println(" setUpBase çalıştı");
        extent = ExtentReportManager.getInstance();
        driver = DriverFactory.getDriver();
    }

    @AfterMethod
    public void tearDownBase() {
        extent.flush();
        DriverFactory.quitDriver();
    }
}

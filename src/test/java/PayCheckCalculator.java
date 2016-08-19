import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

public class PayCheckCalculator {

    public WebDriver driver;

    @BeforeClass(groups = {"regression", "p1"})
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10L, TimeUnit.SECONDS);

    }

    @Test(groups = {"regression", "p1"})
    public void NetPay() throws Exception {

        driver.get("http://www.paycheckcity.com/calculator/salary/");
        driver.findElement(By.id("calcDate")).clear();
        driver.findElement(By.id("calcDate")).sendKeys("08/31/2016");

        driver.findElement(By.id("state")).clear();
        driver.findElement(By.id("state")).sendKeys("California");
        driver.findElement(By.id("generalInformation.grossPayAmount")).clear();
        driver.findElement(By.id("generalInformation.grossPayAmount")).sendKeys("10000");
        driver.findElement(By.id("generalInformation.grossPayMethodType")).clear();
        driver.findElement(By.id("generalInformation.grossPayMethodType")).sendKeys("Pay Per Period");
        driver.findElement(By.id("generalInformation.payFrequencyType")).clear();
        driver.findElement(By.id("generalInformation.payFrequencyType")).sendKeys("Monthly");
        driver.findElement(By.id("generalInformation.exemptFederal1")).click();
        driver.findElement(By.id("calculate")).click();

        new WebDriverWait(driver,10L).until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.xpath(".//span[text()='Net Pay']/following-sibling::span")), "$8,387.79"));

        WebElement result = driver.findElement(By.xpath(".//span[text()='Net Pay']/following-sibling::span"));
        String actualResult = result.getText();

        String expectedResult = "$8,387.79";

        Assert.assertEquals(actualResult, expectedResult);

    }

    @Test(groups = {"regression", "p1"})
    public void GrossPay() throws Exception {

        driver.get("http://www.paycheckcity.com/calculator/hourly/");

        driver.findElement(By.id("calcType.rates0.payRate")).clear();
        driver.findElement(By.id("calcType.rates0.payRate")).sendKeys("50");
        driver.findElement(By.id("calcType.rates0.hours")).clear();
        driver.findElement(By.id("calcType.rates0.hours")).sendKeys("160");
        driver.findElement(By.id("addRate")).click();
        driver.findElement(By.id("calcType.rates1.payRate")).clear();
        driver.findElement(By.id("calcType.rates1.payRate")).sendKeys("80");
        driver.findElement(By.id("calcType.rates1.hours")).clear();
        driver.findElement(By.id("calcType.rates1.hours")).sendKeys("8");
        driver.findElement(By.id("generalInformation.grossPayAmount")).click();

        WebElement result = driver.findElement(By.id("generalInformation.grossPayAmount"));
        String actualResult = result.getAttribute("value");

        String expectedResult = "8640";

        Assert.assertEquals(actualResult, expectedResult);

    }

    @AfterClass(groups = {"regression", "p1"})

    public void tearDown() {

        driver.quit();
    }
}

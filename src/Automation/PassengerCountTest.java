package Automation;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class PassengerCountTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setup() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.manage().window().maximize();
        driver.get("https://www.spicejet.com/");
        Thread.sleep(5000);
    }

    @Test
    public void updatePassengerCount() throws InterruptedException {
        // === Click on passenger selector ===
        WebElement passengerDropdown = driver.findElement(By.xpath("//div[@data-testid='home-page-travellers']"));
        passengerDropdown.click();
        Thread.sleep(1000);

        // === Add Adults (by default 1 is selected) ===
        WebElement adultPlus = driver.findElement(By.xpath("//div[contains(text(),'Adult')]/following::div[@data-testid='Adult-testID-plus-one-cta']"));
        adultPlus.click(); // now 2 Adults
        Thread.sleep(500);

        // === Add 1 Child ===
        WebElement childPlus = driver.findElement(By.xpath("//div[contains(text(),'Children')]/following::div[@data-testid='Children-testID-plus-one-cta']"));
        childPlus.click(); // now 1 Child
        Thread.sleep(500);

        // === Add 1 Infant ===
        WebElement infantPlus = driver.findElement(By.xpath("//div[contains(text(),'Infant')]/following::div[@data-testid='Infant-testID-plus-one-cta']"));
        infantPlus.click(); // now 1 Infant
        Thread.sleep(500);

        // === Click Done or outside area to close the selector ===
        WebElement doneButton = driver.findElement(By.xpath("//div[@data-testid='home-page-travellers-done-cta']"));
        doneButton.click();
        Thread.sleep(1000);

        // === Check the passenger summary text ===
        WebElement summary = driver.findElement(By.xpath("//div[@data-testid='home-page-travellers']"));
        String summaryText = summary.getText().trim();
        System.out.println("👥 Passenger Summary: " + summaryText);

        // === Assert that the summary includes all selected categories ===
        Assert.assertTrue(summaryText.contains("2 Adults") &&
                          summaryText.contains("1 Child") &&
                          summaryText.contains("1 Infant"),
                "❌ Passenger summary does not reflect the selected counts.");
    }


    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}

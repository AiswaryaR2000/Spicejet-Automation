package Automation;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class RoundTripPassengerTest {

    WebDriver driver;

    @BeforeClass
    public void setup() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.spicejet.com/");
        Thread.sleep(5000);
    }

    @Test
    public void searchFlight() throws InterruptedException {

        // === SELECT ROUND TRIP ===
        WebElement roundTripOption = driver.findElement(By.xpath("//div[@data-testid='round-trip-radio-button']"));
        roundTripOption.click();
        Thread.sleep(1000);

        // === FROM ===
        WebElement fromField = driver.findElement(By.xpath("//div[@data-testid='to-testID-origin']//input[@type='text']"));
        fromField.click();
        fromField.sendKeys("Hyd");
        Thread.sleep(2000);
        

        // === TO ===
        WebElement toField = driver.findElement(By.xpath("//div[@data-testid='to-testID-destination']//input[@type='text']"));
        toField.click();
        toField.sendKeys("Del");
        Thread.sleep(2000);
        

        // === DEPARTURE DATE (20) ===
        List<WebElement> departureDates = driver.findElements(By.xpath("//div[contains(@data-testid, 'calendar-day')]"));
        driver.findElement(By.xpath("//div[@data-testid='undefined-calendar-day-20' and ancestor::div[contains(@data-testid,'month-April')]]")).click();
        Thread.sleep(1500);

        // === RETURN DATE (25) ===
        List<WebElement> returnDates = driver.findElements(By.xpath("//div[contains(@data-testid, 'calendar-day')]"));
        driver.findElement(By.xpath("//div[@data-testid='undefined-calendar-day-25' and ancestor::div[contains(@data-testid,'month-May')]]")).click();


        // === SELECT PASSENGERS ===
        WebElement passengersToggle = driver.findElement(By.xpath("//div[@data-testid='home-page-travellers']"));
        passengersToggle.click();
        Thread.sleep(1000);

        // Add 1 more adult (default is 1)
        driver.findElement(By.xpath("//div[@data-testid='Adult-testID-plus-one-cta']")).click();
        // Add 1 child
        driver.findElement(By.xpath("//div[@data-testid='Children-testID-plus-one-cta']")).click();

        // Click done/close passengers
        driver.findElement(By.xpath("//div[@data-testid='home-page-travellers-done-cta']")).click();
        Thread.sleep(1000);

        // === CLICK SEARCH ===
        driver.findElement(By.xpath("//div[@data-testid='home-page-flight-cta']")).click();

        // === Wait and check ===
        Thread.sleep(6000);
        String pageSource = driver.getPageSource();
        if (pageSource.contains("Flights") || pageSource.contains("Sort By")) {
            System.out.println("Round trip with passengers search successful!");
        } else {
            System.out.println(" Flight results not loaded.");
        }

        // === SCREENSHOT CAPTURE ===
        TakesScreenshot ts = (TakesScreenshot) driver;
        File srcFile = ts.getScreenshotAs(OutputType.FILE);

        String filePath = System.getProperty("user.dir") + "/screenshots/round_trip_passenger_result.png";

        try {
            FileUtils.copyFile(srcFile, new File(filePath));
            System.out.println("Screenshot saved to: " + filePath);
        } catch (IOException e) {
            System.out.println(" Failed to save screenshot: " + e.getMessage());
        }
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}

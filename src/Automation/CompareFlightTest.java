package Automation;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;
import java.util.List;

public class CompareFlightTest {

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
    public void extractFlightNumbersOnly() throws InterruptedException {
        // === FROM ===
        WebElement fromField = driver.findElement(By.xpath("//div[@data-testid='to-testID-origin']//input[@type='text']"));
        fromField.click();
        fromField.sendKeys("Chen");
       

        // === TO ===
        WebElement toField = driver.findElement(By.xpath("//div[@data-testid='to-testID-destination']//input[@type='text']"));
        toField.click();
        toField.sendKeys("Del");
        Thread.sleep(8000);

        // === SELECT May 2 ===
        driver.findElement(By.xpath("//div[@data-testid='undefined-calendar-day-2' and ancestor::div[contains(@data-testid,'month-May')]]")).click();
        Thread.sleep(3000);

        // === CLICK SEARCH ===
        driver.findElement(By.xpath("//div[@data-testid='home-page-flight-cta']")).click();
        Thread.sleep(8000); // wait for results

        System.out.println(" Flight numbers for May 2:\n");

        // === Loop through flight cards ===
        List<WebElement> flightBlocks = driver.findElements(By.xpath("//*[@id='onward-flight-container']/div/div/div"));

        if (flightBlocks.size() == 0) {
            System.out.println(" No flight cards found.");
            return;
        }

        for (int i = 0; i < flightBlocks.size(); i++) {
            try {
                WebElement flight = flightBlocks.get(i).findElement(By.xpath(".//div[1]/div[4]/div[1]"));
                String flightNumber = flight.getText().trim();
                System.out.println( flightNumber);
            } catch (Exception e) {
                System.out.println("Skipped one card – flight number not found.");
            }
        }

        System.out.println("\n Total flights captured: " + flightBlocks.size());
    }



    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}

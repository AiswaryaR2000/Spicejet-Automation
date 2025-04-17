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

public class FlightSearchTest {

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
        // From city
    	// === FROM ===
        WebElement fromField = driver.findElement(By.xpath("//div[@data-testid='to-testID-origin']//input[@type='text']"));
        fromField.click();
        fromField.sendKeys("Beng");
        Thread.sleep(2000);
        //driver.findElement(By.xpath("//div[@data-testid='station-BLR']")).click();

        // === TO ===
        WebElement toField = driver.findElement(By.xpath("//div[@data-testid='to-testID-destination']//input[@type='text']"));
        toField.click();
        toField.sendKeys("Del");
        Thread.sleep(2000);
        //driver.findElement(By.xpath("//div[@data-testid='station-DEL']")).click();


     // Get all date elements
        List<WebElement> dates = driver.findElements(By.xpath("//div[contains(@data-testid, 'calendar-day')]"));

        boolean dateClicked = false;

        for (WebElement date : dates) {
            String dateText = date.getText().trim(); // e.g., "30"

            if (date.isDisplayed() && date.isEnabled() && dateText.equals("30")) {
                try {
                    date.click();
                    System.out.println("Clicked on date: " + dateText);
                    dateClicked = true;
                    break;
                } catch (Exception e) {
                    System.out.println("Failed to click on April 30: " + e.getMessage());
                }
            }
        }

        if (!dateClicked) {
            System.out.println("April 30 not found or not clickable.");
        }

        // === CLICK SEARCH ===
        Thread.sleep(2000);
        driver.findElement(By.xpath("//div[@data-testid='home-page-flight-cta']")).click();


        // Wait and check
        Thread.sleep(6000);
        String pageSource = driver.getPageSource();
        if (pageSource.contains("Flights") || pageSource.contains("Sort By")) {
            System.out.println("✅ Flight results loaded successfully!");
        } else {
            System.out.println("❌ Flight results not loaded.");
        }
        
     // === SCREENSHOT CAPTURE ===
        TakesScreenshot ts = (TakesScreenshot) driver;
        File srcFile = ts.getScreenshotAs(OutputType.FILE);

        // Folder "screenshots" will be created automatically
        String filePath = System.getProperty("user.dir") + "/screenshots/result_page.png";

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

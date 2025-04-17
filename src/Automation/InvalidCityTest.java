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

public class InvalidCityTest {

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
    public void testInvalidToCitySuggestion() throws InterruptedException, IOException {
        // === FROM with Valid Input ===
        WebElement fromField = driver.findElement(By.xpath("//div[@data-testid='to-testID-origin']//input[@type='text']"));
        fromField.click();
        fromField.sendKeys("Chen");
        

        // === TO with Invalid Input ===
        WebElement toField = driver.findElement(By.xpath("//div[@data-testid='to-testID-destination']//input[@type='text']"));
        toField.click();
        toField.clear();
        toField.sendKeys("123");
        Thread.sleep(2000);

        // === Check for "No destination found" text
        List<WebElement> noDestMsg = driver.findElements(By.xpath("//*[contains(text(),'No destinations found')]"));

        if (!noDestMsg.isEmpty()) {
            System.out.println("✅ 'No destinations found' message appeared for invalid input.");
        } else {
            System.out.println("❌ 'No destinations found' message did not appear — suggestion list may have defaulted.");
        }

        // === Capture screenshot
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File destination = new File(System.getProperty("user.dir") + "/screenshots/invalid_to_city.png");
        FileUtils.copyFile(screenshot, destination);
        System.out.println("📸 Screenshot saved: " + destination.getAbsolutePath());

        // === Assert that no valid destination was selectable
        Assert.assertFalse(noDestMsg.isEmpty(), "❌ Suggestion list should not have matched any valid destination for '123'");
    }


    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}

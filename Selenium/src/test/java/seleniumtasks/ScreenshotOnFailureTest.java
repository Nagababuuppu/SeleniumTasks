package seleniumtasks;

import org.apache.commons.io.FileUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

//import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
//import java.nio.file.Files;
import java.util.concurrent.TimeUnit;

public class ScreenshotOnFailureTest {

    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\user\\OneDrive\\Desktop\\New folder\\New folder (2)\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    	driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
    }

   
    @Test
    public void validLoginTest() {
        login("John Doe", "ThisIsNotAPassword");
        Assert.assertEquals(driver.getCurrentUrl(),"https://katalon-demo-cura.herokuapp.com/#appointment");
    }

    @Test
    public void invalidLoginTest() {
        login("Joh", "ThisIsNot");
        Assert.assertEquals(driver.getCurrentUrl(),"https://katalon-demo-cura.herokuapp.com/#appointment");
    }

    @Test
    public void emptyFieldsTest() {
        login("", "");
        Assert.assertEquals(driver.getCurrentUrl(),"https://katalon-demo-cura.herokuapp.com/#appointment");
    }
    private void login(String username, String password) {
        driver.get("https://katalon-demo-cura.herokuapp.com/profile.php#login");
        driver.findElement(By.id("txt-username")).sendKeys(username);
        driver.findElement(By.id("txt-password")).sendKeys(password);
        driver.findElement(By.id("btn-login")).click();
    }
    @AfterMethod
    public void tearDown(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE) {
        	TakesScreenshot screenshotDriver = (TakesScreenshot) driver;
            File screenshotFile = screenshotDriver.getScreenshotAs(OutputType.FILE);
            File destinationFile = new File(result.getName()+ ".png");
      FileUtils.copyFile(screenshotFile, destinationFile);
        }
        driver.quit();    }
   

}

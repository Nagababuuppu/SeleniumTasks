package seleniumtasks;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class Brokenlinkstask1 {

    @Test 
    public void main() throws Exception{

        System.setProperty("webdriver.chrome.driver", "C:\\Users\\user\\Downloads\\chromedriver-win64 (1)\\chromedriver-win64\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();

        driver.manage().window().maximize();

        driver.get("http://www.deadlinkcity.com/");

        driver.manage().timeouts().pageLoadTimeout(20,TimeUnit.SECONDS);

        WebDriverWait wait = new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("a")));

        List<WebElement> links = driver.findElements(By.tagName("a"));

        System.out.println(links.size());

        for (WebElement link : links) {

            String linkURL = link.getAttribute("href");

            try {
                @SuppressWarnings("deprecation")
				URL url = new URL(linkURL);
                URLConnection urlConnection = url.openConnection();
                HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();

                if (httpURLConnection.getResponseCode() > 200) {
                    System.out.println(linkURL + " - " + httpURLConnection.getResponseMessage());
                }

                httpURLConnection.disconnect();
            } catch(MalformedURLException e) {
                System.out.println(linkURL + " - Malformed URL: " + e.getMessage());
            } catch (Exception e) {
                System.out.println(linkURL + " - Error: " + e.getMessage());
            }
        }

        driver.quit();
    }
}

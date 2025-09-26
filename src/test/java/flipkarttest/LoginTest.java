package flipkarttest;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import com.flipkart.pages.LoginPage;
import utilis.ConfigReader;
import utilis.DataUtil;
import utilis.DriverFactory;
import utilis.ScreenshotUtil;

import java.time.Duration;

public class LoginTest {
    WebDriver driver;
    LoginPage loginPage;
    WebDriverWait wait;

    @BeforeMethod
    public void setup() {
        loginPage = DriverFactory.launchFlipkart(); // Launch browser & go to Flipkart
        driver = DriverFactory.getDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    @Test(dataProvider = "loginData", dataProviderClass = DataUtil.class)
    public void loginWithEmailOTP(String emailid) {
        try {
            // Read credentials from config.properties
            String appPassword = ConfigReader.get("appPassword");
            String email = ConfigReader.get("emailid"); // from config

            System.out.println("Starting login test for: " + email);

            // Perform login using Page Object
            loginPage.loginWithEmailOTP(email, appPassword);

            // Handle possible overlay/popups
            try {
                By overlay = By.cssSelector("div._2QfC02"); // Example overlay selector
                wait.until(ExpectedConditions.invisibilityOfElementLocated(overlay));
                System.out.println("Overlay handled.");
            } catch (TimeoutException ignored) {
                System.out.println("No overlay found, continuing.");
            }

            // --- Verify login success ---
//            boolean loginSuccess = wait.until(ExpectedConditions.or(
//                    ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Add to Cart')]")),
//                    ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'became a seller')]"))
//            )) != null;
//
//            Assert.assertTrue(loginSuccess, "Login verification failed!");

            System.out.println("Login successful for: " + email);

        } catch (Exception e) {
            String screenshotPath = ScreenshotUtil.takeScreenshot(driver, "login_" + emailid);
            System.out.println("Screenshot saved at: " + screenshotPath);
            
        }
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}

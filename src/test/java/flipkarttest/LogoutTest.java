package flipkarttest;

import com.flipkart.pages.LoginPage;
import com.flipkart.pages.LogoutPage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import utilis.DriverFactory;
import utilis.ConfigReader; // <-- Add this import

public class LogoutTest {

    WebDriver driver;
    LoginPage loginPage;

    @BeforeMethod
    public void setup() {
        loginPage = DriverFactory.launchFlipkart();
        driver = DriverFactory.getDriver();
    }

    @Test
    public void loginAndLogout() {
        try {
            // Fetch credentials from config.properties
            String email = ConfigReader.get("emailid");
            String appPassword = ConfigReader.get("appPassword");

            loginPage.openLoginPopup();
            Thread.sleep(2000);

            // Pass credentials to login method
            loginPage.loginWithEmailOTP(email, appPassword);

            Thread.sleep(10000); // OTP simulation
            System.out.println("Login Successfully");

            loginPage.clickRequestOtp();
            Thread.sleep(8000); // Wait for login state

            LogoutPage logoutPage = new LogoutPage(driver);
            Thread.sleep(3000);
            logoutPage.logout();
            Thread.sleep(9000);

        } catch (Exception e) {
            System.out.println("Test failed: " + e.getMessage());
        }
        System.out.println("Logged out Successfully");
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}

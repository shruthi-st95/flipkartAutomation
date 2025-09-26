package flipkarttest;

import com.flipkart.pages.ProductPage;
import com.flipkart.pages.AddToCartPage;
import com.flipkart.pages.SearchPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import utilis.DataUtil;
import utilis.DriverFactory;
import utilis.ScreenshotUtil;

import java.time.Duration;

public class ProductTest {
    WebDriver driver;
    ProductPage productPage;
    SearchPage searchPage;
    AddToCartPage addToCartPage;
    WebDriverWait wait;

    @BeforeMethod
    public void setup() {
        DriverFactory.initDriver();
        driver = DriverFactory.getDriver();
        driver.get("https://www.flipkart.com");

        productPage = new ProductPage(driver);
        searchPage = new SearchPage(driver);
        addToCartPage = new AddToCartPage(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @Test(dataProvider = "product", dataProviderClass = DataUtil.class)
    public void endToEndTest(String productName, String mobileNumber) {
        try {
            // Step 1: Search for the product
            searchPage.searchProduct(productName);

            // Step 2: Select product from results
            addToCartPage.selectedproduct();

            // Step 3: Switch to the product details tab
            for (String window : driver.getWindowHandles()) {
                driver.switchTo().window(window);
            }

            // Step 4: Count total links on the product page
            int totalLinks = productPage.countAllLinksOnPage();
            System.out.println("Verified: Product page has " + totalLinks + " links.");

            // Step 5: Click Buy Now
            productPage.clickBuyNowButton();

            // Step 6: Login with mobile number and wait for OTP auto-fill
            productPage.loginWithMobile(mobileNumber);
            Thread.sleep(9000);  // Allow Flipkart to load next page

            // Step 7: Select delivery address
            productPage.clickDeliverButton();

            // Step 8: Hover over price and fee details
            productPage.hoverOverPriceDetails();
            Thread.sleep(3000);

            // Step 9: Click Continue
            productPage.clickContinueButton();
            Thread.sleep(3000);

        } catch (Exception e) {
            ScreenshotUtil.takeScreenshot(driver, "failure_" + productName);
            Assert.fail("End-to-End test failed: " + e.getMessage());
        }
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}

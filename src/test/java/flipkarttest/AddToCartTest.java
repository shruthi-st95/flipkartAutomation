package flipkarttest;

import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import com.flipkart.pages.AddToCartPage;
import utilis.DataUtil;
import utilis.DriverFactory;
import utilis.ScreenshotUtil;

public class AddToCartTest {
    WebDriver driver;
    AddToCartPage add;

    @BeforeMethod
    public void setup() {
        DriverFactory.initDriver();
        driver = DriverFactory.getDriver();
        driver.get("https://www.flipkart.com");
        add = new AddToCartPage(driver);
    }

    @Test(dataProvider = "searchData", dataProviderClass = DataUtil.class)
    public void searchDifferentProducts(String productName) {
        try {
            add.searchProduct(productName);

            Assert.assertTrue(driver.getTitle().toLowerCase().contains(productName.toLowerCase()),
                    "Search page title doesn't contain product name: " + productName);
            Thread.sleep(2000);

            add.selectedproduct();
            Thread.sleep(2000);

            String parentWindow = driver.getWindowHandle();
            Set<String> allWindows = driver.getWindowHandles();

            for (String window : allWindows) {
                if (!window.equals(parentWindow)) {
                    driver.switchTo().window(window);
                    break;
                }
            }

            add.clickAddToCart();
            Thread.sleep(2000);

        } catch (Exception e) {
            ScreenshotUtil.takeScreenshot(driver, "search_" + productName);
            Assert.fail("Search/Add to cart failed for " + productName + ": " + e.getMessage());
        }
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}

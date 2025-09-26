package flipkarttest;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import com.flipkart.pages.SearchPage;
import utilis.DataUtil;
import utilis.DriverFactory;
import utilis.ScreenshotUtil;

public class SearchTest {
    WebDriver driver;
    SearchPage searchPage;

    @BeforeMethod
    public void setup() {
        DriverFactory.initDriver();
        driver = DriverFactory.getDriver();
        driver.get("https://www.flipkart.com");
        searchPage = new SearchPage(driver);
    }

    @Test(dataProvider = "searchData", dataProviderClass = DataUtil.class)
    public void searchDifferentProducts(String productName) {
        try {
            searchPage.searchProduct(productName);
            Assert.assertTrue(driver.getTitle().toLowerCase().contains(productName.toLowerCase()), "Search title mismatch for: " + productName);
            
            searchPage.clickSortBy();   
            Thread.sleep(4000);
            searchPage.clickFirstProduct();
            Thread.sleep(5000);
            
        } catch (Exception e) {
            ScreenshotUtil.takeScreenshot(driver, "search_" + productName);
            Assert.fail("Search failed for " + productName + ": " + e.getMessage());
        }
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}

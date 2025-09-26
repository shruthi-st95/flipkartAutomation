package utilis;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.flipkart.pages.LoginPage;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {
	public static WebDriver driver;                 
	public static LoginPage launchFlipkart() {             //loginpage returntype
	    initDriver();  // Open browser                     //method for browsersetup
	    WebDriver driver = getDriver();                    //get instance 
	    driver.get("https://www.flipkart.com");
	    return new LoginPage(driver);  // Return LoginPage object directly
	}

    public static void initDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    public static WebDriver getDriver() {       //to get driver instance we can user driver
        return driver;
    }

    public static void quitDriver() {                    //to close driver
        if (driver != null) {
            driver.quit();
            
        }
    }
}



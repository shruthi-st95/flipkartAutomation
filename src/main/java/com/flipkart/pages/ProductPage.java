package com.flipkart.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;

public class ProductPage {
    WebDriver driver;
    WebDriverWait wait;
    Actions actions;

    // Constructor
    public ProductPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.actions = new Actions(driver);
        PageFactory.initElements(driver, this);
    }

    // Locators
    @FindBy(xpath = "//*[@class='QqFHMw vslbG+ _3Yl67G _7Pd1Fp']")
    WebElement buyNowButton;

    @FindBy(xpath = "//*[@id='container']/div/div[2]/div/div[1]/div[1]/div/div/div/div/div[1]/div/form/div[1]/input")
    WebElement mobileNumberInput;

    @FindBy(xpath = "//*[@id='container']/div/div[2]/div/div[1]/div[1]/div/div/div/div/div[1]/div/form/div[3]/button")
    WebElement continueBtn;

    @FindBy(xpath = "//button[@class='QqFHMw YhpBe+ _7Pd1Fp']")
    WebElement loginButton;

    @FindBy(xpath = "//*[@id='info-icon-Price1item']/div/div/img[2]")
    WebElement priceDetails;

    @FindBy(xpath = "//*[@class='LcLcvv'][2]")
    WebElement addItems;

    @FindBy(xpath = "//button[@class='QqFHMw FA45gW _7Pd1Fp']")
    WebElement deliverButton;

    @FindBy(xpath = "//button[contains(@class, 'QqFHMw VuSC8m _7Pd1Fp')]")
    WebElement continueClick;

    @FindBy(xpath = "//*[@id='container']/div/div[2]/div/div[1]/div[1]/div/div/div/div/div[1]/div/form/div[2]/input")
    WebElement otpInput;

    // Action Methods

    public void clickBuyNowButton() {
        wait.until(ExpectedConditions.elementToBeClickable(buyNowButton)).click();
    }

    public void loginWithMobile(String mobileNumber) throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(mobileNumberInput)).sendKeys(mobileNumber);
        continueBtn.click();

        // Wait up to 60 seconds for OTP to be auto-filled (6-digit)
        WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(60));
        longWait.until(driver -> {
            String otpValue = otpInput.getAttribute("value");
            return otpValue != null && otpValue.matches("\\d{6}");
        });

        System.out.println("Detected 6-digit OTP, clicking Login...");
        loginButton.click();
    }

    public void hoverOverPriceDetails() throws InterruptedException {
        actions.moveToElement(priceDetails).perform();
        Thread.sleep(2000);
    }

    public void clickDeliverButton() {
        wait.until(ExpectedConditions.elementToBeClickable(deliverButton)).click();
    }

    public void clickContinueButton() {
        wait.until(ExpectedConditions.elementToBeClickable(continueClick)).click();
    }

    //  Count all links on the product page
    public int countAllLinksOnPage() {
        List<WebElement> allLinks = driver.findElements(By.tagName("a"));
        int totalLinks = allLinks.size();
        System.out.println("Total number of links on the product page: " + totalLinks);
        return totalLinks;
    }
}

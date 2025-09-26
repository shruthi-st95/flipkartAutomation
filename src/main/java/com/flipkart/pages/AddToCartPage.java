package com.flipkart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AddToCartPage {
    WebDriver driver;
    WebDriverWait wait;

    By searchBox = By.name("q");
    By searchButton = By.cssSelector("button[type='submit']");

    
    By selectedProduct = By.xpath("//a[contains(@class,'wjcEIp') and contains(text(),'Dynamic Bass')]");
    By addToCartBtn = By.xpath("//button[contains(@class,'QqFHMw') and contains(text(),'Add to cart')]");

    public AddToCartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void searchProduct(String productName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchBox)).sendKeys(productName);
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
    }

    public void selectedproduct() {
        wait.until(ExpectedConditions.elementToBeClickable(selectedProduct)).click();
    }

    public void clickAddToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(addToCartBtn)).click();
    }
}

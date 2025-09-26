package com.flipkart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class SearchPage {   
    WebDriver driver;          //web driver instamce
    WebDriverWait wait;        //wait instance

    // Locators
    By searchBox = By.name("q"); 
    By searchButton = By.cssSelector("button[type='submit']");
    By sortBy = By.xpath("//*[@id='container']/div/div[3]/div[1]/div[2]/div[1]/div/div/div[2]/div[3]");
    By firstProduct = By.xpath("//a[@class='wjcEIp'][text()='F FERONS Ws-065 Tune pro Dynamic Bass stereo audio Led ...']");

    public SearchPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void searchProduct(String productName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchBox)).sendKeys(productName);
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
    }

    public void clickSortBy() {
        wait.until(ExpectedConditions.elementToBeClickable(sortBy)).click();
    }

    public void clickFirstProduct() {
        wait.until(ExpectedConditions.elementToBeClickable(firstProduct)).click();
    }
}

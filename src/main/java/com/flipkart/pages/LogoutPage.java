package com.flipkart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class LogoutPage {
    WebDriver driver;
    WebDriverWait wait;

    By profileMenu = By.xpath("//*[@id=\'container\']/div/div[1]/div/div/div/div/div/div/div/div/div/div[1]/div/div/header/div[2]/div[2]/div/div/div/div/a");
    By logoutOption = By.xpath("//*[@id=\"container\"]/div/div[1]/div/div/div/div/div/div/div/div/div/div[1]/div/div/header/div[2]/div[2]/div/div/div/ul/a[9]");

    public LogoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void logout() {
        try {
            // Hover over profile menu
            WebElement profile = wait.until(ExpectedConditions.visibilityOfElementLocated(profileMenu));
            Actions actions = new Actions(driver);
            actions.moveToElement(profile).perform();

            // Click on Logout
            wait.until(ExpectedConditions.elementToBeClickable(logoutOption)).click();
        } catch (Exception e) {
            System.out.println("Logout failed: " + e.getMessage());
        }
    }
}

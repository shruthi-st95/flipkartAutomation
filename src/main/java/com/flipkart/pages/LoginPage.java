package com.flipkart.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilis.EmailOTPUtil;
import utilis.ScreenshotUtil;

import java.time.Duration;
import java.util.List;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30)); // increased wait
    }

    // Locators
    private By loginButton = By.xpath("//a[contains(text(),'Login') or contains(@href, 'account/login')]");
    private By loginIdField = By.xpath("//input[@type='text' and contains(@class,'r4vIwl')]");
    private By requestOtpButton = By.xpath("//button[contains(text(),'Request OTP')]");
    private By singleOtpField = By.xpath("//input[@maxlength='6']");
    private By multiOtpFields = By.xpath("//input[@maxlength='1']");
    
    // ✅ Generic Verify button locator
    private By verifyButton = By.xpath("//button[contains(translate(normalize-space(.),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'VERIFY')]");

    /** Safe click with retries & JS fallback */
    private void safeClick(By locator) {
        int attempts = 0;
        while (attempts < 3) {
            try {
                WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", element);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
                System.out.println("Clicked element: " + locator);
                return;
            } catch (StaleElementReferenceException | ElementClickInterceptedException e) {
                attempts++;
                System.out.println("Retrying click for: " + locator + " | Attempt " + attempts);
                if (attempts == 3) {
                    takeDebugScreenshot("click_fail");
                    printElementHTML(locator);
                    throw e;
                }
            } catch (TimeoutException te) {
                System.out.println("Timeout waiting for element: " + locator);
                takeDebugScreenshot("timeout_fail");
                printElementHTML(locator);
                throw te;
            }
        }
    }

    /** Take screenshot on failure */
    private void takeDebugScreenshot(String name) {
        try {
            String path = ScreenshotUtil.takeScreenshot(driver, name);
            System.out.println("Screenshot captured: " + path);
        } catch (Exception e) {
            System.out.println("Failed to capture screenshot: " + e.getMessage());
        }
    }

    /** Print element HTML for debugging */
    private void printElementHTML(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            System.out.println("Element HTML: " + element.getAttribute("outerHTML"));
        } catch (Exception e) {
            System.out.println("Unable to fetch element HTML for: " + locator);
        }
    }

    /** Switch to OTP iframe if present */
    private void switchToOtpFrameIfPresent() {
        driver.switchTo().defaultContent();
        try {
            List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
            for (WebElement frame : iframes) {
                driver.switchTo().frame(frame);
                if (!driver.findElements(verifyButton).isEmpty()) {
                    System.out.println("Switched to OTP iframe.");
                    return;
                }
                driver.switchTo().defaultContent();
            }
        } catch (Exception e) {
            driver.switchTo().defaultContent();
            System.out.println("No OTP iframe found.");
        }
    }

    /** Open login popup */
    public void openLoginPopup() {
        safeClick(loginButton);
    }

    /** Enter login ID (email/phone) */
    public void enterLoginId(String loginId) {
        WebElement loginInput = wait.until(ExpectedConditions.visibilityOfElementLocated(loginIdField));
        loginInput.clear();
        loginInput.sendKeys(loginId);
        System.out.println("Entered login ID: " + loginId);
    }

    /** Click on Request OTP */
    public void clickRequestOtp() {
        safeClick(requestOtpButton);
    }

    /** ✅ Click Verify Button */
    public void clickVerifyButton() {
        switchToOtpFrameIfPresent(); // ensure inside frame if needed
        safeClick(verifyButton);
        driver.switchTo().defaultContent();
        System.out.println("Clicked Verify and switched back to main content.");
    }

    /** Login with Email OTP */
    public void loginWithEmailOTP(String email, String appPassword) throws Exception {
        openLoginPopup();
        enterLoginId(email);
        clickRequestOtp();

        Thread.sleep(5000); // wait for OTP email

        String otp = EmailOTPUtil.fetchOTPFromEmail(email, appPassword);
        if (otp == null || otp.length() < 6) {
            throw new Exception("No valid OTP found in email.");
        }
        System.out.println("Fetched OTP: " + otp);

        switchToOtpFrameIfPresent();

        try {
            WebElement otpBox = wait.until(ExpectedConditions.visibilityOfElementLocated(singleOtpField));
            otpBox.clear();
            otpBox.sendKeys(otp);
            System.out.println("Entered OTP in single input field.");
        } catch (TimeoutException e) {
            List<WebElement> otpInputs = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(multiOtpFields));
            for (int i = 0; i < otp.length() && i < otpInputs.size(); i++) {
                try {
                    otpInputs.get(i).sendKeys(String.valueOf(otp.charAt(i)));
                } catch (StaleElementReferenceException ignored) {
                    otpInputs = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(multiOtpFields));
                    otpInputs.get(i).sendKeys(String.valueOf(otp.charAt(i)));
                }
            }
            System.out.println("Entered OTP across multiple input fields.");
        }

        Thread.sleep(2000);

        // ✅ Now click Verify
        clickVerifyButton();
    }
}

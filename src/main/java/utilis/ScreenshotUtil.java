package utilis;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class ScreenshotUtil {

    public static String takeScreenshot(WebDriver driver, String fileName) {
        try {
            // Create folder if not exists
            String screenshotDir = System.getProperty("user.dir") + "/screenshots/";
            File dir = new File(screenshotDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // Capture screenshot
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String filePath = screenshotDir + fileName + ".png";
            File destFile = new File(filePath);

            // Save file
            Files.copy(srcFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            return filePath;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

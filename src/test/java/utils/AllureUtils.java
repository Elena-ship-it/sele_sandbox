package utils;

import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class AllureUtils {

    @Attachment(value = "{screenshotName}", type = "image/png")
    public static byte[] attachScreenshot(String screenshotName) {
        try {
            // Берем скриншот напрямую из активного драйвера в виде байтов
            if (WebDriverRunner.hasWebDriverStarted()) {
                return ((TakesScreenshot) WebDriverRunner.getWebDriver()).getScreenshotAs(OutputType.BYTES);
            }
        } catch (Exception e) {
            System.err.println("Не удалось сделать скриншот для Allure: " + e.getMessage());
        }
        return new byte[0];
    }

    @Attachment(value = "{attachName}", type = "text/plain")
    public static String attachLog(String attachName, String message) {
        return message;
    }
}

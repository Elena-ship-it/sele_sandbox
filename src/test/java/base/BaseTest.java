package base;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.testng.ScreenShooter;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import pages.LoginPage;
import pages.EmailPage;
import utils.TestListener;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Listeners({TestListener.class, ScreenShooter.class})
public class BaseTest {
    protected LoginPage loginPage;
    protected EmailPage emailPage;
    protected static Properties config;

    @BeforeSuite
    public void globalSetup() {
        ScreenShooter.captureSuccessfulTests = false;
        config = new Properties();
        try (FileInputStream fis = new FileInputStream("src/test/resources/config.properties")) {
            config.load(fis);
        } catch (IOException e) {
            try (FileInputStream fis = new FileInputStream("config.properties")) {
                config.load(fis);
            } catch (IOException ex) {
                System.err.println("Файл config.properties не найден! Учетные данные будут браться из переменных среды.");
            }
        }
    }

    @BeforeMethod
    public void setup() {
        Configuration.browser = "chrome";
        Configuration.timeout = 10000;
        Configuration.headless = false;
        Configuration.baseUrl = "https://pr-cy.ru";
        Configuration.browserSize = "1920x1080";
        Configuration.holdBrowserOpen = false;

        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.notifications", 2);
        options.setExperimentalOption("prefs", prefs);
        Configuration.browserCapabilities = options;

        loginPage = new LoginPage();
        emailPage = new EmailPage();
    }

    protected String getProperty(String key) {
        return config != null ? config.getProperty(key) : System.getenv(key);
    }
}

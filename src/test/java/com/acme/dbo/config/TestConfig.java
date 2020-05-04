package com.acme.dbo.config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import java.util.ArrayList;

import static java.lang.System.*;


@TestConfiguration
public class TestConfig {
    @Lazy @Bean
    @Profile("it")
    public WebDriver webDriver() {
        if (driver != null) return driver;

        //region Driver setup
        String chromeDriverEnvLocation = getenv("webdriver.chrome.driver");
        String chromeDriverPropLocation = getProperty("webdriver.chrome.driver");
        if (chromeDriverEnvLocation != null || chromeDriverPropLocation != null) {
            setProperty("webdriver.chrome.driver", chromeDriverEnvLocation != null ? chromeDriverEnvLocation : chromeDriverPropLocation);
        } else {
            WebDriverManager.chromiumdriver().setup();
        }

        driver = new ChromeDriver(new ChromeOptions()
                .addArguments("--headless")
                .addArguments("--disable-gpu")
                .addArguments("--disable-dev-shm-usage")
                .addArguments("--disable-extensions")
                .addArguments("--no-sandbox")
                .addArguments("--start-maximized")
                .addArguments("--window-size=800,2000")
                .addArguments("--ignore-certificate-errors")
        );
        //endregion

        //region Performance hack setup
        //always keep open tab to prevent closing driver at <code>driver.close()</code>
        //note that <code>driver.quit()</code> closes driver anyway
        driver.manage().window().maximize();

        ((JavascriptExecutor)driver).executeScript("window.open()");
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        //endregion

        return driver;
    }
}

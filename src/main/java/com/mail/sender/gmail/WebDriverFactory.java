package com.mail.sender.gmail;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

/*
 * Factory to instantiate a WebDriver object.
 */
public class WebDriverFactory {
    private WebDriverFactory() {}

    /*
     * Factory method to return a WebDriver instance.
     *
     * @return RemoteWebDriver
     */
    public static WebDriver getInstance() {
        DesiredCapabilities capability = DesiredCapabilities.chrome();

        capability.setPlatform(Platform.ANY);

        String os = System.getProperty("os.name").toLowerCase().substring(0, 3);
        String chromeBinary = "src/main/resources/drivers/chrome/chromedriver" + (os.equals("win") ? ".exe" : "");
        System.setProperty("webdriver.chrome.driver", chromeBinary);

        return new ChromeDriver();
    }
}

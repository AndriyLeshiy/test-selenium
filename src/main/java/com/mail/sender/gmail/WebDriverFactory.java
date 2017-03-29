package com.mail.sender.gmail;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.net.URL;

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
        String chromeBinary = "drivers/chrome/chromedriver" + (os.equals("win") ? ".exe" : "");
        System.setProperty("webdriver.chrome.driver", chromeBinary);

        ClassLoader classLoader = WebDriverFactory.class.getClassLoader();
        URL resource = classLoader.getResource(chromeBinary);
        File f = new File("Driver");
        if (!f.exists()) {
            f.mkdirs();
        }
        File chromeDriver = new File("Driver" + File.separator + "chromedriver.exe");
        if (!chromeDriver.exists()) {
            try {
                chromeDriver.createNewFile();
                FileUtils.copyURLToFile(resource, chromeDriver);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.setProperty("webdriver.chrome.driver", chromeDriver.getAbsolutePath());
        return new ChromeDriver();
    }
}

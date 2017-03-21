package com.google.pages;

import com.google.util.Browser;
import com.google.util.ResourcesLoader;
import com.google.webdriver.WebDriverFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import java.io.Closeable;
import java.util.concurrent.TimeUnit;

import static com.google.util.ResourcesLoader.loadProperty;

/**
 * Created by serg on 21.03.17.
 */
public class Gmail implements Closeable {
    private WebDriver webDriver;

    private HomePage homePage;

    public Gmail() {
        String gridHubUrl = ResourcesLoader.loadProperty("grid2.hub");

        Browser browser = new Browser();
        browser.setName(ResourcesLoader.loadProperty("browser.name"));
        browser.setVersion(ResourcesLoader.loadProperty("browser.version"));
        browser.setPlatform(ResourcesLoader.loadProperty("browser.platform"));

        webDriver = WebDriverFactory.getInstance(gridHubUrl, browser);
        webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        // Load the page in the browser
        webDriver.get("https:\\\\gmail.com");

    }

    public void login() throws InterruptedException {
        LoginPage loginPage = PageFactory.initElements(webDriver, LoginPage.class);

        loginPage.inputEmail(loadProperty("user.email"));
        loginPage.clickNext();
        loginPage.inputPassword(loadProperty("user.password"));
        homePage = loginPage.clickSignIn();
    }


    public void sendEmail(String email, String name) throws InterruptedException {
        WriteMessagePage writeMessagePage = homePage.clickWrite();
        writeMessagePage.inputWhom(email);
        writeMessagePage.setSubject(loadProperty("email.subject"));
        writeMessagePage.writeMessage(ResourcesLoader.loadBody(name));
        Thread.sleep(1500);
        writeMessagePage.send();
    }

    public void close() {
        if (webDriver != null) {
            webDriver.close();
        }
    }
}

package com.google.app;

import com.google.pages.HomePage;
import com.google.pages.LoginPage;
import com.google.pages.WriteMessagePage;
import com.google.util.Browser;
import com.google.util.ResourcesLoader;
import com.google.webdriver.WebDriverFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import java.io.Closeable;
import java.util.concurrent.TimeUnit;

import static com.google.util.ResourcesLoader.loadProperty;

public class MailSender implements Closeable {
    private WebDriver webDriver;

    public MailSender() {
        String gridHubUrl = ResourcesLoader.loadProperty("grid2.hub");

        Browser browser = new Browser();
        browser.setName(ResourcesLoader.loadProperty("browser.name"));
        browser.setVersion(ResourcesLoader.loadProperty("browser.version"));
        browser.setPlatform(ResourcesLoader.loadProperty("browser.platform"));

        webDriver = WebDriverFactory.getInstance(gridHubUrl, browser);
        webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    public void sendEmail(String email, String name) throws InterruptedException {
        // Load the page in the browser
        webDriver.get("https:\\\\gmail.com");

        LoginPage loginPage = PageFactory.initElements(webDriver, LoginPage.class);

        loginPage.inputEmail(loadProperty("user.email"));
        loginPage.clickNext();
        loginPage.inputPassword(loadProperty("user.password"));

        HomePage homePage = loginPage.clickSignIn();

        WriteMessagePage writeMessagePage = homePage.clickWrite();
        writeMessagePage.inputWhom(email);
        writeMessagePage.setSubject(loadProperty("email.subject"));
        writeMessagePage.writeMessage(ResourcesLoader.loadBody(name));
        writeMessagePage.send();
    }

    public void close() {
        if (webDriver != null) {
            webDriver.close();
        }
    }
}

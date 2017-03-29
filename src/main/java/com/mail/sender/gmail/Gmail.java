package com.mail.sender.gmail;

import com.mail.sender.MailSender;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import java.io.Closeable;
import java.util.concurrent.TimeUnit;

/**
 * Created by serg on 21.03.17.
 */
public class Gmail implements MailSender, Closeable {
    private WebDriver webDriver;

    private HomePage homePage;

    public Gmail(String chromeDriverPath, String username, String password) {
        webDriver = WebDriverFactory.getInstance(chromeDriverPath);
        webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        // Load the page in the browser
        webDriver.get("https:\\\\gmail.com");
        login(username, password);
    }

    public Gmail(String username, String password) {
        webDriver = WebDriverFactory.getInstance();
        webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        // Load the page in the browser
        webDriver.get("https:\\\\gmail.com");
        login(username, password);
    }

    private void login(String username, String password) {
        LoginPage loginPage = PageFactory.initElements(webDriver, LoginPage.class);

        loginPage.inputEmail(username);
        loginPage.clickNext();
        loginPage.inputPassword(password);
        try {
            homePage = loginPage.clickSignIn();
        } catch (InterruptedException e) {
            throw new RuntimeException("");
        }
    }

    @Override
    public void send(String email, String subject, String body) {
        WriteMessagePage writeMessagePage = homePage.clickWrite();
        writeMessagePage.inputWhom(email);
        writeMessagePage.setSubject(subject);
        writeMessagePage.writeMessage(body);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            throw new RuntimeException("");
        }
        writeMessagePage.send();
    }

    @Override
    public void close() {
        if (webDriver != null) {
            webDriver.close();
        }
    }
}

package com.mail.sender.gmail;

import com.mail.sender.MailSender;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import java.io.Closeable;
import java.util.concurrent.TimeUnit;

import static com.mail.sender.util.ResourcesLoader.loadProperty;

/**
 * Created by serg on 21.03.17.
 */
public class Gmail implements MailSender, Closeable {
    private WebDriver webDriver;

    private HomePage homePage;

    public Gmail() {
        webDriver = WebDriverFactory.getInstance();
        webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        // Load the page in the browser
        webDriver.get("https:\\\\gmail.com");
        login();
    }

    private void login() {
        LoginPage loginPage = PageFactory.initElements(webDriver, LoginPage.class);

        loginPage.inputEmail(loadProperty("user.email"));
        loginPage.clickNext();
        loginPage.inputPassword(loadProperty("user.password"));
        try {
            homePage = loginPage.clickSignIn();
        } catch (InterruptedException e) {
            throw new RuntimeException("");
        }
    }

    @Override
    public void send(String email, String body) {
        WriteMessagePage writeMessagePage = homePage.clickWrite();
        writeMessagePage.inputWhom(email);
        writeMessagePage.setSubject(loadProperty("email.subject"));
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

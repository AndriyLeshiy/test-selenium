package com.google.pages;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.google.util.PropertyLoader.loadProperty;

public class SendEmailTest extends TestBase {
    private LoginPage homepage;

    @BeforeClass
    public void testInit() {
        // Load the page in the browser
        webDriver.get("https:\\\\gmail.com");
        homepage = PageFactory.initElements(webDriver, LoginPage.class);
    }

    @Test
    public void sendEmail() throws InterruptedException {
        homepage.inputEmail(loadProperty("user.email"));
        homepage.clickNext();
        homepage.inputPassword(loadProperty("user.password"));

        HomePage homePage = homepage.clickSignIn();

        WriteMessagePage writeMessagePage = homePage.clickWrite();

        writeMessagePage.inputWhom(loadProperty("email.receiver"));
        writeMessagePage.setSubject(loadProperty("email.subject"));
        writeMessagePage.writeMessage(loadProperty("email.body"));
        writeMessagePage.send();
    }
}

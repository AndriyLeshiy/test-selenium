package com.google.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

/*
 * Gmail login page
 * 
 * @author Andrii Leshchenko
 */
public class LoginPage extends Page {
    @FindBy(how = How.ID, using = "Email")
    @CacheLookup
    private WebElement emailElelent;

    @FindBy(how = How.ID, using = "next")
    @CacheLookup
    private WebElement nextElement;

    @FindBy(how = How.ID, using = "Passwd")
    @CacheLookup
    private WebElement passwordElement;

    @FindBy(how = How.ID, using = "signIn")
    @CacheLookup
    private WebElement signInElement;

    public LoginPage(WebDriver webDriver) {
        super(webDriver);
    }

    public void inputEmail(String email) {
        emailElelent.sendKeys(email);
    }

    public void clickNext() {
        nextElement.click();
    }

    public void inputPassword(String password) {
        passwordElement.sendKeys(password);
    }

    public HomePage clickSignIn() throws InterruptedException {
        signInElement.click();
        return PageFactory.initElements(getWebDriver(), HomePage.class);
    }
}

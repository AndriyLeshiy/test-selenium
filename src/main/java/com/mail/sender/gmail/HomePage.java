package com.mail.sender.gmail;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

/*
 * Gmail home page
 * 
 * @author Andrii Leshchenko
 */
public class HomePage extends Page {

    @FindBy(how = How.XPATH, using = "//div[text()=\"НАПИСАТЬ\"]")
    @CacheLookup
    private WebElement write;


    public HomePage(WebDriver webDriver) {
        super(webDriver);
    }

    public WriteMessagePage clickWrite() {
        write.click();
        return PageFactory.initElements(getWebDriver(), WriteMessagePage.class);
    }
}

package com.google.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

/**
 * @author Andrii Leschenko
 */
public class WriteMessagePage extends Page {
    @FindBy(how = How.NAME, using = "to")
    @CacheLookup
    private WebElement whom;

    @FindBy(how = How.NAME, using = "subjectbox")
    @CacheLookup
    private WebElement subject;

    @FindBy(how = How.XPATH, using = "//div[text()=\"Отправить\"]")
    @CacheLookup
    private WebElement send;

    //aria-label="Текст повідомлення"  //div[text()="НАПИСАТЬ"]
    @FindBy(how = How.XPATH, using = "//div[@aria-label='Тело письма']")
    @CacheLookup
    private WebElement body;

    public WriteMessagePage(WebDriver webDriver) {
        super(webDriver);
    }

    public void inputWhom(String whom) {
        this.whom.sendKeys(whom);
    }

    public void setSubject(String subject) {
        this.subject.sendKeys(subject);
    }

    public void send() {
        send.click();
    }

    public void writeMessage(String body) {
        setAttribute(this.body, body);
    }

    public void setAttribute(WebElement element, String value) {
        JavascriptExecutor js = (JavascriptExecutor)getWebDriver();
        js.executeScript("arguments[0].innerHTML=arguments[1]",
                             element, value);
    }
}

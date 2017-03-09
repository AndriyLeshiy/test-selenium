package com.google.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

/*
 * Sample page
 * 
 * @author Sebastiano Armeli-Battana
 */
public class MailPage extends Page {

	@FindBy(how = How.XPATH, using = "//div[text()=\"НАПИСАТЬ\"]")
	@CacheLookup
	private WebElement write;

	@FindBy(how = How.NAME, using = "to")
	@CacheLookup
	private WebElement whom;

	public MailPage(WebDriver webDriver) {
		super(webDriver);
	}

	public void clickWrite() {
		write.click();
	}

	public void inputWhom() {
		whom.sendKeys("WHOM");
	}

}

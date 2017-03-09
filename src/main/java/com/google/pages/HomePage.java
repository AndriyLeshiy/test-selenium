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
public class HomePage extends Page {

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

	public HomePage(WebDriver webDriver) {
		super(webDriver);
	}

	public void inputEmail() {
		emailElelent.sendKeys("LOGIN");
	}

	public void clickNext() {
		nextElement.click();
	}

	public void inputPassword() {
		passwordElement.sendKeys("PASS");
	}


	public void clickSignIn() {
		signInElement.click();
	}

}

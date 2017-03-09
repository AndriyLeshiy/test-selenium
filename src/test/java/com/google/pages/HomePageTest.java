package com.google.pages;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class HomePageTest extends TestBase {

	HomePage homepage;
	MailPage mailPage;

	@BeforeClass
	public void testInit() {
		// Load the page in the browser
		webDriver.get(websiteUrl);
		homepage = PageFactory.initElements(webDriver, HomePage.class);
	}

	@Test
	public void testH1Existing() throws InterruptedException {
		homepage.inputEmail();
		homepage.clickNext();
		homepage.inputPassword();
		homepage.clickSignIn();

		Thread.sleep(2*1000);

		mailPage = PageFactory.initElements(webDriver, MailPage.class);

		mailPage.clickWrite();
		mailPage.inputWhom();
	}

	@Test
	public void test2() throws InterruptedException {
		Assert.assertTrue(true);
	}
}

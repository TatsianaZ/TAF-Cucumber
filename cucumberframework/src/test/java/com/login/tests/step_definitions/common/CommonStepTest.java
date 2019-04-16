package com.login.tests.step_definitions.common;

import com.pageobjects.common.CommonMethods;
import com.pageobjects.pages.LoginPage;

import cucumber.api.java.en.When;

public class CommonStepTest {
	
	private CommonMethods commonMethods = new CommonMethods();
	private LoginPage loginPage = new LoginPage();

	@When("^the user clicks on link \"([^\"]*)\"$")
	public void clickOnLink(String linkText) {
		loginPage.waitForPageToLoad();
		loginPage.waitForPageToLoadAndJQueryProcessing();
		commonMethods.scrollIntoAndClickLink(linkText);
	}
	
	@When("^the user clicks on button \"([^\"]*)\"$")
	public void clickOnButton(String button)  {
		loginPage.waitForPageToLoad();
		commonMethods.button(button).click();
	}

}

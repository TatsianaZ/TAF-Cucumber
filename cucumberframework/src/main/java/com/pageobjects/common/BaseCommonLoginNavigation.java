package com.pageobjects.common;

import com.pageobjects.pages.LoginPage;
import com.pageobjects.utils.BaseStepDef;
import com.pageobjects.utils.LoginUtils;
import org.openqa.selenium.remote.RemoteWebDriver;

public class BaseCommonLoginNavigation extends BaseStepDef {

	private static final String GMAIL_FULL_BASE_URL = "https://accounts.google.com/ServiceLogin?service=mail&continue=https://mail.google.com/mail/";
	private static final String FACEBOOK_FULL_BASE_URL = "https://www.facebook.com/";

	private LoginPage loginPage;
	private RemoteWebDriver driver;
	private LoginUtils loginUtils = new LoginUtils();
	private ExcelFileReader excelFileReader = new ExcelFileReader();

	public BaseCommonLoginNavigation() {
		loginPage = new LoginPage();
		this.driver = LoginPage.getDriver;
	}

	public void gmailUserIsLoggedIn(String userName) {
		userNavigatesToGmailLoginPage();
		String password = excelFileReader.getGmailPassword(userName);
		if (loginPage.isBannerTextDisplayed()) {
			loginUtils.loginGmailWithUsernameAndPassword(userName, password);
		} else {
			loginUtils.loginGmailWithEmailAndPassword(userName, password);
		}
	}

	public void userNavigatesToGmailLoginPage() {
		loginPage.deleteAllCookies();
		driver.get(GMAIL_FULL_BASE_URL);
	}

	public void navigateToFacebook() {
		loginPage.deleteAllCookies();
		driver.get(FACEBOOK_FULL_BASE_URL);
	}


}

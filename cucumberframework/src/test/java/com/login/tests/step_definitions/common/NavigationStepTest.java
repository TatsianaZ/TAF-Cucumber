package com.login.tests.step_definitions.common;

import com.pageobjects.common.BaseCommonLoginNavigation;
import com.pageobjects.pages.FacebookLoginPage;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class NavigationStepTest {

    private BaseCommonLoginNavigation commonLoginNavigation = new BaseCommonLoginNavigation();
    private FacebookLoginPage facebookLoginPage = new FacebookLoginPage();

    @Given("^user navigates to Facebook$")
    public void navigateToFacebook(){
        String driverPath = System.getProperty("basedir");
        System.out.println("driverPath: " + driverPath);
        commonLoginNavigation.navigateToFacebook();
    }

    @Then("^the text \"([^\"]*)\" is displayed on Facebook start page$")
    public void isTextDisplayedOnFacebookStartPage(String text) {
        facebookLoginPage.isTextDisplayedOnMainContent(text);
    }

}

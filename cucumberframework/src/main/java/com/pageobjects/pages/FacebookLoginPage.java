package com.pageobjects.pages;

import com.framework.AbstractPage;
import org.openqa.selenium.By;

public class FacebookLoginPage extends AbstractPage {

    private static final String TEXT_MAIN_CONTENT_XPATH_PATTERN = "//*[contains(@class, 'fb_content')]//*[contains(@class, 'inlineBlock') and contains(text(), '%s')]";

    public boolean isTextDisplayedOnMainContent(String text) {
        return isElementDisplayed(By.xpath(String.format(TEXT_MAIN_CONTENT_XPATH_PATTERN, text)));
    }

}

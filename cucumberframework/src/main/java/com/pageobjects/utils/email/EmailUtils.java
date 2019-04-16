package com.pageobjects.utils.email;

import com.pageobjects.pages.EmailDeliveryPage;
import org.openqa.selenium.WebElement;

public class EmailUtils {

	private EmailDeliveryPage emailDeliveryPage = new EmailDeliveryPage();

	public void editValue(DeliveyEmailField deliverEmailField, String value) {
		WebElement element = emailDeliveryPage.waitForElementVisible(deliverEmailField.getBy());
		element.clear();
		element.sendKeys(value);
	}

}

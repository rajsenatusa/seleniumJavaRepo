package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;


// This is the parent of all  parent object classes.

public class basePage {

	
	WebDriver driver;
	
	public basePage (WebDriver driver) {
		
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
}

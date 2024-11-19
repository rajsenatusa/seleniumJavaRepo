package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class InLoggedInPage extends basePage {
	
	// calling the base page constructor.
	
	public InLoggedInPage(WebDriver driver) {
		
		super(driver);		// constructor separated in a separate class called base page
		
	}
	
	// write code for elements that we are going to interact
	

	@FindBy (xpath = "//a[@id='Menu_Workflow']")
	WebElement homeMenu;
	
	@FindBy (xpath = "//header/nav[@id='PM_Site']/div[2]/div[1]/i[1]")
	WebElement btntonavigateSignOut;
	
	@FindBy(xpath = "//a[@id='SignOutInMenu']")
	WebElement btnSignout;
	
	@FindBy (xpath = "//a[@id='SignOut']")
	WebElement confirmUrlSignout; 

	

	// In this section, write here write methods to perform actions.
	
	public void SignOutInsuranceNow() {
			
			btntonavigateSignOut.click();
			btnSignout.click();
			
		}
			

	// In this section, write methods used for test validations 
	// given method used to return the text of the Sign out Object.
	
	public String getSignOutConfirmationMsg() {
		
		return confirmUrlSignout.getText();
	}

	public boolean isInsuranceNowURLExists() {
		
		return homeMenu.isDisplayed();
	}


}

package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class InHomePage extends basePage {
	
	// calling the base page constructor.
	
	public InHomePage(WebDriver driver) {
		
		super(driver);		// constructor separated in a separate class called base page
		
	}
	
	// write code for elements that we are going to interact
	
	@FindBy (xpath = "//input[@id='j_username']")
	WebElement txtUsername; 
	
	@FindBy (xpath = "//input[@id='j_password']")
	WebElement txtPassword; 
	
	@FindBy (xpath = "//a[@id='SignIn']")
	WebElement btnSignIn;
	
	@FindBy (xpath = "//a[contains(text(),'Forgot Password')]")
	WebElement linkForgotPassword;	
		
	@FindBy (xpath = "//body/form[@id='frmLogin']/div[1]/div[2]/div[2]/div[2]")
	WebElement lbltextHomePageError;
		
	@FindBy (xpath = "//div[contains(text(),'Please Sign In')]")
	WebElement lblPleaseSignInTxt;
	
	
	// In this section, write here write methods to perform actions.
	
	public void loginInsuranceNow(String username, String password) {
		// This action method login's to insurance now.
		sendKeysToElement(txtUsername, username);
		sendKeysToElement(txtPassword,password);
		clickElement(btnSignIn);

	}
	
	// In this section, write methods used for test validations 
	// given method used to return the text of the Sign out Object.
	
	public String getHomePageErrorText() {
		
		return lbltextHomePageError.getText();
	}
	
}

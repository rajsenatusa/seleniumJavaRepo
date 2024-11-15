package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.InHomePage;
import pageObjects.InLoggedInPage;
import testBase.testBaseClass;

public class TC01_InLoginTest extends testBaseClass {
	
	// Test method where the validation happens
	@Test (groups =  {"sanity", "master"})
	public void verify_login() {
		
		logger.info(" **Verify Login Test Started** ");
		
		try {
			// Creating instance of HomePage object	
			logger.info(" **Entering Login Information** ");
			InHomePage hp = new InHomePage(driver);		

			// Fetching login details from properties file
			String username = prop.getProperty("Username");
			String password = prop.getProperty("Password");
			
			// Logging the username for reference
			logger.info("Username used for login: " + username);

			// Performing login actions
			hp.loginInsuranceNow(username, password);
			
			logger.info(" **Login Attempt Made** ");
			
			// Logged in Page
			
			InLoggedInPage lp = new InLoggedInPage(driver);					
			// Validate login is successful by checking sign-out confirmation
			String confirmationMsg = lp.getSignOutConfirmationMsg();
			Assert.assertEquals(confirmationMsg, "Sign Out", "Login validation failed. Expected 'Sign Out' message not found.");
			logger.info("User Logged in Successfully");

			lp.SignOutInsuranceNow();

		} catch (Exception e) {
			// Logging the exception and failing the test
			logger.error("Exception occurred during login test: " + e.getMessage(), e);
			Assert.fail("Test failed due to an exception: " + e.getMessage());

		} finally {
			logger.info(" **Verify Login Test Completed** ");
		}
	}
}

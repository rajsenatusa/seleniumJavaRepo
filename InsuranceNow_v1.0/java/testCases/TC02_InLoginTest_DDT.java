package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import Utilities.DataProviders;
import pageObjects.InHomePage;
import pageObjects.InLoggedInPage;
import testBase.testBaseClass;

public class TC02_InLoginTest_DDT extends testBaseClass {

    @Test(dataProvider = "loginData", dataProviderClass = DataProviders.class, groups = {"datadriven"})
    public void verify_login(String username, String password, String result) {

        logger.info(" **Verify Login Test Started for User: " + username + " **");

        try {
            // Home Page
            InHomePage hp = new InHomePage(driver);

            // Enter login credentials
            hp.setUsername(username);
            hp.setPassword(password);
            hp.clickSignIn();

            // Check for invalid login
            if (result.equalsIgnoreCase("invalid")) {
                String errorMsg = hp.getHomePageErrorText();
                System.out.println("Here is the error message: " + errorMsg);
                
                Assert.assertEquals(errorMsg, "Invalid user or password.", "Error message mismatch for invalid login.");
                logger.info("Invalid login test passed for user: " + username);

            } else if (result.equalsIgnoreCase("valid")) {
                // Check for valid login
                InLoggedInPage lp = new InLoggedInPage(driver);

                boolean isCorrectPage = lp.isInsuranceNowURLExists();
                System.out.println("Login successful, on target page: " + isCorrectPage);

                if (isCorrectPage) {
                    String confirmationMsg = lp.getSignOutConfirmationMsg();
                    Assert.assertEquals(confirmationMsg, "Sign Out", "Sign-out message mismatch after valid login.");
                    logger.info("User Logged in Successfully and confirmed sign-out message.");

                    lp.SignOutInsuranceNow(); // Sign out after successful login
                    logger.info("User signed out successfully.");

                } else {
                    logger.error("Failed to navigate to target page after login for user: " + username);
                    Assert.fail("Navigation to target page failed after valid login.");
                }
            } else {
                logger.error("Invalid test data result value: " + result);
                Assert.fail("Invalid test data result value: " + result);
            }

        } catch (Exception e) {
            logger.error("Exception occurred during login test for user: " + username, e);
            Assert.fail("Test failed due to an exception: " + e.getMessage());
        }

        logger.info(" **Verify Login Test Ended for User: " + username + " **");
    }
}

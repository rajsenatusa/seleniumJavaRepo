package pageObjects;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class InUserManagementPage extends basePage {

       
    private WebDriverWait wait;

    public InUserManagementPage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    // Constants
    private static final String STATUS_ACTIVE = "Active";
    private static final String STATUS_TERMINATED = "Terminated";
    
    
    // Elements
    @FindBy(id = "Menu_Admin")
    private WebElement menuAdmin;

    @FindBy(id = "Menu_Admin_UserManagement")
    private WebElement menuUserManagement;

    @FindBy(id = "SearchBy")
    private WebElement selectSearchBy;

    @FindBy(id = "SearchText")
    private WebElement txtSearchText;

    @FindBy(id = "Search")
    private WebElement btnSearch;
    
    @FindBy (xpath = "//*[@id=\"System User List\"]/div[2]/table")
    private WebElement searchResultTable;
    
    @FindBy(xpath = "//*[@id=\"System User List\"]/div[2]/table/tbody/tr")
    private List<WebElement> resultTableRows;
        
    @FindBy (xpath = "//*[@id=\"UserList\"]/table")
    private List<WebElement> userlistTableRows;
      
    @FindBy(id = "UserInfo.StatusCd")
    private WebElement selectStatus;
   
    @FindBy(xpath = "//*[@id=\"Save\"]/span")
    private WebElement btnSave;
    
    @FindBy(id = "UserInfo.TerminatedDt")
    private WebElement txtDateTerminatedOn;

    @FindBy(id = "UserMenu")
    private WebElement btnUserMenu;

    @FindBy(id = "SignOutInMenu")
    private WebElement linkSignOut;

    @FindBy(linkText = "Reset Password")
    private WebElement resetPasswordLink;

    @FindBy(id = "NewPassword")
    private WebElement txtNewPassword;

    @FindBy(id = "ConfirmNewPassword")
    private WebElement txtConfirmNewPassword;
    
    @FindBy (xpath = "//*[@id=\"UserInfo.PasswordMustChangeInd\"]")
    private WebElement chkBoxPasswordMustChangeInd;

    @FindBy(id = "ResetPassword")
    private WebElement btnChangePassword;

    @FindBy(id = "ChangePassword")
    private WebElement txtChangePassword;

    @FindBy(id = "ConfirmPassword")
    private WebElement txtConfirmPassword;

    @FindBy(id = "AuthorityError")
    private WebElement alertTextPane;
    
    @FindBy (id = "AddUser")
    private WebElement addUserLink;

    @FindBy (xpath = "//*[@id=\"LoginId_9044\"]")
    private WebElement userCodeLink;
    
    @FindBy (xpath = "//*[@id=\"Copy\"]/span")
    private WebElement copyLink;
    
    @FindBy (xpath = "//*[@id=\"UserInfo.LoginId\"]")
    private WebElement txtUserCode;
    
    @FindBy (xpath = "//*[@id=\"UserInfo.TypeCd\"]")
    private WebElement selectUserType;
    
    @FindBy (xpath = "//*[@id=\"UserInfo.ConcurrentSessions\"]")
    private WebElement txtConcurrentSessions;
    
    @FindBy (xpath = "//*[@id=\"UserInfo.DefaultLanguageCd\"]")
    private WebElement selectLanguage;
    
    @FindBy (xpath = "//*[@id=\"UserInfo.FirstName\"]")
    private WebElement txtFirstName;
    
    @FindBy (xpath = "//*[@id=\"UserInfo.LastName\"]")
    private WebElement txtLastName;
    
    @FindBy (xpath = "//*[@id=\"UserInfo.EmailAddr\"]")
    private WebElement txtEmailAddress;
    
    @FindBy (xpath = "//*[@id=\"UserInfoWorkAddr.Addr1\"]")
    private WebElement txtAddress;
    
    @FindBy (xpath = "//*[@id=\"UserInfoWorkAddr.City\"]")
    private WebElement txtCity;
    
    @FindBy (xpath = "//*[@id=\"UserInfoWorkAddr.StateProvCd\"]")
    private WebElement selectState;
    
    @FindBy (xpath = "//*[@id=\"UserInfoWorkAddr.PostalCode\"]")
    private WebElement txtPostalCode;
    
    @FindBy (xpath = "//*[@id=\"UserInfoWorkAddr.RegionCd\"]")
    private WebElement selectCountry;
 
    @FindBy (xpath = "//*[@id=\"UserInfoWorkAddr.addrVerifyImg\"]")
    private WebElement linkVerifyAddress;
    
    @FindBy (xpath = "//*[@id=\"UserInfoWorkAddr.verifyStatusImg\"]")
    private WebElement linkVerifyStatus;
        
    @FindBy (xpath = "//*[@id=\"UserInfoPhoneOne.PhoneName\"]")
    private WebElement selectPrimaryPhoneName;
    
    @FindBy (xpath = "//*[@id=\"UserInfoPhoneOne.PhoneNumber\"]")
    private WebElement txtPrimaryPhoneNumber;
    
    @FindBy (xpath = "//*[@id=\"UserInfoPhoneTwo.PhoneName\"]")
    private WebElement selectSecondaryPhoneName;
    
    @FindBy (xpath = "//*[@id=\"UserInfoPhoneTwo.PhoneNumber\"]")
    private WebElement txtSecondaryPhoneNumber;
    
    @FindBy (xpath = "//*[@id=\"UserInfo.UserManagementGroupCd\"]")
    private WebElement selectManagedGroup;
    
    @FindBy (xpath = "//*[@id=\"UserInfo.DepartmentCd\"]")
    private WebElement selectDepartment;
    
    @FindBy (xpath = "//*[@id=\"UserInfo.Supervisor\"]")
    private WebElement txtSupervisorCode;
    
    @FindBy (xpath = "//*[@id=\"UserLookup\"]")
    private WebElement btnUserLookup;
    
    @FindBy (xpath = "//*[@id=\"UserInfo.BranchCd\"]")
    private WebElement selectBrandCode;
    
    @FindBy (xpath = "//*[@id=\"AddRole\"]")
    private WebElement btnAddRole;
    
    @FindBy (xpath = "//*[@id=\"UserRole.AuthorityRoleIdRef\"]")
    private WebElement selectRole;
    
    @FindBy (xpath = "//*[@id=\"AddManagedGroup\"]")
    private WebElement btnaddManagedGroup;
    
    @FindBy (xpath = "//*[@id=\"ManagedGroup.ManagedGroupCd\"]")
    private WebElement selectUserManagedGroup;
    
    @FindBy (xpath = "//*[@id=\"DiscardChanges\"]/span")
    private WebElement btnDiscardChanges;
    
    

    // Navigation
    public void navigateToUserManagementPage() {
        menuAdmin.click();
        menuUserManagement.click();
    }
    
	// Search
    public boolean searchUser(String userCode) {   	
    	    if (!userCode.isEmpty()) {
            txtSearchText.clear();
            new Select(selectSearchBy).selectByValue("LoginId");
            txtSearchText.sendKeys(userCode);
            btnSearch.click();
            waitForSearchResultsToLoad(resultTableRows, 10);
            boolean emptyList = checkAndHandleEmptyList(resultTableRows, 10);
            if (emptyList) {
            	return false;
            }
        }
		return true;
    }


	// Get Size of Result Table
    public int getResultTableSize() {
        return resultTableRows.size();
    }

    public boolean isUserCodeFound(String userCode) {
        return resultTableRows.stream().anyMatch(row -> row.getText().contains(userCode));
    }
    

    // Logout
    public void signOutInsuranceNow() {
        btnUserMenu.click();
        linkSignOut.click();
    }
    
     // Status Updates
//    public boolean deactivateUser(String userCode) {
//        if (isUserCodeFound(userCode)) {
//            clickUserCodeForMatch(userCode);
//            new Select(selectStatus).selectByValue(STATUS_TERMINATED);
//            waitForElementToBeVisible(By.id("UserInfo.TerminatedDt"), Duration.ofSeconds(30));
//            txtDateTerminatedOn.sendKeys(new DateUtil().getCurrentDate(DATE_FORMAT), Keys.TAB);
//            btnSave.click();
//            return true;
//        }
//        return false;
//    }

    public String activateUser(String userCode) {
    	Wait(3);
        new Select(selectStatus).selectByValue(STATUS_ACTIVE);
    	Wait(3);
        btnSave.click();
        return "User Code " + userCode + " Activated";
    }

    public boolean checkUserStatus(String userCode, String expectedStatus) {
        return getStatusOfUserCode(userCode).equals(expectedStatus);
    }

    private String getStatusOfUserCode(String userCode) {
        for (WebElement row : resultTableRows) {
            if (row.getText().contains(userCode)) {
                return row.findElement(By.xpath("./td[6]")).getText().trim();
            }
        }
        return "Unknown";
    }

    // Password Management
    public String processResetPasswordForUserCode(String userCode, String newPassword) {
        
    	// Get all rows from the result table
        List<WebElement> allRows = searchResultTable.findElements(By.cssSelector("tbody tr"));
        System.out.println("Total rows found: " + allRows.size());

        // Iterate through all rows in the result table
        for (int i = 1; i < allRows.size(); i++) {
        	try {
                // Get all columns in the current row
                List<WebElement> columns = allRows.get(i).findElements(By.tagName("td"));

                // Assuming the first column contains the User Code
                WebElement userCodeCell = columns.get(0);
                String currentUserCode = userCodeCell.getText().trim();
                System.out.println("User Code in row " + (i + 1) + ": " + currentUserCode);

                // Assuming the sixth column contains the User Status
                WebElement statusCell = columns.get(5);
                String currentStatus = statusCell.getText().trim();
                System.out.println("User Status in row " + (i + 1) + ": " + currentStatus);

                // Check for an exact match of the User Code and status
    	
                if (currentUserCode.equalsIgnoreCase(userCode)) {
                    if (STATUS_ACTIVE.equalsIgnoreCase(currentStatus)) {
                    	columns.get(0).findElement(By.className("actionLink")).click();
                        return resetPasswordHelper(newPassword);
                    } else if (STATUS_TERMINATED.equals(currentStatus)) {
                    	columns.get(0).findElement(By.className("actionLink")).click();
                        String actviationStatus = activateUser(userCode);    
                        System.out.println(actviationStatus);
                        return resetPasswordHelper(newPassword);
                    }
                }
            } catch (Exception e) {
                System.err.println("Error processing row for userCode " + userCode + ": " + e.getMessage());
            }
        }
        signOutInsuranceNow();
        return "User Code not found.";

    }
    
 // Password Management
    public String processAddUsers(String userCode, String firstname, String lastname, String emailaddress, String newPassword) {
        
    	// adding users steps started  
    	clickElement(addUserLink);
    	sendKeysToElement(txtUserCode, userCode);
    	selectDropdownByValue(selectUserType, "Company");
    	sendKeysToElement(txtConcurrentSessions, "20");
    	selectDropdownByValue(selectLanguage, "en_US");  
    	sendKeysToElement(txtFirstName, firstname);
    	sendKeysToElement (txtLastName, lastname);
    	
    	//fill address details
    	sendKeysToElement(txtAddress, "5426 Bay Center Dr");
    	handleAddressConfirmationPopup();
    	sendKeysToElement(txtCity, "Tampa");   	
    	selectDropdownByValue(selectState, "FL");   
    	sendKeysToElement(txtPostalCode, "33609");
    	selectDropdownByValue(selectCountry, "United States");  
    	
    	// Perform Address Verification
    	validateAddressVerification(linkVerifyAddress);
    	
    	// fill  Contact Details
    	selectDropdownByValue(selectPrimaryPhoneName, "Mobile");
    	sendKeysToElement(txtPrimaryPhoneNumber, "8131234112");
    	selectDropdownByValue(selectSecondaryPhoneName, "Mobile");
    	sendKeysToElement(txtSecondaryPhoneNumber, "8131234112");
    	
    	// fill Supervisor and departments details
    	
    	selectDropdownByValue(selectManagedGroup,"Claims");
    	selectDropdownByValue(selectDepartment, "IT");
    	sendKeysToElement(txtSupervisorCode, "JRogers1");
    	
    	searchAndSelectUserSeparateWindowHandle("JRogers1" ); 	
    	
    	sendKeysToElement(txtEmailAddress, emailaddress);
    	sendKeysToElement(txtChangePassword, newPassword);
    	sendKeysToElement(txtConfirmPassword, newPassword);
    	clickElement(chkBoxPasswordMustChangeInd);
    	selectDropdownByValue(selectBrandCode, "Home Office");
    	clickElement(btnSave);    	
    	
    	// this method add admin role for the new user
    	adduserRole();
    	
    	// this method add managed user group as claims by default.
    	adduserManagedGroup();   	
    	
    	// signout Insurance Now
    	
    	signOutInsuranceNow();
        return "New User :" +userCode + " Added Successfully and Password is set to :" +newPassword;

    }
  
    private void adduserManagedGroup() {
    	clickElement(btnaddManagedGroup);
     	selectDropdownByValue (selectUserManagedGroup,"Claims" );
     	clickElement(btnSave);
    }

	private void adduserRole() {
		clickElement(btnAddRole);
    	selectDropdownByValue(selectRole, "Everything");
    	clickElement(btnSave);   		    	
    }

	/**
     * Handles the sign-out confirmation widget.
     * @param action The action to perform: "OK" to confirm, "CANCEL" to dismiss.
     */
    public void handleSignOutWidget(String action) throws TimeoutException {
        try {
            // Wait for the widget to appear
            WebElement widget = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[15]")));

            // Find the OK and Cancel buttons
            WebElement okButton = widget.findElement(By.xpath("//*[@id=\"dialogOK\"]/span"));
            WebElement cancelButton = widget.findElement(By.xpath("//*[@id=\"Cancel\"]"));

            // Perform the action based on the parameter
            if ("OK".equalsIgnoreCase(action)) {
                okButton.click();
                System.out.println("Sign-out confirmed by clicking OK.");
            } else if ("CANCEL".equalsIgnoreCase(action)) {
                cancelButton.click();
                System.out.println("Sign-out canceled by clicking Cancel.");
            } else {
                throw new IllegalArgumentException("Invalid action: Use 'OK' or 'CANCEL'.");
            }
        } catch (Exception e) {
            System.err.println("An error occurred while handling the sign-out widget: " + e.getMessage());
        }
    }
    
    // Method to reset the password and handle the alert and widget panes
    public String resetPasswordHelper(String newPassword) {
        try {
             // Clear and enter the new password
        	sendKeysToElement(txtChangePassword,newPassword);
        	sendKeysToElement(txtConfirmPassword,newPassword);
          	clickElement(chkBoxPasswordMustChangeInd);
            // Click the "Save Button" button
          	clickElement(btnSave);

          	// After clicking, check if the alert pane appears
            String alertMessage = handlePostPasswordChangeBehavior();

            // Return the captured message (success or failure)
            
            return alertMessage;
            
        } catch (Exception e) {
        	
            System.err.println("Error resetting password: " + e.getMessage());
            return "Failed to reset password.";
        }
    }
        
    private String handlePostPasswordChangeBehavior() {
        try {
            By errorAlertLocator = By.id("informationMsg");
            String alertMessage = "";

            // Check if the error alert is present
            if (isElementPresent(errorAlertLocator)) {
                WebElement alertElement = driver.findElement(errorAlertLocator);
                alertMessage = alertElement.getText().trim();

                // Update alertMessage for specific success cases
                if (alertMessage.equals("Caution! For security reasons, the Standard Users password policy is recommended for users. "
                        + "Please make sure this user requires the Exempt Users password policy before continuing.") ||
                    alertMessage.equals("Password has been changed")) {                    
                    alertMessage = "Password updated successfully";  // Update alertMessage
                    System.out.println("Password Status: " + alertMessage);
                    signOutInsuranceNow();  // Perform sign-out action
                    return alertMessage;  // Return the updated message
                } else if (alertMessage.contains("Password has not been changed")
                		   || alertMessage.contains("Password must not match one of 3 previous passwords.") 
                		   || alertMessage.contains("Password must not match previous password")
                		   || alertMessage.contains("Password must not match previous password.\r\n"
                		   		+ "Caution! For security reasons, the Standard Users password policy is recommended for users. Please make sure this user requires the Exempt Users password policy before continuing.")
                		   ) 
                	{
                    
                    System.out.println("Password Status: " + alertMessage);
                    signOutInsuranceNow();  // Perform sign-out action
                    return alertMessage;  // Return the specific alert message
                } else if (alertMessage.contains("Supervisor code does not correspond to a valid User")) {
                	 updateSupervisor("JRogers1");
                	 alertMessage = "Alert Message: " + alertMessage + " Appeared and handled successfully. & Password updated successfully";  
                     signOutInsuranceNow();  // Perform sign-out action
                     return alertMessage;  // Return the specific alert message
                	
                }
            }

            // If no relevant alert is found
            signOutInsuranceNow();  // Perform sign-out action
            System.out.println("No relevant alert message found.");
            return "No alert message found.";

        } catch (Exception e) {
            // Handle unexpected exceptions
            System.err.println("Unexpected error while handling post-password change behavior: " + e.getMessage());
            return "Error occurred while handling the alert.";
        }
    }


 
 private void updateSupervisor(String supervisor) {
	 	
 		searchAndSelectUserSeparateWindowHandle(supervisor ); 
 		clickElement(btnSave);
 		System.out.println("Updating supervisor to: " + supervisor);

}

// Helper method to check if an element is present in the DOM
    private boolean isElementPresent(By locator) {
        try {
            return !driver.findElements(locator).isEmpty();
        } catch (Exception e) {
            return false;  // If any error occurs, treat the element as not present
        }
    }

 
    // Sign out and handle the widget if it appears
//    @SuppressWarnings({ "unused", "unused" })
//	private void signOutAndHandleWidget() {
//        // Perform sign out first
//        signOutInsuranceNow();
//
//        // Now, check for the widget (confirmation dialog) and handle it
//        try {
//            WebElement widget = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[15]"))); // XPath for widget
//            if (widget != null) {
//                System.out.println("Widget detected, handling widget...");
//
//                // Locate and click the "OK" button inside the widget
//                WebElement okButton = widget.findElement(By.xpath("//*[@id='dialogOK']/span"));
//                okButton.click();
//                System.out.println("Clicked 'OK' on the widget pane.");
//            }
//        } catch (Exception e) {
//            System.out.println("Widget not detected or an error occurred while handling the widget: " + e.getMessage());
//        }
//    }
    
    


	public String processDeactivationUserCode(String usercode) {
		return null;
	}
	
//	public void selectValueFromDropDown(WebElement selectElement, String value) {
//		// Initialize the Select object using the provided selectElement
//	    Select dropdownvalue = new Select(selectElement);
//	    
//	    try {
//	        // Deselect the option with the specified value
//	    	dropdownvalue.selectByValue(value);
//	        System.out.println("The Value" + value + "' has been selected.");
//	    } catch (UnsupportedOperationException e) {
//	        System.err.println("Select operation is not supported for this dropdown.");
//	    } catch (NoSuchElementException e) {
//	        System.err.println("No option with value '" + value + "' found in the dropdown.");
//	    }
//	}
//	
	
	public void validateAddressVerification(WebElement linkVerifyAddress) {

	    // Wait for the verify link to be visible
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    wait.until(ExpectedConditions.visibilityOf(linkVerifyAddress));

	    // Click the verify link
	    linkVerifyAddress.click();
	    System.out.println("Clicked on verify link.");

	    // Wait for the status element to become visible
	    WebElement verifiedStatus = wait.until(ExpectedConditions.visibilityOfElementLocated(
	            By.id("UserInfoWorkAddr.verifyStatusImg")
	    ));

	    // Validate the presence of the verified address text or visibility
	    String statusTitle = verifiedStatus.getAttribute("title");
	    if (statusTitle.equals("Click to open map")) {
	        System.out.println("Address verified successfully. Status element is visible.");
	    } else {
	        System.err.println("Failed to verify the address. Status element not updated correctly.");
	    }
	}
	
	public void searchAndSelectUserSeparateWindowHandle(String userCode) {
	    // Click the User Lookup button to open the new window
		clickElement(btnUserLookup);

	    // Switch to the new window
	    String parentWindow = driver.getWindowHandle();
	    for (String windowHandle : driver.getWindowHandles()) {
	        if (!windowHandle.equals(parentWindow)) {
	            driver.switchTo().window(windowHandle);
	            break;
	        }
	    }

	    // Wait for the elements in the new window to load
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("SearchText"))); 

	    // Input the user code in the text box	
	    sendKeysToElement(txtSearchText,userCode);

	    // Click the Search button
	    clickElement(btnSearch);	    

	    // Wait for the search results to load
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("UserList"))); 

	    // Select the user from the search results
	 
	    WebElement userRow = driver.findElement(By.xpath("//*[@id=\"UserList\"]/table/tbody/tr[2]/td[2]/a")); 
	    userRow.click();

	    // Close the new window and switch back to the parent window
	    driver.switchTo().window(parentWindow);
	}
	
	
	
	public void handleAddressConfirmationPopup() {
	    // Wait for the dialog to appear
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    WebElement okButton = wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("/html/body/div[17]/div/table/tr/td[2]/button")
	    ));

	    // Click the "OK" button
	    okButton.click();

	    // Log for debugging
	    System.out.println("Address confirmation popup handled successfully.");
	}

}

package pageObjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class InClaimsMaintenancePage extends basePage {
	

public InClaimsMaintenancePage(WebDriver driver) {
		
		super(driver);		// constructor separated in a separate class called base page
		
	}

//write code for elements that we are going to interact

	@FindBy (xpath ="//li[@id='Menu_Claims']")
	WebElement linkClaims;
	
	@FindBy (xpath = "//a[@id='Menu_Claims_ClaimMaintenance']")
	WebElement linkClaimMaintenance;
	
	@FindBy (xpath = "//a[contains(text(),'Provider')]")
	WebElement linkProvider;
	
	@FindBy (xpath= "//span[contains(text(),'New Provider')]")
	WebElement linkNewProvider;
	
	@FindBy (xpath = "//select[@id='SearchBy']")
	WebElement listboxSearchBy;
	
	@FindBy (xpath = "//input[@id='SearchText']")
	WebElement txtboxSearchText ;
	
	@FindBy (xpath = "//a[@id='Search']")
	WebElement btnSearch;
	
	@FindBy(xpath = "//a[contains(text(),'CA0TYNA')]")
	WebElement clickProviderListText;
	
	@FindBy(xpath = "//span[contains(text(),'Copy')]")
	WebElement clickCopyBtn;
	
	@FindBy(xpath = "//input[@id='Provider.ProviderNumber']")
	WebElement txtProviderCode;
	
	@FindBy(xpath = "//input[@id='ProviderName.CommercialName']")
	WebElement txtProviderCommercialName;
	
	@FindBy(xpath = "//input[@id='ProviderName.GivenName']")
	WebElement txtProviderName;
	
	@FindBy(xpath = "//input[@id='ProviderName.DBAName']")
	WebElement txtDBAName;
	
	@FindBy(xpath = "//input[@id='ProviderStreetAddr.Addr1']")
	WebElement txtProviderAddress;
	
	@FindBy(xpath = "//input[@id='ProviderPrimaryPhone.PhoneNumber']")
	WebElement txtProviderPrimaryPhoneNumber;
	
	@FindBy(xpath = "//input[@id='ProviderFax.PhoneNumber']")
	WebElement txtProviderFaxNumber;
	
	@FindBy(xpath = "//input[@id='ProviderEmail.EmailAddr']")
	WebElement txtProviderEmailAddress;
	
	@FindBy(xpath = "//input[@id='AcctName.CommercialName']")
	WebElement txtProviderAccounBusinessName;
	
	@FindBy(xpath = "//input[@id='AcctName.CommercialName2']")
	WebElement txtProviderAccounBusinessName2;
	
	@FindBy(xpath = "//span[contains(text(),'Save')]")
	WebElement btnSave;
	
	@FindBy(xpath = "//div[@id='ProviderSummary_ProviderNumber']")
	WebElement lblProviderCode;
	
	@FindBy(xpath = "//div[@id='GenericBusinessError']")
	WebElement genericErrorLbl;
	
	@FindBy (xpath = "//header/nav[@id='PM_Site']/div[2]/div[1]/i[1]")
	WebElement btntonavigateSignOut;
	
	@FindBy(xpath = "//a[@id='SignOutInMenu']")
	WebElement btnSignout;
	
	@FindBy(xpath = "//a[@id='NewLicense']")
	WebElement linkNewLicense;
	
	@FindBy(xpath = "//body[1]/main[1]/form[1]/div[1]/div[1]/div[4]/div[1]/div[7]/div[4]/div[1]/div[1]/table[1]/tbody[1]/tr[2]/td[7]/a[1]")
	WebElement licenseChangeLink;
	
	@FindBy (xpath = "//select[@id='ProviderLicense.StateProvCd']")
	WebElement providerLicenseState; 
	
	@FindBy (xpath = "//select[@id='ProviderLicense.LicenseTypeCd']")
	WebElement licenseType; 
	
	@FindBy (xpath = "//input[@id='ProviderLicense.LicensePermitNumber']")
	WebElement txtlicenseNumber; 
	
	@FindBy (xpath = "//input[@id='ProviderLicense.ExpirationDt']")
	WebElement txtlicensexpirationDate; 
	
	@FindBy (xpath = "//select[@id='ProviderLicense.LicenseStatusCd']")
	WebElement licenseStatus; 
	
	
		
		
	
	// In this section, write only action methods for your test cases.

	public void clickClaimsMenu () {
		
		linkClaims.click();
	}
	
	public void clickClaimMaintenance () {
		
		linkClaimMaintenance.click();
	}
	
	public void clickProvider () {
		
		linkProvider.click();
	}
	
	
	public void clickNewProvider() {
		linkNewProvider.click();
	}
	
	public void selectSearchbyList () {
		
		Select dropdown = new Select(listboxSearchBy);
		dropdown.selectByValue("ProviderNumber");
		
	}
	
	public void setSearchText (String ProviderNumber) {
		
		txtboxSearchText.sendKeys(ProviderNumber);
	}
	
	public void clickSearchBtn () {
		
		btnSearch.click();
	}

	public void clickProviderListText () {
		
		clickProviderListText.click();
	}
	
	public void clickCopyBtn () {
		
		clickCopyBtn.click();
	}

	public void setBusinessName(String businessname) {
		txtProviderCommercialName.clear();
		txtProviderCommercialName.sendKeys(businessname);
		
	}

	public void setName(String name) {
		txtProviderName.clear();
		txtProviderName.sendKeys(name);
		
	}

	public void setDBAName(String dbaname) {
		txtDBAName.clear();
		txtDBAName.sendKeys(dbaname);
		
	}
	
	public void setProviderPrimaryPhoneNumber(String primaryname) {
		txtProviderPrimaryPhoneNumber.clear();
		txtProviderPrimaryPhoneNumber.sendKeys(primaryname);
		
	}
	
	public void setProviderFaxNumber(String providerfax) {
		txtProviderFaxNumber.clear();
		txtProviderFaxNumber.sendKeys(providerfax);
		
	}
	
	public void setProviderEmailAddress(String provideremail) {
		txtProviderEmailAddress.clear();
		txtProviderEmailAddress.sendKeys(provideremail);
		
	}
	
	public void setProviderAccounBusinessName(String provideraccName) {
		txtProviderAccounBusinessName.clear();
		txtProviderAccounBusinessName.sendKeys(provideraccName);
		
	}

	
	public void setProviderAccounBusinessName2(String provideraccName2) {
		txtProviderAccounBusinessName2.clear();
		txtProviderAccounBusinessName2.sendKeys(provideraccName2);
		
	}
	
	public void setProviderNumber(String providerNumber) {
		txtProviderCode.clear();
		txtProviderCode.sendKeys(providerNumber);
		
	}
	
	
	public void clickSaveBtn () {
		
		btnSave.click();
	}


	public String getProviderCode() {
	
		try {			
			return (lblProviderCode.getText()) ;			
		}		
		catch (Exception e) 
		{			
			return (e.getMessage());
		}
	}


	public void SignOutInsuranceNow() {
		
		btntonavigateSignOut.click();
		btnSignout.click();
		
	}
	
	public WebElement getProviderListElement() {

		return clickProviderListText;
	}
	
	public WebElement getProviderFormElement() {

		return txtProviderCommercialName;
	}

	public WebElement getSaveConfirmationElement() {

		return lblProviderCode;
	}

	public String getErrorMessage() {
		
		return genericErrorLbl.getText();
	}
	
	public void linkNewLicense () {
		
		linkNewLicense.click();
	}
		
	public void selectProviderState (String state) {		
		Select dropdown = new Select(providerLicenseState);
		dropdown.selectByValue(state);
		
	}

	public void selectLicenseType(String LType) {
		Select dropdown = new Select(licenseType);
		dropdown.selectByValue(LType);
		
	}

	public void enterLicenseNumber(String licencenumber) {
		txtlicenseNumber.clear();;
		txtlicenseNumber.sendKeys(licencenumber);
		
	}

	public void enterLicensexpirationDate(String licensexp) {
		txtlicensexpirationDate.clear();
		txtlicensexpirationDate.sendKeys(licensexp);
		
	}

	public void selectLicenseStatus(String lStatus) {
		Select dropdown = new Select(licenseStatus);
		dropdown.selectByValue(lStatus);
		
	}

	public void clickLicenseChangeLink() {
		licenseChangeLink.click();
		
	}

	public void clickProviderCodeByText(String providerCodeTxt) {
		// Dynamically build the XPath using the provider text
        String dynamicXpath = "//a[contains(text(),'" + providerCodeTxt + "')]";

        // Wait until the element is present and clickable
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50)); // Adjust timeout as needed
        WebElement dynamicProviderLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(dynamicXpath)));

        // Click the dynamically found element
        dynamicProviderLink.click();
        System.out.println("Successfully clicked on the provider link with text: " + providerCodeTxt);
		
	}


	public String generateFullName(String firstname, String lastname) {
		String fullName = String.join(" ", firstname, lastname);
		return fullName;
	}
		

}

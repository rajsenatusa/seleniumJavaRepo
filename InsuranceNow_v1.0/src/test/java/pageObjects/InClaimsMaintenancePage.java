package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class InClaimsMaintenancePage extends basePage {
	

public InClaimsMaintenancePage(WebDriver driver) {
		
		super(driver);		// constructor separated in a separate class called base page
		
	}

//write code for elements that we are going to interact

	@FindBy (xpath ="//li[@id='Menu_Claims']")
	WebElement menuClaims;
	
	@FindBy (xpath = "//a[@id='Menu_Claims_ClaimMaintenance']")
	WebElement menuOptionCliamMaintenance;
	
	@FindBy (xpath = "//a[contains(text(),'Provider')]")
	WebElement linkProvider;
	
	@FindBy (xpath= "//span[contains(text(),'New Provider')]")
	WebElement linkNewProvider;
	
	@FindBy (xpath = "//select[@id='Provider.StatusCd']")
	WebElement selectProviderStatus;
	
	@FindBy (xpath = "//select[@id='ProviderTaxInfo.LegalEntityCd']")
	WebElement selectBusinessType;
	
	@FindBy (xpath = "//select[@id='Provider.CombinePaymentInd']")
	WebElement selectAllowedCombinedPayment;
		
	@FindBy (xpath ="//input[@id='Provider.StatusDt']")
	WebElement txtProviderStatusDate;
	
	@FindBy(xpath = "//input[@id='ProviderName.CommercialName']")
	WebElement txtBusinessName;

	@FindBy(xpath = "//input[@id='ProviderName.GivenName']")
	WebElement txtName;
	
	@FindBy(xpath = "//input[@id='ProviderName.DBAName']")
	WebElement txtDBAName;
		
	@FindBy(xpath = "//input[@id='ProviderStreetAddr.Addr1']")
	WebElement txtStreetAddr;
	
	@FindBy (xpath = "//input[@id='ProviderStreetAddr.City']")
	WebElement txtCity;
	
	@FindBy (xpath = "//select[@id='ProviderStreetAddr.StateProvCd']")
	WebElement selectState;
	
	@FindBy (xpath = "//input[@id='ProviderStreetAddr.PostalCode']")
	WebElement txtZipCode;

	@FindBy (xpath = "//select[@id='Provider.ProviderTypeCd']")
	WebElement selectProviderType;
	
	@FindBy (xpath = "//a[@id='CopyBillingAddress']")
	WebElement btnCopyBillingAddress;
		
	@FindBy (xpath = "//a[@id='CopyAddress']")
	WebElement btnSearchDBACopyAddress;
	
	@FindBy (xpath = "//select[@id='ProviderTaxInfo.Required1099Ind']")
	WebElement select1099Ind;
	
	@FindBy (xpath = "//select[@id='ProviderTaxInfo.TaxIdTypeCd']")
	WebElement selectTaxIDtype;
	
	@FindBy (xpath = "//*[@id=\"TaxId\"]")
	WebElement txtTaxId;
		
	@FindBy (xpath = "//*[@id=\"Save\"]/i")
	WebElement btnProviderDetailSave;
	
	@FindBy (xpath = "//select[@id='ProviderPrimaryPhone.PhoneName']")
	WebElement selectprimaryPhoneType;
	
	@FindBy (xpath = "//input[@id='ProviderPrimaryPhone.PhoneNumber']")
	WebElement txtprimaryPhoneNumber;
	
	@FindBy (xpath = "//select[@id='Provider.PaymentPreferenceCd']")
	WebElement selectpaymentPreference;
	
	@FindBy (xpath ="//*[@id=\"ProviderTerritoryLocation.StateProvCd\"]")
	WebElement selectTerritoryState;
	
	@FindBy (xpath ="//*[@id=\"ProviderTerritoryLocation.TypeCd\"]")
	WebElement selectType;
	
	@FindBy (xpath = "//*[@id=\"ProviderTerritoryLocation.Description\"]")
	WebElement txtTerritoryDescriptionDetails;
	
	
	@FindBy (xpath = "//*[@id=\"NewTerritoryLocation\"]")
	WebElement btnNewTerritoryLocation;
	
	@FindBy (xpath = "//*[@id=\"ProviderLicense.StateProvCd\"]")
	WebElement selectProviderLicState;
	
	@FindBy(xpath = "//*[@id=\"ProviderLicense.LicenseTypeCd\"]")
	WebElement selectProviderLicType;
	
	@FindBy (xpath = "//*[@id=\"ProviderLicense.LicensePermitNumber\"]")
	WebElement txtLicenseNumber;
	
	@FindBy (xpath = "//*[@id=\"ProviderLicense.ExpirationDt\"]")
	WebElement txtLicExpriationDate;
	
	@FindBy (xpath ="//*[@id=\"ProviderLicense.LicenseStatusCd\"]")
	WebElement selectLicStatus;
	
	@FindBy (xpath ="//*[@id=\"Save\"]/span")
	WebElement btnLicSave;
	

	
		
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
	

	@FindBy(xpath = "//input[@id='ProviderStreetAddr.Addr1']")
	WebElement txtProviderAddress;
	
	@FindBy (xpath = "//select[@id='ProviderPrimaryPhone.PhoneName']")
	WebElement selectPrimaryPhoneName;
	
	@FindBy(xpath = "//input[@id='ProviderPrimaryPhone.PhoneNumber']")
	WebElement txtPrimaryPhoneNumber;

	@FindBy (xpath = "//select[@id='ProviderSecondaryPhone.PhoneName']")
	WebElement selectSecondaryPhoneName;
	
	@FindBy(xpath = "//input[@id='ProviderSecondaryPhone.PhoneNumber']")
	WebElement txtSecondaryPhoneNumber;

	
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
	
	@FindBy (xpath = "//*[@id=\"SignOutInMenu\"]")
	WebElement linkSignOut;
	
	
	// In this section, write only action methods for your test cases.
	
	public void navigateToNewProviderPage() {
		
		menuClaims.click();
		menuOptionCliamMaintenance.click();
		linkProvider.click();
		linkNewProvider.click();
		
	}
	
	public void setProviderNumber(String providerNumber) {
		txtProviderCode.clear();
		txtProviderCode.sendKeys(providerNumber);
		
	}
	
	public void selectProviderType (String providerType) {
		
		Select ProviderType = new Select (selectProviderType);
		ProviderType.selectByValue(providerType);
				
	}
		
	public void selectStatus(String providerStatus) {
		
		Select Status = new Select(selectProviderStatus);
		Status.selectByValue(providerStatus);		
		
	}
	
	public void selectBusinessType(String businessType) {
		
		Select dropdown = new Select(selectBusinessType);
		dropdown.selectByValue(businessType);
		
	}
	
	public void setProviderStatusDate(String currentDate) {
		txtProviderStatusDate.clear();
		txtProviderStatusDate.sendKeys(currentDate);
		
	}
	

	public void selectAllowCombinedPayment(String combinedPayment) {
		Select dropdown = new Select(selectAllowedCombinedPayment);
		dropdown.selectByValue(combinedPayment);
		
	}
	
	public void setBusinessName(String name) {
		txtBusinessName.clear();
		txtBusinessName.sendKeys(name);
		
	}
	
	public void setName(String fullName) {
		txtName.clear();
		txtName.sendKeys(fullName);
		
	}
	
	public void setDBAName(String dbaname) {
		txtDBAName.clear();
		txtDBAName.sendKeys(dbaname);
		
	}
	
	public void setStreeAddress(String address1,  String City, String State, String Zip ) {
		
		txtStreetAddr.clear();
		txtStreetAddr.sendKeys(address1);
		
		txtCity.clear();
		txtCity.sendKeys(City);
		
		Select state = new Select (selectState);
		state.selectByValue(State);
		
		txtZipCode.clear();
		txtZipCode.sendKeys(Zip);
		
		btnSearchDBACopyAddress.click();
				
		
	}
	
	public void setPrimaryPhoneNumber(String string, String phonenumber) {
		
		Select dropdown = new Select (selectPrimaryPhoneName);
		dropdown.selectByValue(string);
		
		txtPrimaryPhoneNumber.clear();
		txtPrimaryPhoneNumber.sendKeys(phonenumber);
		
	}
	
	public void setSecondaryPhoneNumber(String string, String phonenumber) {

		Select dropdown = new Select (selectSecondaryPhoneName);
		dropdown.selectByValue(string);
		
		txtPrimaryPhoneNumber.clear();
		txtPrimaryPhoneNumber.sendKeys(phonenumber);
	}

	public void clickCopyBillingAddress() {
		
		btnCopyBillingAddress.click();
		
	}

	public void select1099Required(String string) {
		Select dropdown = new Select (select1099Ind);
		dropdown.selectByValue(string);
		
	}
	
	public void selectTaxIDType(String taxidtype) {
		
		Select dropdown = new Select (selectTaxIDtype);
		dropdown.selectByValue(taxidtype);
		
	}
	

	public void setTaxID(String taxid) {
		txtTaxId.sendKeys(taxid);
		
	}

	
	public void selectPaymentPreference(String paymentPreference) {
		
		Select dropdown = new Select (selectpaymentPreference);
		dropdown.selectByValue(paymentPreference);
		
	}
	
	public void clickSaveBtn() {
		
		btnProviderDetailSave.click();
		
	}

	
	public void setNewTerrirotyLocationDetails(String state, String type, String description) {
		
		Select stateDropdown = new Select (selectTerritoryState);
		stateDropdown.selectByValue(state);
		
		Select typeDropdown = new Select (selectType);
		typeDropdown.selectByValue(state);
		
		txtTerritoryDescriptionDetails.sendKeys(description);
			
		
	}

	public void setLicenseDetails(String LicState, String licType, String licensenumber, String licensexp, String liceStatus) {
		
		
		Select licStateDropdown = new Select (selectProviderLicState);
		licStateDropdown.selectByValue(LicState);
		
		Select licTypeDropdown = new Select (selectProviderLicType);
		licTypeDropdown.selectByValue(licType);
		
		txtLicenseNumber.clear();
		txtLicenseNumber.sendKeys(licensenumber);
		
		txtLicExpriationDate.clear();
		txtLicExpriationDate.sendKeys(licensexp);
		
		Select licStatusDropdown = new Select (selectLicStatus);
		
		licStatusDropdown.selectByVisibleText(liceStatus);
		
		btnLicSave.click();
		
		
		
	}


	public WebElement getSaveConfirmationElement() {

		return lblProviderCode;
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
		
		linkSignOut.click();
	}


	
}

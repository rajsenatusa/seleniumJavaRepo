package testCases;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import Utilities.DataProviders;
import pageObjects.InClaimsMaintenancePage;
import pageObjects.InHomePage;
import testBase.testBaseClass;

public class TC03_AddProviderCodeInformation extends testBaseClass {
	
    final String RESULTS_FOLDER = "DataDrivenResults";
    final String RESULTS_FILE = RESULTS_FOLDER + "/TestResults.xlsx";


    @Test(dataProvider = "providerData", dataProviderClass = DataProviders.class, groups = {"datadriven"})
    public void verify_addProviderCode(String username, String password, String firstname, String lastname, String phonenumber,
                                     String licensenumber, String licensexp, String emailaddress, String manager, String roleofuser) {

        logger.info(" **Add Provider Code Test Started! **");

        try {
            validateInputData(username, password, firstname, lastname, phonenumber, licensenumber, licensexp, emailaddress, manager, roleofuser);

            // Log in to the application
            InHomePage hp = new InHomePage(getDriver());
            logger.info("Logging in with username: " + username);
            hp.loginInsuranceNow(username, password);

            // Navigate to Claims Maintenance Page
            logger.info("Navigating to Claims Maintenance page...");
            InClaimsMaintenancePage cmp = new InClaimsMaintenancePage(getDriver());
            cmp.navigateToNewProviderPage();

            // Generate provider number
            String providerCode = generateProviderNumber("CA0", 4);
            logger.info("Generated Provider Code: " + providerCode);

            fillProviderDetails(cmp, providerCode, firstname, lastname, phonenumber);
            // Validate that the provider code is saved correctly
            validateProviderCode(cmp, providerCode);
            // Add new territory location
            addTerritoryLocation(cmp);
            // Handle license details
            handleLicenseDetails(cmp, licensenumber, licensexp);
            // Write results to Excel
                        
            writeResultsToExcel(username, password, firstname, lastname, phonenumber, licensenumber, licensexp, emailaddress, manager, roleofuser, providerCode);

            // Sign out
            cmp.SignOutInsuranceNow();
            logger.info("Provider code test completed successfully.");

        } catch (SkipException e) {
            logger.warn("Test skipped: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getMessage(), e);
            Assert.fail("Test failed due to an exception: " + e.getMessage());
        } finally {
            logger.info(" **Add Provider Code Test Completed! **");
        }
    }

    private void validateInputData(String... fields) {
        for (String field : fields) {
            if (field == null || field.isEmpty()) {
                logger.error("Missing required input data. Skipping test.");
                throw new SkipException("Skipping test due to missing required data.");
            }
        }
    }

    private void fillProviderDetails(InClaimsMaintenancePage cmp, String providerCode, String firstname, String lastname, String phonenumber) {
        cmp.setProviderNumber(providerCode);
        cmp.selectProviderType("Adjuster");
        cmp.setProviderStatusDate(getCurrentDate("MM/dd/yyyy"));
        cmp.selectStatus("Active");
        cmp.selectBusinessType("Individual");
        cmp.selectAllowCombinedPayment("Yes");
        String fullName = generateFullName(firstname, lastname);
        cmp.setBusinessName(fullName);
        cmp.setName(fullName);
        cmp.setDBAName(fullName);
        cmp.setStreeAddress("5424 Bay Center Dr", "Tampa", "FL", "33609");
        cmp.setPrimaryPhoneNumber("Mobile", phonenumber);
        cmp.setSecondaryPhoneNumber("Mobile", phonenumber);
        cmp.clickCopyBillingAddress();
        cmp.select1099Required("No");
        cmp.selectTaxIDType("SSN");
        cmp.selectPaymentPreference("Check");
        cmp.clickSaveBtn();
    }

    private void validateProviderCode(InClaimsMaintenancePage cmp, String providerCode) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(50));
        wait.until(ExpectedConditions.textToBePresentInElement(cmp.getSaveConfirmationElement(), providerCode));

        String capturedProviderCode = cmp.getProviderCode();
        Assert.assertEquals(capturedProviderCode, providerCode, "Provider Code mismatch after saving.");
        logger.info("Provider code successfully added and verified: " + capturedProviderCode);
    }

    private void addTerritoryLocation(InClaimsMaintenancePage cmp) {
        cmp.setNewTerrirotyLocationDetails("FL", "State", "Adding Territory Location");
    }

    private void handleLicenseDetails(InClaimsMaintenancePage cmp, String licensenumber, String licensexp) {
        if (licensexp == null || licensexp.isEmpty()) {
            cmp.setLicenseDetails("FL", "PCAgent", licensenumber, "12/31/9999", "License is valid");
        } else {
            String formattedLicenseExp = convertDateToExpectedFormat(licensexp);
            LocalDate expirationDate = LocalDate.parse(formattedLicenseExp, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
            LocalDate today = LocalDate.now();

            if (expirationDate.isBefore(today)) {
                cmp.setLicenseDetails("FL", "PCAgent", licensenumber, formattedLicenseExp, "Not Licensed");
            } else {
                cmp.setLicenseDetails("FL", "PCAgent", licensenumber, formattedLicenseExp, "License is valid");
            }
        }
    }

    private void writeResultsToExcel(String username, String password, String firstname, String lastname,
                                     String phonenumber, String licensenumber, String licensexp, String emailaddress,
                                     String manager, String roleofuser, String providerCode) throws IOException {

        // Ensure results folder exists
        File resultsDir = new File(RESULTS_FOLDER);
        if (!resultsDir.exists()) resultsDir.mkdirs();
       
        // Set up workbook and sheet
        Workbook workbook = getWorkbook();
        Sheet sheet = workbook.getSheetAt(0);
        
        // Write test data row to Excel
        writeDataRow(sheet, username,  password,  firstname,  lastname,
                 phonenumber,  licensenumber,  licensexp,  emailaddress,
                 manager,  roleofuser,  providerCode);

        // Write changes to Excel file
        try (FileOutputStream fos = new FileOutputStream(RESULTS_FILE)) {
            workbook.write(fos);
        }
        workbook.close();
    } 
  

	private Workbook getWorkbook() throws IOException {
        File file = new File(RESULTS_FILE);
        Workbook workbook;

        if (file.exists() && file.length() > 0) {
            try (FileInputStream fis = new FileInputStream(file)) {
                workbook = WorkbookFactory.create(fis);
            }
        } else {
            workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Test Results");
            createHeaderRow(sheet);
        }
        return workbook;
	}

	private void createHeaderRow(Sheet sheet) {
		Row headerRow = sheet.createRow(0);
        String[] headers = {"Username", "Password", "First Name", "Last Name", "Phone Number", "License #",
                            "License Expiration Date", "Email Address", "Manager", "Role", "Provider Code"};
        for (int i = 0; i < headers.length; i++) {
            headerRow.createCell(i).setCellValue(headers[i]);
        }
	}
	
	private void writeDataRow(Sheet sheet, String username, String password, String firstname, String lastname,
			String phonenumber, String licensenumber, String licensexp, String emailaddress, String manager,
			String roleofuser, String providerCode) {
		 int rowCount = sheet.getLastRowNum();
	        Row row = sheet.createRow(rowCount + 1);

	        String[] values = {username, password, firstname, lastname, phonenumber, licensenumber, licensexp,
	                           emailaddress, manager, roleofuser, providerCode};

	        for (int i = 0; i < values.length; i++) {
	            row.createCell(i).setCellValue(values[i]);
	        }
	    }
}

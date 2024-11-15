package testCases;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import freemarker.template.utility.DateUtil;
import pageObjects.InClaimsMaintenancePage;
import pageObjects.InHomePage;
import testBase.testBaseClass;

public class TC03_AddProviderCodeInformation extends testBaseClass {
	

    @Test(dataProvider = "providerData", dataProviderClass = DataProviders.class, groups = {"datadriven"})
    public void verify_copy_provider(String username, String password, String firstname, String lastname, String phonenumber,
                                     String licensenumber, String licensexp, String emailaddress, String manager, String roleofuser) {

        logger.info(" **Copy Provider Code Test Started! **");      
        
        try {
        	
            // Logging input data for visibility
            logger.info("User Data: " + String.format("Username: %s, Password: %s, FirstName: %s, LastName: %s", username, password, firstname, lastname));

            // Check for null data in provider fields
            if (username == null || password == null || firstname == null || lastname == null ||
                phonenumber == null || licensenumber == null || licensexp == null ||
                emailaddress == null || manager == null || roleofuser == null) {

                logger.error("Missing required data. Skipping test.");
                throw new SkipException("Skipping test due to missing required data.");
            }

            // Home Page - Log in
            InHomePage hp = new InHomePage(driver);
            logger.info("Entering login credentials for user: " + username );
            hp.loginInsuranceNow(username, password);


            // Navigate to Claims Maintenance Page
            logger.info("Navigating to Claims Maintenance page...");
            InClaimsMaintenancePage cmp = new InClaimsMaintenancePage(driver);
            cmp.navigateToNewProviderPage();
            
            // Generate provider number
            
            String ProviderCode = generateProviderNumber("CA0", 4);
            logger.info("Generated Provider Code: " + ProviderCode);
            cmp.setProviderNumber(ProviderCode);
            
            cmp.selectProviderType("Adjuster");
            String currentDate = getCurrentDate("MM/dd/yyyy");
            cmp.setProviderStatusDate(currentDate);
                        
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
            cmp.select1099Required("Yes");
            cmp.selectTaxIDType("SSN");
            cmp.clickSaveBtn();
            
//            Search for Provider and Copy
//            cmp.selectSearchbyList();
//            cmp.setSearchText("CA0TYNA");
//            cmp.clickSearchBtn();  
//  	      cmp.clickProviderCodeByText("CA0TYNA");
//	          cmp.clickCopyBtn();
//            
//            cmp.setBusinessName(fullName);
//            cmp.setName(fullName);
//            cmp.setDBAName(fullName);
//            cmp.setProviderPrimaryPhoneNumber(phonenumber);
//            cmp.setProviderFaxNumber(phonenumber);
//            cmp.setProviderEmailAddress(emailaddress);
//            cmp.setProviderAccounBusinessName(fullName);
//            cmp.setProviderAccounBusinessName2(fullName);
       	
            // Wait for save confirmation
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50)); // Adjust timeout as needed
            wait.until(ExpectedConditions.textToBePresentInElement(cmp.getSaveConfirmationElement(), ProviderCode));

            // Validate that the correct provider code was saved
            String capturedProviderCode = cmp.getProviderCode();
            Assert.assertEquals(capturedProviderCode, ProviderCode, "Provider Code mismatch after saving.");
            logger.info("Provider code successfully added and verified: " + capturedProviderCode);        
            
            // Search the created provider code and update the license details.            
            cmp.clickLicenseChangeLink();
            cmp.selectProviderState("FL");
            cmp.selectLicenseType("PCBroker");
            cmp.enterLicenseNumber(licensenumber);
            
            if (licensexp == null || licensexp.trim().isEmpty()) {
            	
            	String defaultExpirationDate = "12/30/9999"; 
            	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            	LocalDate date = LocalDate.parse(defaultExpirationDate, formatter);
            	cmp.enterLicensexpirationDate (date.format(formatter));
            	
            
            } else {
            	            	
            	cmp.enterLicensexpirationDate (licensexp);
            }
            
            cmp.selectLicenseStatus("VALID");
            cmp.clickSaveBtn();
         
            // Wait for the page to load after clicking copy
            wait.until(ExpectedConditions.visibilityOf(cmp.getProviderFormElement()));
                       
            
            // Log out
            cmp.SignOutInsuranceNow();
            logger.info("Provider code test completed successfully and user signed out.");  
            
          
            // Write the generated provider code to the Excel sheet     
            logger.info("Provider Code details are started written in to excel sheet...");  
            writeResultsToExcel( username,  password,  firstname,  lastname,  phonenumber,
            		licensenumber,  licensexp,  emailaddress,  manager,  roleofuser, capturedProviderCode);
        
            logger.info("Written provider code to Excel: " + capturedProviderCode);
            
    
                    
        } catch (SkipException e) {
            logger.warn("Test Skipped: " + e.getMessage());
            throw e;  // Re-throw to ensure it's logged as skipped in reports

        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getMessage(), e);
            Assert.fail("Test failed due to an exception: " + e.getMessage());

        } finally {
            logger.info(" **Add New Provider Number Test Completed! **");
        }
    }
    
    

	private void writeResultsToExcel(String username, String password, String firstname, String lastname,
			String phonenumber, String licensenumber, String licensexp, String emailaddress, String manager,
			String roleofuser, String capturedProviderCode) throws FileNotFoundException, IOException {

			final String RESULTS_FOLDER = "DataDrivenResults";
			final String RESULTS_FILE = RESULTS_FOLDER + "/TestResults.xlsx";
			
			// Ensure the results folder exists
	        File resultsDir = new File(RESULTS_FOLDER);
	        if (!resultsDir.exists()) {
	            resultsDir.mkdirs();
	        }

	        Workbook workbook;
	        Sheet sheet;

	        // Check if the file exists to either create a new workbook or update the existing one
	        File file = new File(RESULTS_FILE);
	        
	        // Check if the file exists and has a non-zero length
	        if (file.exists() && file.length() > 0) {
	            // File exists and is not empty, open it
	            try (FileInputStream fis = new FileInputStream(file)) {
	                workbook = WorkbookFactory.create(fis);
	                sheet = workbook.getSheetAt(0);
	            }
	            
	        } else {
	            workbook = new XSSFWorkbook();
	            sheet = workbook.createSheet("Test Results");
	            // Create the header row
	            Row headerRow = sheet.createRow(0);
	            headerRow.createCell(0).setCellValue("Username");
	            headerRow.createCell(1).setCellValue("Password");
	            headerRow.createCell(2).setCellValue("First Name");
	            headerRow.createCell(3).setCellValue("Last Name");
	            headerRow.createCell(4).setCellValue("Phone Number");
	            headerRow.createCell(5).setCellValue("License #");
	            headerRow.createCell(6).setCellValue("License Expiration Date");
	            headerRow.createCell(7).setCellValue("Email Address");
	            headerRow.createCell(8).setCellValue("Manager");
	            headerRow.createCell(9).setCellValue("Role");
	            headerRow.createCell(10).setCellValue("Producer Code");

	        }

		        // Write data to the next available row
		        int rowCount = sheet.getLastRowNum();
		        Row row = sheet.createRow(rowCount + 1);	
		        row.createCell(0).setCellValue(username);
		        row.createCell(1).setCellValue(password);
		        row.createCell(2).setCellValue(firstname);
		        row.createCell(3).setCellValue(lastname);
		        row.createCell(4).setCellValue(phonenumber);
		        row.createCell(5).setCellValue(licensenumber);
		        row.createCell(6).setCellValue(licensexp);
		        row.createCell(7).setCellValue(emailaddress);
		        row.createCell(8).setCellValue(manager);
		        row.createCell(9).setCellValue(roleofuser);
		        row.createCell(10).setCellValue(capturedProviderCode);
	       

	        // Write the results back to the file
	        try (FileOutputStream fos = new FileOutputStream(RESULTS_FILE)) {
	            workbook.write(fos);
	        }
	        workbook.close();
	    }
	
}

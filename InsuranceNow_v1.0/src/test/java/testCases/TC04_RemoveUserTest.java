package testCases;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import Utilities.DataProviders;
import pageObjects.InHomePage;
import pageObjects.InUserManagementPage;
import testBase.testBaseClass;

public class TC04_RemoveUserTest extends testBaseClass {

    private static final String RESULTS_FOLDER = "DataDrivenResults";
    private static final String RESULTS_FILE = RESULTS_FOLDER + "/deactivateUserResults.xlsx";

    @Test(dataProvider = "deactivateUserData", dataProviderClass = DataProviders.class, groups = {"datadriven"})
    public void verifyRemoveUser(String username, String password, String firstname, String lastname,
                                 String phonenumber, String licensenumber, String licensexp, String emailaddress,
                                 String manager, String roleofuser, String providercode, String usercode) {

        logger.info("** Deactivate User Test Started! **");

        try {
            
         // Step 1: Validate input data
            validateInputData(
                    new String[]{"Username", username},
                    new String[]{"Password", password},
                    new String[]{"First Name", firstname},
                    new String[]{"Last Name", lastname},
                    new String[]{"Email Address", phonenumber},
                    new String[]{"License #", licensenumber},
                    new String[]{"License Expiration Date", licensexp}, 
                    new String[]{"Email Address", emailaddress},
                    new String[]{"Manager", manager},
                    new String[]{"Role", roleofuser},
                    new String[]{"Provider Code", providercode},
                    new String[]{"User Code", usercode}
            );

            // Initialize Page Objects
            InHomePage homePage = new InHomePage(getDriver());
            InUserManagementPage userManagementPage = new InUserManagementPage(getDriver());

            // Login and navigate to User Management page
            logger.info("Logging in as: " + username);
            homePage.loginInsuranceNow(username, password);
            userManagementPage.navigateToUserManagementPage();

            // Search for the user to deactivate
            logger.info("Searching user with details: FirstName=" + firstname + ", LastName=" + lastname + ", UserCode=" + usercode);
            userManagementPage.searchUser(usercode);
            
            // Step 4: Perform password reset
            logger.info("Resetting password for user: " + usercode);
            String removeUserStatus = userManagementPage.processDeactivationUserCode(usercode);
            logger.info("Password reset status for user " + usercode + ": " + removeUserStatus);

            // Step 5: Record test results in Excel
            writeResultsToExcel( username,  password,  firstname,  lastname,
                     phonenumber,  licensenumber,  licensexp,  emailaddress,
                     manager,  roleofuser,  providercode,  usercode, removeUserStatus);

            // Step 6: Logout after operation
            logger.info("User signed out successfully.");

            // Check if the user is active and deactivate if necessary
            

            logger.info("User signed out successfully.");

        } catch (SkipException e) {
            logger.warn("Test Skipped: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Test failed due to exception: " + e.getMessage(), e);
            Assert.fail("Test failed with exception: " + e.getMessage());
        } finally {
            logger.info("** Deactivate User Test Completed! **");
        }
    }

    /**
     * Validates input data for null or empty values.
     *
     * @param fields Array of String pairs where [0] = field name, [1] = field value
     */
    private void validateInputData(String[]... fields) {
        for (String[] field : fields) {
            String fieldName = field[0];
            String fieldValue = field[1];
            if (fieldValue == null || fieldValue.trim().isEmpty()) {
                String message = "Invalid input data: Missing required field '" + fieldName + "'.";
                logger.error(message);
                throw new SkipException(message);
            }
        }
    }



    private void writeResultsToExcel(String username, String password, String firstname, String lastname,
                                     String phonenumber, String licensenumber, String licensexp, String emailaddress,
                                     String manager, String roleofuser, String providercode, String usercode,
                                     String removeUserStatus) throws IOException {

        // Ensure results folder exists
        File resultsDir = new File(RESULTS_FOLDER);
        if (!resultsDir.exists()) resultsDir.mkdirs();

        // Set up workbook and sheet
        Workbook workbook = getWorkbook();
        Sheet sheet = workbook.getSheetAt(0);

        // Write test data row to Excel
        writeDataRow(sheet, username, password, firstname, lastname, phonenumber, licensenumber, licensexp,
                     emailaddress, manager, roleofuser, providercode, usercode, removeUserStatus);

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
                            "License Expiration Date", "Email Address", "Manager", "Role", "Provider Code",
                            "User Code", "Deactivation Status"};
        for (int i = 0; i < headers.length; i++) {
            headerRow.createCell(i).setCellValue(headers[i]);
        }
    }

    private void writeDataRow(Sheet sheet, String username, String password, String firstname, String lastname,
                              String phonenumber, String licensenumber, String licensexp, String emailaddress,
                              String manager, String roleofuser, String providercode, String usercode,
                              String removeUserStatus) {
        int rowCount = sheet.getLastRowNum();
        Row row = sheet.createRow(rowCount + 1);

        String[] values = {username, password, firstname, lastname, phonenumber, licensenumber, licensexp,
                           emailaddress, manager, roleofuser, providercode, usercode, removeUserStatus};

        for (int i = 0; i < values.length; i++) {
            row.createCell(i).setCellValue(values[i]);
        }
    }
}

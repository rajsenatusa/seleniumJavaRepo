package testCases;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import Utilities.DataProviders;
import pageObjects.InHomePage;
import pageObjects.InUserManagementPage;
import testBase.testBaseClass;

public class TC05_ResetPassword extends testBaseClass {

    private static final String RESULTS_FOLDER = "DataDrivenResults";
    private static final String RESULTS_FILE = RESULTS_FOLDER + "/resetPasswordResults.xlsx";

    @Test(dataProvider = "resetPasswordUserData", dataProviderClass = DataProviders.class, groups = {"datadriven"})
    public void verifyPasswordReset(String username, String password, String firstname, String lastname,
                                    String emailaddress, String usercode, String newpassword) {
        logger.info("** Starting Password Reset Test **");

        try {
            // Step 1: Validate input data
            validateInputData(username, password, firstname, lastname, emailaddress, usercode, newpassword);

            // Step 2: Initialize page objects
            InHomePage homePage = new InHomePage(getDriver());
            InUserManagementPage userManagementPage = new InUserManagementPage(getDriver());

            // Step 3: Login and navigate to User Management page
            loginAndNavigateToUserManagementPage(homePage, username, password, userManagementPage);

            // Step 4: Search for the user and perform password reset or addition
            handleUserManagement(userManagementPage, username, password, 
            		usercode, firstname, lastname, emailaddress, newpassword);

            // Step 5: Logout after operation
            logger.info("User signed out successfully.");
            
        } catch (SkipException e) {
            logger.warn("Test Skipped: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Test failed due to exception: ", e);
            Assert.fail("Test failed with exception: " + e.getMessage());
        } finally {
            logger.info("** Password Reset Test Completed **");
        }
    }

    private void validateInputData(String username, String password, String firstname, String lastname, 
                                   String emailaddress, String usercode, String newpassword) {
        if (username == null || username.trim().isEmpty() ||
            password == null || password.trim().isEmpty() ||
            firstname == null || firstname.trim().isEmpty() ||
            lastname == null || lastname.trim().isEmpty() ||
            emailaddress == null || emailaddress.trim().isEmpty() ||
            usercode == null || usercode.trim().isEmpty() ||
            newpassword == null || newpassword.trim().isEmpty()) {
            String message = "Invalid input data: One or more required fields are missing.";
            logger.error(message);
            Assert.fail(message);
        }
    }

    private void loginAndNavigateToUserManagementPage(InHomePage homePage, String username, String password,
                                                      InUserManagementPage userManagementPage) {
        logger.info("Logging in as user: " + username);
        homePage.loginInsuranceNow(username, password);
        userManagementPage.navigateToUserManagementPage();
    }

    private void handleUserManagement(InUserManagementPage userManagementPage, String username, String password, String usercode, String firstname,
                                      String lastname, String emailaddress, String newpassword) throws IOException {
        logger.info("Searching user with details: FirstName=" + firstname + ", LastName=" + lastname + ", UserCode=" + usercode);
        boolean testResults = userManagementPage.searchUser(usercode);
        
        String status = testResults ? resetPassword(userManagementPage, usercode, newpassword) :
                                      addNewUser(userManagementPage, usercode, firstname, lastname, emailaddress, newpassword);

        writeResultsToExcel(username, password, firstname, lastname, emailaddress, usercode, newpassword, status);
    }

    private String resetPassword(InUserManagementPage userManagementPage, String usercode, String newpassword) {
        logger.info("Resetting password for user: " + usercode);
        return userManagementPage.processResetPasswordForUserCode(usercode, newpassword);
    }

    private String addNewUser(InUserManagementPage userManagementPage, String usercode, String firstname, 
                              String lastname, String emailaddress, String newpassword) {
        logger.info("Adding new user: " + usercode);
        return userManagementPage.processAddUsers(usercode, firstname, lastname, emailaddress, newpassword);
    }

    private void writeResultsToExcel(String username, String password, String firstname, String lastname, String emailaddress, String usercode, String newpassword, String status) throws IOException {
        ensureResultsFolderExists();
        Workbook workbook = getOrCreateWorkbook();
        Sheet sheet = workbook.getSheetAt(0);

        addDataRow(sheet, username, password, firstname, lastname, emailaddress, usercode, newpassword, status);

        try (FileOutputStream fos = new FileOutputStream(RESULTS_FILE)) {
            workbook.write(fos);
        } finally {
            workbook.close();
        }
    }

    // Helper methods for Excel file handling
    private void ensureResultsFolderExists() {
        File resultsDir = new File(RESULTS_FOLDER);
        if (!resultsDir.exists()) {
            resultsDir.mkdirs();
        }
    }

    private Workbook getOrCreateWorkbook() throws IOException {
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
        String[] headers = {"User Name", "Password", "First Name", "Last Name", "Email Address", 
        					"User Code",  "New Password", "Password Reset Status"};
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            headerRow.createCell(i).setCellValue(headers[i]);
        }
    }

    private void addDataRow(Sheet sheet, String username, String password, String firstname, String lastname, 
    		String emailaddress, String usercode, String newpassword, String status) {
        int rowCount = sheet.getLastRowNum() + 1;
        Row row = sheet.createRow(rowCount);
        row.createCell(0).setCellValue(username);
        row.createCell(1).setCellValue(password);
        row.createCell(2).setCellValue(firstname);
        row.createCell(3).setCellValue(lastname);
        row.createCell(4).setCellValue(emailaddress);
        row.createCell(5).setCellValue(usercode);
        row.createCell(6).setCellValue(newpassword);
        row.createCell(7).setCellValue(status);
    }
}

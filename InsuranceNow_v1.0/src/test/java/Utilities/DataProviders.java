package Utilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.DataProvider;

public class DataProviders {

	private static final Logger logger = LoggerFactory.getLogger(DataProviders.class);
	
	// Data Provider 1
	@DataProvider(name = "providerData")
	public Object[][] getProviderData() throws IOException {
	    return loadExcelData(Config.PROVIDER_DATA_PATH, "Sheet1");
	}

	//Data Provider 2
	@DataProvider(name = "loginData")
	public Object[][] getLoginData() throws IOException {
		return loadExcelData(Config.LOGIN_DATA_PATH, "Sheet1");
	}
	
	//Data Provider 3

	@DataProvider(name = "deactivateUserData")
	public Object[][] getDeactivateUserData() throws IOException {
		return loadExcelData(Config.DEACTIVATE_USER_DATA_PATH, "Sheet1");
	}

	// DataProvider 4
	@DataProvider(name = "resetPasswordUserData")
	public Object[][] getResetPasswordUserData() throws IOException {
		return loadExcelData(Config.RESET_PASSWORD_USER_DATA_PATH, "Sheet1");
	}

	private String[][] loadExcelData(String filePath, String sheetName) throws IOException {
	    try {
	        logger.info("Loading data from file: {}", filePath);
	        ExcelUtility xlutil = new ExcelUtility(filePath);

	        int totalRows = xlutil.getRowCount(sheetName);
	        int totalCols = xlutil.getColumnCount(sheetName, 1);

	        logger.info("Loaded {} rows and {} columns from sheet: {}", totalRows, totalCols, sheetName);

	        // Create a dynamic list to hold valid rows
	        List<String[]> validRows = new ArrayList<>();

	        for (int i = 1; i <= totalRows; i++) { // Start from 1 to skip header
	            boolean isRowEmpty = true;
	            String[] rowData = new String[totalCols];

	            for (int j = 0; j < totalCols; j++) {
	                String cellData = xlutil.getCellData(sheetName, i, j);
	                if (cellData != null && !cellData.trim().isEmpty()) {
	                    isRowEmpty = false; // Row has data
	                }
	                rowData[j] = (cellData != null) ? cellData.trim() : ""; // Store trimmed or empty string
	            }

	            if (!isRowEmpty) {
	                validRows.add(rowData); // Add only non-empty rows
	            }
	        }

	        // Convert the list to a 2D array
	        return validRows.toArray(new String[0][]);
	    } catch (Exception e) {
	        logger.error("Error while loading data from Excel file: {}", filePath, e);
	        throw e;
	    }
	}

}

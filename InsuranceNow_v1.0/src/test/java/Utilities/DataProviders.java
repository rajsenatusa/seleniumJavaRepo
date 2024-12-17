package Utilities;

import java.io.IOException;

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

	        String[][] data = new String[totalRows][totalCols];
	        for (int i = 1; i <= totalRows; i++) {
	            // Check if the row is empty
	            if (xlutil.getRow(sheetName, i) == null) {
	                continue;  // Skip empty rows
	            }
	        	
	            for (int j = 0; j < totalCols; j++) {
	                String cellData = xlutil.getCellData(sheetName, i, j);
	                if (cellData == null || cellData.isEmpty()) {
	                    // Handle empty cell if needed
	                }
	                data[i - 1][j] = cellData;  // Add cell data to the array
	            }
	            
	          
	        }

	        return data;
	    } catch (Exception e) {
	        logger.error("Error while loading data from Excel file: {}", filePath, e);
	        throw e;
	    }
	}

}

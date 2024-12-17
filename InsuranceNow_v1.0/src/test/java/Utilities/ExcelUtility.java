package Utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtility {

    private static final Logger logger = LoggerFactory.getLogger(ExcelUtility.class);

    private final String filePath;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet; 

    public ExcelUtility(String filePath) {
        this.filePath = filePath;							//Open the file
        this.workbook = loadWorkbook(filePath);				//Load the workbook
        this.sheet = workbook.getSheetAt(0);  				//// Get the first sheet 
    }

    /**
     * Loads an existing workbook or creates a new one if the file does not exist.
     */
    private XSSFWorkbook loadWorkbook(String path) {
        try (FileInputStream fis = new FileInputStream(path)) {
            return new XSSFWorkbook(fis);
        } catch (FileNotFoundException e) {
            logger.warn("File not found at path: {}. Creating a new workbook.", path);
            return new XSSFWorkbook();
        } catch (IOException e) {
            throw new RuntimeException("Error loading workbook: " + path, e);
        }
    }

    /**
     * Saves the current workbook to the specified file path.
     */
    private void saveWorkbook() {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            workbook.write(fos);
        } catch (IOException e) {
            throw new RuntimeException("Error saving workbook to file: " + filePath, e);
        }
    }

    /**
     * Reads data from the specified sheet into a list of object arrays.
     */
    public List<Object[]> readData(String sheetName) {
        List<Object[]> data = new ArrayList<>();
        XSSFSheet sheet = getOrCreateSheet(sheetName);

        for (Row row : sheet) {
            int lastCell = row.getLastCellNum();
            Object[] rowData = new Object[lastCell];
            for (int i = 0; i < lastCell; i++) {
                rowData[i] = getCellValueAsString(row.getCell(i));
            }
            data.add(rowData);
        }
        return data;
    }

    /**
     * Writes data to a specific cell in a sheet.
     */
    public void writeCellData(String sheetName, int rowNum, int colNum, String value) {
        XSSFSheet sheet = getOrCreateSheet(sheetName);
        Row row = getOrCreateRow(sheet, rowNum);
        Cell cell = row.createCell(colNum);
        cell.setCellValue(value);
        saveWorkbook();
    }

    /**
     * Clears data from a specific cell in a sheet.
     */
    public void clearCellData(String sheetName, int rowNum, int colNum) {
        writeCellData(sheetName, rowNum, colNum, "");
    }

    /**
     * Retrieves cell value as a string.
     */
    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }
        DataFormatter formatter = new DataFormatter();
        return formatter.formatCellValue(cell);
    }

    /**
     * Retrieves the total rows in a sheet.
     */
    public int getRowCount(String sheetName) {
        return getOrCreateSheet(sheetName).getLastRowNum() + 1;
    }

    /**
     * Retrieves the total columns in a specified row of a sheet.
     */
    public int getColumnCount(String sheetName, int rowNum) {
        Row row = getOrCreateSheet(sheetName).getRow(rowNum);
        return row != null ? row.getLastCellNum() : 0;
    }

    /**
     * Highlights a cell with the specified color.
     */
    public void fillCellColor(String sheetName, int rowNum, int colNum, IndexedColors color) {
        XSSFSheet sheet = getOrCreateSheet(sheetName);
        Row row = getOrCreateRow(sheet, rowNum);
        Cell cell = row.getCell(colNum);

        if (cell == null) {
            cell = row.createCell(colNum);
        }

        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(color.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        cell.setCellStyle(style);
        saveWorkbook();
    }

    public void fillGreenColor(String sheetName, int rowNum, int colNum) {
        fillCellColor(sheetName, rowNum, colNum, IndexedColors.GREEN);
    }

    public void fillRedColor(String sheetName, int rowNum, int colNum) {
        fillCellColor(sheetName, rowNum, colNum, IndexedColors.RED);
    }

    /**
     * Returns or creates a sheet by name.
     */
    private XSSFSheet getOrCreateSheet(String sheetName) {
        XSSFSheet sheet = workbook.getSheet(sheetName);
        if (sheet == null) {
            sheet = workbook.createSheet(sheetName);
        }
        return sheet;
    }

    /**
     * Returns or creates a row by index in the given sheet.
     */
    private Row getOrCreateRow(Sheet sheet, int rowNum) {
        Row row = sheet.getRow(rowNum);
        if (row == null) {
            row = sheet.createRow(rowNum);
        }
        return row;
    }

    /**
     * Finds the next empty row in the specified sheet.
     */
    public int getNextEmptyRow(String sheetName) {
        XSSFSheet sheet = getOrCreateSheet(sheetName);
        return sheet.getLastRowNum() + 1;
    }

    /**
     * Finds the next empty cell in a specific column of a sheet.
     */
    public int getNextEmptyCellInColumn(String sheetName, int columnIndex) {
        XSSFSheet sheet = getOrCreateSheet(sheetName);

        for (int i = 0; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null || row.getCell(columnIndex) == null || row.getCell(columnIndex).getCellType() == CellType.BLANK) {
                return i;
            }
        }
        return sheet.getLastRowNum() + 1;
    }

    /**
     * Closes the workbook resource.
     */
    public void close() {
        try {
            if (workbook != null) {
                workbook.close();
            }
        } catch (IOException e) {
            logger.error("Error closing workbook", e);
        }
    }
    
    public String getCellData(String sheetName, int rowNum, int colNum) {
        try (XSSFWorkbook wb = openWorkbook()) {
            XSSFSheet sheet = wb.getSheet(sheetName);

            if (sheet == null) {
                throw new IllegalArgumentException("Sheet '" + sheetName + "' does not exist.");
            }

            Row row = sheet.getRow(rowNum);
            if (row == null) {
                return ""; // Row does not exist, return empty string
            }

            Cell cell = row.getCell(colNum);
            if (cell == null) {
                return ""; // Cell does not exist, return empty string
            }

            DataFormatter formatter = new DataFormatter();
            return formatter.formatCellValue(cell); // Converts the cell value to a string
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }


    private XSSFWorkbook openWorkbook() throws IOException {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            return new XSSFWorkbook(fis);
        }
    }
    
    /**
     * Returns the row at the specified index in the sheet.
     * 
     * @param sheetName The name of the sheet (for this example, we're not using it directly)
     * @param rowIndex The row index to retrieve (0-based index)
     * @return The XSSFRow object corresponding to the row at the specified index
     */
    public XSSFRow getRow(String sheetName, int rowIndex) {
        if (sheet == null) {
            throw new IllegalStateException("Sheet is not loaded.");
        }

        // Ensure the rowIndex is within bounds
        if (rowIndex < 0 || rowIndex >= sheet.getPhysicalNumberOfRows()) {
            return null;  // Return null if the row index is out of bounds
        }

        // Get the row from the sheet at the specified index
        XSSFRow row = sheet.getRow(rowIndex);
        return row;  // Return the row object
    }

   


}

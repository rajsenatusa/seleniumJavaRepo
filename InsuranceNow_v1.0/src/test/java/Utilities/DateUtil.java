package Utilities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {
	
    // Method to get current date in a specific format
	
	 // Example usage
    //String date1 = getCurrentDate("yyyy-MM-dd"); // Output: 2024-12-03
    //String date2 = getCurrentDate("dd/MM/yyyy"); // Output: 03/12/2024
    //String date3 = getCurrentDate("MMMM dd, yyyy"); // Output: December 03, 2024
    
    public String getCurrentDate(String format) {
        // Get the current date
        LocalDate currentDate = LocalDate.now();
        
        // Create a formatter with the provided format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        
        // Format the current date and return as a string
        return currentDate.format(formatter);
    }


}

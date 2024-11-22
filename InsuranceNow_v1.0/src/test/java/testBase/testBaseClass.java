package testBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import io.github.bonigarcia.wdm.WebDriverManager;

public class testBaseClass {
	
	// this base class is the parent of all test case base, 
	// this class contains all the reusable methods in used with in test case.
	
	public static WebDriver driver;
	public Logger logger;
	public Properties prop;
		
	@SuppressWarnings("deprecation")
	@BeforeClass (groups = {"master", "regression", "sanity", "datadriven"})
	@Parameters({"os","browser"})
	
	public void setup (String os, String br) throws IOException {
		
    	//Loading Config.properties file
		
		try {
			FileInputStream input = new FileInputStream(".//src///test//resources//config.properties");
			prop = new Properties();
			prop.load(input);
			
			
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		}
		
			
		logger = LogManager.getLogger(this.getClass()); //getting logger
		
		//if test needs to be run remotely 
		

		if(prop.getProperty("EXECUTION_ENV").equalsIgnoreCase("remote"))
		{
			DesiredCapabilities capabilities=new DesiredCapabilities();
			
			//operating system
			if(os.equalsIgnoreCase("windows"))
			{
				capabilities.setPlatform(Platform.WIN10);
			}
			else if (os.equalsIgnoreCase("mac"))
			{
				capabilities.setPlatform(Platform.MAC);
			}
			else
			{
				System.out.println("No matching os");
				return;
			}
			 
			// get browser
			switch (br.toLowerCase()) 
			{
				case "chrome": capabilities.setBrowserName("chrome"); break;
				case "edge": capabilities.setBrowserName("MicrosoftEdge"); break;
				default: System.out.println("No matching browser"); return;	
			}
			
			driver= new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),capabilities);			
			driver.manage().deleteAllCookies();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			
			System.out.println("TEST RUNNING AT: " + prop.getProperty("Environment"));
			System.out.println("LAUNCHING THE URL: " + prop.getProperty("EnvironmentURL"));
			driver.get(prop.getProperty("EnvironmentURL"));
			System.out.println("Browser launched successfully.");
	    
			driver.manage().window().maximize();
			
		}
		
		
		//if test needs to be run local 
		if(prop.getProperty("EXECUTION_ENV").equalsIgnoreCase("local"))
		{
			switch (br.toLowerCase())
			{
			
			case "chrome" : 
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver(); 
				break;
				
			case "edge" : 
				WebDriverManager.edgedriver().setup();
				driver = new EdgeDriver(); 
				break;
				
			case "firefox" : 
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver(); 
				break;		
			}
			
			driver.manage().deleteAllCookies();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			
			System.out.println("TEST RUNNING AT: " + prop.getProperty("Environment"));
			System.out.println("LAUNCHING THE URL: " + prop.getProperty("EnvironmentURL"));
			driver.get(prop.getProperty("EnvironmentURL"));
			System.out.println("Browser launched successfully.");	 
			
			driver.manage().window().maximize();
			
		}
				
	}
		    
	@AfterClass (groups = {"master", "regression", "sanity", "datadriven"})
	public void teardown () {
		
		driver.quit();
		
	}
	
		
	// add all reusable methods goes here!!
	
    public static String generateProviderNumber(String prefix, int length) {
        StringBuilder providerCode = new StringBuilder(prefix);
        Random random = new Random();

        // Generate random alphanumeric characters
        for (int i = 0; i < length; i++) {
            int randomCharIndex = random.nextInt(36); // 0-25 for letters, 26-35 for numbers
            char randomChar = (randomCharIndex < 26) ? (char) ('A' + randomCharIndex) : (char) ('0' + (randomCharIndex - 26));
            providerCode.append(randomChar);
        }

        return providerCode.toString();
    }
    
	
    /**
     * Gets the current date in the specified format.
     *
     * @param format The date format as a string (e.g., "MM/dd/yyyy").
     * @return The current date as a formatted string.
     */
    public String getCurrentDate(String format) {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return currentDate.format(formatter);
    }

    /**
     * Generates the fullname from the given first name and Last name.
     *
     * @param first and lastname passed
     * @return retusns full name with space in between first and last name.
     */
	
	public String generateFullName(String firstname, String lastname) {
		String fullName = String.join(" ", firstname, lastname);
		return fullName;
	}

        
    // this method capture screen shot of the failure 
    
	public String captureScreen(String tname) throws IOException {

			String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
					
			TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
			File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
			
			String targetFilePath=System.getProperty("user.dir")+"\\screenshots\\" + tname + "_" + timeStamp + ".png";
			File targetFile=new File(targetFilePath);
			
			sourceFile.renameTo(targetFile);
				
			return targetFilePath;

		}
	
	// Method to convert date to MM/dd/yyyy
    public static String convertDateToExpectedFormat(String inputDate) {
        // List of possible input date formats
        List<DateTimeFormatter> possibleFormats = new ArrayList<>();
        possibleFormats.add(DateTimeFormatter.ofPattern("MM/dd/yyyy"));  // Common U.S. format
        possibleFormats.add(DateTimeFormatter.ofPattern("M/d/yyyy"));   // Single-digit month/day
        possibleFormats.add(DateTimeFormatter.ofPattern("MM-dd-yyyy")); // Dash-separated
        possibleFormats.add(DateTimeFormatter.ofPattern("yyyy-MM-dd")); // ISO format
        possibleFormats.add(DateTimeFormatter.ofPattern("M/d/yy"));     // Two-digit year
        possibleFormats.add(DateTimeFormatter.ofPattern("MM/dd/yy"));   // Two-digit year, standard format

        // Expected output format
        DateTimeFormatter expectedFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        // Try parsing with each format
        for (DateTimeFormatter formatter : possibleFormats) {
            try {
                LocalDate parsedDate = LocalDate.parse(inputDate, formatter);
                return parsedDate.format(expectedFormat); // Return in expected format
            } catch (DateTimeParseException e) {
                // Ignore and try the next format
            }
        }

        // If none of the formats work, return an error or handle it
        throw new IllegalArgumentException("Invalid date format: " + inputDate);
    }


	

}

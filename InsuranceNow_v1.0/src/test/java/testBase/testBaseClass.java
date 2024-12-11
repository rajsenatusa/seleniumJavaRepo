package testBase;

import java.io.File;
import java.io.FileInputStream;
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
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import io.github.bonigarcia.wdm.WebDriverManager;
import pageObjects.InUserManagementPage;
import pageObjects.basePage;

public class testBaseClass {

    public static ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();  // ThreadLocal to handle multi-threaded WebDriver usage
    public Logger logger;  // Logger instance
    public Properties prop;  // Properties to store configuration details
    private InUserManagementPage inUserManagementPage;
    private basePage basePage;

    // Setup method to initialize the driver and necessary page objects
    @BeforeClass(groups = {"master", "regression", "sanity", "datadriven"})
    @Parameters({"os", "browser"}) // Parameters from testng.xml file
    public void setup(String os, String br) throws IOException {
        loadProperties();  // Load configuration properties
        logger = LogManager.getLogger(this.getClass());  // Initialize logger

        // Select the execution environment (remote or local)
        if (prop.getProperty("EXECUTION_ENV").equalsIgnoreCase("remote")) {
            setupRemoteDriver(os, br);  // Setup for remote WebDriver (Selenium Grid or cloud services)
        } else if (prop.getProperty("EXECUTION_ENV").equalsIgnoreCase("local")) {
            setupLocalDriver(br);  // Setup for local WebDriver (on the machine)
        } else {
            throw new IllegalArgumentException("Invalid EXECUTION_ENV value in config.properties");
        }

        configureDriver();  // Configure the driver (timeouts, window size, etc.)

        // Initialize page objects after WebDriver setup
        basePage = new basePage(getDriver());
        inUserManagementPage = new InUserManagementPage(getDriver());

        logger.info("Browser launched successfully and navigated to URL: " + prop.getProperty("APP_URL"));
    }

    // Teardown method to quit the WebDriver after tests
    @AfterClass(groups = {"master", "regression", "sanity", "datadriven"})
    public void teardown() {
        if (getDriver() != null) {
            getDriver().quit();
            threadLocalDriver.remove();  // Cleanup ThreadLocal variable
            logger.info("Driver quit successfully.");
        }
    }

    // Method to get the WebDriver instance from ThreadLocal
    public static WebDriver getDriver() {
        return threadLocalDriver.get();
    }

    // Method to load properties from config.properties file
    private void loadProperties() throws IOException {
        String configFilePath = System.getProperty("user.dir") + "/src/test/resources/config.properties";
        try (FileInputStream input = new FileInputStream(configFilePath)) {
            prop = new Properties();
            prop.load(input);
        } catch (IOException e) {
            logger.error("Failed to load config.properties", e);
            throw e;
        }
    }

    // Setup method for Remote WebDriver (Selenium Grid or cloud services)

    @SuppressWarnings("deprecation")
	private void setupRemoteDriver(String os, String br) throws IOException {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        // Configure platform for Remote WebDriver
        switch (os.toLowerCase()) {
            case "windows":
                capabilities.setPlatform(Platform.WIN10);
                break;
            case "mac":
                capabilities.setPlatform(Platform.MAC);
                break;
            default:
                throw new IllegalArgumentException("Unsupported OS: " + os);
        }

        // Configure browser for Remote WebDriver
        switch (br.toLowerCase()) {
            case "chrome":
                capabilities.setBrowserName("chrome");
                break;
            case "edge":
                capabilities.setBrowserName("MicrosoftEdge");
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser: " + br);
        }

        // Initialize Remote WebDriver
        WebDriver remoteDriver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
        threadLocalDriver.set(remoteDriver);
    }

    // Setup method for Local WebDriver (on the machine)
    private void setupLocalDriver(String br) {
        WebDriver localDriver;

        // Configure local WebDriver based on the selected browser
        switch (br.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--disable-notifications");  // Disable notifications in Chrome
                localDriver = new ChromeDriver(options);
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                localDriver = new EdgeDriver();
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                localDriver = new FirefoxDriver();
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser: " + br);
        }

        threadLocalDriver.set(localDriver);  // Store local WebDriver instance in ThreadLocal
    }

    // Configure WebDriver (timeouts, window size, and navigate to the URL)
    private void configureDriver() {
        WebDriver driver = getDriver();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();  // Maximize the browser window
        driver.get(prop.getProperty("APP_URL"));  // Navigate to the environment URL from properties
    }

    // Helper method to generate a random provider number
    public static String generateProviderNumber(String prefix, int length) {
        StringBuilder providerCode = new StringBuilder(prefix);
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int randomCharIndex = random.nextInt(36);  // Random index to pick character (A-Z, 0-9)
            char randomChar = (randomCharIndex < 26) ? (char) ('A' + randomCharIndex) : (char) ('0' + (randomCharIndex - 26));
            providerCode.append(randomChar);
        }

        return providerCode.toString();
    }

    // Helper method to get the current date in a specified format
    public String getCurrentDate(String format) {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return currentDate.format(formatter);
    }

    // Helper method to generate a full name by combining first name and last name
    public String generateFullName(String firstname, String lastname) {
        return String.join(" ", firstname, lastname);
    }

    // Helper method to capture a screenshot and return its file path
    public String captureScreen(String tname) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        TakesScreenshot takesScreenshot = (TakesScreenshot) getDriver();
        File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);

        String targetFilePath = System.getProperty("user.dir") + "/screenshots/" + tname + "_" + timeStamp + ".png";
        File targetFile = new File(targetFilePath);

        if (sourceFile.renameTo(targetFile)) {
            return targetFilePath;
        } else {
            throw new IOException("Failed to save screenshot.");
        }
    }

    // Helper method to convert an input date to a consistent expected format (MM/dd/yyyy)
    public static String convertDateToExpectedFormat(String inputDate) {
        List<DateTimeFormatter> possibleFormats = new ArrayList<>();
        possibleFormats.add(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        possibleFormats.add(DateTimeFormatter.ofPattern("M/d/yyyy"));
        possibleFormats.add(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        possibleFormats.add(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        possibleFormats.add(DateTimeFormatter.ofPattern("M/d/yy"));
        possibleFormats.add(DateTimeFormatter.ofPattern("MM/dd/yy"));

        DateTimeFormatter expectedFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        for (DateTimeFormatter formatter : possibleFormats) {
            try {
                LocalDate parsedDate = LocalDate.parse(inputDate, formatter);
                return parsedDate.format(expectedFormat);  // Return formatted date
            } catch (DateTimeParseException e) {
                // Continue to the next format if current one fails
            }
        }

        throw new IllegalArgumentException("Invalid date format: " + inputDate);  // If no format matched
    }

    // Getter methods for page objects
    public InUserManagementPage getInUserManagementPage() {
        return inUserManagementPage;
    }

    public basePage getBasePage() {
        return basePage;
    }
}

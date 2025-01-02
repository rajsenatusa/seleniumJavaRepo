package pageObjects;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import testBase.testBaseClass;

// This is the parent class for all page object classes.
public class basePage {
	
	private static final Logger logger = LoggerFactory.getLogger(basePage.class);
	protected WebDriver driver;

    // Constructor to initialize the driver and PageFactory
    public basePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


    // Wait for search results to load (table rows should be visible)
    public void waitForSearchResultsToLoad(List<WebElement> resultTableRows, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(testBaseClass.getDriver(), Duration.ofSeconds(timeoutInSeconds));
        wait.until(ExpectedConditions.visibilityOfAllElements(resultTableRows));
        System.out.println("Search results are now loaded and visible.");    
    }
    
    public boolean checkAndHandleEmptyList(List<WebElement> resultTableRows, int timeoutInSeconds ) {
    	WebDriverWait wait = new WebDriverWait(testBaseClass.getDriver(), Duration.ofSeconds(timeoutInSeconds));
        // Wait for the visibility of all elements in resultTableRows
        wait.until(ExpectedConditions.visibilityOfAllElements(resultTableRows));
        
        // Check if any of the rows contain the text "Empty List"
        for (WebElement row : resultTableRows) {
            if (row.getText().contains("Empty List")) {
                System.out.println("Empty List Found, User Needs to be added to the system.");
                return true; // Indicating "Empty List" is found
            }
        }
        
        // If "Empty List" is not found
        System.out.println("Search results are now loaded and visible.");
        return false; // Indicating "Empty List" is not found
    }


    // Wait for a specific element to be visible within the given timeout
    public WebElement waitForElementToBeVisible(By locator, Duration timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(testBaseClass.getDriver(), timeoutInSeconds);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
 
    // Wait for a specific element to be clickable within the given timeout
    public WebElement waitForElementToBeClickable(By locator, Duration timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(testBaseClass.getDriver(), timeoutInSeconds);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    // Wait for an element to be present in the DOM (but not necessarily visible)
    public WebElement waitForElementToBePresent(By locator, Duration timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(testBaseClass.getDriver(), timeoutInSeconds);
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    // Wait for a condition to be satisfied, can be used with other expected conditions
    public <T> T waitForCondition(ExpectedCondition<T> condition, Duration timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(testBaseClass.getDriver(), timeoutInSeconds);
        return wait.until(condition);
    }

    // Select a dropdown option by visible text
    public String selectDropdownByVisibleText(WebElement dropdownElement, String visibleText) {
        try {
            Select dropdown = new Select(dropdownElement);
            dropdown.selectByVisibleText(visibleText);
            return dropdown.getFirstSelectedOption().getText();
        } catch (Exception e) {
            throw new RuntimeException("Failed to select option by visible text: " + visibleText, e);
        }
    }

    // Select a dropdown option by its value
    public void selectDropdownByValue(WebElement dropdownElement, String value) {
        try {
            Select dropdown = new Select(dropdownElement);
            dropdown.selectByValue(value);
        } catch (Exception e) {
            throw new RuntimeException("Failed to select option by value: " + value, e);
        }
    }

    // Select a dropdown option by its index
    public void selectDropdownByIndex(WebElement dropdownElement, int index) {
        try {
            Select dropdown = new Select(dropdownElement);
            dropdown.selectByIndex(index);
        } catch (Exception e) {
            throw new RuntimeException("Failed to select option by index: " + index, e);
        }
    }

    // Get all dropdown options (returns a list of WebElements)
    public List<WebElement> getAllDropdownOptions(WebElement dropdownElement) {
        try {
            Select dropdown = new Select(dropdownElement);
            return dropdown.getOptions();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get options from dropdown.", e);
        }
    }

    // Check if the dropdown allows multiple selections (multi-select dropdown)
    public boolean isDropdownMultiSelect(WebElement dropdownElement) {
        try {
            Select dropdown = new Select(dropdownElement);
            return dropdown.isMultiple();
        } catch (Exception e) {
            throw new RuntimeException("Failed to check if dropdown is multi-select.", e);
        }
    }

    // Example method to click a WebElement, useful if you have many actions
    public void clickElement(WebElement element) {
    	 try {
    	        logger.info("Clicking on element: {}", element);
    	        element.click();
    	    } catch (Exception e) {
    	        logger.error("Failed to click element.", e);
    	        throw new RuntimeException("Failed to click element: " + element, e);
    	    }
    }

    // Example method to send keys to an element (e.g., input field)
    public void sendKeysToElement(WebElement element, String keys) {
        try {
            element.clear();
            element.sendKeys(keys);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send keys to element: " + element, e);
        }
    }

    // Get text of a WebElement
    public String getText(WebElement element) {
    	try {
            String text = element.getText();
            return text != null ? text.trim() : "";
        } catch (Exception e) {
            throw new RuntimeException("Failed to get text from element: " + element, e);
        }
    }
    
    public void highlightElement(WebElement element) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].style.border='3px solid red'", element);
        } catch (Exception e) {
            logger.warn("Failed to highlight element.", e);
        }
    }
    
    public void scrollToElement(WebElement element) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(true);", element);
        } catch (Exception e) {
            throw new RuntimeException("Failed to scroll to element: " + element, e);
        }
    }
    
    public void waitUntilTableRowCountIsGreaterThan(List<WebElement> rows, int count, Duration timeout) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        wait.until(driver -> rows.size() > count);
    }

    public void captureScreenshot(String fileName) {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            Files.copy(screenshot.toPath(), Paths.get("screenshots", fileName + ".png"));
        } catch (IOException e) {
            logger.error("Failed to save screenshot: {}", fileName, e);
        }
    }

    public void waitForPageLoadComplete(Duration timeout) {
        new WebDriverWait(driver, timeout).until(
            webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete")
        );
    }

    public void retryClickElement(WebElement element, int maxRetries) {
        int attempts = 0;
        while (attempts < maxRetries) {
            try {
                element.click();
                return;
            } catch (Exception e) {
                attempts++;
            }
        }
        throw new RuntimeException("Failed to click element after " + maxRetries + " attempts.");
    }



    
}

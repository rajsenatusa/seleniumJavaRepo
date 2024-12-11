package pageObjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import testBase.testBaseClass;

// This is the parent class for all page object classes.
public class basePage {
	
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
    public void selectDropdownByVisibleText(WebElement dropdownElement, String visibleText) {
        try {
            Select dropdown = new Select(dropdownElement);
            dropdown.selectByVisibleText(visibleText);
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
            element.click();
        } catch (Exception e) {
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
            return element.getText();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get text from element: " + element, e);
        }
    }
}

package pageObjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


// This is the parent of all  parent object classes.

public class basePage {

	
	WebDriver driver;
	
	public basePage (WebDriver driver) {
		
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
    /**
     * Wait for an element to be visible.
     *
     * @param locator the locator of the element to wait for
     * @param timeoutInSeconds the maximum time to wait for the element to be visible
     * @return WebElement the element found after waiting
     */
    public WebElement waitForElementToBeVisible(By locator, Duration timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Wait for an element to be clickable.
     *
     * @param locator the locator of the element to wait for
     * @param timeoutInSeconds the maximum time to wait for the element to be clickable
     * @return WebElement the element found after waiting
     */
    public WebElement waitForElementToBeClickable(By locator, Duration timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Wait for an element to be present in the DOM, but not necessarily visible.
     *
     * @param locator the locator of the element to wait for
     * @param timeoutInSeconds the maximum time to wait for the element to be present
     * @return WebElement the element found after waiting
     */
    public WebElement waitForElementToBePresent(By locator, Duration timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /**
     * Wait for a specific condition to be true.
     *
     * @param condition the condition to wait for
     * @param timeoutInSeconds the maximum time to wait for the condition to be true
     * @return the result of the condition (can be any type)
     */
    public <T> T waitForCondition(ExpectedCondition<T> condition, Duration timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        return wait.until(condition);
    }
    
}

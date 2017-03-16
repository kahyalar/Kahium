package com.kahyalar.selenium;

import org.apache.commons.logging.Log;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Set;

import static com.kahyalar.selenium.KahiumConstants.WAITTIME_ELEMENTOCCURENCE;
import static com.kahyalar.selenium.KahiumConstants.WAITTIME_SMALL;
import static com.kahyalar.selenium.KahiumConstants.WAITTIME_TIMEOUT;

/**
 * Created by kahyalar on 16/03/2017.
 */
public class KahiumWebManager extends Kahium {
    public KahiumWebManager(WebDriver driver) {
        super(driver);
    }

    // border: 1px dashed red; border
    public void highlightElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "color: red;");
    }

    public void clickTo(WebElement element) {
        waitObject(element);
        element.click();
    }

    public void clickTo(By by) {
        clickTo(driver.findElement(by));
    }

    public String getText(WebElement element) {
        waitObject(element);
        return element.getText();
    }

    public String getText(By by) {
        return getText(driver.findElement(by));
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public int getSizeOfWindows() {
        return driver.getWindowHandles().size();
    }

    public void switchToFrame(String frameId) {
        driver.switchTo().frame(frameId);
    }

    public void switchWindow() {
        Set<String> handlers = driver.getWindowHandles();
        String mainWindow = "";

        if (handlers.size() > 1) {
            mainWindow = driver.getWindowHandle();
        }

        for (String handler : handlers) {
            if (!handler.equals(mainWindow)) {
                driver.switchTo().window(handler);
            }
        }
    }

    public void waitObject(WebElement element, int timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitObject(By by, int timeOutInSeconds) {
        waitObject(driver.findElement(by), timeOutInSeconds);
    }

    public void waitObject(WebElement element) {
        waitObject(element, WAITTIME_ELEMENTOCCURENCE);
    }

    public void waitObject(By by) {
        waitObject(driver.findElement(by));
    }

    public boolean waitObjectSafely (WebElement element, int timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (TimeoutException ex) {
            System.out.println("WebElement is not found");
            return false;
        }
    }

    public boolean waitObjectSafely (By by, int timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            return true;
        } catch (TimeoutException ex) {
            System.out.println("WebElement is not found");
            return false;
        }
    }

    public void typeTo(WebElement element, String keyword) {
        waitObject(element);
        element.sendKeys(keyword);
    }

    public void typeTo(By by, String keyword) {
        typeTo(driver.findElement(by), keyword);
    }

    public boolean isElementPresent(WebElement element) {
        return waitObjectSafely(element, WAITTIME_SMALL);
    }

    public boolean isElementPresent(By by) {
        return !driver.findElements(by).isEmpty();
    }

    public void moveTo(WebElement element) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
    }

    public void moveTo(By by) {
        moveTo(driver.findElement(by));
    }

    public WebElement findElement(By by, int... index) {

        if (index.length == 0) {
            WebElement element = driver.findElement(by);
            highlightElement(element);
            return element;
        } else if (index[0] >= 0) {

            List<WebElement> elements = driver.findElements(by);
            if (!elements.isEmpty() && index[0] <= elements.size()) {
                highlightElement(elements.get(index[0]));
                return elements.get(index[0]);
            }
        }

        return null;
    }

    public void hoverElement(WebElement element) {
        Actions action = new Actions(driver);
        action.moveToElement(element).build().perform();
    }

}

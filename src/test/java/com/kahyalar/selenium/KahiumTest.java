package com.kahyalar.selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;

/**
 * Created by kahyalar on 16/03/2017.
 */
public class KahiumTest {
    @Test
    public void browserCheck() throws IOException {
        WebDriver driver = new ChromeDriver();
        driver.get("http://google.com");
        KahiumWeb.maximizeBrowserForMBP15(driver);
        WebElement element = driver.findElement(By.id("hplogo"));
        Kahium.createScreenshotManager(driver).getScreenshot(null, null);
        Kahium.createScreenshotManager(driver).getScreenshotOfElement("elementSS", null, element, true);
    }
}

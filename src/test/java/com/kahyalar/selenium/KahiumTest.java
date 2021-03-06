package com.kahyalar.selenium;

import org.junit.After;
import org.junit.Before;
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
    WebDriver driver;

    @Before
    public void startUp(){
        driver = new ChromeDriver();
        driver.get("http://google.com");
    }

    @Test
    public void browserCheck(){
        KahiumBrowserManager.maximizeBrowserForMBP15(driver);
    }

    @Test
    public void screenshotTest() throws IOException {
        WebElement element = driver.findElement(By.id("hplogo"));
        Kahium.createScreenshotManager(driver).getScreenshot(null, null);
        Kahium.createScreenshotManager(driver).getScreenshotOfElement("elementSS", null, element, true);
    }

    @After
    public void tearDown(){
        driver.quit();
    }
}

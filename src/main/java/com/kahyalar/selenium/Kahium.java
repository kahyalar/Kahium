package com.kahyalar.selenium;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Created by kahyalar on 16/03/2017.
 */
public class Kahium {
    static protected WebDriver driver;
    static protected WebElement element;

    public Kahium(WebDriver driver) {
        this.driver = driver;
    }

    public Kahium(){

    }
    public static KahiumWebManager createWebManager(WebDriver driver){
        return new KahiumWebManager(driver);
    }
    public static KahiumScreenshotManager createScreenshotManager(WebDriver driver){
        return new KahiumScreenshotManager(driver);
    }
    public static KahiumMobileManager createMobileManager(AppiumDriver<MobileElement> driver, DesiredCapabilities capabilities){
        return new KahiumMobileManager(driver, capabilities);
    }
}

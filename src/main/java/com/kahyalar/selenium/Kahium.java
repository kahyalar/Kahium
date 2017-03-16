package com.kahyalar.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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
}

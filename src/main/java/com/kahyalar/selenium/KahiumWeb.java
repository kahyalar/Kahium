package com.kahyalar.selenium;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * Created by kahyalar on 16/03/2017.
 */
public class KahiumWeb extends Kahium {
    public static void maximizeBrowserForMBP13(WebDriver driver){
        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(1280, 800));
    }

    public static void maximizeBrowserForMBP15(WebDriver driver){
        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(1440, 900));

    }

    public static void maximizeBrowserForiMac4K(WebDriver driver){
        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(2048, 1152));
    }

    public static void maximizeBrowserForiMac5K(WebDriver driver){
        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(2560, 1440));
    }

    public static void maximizeBrowseriMac(WebDriver driver){
        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(1280, 720));
    }

    public static void maximizeBrowserForWindows(WebDriver driver){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
    }
}

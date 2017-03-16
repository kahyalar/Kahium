package com.kahyalar.selenium;
import org.openqa.selenium.*;
import org.openqa.selenium.Point;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static javax.imageio.ImageIO.read;
import static javax.imageio.ImageIO.write;
import static org.apache.commons.io.FileUtils.*;

/**
 * Created by kahyalar on 16/03/2017.
 */
public class KahiumScreenshotManager extends Kahium {
    private File screenshot;
    private File screenshotLocation;
    private BufferedImage fullSizeImage;
    private String fileName;
    private String filePath;
    private static final String DEAFULT_FILE_NAME = "screenshot";
    private static final String FORMAT_PATTERN = "%s%s.png";
    private static final String DEFAULT_FILE_PATH = System.getProperty("user.home")+"/Desktop/";

    public KahiumScreenshotManager(WebDriver driver){
        this.driver = driver;
    }

    private void checkNullFields(){
        if(fileName == null && filePath == null){
            screenshotLocation = new File(java.lang.String.format(FORMAT_PATTERN, DEFAULT_FILE_PATH, DEAFULT_FILE_NAME));
        }
        else if(fileName == null) {
            screenshotLocation = new File(java.lang.String.format(FORMAT_PATTERN, filePath, DEAFULT_FILE_NAME));
        }
        else if(filePath == null){
            screenshotLocation = new File(String.format(FORMAT_PATTERN, DEFAULT_FILE_PATH, fileName));
        }
        else {
            screenshotLocation = new File(java.lang.String.format(FORMAT_PATTERN, filePath, fileName));
        }
    }

    public void getScreenshot(String fileName, String filePath) throws IOException{
        this.fileName = fileName;
        this.filePath = filePath;
        checkNullFields();
        screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        fullSizeImage = read(screenshot);
        write(fullSizeImage, "png", screenshotLocation);
    }

    private void processElement(String fileName, String filePath, WebElement element, boolean MacOS) throws IOException{
        getScreenshot(fileName, filePath);
        Point point;
        int eleWidth, eleHeight;
        this.element = element;
        if(!MacOS){
            point = new Point(element.getLocation().getX(), element.getLocation().getY());
            eleWidth = element.getSize().getWidth();
            eleHeight = element.getSize().getHeight();
        }
        else {
            point = new Point((element.getLocation().getX()) * 2, (element.getLocation().getY()) * 2);
            eleWidth = (element.getSize().getWidth()) * 2;
            eleHeight = (element.getSize().getHeight()) * 2;
        }
        BufferedImage elementScreenshot= fullSizeImage.getSubimage(point.getX(), point.getY(), eleWidth, eleHeight);
        write(elementScreenshot, "png", screenshot);
    }

    public void getScreenshotOfElement(String fileName, String filePath, WebElement element, boolean isItMacOS) throws IOException {
        processElement(fileName, filePath, element, isItMacOS);
        copyFile(screenshot, screenshotLocation);
    }
}

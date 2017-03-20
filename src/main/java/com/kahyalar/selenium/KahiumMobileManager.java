package com.kahyalar.selenium;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.SwipeElementDirection;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static com.thoughtworks.selenium.SeleneseTestBase.assertTrue;

/**
 * Created by kahyalar on 16/03/2017.
 * Taken from SahaBT Test Project.
 * More will came up !
 */
public class KahiumMobileManager extends  Kahium {
    private static AppiumDriver<MobileElement> driver;
    private static DesiredCapabilities capabilities;
    public static final int DEFAULT_WAIT = 30;
    public static final int MIN_WAIT = 5;
    private static String deviceName, appPackage, appActivity, platformVersion, bundleId, UDID;

    public KahiumMobileManager(AppiumDriver<MobileElement> driver, DesiredCapabilities capabilities) {
        KahiumMobileManager.driver = driver;
        KahiumMobileManager.capabilities = capabilities;
    }

    //Configurate Android devices capabilities
    public static void androidConfig(String deviceName, String appPackage, String appActivity) throws MalformedURLException {
        KahiumMobileManager.deviceName = deviceName;
        KahiumMobileManager.appPackage = appPackage;
        KahiumMobileManager.appActivity = appActivity;
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
        capabilities.setCapability("appPackage", appPackage);
        capabilities.setCapability("appActivity",appActivity);
        driver = new AndroidDriver<MobileElement>(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
    }

    //Configurate iOS devices capabilities
    public static void iOSConfig(String deviceName, String platformVersion, String bundleId, String UDID) throws MalformedURLException {
        KahiumMobileManager.deviceName = deviceName;
        KahiumMobileManager.bundleId = bundleId;
        KahiumMobileManager.platformVersion = platformVersion;
        KahiumMobileManager.UDID = UDID;
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
        capabilities.setCapability("bundleId", bundleId);
        capabilities.setCapability("udid", UDID);
        driver = new IOSDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    /**
     * Click komutunu lokasyona göre işletir, click metodunun çalışmadığı
     * durumlarda kullanılabilir.
     * @param seconds : Element'in aranma süresini belirler.
     * @param elementBy : Ulaşılmak istenen elementin attribute değeri.
     */
    public void getLocationClick(int seconds, By elementBy) {
        WebDriverWait wait = new WebDriverWait(driver, seconds, 1000);
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(elementBy));
        driver.tap(1, element, 1);
    }

    /**
     * Ulaşılmak istenilen WebElement'i döndürür.
     * @param seconds: Element'in aranma süresini belirler.
     * @param elementBy: Ulaşılmak istenen elementin attribute değeri.
     * @return
     */
    public WebElement waitForElement(int seconds, By elementBy) {
        WebDriverWait wait = new WebDriverWait(driver, seconds, 1000);
        wait.until(ExpectedConditions.presenceOfElementLocated(elementBy));
        return driver.findElement(elementBy);
    }

    /**
     * Ulaşılmak istenilen MobileElement'i döndürür.
     * @param seconds: Element'in aranma süresini belirler.
     * @param elementBy: Ulaşılmak istenen elementin attribute değeri.
     * @return
     */
    public MobileElement waitForMobileElement(int seconds, By elementBy) {
        WebDriverWait wait = new WebDriverWait(driver, seconds, 1000);
        return (MobileElement) wait.until(ExpectedConditions.presenceOfElementLocated(elementBy));
    }

    /**
     * Ulaşılmak istenilen WebElement'leri liste şeklinde döndürür.
     * @param seconds: Element'in aranma süresini belirler.
     * @param elementBy: Ulaşılmak istenen elementin attribute değeri.
     * @return
     */
    public List<WebElement> waitForElements(int seconds, By elementBy) {
        WebDriverWait wait = new WebDriverWait(driver, seconds, 1000);
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(elementBy));
    }

    /**
     * Ulaşılmak istenilen WebElement'i döndürür.
     * @param seconds : Element'in aranma süresini belirler.
     * @param resourceId : Ulaşılmak istenen elementin id değeri string cinsinden.
     * @return
     */
    public WebElement waitForElementById(int seconds, String resourceId) {
        return waitForElement(seconds, By.id(resourceId));
    }

    /**
     * Element i bekler bulamazsa assertion basarak testi bitirir.
     * @param byElement
     * @param name
     */
    public void waiterWithAssertion(By byElement, String name) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, DEFAULT_WAIT , 1000);
            wait.until(ExpectedConditions.presenceOfElementLocated(byElement));
        } catch (Exception e) {
        }

    }

    /**
     * Element'i bekler ve click işlemini gerçekleştirir.
     * @param byElement
     * @param Seconds
     */
    public void clickAndWaitForElement(By byElement, int Seconds) {
        WebDriverWait wait = new WebDriverWait(driver, Seconds);
        wait.until(ExpectedConditions.presenceOfElementLocated(byElement)).click();

    }

    /**
     * Aynı id ye sahip elementler arasında text i verilen text e eşit
     * element e tıklar herhangi bir element bulamazsa hata basar.
     * @param text
     */
    public void selectAndClickTextFromList(List<WebElement> list, String text) {
        boolean elementIsFound = false;
        for (WebElement item : list) {
            if (item.getText().equals(text)) {
                elementIsFound = true;
                item.click();
                break;
            }
        }
        assertTrue(text + " butonu bulunamadı", elementIsFound);
    }

    /**
     * Aynı id ye sahip elementler arasında text i  verilen text i içeren
     * element e tıklar herhangi bir element bulamazsa hata basar.
     * @param element
     * @param second
     * @param text
     */
    public void clickAndWaitForElementIfContainsText(By element, int second, String text) {
        List<WebElement> elements = waitForElements(second, element);
        boolean check = false;
        for(WebElement elem : elements){
            if(elem.getText().contains(text)){
                elem.click();
                check = true;
                break;
            }
        }
    }

    /**
     * Elementleri bekler ve index e göre click işlemi yapar.
     * @param element
     * @param second
     * @param index
     */
    public void clickAndWaitForElement(By element, int second, int index) {
        WebDriverWait wait = new WebDriverWait(driver, second);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(element)).get(index).click();

    }

    /**
     * Element'i bekler ve click işlemini gerçekleştirir.
     * @param byElement
     */
    public void clickAndWaitForElement(By byElement) {
        WebDriverWait wait = new WebDriverWait(driver, DEFAULT_WAIT);
        wait.until(ExpectedConditions.presenceOfElementLocated(byElement)).click();
    }

    public void clickAndWaitListElementAtIndex(By byList, By byElement, int index, int seconds) {
        WebDriverWait wait = new WebDriverWait(driver, seconds);
        wait.until(ExpectedConditions.presenceOfElementLocated(byList)).findElements(byElement).get(index).click();

    }

    /**
     * Textfield alanlarına text göndermeyi sağlar. İşlem sonrası
     * klavyeyi gizler.
     * @param byElement
     * @param Seconds
     * @param text
     */
    public void sendKeysAndWaitForElement(By byElement, int Seconds, String text) {
        WebElement element = waitForElement(Seconds, byElement);
        element.click();
        if(driver instanceof IOSDriver){
            element.clear();
            element.sendKeys(text);
            tryHideKeyboardIOS();
        }else{
            element.sendKeys(text);
            tryHideKeyboard();
        }
    }

    /**
     * Sendkeys metodunun çalışmadığı noktalarda denenebilir.
     * @param text
     */
    public void sendKeysByScript(String text) {
        driver.executeScript("target.frontMostApp().keyboard().typeString('" + text + "')");
    }

    /**
     * Textfield alanlarına text göndermeyi sağlar. İşlem sonrası
     * klavyeyi gizler.
     * @param byElement
     * @param text
     */
    public void sendKeysAndWaitForElement(By byElement, String text) {
        WebElement element = waitForElement(DEFAULT_WAIT, byElement);
        element.click();
        if(driver instanceof IOSDriver){
            element.clear();
            element.sendKeys(text);
//			tryHideKeyboardIOS();
        }else{
            element.sendKeys(text);
            tryHideKeyboard();
        }
    }

    /**
     * Textfield alanlarına text göndermeyi sağlar, list halindeli elementleri index
     * ile bulur. İşlem sonrası klavyeyi gizler.
     * @param byElement
     * @param Seconds
     * @param text
     * @param index : liste halindeki elementlerin index değeri.
     */
    public void sendKeysAndWaitForElements(By byElement, int Seconds, String text, int index) {
        WebElement element = waitForElements(DEFAULT_WAIT, byElement).get(index);
        element.click();
        if(driver instanceof IOSDriver)
            element.clear();
        element.sendKeys(text);
    }

    /**
     * Textfield alanlarına text göndermeyi sağlar. Klavyenin
     * gizlenmemesi istenildiğinde kullanılır.
     * @param byElement
     * @param Seconds
     * @param text
     */
    public void sendKeysAndWaitForElementNotHideKeyboard(By byElement, int Seconds, String text) {
        WebElement element = waitForElement(DEFAULT_WAIT, byElement);
        element.click();
        if(driver instanceof IOSDriver)
            element.clear();
        element.sendKeys(text);
    }

    /**
     * Textfield alanlarındaki değeri siler.
     * @param byElement
     * @param Seconds
     */
    public void clearAndWaitForElement(By byElement, int Seconds) {
        WebDriverWait wait = new WebDriverWait(driver, Seconds);
        wait.until(ExpectedConditions.presenceOfElementLocated(byElement)).clear();
    }

    /**
     * Belirtilen saniye boyunca bir sonraki işleme kadar bekler.
     * @param seconds
     */
    public void waitSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Aldığı parametre sayısınca sola swipe eder.
     * @param repeat
     */
    public void swipeLeftMultiple(int repeat) {
        for (int i = 0; i < repeat; i++) {
            swipeLeftAccordingToPhoneSize();
            waitSeconds(1);
        }
    }

    /**
     * Sola swipe eder. Telefon boyutundan bağımsız çalışır.
     */
    public void swipeLeftAccordingToPhoneSize() {

        Dimension d = driver.manage().window().getSize();
        int height = d.height;
        int width = d.width;

        int swipeStartWidth = (width * 60) / 100;
        int swipeEndWidth = (width * 5) / 100;
        int swipeStartHeight = height / 3, swipeEndHeight = height / 3;

//		System.out.println("height= " + height + " width= " + width);
//		System.out.println("start= " + swipeStartWidth + " end= " + swipeEndWidth);

        driver.swipe(swipeStartWidth, swipeStartHeight, swipeEndWidth, swipeEndHeight, 1000);

    }

    /**
     * Sağa swipe eder. Telefon boyutundan bağımsız çalışır.
     */
    public void swipeRightAccordingToPhoneSize() {

        Dimension d = driver.manage().window().getSize();
        int height = d.height;
        int width = d.width;

        int swipeStartWidth = (width * 50) / 100;
        int swipeEndWidth = (width * 90) / 100;
        int swipeStartHeight = height / 3, swipeEndHeight = height / 3;

//		System.out.println("height= " + height + " width= " + width);
//		System.out.println("start= " + swipeStartWidth + " end= " + swipeEndWidth);

        driver.swipe(swipeStartWidth, swipeStartHeight, swipeEndWidth, swipeEndHeight, 1000);

    }

    /**
     * Aşağıya swipe eder. Telefon boyutundan bağımsız çalışır.
     */
    public void swipeDownAccordingToPhoneSize() {

        Dimension d = driver.manage().window().getSize();
        int height = d.height;
        int width = d.width;

        int swipeStartWidth = width / 2, swipeEndWidth = width / 2;

        int swipeStartHeight = (height * 80) / 100;
        int swipeEndHeight = (height * 30) / 100;

        driver.swipe(swipeStartWidth, swipeStartHeight, swipeEndWidth, swipeEndHeight, 1000);

    }

    /**
     * Yukarıya swipe eder. Telefon boyutundan bağımsız çalışır.
     */
    public void swipeUpAccordingToPhoneSize() {

        Dimension d = driver.manage().window().getSize();
        int height = d.height;
        int width = d.width;

        int swipeStartWidth = width / 2, swipeEndWidth = width / 2;

        int swipeStartHeight = (height * 30) / 100;
        int swipeEndHeight = (height * 80) / 100;

        driver.swipe(swipeStartWidth, swipeStartHeight, swipeEndWidth, swipeEndHeight, 1000);

    }

    /**
     * Combobox elementinden indexine göre seçim yapmayı sağlar.
     * @param seconds
     * @param index
     * @param byElement
     */
    public void selectElementFromCombobox(int seconds, int index, By byElement) {
        WebDriverWait wait = new WebDriverWait(driver, seconds);
        Select oSelection = new Select(wait.until(ExpectedConditions.presenceOfElementLocated(byElement)));
        oSelection.selectByIndex(index);
    }

    /**
     * Element bazlı swipe işlemi yapar.
     * @param byElement
     */
    public void swipeLeftById(By byElement){
        MobileElement element =(MobileElement)driver.findElement(byElement);
        element.swipe(SwipeElementDirection.LEFT, 400);
    }

    /**
     * Default IOS HideKeyboard metodu her durumda doğru çalışmadığı
     * için geliştirilmiş metodtur. Default metodta ekranın ortasına
     * tıklayıp klavyeyi kapatmakta fakat tıkladığı alanda buton
     * veya textfield gibi bir element var ise onlara aksiyon sağladığı
     * için işlem başarısız olmakta bu sebeple element bulunmayacağı
     * tahmin edilen noktaya göre klavye gizleme işlemi sağlanmıştır.
     */
    public void tryHideKeyboardIOS(){
        try {
            Dimension d = driver.manage().window().getSize();
            int height = d.height;
            int width = d.width;

            driver.tap(2, width-1, height/2,  1);
        } catch (Exception e) {
        }

    }

    /**
     * Appium default klavye gizleme işlemidir. Android
     * platformunda stabil çalışırken IOS platformunda
     * stabil çalışmamaktadır.
     */
    public void tryHideKeyboard(){
        try {
            driver.hideKeyboard();
        } catch (Exception e) {
        }

    }

    /**
     * Testin çalıştığı telefonun boyutlarını yazdıran metod.
     */
    public void getPhoneSize(){
        Dimension d = driver.manage().window().getSize();
        int height = d.height;
        int width = d.width;
        System.out.println("height : "+ height+"\nwidth : "+ width);
    }

    /**
     * Element'in varlığını kontrol eder, parametre ile belirtilen
     * süre boyunca elementi bulursa true bulamaz ise false döner
     * bu sayede assertion kontrolü sağlanır.
     * @param seconds
     * @param by
     * @return
     */
    public boolean isExist(int seconds,By by){
        try {
            waitForElement(seconds, by);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Element'in varlığını kontrol eder, parametre ile belirtilen süre boyunca
     * elementi ve child elementi bulursa true bulamaz ise false döner bu sayede assertion
     * kontrolü sağlanır.
     *
     * @param seconds
     * @param by
     * @return
     */
    public boolean isExist(int seconds, By byParent, By byChild) {
        try {
            waitForElement(seconds, byParent).findElement(byChild);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Element'in attribute değerini konsol a basar.
     * @param element
     */
    public void foundedElement(WebElement element){
        System.out.println("Founded element ->"+element.toString().split("->")[1]);
    }

    /**
     * Element in text ini döndürür.
     * @param seconds
     * @param elementBy
     * @return
     */
    public String getText(int seconds, By elementBy) {
        return waitForElement(seconds, elementBy).getText();
    }

    /**
     * Aynı id ye sahip elementler arasından index e göre text ini döndürür.
     * @param seconds
     * @param elementBy
     * @param index
     * @return
     */
    public String getText(int seconds, By elementBy, int index) {
        return waitForElements(seconds, elementBy).get(index).getText();
    }

    /**
     * Element in content-desc i döndürür, Android için.
     * @param seconds
     * @param elementBy
     * @return
     */
    public String getContentDesc(int seconds, By elementBy) {
        return waitForElement(seconds, elementBy).getAttribute("name");
    }

    /**
     * Ulaşılmak istenilen Elemente longPress işlemi uygulanır.
     *
     * @param seconds:
     *            Element'in aranma süresini belirler.
     *            Ulaşılmak istenen elementin attribute değeri.
     */
    public void touchLongPress(int seconds, By by) {
        MobileElement el = (MobileElement)waitForElement(seconds, by);
        TouchAction action = new TouchAction(driver);
        action.longPress(el).perform().release();
    }

    /**
     * Ulaşılmak istenilen Elemente index e göre longPress işlemi uygulanır.
     *
     * @param seconds:
     *            Element'in aranma süresini belirler.
     * @param elementBy:
     *            Ulaşılmak istenen elementin attribute değeri.
     */
    public void touchLongPress(int seconds, By by, int index) {
        MobileElement el = (MobileElement)waitForElements(seconds, by).get(index);
        TouchAction action = new TouchAction(driver);
        action.longPress(el).perform().release();
    }

    /**
     * Element'in text değerini verilen değer ile karşılaştırır
     * @param seconds
     * @param by
     * @return
     */
    public boolean getTextIsEqual(int seconds, By by, String text){
        return waitForElement(seconds, by).getText().equals(text);
    }

    /**
     * index e göre Element'in text değerini verilen değer ile karşılaştırır
     * @param seconds
     * @param by
     * @return
     */
    public boolean getTextsIsEqual(int seconds, By by, String text, int index){
        return waitForElements(seconds, by).get(index).getText().equals(text);
    }

    /**
     * Element'in text değerini verilen değer ile içeriyor mu diye karşılaştırır
     * @param seconds
     * @param by
     * @return
     */
    public boolean getTextIsContains(int seconds, By by, String text){
        String getText = waitForElement(seconds, by).getText();
        System.out.println(getText);
        return getText.contains(text);
    }

    /**
     * Elementler'in index e göre text değerini verilen değer ile içeriyor mu diye karşılaştırır
     * @param seconds
     * @param by
     * @return
     */
    public boolean getTextsIsContains(int seconds, By by, String text, int index){
        String getText = waitForElements(seconds, by).get(index).getText();
        System.out.println(getText);
        return getText.contains(text);
    }

    /**
     * Element'in value değerini verilen değer ile karşılaştırır
     * @param seconds
     * @param by
     * @return
     */
    public boolean getValueIsContains(int seconds, By by, String text){
        String getText = waitForElement(seconds, by).getAttribute("value");
        System.out.println(getText);
        return getText.contains(text);
    }

    /**
     * Elementler'in herhangi biri verilen text değerini içeriyor mu
     * kontrol eder.
     * @param seconds
     * @param by
     * @return
     */
    public boolean getAnyTextIsContains(int seconds, By by, String text){
        List<WebElement> elements = waitForElements(seconds, by);
        boolean check = false;
        for(WebElement element : elements){
            if(element.getText().contains(text)){
                check = true;
                System.out.println(element.getText());
                break;
            }
        }
        return check;
    }

    /**
     * Element'in text değeri boş mu kontrol eder.
     * @param seconds
     * @param by
     * @return
     */
    public boolean getTextIsEmpty(int seconds, By by){
        return waitForElement(seconds, by).getText().length() == 0;
    }

    /**
     * Element'in value ı boş mu diye kontrol eder.
     * @param seconds
     * @param by
     * @return
     */
    public boolean getValueIsEmpty(int seconds, By by){
        String text = waitForElement(seconds, by).getAttribute("value");
        System.out.println(text);
        return (text.length() == 0);
    }

    /**
     * Element'in label ı boş mu diye kontrol eder.
     * @param seconds
     * @param by
     * @return
     */
    public boolean getLabelIsEmpty(int seconds, By by){
        String text = waitForElement(seconds, by).getAttribute("label");
        System.out.println(text);
        return (text.length() == 0);
    }

    /**
     * Element'in string uzunlugu verilen değere eşit mi diye kontrol eder.
     * @param seconds
     * @param by
     * @return
     */
    public boolean checkTextLength(int seconds, By by, int length){
        String text = waitForElement(seconds, by).getText();
        System.out.println(text);
        return (text.length() == length);
    }

    /**
     * Element'in enabled attribute unu dondurur
     * @param seconds
     * @param by
     * @return
     */
    public boolean getEnabled(int seconds, By by, int index){
        return waitForElements(seconds, by).get(index).isEnabled();
    }

    /**
     * Element kaybolana kadar bekler.
     * @param seconds
     * @param elementBy
     */
    public void waitForElementDisappear(int seconds, By elementBy) {
        int count = 0;
        while (isExist(1, elementBy)) {
            if (count == seconds) {
                break;
            }
        }
    }

    /**
     * Elementi verilen süre içinde bulmaya çalışır ve kaç tane oldugunu geri döndürür.
     * @param seconds
     * @param by
     * @return
     */
    public int countElements(int seconds,By by){
        int count=0;
        if(isExist(seconds, by)){
            count = waitForElements(1,by).size();
        }
        return count;
    }
}

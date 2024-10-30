package pro.vasudevan.automation.unifiedtestframework.interfaces;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pro.vasudevan.automation.unifiedtestframework.config.IWebDriverConfig;
import pro.vasudevan.automation.unifiedtestframework.constants.Global;

import java.util.HashMap;
import java.util.Map;

/*
Created By: Vasudevan Sampath

 IScrollHelper.java has helper methods for scrolling and swiping
 */
public interface IScrollHelper {

    WebElement scrollIntoView(By parentContainer, Global.FindType findType, String elementToFind,
                              Global.SwipeScrollDirection swipeScrollDirection, Global.SwipeScrollPercentage swipeScrollPercentage);

    static void swipe(Global.SwipeScrollDirection swipeScrollDirection) {
        AppiumDriver appiumDriver = (AppiumDriver) IWebDriverConfig.getDriver();
        if (appiumDriver instanceof IOSDriver) {
            Map<String, String> swipeObj = new HashMap<>();
            swipeObj.put("direction", swipeScrollDirection.toString());
            appiumDriver.executeScript("mobile: swipe", swipeObj);
        }
    }}

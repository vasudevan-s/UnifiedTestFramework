package pro.vasudevan.helpers;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import pro.vasudevan.config.IWebDriverConfig;
import pro.vasudevan.constants.Global;

import java.util.HashMap;
import java.util.Map;

/*
Created By: Vasudevan Sampath

 IScrollHelper.java has helper methods for scrolling
 */
public interface IScrollHelper {
    static void swipe(Global.SwipeOptions swipeOptions) {
        AppiumDriver appiumDriver = (AppiumDriver) IWebDriverConfig.getDriver();
        if (appiumDriver instanceof IOSDriver) {
            Map<String, String> swipeObj = new HashMap<>();
            swipeObj.put("direction", swipeOptions.toString());
            appiumDriver.executeScript("mobile: swipe", swipeObj);
        }
    }
}

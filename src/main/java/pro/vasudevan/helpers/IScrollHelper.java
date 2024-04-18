package pro.vasudevan.helpers;

import io.appium.java_client.AppiumDriver;
import pro.vasudevan.config.IDriverConfig;
import pro.vasudevan.constants.Global;

import java.util.HashMap;
import java.util.Map;

public interface IScrollHelper {
    static void swipe(Global.SwipeOptions swipeOptions) {
        AppiumDriver appiumDriver = (AppiumDriver) IDriverConfig.getDriver();
        Map<String, String> swipeObj = new HashMap<>();
        swipeObj.put("direction", swipeOptions.toString());
        appiumDriver.executeScript("mobile: swipe", swipeObj);
    }
}

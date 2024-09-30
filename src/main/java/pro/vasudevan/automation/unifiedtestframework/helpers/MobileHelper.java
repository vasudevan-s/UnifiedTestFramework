package pro.vasudevan.automation.unifiedtestframework.helpers;

import io.appium.java_client.AppiumDriver;
import org.javatuples.Triplet;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pro.vasudevan.automation.unifiedtestframework.base.ElementBase;
import pro.vasudevan.automation.unifiedtestframework.config.IWebDriverConfig;
import pro.vasudevan.automation.unifiedtestframework.constants.Global;
import pro.vasudevan.automation.unifiedtestframework.interfaces.IScrollHelper;
import pro.vasudevan.automation.unifiedtestframework.misc.Common;

import java.util.Map;

/*
Created By: Vasudevan Sampath

 MobileHelper.java has mobile specific methods
 */
public final class MobileHelper extends ElementBase implements IScrollHelper {

    private static final AppiumDriver appiumDriver;
    static {
        appiumDriver = (AppiumDriver) IWebDriverConfig.getDriver();
    }

    private boolean isEndOfPageReached(String pageSource) {
        return pageSource.equals(getPageSource());
//        return pageSource.equals(appiumDriver.getPageSource());
    }

    @Override
    public WebElement scrollIntoView(By parentContainer, Global.FindType findType, String elementToFind,
                                     Global.SwipeScrollDirection swipeScrollDirection, Global.SwipeScrollPercentage swipeScrollPercentage)
    {
        String locator = "", locatorStrategy = "";
        By by = null;
        Triplet<By, String, String> triplet;

        switch (findType) {
            case PropertyKey -> {
                triplet = getByObject(elementToFind);
                by = (By) triplet.getValue(0);
                if (Common.waitForAnyExpectedCondition(ExpectedConditions.visibilityOfAllElementsLocatedBy(by))!= null) return findElement(by);
                locator = (String) triplet.getValue(1);
                locatorStrategy = (String) triplet.getValue(2);
            }
            case SimpleTextDelimiterSeparated -> {
                locator = elementToFind;
                locatorStrategy = "xpath";
                by = By.xpath(elementToFind);
            }
        }
        RemoteWebElement parentId = (RemoteWebElement) findElement(parentContainer);

        String pageSource = "";
        while(!isEndOfPageReached(pageSource)) {
            if (didElementExist(by)) break;
            pageSource = getPageSource(); //appiumDriver.getPageSource();
            appiumDriver.executeScript("gesture: scrollElementIntoView",
                    Map.of("scrollableView", parentId.getId(),
                    "strategy", "\"" + locatorStrategy + "\"",
                    "selector", locator,
                    "percentage", swipeScrollPercentage.getValue(),
                    "direction", swipeScrollDirection.toString(),
                    "maxCount", 1));
        }
        return findElement(by);
    }
}
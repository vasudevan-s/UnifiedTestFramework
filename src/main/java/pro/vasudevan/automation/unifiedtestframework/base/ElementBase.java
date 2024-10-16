package pro.vasudevan.automation.unifiedtestframework.base;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.apache.commons.io.FileUtils;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pro.vasudevan.automation.unifiedtestframework.config.IWebDriverConfig;
import static pro.vasudevan.automation.unifiedtestframework.config.IWebDriverConfig.getDriver;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;
import java.util.stream.Stream;

/*
Created By: Vasudevan Sampath

 ElementBase.java has web element specific methods.
 Also, initializes object repository file (expects app.repo.properties under src/test/resources in your test suite).
 Property Key value is separated by @@ to identify the locator type
 */
public abstract class ElementBase implements IWebDriverConfig {

    static Map<String, String> map = new HashMap<>();

    protected static void initObjectRepo() throws Exception {
        final Properties properties = new Properties();

        if (map.isEmpty()) {
            properties.load(new FileInputStream(new File(FileUtils.getFile("src", "test", "resources", "objectrepo", "app.repo.properties").getAbsolutePath())));
            map = (Map) properties;    // map Properties (key/value pair) to a Hashmap
        }
    }

    protected static WebElement findElement(String propertyKey) {
        Triplet<By, String, String> triplet = getByObject(propertyKey);
        return getDriver().findElement((By) triplet.getValue(0));
    }

    protected static List<WebElement> findElements(String propertyKey) {
        Triplet<By, String, String> triplet = getByObject(propertyKey);
        return getDriver().findElements((By) triplet.getValue(0));
    }

    protected static boolean didElementExist(By by) {
        boolean didExist;
        try {
            findElement(by);
            didExist = true;
        } catch (Exception ex) {
            didExist = false;
        }
        return didExist;
    }

    protected static WebElement findElement(By by) {
        return getDriver().findElement(by);
    }

    protected static List<WebElement> findElements(By by) {
        return getDriver().findElements(by);
    }

    private static String mapLocatorType(String locatorType) {
        String newLocatorType;
        switch (locatorType) {
            case "accessibilityid" -> newLocatorType = "accessibility id";
            case "iosclasschain" -> newLocatorType = "-ios class chain";
            case "iosnspredicate" -> newLocatorType = "-ios predicate string";
            default -> newLocatorType = locatorType;
        }
        return newLocatorType;
    }

    protected static Triplet<By, String, String> getByObject(String propertyKey) {
        String[] splitString = getValue(propertyKey).split("@@");
        String locator = splitString[0];
        String locatorType = mapLocatorType(splitString[1]);

        By by = switch(splitString[1].toLowerCase()) {
            case "id" -> By.id(locator);
            case "name" -> By.name(locator);
            case "linktext" -> By.linkText(locator);
            case "partiallinktext" -> By.partialLinkText(locator);
            case "classname" -> By.className(locator);
            case "tagname" -> By.tagName(locator);
            case "css" -> By.cssSelector(locator);
            case "xpath" -> By.xpath(locator);
            case "accessibilityid", "accessibility id" -> AppiumBy.accessibilityId(locator);
            case "iosclasschain", "-ios class chain" -> AppiumBy.iOSClassChain(locator);
            case "iosnspredicate", "-ios predicate string" -> by = AppiumBy.iOSNsPredicateString(locator);
            case "androiduiautomator" -> AppiumBy.androidUIAutomator(locator);
            case "androidviewmatcher" -> AppiumBy.androidViewMatcher(locator);
            case "androiddatamatcher" -> AppiumBy.androidDataMatcher(locator);
            case "appiumcustom" -> AppiumBy.custom(locator);
            default -> throw new IllegalArgumentException("Invalid locator type defined in object repo");
        };
        return Triplet.with(by, locator, locatorType);
    }

    protected static String getValue(String propertyKey) {
        return map.get(propertyKey);
    }

    protected static String getPageSource() {
        AppiumDriver appiumDriver = (AppiumDriver) getDriver();
        Map<String, String> map = new HashMap<>();
        map.put("format", "xml");
        map.put("excludedAttributes","visible");
        return (String) appiumDriver.executeScript("mobile:source", map);
    }

    protected static String searchAndReplace(String propertyKey, Map<String, String> replaceData) {
        String propertyValue = getValue(propertyKey);
        for (int i = 0; i < replaceData.entrySet().size(); i++) {
            propertyValue = propertyValue.replace("{" + i + "}", replaceData.get("{" + i + "}"));
        }
        return propertyValue;
    }
}

package pro.vasudevan.base;

import io.appium.java_client.AppiumBy;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pro.vasudevan.config.IWebDriverConfig;
import static pro.vasudevan.config.IWebDriverConfig.getDriver;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

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
            properties.load(new FileInputStream(new File(FileUtils.getFile("src", "test", "resources", "app.repo.properties").getAbsolutePath())));
            map = (Map) properties;    // map Properties (key/value pair) to a Hashmap
        }
    }

    protected static WebElement findElement(String propertyKey) {
        return getDriver().findElement(getByObject(propertyKey));
    }

    protected static List<WebElement> findElements(String propertyKey) {
        return getDriver().findElements(getByObject(propertyKey));
    }

    protected static WebElement findElement(By by) {
        return getDriver().findElement(by);
    }

    protected static List<WebElement> findElements(By by) {
        return getDriver().findElements(by);
    }

    protected static By getByObject(String propertyKey) {
        By by = null;
        String[] splitString = getValue(propertyKey).split("@@");
        String locator = splitString[0];

        switch (splitString[1].toLowerCase()) {
            case "id" -> by = By.id(locator);
            case "name" -> by = By.name(locator);
            case "linktext" -> by = By.linkText(locator);
            case "partialLinkText" -> by = By.partialLinkText(locator);
            case "className" -> by = By.className(locator);
            case "tagName" -> by = By.tagName(locator);
            case "css" -> by = By.cssSelector(locator);
            case "xpath" -> by = By.xpath(locator);
            case "accessibilityId" -> AppiumBy.accessibilityId(locator);
            case "iOSClassChain" -> AppiumBy.iOSClassChain(locator);
            case "iOSNSPredicate" -> AppiumBy.iOSNsPredicateString(locator);
            case "AndroidUIAutomator" -> AppiumBy.androidUIAutomator(locator);
            case "AndroidViewMatcher" -> AppiumBy.androidViewMatcher(locator);
            case "AndroidDataMatcher" -> AppiumBy.androidDataMatcher(locator);
            case "AppiumCustom" -> AppiumBy.custom(locator);
            default -> throw new IllegalArgumentException("Invalid locator type defined in object repo");
        }
        return by;
    }

    protected static String getValue(String propertyKey) {
        return map.get(propertyKey);
    }

}

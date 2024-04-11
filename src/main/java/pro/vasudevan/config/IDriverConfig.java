package pro.vasudevan.config;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;

/*
Created By: Vasudevan Sampath

 IDriverConfig.java is a Java 8+ functional interface has abstract and static methods for Selenium/Appium driver specifics.
 */
@FunctionalInterface
public interface IDriverConfig {
    ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();
    void initDriver(ITestContext testContext) throws Exception;
    static WebDriver getDriver() {
        return threadLocalDriver.get();
    }

    static void tearDown() {
        if (getDriver() != null) getDriver().quit();
    }
}

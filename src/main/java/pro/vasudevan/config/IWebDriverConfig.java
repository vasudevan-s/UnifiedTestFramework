package pro.vasudevan.config;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.ITestContext;
import pro.vasudevan.constants.Global;
import pro.vasudevan.helpers.IAppiumResourceHelper;

import java.time.Duration;
import java.util.Map;

/*
Created By: Vasudevan Sampath

 IWebDriverConfig.java has static methods for Selenium/Appium driver specifics.
 Supports iOS, Android and browser based drivers
 */
public interface IWebDriverConfig {
    ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();

    static void initDriver(ITestContext testContext) {
        Map<String, String> map = testContext.getCurrentXmlTest().getLocalParameters();

        switch (map.get("automationName")) {
            case Global.iOS -> {
                XCUITestOptions xcuiTestOptions = new XCUITestOptions();
                xcuiTestOptions.setUdid(map.get("udid"));
                xcuiTestOptions.setDeviceName(map.get("deviceName"));
                xcuiTestOptions.setPlatformVersion(map.get("platformVersion"));
                xcuiTestOptions.setPlatformName(map.get("platformName"));
                xcuiTestOptions.setAutomationName(map.get("automationName"));
                xcuiTestOptions.setWdaLocalPort(IAppiumResourceHelper.getAnyAvailablePort());
                xcuiTestOptions.setWdaLaunchTimeout(Duration.ofSeconds(600));
                xcuiTestOptions.setBundleId(map.get("bundleId"));
                xcuiTestOptions.setUseNewWDA(false);
                xcuiTestOptions.setNewCommandTimeout(Duration.ofSeconds(600));
                try (AppiumDriverLocalService appiumDriverLocalService = IAppiumResourceHelper.start(GeneralServerFlag.RELAXED_SECURITY)) {
                    threadLocalDriver.set(new IOSDriver(appiumDriverLocalService.getUrl(), xcuiTestOptions));
                }
            }
            case Global.Android_UIAutomator2 -> {
                UiAutomator2Options uiAutomator2Options = new UiAutomator2Options();
                uiAutomator2Options.setUdid(map.get("udid"));
                uiAutomator2Options.setPlatformName("Android");
                uiAutomator2Options.setPlatformVersion(map.get("platformVersion"));
                uiAutomator2Options.setAutomationName(map.get("automationName"));
                uiAutomator2Options.setNewCommandTimeout(Duration.ofSeconds(600));
                uiAutomator2Options.setAppPackage(map.get("packageName"));
                uiAutomator2Options.setAppActivity(map.get("activityName"));
                uiAutomator2Options.setCapability("appium:systemPort", IAppiumResourceHelper.getAnyAvailablePort());
                try (AppiumDriverLocalService appiumDriverLocalService = IAppiumResourceHelper.start(GeneralServerFlag.RELAXED_SECURITY)) {
                    threadLocalDriver.set(new AndroidDriver(appiumDriverLocalService.getUrl(), uiAutomator2Options));
                }
            }
            default -> {
                switch (map.get("browserName").toLowerCase()) {
                    case "chrome":
                        WebDriverManager.chromedriver().setup();
                        threadLocalDriver.set(new ChromeDriver());
                        break;
                    case "msedge":
                        WebDriverManager.edgedriver().setup();
                        threadLocalDriver.set(new EdgeDriver());
                        break;
                    case "safari":
                        WebDriverManager.safaridriver().setup();
                        threadLocalDriver.set(new SafariDriver());
                        break;
                    case "firefox":
                        WebDriverManager.firefoxdriver().setup();
                        threadLocalDriver.set(new FirefoxDriver());
                        break;
                }
                threadLocalDriver.get().get(map.get("launchURL"));
                threadLocalDriver.get().manage().window().maximize();
                threadLocalDriver.get().manage().deleteAllCookies();
            }
        }
    }

    static WebDriver getDriver() {
        return threadLocalDriver.get();
    }

    static void tearDown() {if (getDriver() != null) getDriver().quit();}
}

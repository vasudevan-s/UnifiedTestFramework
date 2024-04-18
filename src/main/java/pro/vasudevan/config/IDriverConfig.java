package pro.vasudevan.config;

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
import pro.vasudevan.helpers.IAppiumServiceHelper;

import java.time.Duration;
import java.util.Map;

/*
Created By: Vasudevan Sampath

 IDriverConfig.java has static methods for Selenium/Appium driver specifics.
 Support iOS, Android and browser based drivers
 */
public interface IDriverConfig {
    ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();
    static void initDriver(ITestContext testContext) {
        Map<String, String> map = testContext.getCurrentXmlTest().getLocalParameters();
        switch (map.get("automationName").toLowerCase()) {
            case "xcuitest" -> {
                XCUITestOptions xcuiTestOptions = new XCUITestOptions();
                xcuiTestOptions.setDeviceName(map.get("deviceName"));
                xcuiTestOptions.setPlatformVersion(map.get("platformVersion"));
                xcuiTestOptions.setPlatformName(map.get("platformName"));
                xcuiTestOptions.setAutomationName("xcuitest");
                xcuiTestOptions.setWdaLocalPort(IAppiumServiceHelper.getAnyAvailablePort());
                xcuiTestOptions.setWdaLaunchTimeout(Duration.ofSeconds(600));
                xcuiTestOptions.setBundleId(map.get("bundleId"));
                xcuiTestOptions.setUdid(map.get("udid"));
                xcuiTestOptions.setUseNewWDA(false);
                xcuiTestOptions.setNewCommandTimeout(Duration.ofSeconds(600));
                try (AppiumDriverLocalService appiumDriverLocalService = IAppiumServiceHelper.start(GeneralServerFlag.RELAXED_SECURITY)) {
                    threadLocalDriver.set(new IOSDriver(appiumDriverLocalService.getUrl(), xcuiTestOptions));
                }
            }
            case "espresso" -> {
                    // coming up in the next release
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
                    case "firefox":
                        WebDriverManager.firefoxdriver().setup();
                        threadLocalDriver.set(new FirefoxDriver());
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
    static void tearDown() {
        if (getDriver() != null) getDriver().quit();
    }
}

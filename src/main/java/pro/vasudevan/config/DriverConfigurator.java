package pro.vasudevan.config;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import io.github.bonigarcia.wdm.WebDriverManager;
import pro.vasudevan.helpers.IAppiumServiceHelper;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.ITestContext;

import java.time.Duration;
import java.util.Map;

/*
Created By: Vasudevan Sampath

 DriverConfigurator.java is a unified class for Mobile (iOS Simulator and physical devices)
 & Web driver (multiple browsers) initialization.
 Also, Appium is started programmatically with Appium port and WebDriverAgentPort dynamically allocated
 */
public class DriverConfigurator implements IDriverConfig {
    @Override
    public final void initDriver(ITestContext testContext) {

        Map<String, String> map = testContext.getCurrentXmlTest().getLocalParameters();
        String browserName = map.get("browserName");
        if (browserName == null) {
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
        } else {
            switch (browserName.toLowerCase()) {
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

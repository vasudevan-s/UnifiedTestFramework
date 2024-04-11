package pro.vasudevan.config;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import org.testng.ITestContext;

import java.net.URI;
import java.time.Duration;
import java.util.Map;
/*
Created By: Vasudevan Sampath

 MobileTestConfig.java has implementation methods for Mobile driver initialization. DriverConfigurator class
 supersedes this class. If MobileTestConfig is required to be kept separate, this class can be extended from TestBase class
 instead of the default DriverConfigurator class
 */
public class MobileTestConfig implements IDriverConfig {
    @Override
    public void initDriver(ITestContext testContext) throws Exception {
        Map<String, String> map = testContext.getCurrentXmlTest().getLocalParameters();
        XCUITestOptions xcuiTestOptions = new XCUITestOptions();
        xcuiTestOptions.setDeviceName(map.get("deviceName"));
        xcuiTestOptions.setPlatformVersion(map.get("platformVersion"));
        xcuiTestOptions.setPlatformName(map.get("platformName"));
        xcuiTestOptions.setAutomationName("xcuitest");
        xcuiTestOptions.setWdaLaunchTimeout(Duration.ofSeconds(20));
        xcuiTestOptions.setUdid(map.get("udid"));
        xcuiTestOptions.setCapability("appium:xcodeOrgId", map.get("xcodeOrgId"));
        xcuiTestOptions.setCapability("appium:xcodeSigningId","iPhone Developer");
        xcuiTestOptions.setBundleId(map.get("bundleId"));
        threadLocalDriver.set(new IOSDriver(new URI("http://127.0.0.1:9000/").toURL(), xcuiTestOptions));
    }
}

package pro.vasudevan.config;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import pro.vasudevan.helpers.IAppiumServiceHelper;
import org.testng.ITestContext;

import java.time.Duration;
import java.util.Map;

/*
Created By: Vasudevan Sampath

 SimulatorMobileTestConfig.java has implementation methods for Mobile driver initialization.
 DriverConfigurator class supersedes this class. If SimulatorMobileTestConfig is required to be kept separate,
 this class can be extended from TestBase class instead of the default DriverConfigurator class.
 Tip: This class is for launching app on the iOS simulator. A specific iOS simulator should be launched and app should be
 available. To get to the running simulator from terminal, use this command:  xcrun simctl list | egrep 'Booted'
 */
public class SimulatorMobileTestConfig implements IDriverConfig {
    @Override
    public void initDriver(ITestContext testContext) {
        Map<String, String> map = testContext.getCurrentXmlTest().getLocalParameters();
        XCUITestOptions xcuiTestOptions = new XCUITestOptions();
        xcuiTestOptions.setDeviceName(map.get("deviceName"));
        xcuiTestOptions.setPlatformVersion(map.get("platformVersion"));
        xcuiTestOptions.setPlatformName(map.get("platformName"));
        xcuiTestOptions.setAutomationName("xcuitest");
        xcuiTestOptions.setWdaLocalPort(IAppiumServiceHelper.getAnyAvailablePort());
        xcuiTestOptions.setWdaLaunchTimeout(Duration.ofSeconds(600));
        xcuiTestOptions.setBundleId(map.get("bundleId"));
        xcuiTestOptions.setUdid(map.get("udid"));
        try (AppiumDriverLocalService appiumDriverLocalService = IAppiumServiceHelper.start(GeneralServerFlag.RELAXED_SECURITY)) {
            threadLocalDriver.set(new IOSDriver(appiumDriverLocalService.getUrl(), xcuiTestOptions));
        }
    }
}

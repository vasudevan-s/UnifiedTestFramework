package pro.vasudevan.config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.ITestContext;
import java.util.Map;

/*
Created By: Vasudevan Sampath

 WebTestConfig.java has implementation methods for Web driver initialization. DriverConfigurator class
 supersedes this class. If WebTestConfig is required to be kept separate, this class can be extended from
 TestBase class instead of the default DriverConfigurator class
 */
public class WebTestConfig implements IDriverConfig {
    @Override
    public void initDriver(ITestContext testContext) {
        Map<String, String> map = testContext.getCurrentXmlTest().getLocalParameters();
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

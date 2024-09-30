package pro.vasudevan.automation.unifiedtestframework.listeners;

import org.testng.*;
import pro.vasudevan.automation.unifiedtestframework.config.IWebDriverConfig;
import pro.vasudevan.automation.unifiedtestframework.misc.Common;

import java.io.IOException;

/*
Created By: Vasudevan Sampath

 TestListener.java is a Test listener class
 */
public class TestListener extends TestListenerAdapter {

    @Override
    public void onTestFailure(ITestResult result) {
        if (IWebDriverConfig.getDriver() != null) {
            try {
                String filePath = Common.takeScreenshot();
                Reporter.log("<a title= \"title\" href=\"../../../" + filePath + "\">" +
                        "<img width=\"418\" height=\"240\" alt=\"Screenshot\" title=\"title\" src=\"../../../" +
                        filePath + "\"></a><BR>");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

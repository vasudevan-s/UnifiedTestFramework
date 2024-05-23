package pro.vasudevan.misc;

import org.apache.commons.io.FileUtils;
import pro.vasudevan.config.IWebDriverConfig;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import pro.vasudevan.constants.Global;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.UUID;

/*
Created By: Vasudevan Sampath

 Common.java has utility methods
 */
public final class Common {

    private static final Properties properties = new Properties();

    public static <T> T waitForAnyExpectedCondition(ExpectedCondition<T> expectedCondition, int... waitInSeconds) throws TimeoutException
    {
        // waitInSeconds param is optional. If not given, defaults to 20 seconds. If given, uses the first element
        // in the array and the rest is ignored
        int waitTime = waitInSeconds.length == 0 ? 20 : waitInSeconds[0];

        return new FluentWait<>(IWebDriverConfig.getDriver())
                .pollingEvery(Duration.ofMillis(100))
                .ignoring(NotFoundException.class)
                .ignoring(NoSuchElementException.class)
                .ignoring(ElementNotInteractableException.class)
                .withTimeout(Duration.ofSeconds(waitTime))
                .withMessage("Element not accessible with the expected condition(s): "
                        + expectedCondition + " in the given timeout period: " + waitTime)
                .until(expectedCondition);
    }
    public static String takeScreenshot() throws IOException {
        final String targetImagePath = Global.SCREENSHOTS_FOLDER + "/" + UUID.randomUUID() + ".jpg";
        TakesScreenshot takesScreenshot = (TakesScreenshot) IWebDriverConfig.getDriver();
        File source = takesScreenshot.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(source, new File(targetImagePath));
        return targetImagePath;
    }
    public static void runTerminalCommand(String command) throws Exception {
        new ProcessBuilder(command).start();
    }
}

package pro.vasudevan.misc;

import org.apache.commons.io.FileUtils;
import pro.vasudevan.config.IDriverConfig;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import pro.vasudevan.constants.Global;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.UUID;

/*
Created By: Vasudevan Sampath

 Common.java has a generic static method for explicit wait and for taking screenshots.
 */
public class Common {
    public static <T> T waitForAnyExpectedCondition(ExpectedCondition<T> expectedCondition, int... waitInSeconds) throws TimeoutException
    {
        int waitTime = waitInSeconds.length == 0 ? 20 : waitInSeconds[0];

        return new FluentWait<>(IDriverConfig.getDriver())
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
        TakesScreenshot takesScreenshot = (TakesScreenshot) IDriverConfig.getDriver();
        File source = takesScreenshot.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(source, new File(targetImagePath));
        return targetImagePath;
    }
}

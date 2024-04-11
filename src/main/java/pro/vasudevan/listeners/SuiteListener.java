package pro.vasudevan.listeners;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import pro.vasudevan.constants.Global;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/*
Created By: Vasudevan Sampath

 SuiteListener.java is a Suite listener class
 */
public class SuiteListener implements ISuiteListener {
    @Override
    public void onStart(ISuite suite) {
        System.setProperty(Global.REPORTNG_ESCAPE_OUTPUT, "false");
        try {
            Files.createDirectories(Paths.get(Global.SCREENSHOTS_FOLDER));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onFinish(ISuite suite) {}
}

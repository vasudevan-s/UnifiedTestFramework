package pro.vasudevan.automation.unifiedtestframework.interfaces;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import io.appium.java_client.service.local.flags.ServerArgument;
import pro.vasudevan.automation.unifiedtestframework.misc.Common;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Random;

/*
Created By: Vasudevan Sampath

 IAppiumHelper.java starts Appium server with dynamic port allocation
 */
public interface IAppiumHelper {
        static AppiumDriverLocalService start(GeneralServerFlag generalServerFlag) {
            AppiumDriverLocalService appiumDriverLocalService =
                    new AppiumServiceBuilder()
                            .withArgument(generalServerFlag)
                            .withArgument(GeneralServerFlag.USE_PLUGINS, "gestures")
                            .usingAnyFreePort().build();
            appiumDriverLocalService.start();
            return appiumDriverLocalService;
        }

        static int getAnyAvailablePort() {
            try (ServerSocket serverSocket = new ServerSocket(0)) {
                serverSocket.setReuseAddress(true);
                return serverSocket.getLocalPort();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Deprecated
        static int getRandomAndroidSystemPort() {
            return new Random().nextInt(8200, 8299);
        }

        @Deprecated
        static void clearAllAndroidSystemPorts(String udid) throws Exception {
            Common.runTerminalCommand("adb -s " + udid + " forward --remove-all");
        }
}

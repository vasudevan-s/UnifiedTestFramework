package pro.vasudevan.helpers;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import pro.vasudevan.misc.Common;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Random;

/*
Created By: Vasudevan Sampath

 IAppiumServiceHelper.java starts Appium server with dynamic port allocation
 */
public interface IAppiumResourceHelper {
        static AppiumDriverLocalService start(GeneralServerFlag generalServerFlag) {
            AppiumDriverLocalService appiumDriverLocalService =
                    new AppiumServiceBuilder()
                            .withArgument(generalServerFlag)
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

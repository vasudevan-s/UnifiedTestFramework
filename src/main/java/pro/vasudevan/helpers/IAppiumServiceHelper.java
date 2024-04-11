package pro.vasudevan.helpers;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

import java.io.IOException;
import java.net.ServerSocket;

/*
Created By: Vasudevan Sampath

 IAppiumServiceHelper.java starts Appium server with dynamic port allocation
 */
public interface IAppiumServiceHelper {
        static AppiumDriverLocalService start(GeneralServerFlag generalServerFlag) {
            AppiumDriverLocalService appiumDriverLocalService =
                    new AppiumServiceBuilder().withArgument(generalServerFlag)
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
}

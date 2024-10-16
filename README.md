This is a unified test framework for mobile (iOS and Android) and web automation. 
This project showcases automation best practices using the following open source tools:

    TestNG
    Apache Maven
    Java 23 (can be used with any Java version starting with 8).
    Appium 2.x or greater
    Selenium 4.x or any newer version

To compile as a jar, mvn clean install

The intent of this project is for illustrative purposes only. The code can be freely used and modified as needed.

****************************************************************************************************************************************************
Setting up your environment for Appium (iOS/Android) and Selenium automation:

1. /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
2. (echo; echo 'eval "$(/opt/homebrew/bin/brew shellenv)"') >> /Users/your user name/.zprofile
3. eval "$(/opt/homebrew/bin/brew shellenv)"
4. Install XCode from App Store
5. brew install node
6. brew install java 
7. nano $HOME/.zshenv
    * export JAVA_HOME=$(/usr/libexec/java_home)
    * export PATH=${PATH}:$JAVA_HOME
    * export PATH=${PATH}:$JAVA_HOME/bin
8. sudo ln -sfn /opt/homebrew/opt/openjdk/libexec/openjdk.jdk /Library/Java/JavaVirtualMachines/openjdk.jdk
9. brew install maven
10. brew install --HEAD ideviceinstaller
11. brew install carthage
12. brew install ffmpeg
13. npm install -g appium
14. appium driver list (install all needed drivers)
15. appium plugin list (install all needed plugins)
16. Install Android Studio (Standard install)
17. nano $HOME/.zshenv 
    * export ANDROID_HOME=$HOME/Library/Android/sdk/
    * export PATH=$PATH:$ANDROID_HOME/tools
    * export PATH=$PATH:$ANDROID_HOME/platform-tools
16. npm install @appium/doctor -g
17. Setup required Android Emulators by running Android Studio
18. Run Xcode simulator and validate
19. Run Appium Inspector and setup configs for iOS and Android
20. Reinstall Xcode if any errors
21. If Android issue on real device,
    * adb uninstall io.appium.uiautomator2.server
    * adb uninstall io.appium.uiautomator2.server.test
    * adb uninstall io.appium.unlock
    * adb uninstall io.appium.settings
22. Check in Xcode Settings…Location for simulator -> may ask to confirm password
23. Run: appium-doctor and fix any issues (if any)
****************************************************************************************************************************************************

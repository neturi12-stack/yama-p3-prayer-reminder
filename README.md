# Yama P3 Prayer Reminder

A manual RuneLite Java plugin with sidebar controls and a movable in-game reminder.

| Sidebar button | Overlay |
| --- | --- |
| Range | PRAY MAGE |
| Mage | PRAY RANGE |
| Clear Reminder | Hidden |

The reminder remains until you change it, clear it, or disable the plugin. It is only drawn while logged in. This plugin does not detect Yama phases or change prayers for you; it displays the opposite of the button you choose.

## Requirements

- A JDK (Java Development Kit), version 11 or newer. JDK 17 is a practical choice for Gradle 8.
- Gradle 8.14.3, available from https://gradle.org/releases/ . Add its `bin` directory to PATH.
- Internet access for the first build to download RuneLite and dependencies.

This ZIP includes Gradle build files but no Gradle wrapper. Run `gradle`, not `gradlew`.

## Build and run on Windows

1. Extract the ZIP to a normal folder.
2. Open PowerShell in the extracted `yama-p3-prayer-reminder` folder (the folder containing `build.gradle`).
3. Confirm `java -version`, `javac -version`, and `gradle --version` work. Set JAVA_HOME to your JDK installation if necessary.
4. Build:

   ```powershell
   gradle clean build
   ```

5. Launch a development RuneLite client with this plugin loaded:

   ```powershell
   gradle run
   ```

6. In RuneLite's plugin settings, find and enable **Yama P3 Prayer Reminder** if it is disabled.
7. Click the blue **Y** icon in the right sidebar to open its controls.

For Jagex accounts, follow RuneLite's official development-client login guide:
https://github.com/runelite/runelite/wiki/Using-Jagex-Accounts

On macOS/Linux use the same `gradle` commands in a terminal.

## IntelliJ IDEA alternative

Open the extracted folder as a Gradle project, select your JDK as the Gradle JVM, allow dependency import to finish, and run the Gradle `run` task.

## Move and check the reminder

Log in, click **Range**, and confirm **PRAY MAGE** appears. Hold **Alt** and drag the visible reminder to reposition it. Click **Mage** and confirm it changes to **PRAY RANGE**. Click **Clear Reminder** and confirm it disappears. Disable the plugin and confirm its sidebar icon disappears; enabling it again should start with no reminder.

RuneLite manages overlay positioning. If Alt-drag does not work, check RuneLite's overlay movement hotkey setting.

## Installation scope

`gradle run` launches a separate development client with the plugin registered by `YamaP3Launcher`. It does not install the plugin into your regular RuneLite launcher or publish it to the Plugin Hub. Copying the built JAR into a regular RuneLite folder is not a supported installation method.

The source JAR output is `build/libs/yama-p3-prayer-reminder-1.0.0.jar`; it is not a standalone application. Launch using the `run` task above.

## Build details and validation

The project targets Java 11 bytecode and uses RuneLite's official Maven repository. By default it resolves the latest released RuneLite client. To build against a specific available release, use `gradle -PruneliteVersion=VERSION clean build`, replacing VERSION with its published version.

Reference template: https://github.com/runelite/example-plugin

The delivery environment had no JDK or Gradle, so this project has not been compiled or tested inside a live RuneLite client. The ZIP contents and source button mappings were checked before delivery. Use the build and manual checks above to validate it on your computer.

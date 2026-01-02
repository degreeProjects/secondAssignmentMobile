# MyKotlinApp - Android Application

A simple Android application written in Kotlin with a MainActivity and TextView.

## Project Structure

```
MyKotlinApp/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/example/mykotlinapp/
│   │       │   └── MainActivity.kt
│   │       ├── res/
│   │       │   ├── layout/
│   │       │   │   └── activity_main.xml
│   │       │   ├── values/
│   │       │   │   ├── strings.xml
│   │       │   │   ├── colors.xml
│   │       │   │   └── themes.xml
│   │       │   └── mipmap/ (launcher icons)
│   │       └── AndroidManifest.xml
│   └── build.gradle.kts
├── gradle/
│   └── wrapper/
├── build.gradle.kts
├── settings.gradle.kts
└── gradle.properties
```

## Requirements

- Android Studio (latest version recommended)
- JDK 8 or higher
- Android SDK with API level 34
- Minimum SDK: API 24 (Android 7.0)

## How to Run

1. **Open the project in Android Studio:**
   - Launch Android Studio
   - Click "Open" and select this project directory
   - Wait for Gradle sync to complete

2. **Set up an emulator or connect a device:**
   - For emulator: Go to Tools > Device Manager > Create Device
   - Recommended: Pixel 5 or newer with API 34
   - For physical device: Enable USB debugging in Developer Options

3. **Run the app:**
   - Click the green "Run" button (or press Shift+F10)
   - Select your device/emulator
   - The app will build and launch

## Features

- MainActivity with a centered TextView displaying "Hello World!"
- Material Design 3 theming
- Support for light and dark themes
- ConstraintLayout for responsive UI

## Configuration

- **Package name:** com.example.mykotlinapp
- **Min SDK:** 24 (Android 7.0)
- **Target SDK:** 34 (Android 14)
- **Compile SDK:** 34

## Dependencies

- AndroidX Core KTX: 1.12.0
- AppCompat: 1.6.1
- Material Components: 1.11.0
- ConstraintLayout: 2.1.4

## License

This project is open source and available for educational purposes.
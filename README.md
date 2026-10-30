# Tranzparency - Emergency SOS & Safety Application

**Tranzparency** is an Android personal safety app designed to provide rapid assistance during emergencies. With a single tap, physical shake gestures, or key combinations, users can broadcast emergency SOS alerts with their precise real-time location to trusted emergency contacts via SMS.

---

## 🌟 Key Features

* **🚨 One-Tap Emergency SOS:** Instantly broadcast emergency messages with live Google Maps location links to all saved emergency contacts.
* **👋 Shake & Volume Hardware Shortcut:** Trigger an emergency SOS discretely using device accelerometer shake detection combined with the hardware **Volume Up** button.
* **👥 Emergency Contacts ("Circle"):** Add, manage, and view your trusted emergency contact list locally on the device.
* **📍 Live Location Sharing:** Automatically fetches GPS coordinates via Android `LocationManager` to send precise location links (`https://maps.google.com/?q=lat,lng`).
* **📜 SOS Alert History & Logging:** Stores historical log of all sent emergency alerts with timestamps for auditing and peace of mind.
* **⚙️ Readiness Checks:** Verifies hardware state (GPS availability, active SIM card) before triggering alerts and alerts the user if the device is not ready.
* **🛡️ Safety Guidelines ("Explore"):** Integrated resource tab offering essential personal safety tips and best practices.
* **🎛️ Feature Toggles ("Profile"):** Custom switches to toggle shake detection and hardware key triggers according to user preference.

---

## 🛠️ Tech Stack & Architecture

| Component | Technology / Library |
| :--- | :--- |
| **Language** | Kotlin (1.9.22) |
| **Minimum SDK** | API 21 (Android 5.0 Lollipop) |
| **Target / Compile SDK** | API 34 (Android 14) |
| **Build System** | Gradle with Kotlin DSL (`build.gradle.kts`) |
| **AGP Version** | 8.13.2 |
| **Architecture** | Single-Activity Architecture (`MainActivity`) with Fragments |
| **UI Components** | AndroidX Appcompat, Material Design 3, ConstraintLayout, ViewBinding |
| **Location Services** | Google Play Services Location & Android `LocationManager` |
| **Telephony & SMS** | Android `SmsManager` & `TelephonyManager` |
| **Hardware Sensors** | Android `SensorManager` & Accelerometer (`ShakeDetector`) |
| **Local Persistence** | Android `SharedPreferences` (`ContactStore`, `ArchiveStore`) |

---

## 📂 Project Structure

```text
Tranzparency/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/tranzparency/
│   │   │   │   ├── MainActivity.kt        # Primary host activity & SOS orchestrator
│   │   │   │   ├── CircleFragment.kt      # Emergency contacts management view
│   │   │   │   ├── ExploreFragment.kt     # Safety tips & guidelines view
│   │   │   │   ├── ProfileFragment.kt     # User preferences & feature switches
│   │   │   │   ├── ContactStore.kt        # Contact persistence via SharedPreferences
│   │   │   │   ├── ArchiveStore.kt        # SOS log history persistence
│   │   │   │   ├── ShakeDetector.kt       # SensorEventListener for accelerometer shake
│   │   │   │   ├── LocationHelper.kt      # GPS Location fetching utility
│   │   │   │   ├── SMSHelper.kt           # SmsManager wrapper for sending text alerts
│   │   │   │   └── PermissionsHelper.kt   # Dynamic runtime permissions utility
│   │   │   ├── res/                       # Layouts, themes, drawables, strings
│   │   │   └── AndroidManifest.xml        # Application declarations & permissions
│   │   └── test/                          # Unit and instrumented tests
│   └── build.gradle.kts                   # App module build configuration
├── build.gradle.kts                       # Root build script
├── settings.gradle.kts                    # Project & repository settings
└── README.md                              # Project documentation
```

---

## 🔐 Required Permissions

The application requests the following Android permissions to function properly:

* `SEND_SMS`, `RECEIVE_SMS`, `READ_SMS`: Required to send emergency alert text messages to contacts.
* `ACCESS_FINE_LOCATION`, `ACCESS_COARSE_LOCATION`: Required to attach accurate GPS coordinates to emergency SMS messages.
* `READ_PHONE_STATE`: Used for checking SIM card readiness prior to sending alerts.

---

## 🚀 Getting Started

### Prerequisites
* **Android Studio**: Jellyfish (2023.3.1) or newer recommended.
* **JDK**: Java 17.
* **Android Device or Emulator**: Android 5.0 (API level 21) or higher. *(A physical device with an active SIM card and GPS enabled is recommended to test SMS and Location functionality).*

### Installation & Build

1. **Clone or Open the Repository:**
   Open Android Studio and select **File > Open**, then select the project directory.

2. **Gradle Sync:**
   Allow Android Studio to sync Gradle dependencies automatically, or run:
   ```bash
   ./gradlew sync
   ```

3. **Build the Project:**
   ```bash
   ./gradlew assembleDebug
   ```

4. **Run on Device/Emulator:**
   Connect a target device with USB debugging enabled and click **Run 'app'** in Android Studio or execute:
   ```bash
   ./gradlew installDebug
   ```

---

## 📱 How It Works

1. **Permissions Request:** Upon launching the app for the first time, grant SMS and Location permissions when prompted.
2. **Add Emergency Contacts:** Navigate to the **Contacts** tab ("Circle"), tap the floating `+` button, and enter phone numbers for your emergency contacts.
3. **Triggering SOS:**
   * **Manual:** Press the big red **SOS** button on the Home screen.
   * **Shake Gesture:** Shake the phone to trigger the shake detector, then press the **Volume Up** button.
4. **Readiness Verification:** The app checks that GPS is enabled and a SIM card is ready. If not, it prompts the user to enable them.
5. **Dispatch:** The app fetches the current GPS location, generates a Google Maps link, and sends an emergency SMS to all saved contacts, while archiving the event in local history.

---

## 👨‍💻 Author

Developed with ❤️ by **Shivam**

* **Project:** Tranzparency
* **GitHub:** [@ShivamPrajapati-7](https://github.com/ShivamPrajapati-7)

---

## 📄 License

This project is maintained for personal safety research and emergency assistance development.

# PinPlace: Location Saver App

PinPlace is a simple and useful Android application that allows users to **save their current location** and revisit it later. The app is designed for quick access, offline storage, share, and easy navigation using Google Maps.

---

## Features

- Save current location (latitude & longitude)
- Add custom name and description for each location
- View all saved locations in a list
- Open saved locations directly in **Google Maps**
- Edit saved locations
- Delete saved locations
- Share the saved locations
- Offline storage using **Room Database**
- Clean and minimal UI (Material Design)

---

## Tech Stack

- **Language:** Java  
- **UI:** XML (Material Design)  
- **Database:** Room (SQLite abstraction)  
- **Location Services:** FusedLocationProviderClient  
- **Maps Integration:** Google Maps Intent  
- **IDE:** Android Studio  

---

## Screenshots

_Screenshots will be added soon_

---

## How It Works

1. The app requests location permission at runtime
2. Current location is fetched using Fused Location Provider
3. User saves the location with a name and description
4. Data is stored locally using Room Database
5. Saved locations can be viewed and opened in Google Maps

---

## Permissions Used

```xml
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
```

---

## Installation

1. Clone the repository  
   ```bash
   git clone https://github.com/your-username/PinPlace.git
   ```
2. Open the project in **Android Studio**
3. Sync Gradle files
4. Run the app on an emulator or physical device

---
## APK Download
-> [Download Latest APK (v1.1)](https://github.com/prateek-dhanker/PinPlace/releases/tag/v1.1)

### Changelog
- v1.1: Added share feature
- v1.0: Initial version

---

## Author

**Prateek Dhanker**  
Software Developer

---

## License

This project is licensed under the **MIT License**.
# FleetControlApp - Offline Fleet Management Android Application

## Overview

FleetControlApp is an Android application designed for managing vehicle fleets entirely offline. This app allows users to store, edit, and export vehicle information without requiring an internet connection, making it ideal for field operations in areas with limited connectivity.

## Key Features

- **Offline Operation**: All data is stored locally on the device
- **CSV Import/Export**: Easily import existing data or export for backup
- **Vehicle Management**: Add, edit, and delete vehicle records
- **Search Functionality**: Find vehicles by driver name
- **Material Design**: Modern, intuitive user interface
- **Data Persistence**: Automatic saving using Room Database

## Prerequisites

To use and modify this project, you'll need:

- **Android Studio** (Giraffe or later recommended)
- **Java Development Kit** (JDK 17 or later)
- **Android device or emulator** (API 21+)

## Getting Started

### Installation

1. Clone the repository:
   ```bash
   https://github.com/AlexandreZanata/Fleet-Control-App-zanata-control-.git
   ```
2. Open the project in Android Studio
3. Build the project (Build > Make Project)
4. Run the app on an emulator or physical device

### Basic Usage

1. **Adding a Vehicle**:
   - Tap the "+" button
   - Fill in vehicle details
   - Tap "Save"

2. **Editing a Vehicle**:
   - Tap on any vehicle in the list
   - Modify the information
   - Tap "Save"

3. **Deleting a Vehicle**:
   - Tap on a vehicle to edit
   - Tap the delete icon (trash can) in the top right

4. **Importing Data**:
   - Tap the menu icon (⋮) in the top right
   - Select "Import CSV"
   - Choose a CSV file from your device

5. **Exporting Data**:
   - Tap the menu icon (⋮)
   - Select "Export CSV"
   - Choose a save location

## Project Structure

```
FleetControlApp/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/fleetcontrolapp/
│   │   │   │   ├── adapter/          # RecyclerView adapters
│   │   │   │   ├── dao/              # Database access objects
│   │   │   │   ├── database/         # Database setup
│   │   │   │   ├── model/            # Data models
│   │   │   │   ├── repository/       # Data repositories
│   │   │   │   ├── viewmodel/        # ViewModels
│   │   │   │   ├── MainActivity.kt   # Main screen
│   │   │   │   └── ...               # Other activities
│   │   │   ├── res/                  # Resources
│   │   │   └── AndroidManifest.xml
│   └── build.gradle                  # Module-level build config
├── gradle/
└── build.gradle                      # Project-level build config
```

## Development Guide

### Modifying the Project

1. **Adding New Fields**:
   - Update the `Veiculo.kt` data class
   - Modify the `item_veiculo.xml` and `activity_adicionar_editar_veiculo.xml`
   - Update the import/export functions in `MainActivity.kt`

2. **Changing the UI**:
   - Edit the XML layout files in `res/layout/`
   - Modify styles in `res/values/themes.xml`

3. **Adding New Features**:
   - Create new activities for additional screens
   - Extend the ViewModel and Repository as needed

### Required Tools

- **Android Studio**: Primary development environment
- **Git**: Version control
- **Room Database**: For local storage
- **OpenCSV**: For CSV handling
- **Material Components**: For UI elements

## Dependencies

The project uses the following libraries:

```gradle
dependencies {
    // Room components
    implementation "androidx.room:room-runtime:2.6.1"
    kapt "androidx.room:room-compiler:2.6.1"
    
    // Lifecycle components
    implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2"
    
    // CSV handling
    implementation 'com.opencsv:opencsv:5.8'
    
    // Coroutines
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3'
    
    // Material Design
    implementation 'com.google.android.material:material:1.10.0'
}
```

## Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a new branch (`git checkout -b feature/your-feature`)
3. Commit your changes (`git commit -m 'Add some feature'`)
4. Push to the branch (`git push origin feature/your-feature`)
5. Open a pull request

## License

This project is licensed under the MIT License

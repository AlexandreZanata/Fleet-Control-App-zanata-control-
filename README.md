# Fleet-Control-App-zanata-control-
A simple offline-first Android application for managing a vehicle fleet. It allows users to add, edit, and view vehicle data, as well as import and export the entire dataset as a CSV file.

🚀 Features
Offline Data Storage: All data is stored locally on the device using the Room Persistence Library.

CRUD Operations: Create, Read, Update, and Delete vehicle entries.

CSV Import/Export: Easily back up and restore your fleet data by exporting to and importing from a CSV file.

Clean User Interface: A simple and intuitive UI built with Material Design components, RecyclerView, and CardView.

Modern Android Architecture: Built using modern Android development practices, including:

Kotlin

Coroutines for asynchronous operations

ViewModel and LiveData for a lifecycle-aware UI

Repository pattern for data abstraction

🛠️ Tools and Technologies
Language: Kotlin

IDE: Android Studio

Architecture: MVVM (Model-View-ViewModel)

Core Libraries:

Android Jetpack:

Room: For local database storage.

ViewModel: To store and manage UI-related data.

LiveData: To build data objects that notify views of database changes.

ViewBinding: To more easily write code that interacts with views.

Coroutines: For managing background threads.

OpenCSV: For handling CSV file import and export operations.

Material Components: For UI elements.

⚙️ How to Set Up and Run the Project
To get this project running on your local machine, follow these steps:

Prerequisites:

Make sure you have the latest version of Android Studio installed.

A physical Android device or an emulator running API 21 (Android 5.0) or higher.

Clone the Repository:

git clone https://github.com/your-username/fleet-control-app.git

Open in Android Studio:

Open Android Studio.

Click on File -> Open...

Navigate to the cloned repository folder and select it.

Build and Run:

Let Android Studio sync the Gradle files.

Click the Run 'app' button (green play icon) and select a target device (emulator or physical device).

📱 How to Use the Application
Main Screen: Displays a list of all registered vehicles. If the list is empty, a message will appear.

Add a Vehicle: Tap the floating + button at the bottom right to open the "Add Vehicle" screen.

Fill in Details: Enter the vehicle's information in the provided form fields. The Plate field is mandatory and acts as a unique identifier.

Save: Tap the "Save" button to add the vehicle to the database. You will be returned to the main screen.

Edit a Vehicle: Tap on any vehicle in the list to open the "Edit Vehicle" screen with its details pre-filled. Make your changes and tap "Save".

Import/Export Data:

Tap the three-dot menu in the top-right corner.

Select "Import CSV" to select a CSV file from your device and add its data to the app.

Select "Export CSV" to save all vehicle data into a new CSV file named frota_export.csv.

CSV File Format
For the import functionality to work correctly, your CSV file must have a header row and the following columns in this exact order:

PLACA,VEICULO,MARCA,MODELO,ANO,ONUS,VALOR,CHASSI,RENAVAN,MOTORISTA,CEP

The app will skip the header row when importing.

Each subsequent row should represent a single vehicle with its data matching the column order.

✏️ How to Edit and Customize
This project is structured to be easily maintainable and extensible.

1. Modifying the User Interface
Main list item: The layout for each vehicle card in the list is defined in app/src/main/res/layout/item_veiculo.xml.

Main screen: The main activity layout containing the list and FAB is in app/src/main/res/layout/activity_main.xml.

Add/Edit screen: The form for adding or editing a vehicle is in app/src/main/res/layout/activity_adicionar_editar_veiculo.xml.

2. Adding a New Field
If you want to store more information about each vehicle (e.g., "Fuel Type"), follow these steps:

Update the Model: Add the new property to the Veiculo.kt data class.

// in app/src/main/java/com/your_name/controledefrota/model/Veiculo.kt
data class Veiculo(
    // ... existing fields
    val fuelType: String
)

Update the Database: You need to handle the database migration. The simplest way for a small change is to increment the version number in AppDatabase.kt and add a migration strategy (or just uninstall and reinstall the app during development).

// in app/src/main/java/com/your_name/controledefrota/database/AppDatabase.kt
@Database(entities = [Veiculo::class], version = 2, exportSchema = false) // Increment version

Update the UI: Add a new TextInputEditText for the new field in activity_adicionar_editar_veiculo.xml.

Update the Logic:

In AdicionarEditarVeiculoActivity.kt, read the text from the new input field and save it to the Veiculo object in the salvarVeiculo() function.

Update preencherCampos() to display the new field's data when editing.

Update CSV Handling:

In MainActivity.kt, add the new field to the header array in exportarCSV().

Add the new property to the linha array in exportarCSV().

Update the importarCSV() function to read the new column from the CSV file. Remember to adjust the column index check (if (it.size >= 12)).

📄 License
This project is licensed under the MIT License. See the LICENSE file for details.

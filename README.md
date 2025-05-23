
# ♿ Sohoj Path — Accessibility Info App

**Sohoj Path** is an Android application designed to help wheelchair users discover accessible public places in Bangladesh. The app provides details like whether a place has accessible entrances, toilets, and an overall accessibility rating. It also allows users to add, view, edit, or delete entries about public places.

## 🚀 Features

* 🔍 View a list of accessible public places
* ➕ Add a new place with detailed accessibility information
* ✏️ Edit or delete entries (only if you're the one who added them)
* 👁️ View full details of any place
* 🔐 User authentication with Firebase
* ☁️ Firestore backend for real-time data storage and sync

## 🏗️ Tech Stack

* **Language**: Java
* **Framework**: Android SDK (Android Studio Meerkat)
* **Database**: Firebase Firestore
* **Authentication**: Firebase Authentication
* **Architecture**: MVVM-lite (Fragements + Firebase)

## 🖼️ Screenshots

| Home Screen              | Add Place                | View Place               |
| ------------------------ | ------------------------ | ------------------------ |
|![image](https://github.com/user-attachments/assets/817582e8-b657-4678-8c8d-9af17a883a7c)|![image](https://github.com/user-attachments/assets/03d7706c-8389-456f-ba26-032c9a9ccde6)|![image](https://github.com/user-attachments/assets/a4c5e67d-a77c-4637-b272-bbb1d83a5299)|

## 📂 Project Structure

```
├── MainActivity.java
├── fragments/
│   ├── AddFragment.java
│   ├── HomeFragment.java
│   ├── UpdatePlaceFragment.java
│   ├── ViewPlaceFragment.java
├── models/
│   └── Place.java
├── adapters/
│   └── PlaceAdapter.java
├── res/
│   └── layout/
│       ├── fragment_add.xml
│       ├── fragment_home.xml
│       ├── fragment_view_place.xml
│       ├── fragment_update_place.xml
│       └── item_place.xml
```

## 🧪 How to Use

1. Clone the repository:

   ```
   git clone https://github.com/your-username/Sohoj-Path.git
   ```
2. Open the project in Android Studio.
3. Connect your Firebase project (or import `google-services.json`).
4. Run the app on a real or virtual Android device.
5. Sign up/login and start adding or exploring accessible places.

## ✍️ Data Fields Stored in Firestore

Each place contains:

* `name`
* `city`
* `type` (e.g., Commercial, Educational)
* `address`
* `accessible_entrance` (Yes/No)
* `accessible_toilet` (Yes/No)
* `notes`
* `rating` (Low, Moderate, High)
* `added_by` (email)


## 📦 Dependencies

To successfully run this project, the following dependencies are required in your `build.gradle` files:

### 🧱 **Project-level `build.gradle`**

Make sure to include the Google services classpath:

```
buildscript {
    dependencies {
        classpath 'com.google.gms:google-services:4.4.1' // or latest
    }
}
```

### 📱 **App-level `build.gradle`**

These are the core dependencies used in the app:

```
// Firebase
implementation 'com.google.firebase:firebase-auth:22.3.1'
implementation 'com.google.firebase:firebase-firestore:24.10.3'

// Material Design
implementation 'com.google.android.material:material:1.11.0'

// RecyclerView and CardView
implementation 'androidx.recyclerview:recyclerview:1.3.2'
implementation 'androidx.cardview:cardview:1.0.0'

// AndroidX Core and AppCompat
implementation 'androidx.appcompat:appcompat:1.6.1'
implementation 'androidx.constraintlayout:constraintlayout:2.1.4'

// Navigation Components (if used)
implementation 'androidx.navigation:navigation-fragment:2.7.7'
implementation 'androidx.navigation:navigation-ui:2.7.7'

// Glide (optional for image support)
implementation 'com.github.bumptech.glide:glide:4.16.0'
annotationProcessor 'com.github.bumptech.glide:compiler:4.16.0'
```

And don’t forget to apply the Firebase plugin at the bottom of the `app/build.gradle`:

```
apply plugin: 'com.google.gms.google-services'
```

## 🔐 Permissions Required

* Internet access (for Firebase)
* Network state (optional)

## 🤝 Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss your ideas.

To contribute:

1. Fork this repository
2. Create your feature branch (`git checkout -b feature/my-feature`)
3. Commit your changes (`git commit -am 'Add some feature'`)
4. Push to the branch (`git push origin feature/my-feature`)
5. Create a new Pull Request

## 🧑‍💻 Author

* **Shazzatul Islam Anam**

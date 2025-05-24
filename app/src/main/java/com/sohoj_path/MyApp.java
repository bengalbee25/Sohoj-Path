package com.sohoj_path;

import android.app.Application;
import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseFirestore.getInstance().clearPersistence()
                .addOnCompleteListener(task -> {
                    Log.d("MyApp", "Local cache cleared");

                    FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                            .setPersistenceEnabled(false)
                            .build();

                    FirebaseFirestore.getInstance().setFirestoreSettings(settings);
                    Log.d("MyApp", "Firestore persistence disabled");
                });
    }

}

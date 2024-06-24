package com.example.project2.app

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

/**
 * This is the main application class. It initializes Firebase.
 */
class MyApp : Application() {
    /**
     * Called when the application is starting. This is where most initialization should go.
     */
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}
package com.cris.proyfirestorek

import android.app.Application
import com.google.firebase.FirebaseApp

class FirestoreBasicoYtApp: Application() {

    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)
    }
}
package com.cris.proyfirestorek.data.common

import com.google.firebase.firestore.FirebaseFirestore

object FirestoreInstance {
    //proporciona la instancia del firestore
    fun get(): FirebaseFirestore{
        return FirebaseFirestore.getInstance()
    }

}
package com.example.project2.app

import com.example.project2.app.Register
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
//import com.example.project2.app.Register


class FireStoreClass {

    private val mFireStore = FirebaseFirestore.getInstance()


    fun registerUserFS(activity: Register, userInfo: User) {

        mFireStore.collection("users")
            .document(userInfo.id)
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                activity.userRegistrationSuccess()

            }
            .addOnFailureListener {

            }
    }

}
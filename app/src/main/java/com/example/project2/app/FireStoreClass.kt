package com.example.project2.app

import com.example.project2.DB_management.dto.user.CreateUserDto
import com.example.project2.app.Register
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
//import com.example.project2.app.Register

/**
 * This class is used to interact with the Firestore database.
 */
class FireStoreClass {

    private val mFireStore = FirebaseFirestore.getInstance()

    /**
     * Registers a new user in the Firestore database.
     *
     * @param activity The activity that is calling this function.
     * @param userInfo The user data to be registered.
     */
    fun registerUserFS(activity: Register, userInfo: CreateUserDto) {

        mFireStore.collection("users")
            .document(userInfo.username)
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                activity.userRegistrationSuccess()

            }
            .addOnFailureListener {

            }
    }

}
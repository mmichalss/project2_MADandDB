package com.example.project2.DB_management

import android.content.ContentValues.TAG
import android.util.Log
import com.example.project2.DB_management.common_types.ResultValue
import com.example.project2.DB_management.common_types.TestType
import com.example.project2.DB_management.dto.result.CreateResultDto
import com.example.project2.DB_management.dto.result.GetResultDto
import com.example.project2.DB_management.dto.test.CreateTestDto
import com.example.project2.DB_management.dto.test.GetTestDto
import com.example.project2.DB_management.dto.user.CreateUserDto
import com.example.project2.DB_management.dto.user.GetUserDto
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskCompletionSource
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class DbClient() {

    val db = Firebase.firestore

    // Create a new user with a first and last name
    val user = hashMapOf(
        "first" to "Ada",
        "last" to "Lovelace",
        "born" to 1815
    )

    // Add a new document with a generated ID
    fun addUser(user: CreateUserDto) {
        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }


    fun getUser(id: String): Task<GetUserDto?> {
        return db.collection("users")
            .document(id)
            .get()
            .continueWith { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document != null && document.exists()) {
                        val user = document.toObject(GetUserDto::class.java)
                        user?.id = document.id
                        user // Return the GetUserDto object
                    } else {
                        null // Return null if the document doesn't exist
                    }
                } else {
                    Log.w(TAG, "Error getting document.", task.exception)
                    null // Return null in case of an error
                }
            }
    }


   fun getLoggedUser(): GetUserDto? {
    val user = FirebaseAuth.getInstance().currentUser
    return if (user != null) {
        // User is signed in
        GetUserDto(user.uid, user.email!!, "")
    } else {
        null
    }
}

    fun addTest(test: CreateTestDto){
        db.collection("tests")
            .add(test)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    fun getTest(id: String): Task<GetTestDto?> {
        return db.collection("results")
            .document(id)
            .get()
            .continueWith { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document != null && document.exists()) {
                        val test = document.toObject(GetTestDto::class.java)
                        test?.id = document.id
                        test // Return the GetUserDto object
                    } else {
                        null // Return null if the document doesn't exist
                    }
                } else {
                    Log.w(TAG, "Error getting document.", task.exception)
                    null // Return null in case of an error
                }
            }
    }

    fun addResult(result: CreateResultDto){
        db.collection("results")
            .add(result)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    fun getResultsByUserId(userId: String): Task<List<GetResultDto?>> {
    val db = FirebaseFirestore.getInstance()
    val taskCompletionSource = TaskCompletionSource<List<GetResultDto?>>()

    Log.e("Test", "Getting results for user ID: $userId")

    db.collection("results")
        .whereEqualTo("userId", userId)
        .get()
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val resultList = mutableListOf<GetResultDto?>()
                for (document in task.result) {
                    Log.d("Test", "Document data: ${document.data}")
                    val result = document.let {
                        GetResultDto(
                            id = it.id,
                            resultValue = ResultValue.fromString(it.getString("resultValue") ?: "NONE"),
                            testId = TestType.fromString(it.getString("testId") ?: "TEST1"),
                            timeSpent = it.getLong("timeSpent")?.toInt() ?: 0,
                            userId = it.getString("userId") ?: ""
                        )
                    }
                    resultList.add(result)
                }
                taskCompletionSource.setResult(resultList)
            } else {
                Log.w("Test", "Error getting documents.", task.exception)
                taskCompletionSource.setException(task.exception ?: Exception("Unknown error occurred"))
            }
        }

    return taskCompletionSource.task
}


}
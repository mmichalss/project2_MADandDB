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

    /**
     * Adds a new user to the Firestore database.
     *
     * @param user The user data to be added.
     */
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

    /**
     * Retrieves a user from the Firestore database by their ID.
     *
     * @param id The ID of the user to retrieve.
     * @return A Task that will be resolved with the user data or null if the user doesn't exist.
     */
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


    /**
     * Retrieves the currently logged in user.
     *
     * @return The data of the logged in user or null if no user is logged in.
     */
   fun getLoggedUser(): GetUserDto? {
    val user = FirebaseAuth.getInstance().currentUser
    return if (user != null) {
        // User is signed in
        GetUserDto(user.uid, user.email!!, "")
    } else {
        null
    }
}
    /**
     * Adds a new test to the Firestore database.
     *
     * @param test The test data to be added.
     */
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

    /**
     * Retrieves a test from the Firestore database by its ID.
     *
     * @param id The ID of the test to retrieve.
     * @return A Task that will be resolved with the test data or null if the test doesn't exist.
     */
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

    /**
     * Adds a new result to the Firestore database.
     *
     * @param result The result data to be added.
     */
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

    /**
     * Retrieves a result from the Firestore database by its ID.
     *
     * @param id The ID of the result to retrieve.
     * @return A Task that will be resolved with the result data or null if the result doesn't exist.
     */
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
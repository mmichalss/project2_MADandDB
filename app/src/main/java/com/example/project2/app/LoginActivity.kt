package com.example.project2.app

import android.app.Activity
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import com.example.project2.R
import com.example.project2.app.test1.Test1HomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp

/**
 * This is the login activity. It handles the login process for the user.
 */
class LoginActivity : Activity() {

    private var inputEmail: EditText? = null
    private var inputPassword: EditText? = null
    private var loginButton: Button? = null
    private lateinit var auth: FirebaseAuth

    /**
     * Called when the activity is starting. This is where most initialization should go.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContentView(R.layout.activity_login)
        auth = Firebase.auth

        inputEmail = findViewById(R.id.editTextTextEmailAddress)
        inputPassword = findViewById(R.id.editTextPassword)
        loginButton = findViewById(R.id.buttonletsplay)
        val registerButton = findViewById<TextView>(R.id.registerHere)

        registerButton.setOnClickListener {
            val intent = Intent(applicationContext, Register::class.java)
            startActivity(intent)
        }

        loginButton?.setOnClickListener {
            logInRegisteredUser()
        }

//        if (Build.VERSION.SDK_INT >= 33) {
//            notificationPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
//        } else {
//            hasNotificationPermissionGranted = true
//        }

        var playAsGuest = findViewById<Button>(R.id.guestbutton)
        playAsGuest.setOnClickListener {
            val intent = Intent(this, MainPage::class.java)
            startActivity(intent)
        }
    }

    /**
     * This function validates the login details entered by the user.
     * @return true if the login details are valid, false otherwise.
     */
    private fun validateLoginDetails(): Boolean {
        return when {
            TextUtils.isEmpty(inputEmail?.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar("Please enter email", true)
                false
            }
            TextUtils.isEmpty(inputPassword?.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar("Please enter password", true)
                false
            }
            else -> {
                true
            }
        }
    }

    /**
     * This function logs in a registered user.
     */
    private fun logInRegisteredUser() {
        if (validateLoginDetails()) {
            val email = inputEmail?.text.toString().trim()
            val password = inputPassword?.text.toString().trim()

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        GlobalScope.launch(Dispatchers.Main) {
                            val intent = Intent(this@LoginActivity, MainPage::class.java)
                            startActivity(intent)
                            finish()
                        }
                    } else {
                        showErrorSnackBar(task.exception?.message ?: "Login failed", true)
                    }
                }
        }
    }
    /**
     * This function shows a snackbar with an error message.
     * @param message The message to show in the snackbar.
     * @param errorMessage true if the message is an error message, false otherwise.
     */
    private fun showErrorSnackBar(message: String, errorMessage: Boolean) {
        val snackbar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        val snackbarView = snackbar.view

        if (errorMessage) {
            snackbarView.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(this@LoginActivity, R.color.red)
            )
        } else {
            snackbarView.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(this@LoginActivity, R.color.green)
            )
        }
        snackbar.show()
    }
}

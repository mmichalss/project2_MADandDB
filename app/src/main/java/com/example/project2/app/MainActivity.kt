package com.example.project2.app

import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import androidx.activity.result.contract.ActivityResultContracts
import com.example.project2.R

class MainActivity : ComponentActivity() {

    private var inputEmail: EditText? = null
    private var inputPassword: EditText? = null
    private var loginButton: Button? = null

    private val notificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            hasNotificationPermissionGranted = isGranted
            if (!isGranted) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (Build.VERSION.SDK_INT >= 33) {
                        if (shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS)) {
                            showNotificationPermissionRationale()
                        } else {
                            showSettingDialog()
                        }
                    }
                }
            } else {

            }
        }

    private fun showSettingDialog() {
        MaterialAlertDialogBuilder(this, com.google.android.material.R.style.MaterialAlertDialog_Material3)
            .setTitle("Notification Permission")
            .setMessage("Notification permission is required, Please allow notification permission from setting")
            .setPositiveButton("Ok") { _, _ ->
                val intent = Intent(ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.data = Uri.parse("package:$packageName")
                startActivity(intent)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showNotificationPermissionRationale() {
        MaterialAlertDialogBuilder(this, com.google.android.material.R.style.MaterialAlertDialog_Material3)
            .setTitle("Alert")
            .setMessage("Notification permission is required, to show notification")
            .setPositiveButton("Ok") { _, _ ->
                if (Build.VERSION.SDK_INT >= 33) {
                    notificationPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    var hasNotificationPermissionGranted = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

    private fun logInRegisteredUser() {
        if (validateLoginDetails()) {
            val email = inputEmail?.text.toString().trim()
            val password = inputPassword?.text.toString().trim()

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        GlobalScope.launch(Dispatchers.Main) {
                            val intent = Intent(this@MainActivity, MainPage::class.java)
                            startActivity(intent)
                            finish()
                        }
                    } else {
                        showErrorSnackBar(task.exception?.message ?: "Login failed", true)
                    }
                }
        }
    }

    private fun showErrorSnackBar(message: String, errorMessage: Boolean) {
        val snackbar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        val snackbarView = snackbar.view

        if (errorMessage) {
            snackbarView.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(this@MainActivity, R.color.red)
            )
        } else {
            snackbarView.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(this@MainActivity, R.color.green)
            )
        }
        snackbar.show()
    }
}

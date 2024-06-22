package com.example.project2.app

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.text.TextUtils
import com.example.project2.R
import android.widget.Button
import kotlinx.coroutines.launch
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope

class Register : ComponentActivity() {

    private lateinit var inputEmail: EditText
    private lateinit var inputName: EditText
    private lateinit var inputPassword: EditText
    private lateinit var inputRepPass: EditText

    private lateinit var inputLastName: EditText
    private lateinit var inputDate: EditText

    /**
     * Activity for registering new users to FireBase and SQL Database
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val logInButton =findViewById<TextView>(R.id.logInText)
        logInButton.setOnClickListener {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
        }


//        val testConnectionButton: Button = findViewById(R.id.buttonTestConnection)
//        testConnectionButton.setOnClickListener {
//
//        }







        val registerButton: Button = findViewById(R.id.buttonSignUp)
        registerButton.isEnabled = true

        inputEmail = findViewById(R.id.editTextEmailAddress)
        inputName = findViewById(R.id.editTextName)
        inputPassword = findViewById(R.id.editTextPassword)
        inputRepPass = findViewById(R.id.editTextRePassword)
        inputLastName = findViewById(R.id.editTextLastName)
        inputDate= findViewById(R.id.editTextDate)

        registerButton.setOnClickListener{
            validateRegisterDetails()
            registerUser()
        }
    }

    /**
     * Validates data provided by the user
     * @param none
     * @return [Boolean] whether details are valid or not
     */
    private fun validateRegisterDetails(): Boolean {
        val specialCharacters = "[!@#\$%^&*(),.?\":{}|<>]"
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+"
        val passwordContainsNumber = "1234567890"
        return when{
            TextUtils.isEmpty(inputEmail.text.toString().trim{ it <= ' '}) -> {
                showErrorSnackBar("Please enter your email",true)
                false
            }
            TextUtils.isEmpty(inputName.text.toString().trim{ it <= ' '}) -> {
                showErrorSnackBar("Please enter your name",true)
                false
            }
            TextUtils.isEmpty(inputPassword.text.toString().trim{ it <= ' '}) -> {
                showErrorSnackBar("Please enter password",true)
                false
            }
            TextUtils.isEmpty(inputRepPass.text.toString().trim{ it <= ' '}) -> {
                showErrorSnackBar("Please enter password",true)
                false
            }
            inputPassword.text.toString().trim {it <= ' '} != inputRepPass.text.toString().trim{it <= ' '} -> {
                showErrorSnackBar("Passwords must match",true)
                false
            }
            !inputPassword.text.toString().trim().matches(Regex(".*[$specialCharacters].*")) -> {
                showErrorSnackBar("Password must contain at least one special character", true)
                false
            }
            inputPassword.text.toString().trim().length < 8 -> {
                showErrorSnackBar("Password must have at least 8 characters", true)
                false
            }
            !inputEmail.text.toString().trim().matches(Regex(emailPattern)) -> {
                showErrorSnackBar("Please enter a valid email address", true)
                false
            }
            !inputPassword.text.toString().trim().matches(Regex(".*[$passwordContainsNumber].*")) -> {
                showErrorSnackBar("Password must contain a number", true)
                false
            }
            else -> {
                showErrorSnackBar("Your details are valid",false)
                true
            }
        }
    }
    /**
     * Builds the snackbar
     * @param [message: String, errorMessage: Boolean] message to be displayed and whether it is green or red
     * @return none
     */
    private fun showErrorSnackBar(message: String, errorMessage: Boolean) {
        val snackbar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        val snackbarView = snackbar.view

        if (errorMessage) {
            snackbarView.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    this@Register,
                    R.color.red
                )
            )
        } else {
            snackbarView.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    this@Register,
                    R.color.green
                )
            )
        }

        snackbar.show()
    }

    fun  userRegistrationSuccess(){
        Toast.makeText(this@Register, "Registered Successfully", Toast.LENGTH_LONG).show()
    }

    /**
     * Registers the user to FireBase and logs them in with their profile ID if successful
     * @param none
     * @return none
     */
    private fun registerUser() {
        if (validateRegisterDetails()) {
            val login: String = inputEmail.text.toString().trim() { it <= ' ' }
            val password: String = inputPassword.text.toString().trim() { it <= ' ' }
            val name: String = inputName.text.toString().trim() { it <= ' ' }

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(login, password)
                .addOnCompleteListener(
                    OnCompleteListener<AuthResult> { task ->

                        if (task.isSuccessful) {
                            val firebaseUser: FirebaseUser = task.result!!.user!!
                            showErrorSnackBar(
                                "You are registered successfully. Your user id is ${firebaseUser.uid}",
                                false
                            )
                            val email = FirebaseAuth.getInstance().currentUser?.email.toString()
                            val user = User(
                                email,
                                name,
                                true,
                                login,
                                true
                            )
                            FireStoreClass().registerUserFS(this@Register, user)

                            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener { signInTask ->

                                    if (signInTask.isSuccessful) {
                                        GlobalScope.launch(Dispatchers.Main) {
                                            showErrorSnackBar("You are logged in successfully.", false)
                                            val intent = Intent(this@Register, MainPage::class.java)
                                            startActivity(intent)
                                        }
                                    } else {
                                        showErrorSnackBar(signInTask.exception!!.message.toString(), true)
                                    }
                                }

                        } else {
                            showErrorSnackBar(task.exception!!.message.toString(), true)
                        }
                    }
                )
        }
    }}

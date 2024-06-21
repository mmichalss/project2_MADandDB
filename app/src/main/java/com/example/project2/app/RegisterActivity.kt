package com.example.project2.app

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.project2.DB_management.dto.user.CreateUserDto
import com.example.project2.R
import kotlinx.coroutines.runBlocking

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val button = findViewById<Button>(R.id.registerBTN)

        button.setOnClickListener{
            runBlocking { logInUser() }
        }


    }

    private suspend fun logInUser(){
        print("Testing insertUserDto()")
        val newUser = CreateUserDto("user", "password")
    }
}
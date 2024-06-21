package com.example.project2.app

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.project2.DB_management.DbClient
import com.example.project2.DB_management.dto.user.CreateUserDto
import com.example.project2.R

class DbTesting : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_db_testing)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val db = DbClient()

        val addButton = findViewById<Button>(R.id.addButton)
        val user = CreateUserDto("user", "password")

        addButton.setOnClickListener {
            db.getUser("1NG4WVh8nxPYE8mVd1HK").addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userRetrieved = task.result
                    addButton.text = userRetrieved.toString()
                } else {
                    Log.w(TAG, "Error retrieving user", task.exception)
                }
            }

        }
    }
}
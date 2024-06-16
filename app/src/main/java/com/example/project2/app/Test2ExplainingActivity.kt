package com.example.project2.app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.project2.R
import com.example.project2.Test2Activity

class Test2ExplainingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_test2_explaining)

        var goToActivity = findViewById<Button>(R.id.button)
        goToActivity.setOnClickListener {
            val intent = Intent(this, Test2Activity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
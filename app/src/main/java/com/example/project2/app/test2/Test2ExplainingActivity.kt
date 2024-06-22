package com.example.project2.app.test2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.project2.R

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
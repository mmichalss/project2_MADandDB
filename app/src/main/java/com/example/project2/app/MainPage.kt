package com.example.project2.app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.project2.R
import com.example.project2.app.test2.Test2ExplainingActivity

class MainPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_page)

        var buttonTest2 = findViewById<Button>(R.id.button2)
        buttonTest2.setOnClickListener {
            val intent = Intent(this, Test2ExplainingActivity::class.java)
            startActivity(intent)
        }


    }
}

package com.example.project2.app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.project2.R
import com.example.project2.Test2Activity

class MainPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_page)

        var buttonTest2 = findViewById<LinearLayout>(R.id.button2)
        //its not actually a button since I added the images and turned it to a linear view
        buttonTest2.setOnClickListener {
            val intent = Intent(this, Test2ExplainingActivity::class.java)
            startActivity(intent)
        }


    }
}

package com.example.project2.app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.project2.R
import com.example.project2.app.test1.Test1HomeActivity
import com.example.project2.app.test2.Test2ExplainingActivity
import com.example.project2.app.test3.Test3ExplainingActivity
import com.google.firebase.auth.FirebaseAuth

class MainPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_page)

        val buttonTest1 = findViewById<LinearLayout>(R.id.button1)
        buttonTest1.setOnClickListener {
            val intent = Intent(this, Test1HomeActivity::class.java)
            startActivity(intent)
        }

        var buttonTest2 = findViewById<LinearLayout>(R.id.button2)
        //its not actually a button since I added the images and turned it to a linear view
        buttonTest2.setOnClickListener {
            val intent = Intent(this, Test2ExplainingActivity::class.java)
            startActivity(intent)
        }

        var buttonTest3 = findViewById<LinearLayout>(R.id.button3)
        buttonTest3.setOnClickListener {
            val intent = Intent(this, Test3ExplainingActivity::class.java)
            startActivity(intent)
        }

        val buttonStats = findViewById<Button>(R.id.button4)
        val user = FirebaseAuth.getInstance().currentUser
        if (user == null) {
            buttonStats.isEnabled = false
            buttonStats.text = "Log in to view stats"
            buttonStats.setTextColor(resources.getColor(R.color.white))
        } else {
            buttonStats.setOnClickListener {
                val intent = Intent(this, StatsActivity::class.java)
                startActivity(intent)
            }
        }


    }
}

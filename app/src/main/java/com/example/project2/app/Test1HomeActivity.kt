package com.example.project2.app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.project2.R
import android.text.Html

class Test1HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_test1_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val instructionTV = findViewById<TextView>(R.id.instructionTV)
        instructionTV?.text = Html.fromHtml(getString(R.string.instructionDescription), Html.FROM_HTML_MODE_COMPACT)
        val startButton = findViewById<Button>(R.id.startBTN)

        startButton.setOnClickListener {
            goToTest1Activity()
        }
    }

    private fun goToTest1Activity(){
        val intent = Intent(this, Test1Activity::class.java)
        startActivity(intent)
    }
}
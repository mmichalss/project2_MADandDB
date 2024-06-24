package com.example.project2.app.test1

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

/**
 * This is the home activity for Test1. It sets up the UI and handles the navigation to the Test1Activity.
 */
class Test1HomeActivity : AppCompatActivity() {
    /**
     * Called when the activity is starting. This is where most initialization should go.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     */
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
    /**
     * This function navigates to the Test1Activity when the start button is clicked.
     */
    private fun goToTest1Activity(){
        val intent = Intent(this, Test1Activity::class.java)
        startActivity(intent)
    }
}
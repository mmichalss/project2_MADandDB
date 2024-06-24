package com.example.project2.app.test2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.project2.R

/**
 * This is the explaining activity for Test2. It explains the test to the user and provides a button to start the test.
 */
class Test2ExplainingActivity : AppCompatActivity() {
    /**
     * Called when the activity is starting. This is where most initialization should go.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     */
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
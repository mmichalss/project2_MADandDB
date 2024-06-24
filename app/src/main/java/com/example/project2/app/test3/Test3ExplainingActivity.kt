package com.example.project2.app.test3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.project2.R
import com.example.project2.app.test2.Test2Activity

/**
 * This is the explaining activity for Test3. It explains the test to the user and provides a button to start the test.
 */
class Test3ExplainingActivity : AppCompatActivity() {
    /**
     * Called when the activity is starting. This is where most initialization should go.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_test3_explaining)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var goToActivity = findViewById<Button>(R.id.button)
        goToActivity.setOnClickListener {
            val intent = Intent(this, Test3Activity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
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
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

/**
 * This is the main page activity. It sets up the UI and handles the navigation to the other activities.
 */
class MainPage : AppCompatActivity() {
    /**
     * Called when the activity is starting. This is where most initialization should go.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     */
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
            buttonStats.setTextColor(resources.getColor(R.color.secondary_text_color))
            buttonStats.text = "Log in to view stats"
            buttonStats.setOnClickListener {
                val snackbar =
                    Snackbar.make(it, "You need to be logged in to see stats", Snackbar.LENGTH_LONG)
                snackbar.show()
            }
        } else {
            buttonStats.setOnClickListener {
                val intent = Intent(this, StatsActivity::class.java)
                startActivity(intent)
            }
        }

        val logoutButton = findViewById<Button>(R.id.logoutButton)
        logoutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }


    }
}

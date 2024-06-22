package com.example.project2.app

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.project2.R

class Test1ResultsActivity : AppCompatActivity() {
    var timeSpent: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_test1_results)


        val timeSpent = intent.getLongExtra("timeSpent", 0)


        val slideInAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_in)
        val fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in)


        val textView1 = findViewById<TextView>(R.id.GreatJobTV)
        val textView2 = findViewById<TextView>(R.id.YourScoreTV)
        val textView3 = findViewById<TextView>(R.id.timeSpent)
        val textView4 = findViewById<TextView>(R.id.percentageCorrect)
        val textView5 = findViewById<TextView>(R.id.result)

        val button1 = findViewById<Button>(R.id.tryAgainBTN)
        val button2 = findViewById<Button>(R.id.statsBTN)
        val button3 = findViewById<Button>(R.id.goHomeBTN)

        textView1.startAnimation(fadeInAnimation)
        textView2.startAnimation(slideInAnimation)
        textView3.startAnimation(fadeInAnimation)
        textView4.startAnimation(slideInAnimation)
        textView5.startAnimation(fadeInAnimation)

        fadeInAnimation.duration = 5000

        button1.startAnimation(fadeInAnimation)
        button2.startAnimation(fadeInAnimation)
        button3.startAnimation(fadeInAnimation)

        button1.setOnClickListener {
            tryAgain()
        }
        button2.setOnClickListener {
            goToStatsPage()
        }
        button3.setOnClickListener {
            goToMainPage()

        }

    }

    private fun goToMainPage(){
        val intent = Intent(this, MainPage::class.java)
        startActivity(intent)
    }

    private fun goToStatsPage(){
        val intent = Intent(this, StatsActivity::class.java)
        startActivity(intent)
    }

    private fun tryAgain(){
        val intent = Intent(this, Test1Activity::class.java)
        startActivity(intent)
    }
}
package com.example.project2.app.test1

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.project2.DB_management.DbClient
import com.example.project2.DB_management.common_types.ResultValue
import com.example.project2.DB_management.common_types.TestType
import com.example.project2.DB_management.dto.result.CreateResultDto
import com.example.project2.R
import com.example.project2.app.MainPage
import com.example.project2.app.StatsActivity
import com.google.firebase.auth.FirebaseAuth
/**
 * This is the results activity for Test1. It calculates the result based on the time spent and result ratio,
 * and displays the result to the user. It also provides options to try again, view stats, or go back to the main page.
 */
class Test1ResultsActivity : AppCompatActivity() {
    var timeSpent: Long = 0
    var timeInSeconds: Long = 0
    var resultRatio: Float = 0f
    var result: ResultValue = ResultValue.NONE
    /**
     * Called when the activity is starting. This is where most initialization should go.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     */
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_test1_results)


        timeSpent = intent.getLongExtra("timeSpent", 0)
        resultRatio = intent.getFloatExtra("result", 0f)

        if (resultRatio >= 0.8 || timeSpent > 160000) {
            result = ResultValue.NONE
        } else if (resultRatio >= 0.6 || timeSpent > 120000) {
            result = ResultValue.SMALL
        } else if (resultRatio >= 0.4 || timeSpent > 100000) {
            result = ResultValue.MEDIUM
        } else {
            result = ResultValue.HIGH
        }

        val slideInAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_in)
        val fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in)


        val textView1 = findViewById<TextView>(R.id.GreatJobTV)
        val textView2 = findViewById<TextView>(R.id.YourScoreTV)
        val textView3 = findViewById<TextView>(R.id.timeSpent)
        timeInSeconds = timeSpent/1000
        textView3.text = "${timeInSeconds}s"
        val textView4 = findViewById<TextView>(R.id.percentageCorrect)
        textView4.text = String.format("%.2f%%", resultRatio * 100)
        val textView5 = findViewById<TextView>(R.id.result)
        textView5.text = result.toString()
        createResult()


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

        val user = FirebaseAuth.getInstance().currentUser
        val userId = user?.email
        if (userId != null) {
            button2.setOnClickListener {
                goToStatsPage()
            }
        } else {
            button2.isEnabled = false
        }
        button3.setOnClickListener {
            goToMainPage()

        }

    }
    /**
     * This function navigates to the MainPage when the "Go Home" button is clicked.
     */
    private fun goToMainPage(){
        val intent = Intent(this, MainPage::class.java)
        startActivity(intent)
    }
    /**
     * This function navigates to the StatsActivity when the "Stats" button is clicked.
     */
    private fun goToStatsPage(){
        val intent = Intent(this, StatsActivity::class.java)
        startActivity(intent)
    }

    /**
     * This function navigates back to the Test1Activity when the "Try Again" button is clicked.
     */
    private fun tryAgain(){
        val intent = Intent(this, Test1Activity::class.java)
        startActivity(intent)
    }
    /**
     * This function creates a new result entry in the database with the calculated result.
     */
    private fun createResult(){
        val user = FirebaseAuth.getInstance().currentUser
        val userId = user?.email
        if (userId != null) {
            val result = CreateResultDto(TestType.TEST1, userId, result, timeInSeconds.toInt())
            val dbClient = DbClient()
            dbClient.addResult(result)
        }
    }
}
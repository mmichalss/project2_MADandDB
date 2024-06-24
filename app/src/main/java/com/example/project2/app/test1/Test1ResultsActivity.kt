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

class Test1ResultsActivity : AppCompatActivity() {
    var timeSpent: Long = 0
    var resultRatio: Float = 0f
    var result: ResultValue = ResultValue.NONE
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_test1_results)


        timeSpent = intent.getLongExtra("timeSpent", 0)
        resultRatio = intent.getFloatExtra("result", 0f)

        if (resultRatio >= 0.8) {
            result = ResultValue.NONE
        } else if (resultRatio >= 0.6) {
            result = ResultValue.SMALL
        } else if (resultRatio >= 0.4) {
            result = ResultValue.MEDIUM
        } else {
            result = ResultValue.HIGH
        }

        createResult()
        val slideInAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_in)
        val fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in)


        val textView1 = findViewById<TextView>(R.id.GreatJobTV)
        val textView2 = findViewById<TextView>(R.id.YourScoreTV)
        val textView3 = findViewById<TextView>(R.id.timeSpent)
        textView3.text = "${timeSpent}s"
        val textView4 = findViewById<TextView>(R.id.percentageCorrect)
        textView4.text = String.format("%.2f%%", resultRatio * 100)
        val textView5 = findViewById<TextView>(R.id.result)
        textView5.text = result.toString()

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

    private fun createResult(){
        val user = FirebaseAuth.getInstance().currentUser
        val userId = user?.email
        if (userId != null) {
            val result = CreateResultDto(TestType.TEST1, userId, result, timeSpent.toInt())
            val dbClient = DbClient()
            dbClient.addResult(result)
        }
    }
}
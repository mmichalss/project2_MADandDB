package com.example.project2.app.test3

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.project2.R
import com.example.project2.app.MainPage
import kotlin.random.Random

class Test3Activity : AppCompatActivity() {
    private lateinit var startButton: Button
    private lateinit var numGrid: GridLayout
    private lateinit var stopwatchText: TextView
    private lateinit var generatedNumberText: TextView
    private var handler = Handler()
    private var startTime = 0L
    private var elapsedTime = 0L
    private var moveCount = 0
    private var numbersToFind = 0
    private var foundNumbers = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_test3)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        startButton = findViewById(R.id.start_button1)
        numGrid = findViewById(R.id.card_grid)
        stopwatchText = findViewById(R.id.textTime)
        generatedNumberText = findViewById(R.id.generatedNumber)

        var returnToMainButton = findViewById<Button>(R.id.mainmenureturn)
        returnToMainButton.setOnClickListener {
            val intent = Intent(this, MainPage::class.java)
            startActivity(intent)}

        startButton.setOnClickListener {
            startGame()
            startTime = System.currentTimeMillis()
            handler.post(updateStopwatch)
            startButton.isEnabled = false
        }

    }
    var desiredNum = randomNumberGenerator()
    private fun startGame() {
        startButton.isEnabled = false
        generatedNumberText.text=desiredNum.toString()
        fillGridWithNumbers()
        foundNumbers = 0
        numbersToFind = howMany(desiredNum)

    }
    private fun howMany(number: Int): Int {
        var count = 0
        for (i in 0 until numGrid.childCount) {
            val child = numGrid.getChildAt(i)
            if (child is TextView) {
                if (child.text.toString() == number.toString()) {
                    count++
                }
            }
        }
        return count
    }

    private fun fillGridWithNumbers(){
        numGrid.removeAllViews()
        for (i in 0 until 25){
            val textView = TextView(this).apply {
                layoutParams = GridLayout.LayoutParams().apply {
                    width = 0
                    height = 0
                    columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f) //(bibliography [3] )
                    rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                    setMargins(4, 4, 4, 4)
                }
                text = randomNumberGenerator().toString()
                // its a little confusing but we set it to text but then need it as an Int when handlingNumberClick
                gravity = android.view.Gravity.CENTER
                setOnClickListener { view ->
                    handleNumberClick(text.toString().toInt(), this)}

            }
            numGrid.addView(textView)
        }
    }
    private fun handleNumberClick(clickedNumber: Int, textView: TextView) {
        if (clickedNumber == desiredNum) {
            textView.setBackgroundColor(getResources().getColor(R.color.green))
            foundNumbers++
            if (foundNumbers == numbersToFind) {
                showGameOverDialog("Congrats!! You found all the missing numbers ")
            }
        } else {
            textView.setBackgroundColor(getResources().getColor(R.color.red))
            moveCount++
        }
    }
    private fun showGameOverDialog(message: String) {
        val seconds = (elapsedTime / 1000).toInt() % 60
        val minutes = (elapsedTime / 1000 / 60).toInt() % 60
        val hours = (elapsedTime / 1000 / 60 / 60).toInt()

        val timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds)
        AlertDialog.Builder(this)
            .setTitle("Game Over")


            .setMessage("$message \n Wrong moves: $moveCount \n Time: $timeString ")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
                finish()
            }
            .show()
    }


    private val updateStopwatch = object : Runnable {
        override fun run() {
            val currentTime = System.currentTimeMillis()
            elapsedTime = currentTime - startTime
            val seconds = (elapsedTime / 1000).toInt() % 60
            val minutes = (elapsedTime / 1000 / 60).toInt() % 60
            val hours = (elapsedTime / 1000 / 60 / 60).toInt()

            val timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds)

            stopwatchText.text = timeString
            handler.postDelayed(this, 1000)
        }
    }
    private fun randomNumberGenerator(): Int {
        val randomNum = Random.nextInt(0, 10)
        return randomNum
    }



}
/*
The game was inspired by the sudoku series by Patrick Feltes
By inspied I mean the grid layout etc
https://www.youtube.com/watch?v=ltEwtbva_yA&list=PLJSII25WrAz72NhnBitybKMMX0_f1UEym&index=6

[3] https://stackoverflow.com/questions/59121268/programmatically-generated-gridlayout-has-column-being-pushed-to-the-right


 */
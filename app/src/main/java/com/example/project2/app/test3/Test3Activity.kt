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
import com.example.project2.DB_management.DbClient
import com.example.project2.DB_management.common_types.ResultValue
import com.example.project2.DB_management.common_types.TestType
import com.example.project2.DB_management.dto.result.CreateResultDto
import com.example.project2.R
import com.example.project2.app.MainPage
import com.google.firebase.auth.FirebaseAuth
import kotlin.random.Random
/**
 * This is the activity for Test3. It generates a grid of numbers and the user has to find the numbers that are multiples of a given number.
 */
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
    private var result = ResultValue.NONE

    /**
     * Called when the activity is starting. This is where most initialization should go.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     */
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

    /**
     * This function starts the game by generating a number and filling the grid with numbers.
     */
    private fun startGame() {
        startButton.isEnabled = false
        generatedNumberText.text=desiredNum.toString()
        fillGridWithNumbers()
        foundNumbers = 0
        numbersToFind = howMany(desiredNum)

    }
    /**
     * This function counts how many times a number appears in the grid.
     * @param number The number to count.
     * @return The number of times the number appears in the grid.
     */
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
    /**
     * This function fills the grid with numbers and sets the click listener for each number.
     */
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

                var divisor = randomNumberGenerator() +1


                if(i % divisor == 0){
                    text = desiredNum.toString()
                }else{
                text = randomNumberGenerator().toString()}

                // its a little confusing but we set it to text but then need it as an Int when handlingNumberClick
                gravity = android.view.Gravity.CENTER
                setOnClickListener { view ->
                    handleNumberClick(text.toString().toInt(), this)}

            }
            numGrid.addView(textView)
        }

    }
    /**
     * This function handles the click of a number in the grid.
     * @param clickedNumber The number that was clicked.
     * @param textView The TextView that was clicked.
     */
    private fun handleNumberClick(clickedNumber: Int, textView: TextView) {
        if (clickedNumber == desiredNum) {
            textView.setBackgroundColor(getResources().getColor(R.color.green))
            textView.isClickable = false
            foundNumbers++
            if (foundNumbers == numbersToFind) {
                showGameOverDialog("Congrats!! You found all the missing numbers ")
            }
        } else {
            textView.setBackgroundColor(getResources().getColor(R.color.red))
            moveCount++
        }
    }
    /**
     * This function shows the game over dialog.
     * @param message The message to show in the dialog.
     */
    private fun showGameOverDialog(message: String) {

        createResult()
        val seconds = (elapsedTime / 1000).toInt() % 60
        val minutes = (elapsedTime / 1000 / 60).toInt() % 60
        val hours = (elapsedTime / 1000 / 60 / 60).toInt()

        val timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds)
        AlertDialog.Builder(this)
            .setTitle("Game Over")


            .setMessage("$message \n Wrong moves: $moveCount \n Time: $timeString \n Result: $result")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
                finish()
            }
            .show()
    }

    /**
     * This function updates the stopwatch.
     */
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
    /**
     * This function generates a random number.
     * @return The random number.
     */
    private fun randomNumberGenerator(): Int {
        val randomNum = Random.nextInt(0, 10)
        return randomNum
    }

    /**
     * This function creates a result object and sends it to the database.
     */
    private fun createResult(){
        result = if (moveCount > 3) ResultValue.HIGH
        else if (moveCount > 2) ResultValue.MEDIUM
        else if (moveCount > 1) ResultValue.SMALL
        else ResultValue.NONE

        val user = FirebaseAuth.getInstance().currentUser
        val userId = user?.email
        if (userId != null) {
            val createResult = CreateResultDto(TestType.TEST3, userId, result, (elapsedTime/1000).toInt())
            val dbClient = DbClient()
            dbClient.addResult(createResult)
        }
    }


}
/*
The game was inspired by the sudoku series by Patrick Feltes
By inspied I mean the grid layout etc
https://www.youtube.com/watch?v=ltEwtbva_yA&list=PLJSII25WrAz72NhnBitybKMMX0_f1UEym&index=6

[3] https://stackoverflow.com/questions/59121268/programmatically-generated-gridlayout-has-column-being-pushed-to-the-right


 */
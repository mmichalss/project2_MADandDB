package com.example.project2.app.test2

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.project2.DB_management.DbClient
import com.example.project2.DB_management.common_types.ResultValue
import com.example.project2.DB_management.common_types.TestType
import com.example.project2.DB_management.dto.result.CreateResultDto
import com.example.project2.R
import com.example.project2.app.MainPage
import com.google.firebase.auth.FirebaseAuth
/**
 * This is the activity for Test2. It sets up a memory card game with different levels.
 * The game tracks the time elapsed and the number of moves made by the user.
 */
class Test2Activity : AppCompatActivity() {
    private lateinit var startButton: Button
    private lateinit var cardGrid: GridLayout
    private lateinit var stopwatchText: TextView
    private lateinit var moveCounterText: TextView

    private var cards: MutableList<Int> = mutableListOf()
    private var revealedCards: MutableList<ImageButton> = mutableListOf()
    private var cardImages: MutableList<Int> = mutableListOf(
        R.drawable.card1apple, R.drawable.card2watermelon, R.drawable.card3orange, R.drawable.card4kiwi, R.drawable.card5pineapple,
        R.drawable.card6pear, R.drawable.card7pomegranate, R.drawable.card8strawberry, R.drawable.card9fig, R.drawable.card10cherry
    )
    private var firstCard: ImageButton? = null
    private var secondCard: ImageButton? = null
    private var handler = Handler()

    // Initialising
    private var level = 1
    private var startTime = 0L
    private var elapsedTime = 0L
    private var moveCount = 0
    private var result = ResultValue.NONE

    /**
     * Called when the activity is starting. This is where most initialization should go.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test2)

        var returnToMainButton = findViewById<Button>(R.id.mainmenureturn)
        returnToMainButton.setOnClickListener {
            val intent = Intent(this, MainPage::class.java)
            startActivity(intent)}

        startButton = findViewById(R.id.start_button)
        cardGrid = findViewById(R.id.card_grid)
        stopwatchText = findViewById(R.id.textTime)
        moveCounterText = findViewById(R.id.textMoves)

        startButton.setOnClickListener {
            startGame()
            startTime = System.currentTimeMillis()
            //your system time to calculate time difference
            handler.post(updateStopwatch)
            startButton.isEnabled = false
        }
    }
    /**
     * This function starts the game by setting up the cards and showing them for three seconds.
     */
    private fun startGame() {
        startButton.isEnabled = false
        updateMoveCounter()
        setupCards(level)
        showCardsForThreeSeconds()
    }
    /**
     * This function sets up the cards for the game based on the level.
     * @param level The level of the game.
     */
    private fun setupCards(level: Int) {
        cardGrid.removeAllViews()
        // remove all child views from the ViewGroup (stackoverflow,2013)
        cards.clear()
        revealedCards.clear()

        val pairs = level + 1
        for (i in 0 until pairs) {
            cards.add(cardImages[i])
            cards.add(cardImages[i])
        }
        cards.shuffle()

        val screenWidth = resources.displayMetrics.widthPixels
        val screenHeight = resources.displayMetrics.heightPixels


        // different number of columns based on levels so all cards are visible
        var numColumns = 0
        if (cards.size <= 8) {
            numColumns = 2
        } else if (cards.size <= 14) {
            numColumns = 3
        } else {
            numColumns = 4
        }
        val numRows = (cards.size + numColumns - 1) / numColumns

        cardGrid.columnCount = numColumns
        cardGrid.rowCount = numRows

        var cardWidth = 0
        var cardHeight = 0

        if (cards.size <= 6) {
            cardWidth = screenWidth / numColumns - 100
            cardHeight = screenHeight / numRows - 60
        } else if(cards.size <=8){
            cardWidth = screenWidth / numColumns - 100
            cardHeight = screenHeight / numRows - 140
        } else {
            cardWidth = screenWidth / numColumns - 60
            cardHeight = screenHeight / numRows - 10
        }
        val cardSize = minOf(cardWidth, cardHeight)

        //

        for (i in 0 until cards.size) {
            val cardButton = ImageButton(this).apply {
                layoutParams = GridLayout.LayoutParams().apply {
                    width = cardSize
                    height = cardSize
                    //setGravity(Gravity.CENTER_HORIZONTAL)
                    setMargins(0, 2, 0, 2) // distance between each element
                }
                scaleType = ImageView.ScaleType.CENTER_CROP
                setImageResource(R.drawable.back_of_card)
                setOnClickListener {
                    revealCard(this, cards[i])
                }
            }
            cardGrid.addView(cardButton)
            revealedCards.add(cardButton)
        }
    }

    /**
     * This function shows the cards for three seconds before flipping them back.
     */
    private fun showCardsForThreeSeconds() {
        for (i in 0 until cards.size) {
            revealedCards[i].setImageResource(cards[i])
        }
        handler.postDelayed({
            for (i in 0 until cards.size) {
                revealedCards[i].setImageResource(R.drawable.back_of_card)
            }
        }, 3000)
    }
    /**
     * This function reveals the card when clicked.
     * @param card The card to be revealed.
     * @param imageResId The image resource id of the card.
     */
    private fun revealCard(card: ImageButton, imageResId: Int) {
        if (firstCard == null) {
            firstCard = card
            card.setImageResource(imageResId)
        } else if (secondCard == null && card != firstCard) {
            secondCard = card
            card.setImageResource(imageResId)
            moveCount++
            updateMoveCounter()
            checkForMatch()
        }
    }
    /**
     * This function updates the move counter.
     */
    private fun updateMoveCounter() {
        moveCounterText.text = "Moves: $moveCount"
    }
    /**
     * This function checks if the two revealed cards match.
     */
    private fun checkForMatch() {
        if (firstCard?.drawable?.constantState == secondCard?.drawable?.constantState) {
            firstCard = null
            secondCard = null
            if (revealedCards.all { it.drawable.constantState != getDrawable(R.drawable.back_of_card)?.constantState }) {
                if (level < 9) {
                    level++
                    startGame()
                } else {
                    showGameOverDialog("Congrats!! You completed all the levels ")
                }
            }
        } else {
            handler.postDelayed({
                firstCard?.setImageResource(R.drawable.back_of_card)
                secondCard?.setImageResource(R.drawable.back_of_card)
                firstCard = null
                secondCard = null
            }, 1000)
        }
    }
    /**
     * This function shows the game over dialog.
     * @param message The message to be displayed in the dialog.
     */
    private fun showGameOverDialog(message: String) {

        createResult()
        val seconds = (elapsedTime / 1000).toInt() % 60
        val minutes = (elapsedTime / 1000 / 60).toInt() % 60
        val hours = (elapsedTime / 1000 / 60 / 60).toInt()

        val timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds)
        AlertDialog.Builder(this)
            .setTitle("Game Over")


            .setMessage("$message \n Moves: $moveCount \n Time: $timeString \n Result: $result")


            //we could send to firebase even from here, with the moveCount and timeString
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
     * This function creates the result of the game.
     */
    private fun createResult(){
        result = if (moveCount > 160) ResultValue.HIGH
        else if (moveCount > 120) ResultValue.MEDIUM
        else if (moveCount > 110) ResultValue.SMALL
        else ResultValue.NONE

        val user = FirebaseAuth.getInstance().currentUser
        val userId = user?.email
        if (userId != null) {
            val createResult = CreateResultDto(TestType.TEST2, userId, result, (elapsedTime/1000).toInt())
            val dbClient = DbClient()
            dbClient.addResult(createResult)
        }
    }
}
/*
Bibliography :
https://developer.android.com/reference/android/os/Handler
https://stackoverflow.com/questions/11952598/whats-difference-between-removeallviews-and-removeallviewsinlayout
 */

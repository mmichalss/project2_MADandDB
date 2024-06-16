package com.example.project2

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.project2.R

class Test2Activity : AppCompatActivity() {

    private lateinit var startButton: Button
    private lateinit var cardGrid: GridLayout
    private var level = 1
    private var cards: MutableList<Int> = mutableListOf()
    private var revealedCards: MutableList<ImageButton> = mutableListOf()
    private var cardImages: MutableList<Int> = mutableListOf(
        R.drawable.card1apple, R.drawable.card2watermelon, R.drawable.card3orange, R.drawable.card4kiwi, R.drawable.card5pineapple,
        R.drawable.card6pear, R.drawable.card7pomegranate, R.drawable.card8strawberry, R.drawable.card9fig, R.drawable.card10cherry
    )
    private var firstCard: ImageButton? = null
    private var secondCard: ImageButton? = null
    private var handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test2)

        startButton = findViewById(R.id.start_button)
        cardGrid = findViewById(R.id.card_grid)

        startButton.setOnClickListener {
            startGame()
        }
    }

    private fun startGame() {
        startButton.visibility = View.GONE
        setupCards(level)
        showCardsForThreeSeconds()
    }

    private fun setupCards(level: Int) {
        cardGrid.removeAllViews()
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

        var numColumns = 0

        if(cards.size <= 8) {
            numColumns = 2
        }else if(cards.size <=16) {
            numColumns = 3
        }else{
            numColumns = 4
        }

        val numRows = (cards.size + numColumns - 1) / numColumns

        cardGrid.columnCount = numColumns
        cardGrid.rowCount = numRows

        var cardWidth = 0
        var cardHeight = 0

        if(cards.size < 9) {
            cardWidth = screenWidth / numColumns - 100
            cardHeight = screenHeight / numRows - 50
        } else {
            cardWidth = screenWidth / numColumns - 50
            cardHeight = screenHeight / numRows - 10
        }
        val cardSize = minOf(cardWidth, cardHeight)

        for (i in 0 until cards.size) {
            val cardButton = ImageButton(this).apply {
                layoutParams = GridLayout.LayoutParams().apply {
                    width = cardSize
                    height = cardSize
                    setMargins(0, 2, 0, 2)
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

    private fun revealCard(card: ImageButton, imageResId: Int) {
        if (firstCard == null) {
            firstCard = card
            card.setImageResource(imageResId)
        } else if (secondCard == null && card != firstCard) {
            secondCard = card
            card.setImageResource(imageResId)
            checkForMatch()
        }
    }

    private fun checkForMatch() {
        if (firstCard?.drawable?.constantState == secondCard?.drawable?.constantState) {
            firstCard = null
            secondCard = null
            if (revealedCards.all { it.drawable.constantState != getDrawable(R.drawable.back_of_card)?.constantState }) {
                if (level < 9) {
                    level++
                    startGame()
                } else {
                    showGameOverDialog("Congrats!! You completed all the  levels, go to menu to check your score? ")
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

    private fun showGameOverDialog(message: String) {
        AlertDialog.Builder(this)
            .setTitle("Game Over")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
                finish()
            }
            .show()
    }
}

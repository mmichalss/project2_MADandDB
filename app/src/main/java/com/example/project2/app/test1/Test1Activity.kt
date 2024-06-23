package com.example.project2.app.test1

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.view.MotionEvent
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.project2.R
import kotlin.random.Random

class Test1Activity : AppCompatActivity() {
    private var layoutOpenTime: Long = 0
    private var  totalTimeElapsed: Long = 0
    private lateinit var speechRecognizer: SpeechRecognizer
    private lateinit var micButton: ImageView
    private lateinit var pictureIMGV: ImageView
    private lateinit var voiceInput: String
    private lateinit var voiceTV: TextView
    private lateinit var resultTV: TextView
    private val pictures = arrayOf(R.drawable.pen, R.drawable.table, R.drawable.pencil, R.drawable.chair, R.drawable.clock, R.drawable.swing, R.drawable.cup)
    private val answers = arrayOf("pen", "table", "pencil", "chair", "clock", "swing", "cup")
    private var answerInitial = ""
    private var picturesIndexes = pictures.indices.toList().toTypedArray()
    private val result: MutableList<Int> = mutableListOf()
    private var finalResult: Float = 0f

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test1)

        onStart()
        onStop()

        if (ContextCompat.checkSelfPermission
                (this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
        ) {
            checkPermissions()
        } else {

            pictureIMGV = findViewById(R.id.pictureIMGV)
            pictureIMGV.setBackgroundResource(R.drawable.rounded_corners)

            micButton = findViewById(R.id.micBTN)
            changePicture()

            voiceTV = findViewById(R.id.voiceTV)
            resultTV = findViewById(R.id.resultTV)
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)

            speechRecognition()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun speechRecognition() {
        val speechRecognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US")
        }

        speechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {}

            override fun onBeginningOfSpeech() {

            }

            override fun onRmsChanged(rmsdB: Float) {}

            override fun onBufferReceived(buffer: ByteArray?) {}

            override fun onEndOfSpeech() {}

            override fun onError(error: Int) {}

            override fun onResults(results: Bundle?) {
                micButton.setImageResource(R.drawable.baseline_mic_off)
                results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)?.let { data ->
                    voiceTV.text = data.toString()
                    checkTheAnswer(data)
                    resultTV.text = result.toString()
                    changePicture()
                }
            }

            override fun onPartialResults(partialResults: Bundle?) {}

            override fun onEvent(eventType: Int, params: Bundle?) {}

        })

            micButton.setOnTouchListener { _, motionEvent ->
                when (motionEvent.action) {
                    MotionEvent.ACTION_UP -> speechRecognizer.stopListening()
                    MotionEvent.ACTION_DOWN -> {
                        micButton.setImageResource(R.drawable.baseline_mic)
                        speechRecognizer.startListening(speechRecognizerIntent)
                    }
                }
                false
            }
    }
    override fun onStart() {
        super.onStart()
        layoutOpenTime = System.currentTimeMillis()
    }
    override fun onStop() {
    super.onStop()
    val timeElapsed = System.currentTimeMillis() - layoutOpenTime
    totalTimeElapsed += timeElapsed
        Log.e("Time Elapsed", "$totalTimeElapsed")
}
    override fun onPause() {
        super.onPause()
        val timeElapsed = System.currentTimeMillis() - layoutOpenTime
        totalTimeElapsed += timeElapsed
    }

    override fun onDestroy(){
        super.onDestroy()
        speechRecognizer.destroy()
    }

    private fun checkPermissions() {
        ActivityCompat.requestPermissions(
            this, arrayOf(Manifest.permission.RECORD_AUDIO),
            RecordAudioRequestCode

        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == RecordAudioRequestCode && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
        }
    }

    companion object{
        const val RecordAudioRequestCode = 1
    }

    private fun changePicture() {
    if (picturesIndexes.isNotEmpty()) {
        val randomIndex = Random.nextInt(picturesIndexes.size)
        answerInitial = answers[picturesIndexes[randomIndex]]
        pictureIMGV.setImageResource(pictures[picturesIndexes[randomIndex]])
        picturesIndexes =
            picturesIndexes.filterIndexed { index, _ -> index != randomIndex }.toTypedArray()

        // Disable the micButton immediately when the picture changes
        micButton.isEnabled = false

        // Enable the micButton after 1.5 seconds
        Handler(Looper.getMainLooper()).postDelayed({
            micButton.isEnabled = true
        }, 1500)
    } else {
        finalResult = result.sum().toFloat() / answers.size
        goToTest1ResultsActivity()
    }
}
            private fun removeElement(arr: Array<Int>, element: Int): Array<Int> {
        return arr.filter { it != element }.toTypedArray()
    }

    private fun checkTheAnswer(results: ArrayList<String>): MutableList<Int> {
        results.forEach {
            if (it.contains(answerInitial)) {
                result.add(1)
                return result
            }
        }
        result.add(0)
        return result
    }
    private fun goToTest1ResultsActivity(){
        val intent = Intent(this, Test1ResultsActivity::class.java)
        intent.putExtra("result", finalResult)
        intent.putExtra("timeSpent", totalTimeElapsed)
        startActivity(intent)
    }
}
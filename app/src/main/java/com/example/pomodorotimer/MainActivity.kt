package com.example.pomodorotimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.util.*
import kotlin.concurrent.fixedRateTimer

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        val start: Button = findViewById(R.id.startButton)
        val reset: Button = findViewById(R.id.resetButton)
        val timerView: TextView = findViewById(R.id.textView)

        var seconds = 3
        var timer: Timer? = null

        fun resetTimer() {
            timer?.cancel()
            seconds = 3
            timerView.setText(R.string.default_time)
        }

        reset.setOnClickListener { resetTimer() }

        start.setOnClickListener {
            resetTimer()
            timer = fixedRateTimer(initialDelay = 1000, period = 1000) {
                timerView.post { timerView.setText("00:0${--seconds}") }
                if (seconds == 1) cancel()
            }
        }
    }
}
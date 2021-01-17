package com.example.pomodorotimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // init buttons
        val timer = findViewById<TimerView>(R.id.timerView)
        findViewById<Button>(R.id.startButton).setOnClickListener { timer.start() }
        findViewById<Button>(R.id.resetButton).setOnClickListener { timer.reset() }
    }
}
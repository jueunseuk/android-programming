package com.example.androidprogramming

import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var tvTime: TextView
    private lateinit var btnStart: Button
    private lateinit var btnStop: Button

    private var startTime = 0L
    private var elapsedTime = 0L
    private var isRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvTime = findViewById(R.id.tvTime)
        btnStart = findViewById(R.id.btnStart)
        btnStop = findViewById(R.id.btnStop)

        btnStart.setOnClickListener {
            if (!isRunning) {
                startTime = SystemClock.elapsedRealtime() - elapsedTime
                isRunning = true
                startTimer()
            }
        }

        btnStop.setOnClickListener {
            isRunning = false
        }
    }

    private fun startTimer() {
        lifecycleScope.launch {
            while (isRunning) {
                elapsedTime = SystemClock.elapsedRealtime() - startTime
                tvTime.text = formatTime(elapsedTime)
                delay(100)
            }
        }
    }

    private fun formatTime(ms: Long): String {
        val seconds = (ms / 1000) % 60
        val minutes = (ms / (1000 * 60)) % 60
        val hours = (ms / (1000 * 60 * 60))

        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }
}
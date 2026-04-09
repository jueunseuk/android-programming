package com.example.androidprogramming

import android.os.Bundle
import android.os.SystemClock
import androidx.appcompat.app.AppCompatActivity
import com.example.androidprogramming.databinding.StopwatchActivityBinding

class StopwatchActivity : AppCompatActivity() {
    var pauseTime = 0L; // stop 버튼을 누른 시각

    override fun onCreate(savedInstanceState: Bundle?) {
        val binding = StopwatchActivityBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.startButton.setOnClickListener {
            binding.timer.base = pauseTime + SystemClock.elapsedRealtime()
            binding.timer.start()
            binding.startButton.isEnabled = false
            binding.stopButton.isEnabled = true
            binding.resetButton.isEnabled = false
        }

        binding.stopButton.setOnClickListener {
            pauseTime = binding.timer.base - SystemClock.elapsedRealtime()
            binding.timer.stop()
            binding.startButton.isEnabled = true
            binding.stopButton.isEnabled = false
            binding.resetButton.isEnabled = true
        }

        binding.resetButton.setOnClickListener {
            pauseTime = 0L
            binding.timer.stop()
            binding.timer.base = SystemClock.elapsedRealtime()
            binding.startButton.isEnabled = true
            binding.stopButton.isEnabled = false
            binding.resetButton.isEnabled = false
        }
    }
}
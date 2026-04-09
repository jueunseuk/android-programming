package com.example.androidprogramming

import android.os.Bundle
import android.os.SystemClock
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.androidprogramming.databinding.ActivityStopwatchBinding

class StopwatchActivity : AppCompatActivity() {

    var initTime = 0L; // 뒤로가기 버튼을 누른 시각
    var pauseTime = 0L; // stop 버튼을 누른 시각
    private var isMonitoring: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        val binding = ActivityStopwatchBinding.inflate(layoutInflater)

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
            binding.startButton.isEnabled = true
            binding.stopButton.isEnabled = false
            binding.resetButton.isEnabled = false
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (System.currentTimeMillis() - initTime <= 2000) {
                    finish()
                } else {
                    initTime = System.currentTimeMillis()
                    Toast.makeText(
                        this@StopwatchActivity,
                        "뒤로 버튼을 한 번 더 누르면 종료됩니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }
}
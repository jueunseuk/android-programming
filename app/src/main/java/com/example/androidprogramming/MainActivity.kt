package com.example.androidprogramming

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidprogramming.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {
    val USER_ID: String = "2471506"
    val USER_PASSWORD: String = "1234"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val prefs = getSharedPreferences("service", MODE_PRIVATE)
        val edit = prefs.edit()

        val savedUserId = prefs.getString("user_id", "")
        binding.inputId.setText(savedUserId)
        if (!savedUserId.isNullOrEmpty()) {
            binding.rememberCheckBox.isChecked = true
        }

        binding.loginButton.setOnClickListener {
            val inputId = binding.inputId.text.toString().trim()
            val inputPassword = binding.inputPassword.text.toString().trim()

            if (inputId.isEmpty() || inputPassword.isEmpty()) {
                Toast.makeText(this, "아이디와 비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show()
            } else if (inputId == USER_ID && inputPassword == USER_PASSWORD) {
                Toast.makeText(this, "로그인 성공", Toast.LENGTH_LONG).show()

                if(binding.rememberCheckBox.isChecked) {
                    edit.putString("user_id", inputId)
                } else {
                    edit.putString("user_id", "")
                    binding.inputId.setText("")
                }
                edit.apply()
                binding.inputPassword.setText("")

                val intent = Intent(this, StopwatchActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "아이디 또는 비밀번호가 올바르지 않습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                var initTime = 0L
                if (System.currentTimeMillis() - initTime <= 2000) {
                    finish()
                } else {
                    initTime = System.currentTimeMillis()
                    Toast.makeText(this@MainActivity, "뒤로 버튼을 한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

}
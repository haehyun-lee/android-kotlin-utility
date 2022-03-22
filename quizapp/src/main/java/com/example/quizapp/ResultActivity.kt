package com.example.quizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.quizapp.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        binding.tvName.text = intent.getStringExtra("name").toString()

        val score= intent.getIntExtra("score", 0)
        val total = intent.getIntExtra("total", 0)

        binding.tvScore.text = "Your Score is $score out of $total."
    }
}
package com.example.app2

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val storyText = findViewById<TextView>(R.id.storyResultText)
        val backButton = findViewById<Button>(R.id.backButton)

        val result = intent.getStringExtra("story_seed") ?: "No story generated."
        storyText.text = result

        backButton.setOnClickListener {
            finish()
        }
    }
}
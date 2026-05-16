package com.example.app1

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val moodGroup = findViewById<RadioGroup>(R.id.moodGroup)
        val calculateButton = findViewById<Button>(R.id.calculateButton)
        val btnInstructions = findViewById<Button>(R.id.btn_instructions)

        btnInstructions.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("App Instructions")

            val message = """
                ✅ DO: Select your current mood accurately.
                ✅ DO: Take the suggested tips seriously for best results.
                
                ❌ DON'T: Ignore the recharge mode if you are tired.
                ❌ DON'T: Rush through the deep breathing exercises.
            """.trimIndent()

            builder.setMessage(message)
            builder.setPositiveButton("Got it!") { dialog, _ ->
                dialog.dismiss()
            }
            builder.show()
        }


        calculateButton.setOnClickListener {
            val resultTitle: String
            val resultDescription: String
            val resultTips: String

            val selectedMood = when (moodGroup.checkedRadioButtonId) {
                R.id.stressed -> "Stressed"
                R.id.tired -> "Tired"
                else -> "Happy"
            }

            when (selectedMood) {
                "Stressed" -> {
                    resultTitle = "Reset Routine"
                    resultDescription = "You seem stressed and need a calming reset."
                    resultTips = "Drink water, take a short walk, listen to calm music, and breathe deeply."
                }
                "Tired" -> {
                    resultTitle = "Recharge Mode"
                    resultDescription = "Your body and mind need recovery."
                    resultTips = "Stretch, rest your eyes, and relax for a few minutes."
                }
                else -> {
                    resultTitle = "Creative Boost"
                    resultDescription = "You are feeling positive and productive."
                    resultTips = "Write ideas, organize goals, and listen to upbeat music."
                }
            }

            val dialogView = layoutInflater.inflate(R.layout.dialog_result, null)
            val title = dialogView.findViewById<TextView>(R.id.resultTitle)
            val description = dialogView.findViewById<TextView>(R.id.resultDescription)
            val tips = dialogView.findViewById<TextView>(R.id.resultTips)
            val closeButton = dialogView.findViewById<Button>(R.id.closeButton)

            title.text = resultTitle
            description.text = resultDescription
            tips.text = resultTips

            val dialog = AlertDialog.Builder(this)
                .setView(dialogView)
                .create()

            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            closeButton.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()
        }
    }
}
package com.example.app2

import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var genreSpinner: Spinner
    private lateinit var moodSpinner: Spinner
    private lateinit var settingSpinner: Spinner
    private lateinit var objectSpinner: Spinner
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Shared Preferences
        prefs = getSharedPreferences("com.example.app2.prefs", MODE_PRIVATE)

        // Bind Spinner Views
        genreSpinner = findViewById(R.id.genreSpinner)
        moodSpinner = findViewById(R.id.moodSpinner)
        settingSpinner = findViewById(R.id.settingSpinner)
        objectSpinner = findViewById(R.id.objectSpinner)

        // Spinner Data Arrays
        val genres = arrayOf("Fantasy", "Sci-Fi", "Mystery", "Romance", "Adventure")
        val moods = arrayOf("Dark", "Dreamy", "Funny", "Hopeful", "Chaotic")
        val settings = arrayOf("Abandoned City", "Moonlit Forest", "Floating Library", "Desert Village", "Hidden Train Station")
        val objects = arrayOf("Golden Key", "Broken Watch", "Glass Map", "Whispering Candle", "Silver Feather")

        // Set Up Spinner Adapters
        genreSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, genres)
        moodSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, moods)
        settingSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, settings)
        objectSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, objects)

        // Load favorite genre preference if saved
        val savedGenre = prefs.getString("favorite_genre", "Fantasy")
        genreSpinner.setSelection(genres.indexOf(savedGenre))

        // Profile Icon Click Listener (Merged Perfectly)
        findViewById<ImageView>(R.id.ivProfileButton).setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        // Generate Story Seed Button Click Listener
        findViewById<Button>(R.id.generateButton).setOnClickListener {
            val genre = genreSpinner.selectedItem.toString()
            val mood = moodSpinner.selectedItem.toString()
            val setting = settingSpinner.selectedItem.toString()
            val obj = objectSpinner.selectedItem.toString()

            val storySeed = "A $mood $genre story begins in a $setting, where a mysterious $obj changes everything."

            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("story_seed", storySeed)
            startActivity(intent)
        }

        // Settings Button Click Listener
        findViewById<Button>(R.id.settingsButton).setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        // Information Button Click Listener
        findViewById<Button>(R.id.infoButton).setOnClickListener {
            showInfoDialog()
        }
    }

    // Helper function to build custom dark thematic Info Alert Dialog
    private fun showInfoDialog() {
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_info, null)
        val dialog = AlertDialog.Builder(this).setView(view).create()

        view.findViewById<Button>(R.id.closeInfoButton).setOnClickListener {
            dialog.dismiss()
        }

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
    }
}
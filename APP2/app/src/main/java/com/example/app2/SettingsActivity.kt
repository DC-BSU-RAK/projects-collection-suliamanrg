package com.example.app2
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        prefs = getSharedPreferences("StoryPrefs", MODE_PRIVATE)

        val favoriteGenreSpinner = findViewById<Spinner>(R.id.favoriteGenreSpinner)
        val saveButton = findViewById<Button>(R.id.saveSettingsButton)

        val genres = arrayOf("Fantasy", "Sci-Fi", "Mystery", "Romance", "Adventure")
        favoriteGenreSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, genres)

        val savedGenre = prefs.getString("favorite_genre", "Fantasy")
        favoriteGenreSpinner.setSelection(genres.indexOf(savedGenre))

        saveButton.setOnClickListener {
            prefs.edit().putString("favorite_genre", favoriteGenreSpinner.selectedItem.toString()).apply()
            Toast.makeText(this, "Preferences saved", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
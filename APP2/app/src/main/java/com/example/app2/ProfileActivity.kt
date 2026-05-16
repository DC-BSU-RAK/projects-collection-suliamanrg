package com.example.app2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val tvName = findViewById<TextView>(R.id.tvProfileName)
        val tvEmail = findViewById<TextView>(R.id.tvProfileEmail)
        val btnBack = findViewById<Button>(R.id.btnBackToMain)
        val btnLogOut = findViewById<Button>(R.id.btnLogOut)

        // Fetch persistent account data safely from Firestore DB
        val currentUserId = auth.currentUser?.uid
        if (currentUserId != null) {
            db.collection("users").document(currentUserId).get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        val nameStr = document.getString("name") ?: "No Name found"
                        val emailStr = document.getString("email") ?: "No Email found"

                        tvName.text = "Name: $nameStr"
                        tvEmail.text = "Email: $emailStr"
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Failed to load data: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }

        // Back Button
        btnBack.setOnClickListener {
            finish()
        }

        // Log Out Button (Clears local system login session tokens securely)
        btnLogOut.setOnClickListener {
            auth.signOut()
            Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()

            // Kick user all the way back to the authorization panel screen
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}
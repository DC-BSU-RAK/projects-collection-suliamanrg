package com.example.app2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val etName = findViewById<EditText>(R.id.etSignUpName)
        val etEmail = findViewById<EditText>(R.id.etSignUpEmail)
        val etPassword = findViewById<EditText>(R.id.etSignUpPassword)
        val btnSignUp = findViewById<Button>(R.id.btnSignUp)
        val tvGoToLogin = findViewById<TextView>(R.id.tvGoToLogin)

        btnSignUp.setOnClickListener {
            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                if (password.length < 6) {
                    Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                // Create user authentication account in Firebase
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val userId = auth.currentUser?.uid ?: ""

                            // Create the profile map to store persistent data in Firestore DB
                            val userProfile = hashMapOf(
                                "userId" to userId,
                                "name" to name,
                                "email" to email,
                                "savedSeedsCount" to 0
                            )

                            // Save user to cloud database
                            db.collection("users").document(userId)
                                .set(userProfile)
                                .addOnSuccessListener {
                                    Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT).show()
                                    // Go straight to your main application generator view
                                    startActivity(Intent(this, MainActivity::class.java))
                                    finish()
                                }
                        } else {
                            Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Please fill in all details", Toast.LENGTH_SHORT).show()
            }
        }

        // Quick link to go back to Login screen if they already have an account
        tvGoToLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
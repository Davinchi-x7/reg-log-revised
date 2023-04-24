package com.example.reg

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Login : AppCompatActivity() {

    private lateinit var db: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Create or open the database
        db = openOrCreateDatabase("users.db", MODE_PRIVATE, null)

        // Get references to the UI elements
        val editTextEmail = findViewById<EditText>(R.id.mail_field)
        val editTextPassword = findViewById<EditText>(R.id.Pass_field)
        val buttonLogin = findViewById<Button>(R.id.btn_log)
        val buttonCreate = findViewById<Button>(R.id.btn_create)

        // Handle the button click
        buttonLogin.setOnClickListener {
            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString().trim()

            // Check for empty fields
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Query the database for the user's credentials
            val cursor: Cursor = db.rawQuery("SELECT * FROM users WHERE email = ? AND password = ?", arrayOf(email, password))
            if (cursor.moveToFirst()) {
                // Credentials match, take the user to the dashboard
                val intent = Intent(this, Dashboard::class.java)
                startActivity(intent)
                finish()
            } else {
                // Credentials don't match, show an error message
                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
            }
            cursor.close()

            buttonCreate.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Close the database
        db.close()
    }
}
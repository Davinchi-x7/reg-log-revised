package com.example.reg



import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var db: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Create or open the database
        db = openOrCreateDatabase("users.db", MODE_PRIVATE, null)

        // Create the users table if it doesn't exist
        db.execSQL("CREATE TABLE IF NOT EXISTS users (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, email TEXT, password TEXT)")

        // Get references to the UI elements
        val editTextName = findViewById<EditText>(R.id.NamePlate)
        val SecName = findViewById<EditText>(R.id.SecondName)
        val editTextEmail = findViewById<EditText>(R.id.Email_ad)
        val editTextPassword = findViewById<EditText>(R.id.Password)
        val buttonCreateAccount = findViewById<Button>(R.id.reg_btn)
        val buttonlogin = findViewById<Button>(R.id.login_b_t)

        // Handle the button click
        buttonCreateAccount.setOnClickListener {
            val name = editTextName.text.toString().trim()
            val secName = SecName.text.toString().trim()
            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString().trim()

            // Check for empty fields
            if (name.isEmpty() ||secName.isEmpty()|| email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Insert a new user
            val values = ContentValues()
            values.put("name", name)
            values.put("Sname", secName)
            values.put("email", email)
            values.put("password", password)
            db.insert("users", null, values)

            // Clear the fields
            editTextName.text.clear()
            SecName.text.clear()
            editTextEmail.text.clear()
            editTextPassword.text.clear()

            // Show a success message
            Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show()

            // Take the user to the login screen
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()

            buttonlogin.setOnClickListener {
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Close the database
        db.close()
    }
}




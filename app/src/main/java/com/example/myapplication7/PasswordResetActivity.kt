package com.example.myapplication7

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class PasswordResetActivity : AppCompatActivity() {

    private lateinit var editTextEmail: EditText
    private lateinit var buttonSendEmail: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_reset)

        init()

        registerListeners()

    }

    private fun init() {
        buttonSendEmail = findViewById(R.id.buttonSendEmail)
        editTextEmail = findViewById(R.id.editTextEmail)
    }

    private fun registerListeners() {
        buttonSendEmail.setOnClickListener {

            val email = editTextEmail.text.toString()

            if (email.isEmpty()) {
                Toast.makeText(this, "Empty email!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener { jemali ->
                    if (jemali.isSuccessful) {
                        Toast.makeText(this, "Check email!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                    }
                }

        }
    }

}
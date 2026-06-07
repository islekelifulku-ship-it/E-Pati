package com.example.e_pati

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class RegisterActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var registerButton: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_register
        )

        emailEditText =
            findViewById(R.id.emailEditText)

        passwordEditText =
            findViewById(R.id.passwordEditText)

        registerButton =
            findViewById(R.id.registerButton)

        registerButton.setOnClickListener {

            val email =
                emailEditText.text.toString()

            val password =
                passwordEditText.text.toString()

            if (
                email.isEmpty() ||
                password.isEmpty()
            ) {

                Toast.makeText(
                    this,
                    "Tüm alanları doldurun",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            val prefs =
                getSharedPreferences(
                    "user",
                    MODE_PRIVATE
                )

            prefs.edit()
                .putString(
                    "email",
                    email
                )
                .putString(
                    "password",
                    password
                )
                .apply()

            Toast.makeText(
                this,
                "Kayıt Başarılı",
                Toast.LENGTH_SHORT
            ).show()

            finish()
        }
    }
}
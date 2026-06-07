package com.example.e_pati

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class LoginActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: MaterialButton
    private lateinit var registerText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs =
            getSharedPreferences(
                "user",
                MODE_PRIVATE
            )

        if (
            prefs.getBoolean(
                "isLoggedIn",
                false
            )
        ) {
            startActivity(
                Intent(
                    this,
                    MainActivity::class.java
                )
            )
            finish()
        }

        setContentView(R.layout.activity_login)

        emailEditText =
            findViewById(R.id.emailEditText)

        passwordEditText =
            findViewById(R.id.passwordEditText)

        loginButton =
            findViewById(R.id.loginButton)

        registerText =
            findViewById(R.id.registerText)

        loginButton.setOnClickListener {

            val email =
                emailEditText.text.toString()

            val password =
                passwordEditText.text.toString()

            val savedEmail =
                prefs.getString(
                    "email",
                    ""
                )

            val savedPassword =
                prefs.getString(
                    "password",
                    ""
                )

            if (
                email == savedEmail &&
                password == savedPassword
            ) {

                prefs.edit()
                    .putBoolean(
                        "isLoggedIn",
                        true
                    )
                    .apply()

                Toast.makeText(
                    this,
                    "Giriş Başarılı",
                    Toast.LENGTH_SHORT
                ).show()

                startActivity(
                    Intent(
                        this,
                        MainActivity::class.java
                    )
                )

                finish()

            } else {

                Toast.makeText(
                    this,
                    "E-posta veya şifre hatalı",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        registerText.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    RegisterActivity::class.java
                )
            )

        }
    }
}
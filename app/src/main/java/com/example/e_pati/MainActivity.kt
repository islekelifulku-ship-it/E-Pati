package com.example.e_pati

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.content.Intent
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            )
            != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.POST_NOTIFICATIONS
                ),
                100
            )
        }
        setContentView(R.layout.activity_main)
        val logoutButton =
            findViewById<Button>(
                R.id.logoutButton
            )

        logoutButton.setOnClickListener {

            val prefs =
                getSharedPreferences(
                    "user",
                    MODE_PRIVATE
                )

            prefs.edit()
                .putBoolean(
                    "isLoggedIn",
                    false
                )
                .apply()

            startActivity(
                Intent(
                    this,
                    LoginActivity::class.java
                )
            )

            finish()
        }

        val bottomNavigation =
            findViewById<BottomNavigationView>(
                R.id.bottom_navigation
            )

        replaceFragment(HomeFragment())

        bottomNavigation.setOnItemSelectedListener {

            when (it.itemId) {

                R.id.homeFragment -> {
                    replaceFragment(HomeFragment())
                }

                R.id.petsFragment -> {
                    replaceFragment(PetsFragment())
                }

                R.id.healthFragment -> {
                    replaceFragment(HealthFragment())
                }

                R.id.calorieFragment -> {
                    replaceFragment(CalorieFragment())
                }
                R.id.statsFragment -> {
                    replaceFragment(StatisticsFragment())
                }
                R.id.calendarFragment -> {
                    replaceFragment(CalendarFragment())
                }

            }

            true

        }

    }

    private fun replaceFragment(fragment: Fragment) {

        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fragment_container,
                fragment
            )
            .commit()

    }
}
@file:Suppress("DEPRECATION")

package com.example.githubuser.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import com.example.githubuser.MainActivity
import com.example.githubuser.view.login.LoginActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check if the user is logged in
        if (isLoggedIn()) {
            // User is logged in, start MainActivity
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            // User is not logged in, start LoginActivity
            startActivity(Intent(this, LoginActivity::class.java))
        }

        // Finish the SplashActivity
        finish()
    }

    private fun isLoggedIn(): Boolean {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        return sharedPreferences.getBoolean("isLoggedIn", false)
    }

}

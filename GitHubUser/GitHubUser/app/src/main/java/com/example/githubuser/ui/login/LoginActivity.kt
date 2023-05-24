package com.example.githubuser.view.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.githubuser.MainActivity
import com.example.githubuser.data.local.logindata.DatabaseHelper
import com.example.githubuser.databinding.ActivityLoginBinding
import com.example.githubuser.view.register.RegisterActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Suppress("DEPRECATION")
class LoginActivity : AppCompatActivity() {
    private lateinit var editTextUsername: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DatabaseHelper.init(applicationContext)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        editTextUsername = binding.emailEditText
        editTextPassword = binding.passwordEditText
        val buttonLogin: Button = binding.loginButton

        binding.registbut.setOnClickListener{
            Intent(this, RegisterActivity::class.java).apply {
                startActivity(this)
            }}

        buttonLogin.setOnClickListener {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
            sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()
            val username = editTextUsername.text.toString()
            val password = editTextPassword.text.toString()

            lifecycleScope.launch {
                val user = withContext(Dispatchers.IO) {
                    DatabaseHelper.getUser(username)
                }

                if (user != null && user.password == password) {
                    // Login successful
                    sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()
                    sharedPreferences.edit().putString("username", username).apply()
                    Toast.makeText(this@LoginActivity, "Login successful", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    intent.putExtra("username", username)
                    startActivity(intent)
                    finish()
                } else {
                    // Login failed
                    Toast.makeText(this@LoginActivity, "Invalid credentials", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
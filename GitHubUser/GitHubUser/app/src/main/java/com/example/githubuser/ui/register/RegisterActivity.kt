package com.example.githubuser.view.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.githubuser.data.local.logindata.DatabaseHelper
import com.example.githubuser.data.local.logindata.Userdata
import com.example.githubuser.databinding.ActivityRegisterBinding
import com.example.githubuser.view.login.LoginActivity
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    private lateinit var editTextUsername: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DatabaseHelper.init(applicationContext)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        editTextUsername = binding.usernamereg
        editTextPassword = binding.passreg

        binding.signupButton.setOnClickListener {
            val username = editTextUsername.text.toString()
            val password = editTextPassword.text.toString()

            if (username.isNotBlank() && password.isNotBlank()) {
                val user = Userdata(username = username, password = password)

                lifecycleScope.launch {
                    DatabaseHelper.insertUser(user)
                    Toast.makeText(this@RegisterActivity, "Registration successful", Toast.LENGTH_SHORT).show()
                    // Proceed to the login activity or perform automatic login
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            } else {
                Toast.makeText(this@RegisterActivity, "Invalid credentials", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

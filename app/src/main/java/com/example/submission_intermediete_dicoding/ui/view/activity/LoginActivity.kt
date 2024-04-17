package com.example.submission_intermediete_dicoding.ui.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.submission_intermediete_dicoding.databinding.ActivityLoginBinding
import com.example.submission_intermediete_dicoding.ui.viewmodel.LoginViewModel
import com.example.submission_intermediete_dicoding.ui.viewmodel.ViewModelFactory
import com.example.submission_intermediete_dicoding.util.LoginPreference
import com.example.submission_intermediete_dicoding.util.datastore

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = LoginPreference.getInstance(application.datastore)
        val loginViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            LoginViewModel::class.java
        )

        loginViewModel.getLoginSession().observe(this) {isLoged: Boolean ->
            if (isLoged) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

        binding.btnLogin.setOnClickListener {
            loginViewModel.saveLoginSession(true)
        }


    }
}
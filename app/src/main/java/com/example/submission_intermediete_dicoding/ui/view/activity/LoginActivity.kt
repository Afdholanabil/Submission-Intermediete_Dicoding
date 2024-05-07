package com.example.submission_intermediete_dicoding.ui.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.submission_intermediete_dicoding.databinding.ActivityLoginBinding
import com.example.submission_intermediete_dicoding.ui.viewmodel.LoginViewModel
import com.example.submission_intermediete_dicoding.ui.viewmodel.ViewModelFactory
import com.example.submission_intermediete_dicoding.util.LoginPreference
import com.example.submission_intermediete_dicoding.util.datastore
import com.google.android.material.snackbar.Snackbar

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

        binding.btnLogin.setOnClickListener {

            val inputEmail = binding.etEmail.text.toString()
            val inputPw = binding.etPw.text.toString()
            if (inputEmail.isNotEmpty() && inputPw.isNotEmpty()) {
                loginViewModel.loginPost(inputEmail, inputPw)

            }
        }




        loginViewModel.loginResponse.observe(this) { response ->
            if (response != null) {
                if (!response.error) {
                    loginViewModel.saveLoginSession(true)
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Log.d(TAG, "response : ${response.message}")
                }
            }
        }

        loginViewModel.getLoginSession().observe(this) { isLoged: Boolean ->
            if (isLoged) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }

    companion object {
        private const val TAG = "LoginActivity"
    }
}
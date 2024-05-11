package com.example.submission_intermediete_dicoding.ui.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.submission_intermediete_dicoding.data.response.LoginResponse
import com.example.submission_intermediete_dicoding.databinding.ActivityLoginBinding
import com.example.submission_intermediete_dicoding.ui.viewmodel.LoginViewModel
import com.example.submission_intermediete_dicoding.ui.viewmodel.ViewModelFactory
import com.example.submission_intermediete_dicoding.util.LoginPreference
import com.example.submission_intermediete_dicoding.util.datastore
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var pref : LoginPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pref = LoginPreference.getInstance(application.datastore)
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

        binding.tvSignup.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        loginViewModel.loginResponse.observe(this) { response ->
            if (response != null) {
                if (!response.error) {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("id_login",response)
                    startActivity(intent)
                    loginViewModel.saveLoginSession(response)
                    Log.d(TAG, "data-activity: ${response.loginResult}")

                } else {
                    Log.d(TAG, "response : ${response.message}")
                }
            }
        }

        loginViewModel.getLoginSession().observe(this) { isLoged: LoginResponse? ->
                if (isLoged != null) {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("id_login",isLoged)
                    Log.d(TAG,"getLoginToken : $isLoged")
                    startActivity(intent)
                }
        }
    }


    companion object {
        private const val TAG = "LoginActivity"
    }
}
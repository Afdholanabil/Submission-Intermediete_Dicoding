package com.example.submission_intermediete_dicoding.ui.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.submission_intermediete_dicoding.R
import com.example.submission_intermediete_dicoding.data.response.LoginResponse
import com.example.submission_intermediete_dicoding.databinding.ActivityLoginBinding
import com.example.submission_intermediete_dicoding.repository.MyStoryRepository
import com.example.submission_intermediete_dicoding.ui.viewmodel.LoginViewModel
import com.example.submission_intermediete_dicoding.ui.viewmodel.ViewModelFactory
import com.example.submission_intermediete_dicoding.util.LoginPreference
import com.example.submission_intermediete_dicoding.util.datastore
import com.google.android.material.snackbar.Snackbar

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var pref : LoginPreference
    private var email : String? = ""
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
                if (isValidEmail(inputEmail)) {
                    email = inputEmail
                    loginViewModel.loginPost(inputEmail, inputPw)
                } else {
                    binding.etEmail.error = getString(R.string.error_emailNotValid)
                }
            } else {
                Snackbar.make(window.decorView.rootView, R.string.error_regist_allnull, Snackbar.LENGTH_SHORT).show()
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
                    intent.putExtra("email", email)
                    startActivity(intent)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
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
                    intent.putExtra("email", email)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    Log.d(TAG,"getLoginToken : $isLoged")
                    startActivity(intent)
                }
        }

        loginViewModel.loading.observe(this) {boolean ->
            showLoading(boolean)
        }

        loginViewModel.snackbar.observe(this) {
            it.getContentIfNotHandled()?.let { snackBar ->
                Snackbar.make(window.decorView.rootView, snackBar, Snackbar.LENGTH_SHORT).show()
            }
        }

        binding.apple.setOnClickListener {
            Snackbar.make(window.decorView.rootView,R.string.Message_notYetAvailable, Snackbar.LENGTH_SHORT).show()
        }

        binding.facebook.setOnClickListener {
            Snackbar.make(window.decorView.rootView,R.string.Message_notYetAvailable, Snackbar.LENGTH_SHORT).show()
        }
        binding.google.setOnClickListener {
            Snackbar.make(window.decorView.rootView,R.string.Message_notYetAvailable, Snackbar.LENGTH_SHORT).show()
        }

        setMyButtonEnable()

        binding.etPw.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                setMyButtonEnable()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

        binding.etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                setMyButtonEnable()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
    }

    private fun showLoading(a: Boolean) {
        if (a) {
            binding.progressCircular.visibility = View.VISIBLE
        } else {
            binding.progressCircular.visibility = View.GONE
        }
    }

    private fun isValidEmail(email: CharSequence?): Boolean {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun setMyButtonEnable() {
        val emailR = binding.etEmail.text
        val passR = binding.etPw.text

        val resultE = emailR != null && emailR.toString().isNotEmpty()
        val resultP = passR != null && passR.toString().isNotEmpty()
        binding.btnLogin.isEnabled = resultP && resultE
    }

    companion object {
        private const val TAG = "LoginActivity"
    }
}
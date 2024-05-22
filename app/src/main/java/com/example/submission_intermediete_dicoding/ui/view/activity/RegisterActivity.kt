package com.example.submission_intermediete_dicoding.ui.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import com.example.submission_intermediete_dicoding.R
import com.example.submission_intermediete_dicoding.databinding.ActivityRegisterBinding
import com.example.submission_intermediete_dicoding.ui.viewmodel.RegisterViewModel
import com.google.android.material.snackbar.Snackbar

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel by viewModels<RegisterViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerViewModel.loading.observe(this) {
            showLoading(it)
        }

        registerViewModel.editTextLenght.observe(this) {lenght ->
            registerViewModel.checkPwLenght()
        }

        registerViewModel.isInputValid.observe(this) {isValid ->
            if (!isValid) {
                binding.etPw.error = getString(R.string.error_regist_inputmusteight)
            } else {
                binding.etPw.error = null
            }
        }

        binding.etPw.addTextChangedListener { editable ->
            editable?.length?.let {
                registerViewModel.setEditTextLength(it) }
        }

        binding.tvToLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        registerViewModel.snackbar.observe(this) {
            it.getContentIfNotHandled()?.let { snackBar ->
                Snackbar.make(window.decorView.rootView, snackBar, Snackbar.LENGTH_SHORT).show()
            }
        }

        registerViewModel.registerDataResponse.observe(this) { response ->
            if (response != null) {
                if (!response.error) {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }

        binding.btnRegister.setOnClickListener {
            val inputName = binding.etName.text.toString()
            val inputEmail = binding.etEmail.text.toString()
            val inputPassword = binding.etPw.text.toString()
            val inputConfirmPassword = binding.etConfirmPw.text.toString()

            if (inputName.isNotEmpty() && inputEmail.isNotEmpty() && inputPassword.isNotEmpty() && inputConfirmPassword.isNotEmpty()) {
                if (inputPassword == inputConfirmPassword) {
                    binding.etConfirmPw.error = null
                    if (isValidEmail(inputEmail)) {
                        binding.etEmail.error = null
                        registerViewModel.postRegister(inputName, inputEmail, inputPassword)
                    } else {
                        binding.etEmail.error = getString(R.string.error_emailNotValid)
                    }
                } else {
                    binding.etConfirmPw.error = getString(R.string.error_regist_konfirmNotSame)
                }
            } else {
                Snackbar.make(window.decorView.rootView, R.string.error_regist_allnull, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun isValidEmail(email: CharSequence?): Boolean {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun showLoading(a: Boolean) {
        if (a) {
            binding.progressCircular.visibility = View.VISIBLE
        } else {
            binding.progressCircular.visibility = View.GONE
        }
    }

}
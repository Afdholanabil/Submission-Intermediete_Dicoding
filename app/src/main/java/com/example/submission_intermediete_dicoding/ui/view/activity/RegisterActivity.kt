package com.example.submission_intermediete_dicoding.ui.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
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
                if (isValidEmail(inputEmail)) {
                    binding.etEmail.hideError()
                    if (inputPassword.length >= 8) {
                        binding.etPw.hideError()
                        if (inputPassword == inputConfirmPassword) {
                            binding.etConfirmPw.error = null

                            registerViewModel.postRegister(inputName, inputEmail, inputPassword)
                        } else {
                            binding.etConfirmPw.error = getString(R.string.error_confirm_password)
                        }
                    } else {
                        binding.etPw.showError()
                    }
                } else {
                    binding.etEmail.showError()
                }
            } else {
                Snackbar.make(window.decorView.rootView, R.string.error_regist_allnull, Snackbar.LENGTH_SHORT).show()
            }

        }
        setRegisterButtonEnable()

        binding.etName.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                setRegisterButtonEnable()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        binding.etEmail.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                setRegisterButtonEnable()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        binding.etPw.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                setRegisterButtonEnable()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        binding.etConfirmPw.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                setRegisterButtonEnable()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
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

    private fun setRegisterButtonEnable() {
        val name = binding.etName.text
        val email = binding.etEmail.text
        val password = binding.etPw.text
        val confirmPassword = binding.etConfirmPw.text

        val nameR = name != null && name.toString().isNotEmpty()
        val emailR = email != null && email.toString().isNotEmpty()
        val passwordR = password != null && password.toString().isNotEmpty()
        val confirmPwR = confirmPassword != null && confirmPassword.toString().isNotEmpty()

        binding.btnRegister.isEnabled = nameR && emailR && passwordR && confirmPwR
    }



}
package com.example.submission_intermediete_dicoding.ui.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModel
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
                binding.etPw.error = "Input password harus lebih dari 8 karakter"

            } else {
                binding.etPw.error = null
            }
        }

        binding.etPw.addTextChangedListener { editable ->
            editable?.length?.let {
                registerViewModel.setEditTextLength(it) }
        }

//        registerViewModel.isConfirmSame.observe(this) {
//            if (!it) {
//                binding.etConfirmPw.error = "Konfirmasi password tidak sama!"
//            } else {
//                binding.etConfirmPw.error = null
//            }
//        }
//
//        binding.etConfirmPw.addTextChangedListener {editable ->
//            editable.let {
//                registerViewModel.setConfirmPassword(it.toString())
//            }
//
//        }
        registerViewModel.snackbar.observe(this) {
            it.getContentIfNotHandled()?.let { snackBar ->
                Snackbar.make(window.decorView.rootView, snackBar, Snackbar.LENGTH_SHORT).show()
            }
        }

        binding.btnRegister.setOnClickListener {
            val inputName = binding.etName.text.toString()
            val inputEmail = binding.etEmail.text.toString()
            val inputPaswword = binding.etPw.text.toString()
            val inputConfirmPasswrod = binding.etConfirmPw.text.toString()

            if (inputName.isNotEmpty() && inputEmail.isNotEmpty() && inputPaswword.isNotEmpty() && inputConfirmPasswrod.isNotEmpty() ) {
                if (inputPaswword == inputConfirmPasswrod) {
                    binding.etConfirmPw.error = null
                    registerViewModel.postRegister(inputName,inputEmail,inputPaswword)
                } else {
                    binding.etConfirmPw.error = "Konfirmasi password tidak sama!"
                }
            }
        }
    }

    private fun showLoading(a: Boolean) {
        if (a) {
            binding.progressCircular.visibility = View.VISIBLE
        } else {
            binding.progressCircular.visibility = View.GONE
        }
    }

}
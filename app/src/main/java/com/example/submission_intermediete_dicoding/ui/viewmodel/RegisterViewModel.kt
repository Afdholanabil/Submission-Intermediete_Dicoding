package com.example.submission_intermediete_dicoding.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission_intermediete_dicoding.R
import com.example.submission_intermediete_dicoding.data.response.RegisterResponse
import com.example.submission_intermediete_dicoding.data.retrofit.ApiConfig
import com.example.submission_intermediete_dicoding.util.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel :ViewModel() {

    private val _registerDataResponse = MutableLiveData<RegisterResponse>()
    val registerDataResponse : LiveData<RegisterResponse> = _registerDataResponse

    private val _loading = MutableLiveData<Boolean>()
    val loading : LiveData<Boolean> = _loading

    private val _editTextLenght = MutableLiveData<Int>()
    val editTextLenght : LiveData<Int> = _editTextLenght

    private val _isInputValid = MutableLiveData<Boolean>()
    val isInputValid : LiveData<Boolean> = _isInputValid

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password


    private val _confirmEt = MutableLiveData<String>()
    val confirmEt : LiveData<String> = _confirmEt

    private val _isConfirmSame = MutableLiveData<Boolean>()
    val isConfirmSame : LiveData<Boolean> = _isConfirmSame

    private val _snackBar = MutableLiveData<Event<String>>()
    val snackbar : LiveData<Event<String>> = _snackBar

    fun postRegister(nameP: String, emailP: String, passwordP: String) {
        _loading.value = true
        val call = ApiConfig.getApiService().postRegister(nameP, emailP, passwordP)
        call.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {

                if (response.isSuccessful) {
                    if (response.code() == 200 || response.code() == 201) {
                        _loading.value = false
                        _registerDataResponse.value = response.body()
                        Log.d(TAG, "OnResponse : ${response.message()}")
                        _snackBar.value = Event("Registrasi berhasil, Akun anda berhasil dibuat!")
                    }
                } else {
                    Log.d(TAG,"OnFail : ${response.message()}")
                    _snackBar.value = Event("Registrasi gagal!")
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _loading.value = false
                Log.d(TAG,"OnFailure : ${t.message}")
                _snackBar.value = Event("Terjadi kesalahan ! Mohon coba lagi nanti")

            }
        })
    }

    fun checkPwLenght() {
        val lenght = _editTextLenght.value ?: 0
        _isInputValid.value = lenght>= 8

    }

    fun setEditTextLength(length: Int) {
        _editTextLenght.value = length
    }

    fun confirmCheck() {
        val password = _password.value
        val confirmPassword = _confirmEt.value

        val check = password == confirmPassword
        _isConfirmSame.value = check
    }

    fun setConfirmPassword(confirm : String) {
        _confirmEt.value = confirm
    }

    companion object {
        private const val TAG = "RegisterViewModel"
    }
}
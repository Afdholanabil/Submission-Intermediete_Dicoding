package com.example.submission_intermediete_dicoding.ui.viewmodel

import android.util.Log
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.submission_intermediete_dicoding.data.response.LoginResponse
import com.example.submission_intermediete_dicoding.data.retrofit.ApiConfig
import com.example.submission_intermediete_dicoding.util.Event
import com.example.submission_intermediete_dicoding.util.LoginPreference
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginViewModel(private val preferences: LoginPreference) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading : LiveData<Boolean> = _loading

    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse : LiveData<LoginResponse> = _loginResponse

    private val _snackBar = MutableLiveData<Event<String>>()
    val snackbar : LiveData<Event<String>> = _snackBar

    fun loginPost(emailP : String, passwordP : String) {
        _loading.value = true
        val call = ApiConfig.getApiService().postLogin(emailP, passwordP)
        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    _loading.value = false
                    _loginResponse.value = response.body()
                    Log.d(TAG, "OnResponse : ${response.message()}")
                    _snackBar.value = Event("Berhasil Login !")
                } else {
                    Log.d(TAG,"OnFail : ${response.message()}")
                    _snackBar.value = Event("Gagal Login !")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _loading.value = false
                Log.d(TAG,"OnFailure : ${t.message}")
                _snackBar.value = Event("Terjadi kesalahan ! Mohon coba lagi nanti")
            }
        })

    }

    fun getLoginSession(): LiveData<Boolean> {
        return preferences.getLoginSession().asLiveData()
    }

    fun saveLoginSession(isLoged : Boolean) {
        viewModelScope.launch {
            preferences.saveLoginSession(isLoged)
        }
    }

    companion object {
        private const val TAG = "LoginViewModel"
    }
}
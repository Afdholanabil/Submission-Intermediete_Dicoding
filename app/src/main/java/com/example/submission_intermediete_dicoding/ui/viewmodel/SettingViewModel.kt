package com.example.submission_intermediete_dicoding.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.submission_intermediete_dicoding.data.response.LoginResponse
import com.example.submission_intermediete_dicoding.util.LoginPreference
import kotlinx.coroutines.launch

class SettingViewModel(private val pref : LoginPreference) : ViewModel() {

    fun getLoginSession(): LiveData<LoginResponse?> {
        return pref.getLoginSession().asLiveData()
    }


    fun logout() {
        viewModelScope.launch {
            pref.removeSession()
        }
    }

}
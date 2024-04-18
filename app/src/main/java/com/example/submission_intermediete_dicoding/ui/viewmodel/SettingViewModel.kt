package com.example.submission_intermediete_dicoding.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.submission_intermediete_dicoding.util.LoginPreference
import kotlinx.coroutines.launch

class SettingViewModel(private val pref : LoginPreference) : ViewModel() {

    fun getLoginSession(): LiveData<Boolean> {
        return pref.getLoginSession().asLiveData()
    }

    fun saveLoginSession(isLoged : Boolean) {
        viewModelScope.launch {
            pref.saveLoginSession(isLoged)
        }
    }

}
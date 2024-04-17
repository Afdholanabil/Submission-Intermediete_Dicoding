package com.example.submission_intermediete_dicoding.ui.viewmodel

import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.submission_intermediete_dicoding.util.LoginPreference
import kotlinx.coroutines.launch

class LoginViewModel(private val preferences: LoginPreference) : ViewModel() {

    fun getLoginSession(): LiveData<Boolean> {
        return preferences.getLoginSession().asLiveData()
    }

    fun saveLoginSession(isLoged : Boolean) {
        viewModelScope.launch {
            preferences.saveLoginSession(isLoged)
        }
    }
}
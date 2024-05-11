package com.example.submission_intermediete_dicoding.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.submission_intermediete_dicoding.util.OnBoardPreference
import kotlinx.coroutines.launch

class OnBoardViewModel(private val pref : OnBoardPreference) : ViewModel() {

    fun getOnBoardSession():LiveData<Boolean> {
        return pref.getOnBoardSession().asLiveData()
    }

    fun saveOnBoardSession(isOnBoarded : Boolean) {
        viewModelScope.launch {
            pref.saveOnboardSession(isOnBoarded)
        }
    }




    companion object {
        val kamu: String = "Fanny Rahma Jenni"
        private val FEELtoYOU = "kamu adalah rumahku," +
                "kamu adalah orang yang ada di hatiku" +
                "yang terakhir dan satu satunya, sampai" +
                "Allah memberikan jalan yang beda untuk kita." +
                "Ya, kamu $kamu  "


    }
}
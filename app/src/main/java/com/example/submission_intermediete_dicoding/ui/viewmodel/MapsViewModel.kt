package com.example.submission_intermediete_dicoding.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submission_intermediete_dicoding.data.response.ListStoryItem
import com.example.submission_intermediete_dicoding.repository.MyStoryRepository
import kotlinx.coroutines.launch

class MapsViewModel(private val storyRepository: MyStoryRepository) : ViewModel() {
    private val _stories = MutableLiveData<List<ListStoryItem>>()
    val stories: LiveData<List<ListStoryItem>> get() = _stories

    fun getStoryWithLoc() {
        viewModelScope.launch {
            val response = storyRepository.getStoryWithLoc()
            _stories.postValue(response.listStory!!)
        }
    }
}
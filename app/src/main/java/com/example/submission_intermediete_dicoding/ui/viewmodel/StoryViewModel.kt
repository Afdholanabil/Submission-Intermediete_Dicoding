package com.example.submission_intermediete_dicoding.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.submission_intermediete_dicoding.data.response.ListStoryItem
import com.example.submission_intermediete_dicoding.database.myStory.MyStory
import com.example.submission_intermediete_dicoding.repository.MyStoryRepository
import com.example.submission_intermediete_dicoding.util.Event

class StoryViewModel(private val storyRepository: MyStoryRepository) : ViewModel() {

    private val _allMyStory = MutableLiveData<List<MyStory>>()
    val allMyStories: LiveData<List<MyStory>> = _allMyStory


    val storiesWithPaging: LiveData<PagingData<ListStoryItem>> = storyRepository.getStoriesWithPaging().cachedIn(viewModelScope)

    private val _loading = MutableLiveData<Boolean>()
    val loading : LiveData<Boolean> = _loading

    private val _snackBar = MutableLiveData<Event<String>>()
    val snackbar : LiveData<Event<String>> = _snackBar

    fun getMyStoryALl() {
        _loading.value = true
        storyRepository.getAllMyStory().observeForever { list ->
            _allMyStory.value = list
            _loading.value = false
            _snackBar.value = Event("Berhasil menampilkan cerita anda!")

        }
    }

    companion object {
        private const val TAG = "StoryViewModel"
    }
}
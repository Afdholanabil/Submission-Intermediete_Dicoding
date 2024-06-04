package com.example.submission_intermediete_dicoding.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.submission_intermediete_dicoding.data.response.AddStoryResponse
import com.example.submission_intermediete_dicoding.data.response.ListStoryItem
import com.example.submission_intermediete_dicoding.database.myStory.MyStory
import com.example.submission_intermediete_dicoding.repository.MyStoryRepository
import com.example.submission_intermediete_dicoding.util.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class StoryViewModel(private val storyRepository: MyStoryRepository) : ViewModel() {

    private val _allMyStory = MutableLiveData<List<MyStory>>()
    val allMyStories: LiveData<List<MyStory>> = _allMyStory
    private val _addStoryResult = MutableLiveData<Result<AddStoryResponse>>()
    val addStoryResult: LiveData<Result<AddStoryResponse>> get() = _addStoryResult
    private val _addStoryResponse = MutableLiveData<AddStoryResponse>()
    val addStoryResponse: LiveData<AddStoryResponse> get() = _addStoryResponse

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _stories = MutableLiveData<List<ListStoryItem>?>()
    val stories: LiveData<List<ListStoryItem>?> = _stories

    val storiesWithPaging: Flow<PagingData<ListStoryItem>> = storyRepository.getStoriesWithPaging()
        .cachedIn(viewModelScope)

    private val _loading = MutableLiveData<Boolean>()
    val loading : LiveData<Boolean> = _loading

    private val _snackBar = MutableLiveData<Event<String>>()
    val snackbar : LiveData<Event<String>> = _snackBar

    fun getStories() {
        _loading.value = true
        viewModelScope.launch {
            try {
                val response = storyRepository.getStories()
                if (!response.error!!) {
                    _loading.value = false
                    _stories.postValue(response.listStory)
                    _snackBar.value = Event("Berhasil menampilkan cerita!")
                } else {
                    _loading.value = false
                    Log.d(TAG, "Error : else")
                    _snackBar.value = Event("Gagal menampilkan cerita!")
                }

            } catch (e: Exception) {
                _loading.value = false
                Log.e(TAG, "Error : $e")
                _snackBar.value = Event("Gagal, Terjadi masalah!")
            }
        }
    }

//    fun getStoriesWithPaging(): Flow<PagingData<ListStoryItem>> {
//        return storyRepository.getStoriesWithPaging()
//            .flow.cachedIn(viewModelScope)
//    }

    fun uploadStory(description: String, photoFile: File, lat: Double?, lon: Double?, myStory: MyStory) {

        _loading.value = true
        if (photoFile.length() > 1_000_000) {
            _loading.value = true
            _error.value = "File size should be less than 1MB"
            return
        }
        val descriptionPart = RequestBody.create("text/plain".toMediaTypeOrNull(), description)
        val photoPart = MultipartBody.Part.createFormData(
            "photo",
            photoFile.name,
            photoFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
        )

        viewModelScope.launch {
            try {
                _loading.value = false
                val response = storyRepository.addStory(descriptionPart, photoPart, lat, lon, myStory)
                _addStoryResponse.value = response
                _snackBar.value = Event("Berhasil menambahkan cerita!")
            } catch (e: Exception) {
                _loading.value = false
                _error.value = e.message
                _snackBar.value = Event("Gagal menambahkan cerita!")
            }
        }
    }

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
package com.example.submission_intermediete_dicoding.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submission_intermediete_dicoding.data.response.AddStoryResponse
import com.example.submission_intermediete_dicoding.data.response.ListStoryItem
import com.example.submission_intermediete_dicoding.data.response.StoryResponse
import com.example.submission_intermediete_dicoding.database.MyStory.MyStory
import com.example.submission_intermediete_dicoding.repository.MyStoryRepository
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class StoryViewModel(private val storyRepository: MyStoryRepository) : ViewModel() {

    private val _allMyStory = MutableLiveData<List<MyStory>>()
    val allMyStories: LiveData<List<MyStory>> = _allMyStory



    private val _addStoryResult = MutableLiveData<Result<AddStoryResponse>>()
    val addStoryResult: LiveData<Result<AddStoryResponse>> get() = _addStoryResult

    //baruu

    private val _addStoryResponse = MutableLiveData<AddStoryResponse>()
    val addStoryResponse: LiveData<AddStoryResponse> get() = _addStoryResponse

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    ///

    private val _stories = MutableLiveData<List<ListStoryItem>?>()
    val stories: LiveData<List<ListStoryItem>?> get() = _stories

    private val _loading = MutableLiveData<Boolean>()
    val loading : LiveData<Boolean> = _loading
    fun getStories() {
        viewModelScope.launch {
            try {
                val response = storyRepository.getStories()
                if (!response.error!!) {
                    _stories.postValue(response.listStory)
                } else {
                    Log.d(TAG, "Error : else")
                }

            } catch (e: Exception) {
                Log.e(TAG, "Error : $e")
            }
        }
    }

    fun addStory(description: String, photoFile: File, lat: Double?, lon: Double?, myStory: MyStory) {
        viewModelScope.launch {
            try {
                val requestFile = photoFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
                val photo = MultipartBody.Part.createFormData("photo", photoFile.name, requestFile)
                storyRepository.addStory(description, photo, lat, lon, myStory)
            } catch (e: Exception) {
               Log.e(TAG,"Error : $e")
            }
        }
    }

    fun uploadStory(description: String, photoFile: File, lat: Double?, lon: Double?, myStory: MyStory) {
        if (photoFile.length() > 1_000_000) {
            _error.value = "File size should be less than 1MB"
            return
        }

        val photoPart = MultipartBody.Part.createFormData(
            "photo",
            photoFile.name,
            photoFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
        )

        viewModelScope.launch {
            try {
                val response = storyRepository.addStory(description, photoPart, lat, lon, myStory)
                _addStoryResponse.value = response
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun getMyStoryALl() {
        _loading.value = true
        storyRepository.getAllMyStory().observeForever { list ->
            _allMyStory.value = list
            _loading.value = false
            _error.value = "Berhasil Menampilkan Story Anda"

        }
    }


    companion object {
        private const val TAG = "StoryViewModel"
    }
}
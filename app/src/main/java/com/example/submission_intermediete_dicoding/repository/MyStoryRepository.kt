package com.example.submission_intermediete_dicoding.repository

import androidx.lifecycle.LiveData
import com.example.submission_intermediete_dicoding.data.response.AddStoryResponse
import com.example.submission_intermediete_dicoding.data.response.ListStoryItem
import com.example.submission_intermediete_dicoding.data.response.StoryResponse
import com.example.submission_intermediete_dicoding.data.retrofit.ApiService
import com.example.submission_intermediete_dicoding.database.MyStory.MyStory
import com.example.submission_intermediete_dicoding.database.MyStory.MyStorydao
import com.example.submission_intermediete_dicoding.util.LoginPreference
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MyStoryRepository private constructor(
    private val apiService : ApiService,
    private val loginPreference: LoginPreference,
    private val myStoryDao : MyStorydao
) {
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    suspend fun getStories(): StoryResponse {
        val token = runBlocking { loginPreference.getLoginSession().first()?.loginResult?.token }
        return apiService.getStories("Bearer $token")
    }

    suspend fun addStory(
        description: String,
        photo: MultipartBody.Part,
        lat: Double?,
        lon: Double?,
        myStory: MyStory
    ) {

        val token = runBlocking { loginPreference.getLoginSession().first()?.loginResult?.token}
        val response = apiService.addStory("Bearer $token", description,photo,lat, lon)
        response.enqueue(object : Callback<AddStoryResponse> {
            override fun onResponse(
                call: Call<AddStoryResponse>,
                response: Response<AddStoryResponse>
            ) {
                executorService.execute {
                    myStoryDao.insert(myStory) }
            }

            override fun onFailure(call: Call<AddStoryResponse>, t: Throwable) {

            }
        })
    }

    fun getAllMyStory(): LiveData<List<MyStory>> = myStoryDao.getAllMyStory()

    fun getMyStoryById(id: String): LiveData<MyStory> = myStoryDao.getMyStoryById(id)

    fun insert(myStory: MyStory) {
        executorService.execute { myStoryDao.insert(myStory) }
    }

    fun delete(myStory: MyStory) {
        executorService.execute { myStoryDao.deleteMyStoryById(myStory) }
    }

    companion object {
        @Volatile
        private var instance: MyStoryRepository? = null

        fun getInstance(
            apiService: ApiService,
            loginPreference: LoginPreference,
            myStoryDao: MyStorydao
        ): MyStoryRepository = instance ?: synchronized(this) {
            instance ?: MyStoryRepository(apiService, loginPreference, myStoryDao).also { instance = it }
        }
    }

}
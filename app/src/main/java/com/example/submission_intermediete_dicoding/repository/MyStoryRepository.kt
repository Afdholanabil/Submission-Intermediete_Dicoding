package com.example.submission_intermediete_dicoding.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.submission_intermediete_dicoding.data.response.AddStoryResponse
import com.example.submission_intermediete_dicoding.data.response.ListStoryItem
import com.example.submission_intermediete_dicoding.data.response.LoginResponse
import com.example.submission_intermediete_dicoding.data.response.StoryResponse
import com.example.submission_intermediete_dicoding.data.retrofit.ApiService
import com.example.submission_intermediete_dicoding.database.myStory.MyStory
import com.example.submission_intermediete_dicoding.database.myStory.MyStorydao
import com.example.submission_intermediete_dicoding.repository.paging.StoryPagingSource
import com.example.submission_intermediete_dicoding.util.LoginPreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.mockito.Mockito
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors



open class MyStoryRepository (
    private val apiService : ApiService,
    private val loginPreference: LoginPreference,
    private val myStoryDao : MyStorydao
) {
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()


    suspend fun getStories(): StoryResponse {
        val token = runBlocking { loginPreference.getLoginSession().first()?.loginResult?.token }
        return apiService.getStories("Bearer $token")
    }

    fun initLoginSession(loginSession: LoginResponse) {
        Mockito.`when`(loginPreference.getLoginSession()).thenReturn(flowOf(loginSession))
    }

    suspend fun initToken(token: String?, dummyLoginSession:LoginResponse) {
        val loginSessionFlow = flowOf(dummyLoginSession)
        Mockito.doReturn(loginSessionFlow).`when`(loginPreference).getLoginSession()
    }


    fun getStoriesWithPaging(): LiveData<PagingData<ListStoryItem>> {

        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoryPagingSource(apiService,loginPreference)
            }
        ).liveData
    }

    suspend fun getStoryWithLoc(): StoryResponse {
        return withContext(Dispatchers.IO) {
            val token = runBlocking { loginPreference.getLoginSession().first()?.loginResult?.token }
            apiService.getStoryWithLoc("Bearer $token",1)
        }
    }

    suspend fun addStory(
        description: RequestBody?,
        photo: MultipartBody.Part,
        lat: Double?,
        lon: Double?,
        myStory: MyStory
    ) : AddStoryResponse {
        return withContext(Dispatchers.IO) {
            val token = loginPreference.getLoginSession().first()?.loginResult?.token
            val response = apiService.addStory("Bearer $token", description, photo, lat, lon)
            if (response.isSuccessful) {
                executorService.execute {
                    myStoryDao.insert(myStory) }
                response.body() ?: throw Exception("Response body is null")
            } else {
                throw Exception("Failed to add story: ${response.errorBody()?.string()}")
            }
        }
    }

    fun getAllMyStory(): LiveData<List<MyStory>> = myStoryDao.getAllMyStory()

    fun getMyStoryById(id: String): LiveData<MyStory> = myStoryDao.getMyStoryById(id)

    fun insert(myStory: MyStory) {
        executorService.execute { myStoryDao.insert(myStory) }
    }

    fun delete(myStory: MyStory) {
        executorService.execute { myStoryDao.deleteMyStoryById(myStory) }
    }

    fun getStoryCount(): LiveData<Int> {
        return myStoryDao.getStoryCount()
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
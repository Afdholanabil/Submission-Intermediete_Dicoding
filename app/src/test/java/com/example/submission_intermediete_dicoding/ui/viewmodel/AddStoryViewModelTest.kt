package com.example.submission_intermediete_dicoding.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.submission_intermediete_dicoding.data.response.AddStoryResponse
import com.example.submission_intermediete_dicoding.database.myStory.MyStory
import com.example.submission_intermediete_dicoding.repository.MyStoryRepository
import com.example.submission_intermediete_dicoding.util.DummyData
import com.example.submission_intermediete_dicoding.util.MainDispatcherRule
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.io.File

class AddStoryViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var storyRepository: MyStoryRepository

    private lateinit var addStoryViewModel: AddStoryViewModel

    private val dummyLoginSession = DummyData.generateDummyLogin()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        addStoryViewModel = AddStoryViewModel(storyRepository)

        runBlocking {
            storyRepository.initToken(dummyLoginSession.loginResult.token, dummyLoginSession)
        }
    }

    @Test
    fun `ketika postStory Tidak Boleh Null dan mengembalikan sukses`(): Unit = runBlocking {
        val descriptionText = "Teks deskripsi"
        val description = descriptionText.toRequestBody("text/plain".toMediaType())

        val file = Mockito.mock(File::class.java)
        val requestImageFile = file.asRequestBody("image/jpg".toMediaTypeOrNull())
        val imageMultipart = MultipartBody.Part.createFormData(
            "photo",
            "photo.jpg",
            requestImageFile
        )

        val lat = 0.0
        val lon = 0.0
        val myStory = MyStory(id = 1, desc = "desc", lat = lat, lon = lon, photoUrl = "url")

        val dummyResponse = AddStoryResponse(
            error = false,
            message = "sukses"
        )

        Mockito.`when`(storyRepository.addStory(description, imageMultipart, lat, lon, myStory)).thenReturn(dummyResponse)

        addStoryViewModel.postStory(description, imageMultipart, lat, lon, myStory)

        Mockito.verify(storyRepository).addStory(description, imageMultipart, lat, lon, myStory)
    }

    @Test
    fun `ketika Kesalahan Jaringan Harus Mengembalikan Error`() = runBlocking {
        val errorMessage = "Kesalahan jaringan"
        val descriptionText = "Teks deskripsi"
        val description = descriptionText.toRequestBody("text/plain".toMediaType())

        val file = Mockito.mock(File::class.java)
        val requestImageFile = file.asRequestBody("image/jpg".toMediaTypeOrNull())
        val imageMultipart = MultipartBody.Part.createFormData(
            "photo",
            "photo.jpg",
            requestImageFile
        )

        val lat = 0.0
        val lon = 0.0
        val myStory = MyStory(id = 1, desc = "desc", lat = lat, lon = lon, photoUrl = "url")

        Mockito.`when`(storyRepository.addStory(description, imageMultipart, lat, lon, myStory)).thenThrow(
            RuntimeException(errorMessage)
        )

        try {
            addStoryViewModel.postStory(description, imageMultipart, lat, lon, myStory)
        } catch (e: Exception) {
            assertEquals(errorMessage, e.message)
        }
    }
}
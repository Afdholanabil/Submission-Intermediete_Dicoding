package com.example.submission_intermediete_dicoding.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import androidx.paging.map
import com.example.submission_intermediete_dicoding.data.response.ListStoryItem
import com.example.submission_intermediete_dicoding.database.myStory.MyStory
import com.example.submission_intermediete_dicoding.repository.MyStoryRepository
import com.example.submission_intermediete_dicoding.util.DummyData
import com.example.submission_intermediete_dicoding.util.MainDispatcherRules
import com.example.submission_intermediete_dicoding.util.getOrAwaitValue
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.File


@ExperimentalCoroutinesApi
class StoryViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRules()

    private lateinit var storyViewModel: StoryViewModel
    private val storyRepository = mockk<MyStoryRepository>()

    @Before
    fun setUp() {
        storyViewModel = StoryViewModel(storyRepository)
    }

    @Test
    fun `getStoriesWithPaging should return PagingData`() = runBlockingTest {
        val dummyPagingData = PagingData.from(DummyData.generateDummyStories().listStory!!)
        val expectedResult = flowOf(dummyPagingData)

        coEvery { storyRepository.getStoriesWithPaging() } returns expectedResult

        val actualResult = storyViewModel.storiesWithPaging.first()

        assertNotNull(actualResult)
        assertEquals(dummyPagingData, actualResult)
    }

    @Test
    fun `when loading stories is successful, ensure data is not null and matches expected values`() = runBlockingTest {
        val dummyStories = DummyData.generateDummyStories().listStory!!
        val pagingData = PagingData.from(dummyStories)

        coEvery { storyRepository.getStoriesWithPaging() } returns flowOf(pagingData)

        val actualData = storyViewModel.storiesWithPaging.asSnapshot()

        assertNotNull(actualData)
        assertEquals(dummyStories.size, actualData.size)
        assertEquals(dummyStories.first(), actualData.first())
    }

    @Test
    fun `when no stories are returned, ensure data size is zero`() = runBlockingTest {
        val emptyPagingData = PagingData.from(emptyList<ListStoryItem>())

        coEvery { storyRepository.getStoriesWithPaging() } returns flowOf(emptyPagingData)

        val actualData = storyViewModel.storiesWithPaging.asSnapshot()

        assertNotNull(actualData)
        assertTrue(actualData.isEmpty())
    }

    @Test
    fun `addStory should trigger uploadStory in repository`() = runBlockingTest {
        val description = "Test Description".toRequestBody("text/plain".toMediaTypeOrNull())
        val photoFile = mockk<File>()
        val requestBody = photoFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val photo = MultipartBody.Part.createFormData("photo", photoFile.name, requestBody)
        val lat = 1.0
        val lon = 2.0
        val myStory = MyStory(desc = "Test Description", photoUrl = "photoUrl", lat = lat, lon = lon)
        val addStoryResponse = DummyData.generateDummyCreateStory()

        coEvery { storyRepository.addStory(description, photo, lat, lon, myStory) } returns addStoryResponse

        storyViewModel.uploadStory("Test Description", photoFile, lat, lon, myStory)
        val result = storyViewModel.addStoryResponse.getOrAwaitValue()

        coVerify { storyRepository.addStory(description, photo, lat, lon, myStory) }
        assertNotNull(result)
        assertEquals(addStoryResponse, result)
    }
}

@ExperimentalCoroutinesApi
suspend fun <T : Any> Flow<PagingData<T>>.asSnapshot(): List<T> {
    val items = mutableListOf<T>()
    this.collectLatest { pagingData ->
        pagingData.map { item ->
            items.add(item)
        }
    }
    return items
}
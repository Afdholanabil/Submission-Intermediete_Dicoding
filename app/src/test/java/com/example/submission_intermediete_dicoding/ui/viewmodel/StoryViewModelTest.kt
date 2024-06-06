package com.example.submission_intermediete_dicoding.ui.viewmodel

import androidx.paging.PagingData
import com.example.submission_intermediete_dicoding.repository.MyStoryRepository
import com.example.submission_intermediete_dicoding.util.DummyData
import com.example.submission_intermediete_dicoding.util.MainDispatcherRules
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class StoryViewModelTest {

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
}
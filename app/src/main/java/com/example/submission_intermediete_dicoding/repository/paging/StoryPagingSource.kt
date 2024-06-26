package com.example.submission_intermediete_dicoding.repository.paging


import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.submission_intermediete_dicoding.data.response.ListStoryItem
import com.example.submission_intermediete_dicoding.data.retrofit.ApiService
import com.example.submission_intermediete_dicoding.util.LoginPreference
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class StoryPagingSource(private val apiService: ApiService, private val loginPreference: LoginPreference) : PagingSource<Int, ListStoryItem>() {
    override fun getRefreshKey(state: PagingState<Int, ListStoryItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListStoryItem> {
        return try {
            val token = runBlocking { loginPreference.getLoginSession().first()?.loginResult?.token }
            val page = params.key ?: INIT_PAGE_INDX
            val responseData = apiService.getStoryPaging("Bearer $token",page, params.loadSize)

            LoadResult.Page(
                data = responseData.listStory!!,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (responseData.listStory.isEmpty()) null else page + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    companion object {
        private const val INIT_PAGE_INDX = 1
    }
}
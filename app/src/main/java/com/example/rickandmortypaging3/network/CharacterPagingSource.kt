package com.example.rickandmortypaging3.network

import android.net.Uri
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmortypaging3.network.model.PojoResult
import javax.inject.Inject


class CharacterPagingSource @Inject constructor(private val apiService: ApiService) :
    PagingSource<Int, PojoResult>() {
    override fun getRefreshKey(state: PagingState<Int, PojoResult>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PojoResult> {

        return try {
            val nextPage: Int = params.key ?: FIRST_PAGE_INDEX
            val response = apiService.apiGetCharacterFromPage(nextPage)
            var nextPageNumber: Int? = null

            if (response.info.next != null) {
                val uri = Uri.parse(response.info.next)
                val nextPageQuery = uri.getQueryParameter("page")
                Log.d("TEST", "nextPageQuery: $nextPageQuery ")
                nextPageNumber = nextPageQuery?.toInt()
                Log.d("TEST", "nextPageNumber: $nextPageNumber ")
            }
            var prevPageNumber: Int? = null
            if (response.info.prev != null) {
                val uri = Uri.parse(response.info.prev)
                Log.d("TEST", "uri: $uri ")
                val prevPageQuery = uri.getQueryParameter("page")
                Log.d("TEST", "prevPageQuery: $prevPageQuery ")
                prevPageNumber = prevPageQuery?.toInt()
            }
            LoadResult.Page(
                data = response.results,
                prevKey = prevPageNumber,
                nextKey = nextPageNumber
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object {
        private const val FIRST_PAGE_INDEX = 1
    }


}

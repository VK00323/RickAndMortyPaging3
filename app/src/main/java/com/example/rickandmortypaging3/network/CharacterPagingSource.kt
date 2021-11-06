package com.example.rickandmortypaging3.network

import android.net.Uri
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmortypaging3.network.model.PojoResult
import javax.inject.Inject


class CharacterPagingSource @Inject constructor(private val apiService: ApiService): PagingSource<Int, PojoResult>() {
    override fun getRefreshKey(state: PagingState<Int, PojoResult>): Int? {
//Последний доступный индекс в списке
//значение null, если доступ к данным подкачки еще не был осуществлен. Например, если этот снимок был создан до или во время первой загрузки.
        return state.anchorPosition
    }
//    задам логику
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PojoResult> {

//        может вылететь ошибка, все в трай-кетч
    return try {
        //либо номер из парамс.кей либо 1 страница
        val nextPage: Int = params.key ?: FIRST_PAGE_INDEX
// мой запрос, в ретрофит засовываем следующую страницу
        val response = apiService.apiGetCharacterFromPage(nextPage)
// номер следующей страницы
        var nextPageNumber: Int? = null

        if(response.info.next != null) {
//парсим нест пейдж
            val uri = Uri.parse(response.info.next)
//                 и берем из нее педж
            val nextPageQuery = uri.getQueryParameter("page")
            Log.d("TEST", "nextPageQuery: $nextPageQuery ")
//просто переводим в инт
            nextPageNumber = nextPageQuery?.toInt()
            Log.d("TEST", "nextPageNumber: $nextPageNumber ")
        }
//            номер предыдущей страницы
        var prevPageNumber: Int? = null
//            если предыдущая страница не равно нулю то мы берем ее и сохраняем в юрай
        if(response.info.prev != null) {
            val uri = Uri.parse(response.info.prev)
            Log.d("TEST", "uri: $uri ")
//                забираем из нее только номер страницы
            val prevPageQuery = uri.getQueryParameter("page")
            Log.d("TEST", "prevPageQuery: $prevPageQuery ")
//                сохраняем в переменную приводим к ИНТ
            prevPageNumber = prevPageQuery?.toInt()
        }
//загрузчик, передаем туда наш лист и номера страниц
        LoadResult.Page(data = response.results,
            prevKey = prevPageNumber,
            nextKey = nextPageNumber)
    }
    catch (e: Exception) {
        LoadResult.Error(e)
    }
}
    companion object {
        private const val FIRST_PAGE_INDEX = 1
    }

}

package com.example.rickandmortypaging3

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.rickandmortypaging3.di.RetrofitModule
import com.example.rickandmortypaging3.network.ApiService
import com.example.rickandmortypaging3.network.CharacterPagingSource
import com.example.rickandmortypaging3.network.model.PojoResult
import kotlinx.coroutines.flow.Flow

class CharacterViewModel  (application: Application) : AndroidViewModel(
    application)
{
    private val apiService: ApiService = RetrofitModule.retrofit().create(ApiService::class.java)
    //    загрузчик, вернет Пэйджинг дату обернутую в корутин флоу
    fun getAllCharacter(): Flow<PagingData<PojoResult>> {
//       Возвращаю пейджер, в параметры отдаю размер страница из АПИ и количество элементов которые сохраняются в памяти перед удаением
        return Pager (config = PagingConfig(pageSize = 20, maxSize = 100),
            pagingSourceFactory = { CharacterPagingSource(apiService) }).flow.cachedIn(viewModelScope)
    }
}

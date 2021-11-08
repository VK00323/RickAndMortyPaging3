package com.example.rickandmortypaging3.network

import com.example.rickandmortypaging3.network.model.PojoExample
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("character")
   suspend fun apiGetCharacterFromPage(
        @Query("page") page: Int
    ) : PojoExample
}

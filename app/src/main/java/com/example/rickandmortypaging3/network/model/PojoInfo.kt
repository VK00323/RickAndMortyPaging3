package com.example.rickandmortypaging3.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PojoInfo(

    @SerializedName("count") @Expose val count: Int?,
    @SerializedName("pages") @Expose val pages: Int?,
    @SerializedName("next") @Expose val next: String?,
    @SerializedName("prev") @Expose val prev: String?,
)
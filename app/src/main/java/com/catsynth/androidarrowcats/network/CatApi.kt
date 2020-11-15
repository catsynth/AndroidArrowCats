package com.catsynth.androidarrowcats.network

import arrow.integrations.retrofit.adapter.CallK
import com.catsynth.androidarrowcats.data.CatResult
import retrofit2.http.GET
import retrofit2.http.Query

interface CatApi {
    @GET("v1/images/search")
    fun images(@Query("limit") limit : Int = 1) : CallK<List<CatResult>>
}
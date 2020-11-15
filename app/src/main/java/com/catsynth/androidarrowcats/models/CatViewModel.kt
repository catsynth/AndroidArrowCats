package com.catsynth.androidarrowcats.models

import androidx.lifecycle.ViewModel
import arrow.core.*
import arrow.core.extensions.EitherApplicativeError
import arrow.core.extensions.either.applicativeError.applicativeError
import arrow.fx.IO
import arrow.fx.extensions.io.async.async
import arrow.fx.fix
import arrow.integrations.retrofit.adapter.CallKindAdapterFactory
import arrow.integrations.retrofit.adapter.unwrapBody
import com.catsynth.androidarrowcats.BuildConfig
import com.catsynth.androidarrowcats.network.CatApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class CatViewModel : ViewModel() {

    private val _state: MutableStateFlow<CatState> = MutableStateFlow(CatState())
    val state: StateFlow<CatState> = _state

    fun nextCatImage ()  {
        val meow = getCatImage()
            .unsafeRunSync()
            .unwrapBody(Either.applicativeError())
            .fix()
            .map { it.firstOrNull() }
            .flatMap { it.rightIfNotNull { } }
            .map { it.url }
        _state.value = CatState(meow)
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addNetworkInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val originalRequest = chain.request()
                val newRequest = originalRequest.newBuilder()
                    .addHeader("x-api-key", BuildConfig.CAT_API_KEY)
                    .build()
                return chain.proceed(newRequest)
            }
        })
        .build()

   private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.thecatapi.com")
        .client(okHttpClient)
        .addCallAdapterFactory(CallKindAdapterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create())
       .build()
    val catApi = retrofit.create(CatApi::class.java)

    private fun  getCatImage () = catApi.images().async(IO.async()).fix()


}
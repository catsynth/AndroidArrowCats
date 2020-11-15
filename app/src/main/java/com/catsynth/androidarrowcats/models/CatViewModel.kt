package com.catsynth.androidarrowcats.models

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.lifecycle.ViewModel
import arrow.core.*
import arrow.core.Either.Companion.left
import arrow.core.extensions.either.applicativeError.applicativeError
import arrow.fx.IO
import arrow.fx.extensions.io.async.async
import arrow.fx.fix
import arrow.integrations.retrofit.adapter.CallKindAdapterFactory
import arrow.integrations.retrofit.adapter.unwrapBody
import arrow.optics.Lens
import arrow.optics.Optional
import com.catsynth.androidarrowcats.BuildConfig
import com.catsynth.androidarrowcats.network.CatApi
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.lang.Exception

class CatViewModel : ViewModel() {

    enum class Color {
        Red, Green, Blue
    }

    private val _state: MutableStateFlow<CatState> = MutableStateFlow(CatState())
    val state: StateFlow<CatState> = _state

    val bitmapLens : Lens<CatState,Either<Any,Bitmap>> = CatState.bitmap
    val redLens : Lens<CatState,Float> = CatState.red
    val greenLens : Lens<CatState,Float> = CatState.green
    val blueLens : Lens<CatState,Float> = CatState.blue

    @ExperimentalCoroutinesApi
    fun nextCatImage ()  {
        val meow = getCatImage()
            .unsafeRunSync()
            .unwrapBody(Either.applicativeError())
            .fix()
            .map { it.firstOrNull() }
            .flatMap { it.rightIfNotNull { } }
            .map { it.url }
            .orNull()
            ?.let { url ->
                Picasso.get().load(url)
                    .into(object : Target {
                        override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                            _state.value = bitmapLens.set(state.value,bitmap.rightIfNotNull {  })
                        }

                        override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                            _state.value = bitmapLens.set(state.value,"".left())
                        }

                        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                        }

                    })
            }
    }

    @ExperimentalCoroutinesApi
    fun updateColor (color : Color, value : Float) {
        when(color) {
            Color.Red -> _state.value = redLens.set(state.value,value)
            Color.Green -> _state.value = greenLens.set(state.value,value)
            Color.Blue -> _state.value = blueLens.set(state.value,value)
        }
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
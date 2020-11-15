package com.catsynth.androidarrowcats.models

import android.graphics.Bitmap
import arrow.core.Either
import arrow.core.left
import arrow.optics.optics


@optics data class CatState (
    val bitmap : Either<Any, Bitmap> = "".left(),
    val red : Float = 1.0f,
    val green : Float = 1.0f,
    val blue : Float = 1.0f
) {
    companion object
}

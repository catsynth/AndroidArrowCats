package com.catsynth.androidarrowcats.models

import arrow.core.Either
import arrow.core.left


data class CatState (
    val url : Either<Any, String> = "".left()
)

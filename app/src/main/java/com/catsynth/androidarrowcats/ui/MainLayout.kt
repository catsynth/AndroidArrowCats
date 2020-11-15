package com.catsynth.androidarrowcats.ui

import NetworkImage

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.imageResource
import arrow.core.Either
import arrow.core.getOrElse
import com.catsynth.androidarrowcats.R
import com.catsynth.androidarrowcats.models.CatViewModel


@Composable
fun MainLayout (model : CatViewModel) {

    val imageState = model.state.collectAsState()

    MaterialTheme {
        Column {
            ButtonBar(model)
            when (imageState.value.url) {
                is Either.Left -> Image (
                    modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                    asset = imageResource(id = R.drawable.kitty)
                )
                is Either.Right -> {
                    val right = imageState.value.url.getOrElse { "" }
                    NetworkImage(
                        url = right,
                        modifier = Modifier.fillMaxWidth().fillMaxHeight()
                    )
                }
            }
        }
    }
}
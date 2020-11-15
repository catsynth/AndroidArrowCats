package com.catsynth.androidarrowcats.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageAsset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import arrow.core.Either
import com.catsynth.androidarrowcats.R
import com.catsynth.androidarrowcats.models.CatViewModel


@Composable
fun MainLayout (model : CatViewModel) {

    val imageState = model.state.collectAsState()

    MaterialTheme {
        Column {
            ButtonBar(model)
            Row (
                modifier = Modifier.height(400.dp).padding(vertical = 20.dp)
            ) {
                val colorFilter = ColorFilter(Color(imageState.value.red,
                                                         imageState.value.green,
                                                         imageState.value.blue,
                                                         0.2f),
                                              BlendMode.Hue)
                when (imageState.value.bitmap) {
                    is Either.Left -> Image(
                        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                        asset = imageResource(id = R.drawable.kitty),
                        contentScale = ContentScale.Inside,
                        colorFilter = colorFilter
                    )
                    is Either.Right -> {
                        val asset = imageState.value.bitmap.map { it.asImageAsset() }
                        asset.orNull()?.let { asset ->
                            Image(
                                modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                                asset = asset,
                                contentScale = ContentScale.Fit,
                                colorFilter = colorFilter
                            )
                        }
                    }
                }
            }
            ColorChange(model)
        }
    }
}
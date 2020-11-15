package com.catsynth.androidarrowcats.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.imageResource
import arrow.core.Either
import arrow.core.extensions.either.applicativeError.applicativeError
import arrow.core.fix
import arrow.integrations.retrofit.adapter.unwrapBody
import com.catsynth.androidarrowcats.R
import com.catsynth.androidarrowcats.models.CatViewModel

@Composable
fun ButtonBar (model: CatViewModel) {
    Row(
        modifier = Modifier.height(dimensionResource(id = R.dimen.top_button_height))
    ) {
        TextButton(
            modifier = Modifier.width(dimensionResource(id = R.dimen.top_button_width))
                .height(dimensionResource(id = R.dimen.top_button_height)),
            onClick = {
                model.nextCatImage()
            }
        ) {
            Image(asset = imageResource(id = R.drawable.kitty))
        }
        TextButton(
            modifier = Modifier.width(dimensionResource(id = R.dimen.top_button_width))
                .height(dimensionResource(id = R.dimen.top_button_height)),
            onClick = {}
        ) {
            Image(asset = imageResource(id = R.drawable.camera))
        }
    }
}
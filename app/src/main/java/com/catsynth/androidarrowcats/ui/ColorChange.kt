package com.catsynth.androidarrowcats.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.catsynth.androidarrowcats.R
import com.catsynth.androidarrowcats.models.CatViewModel

@Composable
fun ColorChange (model : CatViewModel) {
    Column {
        ColorChangeRow(
            model = model,
            label = stringResource(id = R.string.red),
            color = CatViewModel.Color.Red
        )
        ColorChangeRow(
            model = model,
            label = stringResource(id = R.string.green),
            color = CatViewModel.Color.Green
        )
        ColorChangeRow(
            model = model,
            label = stringResource(id = R.string.blue),
            color = CatViewModel.Color.Blue
        )
    }
}
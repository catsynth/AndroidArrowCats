package com.catsynth.androidarrowcats.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.catsynth.androidarrowcats.R
import com.catsynth.androidarrowcats.models.CatViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Composable
fun ColorChangeRow (model : CatViewModel, label : String, color : CatViewModel.Color ) {
    var sliderPosition by remember { mutableStateOf(1f) }

    Row (verticalAlignment = Alignment.CenterVertically) {
        Text(
            modifier = Modifier.padding(horizontal = 10.dp),
            text = label
        )
        Slider(
            value = sliderPosition,
            modifier = Modifier.height(dimensionResource(R.dimen.top_button_height))
                .padding(horizontal = 10.dp),
            onValueChange = {
                sliderPosition = it
                model.updateColor(color,it)
            }
        )
    }
}
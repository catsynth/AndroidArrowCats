package com.catsynth.androidarrowcats

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.stringResource
import com.catsynth.androidarrowcats.models.CatViewModel

import com.catsynth.androidarrowcats.ui.MainLayout

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val model : CatViewModel by viewModels()
        setContent {
            MaterialTheme {
                TopAppBar {
                    title = stringResource(id = R.string.app_title)
                }
                MainLayout(model)
            }
        }
    }
}

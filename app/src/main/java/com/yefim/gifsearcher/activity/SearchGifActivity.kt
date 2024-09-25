package com.yefim.gifsearcher.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.yefim.gifsearcher.screen.search.GifSearchScreen
import com.yefim.gifsearcher.ui.theme.GifSearcherTheme
import com.yefim.gifsearcher.ui.util.LocalInsetsPaddings
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchGifActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        installSplashScreen()
        actionBar?.hide()
        super.onCreate(savedInstanceState)

        setContent {
            GifSearcherTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .imePadding()
                ) { innerPadding ->
                    CompositionLocalProvider(LocalInsetsPaddings provides innerPadding) {
                        GifSearchScreen()
                    }
                }
            }
        }
    }
}
package com.williamsel.mathstack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.williamsel.mathstack.core.navigation.AppNavHost
import com.williamsel.mathstack.ui.theme.MathStackTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MathStackTheme {
                AppNavHost()
            }
        }
    }
}
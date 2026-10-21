package com.rubenmariaca.viewmodeldemo.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple = Color(0xFF5B3CC4)
val Green = Color(0xFF1E8E5A)
val Orange = Color(0xFFD9822B)

@Composable
fun ViewModelDemoTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Purple,
            secondary = Green,
            background = Color(0xFFF7F5FC),
            surface = Color.White
        ),
        content = content
    )
}

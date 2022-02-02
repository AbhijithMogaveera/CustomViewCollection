package com.abhijith.customviewcollection.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.setPadding
import com.example.simple_clock.MyClockView

@Composable
fun ClockView() {
    AndroidView(factory = {
        MyClockView(it).apply {
            setPadding(50)
        }
    }, modifier = Modifier.padding(horizontal = 30.dp))
}
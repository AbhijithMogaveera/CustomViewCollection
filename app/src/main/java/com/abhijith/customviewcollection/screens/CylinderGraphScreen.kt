package com.abhijith.customviewcollection.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.abhijith.miui_cylinder_graph.ui.CylinderGraphView

@Composable
fun CylinderGraphComp() {
    AndroidView(factory = {
        CylinderGraphView(it)
    }, modifier = Modifier.padding(horizontal = 100.dp, vertical = 200.dp))
}
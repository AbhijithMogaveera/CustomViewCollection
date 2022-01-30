package com.abhijith.miui_cylinder_graph.util

import android.graphics.Paint
import android.graphics.Rect

interface ViewHelper {
    val tempBound: Rect
    val paint: Paint
    val canvasBound: Rect
}
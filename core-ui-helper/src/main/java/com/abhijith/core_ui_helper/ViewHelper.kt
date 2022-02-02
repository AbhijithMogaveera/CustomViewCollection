package com.abhijith.core_ui_helper

import android.graphics.Paint
import android.graphics.Rect

interface ViewHelper {
    val tempBound: Rect
    val paint: Paint
    val canvasBound: Rect
}
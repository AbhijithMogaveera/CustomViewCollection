package com.abhijith.core_ui_helper

import android.graphics.Rect
import kotlin.math.roundToInt

fun Rect.getSpaceDistributed(
    viewHelper: ViewHelper,
    list: List<SectionData>,
    calBack: (Int, Rect, SectionData) -> Unit
) {
    val unit = height() / 100.0
    var lastY = top
    list.forEachIndexed { index, sectionData ->
        val delta = unit * sectionData.percentage
        viewHelper.tempBound.set(
            left,
            lastY,
            right,
            (lastY + delta).roundToInt()
        )
        calBack(index, viewHelper.tempBound, sectionData)
        lastY += delta.toInt()
    }
}
package com.abhijith.cylindergraph

import android.graphics.*
import kotlin.math.roundToInt

fun Rect.getSpaceDistributedStartByBottom(
    cylinderGraphView: CylinderGraphView,
    list: List<CylinderSectionData>,
    calBack: (Int, Rect, CylinderSectionData) -> Unit
): Int {
    val unit = height() / 100.0
    var lastBottomY = bottom
    list.forEachIndexed { index, sectionData ->
        val delta = unit * sectionData.percentage
        cylinderGraphView.bound.set(
            left,
            (lastBottomY - delta).roundToInt(),
            right,
            lastBottomY
        )
        calBack(index, cylinderGraphView.bound, sectionData)
        lastBottomY -= delta.toInt()
    }
    return lastBottomY
}

fun Rect.getSpaceDistributedStartByBottomWithGap(
    cylinderGraphView: CylinderGraphView,
    list: List<CylinderSectionData>,
    gap: Int,
    calBack: (Int, Rect, CylinderSectionData) -> Unit
): Int {
    val unit = height() / 100.0
    var lastBottomY = bottom-gap
    list.forEachIndexed { index, sectionData ->
        val delta = unit * sectionData.percentage
        cylinderGraphView.bound.set(
            left,
            (lastBottomY - delta).roundToInt(),
            right,
            lastBottomY
        )
        calBack(index, cylinderGraphView.bound, sectionData)
        lastBottomY -= delta.toInt()+gap
    }
    return lastBottomY
}


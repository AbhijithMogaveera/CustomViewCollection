package com.abhijith.miui_cylinder_graph.extension

import android.graphics.*
import com.abhijith.miui_cylinder_graph.util.ViewHelper
import com.abhijith.miui_cylinder_graph.model.SectionData
import kotlin.math.roundToInt

fun Rect.getSpaceDistributedStartByTop(
    viewHelper: ViewHelper,
    list: List<SectionData>,
    calBack: (Int, Rect, SectionData) -> Unit
): Int {
    val unit = height() / 100.0
    var lastTopY = top
    list.forEachIndexed { index, sectionData ->
        val delta = unit * sectionData.percentage
        viewHelper.tempBound.set(
            left,
            lastTopY,
            right,
            (lastTopY + delta).roundToInt()
        )
        calBack(index, viewHelper.tempBound, sectionData)
        lastTopY += delta.toInt()
    }
    return height() - lastTopY
}

fun Rect.getSpaceDistributedStartByBottom(
    viewHelper: ViewHelper,
    list: List<SectionData>,
    calBack: (Int, Rect, SectionData) -> Unit
): Int {
    val unit = height() / 100.0
    var lastBottomY = bottom
    list.forEachIndexed { index, sectionData ->
        val delta = unit * sectionData.percentage
        viewHelper.tempBound.set(
            left,
            (lastBottomY - delta).roundToInt(),
            right,
            lastBottomY
        )
        calBack(index, viewHelper.tempBound, sectionData)
        lastBottomY -= delta.toInt()
    }
    return lastBottomY
}

fun Rect.getSpaceDistributedStartByBottomWithGap(
    viewHelper: ViewHelper,
    list: List<SectionData>,
    gap: Int,
    calBack: (Int, Rect, SectionData) -> Unit
): Int {
    val unit = height() / 100.0
    var lastBottomY = bottom-gap
    list.forEachIndexed { index, sectionData ->
        val delta = unit * sectionData.percentage
        viewHelper.tempBound.set(
            left,
            (lastBottomY - delta).roundToInt(),
            right,
            lastBottomY
        )
        calBack(index, viewHelper.tempBound, sectionData)
        lastBottomY -= delta.toInt()+gap
    }
    return lastBottomY
}

fun RectF.drawBorder(canvas: Canvas, paint: Paint, color: Int = Color.WHITE) {
    return
    val holdColor = paint.color
    paint.color = color
//    val fl = this.width() * this.height()
//    paint.textSize = 52f
//    canvas.drawText(fl.toString(), centerX(), centerY(), paint)
//    Log.e("SizeOfRect","$fl")
    canvas.drawRect(this, paint)
    paint.color = holdColor
}
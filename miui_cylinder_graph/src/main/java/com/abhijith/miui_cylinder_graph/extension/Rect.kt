package com.abhijith.miui_cylinder_graph.extension

import android.graphics.*
import com.abhijith.miui_cylinder_graph.util.ViewHelper
import com.abhijith.miui_cylinder_graph.model.SectionData
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
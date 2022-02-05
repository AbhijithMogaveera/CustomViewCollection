package com.abhijith.miui_cylinder_graph.shape

import android.graphics.*
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.core.graphics.toColorInt
import androidx.core.graphics.toRectF
import com.abhijith.miui_cylinder_graph.util.ARC_VALUE

class CylinderDrawable : Drawable() {

    @ColorInt
    var color = Color.RED

    private val rectanglePaint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.RED
        isAntiAlias = true
    }
    private val ovalPaint = Paint().apply {
        style = Paint.Style.FILL
        color = "#c3cef0".toColorInt()
        isAntiAlias = true
    }

    private val ovalBound = RectF()

    init {
        reset()
    }

    private var myPath = Path()
    fun reset() {

        val delta = bounds.height() * 0.1
        val top = bounds.top.toFloat()
        val start = bounds.left.toFloat()
        val bottom = bounds.bottom.toFloat()
        val end = bounds.right.toFloat()

        myPath = Path()

        myPath.reset()

        val ovalMidY = top + (delta * 2) - 60

        myPath.moveTo(start, ovalMidY.toFloat())
        myPath.lineTo(start, bottom - ARC_VALUE)
        myPath.arcTo(RectF(start, bottom - ARC_VALUE, end, bottom), 180f, -180f)
        myPath.lineTo(end, ovalMidY.toFloat())
        myPath.lineTo(start, ovalMidY.toFloat())

        ovalBound.set(start, bounds.top.toFloat(), end, (ovalMidY * 2).toFloat(),)
    }

    override fun draw(canvas: Canvas) {

        canvas.drawOval(bounds.toRectF(), ovalPaint)
    }

    override fun setAlpha(alpha: Int) {
        rectanglePaint.alpha = alpha
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {

    }

    override fun getOpacity(): Int {
        return rectanglePaint.alpha
    }

}


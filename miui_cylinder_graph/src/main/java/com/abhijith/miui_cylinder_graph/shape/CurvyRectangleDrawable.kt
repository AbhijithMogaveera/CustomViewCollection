package com.abhijith.miui_cylinder_graph.shape

import android.graphics.*
import android.graphics.drawable.Drawable
import com.abhijith.miui_cylinder_graph.extension.drawBorder
import com.abhijith.miui_cylinder_graph.util.ARC_VALUE

class CurvyRectangleDrawable : Drawable() {

    var color = Color.BLUE

    private val rectanglePaint = Paint().apply {
        style = Paint.Style.FILL
        strokeWidth = 10f
        color = Color.RED
        isAntiAlias = true
    }

    //bounds
    private val rectangleBound = RectF()

    init {
        reset()
    }

    fun reset() {
        val delta = bounds.top
        val start = bounds.left
        val bottom = bounds.bottom
        val end = bounds.right
        rectangleBound.set(
            bounds.left + start.toFloat(), delta.toInt().toFloat(), end.toFloat(),
            bottom.toFloat()
        )
    }

    override fun draw(canvas: Canvas) {
        val path = Path()
        path.reset()
        rectangleBound.drawBorder(canvas, rectanglePaint, Color.YELLOW)
        val top = rectangleBound.top
        val left = rectangleBound.left
        val bottom = rectangleBound.bottom
        val right = rectangleBound.right
        path.reset()
        path.moveTo(left, top)
        path.lineTo(left, bottom - ARC_VALUE)
        path.arcTo(RectF(left, bottom - ARC_VALUE, right, bottom).apply {
            drawBorder(canvas, rectanglePaint)
        }, 180f, -180f)
        path.lineTo(right, top)
        path.arcTo(RectF(left, top, right, top + ARC_VALUE).apply {
            drawBorder(canvas, rectanglePaint)
        }, 0f, 180f)
        canvas.drawPath(path, rectanglePaint.apply {
            color = this@CurvyRectangleDrawable.color
        })
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
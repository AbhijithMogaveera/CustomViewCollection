package com.abhijith.miui_cylinder_graph.shape

import android.graphics.*
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.core.graphics.ColorUtils
import com.abhijith.miui_cylinder_graph.util.ARC_VALUE

/*@ColorInt fun darkenColor(@ColorInt color: Int): Int {
    return Color.HSVToColor(FloatArray(3).apply {
        Color.colorToHSV(color, this)
        this[2] *= 0.8f
    })
}*/

class CylinderDrawable : Drawable() {

    @ColorInt
    var color = Color.RED

    var isEnabled: Boolean = true

    private val rectanglePaint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.RED
        isAntiAlias = true
    }
    private val ovalPaint = Paint().apply {
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    private val ovalBoundTop = RectF()
    private val ovalBoundBottom = RectF()

    init {
        invalidate(bounds)
    }

    fun invalidate(newBound: Rect) {
        bounds = newBound
        val start = bounds.left.toFloat()
        val end = bounds.right.toFloat()
        val i = 60
        ovalBoundTop.set(
            start,
            bounds.top.toFloat() - i,
            end,
            bounds.top.toFloat() + i.toFloat(),
        )
        ovalBoundBottom.set(
            start,
            bounds.bottom.toFloat() + i,
            end,
            bounds.bottom.toFloat() - i.toFloat(),
        )
    }

    override fun draw(canvas: Canvas) {
        canvas.drawRect(bounds, rectanglePaint.apply {
            color = if(isEnabled) this@CylinderDrawable.color else ColorUtils.blendARGB(this@CylinderDrawable.color,  Color.WHITE, 0.8f)
            if(!isEnabled)
                alpha = 150
            else
                alpha = 255
        })
        canvas.drawOval(ovalBoundBottom, ovalPaint.apply {
            if(!isEnabled)
                alpha = 150
            else
                alpha = 255
            color = if(isEnabled) this@CylinderDrawable.color else ColorUtils.blendARGB(this@CylinderDrawable.color,  Color.WHITE, 0.8f)
        })
        canvas.drawOval(ovalBoundTop, ovalPaint.apply {
            color = ColorUtils.blendARGB(
                this@CylinderDrawable.color,
                if (isEnabled) Color.BLACK else Color.WHITE,
                if (isEnabled) 0.2f else 0.7f
            )
        })
    }

    override fun setAlpha(alpha: Int) {
        rectanglePaint.alpha = alpha
        ovalPaint.alpha = alpha
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {

    }

    override fun getOpacity(): Int {
        return rectanglePaint.alpha
    }

}


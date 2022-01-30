package com.abhijith.custom_drawables

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.Shape
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import kotlin.math.roundToInt

const val total = 100

class SectionData(
    val percentage: Int,
    @ColorRes
    val color: Int
)

object DummyData {
    val items = listOf(
        SectionData(50, R.color.lavender),
        SectionData(25, R.color.saffron),
        SectionData(25, R.color.sana),
    )
}

class BorderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes), ViewHelper {

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas ?: return
        canvas.getClipBounds(canvasBound)
        val i = 110
        canvasBound.bottom = canvasBound.bottom - i
        canvasBound.getSpaceDistributed(this, DummyData.items) { index, rect, data ->
//            rect.bottom = rect.bottom + i
            canvas.drawRect(
                rect,
                paint.apply {
                    color = Color.RED
                    style = Paint.Style.STROKE
                    strokeWidth = 10f
                }
            )
        }
    }

    override val tempBound: Rect = Rect()
    override val paint: Paint = Paint()
    override val canvasBound: Rect = Rect()
}

interface ViewHelper {
    val tempBound: Rect
    val paint: Paint
    val canvasBound: Rect
}

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
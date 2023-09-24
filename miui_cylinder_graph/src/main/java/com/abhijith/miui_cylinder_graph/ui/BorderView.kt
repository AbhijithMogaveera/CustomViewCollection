package com.abhijith.miui_cylinder_graph.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import com.abhijith.miui_cylinder_graph.data.DummyData
import com.abhijith.miui_cylinder_graph.util.ViewHelper
import com.abhijith.miui_cylinder_graph.extension.getSpaceDistributedStartByTop

class BorderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes), ViewHelper {

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.getClipBounds(canvasBound)
        val i = 110
        canvasBound.bottom = canvasBound.bottom - i
        canvasBound.getSpaceDistributedStartByTop(this, DummyData.items) { index, rect, data ->
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
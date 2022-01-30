package com.abhijith.miui_cylinder_graph.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.abhijith.miui_cylinder_graph.data.DummyData
import com.abhijith.miui_cylinder_graph.extension.getSpaceDistributed
import com.abhijith.miui_cylinder_graph.shape.CurvyRectangleDrawable
import com.abhijith.miui_cylinder_graph.shape.CylinderDrawable
import com.abhijith.miui_cylinder_graph.util.ViewHelper

class CylinderGraphView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes), ViewHelper {

    private val cylinderDrawable = CylinderDrawable()
    private val curvyRectangleDrawable = CurvyRectangleDrawable()

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas ?: return
        canvas.getClipBounds(canvasBound)
        val i = 110
        canvasBound.bottom = canvasBound.bottom - i
        canvasBound.getSpaceDistributed(this, DummyData.items) { index, rect, sectionData ->
            rect.bottom = rect.bottom + i
            if (index == 0) {
                cylinderDrawable.bounds = rect
                cylinderDrawable.reset()
                cylinderDrawable.color = ContextCompat.getColor(context, sectionData.color)
                cylinderDrawable.draw(canvas)
            } else {
                curvyRectangleDrawable.bounds = rect
                curvyRectangleDrawable.reset()
                curvyRectangleDrawable.color = ContextCompat.getColor(context, sectionData.color)
                curvyRectangleDrawable.draw(canvas)
            }
        }
    }

    override val tempBound: Rect = Rect()
    override val paint: Paint = Paint()
    override val canvasBound: Rect = Rect()
}
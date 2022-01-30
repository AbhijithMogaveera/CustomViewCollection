package com.abhijith.miui_cylinder_graph.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import com.abhijith.miui_cylinder_graph.data.DummyData
import com.abhijith.miui_cylinder_graph.extension.getSpaceDistributed
import com.abhijith.miui_cylinder_graph.model.SectionData
import com.abhijith.miui_cylinder_graph.model.SectionDataWrapper
import com.abhijith.miui_cylinder_graph.shape.CurvyRectangleDrawable
import com.abhijith.miui_cylinder_graph.shape.CylinderDrawable
import com.abhijith.miui_cylinder_graph.util.ViewHelper

class CylinderGraphView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes), ViewHelper {
    private val shrinkY = 110
    private val cylinderDrawable = CylinderDrawable()

    private val curvyRectangleDrawable = CurvyRectangleDrawable()

    private var sectionListWithWrapper: List<SectionDataWrapper> = listOf()

    init {
        setData(DummyData.items)
    }

    private fun setData(list: List<SectionData>) {
        sectionListWithWrapper = list.map {
            SectionDataWrapper(it)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val start = paddingStart
        val top = paddingTop
        canvasBound.set(start, top, w, h)
        canvasBound.bottom = canvasBound.bottom - shrinkY
        calculateIndividualSectionData()
    }

    private fun calculateIndividualSectionData() {
        canvasBound.getSpaceDistributed(
            this,
            sectionListWithWrapper.map {
                it.data
            }
        ) { index, rect, sectionData ->
            rect.bottom = rect.bottom + shrinkY
            sectionListWithWrapper[index].rect.set(rect)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas ?: return
        sectionListWithWrapper.forEachIndexed { index, sectionDataWrapper ->
            if (index == 0) {
                cylinderDrawable.bounds = sectionDataWrapper.rect
                cylinderDrawable.reset()
                cylinderDrawable.color = ContextCompat.getColor(context, sectionDataWrapper.data.color)
                cylinderDrawable.draw(canvas)
            } else {
                curvyRectangleDrawable.bounds = sectionDataWrapper.rect
                curvyRectangleDrawable.reset()
                curvyRectangleDrawable.color = ContextCompat.getColor(context, sectionDataWrapper.data.color)
                curvyRectangleDrawable.draw(canvas)
            }
        }
    }

    override val tempBound: Rect = Rect()
    override val paint: Paint = Paint()
    override val canvasBound: Rect = Rect()
}
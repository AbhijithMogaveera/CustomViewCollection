package com.abhijith.miui_cylinder_graph.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
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

    private val shrinkY = 60

    private val ovalDrawable = CylinderDrawable()

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
        canvasBound.top = canvasBound.top + 130
        canvasBound.bottom = canvasBound.bottom - 130
        calculateIndividualSectionData()
    }


    private fun calculateIndividualSectionData() {
        canvasBound.getSpaceDistributed(
            this,
            sectionListWithWrapper.map {
                it.data
            }
        ) { index, rect, _ ->
            val i = 90
            if (index == 0) {
                rect.top = rect.top - (i/3)
                oval = Rect(0, rect.top - i, width, rect.top + i)
            }
            /*if(index == 0){
                rect.bottom = rect.bottom - shrinkY * index
            }
            if (index != 0) {
                rect.top = rect.top - shrinkY * index
                rect.bottom = rect.bottom - shrinkY * index
            }*/
            rect.top = if (index == 0) ((rect.top) - shrinkY) else rect.top - shrinkY
            rect.bottom = rect.bottom + shrinkY
            sectionListWithWrapper[index].rect.set(rect)


        }
    }

    private var oval = Rect()
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas ?: return
        canvas.drawRect(canvasBound, paint)
        sectionListWithWrapper.forEachIndexed { index, sectionDataWrapper ->
            curvyRectangleDrawable.bounds = sectionDataWrapper.rect
            curvyRectangleDrawable.reset()
            curvyRectangleDrawable.color = ContextCompat.getColor(context, sectionDataWrapper.data.color)
            curvyRectangleDrawable.draw(canvas)
            if (index == 0) {
                ovalDrawable.bounds = oval
                ovalDrawable.reset()
                ovalDrawable.color = ContextCompat.getColor(context, sectionDataWrapper.data.color)
                ovalDrawable.draw(canvas)
            }
        }
    }

    override val tempBound: Rect = Rect()
    override val paint: Paint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = 10f
        color = Color.RED
    }
    override val canvasBound: Rect = Rect()
}
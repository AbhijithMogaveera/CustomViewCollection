package com.abhijith.miui_cylinder_graph.ui

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.abhijith.miui_cylinder_graph.data.DummyData
import com.abhijith.miui_cylinder_graph.extension.getSpaceDistributedStartByBottom
import com.abhijith.miui_cylinder_graph.extension.getSpaceDistributedStartByBottomWithGap
import com.abhijith.miui_cylinder_graph.model.SectionData
import com.abhijith.miui_cylinder_graph.model.SectionDataWrapper
import com.abhijith.miui_cylinder_graph.shape.CylinderDrawable
import com.abhijith.miui_cylinder_graph.util.ViewHelper

interface CylinderCallBack {
    fun onSelectionStarted()
    fun onSelection(x: Float, y: Float, sectionData: SectionData)
    fun onSelectionCleared()
}

class CylinderGraphView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes), ViewHelper {

    var cylinderCallBack: CylinderCallBack? = null
    private val cylinder = CylinderDrawable()
    private var selectedWrapper: SectionDataWrapper? = null
    private var sectionListWithWrapper: List<SectionDataWrapper> = listOf()
    private var space = 0
    private var isInSelectionMode = false

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN-> {
                cylinderCallBack?.onSelectionStarted()
                isInSelectionMode = true
                detectSelectedRect(event)
            }
            MotionEvent.ACTION_MOVE -> {
                detectSelectedRect(event)
            }
            MotionEvent.ACTION_UP -> {
                isInSelectionMode = false
                selectedWrapper = null
                cylinderCallBack?.onSelectionCleared()
                invalidate()
            }
        }
        return true
    }

    private fun detectSelectedRect(event: MotionEvent) {
        sectionListWithWrapper.onEach {

            if (
                canvasBound.contains(canvasBound.centerX(), event.y.toInt()) &&
                it.rect.contains(it.rect.centerX(), (event.y).toInt())
                && selectedWrapper != it
            ) {
                cylinderCallBack?.onSelection(
                    it.rect.centerX().toFloat(),
                    it.rect.centerY() - 160f,
                    it.data
                )
                selectedWrapper = it
                invalidate()
            }
        }
    }

    init {

//        setData(DummyData.items)
    }

    private fun distributeRectEvenly() {
        canvasBound.getSpaceDistributedStartByBottomWithGap(
            this,
            sectionListWithWrapper.map {
                it.data
            },
            space
        ) { index, rect, _ ->
            distributeSpace(rect, index)
        }
        invalidate()
    }

    fun setData(list: List<SectionData>) {
        sectionListWithWrapper = list.map {
            SectionDataWrapper(it)
        }
        distributeRectEvenly()
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
        canvasBound.getSpaceDistributedStartByBottom(
            this,
            sectionListWithWrapper.map {
                it.data
            }
        ) { index, rect, _ ->
            distributeSpace(rect, index)
        }
    }

    private fun distributeSpace(rect: Rect, index: Int) {
        sectionListWithWrapper[index].rect.set(rect)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (!isInSelectionMode) {
            sectionListWithWrapper.forEachIndexed { _, sectionDataWrapper ->
                cylinder.invalidate(newBound = sectionDataWrapper.rect)
                cylinder.isEnabled = true
                cylinder.color = ContextCompat.getColor(context, sectionDataWrapper.data.color)
                cylinder.draw(canvas)
            }
        } else {
            sectionListWithWrapper.forEachIndexed { _, sectionDataWrapper ->
                if (selectedWrapper === sectionDataWrapper) {
                    cylinder.isEnabled = true
                    cylinder.invalidate(newBound = sectionDataWrapper.rect)
                    cylinder.color = ContextCompat.getColor(context, sectionDataWrapper.data.color)
                    cylinder.draw(canvas)
                } else {
                    cylinder.isEnabled = false
                    cylinder.invalidate(newBound = sectionDataWrapper.rect)
                    cylinder.color = ContextCompat.getColor(context, sectionDataWrapper.data.color)
                    cylinder.draw(canvas)
                }
            }
        }
    }

    private val valueAnimator = ValueAnimator()
    fun animateStack() {
        valueAnimator.apply {
            setFloatValues(1000f, 0f)
            duration = 1500
            addUpdateListener {
                space = (it.animatedValue as Float).toInt()
                distributeRectEvenly()
            }
        }.start()
    }

    override val tempBound: Rect = Rect()
    override val paint: Paint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = 10f
        color = Color.BLACK
    }
    override val canvasBound: Rect = Rect()
}
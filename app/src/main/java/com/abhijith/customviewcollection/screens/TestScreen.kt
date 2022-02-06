package com.abhijith.customviewcollection.screens

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.abhijith.miui_cylinder_graph.data.DummyData
import com.abhijith.miui_cylinder_graph.extension.getSpaceDistributedStartByBottom
import com.abhijith.miui_cylinder_graph.extension.getSpaceDistributedStartByBottomWithGap
import com.abhijith.miui_cylinder_graph.model.SectionDataWrapper
import com.abhijith.miui_cylinder_graph.util.ViewHelper

@Composable
fun TestScreen() {
    AndroidView(
        factory = {
            RectangleTes(it)
        },
        modifier = Modifier
            .padding(vertical = 130.dp)
            .width(130.dp)
    )
}

//@SuppressLint("Recycle")
class RectangleTes @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes), ViewHelper {

    init {
        ValueAnimator().apply {
            setFloatValues(500f, 0f)
            duration = 500
            addUpdateListener {
                x = (it.animatedValue as Float).toInt()
                distributeRectEvenly()
            }
            setOnClickListener {
                start()
            }
        }
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas ?: return
        sectionListWithWrapper.forEach {
            canvas.drawRect(it.rect, paint.apply {
                color = ContextCompat.getColor(context, it.data.color)
                alpha = 255
            })
        }
        canvas.drawRect(canvasBound, paint.apply {
            color = Color.GREEN
            alpha = 50
        })
    }

    private var sectionListWithWrapper: List<SectionDataWrapper> = DummyData.items.map {
        SectionDataWrapper(data = it)
    }

    private var leftSpace = 0

    private fun calculateIndividualSectionData() {
        leftSpace = canvasBound.getSpaceDistributedStartByBottom(
            this,
            sectionListWithWrapper.map {
                it.data
            }
        ) { index, rect, _ ->
            sectionListWithWrapper[index].rect.set(rect)
        }
        leftSpace /= sectionListWithWrapper.size
    }


    var x = 0
    private fun distributeRectEvenly() {
        if (leftSpace >= 0) {
            canvasBound.getSpaceDistributedStartByBottomWithGap(
                this,
                sectionListWithWrapper.map {
                    it.data
                },
                x
            ) { index, rect, _ ->
                sectionListWithWrapper[index].rect.set(rect)
            }
        }
        invalidate()
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

    override val tempBound: Rect = Rect()

    override val paint: Paint = Paint().apply {
        color = Color.RED
        strokeWidth = 10f
        style = Paint.Style.FILL
    }

    override val canvasBound: Rect = Rect()

}




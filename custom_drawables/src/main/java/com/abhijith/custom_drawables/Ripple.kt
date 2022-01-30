package com.abhijith.custom_drawables

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColorInt

/*

1. Create cylinder shape
    combination of rectangle and oval


2. Create curvy rectangle

3. Base alpha pole which will contain all cylinder

4. stacking of cylinder basic

5. collecting data regarding plotting

6. adjust height according to data

7. animate things

* * */

const val ARC_VALUE = 110f

object X {
    val paint = Paint().apply {
    }
}

data class Padding(
    val top: Int = 0,
    val start: Int = 0,
    val bottom: Int = 0,
    val end: Int = 0
)

data class Space(val mine: Int)
data class Boundary(val max: Int)
class SpaceManager() {
    val bound = RectF()
    fun distributeHeight(
        space: Space,
        boundary: Boundary,
        callBack: (RectF) -> Unit
    ) {

    }
}


class CylinderDrawable : Drawable() {

    @ColorInt
    var color = Color.RED

    //paints
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


//        ovalBound.set(start, bounds.top.toFloat(), end, (bounds.top.toFloat() + (delta * 4)).toFloat(),)
        ovalBound.set(start, bounds.top.toFloat(), end, (ovalMidY * 2).toFloat(),)
    }

    override fun draw(canvas: Canvas) {
        canvas.drawPath(myPath, rectanglePaint.apply {
            color = this@CylinderDrawable.color
        })
        canvas.drawOval(ovalBound, ovalPaint)
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

class CurvyRectangleDrawable : Drawable() {

    var color = Color.BLUE

    //spacing
    /*private val padding = Padding(
        start = 0,
        top = 0,
        end = 0,
        bottom = 0
    )*/

    //paints
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
        //1
        path.moveTo(left, top)
        //2
        path.lineTo(left, bottom - ARC_VALUE)
        //3
        path.arcTo(RectF(left, bottom - ARC_VALUE, right, bottom).apply {
            drawBorder(canvas, rectanglePaint)
        }, 180f, -180f)
        //4
        path.lineTo(right, top)
        //5
        path.arcTo(RectF(left, top, right, top + ARC_VALUE).apply {
            drawBorder(canvas, rectanglePaint)
        }, 0f, 180f)
        canvas.drawPath(path, rectanglePaint.apply {
            color = this@CurvyRectangleDrawable.color
            alpha = 255
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
                cylinderDrawable.color = ContextCompat.getColor(context,sectionData.color)
                cylinderDrawable.draw(canvas)
            } else {
                curvyRectangleDrawable.bounds = rect
                curvyRectangleDrawable.reset()
                curvyRectangleDrawable.color = ContextCompat.getColor(context,sectionData.color)
                curvyRectangleDrawable.draw(canvas)
            }
        }
    }

    override val tempBound: Rect = Rect()
    override val paint: Paint = Paint()
    override val canvasBound: Rect = Rect()
}

fun RectF.drawBorder(canvas: Canvas, paint: Paint, color: Int = Color.WHITE) {
    return
    val holdColor = paint.color
    paint.color = color
//    val fl = this.width() * this.height()
//    paint.textSize = 52f
//    canvas.drawText(fl.toString(), centerX(), centerY(), paint)
//    Log.e("SizeOfRect","$fl")
    canvas.drawRect(this, paint)
    paint.color = holdColor
}
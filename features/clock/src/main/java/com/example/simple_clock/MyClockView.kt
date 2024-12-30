package com.example.simple_clock;

import android.content.Context;
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet;
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColorInt

import kotlin.jvm.JvmOverloads;
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

class MyClockView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {
    private data class ViewMetadata(
        val width: Int,
        val height: Int,
        val centerX: Float,
        val centerY: Float,
    )


    private fun Int.getColor() = ContextCompat.getColor(context, this)

    private val outerFrameColor: Int

    private val centerCircleColor: Int
    private val circleBehindNumberColor: Int

    private var hourHandColor: Int
    private val minuteHandColor: Int
    private val secondHandColor: Int
    private val textColor: Int

    private var minuteHandRadius: Float = 0F
    private var hourHandRadius: Float = 0F
    private var secondHandRadius: Float = 0F

    init {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.MyClockView)
        centerCircleColor = ta.getColor(
            R.styleable.MyClockView_mcv_centerCircleColor,
            R.color.wild_watermelon.getColor()
        )
        circleBehindNumberColor = ta.getColor(
            R.styleable.MyClockView_mcv_circleBehindNumberColor,
            R.color.lochinvar.getColor()
        )
        hourHandColor =
            ta.getColor(R.styleable.MyClockView_mcv_hourHandColor, R.color.melon.getColor())
        minuteHandColor =
            ta.getColor(R.styleable.MyClockView_mcv_hourHandColor, R.color.melon.getColor())
        secondHandColor =
            ta.getColor(R.styleable.MyClockView_mcv_secondHandColor, R.color.melon.getColor())
        textColor = ta.getColor(R.styleable.MyClockView_mcv_textColor, Color.WHITE)
        outerFrameColor = ta.getColor(R.styleable.MyClockView_mcv_outerFrameColor, Color.WHITE)
        ta.recycle()
    }

    private val basePaint = Paint().apply {
        isAntiAlias = true
    }

    private val framePaint: Paint
        get() = basePaint.apply {
            reset()
            color = "#94E4C9".toColorInt()
            style = Paint.Style.STROKE
            strokeWidth = 85f
            strokeCap = Paint.Cap.ROUND
        }

    private val hourCirclePaint: Paint
        get() = basePaint.apply {
            reset()
            color = circleBehindNumberColor
            style = Paint.Style.FILL
        }

    private val numberPaint: Paint
        get() = basePaint.apply {
            color = textColor
            style = Paint.Style.STROKE
            strokeWidth = 4f
            textSize = 40f
            strokeCap = Paint.Cap.ROUND
        }

    private val filledCirclePaint
        get() = basePaint.apply {
            reset()
            color = centerCircleColor
            strokeWidth = 80f
            style = Paint.Style.FILL
            strokeCap = Paint.Cap.ROUND
        }

    private val hourHandPaint
        get() = basePaint.apply {
            reset()
            color = hourHandColor
            strokeWidth = 10f
            style = Paint.Style.FILL
            strokeCap = Paint.Cap.ROUND
        }

    private val minuteHandPaint
        get() = basePaint.apply {
            reset()
            color = minuteHandColor
            strokeWidth = 10f
            style = Paint.Style.FILL
            strokeCap = Paint.Cap.ROUND
        }

    private val secondHandPaint
        get() = basePaint.apply {
            reset()
            color = secondHandColor
            strokeWidth = 10f
            style = Paint.Style.FILL
            strokeCap = Paint.Cap.ROUND
        }

    private var radius: Float = 0f
    private lateinit var viewMetadata: ViewMetadata
    private val bound: Rect = Rect()
    private val numBound: Rect = Rect()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (this::viewMetadata.isInitialized) {
            canvas.apply {
                val time = ClockHelper.time
                getClipBounds(bound)
                drawFrame()
                drawClockNumbers(time)
                drawHourHands(time)
                drawMinutesHand(time)
                drawSecondHand(time)
                drawCenterCircle()
            }
        }
        postInvalidateDelayed(500)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val mWidth = width - (paddingStart + paddingEnd)
        val mHeight = height - (paddingTop + paddingBottom)
        val radius = min(mWidth, mHeight) / 2
        this.radius = radius.toFloat()
        viewMetadata = ViewMetadata(
            width = mWidth,
            height = mHeight,
            centerX = (width / 2).toFloat(),
            centerY = (height / 2).toFloat(),

            )
        minuteHandRadius = (this.radius * 0.7).toFloat()
        secondHandRadius = (this.radius * 0.8).toFloat()
        hourHandRadius = (this.radius * 0.6).toFloat()

    }

    private fun Canvas.drawFrame() {
        drawCircle(
            viewMetadata.centerX,
            viewMetadata.centerY,
            radius,
            framePaint
        )
    }

    private fun Canvas.drawClockNumbers(time: Time) {
        //circle = 2*PI
        //we need to plot circle into 12 parts hence 12 hours
        ClockHelper.hours.forEach { number ->
            val num = number.toString()
            numberPaint.getTextBounds(num, 0, num.length, numBound)
            val angle = (PI / 6) * (number - 3)

            val x = viewMetadata.centerX + (cos(angle) * radius)
            val y = viewMetadata.centerY + (sin(angle) * radius)
            drawCircle(
                x.toFloat(),
                y.toFloat(),
                35f,
                hourCirclePaint
            )
            drawText(
                num,
                x.toFloat() - (numBound.width() / 2),
                y.toFloat() + (numBound.height() / 2),
                numberPaint
            )
        }
    }

    private fun Canvas.drawHourHands(time: Time) {
        var angle = ((PI / 6) * (time.hour - 3))
        val minuteAngleWithin30 = (time.minute / 60) * 0.5 // 0.5 => ( 30=>(PI/6) /60 ) /2
        angle += minuteAngleWithin30
        val x = viewMetadata.centerX + (cos(angle) * (hourHandRadius))
        val y = viewMetadata.centerY + (sin(angle) * (hourHandRadius))
        drawLine(
            viewMetadata.centerX,
            viewMetadata.centerY,
            x.toFloat(),
            y.toFloat(),
            hourHandPaint
        )
    }

    private fun Canvas.drawMinutesHand(time: Time) {
        val angle = ((PI / 30) * (time.minute - 15));
        val x = viewMetadata.centerX + (cos(angle) * (minuteHandRadius))
        val y = viewMetadata.centerY + (sin(angle) * (minuteHandRadius))
        drawLine(
            viewMetadata.centerX,
            viewMetadata.centerY,
            x.toFloat(),
            y.toFloat(),
            minuteHandPaint
        )
    }

    private fun Canvas.drawSecondHand(time: Time) {
        val angle = ((PI / 30) * (time.second - 15));
        val x = viewMetadata.centerX + (cos(angle) * (secondHandRadius))
        val y = viewMetadata.centerY + (sin(angle) * (secondHandRadius))
        drawLine(
            viewMetadata.centerX,
            viewMetadata.centerY,
            x.toFloat(),
            y.toFloat(),
            secondHandPaint
        )
    }

    private fun Canvas.drawCenterCircle() {
        drawCircle(
            viewMetadata.centerX,
            viewMetadata.centerY,
            25f,
            filledCirclePaint
        )
    }
}

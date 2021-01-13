package com.example.pomodorotimer

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import java.util.*
import kotlin.concurrent.fixedRateTimer

class TimerView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private var seconds = 3
    private val arcStep = 360f / seconds
    private var timer: Timer? = null
    private val rect = RectF()
    private val timerPaint = Paint().apply {
        color = Color.DKGRAY
        isAntiAlias = true
        textSize = 50f
        textAlign = Paint.Align.CENTER
    }
    private val arcPaint = Paint().apply {
        color = Color.RED
        isAntiAlias = true
    }

    fun reset() {
        timer?.cancel()
        seconds = 3
        post { invalidate() }
    }

    fun start() {
        reset()
        timer = fixedRateTimer(initialDelay = 1000, period = 1000) {
            seconds--
            post { invalidate() }
            if (seconds == 0) cancel()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        rect.set(0f, 0f, width.toFloat(), height.toFloat())
        canvas.drawArc(rect, 270f, arcStep * (3 - seconds), true, arcPaint)
        canvas.drawText("00:0$seconds", width / 2f, height / 2f, timerPaint)
    }
}
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
import com.example.pomodorotimer.TimerState.*

class TimerView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private var state = WAITING
    private var rounds = 5
    private var currSeconds = state.seconds
    private var timer: Timer? = null
    private val rect = RectF()

    private val timerPaint = Paint().apply {
        color = Color.DKGRAY
        isAntiAlias = true
        textSize = 50f
        textAlign = Paint.Align.CENTER
    }
    private val arcPaint = Paint().apply {
        color = state.color
        isAntiAlias = true
    }

    private fun startTimer() {
        if (rounds == 0) {
            changeState(FINISHED)
        } else {
            changeState(if (rounds-- % 2 == 1) WORKING else RESTING)

            timer = fixedRateTimer(initialDelay = 1000, period = 1000) {
                currSeconds--
                post { invalidate() }
                if (currSeconds == 0) startTimer()
            }
        }
    }

    private fun changeState(state: TimerState) {
        timer?.cancel()
        this.state = state
        currSeconds = state.seconds
        arcPaint.color = state.color
        post { invalidate() }
    }

    fun start() {
        rounds = 5
        startTimer()
    }

    fun reset() = changeState(WAITING)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        rect.set(0f, 0f, width.toFloat(), height.toFloat())
        val arcSweepAngle = when (state) {
            WAITING, FINISHED -> state.arcStep
            WORKING, RESTING -> state.arcStep * (state.seconds - currSeconds)
        }

        canvas.drawArc(rect, 270f, arcSweepAngle, true, arcPaint)
        canvas.drawText("00:0$currSeconds", width / 2f, height / 2f, timerPaint)
    }
}
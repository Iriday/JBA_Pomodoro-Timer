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

class TimerView(context: Context, attrs: AttributeSet?) : View(context, attrs), SettingsFragment.PositiveBtn {
    private var state = WAITING
    private val defaultRounds = 5
    private var rounds = defaultRounds
    private var workTime = 1800 // 30 min
    private var restTime = 300 // 5 min
    private var currSeconds = workTime
    private var timer: Timer? = null
    private val rect = RectF()
    private var arcStep = 360f / workTime
    private val arcWidth = 30f

    private val timerPaint = Paint().apply {
        color = Color.DKGRAY
        isAntiAlias = true
        textSize = 40f
        textAlign = Paint.Align.CENTER
    }
    private val arcPaint = Paint().apply {
        color = state.color
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeWidth = arcWidth
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
        currSeconds = when (state) {
            WAITING, WORKING -> workTime
            RESTING -> restTime
            FINISHED -> 0
        }
        arcStep = when (state) {
            WAITING -> 0f
            WORKING, RESTING -> 360f / currSeconds
            FINISHED -> 360f
        }
        arcPaint.color = state.color

        post { invalidate() }
    }

    fun start() {
        rounds = defaultRounds
        startTimer()
    }

    fun reset() = changeState(WAITING)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        rect.set(arcWidth, arcWidth, width.toFloat() - arcWidth, height.toFloat() - arcWidth)
        val arcSweepAngle = when (state) {
            WAITING, FINISHED -> arcStep
            WORKING -> arcStep * (workTime - currSeconds)
            RESTING -> arcStep * (restTime - currSeconds)
        }

        canvas.drawArc(rect, 270f, arcSweepAngle, false, arcPaint)
        canvas.drawText(currSeconds.toTime(), width / 2f, height / 2f, timerPaint)
    }

    override fun clicked(workTime: Int?, restTime: Int?) {
        workTime?.let { this.workTime = it }
        restTime?.let { this.restTime = it }
        changeState(WAITING)
    }
}

fun Int.toTime() : String = StringBuilder()
    .append((this / 3600).toString().padStart(2, '0')).append(":")
    .append((this % 3600 / 60).toString().padStart(2, '0')).append(":")
    .append((this % 60).toString().padStart(2, '0')).toString()
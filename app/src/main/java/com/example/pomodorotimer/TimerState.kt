package com.example.pomodorotimer

import android.graphics.Color

enum class TimerState(val color: Int, val seconds: Int = 0, val arcStep: Float = 360f / seconds) {
    WAITING(Color.WHITE, arcStep = 0f, seconds = 3),
    WORKING(Color.RED, seconds = 3),
    RESTING(Color.GREEN, seconds = 2),
    FINISHED(Color.YELLOW, arcStep = 360f)
}
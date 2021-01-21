package com.example.pomodorotimer

import android.graphics.Color

enum class TimerState(val color: Int) {
    WAITING(Color.WHITE),
    WORKING(Color.RED),
    RESTING(Color.GREEN),
    FINISHED(Color.YELLOW)
}
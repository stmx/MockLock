package com.stmx.mocklock.data

import com.stmx.mocklock.data.entity.GeoPoint
import kotlinx.coroutines.delay
import kotlin.random.Random
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class Track(
    private val polyline: Polyline,
    speed: Double,
) {
    private val totalTime: Long =
        (MILLIS_IN_SEC * polyline.totalLength / speed / KM_H_TO_M_S).toLong()
    private var startTime: Long = 0L

    fun start(
        minUpdateInterval: Long = DEFAULT_MIN_UPDATE_INTERVAL,
        maxUpdateInterval: Long = DEFAULT_MAX_UPDATE_INTERVAL
    ): Flow<Position> = flow {
        startTime = System.currentTimeMillis()
        var time = 0L
        while (time <= totalTime) {
            time = System.currentTimeMillis() - startTime
            val position = calculatePosition(time)
            val point = polyline.getPointAtPosition(position)
            emit(Position(position, point))
            delay(Random.nextLong(minUpdateInterval, maxUpdateInterval))
        }
    }

    private fun calculatePosition(currentTime: Long): Double {
        val part = (currentTime.toDouble() / totalTime.toDouble())
        val position = when {
            part > MAX_TIME_RATIO -> MAX_TIME_RATIO
            part < MIN_TIME_RATIO -> MIN_TIME_RATIO
            else -> part
        }
        return position
    }

    companion object {
        private const val DEFAULT_MIN_UPDATE_INTERVAL = 800L
        private const val DEFAULT_MAX_UPDATE_INTERVAL = 1500L
        private const val MILLIS_IN_SEC = 1000
        private const val MIN_TIME_RATIO = 0.0
        private const val MAX_TIME_RATIO = 1.0
        private const val KM_H_TO_M_S = 0.27777777
    }

    class Position(val value: Double, val point: GeoPoint)
}
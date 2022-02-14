package com.stmx.mocklock.domain

import com.stmx.mocklock.domain.entity.GeoPoint
import com.stmx.mocklock.domain.entity.calculator.PositionCalculator
import com.stmx.mocklock.domain.entity.calculator.TimeCalculator
import kotlin.random.Random
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


interface Track {
    fun setMaxUpdateInterval(maxUpdateInterval: Long): Track
    fun setMinUpdateInterval(minUpdateInterval: Long): Track
    fun setConfiguration(configuration: Configuration)

    class Polyline(
        private val positionCalculator: PositionCalculator,
        private val timeCalculator: TimeCalculator,
    ) : Track {
        private var _configuration: Configuration? = null
        private val configuration = checkNotNull(_configuration)
        private val points = configuration.points
        private val speed = configuration.speed
        private var minUpdateInterval: Long = DEFAULT_MIN_UPDATE_INTERVAL
        private var maxUpdateInterval: Long = DEFAULT_MAX_UPDATE_INTERVAL
        private var totalTime: Double = timeCalculator.calculate(points, speed)

        override fun setMaxUpdateInterval(maxUpdateInterval: Long): Track {
            this.maxUpdateInterval = maxUpdateInterval
            return this
        }

        override fun setMinUpdateInterval(minUpdateInterval: Long): Track {
            this.minUpdateInterval = minUpdateInterval
            return this
        }

        override fun setConfiguration(configuration: Configuration) {
            this._configuration = configuration
        }

        companion object {
            private const val DEFAULT_MIN_UPDATE_INTERVAL: Long = 800L
            private const val DEFAULT_MAX_UPDATE_INTERVAL: Long = 1500L
        }
    }

    class Configuration(val points: List<GeoPoint>, val speed: Double)
}

class TrackImpl(
    private val points: List<GeoPoint>,
    private val positionCalculator: PositionCalculator,
    timeCalculator: TimeCalculator,
    speed: Double,
) {
    private val totalTime: Long = timeCalculator.calculate(points, speed).toLong()
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
            val point = positionCalculator.calculate(points, position)
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
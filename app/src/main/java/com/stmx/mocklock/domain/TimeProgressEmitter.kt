package com.stmx.mocklock.domain

import com.stmx.mocklock.domain.entity.TrackConfiguration
import com.stmx.mocklock.domain.entity.calculator.TimeCalculator
import javax.inject.Inject
import javax.inject.Named
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface TimeProgressEmitter {

    fun start(configuration: TrackConfiguration): Flow<Double>

    class Linear @Inject constructor(
        private val timeCalculator: TimeCalculator,
        @Named("DEFAULT_MIN_PERIOD") private val minimalPeriod: Long
    ) : TimeProgressEmitter {
        override fun start(configuration: TrackConfiguration): Flow<Double> = flow {
            val totalTime = timeCalculator.calculate(configuration.points, configuration.speed)
            val period = minimalPeriod.coerceAtMost(totalTime)
            val startTime = System.currentTimeMillis()
            var currentTime = 0L
            while (currentTime <= totalTime) {
                currentTime = System.currentTimeMillis() - startTime
                val value = calculateProgress(currentTime, totalTime)
                emit(value)
                delay(period)
            }
        }

        private fun calculateProgress(currentTime: Long, totalTime: Long): Double {
            if (totalTime == 0L) return 1.0
            return (currentTime.toDouble() / totalTime.toDouble())
                .coerceAtLeast(0.0)
                .coerceAtMost(1.0)
        }

        companion object {
            const val DEFAULT_MIN_PERIOD: Long = 1000L
        }
    }
}
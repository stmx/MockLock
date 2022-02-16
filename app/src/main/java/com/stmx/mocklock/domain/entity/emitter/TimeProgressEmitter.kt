package com.stmx.mocklock.domain.entity.emitter

import com.stmx.mocklock.domain.entity.TrackConfiguration
import com.stmx.mocklock.domain.entity.calculator.TimeCalculator
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface TimeProgressEmitter {

    fun start(configuration: TrackConfiguration): Flow<Double>
    fun setMinimalPeriodUpdate(minimalPeriod: Long)

    class Linear @Inject constructor(
        private val timeCalculator: TimeCalculator,
    ) : TimeProgressEmitter {

        private var minimalPeriod: Long = DEFAULT_MIN_PERIOD

        override fun start(configuration: TrackConfiguration): Flow<Double> = flow {
            val totalTime =
                timeCalculator.calculate(configuration.points, configuration.speed) * MS_IN_S
            val period = minimalPeriod.coerceAtMost(totalTime)
            val startTime = System.currentTimeMillis()
            var currentTime = 0L
            while (currentTime <= totalTime) {
                currentTime = System.currentTimeMillis() - startTime
                val progress = calculateProgress(currentTime, totalTime)
                emit(progress)
                delay(period)
            }
        }

        override fun setMinimalPeriodUpdate(minimalPeriod: Long) {
            this.minimalPeriod = minimalPeriod
        }

        private fun calculateProgress(currentTime: Long, totalTime: Long): Double {
            if (totalTime == 0L) return 1.0
            return (currentTime.toDouble() / totalTime.toDouble())
                .coerceAtLeast(0.0)
                .coerceAtMost(1.0)
        }

        companion object {
            private const val DEFAULT_MIN_PERIOD: Long = 1000L
            private const val MS_IN_S: Long = 1000L
        }
    }
}

package com.stmx.mocklock.domain.entity.calculator

import com.stmx.mocklock.domain.entity.GeoPoint
import com.stmx.mocklock.domain.entity.Speed
import javax.inject.Inject

interface TimeCalculator {
    fun calculate(points: List<GeoPoint>, speed: Speed): Long

    class Polyline @Inject constructor(
        private val distanceCalculator: DistanceCalculator
    ) : TimeCalculator {
        override fun calculate(points: List<GeoPoint>, speed: Speed): Long {
            if (speed.ms == 0.0) return Long.MAX_VALUE
            val calculateTotal = distanceCalculator.calculateTotal(points)
            return (calculateTotal / speed.ms).toLong()
        }
    }
}

package com.stmx.mocklock.domain.entity.calculator

import com.stmx.mocklock.domain.entity.GeoPoint

interface TimeCalculator {
    fun calculate(points: List<GeoPoint>, speed: Double): Double

    class Polyline(
        private val distanceCalculator: DistanceCalculator
    ) : TimeCalculator {
        override fun calculate(points: List<GeoPoint>, speed: Double): Double {
            return distanceCalculator.calculateTotal(points) / speed
        }
    }
}
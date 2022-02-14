package com.stmx.mocklock.domain.entity.calculator

import com.stmx.mocklock.domain.entity.GeoPoint
import com.stmx.mocklock.domain.integrate

interface PositionCalculator {

    fun calculate(points: List<GeoPoint>, position: Double): GeoPoint

    class Polyline(
        private val distanceCalculator: DistanceCalculator,
        private val positionEquationCalculator: PositionEquationCalculator,
    ) : PositionCalculator {

        override fun calculate(points: List<GeoPoint>, position: Double): GeoPoint {
            val equations = positionEquationCalculator.calculate(points)
            val normalizedLengthDistribution = calculateNormalizedLengthDistribution(points)
            val index = normalizedLengthDistribution.indexOfFirst { it >= position }
            val equation = equations[index]
            val startPercent = if (index == 0) 0.0 else normalizedLengthDistribution[index - 1]
            val endPercent = normalizedLengthDistribution[index]
            val relativePercent = (position - startPercent) / (endPercent - startPercent)
            return equation.calculate(relativePercent)
        }

        private fun calculateNormalizedLengthDistribution(points: List<GeoPoint>): List<Double> {
            val totalDistance = distanceCalculator.calculateTotal(points)
            return distanceCalculator.calculate(points)
                .map { distance -> distance / totalDistance }
                .integrate()
        }

    }
}
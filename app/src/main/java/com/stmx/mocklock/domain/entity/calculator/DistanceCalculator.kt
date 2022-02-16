package com.stmx.mocklock.domain.entity.calculator

import com.stmx.mocklock.domain.entity.GeoPoint
import com.stmx.mocklock.domain.entity.equation.DistanceEquation
import javax.inject.Inject

interface DistanceCalculator {

    fun calculateTotal(points: List<GeoPoint>): Double
    fun calculate(points: List<GeoPoint>): List<Double>

    class Polyline @Inject constructor(
        private val distanceEquation: DistanceEquation
    ) : DistanceCalculator {

        override fun calculateTotal(points: List<GeoPoint>): Double {
            return points.foldIndexed(LENGTH_OFFSET) { index, acc, geoPoint ->
                val distance = if (index != 0) {
                    distanceEquation.calculate(points[index - 1], geoPoint)
                } else {
                    0.0
                }
                acc + distance
            }
        }

        override fun calculate(points: List<GeoPoint>): List<Double> {
            return points.mapIndexed { index, geoPoint ->
                val prevIndex = if (index == 0) index else index - 1
                distanceEquation.calculate(points[prevIndex], geoPoint)
            }
        }

        companion object {
            private const val LENGTH_OFFSET = 0.0
        }

    }
}
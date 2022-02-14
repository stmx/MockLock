package com.stmx.mocklock.domain.entity.calculator

import com.stmx.mocklock.domain.entity.equation.DistanceEquation
import com.stmx.mocklock.domain.entity.GeoPoint

interface DistanceCalculator {

    val distanceEquation: DistanceEquation

    fun calculateTotal(points: List<GeoPoint>): Double
    fun calculate(points: List<GeoPoint>): List<Double>

    class Polyline(override val distanceEquation: DistanceEquation) : DistanceCalculator {

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
            val list = mutableListOf<Double>()
            if (points.size < 2) return list
            for (index in 0..points.size - 2) {
                list.add(distanceEquation.calculate(points[index], points[index + 1]))
            }
            return list
        }

        companion object {
            private const val LENGTH_OFFSET = 0.0
        }

    }
}
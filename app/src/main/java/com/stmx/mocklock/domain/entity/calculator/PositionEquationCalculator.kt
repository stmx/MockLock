package com.stmx.mocklock.domain.entity.calculator

import com.stmx.mocklock.domain.entity.GeoPoint
import com.stmx.mocklock.domain.entity.equation.PositionEquation
import javax.inject.Inject

interface PositionEquationCalculator {

    fun calculate(start: GeoPoint, end: GeoPoint): PositionEquation
    fun calculate(points: List<GeoPoint>): List<PositionEquation>

    class Parametric @Inject constructor() : PositionEquationCalculator {

        override fun calculate(start: GeoPoint, end: GeoPoint): PositionEquation {
            val kx = end.latitude - start.latitude
            val ky = end.longitude - start.longitude
            val bx = start.latitude
            val by = start.longitude
            return PositionEquation.Parametric(kx, ky, bx, by)
        }

        override fun calculate(points: List<GeoPoint>): List<PositionEquation> {
            return points.mapIndexed { index, geoPoint ->
                val prevIndex = if (index == 0) index else index - 1
                calculate(points[prevIndex], geoPoint)
            }
        }
    }
}
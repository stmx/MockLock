package com.stmx.mocklock.domain.entity.calculator

import com.stmx.mocklock.domain.entity.GeoPoint
import com.stmx.mocklock.domain.entity.equation.PositionEquation

interface PositionEquationCalculator {

    fun calculate(start: GeoPoint, end: GeoPoint): PositionEquation
    fun calculate(points: List<GeoPoint>): List<PositionEquation>

    class Parametric : PositionEquationCalculator {

        override fun calculate(start: GeoPoint, end: GeoPoint): PositionEquation {
            val kx = end.latitude - start.latitude
            val ky = end.longitude - start.longitude
            val bx = start.latitude
            val by = start.longitude
            return PositionEquation.Parametric(kx, ky, bx, by)
        }

        override fun calculate(points: List<GeoPoint>): List<PositionEquation> {
            if (points.isEmpty()) return emptyList()
            if (points.size == 1) return listOf(calculate(points[0], points[0]))
            val list = mutableListOf<PositionEquation>()
            for (index in 0..points.size - 2) {
                list.add(calculate(points[index], points[index + 1]))
            }
            return list
        }
    }
}
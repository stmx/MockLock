package com.stmx.mocklock.domain.entity.calculator

import com.stmx.mocklock.domain.entity.GeoPoint
import com.stmx.mocklock.domain.entity.equation.PositionEquation

interface PositionEquationCalculator {

    fun calculate(points: List<GeoPoint>): List<PositionEquation>

    class Parametric : PositionEquationCalculator {
        override fun calculate(points: List<GeoPoint>): List<PositionEquation> {
            val list = mutableListOf<PositionEquation>()
            if (points.size < 2) return list
            for (index in 0..points.size - 2) {
                val kx = points[index + 1].latitude - points[index].latitude
                val ky = points[index + 1].longitude - points[index].longitude
                val bx = points[index].latitude
                val by = points[index].longitude
                list.add(PositionEquation.Parametric(kx, ky, bx, by))
            }
            return list
        }
    }
}
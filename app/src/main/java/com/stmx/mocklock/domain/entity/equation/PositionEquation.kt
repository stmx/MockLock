package com.stmx.mocklock.domain.entity.equation

import com.stmx.mocklock.domain.entity.GeoPoint

interface PositionEquation {

    fun calculate(t: Double): GeoPoint

    class Parametric(
        private val kx: Double,
        private val ky: Double,
        private val bx: Double,
        private val by: Double,
    ) : PositionEquation {
        override fun calculate(t: Double): GeoPoint {
            val x = kx * t + bx
            val y = ky * t + by
            return GeoPoint(x, y)
        }
    }
}

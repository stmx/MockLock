package com.stmx.mocklock.data.entity

class ParametricEquation(
    private val kx: Double,
    private val ky: Double,
    private val bx: Double,
    private val by: Double,
) {
    fun calculate(t: Double): GeoPoint {
        val x = kx * t + bx
        val y = ky * t + by
        return GeoPoint(x, y)
    }
}
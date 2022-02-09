package com.stmx.mocklock.data

data class ParametricEquation(
    private val kx: Double,
    private val ky: Double,
    private val bx: Double,
    private val by: Double,
) {
    fun calculate(t: Double): Point {
        val x = kx * t + bx
        val y = ky * t + by
        return Point(x, y)
    }
}
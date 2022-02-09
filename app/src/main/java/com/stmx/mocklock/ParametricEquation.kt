package com.stmx.mocklock

data class ParametricEquation(
    private val kx: Float,
    private val ky: Float,
    private val bx: Float,
    private val by: Float,
) {
    fun calculate(t: Float): Point {
        val x = kx * t + bx
        val y = ky * t + by
        return Point(x, y)
    }
}
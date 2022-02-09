package com.stmx.mocklock

class Polyline(private val points: List<Point>) {

    private val lengthDistribution: List<Float> = calculateLengthDistribution()
    val totalLength: Float = lengthDistribution.last()
    private val normalizeLengthDistribution: List<Float> = lengthDistribution.map { it / totalLength }
    private val parametricEquationDistribution: List<ParametricEquation> =
        calculateParametricEquationDistribution()

    fun getPointAtPercent(percent: Float): Point {
        val index = normalizeLengthDistribution.indexOfFirst { it >= percent }
        val equation = parametricEquationDistribution[index]
        val startPercent = if (index == 0) 0.0f else normalizeLengthDistribution[index - 1]
        val endPercent = normalizeLengthDistribution[index]
        val relativePercent = (percent - startPercent) / (endPercent - startPercent)
        return equation.calculate(relativePercent)
    }

    private fun calculateLengthDistribution(): List<Float> {
        val list = mutableListOf<Float>()
        if (points.size < 2) return list
        for (index in 0..points.size - 2) {
            val length = points[index] lengthTo points[index + 1]
            list.add((list.lastOrNull() ?: 0.0f) + length)
        }
        return list
    }

    private fun calculateParametricEquationDistribution(): List<ParametricEquation> {
        val list = mutableListOf<ParametricEquation>()
        if (points.size < 2) return list
        for (index in 0..points.size - 2) {
            val kx = points[index + 1].x - points[index].x
            val ky = points[index + 1].y - points[index].y
            val bx = points[index].x
            val by = points[index].y
            list.add(ParametricEquation(kx, ky, bx, by))
        }
        return list
    }
}


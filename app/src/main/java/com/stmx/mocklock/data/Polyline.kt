package com.stmx.mocklock.data

class Polyline(private val points: Array<Point>) {

    private val lengthDistribution: List<Double> = calculateLengthDistribution()
    val totalLength: Double = lengthDistribution.last()
    private val normalizeLengthDistribution: List<Double> = lengthDistribution.map { it / totalLength }
    private val parametricEquationDistribution: List<ParametricEquation> =
        calculateParametricEquationDistribution()

    fun getPointAtPosition(position: Double): Point {
        val index = normalizeLengthDistribution.indexOfFirst { it >= position }
        val equation = parametricEquationDistribution[index]
        val startPercent = if (index == 0) 0.0 else normalizeLengthDistribution[index - 1]
        val endPercent = normalizeLengthDistribution[index]
        val relativePercent = (position - startPercent) / (endPercent - startPercent)
        return equation.calculate(relativePercent)
    }

    private fun calculateLengthDistribution(): List<Double> {
        val list = mutableListOf<Double>()
        if (points.size < 2) return list
        for (index in 0..points.size - 2) {
            val length = points[index] lengthTo points[index + 1]
            list.add((list.lastOrNull() ?: 0.0) + length)
        }
        return list
    }

    private fun calculateParametricEquationDistribution(): List<ParametricEquation> {
        val list = mutableListOf<ParametricEquation>()
        if (points.size < 2) return list
        for (index in 0..points.size - 2) {
            val kx = points[index + 1].latitude - points[index].latitude
            val ky = points[index + 1].longitude - points[index].longitude
            val bx = points[index].latitude
            val by = points[index].longitude
            list.add(ParametricEquation(kx, ky, bx, by))
        }
        return list
    }
}


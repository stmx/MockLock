package com.stmx.mocklock.data

import com.stmx.mocklock.data.entity.GeoPoint
import com.stmx.mocklock.data.entity.HaversineEquation
import com.stmx.mocklock.data.entity.ParametricEquation

class Polyline(private val geoPoints: Array<GeoPoint>) {

    private val haversineEquation: HaversineEquation = HaversineEquation()
    val totalLength: Double = calculateTotalLength()

    private val normalizeLengthDistribution: List<Double> =
        calculateLengthDistribution().map { it / totalLength }
    private val parametricEquationDistribution: List<ParametricEquation> =
        calculateParametricEquationDistribution()


    private fun calculateTotalLength(): Double {
        var length = 0.0
        if (geoPoints.size < 2) return length
        for (index in 0..geoPoints.size - 2) {
            length += haversineEquation.calculate(geoPoints[index], geoPoints[index + 1])
        }
        return length
    }

    fun getPointAtPosition(position: Double): GeoPoint {
        val index = normalizeLengthDistribution.indexOfFirst { it >= position }
        val equation = parametricEquationDistribution[index]
        val startPercent = if (index == 0) 0.0 else normalizeLengthDistribution[index - 1]
        val endPercent = normalizeLengthDistribution[index]
        val relativePercent = (position - startPercent) / (endPercent - startPercent)
        return equation.calculate(relativePercent)
    }

    private fun calculateLengthDistribution(): List<Double> {
        val list = mutableListOf<Double>()
        if (geoPoints.size < 2) return list
        for (index in 0..geoPoints.size - 2) {
            val length = haversineEquation.calculate(geoPoints[index], geoPoints[index + 1])
            list.add((list.lastOrNull() ?: 0.0) + length)
        }
        return list
    }

    private fun calculateParametricEquationDistribution(): List<ParametricEquation> {
        val list = mutableListOf<ParametricEquation>()
        if (geoPoints.size < 2) return list
        for (index in 0..geoPoints.size - 2) {
            val kx = geoPoints[index + 1].latitude - geoPoints[index].latitude
            val ky = geoPoints[index + 1].longitude - geoPoints[index].longitude
            val bx = geoPoints[index].latitude
            val by = geoPoints[index].longitude
            list.add(ParametricEquation(kx, ky, bx, by))
        }
        return list
    }
}


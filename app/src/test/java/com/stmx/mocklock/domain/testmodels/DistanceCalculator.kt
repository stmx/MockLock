package com.stmx.mocklock.domain.testmodels

import com.stmx.mocklock.domain.entity.GeoPoint
import com.stmx.mocklock.domain.entity.calculator.DistanceCalculator
import com.stmx.mocklock.domain.entity.equation.DistanceEquation

fun polylineDistanceCalculator(
    distanceEquation: DistanceEquation = haversineDistanceEquation()
): DistanceCalculator.Polyline {
    return DistanceCalculator.Polyline(distanceEquation)
}

fun distanceCalculatorStub(
    totalDistance: Double = DEFAULT_DISTANCE_STUB,
    distanceDistribution: List<Double> = DEFAULT_DISTANCE_DISTRIBUTION_STUB
): DistanceCalculator = object : DistanceCalculator {
    override fun calculateTotal(points: List<GeoPoint>): Double = totalDistance
    override fun calculate(points: List<GeoPoint>): List<Double> = distanceDistribution
}

private const val DEFAULT_DISTANCE_STUB: Double = 100.0
private val DEFAULT_DISTANCE_DISTRIBUTION_STUB: List<Double> = listOf(10.0, 20.0, 30.0, 40.0)
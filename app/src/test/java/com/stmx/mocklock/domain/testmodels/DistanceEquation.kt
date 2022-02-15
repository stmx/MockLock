package com.stmx.mocklock.domain.testmodels

import com.stmx.mocklock.domain.entity.GeoPoint
import com.stmx.mocklock.domain.entity.equation.DistanceEquation
import kotlin.math.sqrt

fun haversineDistanceEquation(): DistanceEquation = DistanceEquation.Haversine()

fun linearDistanceEquation(): DistanceEquation = object : DistanceEquation {
    override fun calculate(startPoint: GeoPoint, endPoint: GeoPoint): Double {
        val dLon = endPoint.longitude - startPoint.longitude
        val dLat = endPoint.latitude - startPoint.latitude
        return sqrt(dLon * dLon + dLat * dLat)
    }
}

fun distanceEquationStub(
    distance: Double = DEFAULT_DISTANCE_STUB
): DistanceEquation = object : DistanceEquation {
    override fun calculate(startPoint: GeoPoint, endPoint: GeoPoint): Double = distance
}

private const val DEFAULT_DISTANCE_STUB = 5.0
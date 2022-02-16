package com.stmx.mocklock.domain.testmodels

import com.stmx.mocklock.domain.entity.GeoPoint
import com.stmx.mocklock.domain.entity.Speed
import com.stmx.mocklock.domain.entity.calculator.DistanceCalculator
import com.stmx.mocklock.domain.entity.calculator.TimeCalculator

fun polylineTimeCalculator(
    distanceCalculator: DistanceCalculator = polylineDistanceCalculator()
): TimeCalculator = TimeCalculator.Polyline(distanceCalculator)

fun timeCalculatorStub(
    totalTime: Long = DEFAULT_TIME_STUB
): TimeCalculator = object : TimeCalculator {
    override fun calculate(points: List<GeoPoint>, speed: Speed): Long = totalTime
}

private const val DEFAULT_TIME_STUB: Long = 100L
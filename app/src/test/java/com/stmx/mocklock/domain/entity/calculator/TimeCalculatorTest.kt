package com.stmx.mocklock.domain.entity.calculator

import com.stmx.mocklock.domain.entity.Speed
import com.stmx.mocklock.domain.testmodels.distanceCalculatorStub
import com.stmx.mocklock.domain.testmodels.polylineTimeCalculator
import com.stmx.mocklock.domain.testmodels.randomGeoPointList
import kotlin.test.Test
import kotlin.test.assertEquals

class TimeCalculatorTest {

    @Test
    fun `calculate time with non empty point list and zero speed`() {
        val timeCalculator = polylineTimeCalculator(distanceCalculatorStub())
        val points = randomGeoPointList(from = 1)
        assertEquals(
            expected = Long.MAX_VALUE,
            actual = timeCalculator.calculate(points, Speed.Zero)
        )
    }

    @Test
    fun `calculate time with empty point list and non zero speed`() {
        val distanceCalculator = distanceCalculatorStub(totalDistance = 0.0)
        val timeCalculator = polylineTimeCalculator(distanceCalculator)
        assertEquals(
            expected = 0L,
            actual = timeCalculator.calculate(emptyList(), Speed.MS(100.0))
        )
    }

    @Test
    fun `calculate time with empty point list and zero speed`() {
        val distanceCalculator = distanceCalculatorStub(totalDistance = 0.0)
        val timeCalculator = polylineTimeCalculator(distanceCalculator)
        assertEquals(
            expected = Long.MAX_VALUE,
            actual = timeCalculator.calculate(emptyList(), Speed.Zero)
        )
    }

    @Test
    fun `calculate total time in m_s with non empty point list and non zero speed`() {
        val distance = 10.0 // in meters
        val speed = 10.0 // in meters per seconds
        val distanceCalculator = distanceCalculatorStub(totalDistance = distance)
        val timeCalculator = polylineTimeCalculator(distanceCalculator)
        assertEquals(
            expected = (distance / speed).toLong(),
            actual = timeCalculator.calculate(emptyList(), Speed.MS(speed))
        )
    }

    @Test
    fun `calculate total time in km_h with non empty point list and non zero speed`() {
        val distance = 10.0 // in meters
        val speed = 10.0 // in km per hour
        val distanceCalculator = distanceCalculatorStub(totalDistance = distance)
        val timeCalculator = polylineTimeCalculator(distanceCalculator)
        assertEquals(
            expected = (distance / (speed / 3.6)).toLong(),
            actual = timeCalculator.calculate(emptyList(), Speed.KmH(speed)),
        )
    }
}
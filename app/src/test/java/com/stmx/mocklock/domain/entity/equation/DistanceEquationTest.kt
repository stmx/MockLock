package com.stmx.mocklock.domain.entity.equation

import com.stmx.mocklock.domain.DELTA_DISTANCE
import com.stmx.mocklock.domain.testmodels.geoPoint
import com.stmx.mocklock.domain.testmodels.haversineDistanceEquation
import kotlin.test.Test
import kotlin.test.assertEquals

class DistanceEquationTest {

    private val equation = haversineDistanceEquation()

    @Test
    fun `calculating Haversine formula length`() {
        val startPoint = geoPoint(latitude = 51.5007, longitude = 0.1246)
        val endPoint = geoPoint(latitude = 40.6892, longitude = 74.0445)
        assertEquals(5574840.45, equation.calculate(startPoint, endPoint), DELTA_DISTANCE)
    }

    @Test
    fun `calculating Haversine formula zero length in zero coordinates`() {
        val startPoint = geoPoint()
        val endPoint = geoPoint()
        assertEquals(equation.calculate(startPoint, endPoint), 0.0, DELTA_DISTANCE)
    }

    @Test
    fun `calculating Haversine formula zero length in non-zero coordinates`() {
        val startPoint = geoPoint(latitude = 10.0, longitude = 20.0)
        val endPoint = geoPoint(latitude = 10.0, longitude = 20.0)
        assertEquals(equation.calculate(startPoint, endPoint), 0.0, DELTA_DISTANCE)
    }

    @Test
    fun `calculating Haversine formula zero length over 360 degrees`() {
        val startPoint = geoPoint()
        val endPoint = geoPoint(latitude = 360.0)
        assertEquals(equation.calculate(startPoint, endPoint), 0.0, DELTA_DISTANCE)
    }

    @Test
    fun `calculating Haversine formula regardless of the sign latitude`() {
        val startPoint = geoPoint(latitude = -10.0)
        val endPoint = geoPoint()

        val startPoint2 = geoPoint(latitude = 10.0)
        val endPoint2 = geoPoint()

        assertEquals(
            equation.calculate(startPoint, endPoint),
            equation.calculate(startPoint2, endPoint2),
            DELTA_DISTANCE
        )
    }

    @Test
    fun `calculating Haversine formula regardless of the sign longitude`() {
        val startPoint = geoPoint(longitude = -10.0)
        val endPoint = geoPoint()

        val startPoint2 = geoPoint(longitude = 10.0)
        val endPoint2 = geoPoint()

        assertEquals(
            equation.calculate(startPoint, endPoint) - equation.calculate(startPoint2, endPoint2),
            0.0,
            DELTA_DISTANCE
        )
    }
}
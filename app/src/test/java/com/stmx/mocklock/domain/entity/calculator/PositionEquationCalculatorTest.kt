package com.stmx.mocklock.domain.entity.calculator

import com.stmx.mocklock.domain.entity.DELTA_DEGREES
import com.stmx.mocklock.domain.entity.geoPoint
import com.stmx.mocklock.domain.entity.randomGeoPointList
import kotlin.random.Random
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class PositionEquationCalculatorTest {

    private val positionEquation = PositionEquationCalculator.Parametric()

    @Test
    fun `check result list size with geopoints without  elements`() {
        assertTrue(positionEquation.calculate(emptyList()).isEmpty())
    }

    @Test
    fun `check result with geopoints with 1 elements`() {
        val latitude = 23.0
        val longitude = 12.0
        val geoPoint = geoPoint(latitude = latitude, longitude = longitude)
        val points = listOf(geoPoint)
        val result = positionEquation.calculate(points)
        assertTrue(result.size == 1)
        val startResult = result[0].calculate(0.0)
        val endResult = result[0].calculate(1.0)
        assertEquals(startResult.latitude, latitude, DELTA_DEGREES)
        assertEquals(startResult.longitude, longitude, DELTA_DEGREES)
        assertEquals(endResult.latitude, latitude, DELTA_DEGREES)
        assertEquals(endResult.longitude, longitude, DELTA_DEGREES)
    }

    @Test
    fun `check result with geopoints with more than 2 elements`() {
        val points = randomGeoPointList(Random.nextInt(2, 50))
        val results = positionEquation.calculate(points)
        assertTrue(results.size == points.size - 1)
        for (index in results.indices) {
            val start = results[index].calculate(0.0)
            val between = results[index].calculate(0.5)
            val end = results[index].calculate(1.0)
            val expectedBetweenLatitude = (points[index + 1].latitude + points[index].latitude) / 2
            val expectedBetweenLongitude = (points[index + 1].longitude + points[index].longitude) / 2
            assertEquals(start.latitude, points[index].latitude, DELTA_DEGREES)
            assertEquals(start.longitude, points[index].longitude, DELTA_DEGREES)
            assertEquals(between.latitude, expectedBetweenLatitude, DELTA_DEGREES)
            assertEquals(between.longitude, expectedBetweenLongitude, DELTA_DEGREES)
            assertEquals(end.latitude, points[index + 1].latitude, DELTA_DEGREES)
            assertEquals(end.longitude, points[index + 1].longitude, DELTA_DEGREES)
        }
    }
}
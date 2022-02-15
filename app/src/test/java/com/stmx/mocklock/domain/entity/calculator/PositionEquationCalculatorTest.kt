package com.stmx.mocklock.domain.entity.calculator

import com.stmx.mocklock.domain.DELTA_DEGREES
import com.stmx.mocklock.domain.testmodels.geoPoint
import com.stmx.mocklock.domain.testmodels.parametricPositionEquationCalculator
import com.stmx.mocklock.domain.testmodels.randomGeoPointList
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PositionEquationCalculatorTest {

    private val positionEquation = parametricPositionEquationCalculator()

    @Test
    fun `check result list size with geopoints without  elements`() {
        assertTrue(actual = positionEquation.calculate(emptyList()).isEmpty())
    }

    @Test
    fun `check result with geopoints with 1 elements`() {
        val latitude = 23.0
        val longitude = 12.0
        val geoPoint = geoPoint(latitude = latitude, longitude = longitude)
        val points = listOf(geoPoint)
        val result = positionEquation.calculate(points)
        assertTrue(actual = result.size == 1)
        val startResult = result[0].calculate(0.0)
        val endResult = result[0].calculate(1.0)
        assertEquals(
            expected = latitude,
            actual = startResult.latitude,
            absoluteTolerance = DELTA_DEGREES
        )
        assertEquals(
            expected = longitude,
            actual = startResult.longitude,
            absoluteTolerance = DELTA_DEGREES
        )
        assertEquals(
            expected = latitude,
            actual = endResult.latitude,
            absoluteTolerance = DELTA_DEGREES
        )
        assertEquals(
            expected = longitude,
            actual = endResult.longitude,
            absoluteTolerance = DELTA_DEGREES
        )
    }

    @Test
    fun `check result with geopoints with more than 2 elements`() {
        val points = randomGeoPointList(from = 2)
        val results = positionEquation.calculate(points)

        assertEquals(
            expected = points.size,
            actual = results.size
        )

        for (index in results.indices) {
            val prevIndex = if (index == 0) index else index - 1
            assertEquals(
                expected = points[prevIndex].latitude,
                actual = results[index].calculate(0.0).latitude,
                absoluteTolerance = DELTA_DEGREES
            )
            assertEquals(
                expected = points[prevIndex].longitude,
                actual = results[index].calculate(0.0).longitude,
                absoluteTolerance = DELTA_DEGREES
            )
            assertEquals(
                expected = points[index].latitude,
                actual = results[index].calculate(1.0).latitude,
                absoluteTolerance = DELTA_DEGREES
            )
            assertEquals(
                expected = points[index].longitude,
                actual = results[index].calculate(1.0).longitude,
                absoluteTolerance = DELTA_DEGREES
            )
        }
    }

    @Test
    fun `check result with random geopoints with more than 2 elements`() {
        val points = randomGeoPointList(from = 2)
        val results = positionEquation.calculate(points)
        assertEquals(
            expected = points.size,
            actual = results.size
        )
        for (index in results.indices) {
            val between = results[index].calculate(0.5)
            val prevIndex = if (index == 0) index else index - 1
            assertEquals(
                expected = (points[index].latitude + points[prevIndex].latitude) / 2,
                actual = between.latitude,
                absoluteTolerance = DELTA_DEGREES
            )
            assertEquals(
                expected = (points[index].longitude + points[prevIndex].longitude) / 2,
                actual = between.longitude,
                absoluteTolerance = DELTA_DEGREES
            )
        }
    }
}
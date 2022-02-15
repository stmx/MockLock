package com.stmx.mocklock.domain.entity.calculator

import com.stmx.mocklock.domain.DELTA_DEGREES
import com.stmx.mocklock.domain.MAX_LIST_SIZE
import com.stmx.mocklock.domain.entity.GeoPoint
import com.stmx.mocklock.domain.testmodels.geoPoint
import com.stmx.mocklock.domain.testmodels.geoPointListWithEqualsDistance
import com.stmx.mocklock.domain.testmodels.linearDistanceEquation
import com.stmx.mocklock.domain.testmodels.polylineDistanceCalculator
import com.stmx.mocklock.domain.testmodels.polylinePositionCalculator
import com.stmx.mocklock.domain.testmodels.randomGeoPoint
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class PositionCalculatorTest {

    private val positionCalculator = polylinePositionCalculator(
        distanceCalculator = polylineDistanceCalculator(linearDistanceEquation())
    )

    @Test
    fun `calculate position with empty geopoint`() {
        assertFails {
            positionCalculator.calculate(emptyList(), 0.0)
        }
    }

    @Test
    fun `calculate position with geopoint list with 1 elements`() {
        for (i in 0..MAX_LIST_SIZE) {
            val point = randomGeoPoint()
            assertPosition(point, 0.0)
            assertPosition(point, 1.0)
            assertPosition(point, Random.nextDouble(0.0, 1.0))
            assertPosition(point, Random.nextDouble(0.0, 1.0))
            assertPosition(point, Random.nextDouble(0.0, 1.0))
        }
    }

    private fun assertPosition(point: GeoPoint, progress: Double) {
        val result = positionCalculator.calculate(listOf(point), progress)
        assertEquals(
            expected = point.latitude,
            actual = result.latitude,
            absoluteTolerance = DELTA_DEGREES
        )
        assertEquals(
            expected = point.longitude,
            actual = result.longitude,
            absoluteTolerance = DELTA_DEGREES
        )
    }

    @Test
    fun `calculate nodes position with geopoint list more than 1 elements`() {
        val geoPoints = geoPointListWithEqualsDistance(from = 2)
        for (index in geoPoints.indices) {
            val progress = index.toDouble() / (geoPoints.size - 1)
            assertPosition(geoPoints, progress, geoPoints[index])
        }
    }

    @Test
    fun `calculate position with geopoint list more than 1 elements`() {
        val geoPoints = listOf(
            geoPoint(0.0, 0.0),
            geoPoint(3.0, 4.0),
            geoPoint(13.0, 4.0),
            geoPoint(9.0, 7.0),
        )
        assertPosition(geoPoints, 0.0, geoPoints[0])
        assertPosition(geoPoints, 0.25, geoPoints[1])
        assertPosition(geoPoints, 0.75, geoPoints[2])
        assertPosition(geoPoints, 1.0, geoPoints[3])
        assertPosition(geoPoints, 0.5, geoPoint(8.0, 4.0))
        assertPosition(geoPoints, 0.125, geoPoint(1.5, 2.0))
        assertPosition(geoPoints, 0.875, geoPoint(11.0, 5.5))
    }

    private fun assertPosition(geoPoints: List<GeoPoint>, progress: Double, expected: GeoPoint) {
        val result = positionCalculator.calculate(geoPoints, progress)
        assertEquals(
            expected = expected.latitude,
            actual = result.latitude,
            absoluteTolerance = DELTA_DEGREES,
            message = "progress: $progress"
        )
        assertEquals(
            expected = expected.longitude,
            actual = result.longitude,
            absoluteTolerance = DELTA_DEGREES,
            message = "progress: $progress"
        )
    }
}
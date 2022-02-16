package com.stmx.mocklock.domain.entity.calculator

import com.stmx.mocklock.domain.DELTA_DISTANCE
import com.stmx.mocklock.domain.testmodels.emptyGeoPointList
import com.stmx.mocklock.domain.testmodels.linearDistanceEquation
import com.stmx.mocklock.domain.testmodels.polylineDistanceCalculator
import com.stmx.mocklock.domain.testmodels.randomGeoPoint
import com.stmx.mocklock.domain.testmodels.randomGeoPointList
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class DistanceCalculatorTest {

    private val distanceEquation = linearDistanceEquation()
    private val distanceCalculator = polylineDistanceCalculator(distanceEquation = distanceEquation)

    @Test
    fun `calculating total length of empty point list`() {
        val points = emptyGeoPointList()
        assertEquals(
            expected = 0.0,
            actual = distanceCalculator.calculateTotal(points),
            absoluteTolerance = DELTA_DISTANCE
        )
    }

    @Test
    fun `calculating length distribution of empty point list`() {
        val points = emptyGeoPointList()
        assertTrue(
            actual = distanceCalculator.calculate(points).isEmpty()
        )
    }

    @Test
    fun `calculating total length of point list with 1 element`() {
        val points = listOf(randomGeoPoint())
        assertEquals(
            expected = 0.0,
            actual = distanceCalculator.calculateTotal(points),
            absoluteTolerance = DELTA_DISTANCE
        )
    }

    @Test
    fun `calculating total length distribution of point list with 1 element`() {
        val points = listOf(randomGeoPoint())
        val result = distanceCalculator.calculate(points)
        assertTrue(
            actual = result.size == 1
        )
        assertEquals(
            expected = 0.0,
            actual = result[0],
            absoluteTolerance = DELTA_DISTANCE
        )
    }

    @Test
    fun `calculating total length of point list with more than 1 element`() {
        val points = randomGeoPointList(from = 2)
        var totalLength = 0.0
        for (index in 0..points.size - 2) {
            totalLength += distanceEquation.calculate(points[index], points[index + 1])
        }
        assertEquals(
            expected = totalLength,
            actual = distanceCalculator.calculateTotal(points),
            absoluteTolerance = DELTA_DISTANCE
        )
    }

    @Test
    fun `calculating length distribution of point list with more than 1 element`() {
        val points = randomGeoPointList(from = 2)
        val expectedResult = ArrayList<Double>(points.size)
        for (index in points.indices) {
            val prev = if (index == 0) {
                points[index]
            } else {
                points[index - 1]
            }
            expectedResult.add(distanceEquation.calculate(prev, points[index]))
        }
        val result = distanceCalculator.calculate(points)
        assertEquals(
            expected = points.size,
            actual = result.size
        )
        assertEquals(
            expected = expectedResult,
            actual = result
        )
    }


    @Test
    fun `calculating sum of length distribution and compare with total distance`() {
        val points = randomGeoPointList(from = 2)
        val result = distanceCalculator.calculate(points).sumOf { it }
        val total = distanceCalculator.calculateTotal(points)
        assertEquals(
            expected = 0.0,
            actual = result - total,
            absoluteTolerance = DELTA_DISTANCE
        )
    }
}
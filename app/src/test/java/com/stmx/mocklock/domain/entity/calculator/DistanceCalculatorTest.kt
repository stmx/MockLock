package com.stmx.mocklock.domain.entity.calculator

import com.stmx.mocklock.domain.entity.DELTA_DISTANCE
import com.stmx.mocklock.domain.entity.GeoPoint
import com.stmx.mocklock.domain.entity.geoPointRandom
import com.stmx.mocklock.domain.entity.testDistanceEquation
import org.junit.Assert.assertEquals
import org.junit.Test

class DistanceCalculatorTest {

    private val distanceCalculator: DistanceCalculator =
        DistanceCalculator.Polyline(testDistanceEquation())

    @Test
    fun `calculating total length of empty point list`() {
        val list = emptyList<GeoPoint>()
        assertEquals(distanceCalculator.calculateTotal(list), 0.0, DELTA_DISTANCE)
    }

    @Test
    fun `calculating total length of point list with 1 element`() {
        val list = listOf(geoPointRandom())
        assertEquals(distanceCalculator.calculateTotal(list), 0.0, DELTA_DISTANCE)
    }

    @Test
    fun `calculating total length of point list with more than 1 element`() {
        val list = listOf(geoPointRandom())
        assertEquals(distanceCalculator.calculateTotal(list), 0.0, DELTA_DISTANCE)
    }
}
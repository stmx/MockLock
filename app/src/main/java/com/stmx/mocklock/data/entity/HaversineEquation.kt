package com.stmx.mocklock.data.entity

import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

class HaversineEquation {

    fun calculate(startPoint: GeoPoint, endPoint: GeoPoint): Double {
        val lat1 = Math.toRadians(startPoint.latitude)
        val lon1 = Math.toRadians(startPoint.longitude)
        val lat2 = Math.toRadians(endPoint.latitude)
        val lon2 = Math.toRadians(endPoint.longitude)

        // Haversine formula
        val dlon = lon2 - lon1
        val dlat = lat2 - lat1
        val a = (sin(dlat / 2).pow(2.0) + (cos(lat1) * cos(lat2) * sin(dlon / 2).pow(2.0)))
        val c = 2 * asin(sqrt(a))
        return c * EARTH_RADIUS
    }

    companion object {
        private const val EARTH_RADIUS = 6371000
    }
}
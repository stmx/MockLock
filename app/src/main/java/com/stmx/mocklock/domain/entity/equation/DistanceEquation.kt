package com.stmx.mocklock.domain.entity.equation

import com.stmx.mocklock.domain.entity.GeoPoint
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

interface DistanceEquation {

    fun calculate(startPoint: GeoPoint, endPoint: GeoPoint): Double

    class Haversine : DistanceEquation {

        override fun calculate(startPoint: GeoPoint, endPoint: GeoPoint): Double {
            val lat1 = Math.toRadians(startPoint.latitude)
            val lon1 = Math.toRadians(startPoint.longitude)
            val lat2 = Math.toRadians(endPoint.latitude)
            val lon2 = Math.toRadians(endPoint.longitude)

            val dLon = lon2 - lon1
            val dLat = lat2 - lat1
            val a = (sin(dLat / 2).pow(2.0) + (cos(lat1) * cos(lat2) * sin(dLon / 2).pow(2.0)))
            val c = 2 * asin(sqrt(a))
            return c * EARTH_RADIUS
        }

        companion object {
            private const val EARTH_RADIUS = 6371000
        }
    }

}

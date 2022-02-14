package com.stmx.mocklock.domain.entity

import com.stmx.mocklock.domain.entity.equation.DistanceEquation
import kotlin.math.sqrt
import kotlin.random.Random

fun geoPoint(latitude: Double = 0.0, longitude: Double = 0.0): GeoPoint {
    return GeoPoint(latitude, longitude)
}

fun geoPointRandom(
    latitude: Double = Random.nextDouble(-180.0, 180.0),
    longitude: Double = Random.nextDouble(-180.0, 180.0)
): GeoPoint {
    return GeoPoint(latitude, longitude)
}

fun randomGeoPointList(size: Int): List<GeoPoint> {
    val result = ArrayList<GeoPoint>(size)
    for (index in 0 until size) {
        result.add(geoPointRandom())
    }
    return result
}

fun distance(startPoint: GeoPoint, endPoint: GeoPoint): Double {
    val dLon = endPoint.longitude - startPoint.longitude
    val dLat = endPoint.latitude - startPoint.latitude
    return sqrt(dLon * dLon + dLat * dLat)
}

fun testDistanceEquation(): DistanceEquation {
    return object : DistanceEquation {
        override fun calculate(startPoint: GeoPoint, endPoint: GeoPoint): Double {
            return distance(startPoint, endPoint)
        }

    }
}

const val DELTA_DISTANCE: Double = 0.1
const val DELTA_DEGREES: Double = 0.000001



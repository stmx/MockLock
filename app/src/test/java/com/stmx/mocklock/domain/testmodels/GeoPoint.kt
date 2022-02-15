package com.stmx.mocklock.domain.testmodels

import com.stmx.mocklock.domain.MAX_LIST_SIZE
import com.stmx.mocklock.domain.entity.GeoPoint
import kotlin.random.Random

fun geoPoint(latitude: Double = 0.0, longitude: Double = 0.0): GeoPoint {
    return GeoPoint(latitude, longitude)
}

fun randomGeoPoint(
    latitude: Double = Random.nextDouble(-180.0, 180.0),
    longitude: Double = Random.nextDouble(-180.0, 180.0)
): GeoPoint {
    return GeoPoint(latitude, longitude)
}

fun randomGeoPointList(from: Int, until: Int = MAX_LIST_SIZE): List<GeoPoint> {
    val size = Random.nextInt(from, until)
    val result = ArrayList<GeoPoint>(size)
    for (index in 0 until size) {
        result.add(randomGeoPoint())
    }
    return result
}

fun emptyGeoPointList(): List<GeoPoint> {
    return emptyList()
}

fun geoPointListWithEqualsDistance(from: Int, until: Int = MAX_LIST_SIZE): List<GeoPoint> {
    val size = Random.nextInt(from, until)
    val result = ArrayList<GeoPoint>(size)
    val initPoint = geoPoint()
    result.add(initPoint)
    for (index in 1 until size) {
        val prev = result[index - 1]
        val point = when (index % 2) {
            1 -> geoPoint(prev.latitude + 1.0, prev.longitude)
            else -> geoPoint(prev.latitude, prev.longitude + 1.0)
        }
        result.add(point)
    }
    return result
}
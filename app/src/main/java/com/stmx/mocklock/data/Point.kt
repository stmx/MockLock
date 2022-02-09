package com.stmx.mocklock.data

import android.os.Parcelable
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt
import kotlinx.parcelize.Parcelize

@Parcelize
class Point(val latitude: Double, val longitude: Double) : Parcelable {
    override fun toString(): String {
        val f = "%.6f"
        return "Point(latitude=${f.format(latitude)}, longitude=${f.format(longitude)})"
    }
}

infix fun Point.lengthTo(point: Point): Double {

    val lat1 = Math.toRadians(this.latitude)
    val lon1 = Math.toRadians(this.longitude)
    val lat2 = Math.toRadians(point.latitude)
    val lon2 = Math.toRadians(point.longitude)

    // Haversine formula
    val dlon = lon2 - lon1
    val dlat = lat2 - lat1
    val a = (sin(dlat / 2).pow(2.0) + (cos(lat1) * cos(lat2) * sin(dlon / 2).pow(2.0)))
    val c = 2 * asin(sqrt(a))
    return (c * EARTH_RADIUS)
}

private const val EARTH_RADIUS = 6371000

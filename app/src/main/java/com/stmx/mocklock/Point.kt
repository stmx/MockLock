package com.stmx.mocklock

import kotlin.math.*

data class Point(val x: Float, val y: Float) {
    override fun toString(): String {
        val f = "%.6f"
        return "Point(x=${f.format(x)}, y=${f.format(y)})"
    }
}

infix fun Point.lengthTo(point: Point): Float {
    var lat1 = this.x.toDouble()
    var lon1 = this.y.toDouble()
    var lat2 = point.x.toDouble()
    var lon2 = point.y.toDouble()

    lon1 = Math.toRadians(lon1)
    lon2 = Math.toRadians(lon2)
    lat1 = Math.toRadians(lat1)
    lat2 = Math.toRadians(lat2)

    // Haversine formula
    val dlon = lon2 - lon1
    val dlat = lat2 - lat1
    val a = (sin(dlat / 2).pow(2.0) + (cos(lat1) * cos(lat2) * sin(dlon / 2).pow(2.0)))
    val c = 2 * asin(sqrt(a))
    val r = 6371.0f
    return (c * r * 1000).toFloat()
}

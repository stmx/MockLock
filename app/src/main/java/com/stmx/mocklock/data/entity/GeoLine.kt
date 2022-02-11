package com.stmx.mocklock.data.entity

class GeoLine(val start: GeoPoint, val end: GeoPoint) {
    val length = HaversineEquation().calculate(start, end)
}
package com.stmx.mocklock.domain.testmodels

import com.stmx.mocklock.domain.entity.GeoPoint
import com.stmx.mocklock.domain.entity.Speed
import com.stmx.mocklock.domain.entity.TrackConfiguration

private const val DEFAULT_DELAY = 1000L
fun trackConfiguration(
    points: List<GeoPoint> = emptyList(),
    speed: Speed = Speed.Zero,
): TrackConfiguration = TrackConfiguration(points, speed)
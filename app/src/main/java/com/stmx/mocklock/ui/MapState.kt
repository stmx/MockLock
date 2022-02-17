package com.stmx.mocklock.ui

import com.stmx.mocklock.ui.models.GeoPointUI

data class MapState(
    val currentMockPoint: GeoPointUI? = null,
    val polyline: List<GeoPointUI>? = null,
    val zoom: Double = DEFAULT_ZOOM,
    val center: GeoPointUI = GeoPointUI(DEFAULT_LATITUDE, DEFAULT_LONGITUDE),
    val needInvalidateCenter: Boolean = true,
    val needInvalidateZoom: Boolean = true,
    val trackCanBeStarted: Boolean = false,
    val trackCanBeStopped: Boolean = false,
    val trackCanBeCleared: Boolean = false,
)

private const val DEFAULT_LATITUDE: Double = 57.6877433463916
private const val DEFAULT_LONGITUDE: Double = 39.76345896720887
private const val DEFAULT_ZOOM: Double = 15.0

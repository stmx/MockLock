package com.stmx.mocklock.ui.map

import com.stmx.mocklock.ui.models.GeoPointUI

interface GeoPointMapper<T> {
    fun mapTo(geoPointUI: GeoPointUI): T
    fun mapFrom(value: T): GeoPointUI
}
package com.stmx.mocklock.ui.map

import com.stmx.mocklock.ui.models.GeoPointUI

interface GeoPointMapper<T> {
    fun mapTo(pointUI: GeoPointUI): T
    fun mapFrom(value: T): GeoPointUI
}

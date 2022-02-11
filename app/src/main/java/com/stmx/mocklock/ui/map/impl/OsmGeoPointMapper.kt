package com.stmx.mocklock.ui.map.impl

import com.stmx.mocklock.ui.map.GeoPointMapper
import com.stmx.mocklock.ui.models.GeoPointUI
import org.osmdroid.util.GeoPoint

class OsmGeoPointMapper : GeoPointMapper<GeoPoint> {
    override fun mapTo(geoPointUI: GeoPointUI): GeoPoint {
        return GeoPoint(geoPointUI.latitude, geoPointUI.longitude)
    }

    override fun mapFrom(value: GeoPoint): GeoPointUI {
        return GeoPointUI(value.latitude, value.longitude)
    }
}
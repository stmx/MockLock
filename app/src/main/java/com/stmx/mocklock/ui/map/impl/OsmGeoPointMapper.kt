package com.stmx.mocklock.ui.map.impl

import com.stmx.mocklock.ui.map.GeoPointMapper
import com.stmx.mocklock.ui.models.GeoPointUI
import org.osmdroid.util.GeoPoint

class OsmGeoPointMapper : GeoPointMapper<GeoPoint> {
    override fun mapTo(pointUI: GeoPointUI): GeoPoint {
        return GeoPoint(pointUI.latitude, pointUI.longitude)
    }

    override fun mapFrom(value: GeoPoint): GeoPointUI {
        return GeoPointUI(value.latitude, value.longitude)
    }
}

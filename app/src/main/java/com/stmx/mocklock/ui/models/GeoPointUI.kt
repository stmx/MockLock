package com.stmx.mocklock.ui.models

import android.os.Parcelable
import com.stmx.mocklock.domain.entity.GeoPoint
import com.stmx.mocklock.domain.entity.Mapper
import javax.inject.Inject
import kotlinx.parcelize.Parcelize

@Parcelize
data class GeoPointUI(val latitude: Double, val longitude: Double) : Parcelable {

    private constructor(point: GeoPoint) : this(point.latitude, point.longitude)

    override fun toString(): String {
        val f = "%.6f"
        return "Point(latitude=${f.format(latitude)}, longitude=${f.format(longitude)})"
    }

    class UIMapper @Inject constructor() : Mapper<GeoPoint, GeoPointUI> {
        override fun map(data: GeoPoint): GeoPointUI {
            return GeoPointUI(data)
        }
    }

    class DomainMapper @Inject constructor() : Mapper<GeoPointUI, GeoPoint> {
        override fun map(data: GeoPointUI): GeoPoint {
            return GeoPoint(data.latitude, data.longitude)
        }
    }
}

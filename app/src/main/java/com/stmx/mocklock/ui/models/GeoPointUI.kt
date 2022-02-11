package com.stmx.mocklock.ui.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class GeoPointUI(val latitude: Double, val longitude: Double) : Parcelable {

    override fun toString(): String {
        val f = "%.6f"
        return "Point(latitude=${f.format(latitude)}, longitude=${f.format(longitude)})"
    }
}

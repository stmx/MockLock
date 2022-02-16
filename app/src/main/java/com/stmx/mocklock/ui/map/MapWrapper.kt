package com.stmx.mocklock.ui.map

import com.stmx.mocklock.ui.models.GeoPointUI

interface MapWrapper {
    fun setOnLongClickListener(onLongClickListener: ((GeoPointUI) -> Unit)?)
    fun setCurrentPosition(point: GeoPointUI)
    fun showCurrentPosition()
    fun hideCurrentPosition()
    fun showPolyline(points: Array<GeoPointUI>)
    fun hidePolyline()
    fun setZoom(zoom: Double)
    fun setCenter(point: GeoPointUI)
    fun onResume()
    fun onPause()
}

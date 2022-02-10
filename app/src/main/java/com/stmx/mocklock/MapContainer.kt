package com.stmx.mocklock

import android.preference.PreferenceManager
import com.stmx.mocklock.data.Point
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

interface MapContainer {
    fun setCurrentPosition(point: Point)
    fun setZoom(zoom: Double)
    fun setCenter(point: Point)
    fun onResume()
    fun onPause()
}

class OsmMapContainer(
    private val map: MapView
) : MapContainer {
    private val currentPointMarker: Marker by lazy(LazyThreadSafetyMode.NONE) { Marker(map) }

    init {
        map.setTileSource(TileSourceFactory.MAPNIK);
        currentPointMarker.setOnMarkerClickListener { _, _ -> true }
        map.overlays.add(currentPointMarker)
        currentPointMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        Configuration.getInstance().load(map.context, PreferenceManager.getDefaultSharedPreferences(map.context))
    }

    override fun setCurrentPosition(point: Point) {
        currentPointMarker.position = GeoPoint(point.latitude, point.longitude)
        map.invalidate()
    }

    override fun setZoom(zoom: Double) {
        map.controller.setZoom(zoom)
    }

    override fun setCenter(point: Point) {
        map.controller.setCenter(GeoPoint(point.latitude, point.longitude))
    }

    override fun onResume() {
        map.onResume()
    }

    override fun onPause() {
        map.onPause()
    }
}
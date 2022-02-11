package com.stmx.mocklock.ui.map.impl

import android.preference.PreferenceManager
import com.stmx.mocklock.ui.map.GeoPointMapper
import com.stmx.mocklock.ui.map.MapWrapper
import com.stmx.mocklock.ui.models.GeoPointUI
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline

class OsmMapWrapper(
    private val map: MapView,
    private val mapper: GeoPointMapper<GeoPoint>
) : MapWrapper {

    private var currentPointIsShown: Boolean = false
    private val currentPointMarker: Marker by lazy(LazyThreadSafetyMode.NONE) { Marker(map) }
    private val polyline: Polyline by lazy(LazyThreadSafetyMode.NONE) { Polyline() }

    private val mapEventsReceiver: MapEventsReceiver = object : MapEventsReceiver {
        override fun singleTapConfirmedHelper(geoPoint: GeoPoint?): Boolean {
            return true
        }

        override fun longPressHelper(geoPoint: GeoPoint?): Boolean {
            if (geoPoint != null) {
                onLongClickListener?.invoke(mapper.mapFrom(geoPoint))
            }
            return true
        }

    }
    private val mapEventOverlay: MapEventsOverlay = MapEventsOverlay(mapEventsReceiver)
    private var onLongClickListener: ((GeoPointUI) -> Unit)? = null

    init {
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.overlays.add(mapEventOverlay)
        currentPointMarker.setOnMarkerClickListener { _, _ -> true }
        currentPointMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        polyline.setOnClickListener { _, _, _ -> true }
        polyline.outlinePaint.strokeWidth = 5f
        Configuration.getInstance()
            .load(map.context, PreferenceManager.getDefaultSharedPreferences(map.context))
    }

    override fun setOnLongClickListener(onLongClickListener: ((GeoPointUI) -> Unit)?) {
        this.onLongClickListener = onLongClickListener
    }

    override fun setCurrentPosition(point: GeoPointUI) {
        currentPointMarker.position = mapper.mapTo(point)
        map.invalidate()
    }

    override fun showCurrentPosition() {
        if (!currentPointIsShown) {
            map.overlays.add(currentPointMarker)
            currentPointIsShown = true
        }
    }

    override fun hideCurrentPosition() {
        if (currentPointIsShown) {
            map.overlays.remove(currentPointMarker)
            currentPointIsShown = false
        }
    }

    override fun showPolyline(points: Array<GeoPointUI>) {
        if (!map.overlays.contains(this.polyline)) {
            map.overlays.add(this.polyline)
        }
        val geoPoints = points.map { mapper.mapTo(it) }
        this.polyline.setPoints(geoPoints)
        map.invalidate()
    }

    override fun hidePolyline() {
        if (map.overlays.contains(this.polyline)) {
            map.overlays.remove(this.polyline)
        }
    }

    override fun setZoom(zoom: Double) {
        map.controller.setZoom(zoom)
    }

    override fun setCenter(point: GeoPointUI) {
        map.controller.setCenter(mapper.mapTo(point))
    }

    override fun onResume() {
        map.onResume()
    }

    override fun onPause() {
        map.onPause()
    }
}
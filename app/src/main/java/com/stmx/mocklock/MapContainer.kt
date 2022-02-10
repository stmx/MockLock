package com.stmx.mocklock

import android.preference.PreferenceManager
import com.stmx.mocklock.data.Point
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline as OsmPolyline

interface MapContainer {
    fun setOnLongClickListener(onLongClickListener: ((Point) -> Unit)?)
    fun setCurrentPosition(point: Point)
    fun showCurrentPosition()
    fun hideCurrentPosition()
    fun showPolyline(points: Array<Point>)
    fun hidePolyline()
    fun setZoom(zoom: Double)
    fun setCenter(point: Point)
    fun onResume()
    fun onPause()
}

class OsmMapContainer(
    private val map: MapView
) : MapContainer {

    private var currentPointIsShown: Boolean = false
    private val currentPointMarker: Marker by lazy(LazyThreadSafetyMode.NONE) { Marker(map) }
    private val polyline: OsmPolyline by lazy(LazyThreadSafetyMode.NONE) { OsmPolyline() }
    private var points: List<Point> = mutableListOf()

    private val mapEventsReceiver: MapEventsReceiver = object : MapEventsReceiver {
        override fun singleTapConfirmedHelper(geoPoint: GeoPoint?): Boolean {
            return true
        }

        override fun longPressHelper(geoPoint: GeoPoint?): Boolean {
            if (geoPoint != null) {
                onLongClickListener?.invoke(Point(geoPoint.latitude, geoPoint.longitude))
            }
            return true
        }

    }
    private val mapEventOverlay: MapEventsOverlay = MapEventsOverlay(mapEventsReceiver)
    private var onLongClickListener: ((Point) -> Unit)? = null

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

    override fun setOnLongClickListener(onLongClickListener: ((Point) -> Unit)?) {
        this.onLongClickListener = onLongClickListener
    }

    override fun setCurrentPosition(point: Point) {
        currentPointMarker.position = GeoPoint(point.latitude, point.longitude)
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

    override fun showPolyline(points: Array<Point>) {
        if (!map.overlays.contains(this.polyline)) {
            map.overlays.add(this.polyline)
        }

        this.polyline.setPoints(points.map { GeoPoint(it.latitude, it.longitude) })
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

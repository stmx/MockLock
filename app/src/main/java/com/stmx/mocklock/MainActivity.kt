package com.stmx.mocklock

import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.stmx.mocklock.data.Point
import com.stmx.mocklock.data.Track
import com.stmx.mocklock.data.Polyline
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class MainActivity : AppCompatActivity() {

    private val map: MapView by lazy(LazyThreadSafetyMode.NONE) { findViewById(R.id.map_view) }
    private val marker: Marker by lazy(LazyThreadSafetyMode.NONE) { Marker(map) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this))
        val mapController = map.controller
        mapController.setZoom(15.0)
        mapController.setCenter(GeoPoint(57.6877433463916, 39.76345896720887))
        map.setTileSource(TileSourceFactory.MAPNIK);
        marker.setOnMarkerClickListener { _, _ -> true }
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        map.overlays.add(marker)
        Foo.observePoint {
            showPosition(it)
        }
        findViewById<Button>(R.id.start_service).setOnClickListener {
            startService(MockLocationService.newIntent(this, route))
        }
    }

    private fun showPosition(point: Point) {
        Log.d("FooTag", "Point: $point")
        marker.position = GeoPoint(point.latitude, point.longitude)
        map.invalidate()
    }

    override fun onResume() {
        super.onResume()
        map.onResume()
    }

    override fun onPause() {
        super.onPause()
        map.onPause()
    }

}

package com.stmx.mocklock.ui

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.stmx.mocklock.Foo
import com.stmx.mocklock.MockLocationService
import com.stmx.mocklock.R
import com.stmx.mocklock.route
import com.stmx.mocklock.ui.map.MapWrapper
import com.stmx.mocklock.ui.map.impl.OsmGeoPointMapper
import com.stmx.mocklock.ui.map.impl.OsmMapWrapper
import com.stmx.mocklock.ui.models.GeoPointUI
import org.osmdroid.views.MapView

class MainActivity : AppCompatActivity() {

    private val map: MapWrapper by lazy {
        val osmMapper = OsmGeoPointMapper()
        val mapView = findViewById<MapView>(R.id.map_view)
        OsmMapWrapper(mapView, osmMapper)
    }

    private val polyline: MutableList<GeoPointUI> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        map.setZoom(15.0)
        map.setCenter(GeoPointUI(57.6877433463916, 39.76345896720887))
        map.setOnLongClickListener {
            polyline.add(it)
            if (polyline.isNotEmpty()) {
                map.showPolyline(polyline.toTypedArray())
            }
        }

        Foo.observePoint {
            showPosition(it)
        }
        findViewById<Button>(R.id.start_service).setOnClickListener {
            startService(MockLocationService.newIntent(this, route))
        }
    }

    private fun showPosition(point: GeoPointUI) {
        Log.d("FooTag", "Point: $point")
        map.setCurrentPosition(point)
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

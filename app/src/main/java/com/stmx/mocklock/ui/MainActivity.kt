package com.stmx.mocklock.ui

import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.stmx.mocklock.R
import com.stmx.mocklock.TrackEmitterService
import com.stmx.mocklock.appComponent
import com.stmx.mocklock.ui.map.MapWrapper
import com.stmx.mocklock.ui.map.impl.OsmGeoPointMapper
import com.stmx.mocklock.ui.map.impl.OsmMapWrapper
import com.stmx.mocklock.ui.models.GeoPointUI
import dagger.Lazy
import org.osmdroid.views.MapView
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: Lazy<ViewModelProvider.Factory>

    private val viewModel: MainViewModel by viewModels {
        factory.get()
    }

    private val map: MapWrapper by lazy {
        val osmMapper = OsmGeoPointMapper()
        val mapView = findViewById<MapView>(R.id.map_view)
        OsmMapWrapper(mapView, osmMapper)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        applicationContext.appComponent.inject(this)
        viewModel.liveData.observe(this) { state ->
            renderState(state)
        }

        map.setOnLongClickListener {
            viewModel.addPointToPolyline(it)
        }

        findViewById<Button>(R.id.start_service).setOnClickListener {
            viewModel.liveData.value?.polyline?.let { points ->
                viewModel.startTrack()
                startService(TrackEmitterService.newIntent(this, points))
            }
        }

        findViewById<Button>(R.id.clear).setOnClickListener {
            viewModel.clearPolyline()
        }
    }

    private fun renderState(state: MapState) {
        setZoom(state.zoom, state.needInvalidateZoom)
        setCenter(state.center, state.needInvalidateCenter)
        setCurrentMockPoint(state.currentMockPoint)
        setPolyline(state.polyline)
    }

    private fun setCurrentMockPoint(point: GeoPointUI?) {
        if (point != null) {
            map.showCurrentPosition()
            map.setCurrentPosition(point)
        } else {
            map.hideCurrentPosition()
        }
    }

    private fun setPolyline(polyline: List<GeoPointUI>?) {
        if (polyline != null && polyline.isNotEmpty()) {
            map.showPolyline(polyline.toTypedArray())
        } else {
            map.hidePolyline()
        }
    }

    private fun setZoom(zoom: Double, needInvalidate: Boolean) {
        if (needInvalidate) {
            map.setZoom(zoom)
        }
    }

    private fun setCenter(point: GeoPointUI, needInvalidate: Boolean) {
        if (needInvalidate) {
            map.setCenter(point)
        }
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

package com.stmx.mocklock

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import com.stmx.mocklock.domain.Polyline
import com.stmx.mocklock.domain.Track
import com.stmx.mocklock.domain.entity.GeoPoint
import com.stmx.mocklock.ui.models.GeoPointUI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MockLocationService : Service() {

    private var job: Job? = null

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val points = parseIntent(intent)
        if (points.isNotEmpty()) {
            startTrack(points)
        } else {
            stopService()
        }
        return START_STICKY
    }

    private fun startTrack(points: Array<GeoPointUI>) {
        val points = points.map { GeoPoint(it.latitude, it.longitude) }.toTypedArray()
        val polyline = Polyline(points)
        val track = Track(polyline, 120.0)
        job = CoroutineScope(Dispatchers.Default).launch {
            track.start().collect {
                Foo.setPoint(it.point)
                if (it.value >= 1.0) stopService()
            }
        }
    }

    private fun stopService() {
        stopSelf()
    }

    override fun onDestroy() {
        job?.cancel()
        job = null
        super.onDestroy()
    }

    companion object {

        private const val EXTRA_POINT_ARRAY_KEY = "arrayPointsKey"

        fun newIntent(context: Context, array: Array<GeoPointUI>): Intent {
            return Intent(context, MockLocationService::class.java).apply {
                putExtra(EXTRA_POINT_ARRAY_KEY, array)
            }
        }

        private fun parseIntent(intent: Intent): Array<GeoPointUI> {
            val array = intent.getParcelableArrayExtra(EXTRA_POINT_ARRAY_KEY) ?: return emptyArray()
            return array.mapNotNull {
                it as? GeoPointUI
            }.toTypedArray()
        }
    }
}
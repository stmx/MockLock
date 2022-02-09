package com.stmx.mocklock

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import com.stmx.mocklock.data.Point
import com.stmx.mocklock.data.Polyline
import com.stmx.mocklock.data.Track
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MockLocationService : Service() {
    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val points = parseIntent(intent)
        if (points.isNotEmpty()) {
            val polyline = Polyline(route)
            val track = Track(polyline, 120.0)
            CoroutineScope(Dispatchers.Default).launch {
                track.start().collect {
                    Foo.setPoint(it.point)
                    if (it.value >= 1.0) stopService()
                }
            }
        } else {
            stopService()
        }
        return START_STICKY
    }

    private fun stopService() {
        stopSelf()
    }

    companion object {

        private const val EXTRA_POINT_ARRAY_KEY = "arrayPointsKey"

        fun newIntent(context: Context, array: Array<Point>): Intent {
            return Intent(context, MockLocationService::class.java).apply {
                putExtra(EXTRA_POINT_ARRAY_KEY, array)
            }
        }

        private fun parseIntent(intent: Intent): Array<Point> {
            val array = intent.getParcelableArrayExtra(EXTRA_POINT_ARRAY_KEY) ?: return emptyArray()
            return array.mapNotNull {
                it as? Point
            }.toTypedArray()
        }
    }
}
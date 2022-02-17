@file:Suppress("MagicNumber")

package com.stmx.mocklock

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import com.stmx.mocklock.domain.entity.GeoPoint
import com.stmx.mocklock.domain.entity.Mapper
import com.stmx.mocklock.domain.entity.Speed
import com.stmx.mocklock.domain.entity.TrackConfiguration
import com.stmx.mocklock.domain.entity.emitter.TrackPositionEmitter
import com.stmx.mocklock.ui.models.GeoPointUI
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

class TrackEmitterService : Service() {

    @Inject
    lateinit var trackPositionEmitter: TrackPositionEmitter

    @Inject
    lateinit var domainMapper: Mapper<GeoPointUI, GeoPoint>

    private var job: Job? = null

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        applicationContext.appComponent.inject(this)
        val points = parseIntent(intent)
        if (points.isNotEmpty()) {
            startForeground()
            startTrack(points)
        } else {
            stopService()
        }
        return START_STICKY
    }

    private fun startTrack(points: List<GeoPointUI>) {
        val configuration = TrackConfiguration(
            points = points.map(domainMapper::map),
            speed = Speed.KmH(120.0)
        )
        val flow = trackPositionEmitter.start(configuration)
        job?.cancel()
        job = CoroutineScope(Dispatchers.Default).launch {
            flow.onCompletion { stopService() }.collect()
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

    private fun startForeground() {
        val notificationId = 123456
        val channelId = "mock_service"
        val channelName = "Общее"
        val notificationManager = getSystemService(NotificationManager::class.java)
        val notificationBuilder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
            Notification.Builder(baseContext, channelId)
        } else {
            Notification.Builder(baseContext)
        }
        notificationBuilder.setContentTitle("Title mock service")
        notificationBuilder.setContentTitle("Content mock service")
        notificationBuilder.setOngoing(true)
        startForeground(notificationId, notificationBuilder.build())
    }

    companion object {

        private const val EXTRA_POINT_ARRAY_KEY = "arrayPointsKey"

        fun newIntent(context: Context, points: List<GeoPointUI>? = null): Intent {
            return Intent(context, TrackEmitterService::class.java).apply {
                if (!points.isNullOrEmpty()) {
                    putExtra(EXTRA_POINT_ARRAY_KEY, points.toTypedArray())
                }
            }
        }

        private fun parseIntent(intent: Intent): List<GeoPointUI> {
            val array = intent.getParcelableArrayExtra(EXTRA_POINT_ARRAY_KEY) ?: return emptyList()
            return array.mapNotNull {
                it as? GeoPointUI
            }
        }
    }
}

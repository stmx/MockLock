package com.stmx.mocklock

import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private val map: MapView by lazy(LazyThreadSafetyMode.NONE) { findViewById(R.id.map_view) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        val mapController = map.controller
        mapController.setZoom(15.0)
        mapController.setCenter(GeoPoint(57.6877433463916, 39.76345896720887))
        map.setTileSource(TileSourceFactory.MAPNIK);


        val polyline = Polyline(route)
        val marker = Marker(map)
        marker.setOnMarkerClickListener { _, _ -> true }
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        map.overlays.add(marker)

        val foo = Foo(polyline, 120f / 3.6f) {
            marker.position = GeoPoint(it.x.toDouble(), it.y.toDouble())
            map.invalidate()
        }
        CoroutineScope(Dispatchers.Default).launch {
            foo.start()
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

fun main() {
    val polyline = Polyline(
        listOf(
            Point(57.691052243336436f, 39.77586150169373f),
        )
    )
    println(polyline.totalLength)
    val n = 1000
    val array = FloatArray(n + 1)
    for (i in 0..n) {
        array[i] = (i.toFloat() / n.toFloat())
    }
    for (x in array) {
        println(polyline.getPointAtPercent(x))
    }

}

class Foo(
    private val track: Polyline,
    private val speed: Float,
    private val callback: (Point) -> Unit
) {
    private val totalTime: Long = (1000 * track.totalLength / speed).toLong()
    private var startTime: Long = 0L

    suspend fun start() {
        startTime = System.currentTimeMillis()
        var time = 0L
        while (time <= totalTime) {
            time = System.currentTimeMillis() - startTime
            val part = (time.toFloat() / totalTime.toFloat())
            val percent = when {
                part > 1.0f -> 1.0f
                part < 0.0f -> 0.0f
                else -> part
            }
            val point = track.getPointAtPercent(percent)
            callback.invoke(point)
            Log.d("FooTag", "Time: $time Point: $point")
            delay(Random.nextLong(800L, 1500L))
        }
        Log.d("FooTag", "That's all")

    }

    fun stop() {
        startTime = 0L
    }

    companion object {
        private const val MIN_DISCRETE_TIME_IN_MILLIS = 500
        private const val MAX_DISCRETE_TIME_IN_MILLIS = 3000
        private const val DISCRETE_TIME_IN_MILLIS = 1000
    }
}

private val route = listOf(
    Point(57.691052243336436f, 39.77586150169373f),
    Point(57.691047f, 39.775871f),
    Point(57.691959f, 39.777717f),
    Point(57.692249f, 39.778262f),
    Point(57.692239f, 39.778285f),
    Point(57.692239f, 39.778285f),
    Point(57.692249f, 39.778262f),
    Point(57.692383f, 39.778429f),
    Point(57.696160f, 39.782325f),
    Point(57.696193f, 39.782269f),
    Point(57.696193f, 39.782268f),
    Point(57.697147f, 39.780555f),
    Point(57.697172f, 39.780601f),
    Point(57.697172f, 39.780601f),
    Point(57.697147f, 39.780555f),
    Point(57.696930f, 39.780125f),
    Point(57.696930f, 39.780125f),
    Point(57.696921f, 39.780109f),
    Point(57.697020f, 39.779938f),
    Point(57.696772f, 39.779451f),
    Point(57.696796f, 39.779409f),
    Point(57.696796f, 39.779409f),
    Point(57.697184f, 39.778724f),
    Point(57.697360f, 39.779090f),
    Point(57.697660f, 39.778554f),
    Point(57.697654f, 39.778544f),
    Point(57.697654f, 39.778544f),
    Point(57.697660f, 39.778554f),
    Point(57.697891f, 39.778302f),
    Point(57.697954f, 39.778176f),
    Point(57.697900f, 39.777849f),
    Point(57.698013f, 39.777651f),
    Point(57.698125f, 39.777554f),
    Point(57.698641f, 39.776733f),
    Point(57.698777f, 39.776875f),
    Point(57.699076f, 39.776376f),
    Point(57.699058f, 39.776009f),
    Point(57.699070f, 39.775990f),
    Point(57.699070f, 39.775990f),
    Point(57.699383f, 39.775486f),
    Point(57.699748f, 39.776204f),
    Point(57.700353f, 39.777378f),
    Point(57.701004f, 39.778733f),
    Point(57.701467f, 39.779649f),
    Point(57.701449f, 39.779680f),
)

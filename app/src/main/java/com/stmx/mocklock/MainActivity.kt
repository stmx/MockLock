package com.stmx.mocklock

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.stmx.mocklock.data.Point

class MainActivity : AppCompatActivity() {

    private val map: Map by lazy(LazyThreadSafetyMode.NONE) { MapImpl(findViewById(R.id.map_view)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        map.setZoom(15.0)
        map.setCenter(Point(57.6877433463916, 39.76345896720887))

        Foo.observePoint {
            showPosition(it)
        }
        findViewById<Button>(R.id.start_service).setOnClickListener {
            startService(MockLocationService.newIntent(this, route))
        }
    }

    private fun showPosition(point: Point) {
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

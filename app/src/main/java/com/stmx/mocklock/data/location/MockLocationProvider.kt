package com.stmx.mocklock.data.location

import android.location.Location
import android.location.LocationManager
import android.location.provider.ProviderProperties
import android.os.Build
import android.os.SystemClock
import com.stmx.mocklock.domain.entity.GeoPoint
import javax.inject.Inject

interface MockLocationProvider {
    fun setMockLocation(point: GeoPoint)
}

class MockLocationProviderImpl @Inject constructor(
    private val locationManager: LocationManager
) : MockLocationProvider {

    override fun setMockLocation(point: GeoPoint) {
        val mockLocation = createLocation(point)
        locationManager.addTestProvider(
            LocationManager.GPS_PROVIDER,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            ProviderProperties.POWER_USAGE_HIGH,
            ProviderProperties.ACCURACY_FINE
        )
        locationManager.setTestProviderEnabled(LocationManager.GPS_PROVIDER, true)
        locationManager.setTestProviderLocation(LocationManager.GPS_PROVIDER, mockLocation)
    }

    private fun createLocation(point: GeoPoint) = Location(LocationManager.GPS_PROVIDER).apply {
        accuracy = DEFAULT_ACCURACY
        altitude = DEFAULT_ALTITUDE
        latitude = point.latitude
        longitude = point.longitude
        time = System.currentTimeMillis()
        elapsedRealtimeNanos = SystemClock.elapsedRealtimeNanos()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            isMock = true
        }
    }

    companion object {
        private const val DEFAULT_ACCURACY = 20f
        private const val DEFAULT_ALTITUDE = 0.0
    }
}

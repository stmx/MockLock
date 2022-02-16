package com.stmx.mocklock.data.location

import android.location.Location
import android.location.LocationManager
import android.os.Build
import javax.inject.Inject
import org.osmdroid.util.GeoPoint

interface MockLocationProvider {
    fun setMockLocation(point: GeoPoint)
}

class MockLocationProviderImpl @Inject constructor(
    private val locationManager: LocationManager
) : MockLocationProvider {

    override fun setMockLocation(point: GeoPoint) {
        val mockLocation = Location(LocationManager.GPS_PROVIDER)
        mockLocation.accuracy = 20f
        mockLocation.altitude = 0.0
        mockLocation.latitude = point.latitude
        mockLocation.longitude = point.longitude
        mockLocation.time = System.currentTimeMillis()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            mockLocation.isMock = true
        }
        locationManager.setTestProviderEnabled(LocationManager.GPS_PROVIDER, true)
        locationManager.setTestProviderLocation(LocationManager.GPS_PROVIDER, mockLocation)
    }

}


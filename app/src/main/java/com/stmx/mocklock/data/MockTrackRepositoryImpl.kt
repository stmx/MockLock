package com.stmx.mocklock.data

import com.stmx.mocklock.data.location.MockLocationProvider
import com.stmx.mocklock.domain.entity.GeoPoint
import com.stmx.mocklock.domain.repository.MockTrackRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow

class MockTrackRepositoryImpl @Inject constructor(
    private val mockLocationProvider: MockLocationProvider
) : MockTrackRepository {

    private val currentLocation: MutableStateFlow<GeoPoint?> = MutableStateFlow(null)
    private val currentPolyline: MutableSharedFlow<List<GeoPoint>?> = MutableSharedFlow()

    private val polyline: MutableList<GeoPoint> = mutableListOf()

    override suspend fun setMockLocation(point: GeoPoint?) {
        if (point != null) mockLocationProvider.setMockLocation(point)
        currentLocation.emit(point)
    }

    override suspend fun addPointToPolyline(point: GeoPoint) {
        polyline.add(point)
        currentPolyline.emit(polyline)
    }

    override suspend fun clearPolyline() {
        polyline.clear()
        currentPolyline.emit(polyline)
    }

    override fun observeMockLocation(): Flow<GeoPoint?> = currentLocation
    override fun observePolyline(): Flow<List<GeoPoint>?> = currentPolyline
}

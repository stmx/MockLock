package com.stmx.mocklock.domain.repository

import com.stmx.mocklock.domain.entity.GeoPoint
import kotlinx.coroutines.flow.Flow

interface MockTrackRepository {
    suspend fun setMockLocation(point: GeoPoint?)
    suspend fun addPointToPolyline(point: GeoPoint)
    suspend fun clearPolyline()
    fun observeMockLocation(): Flow<GeoPoint?>
    fun observePolyline(): Flow<List<GeoPoint>?>
}

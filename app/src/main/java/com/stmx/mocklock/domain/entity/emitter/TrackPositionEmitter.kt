package com.stmx.mocklock.domain.entity.emitter

import com.stmx.mocklock.domain.entity.GeoPoint
import com.stmx.mocklock.domain.entity.TrackConfiguration
import com.stmx.mocklock.domain.entity.calculator.PositionCalculator
import com.stmx.mocklock.domain.repository.MockTrackRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

interface TrackPositionEmitter {
    fun start(configuration: TrackConfiguration): Flow<Position>

    class Polyline @Inject constructor(
        private val mockTrackRepository: MockTrackRepository,
        private val progressEmitter: TimeProgressEmitter,
        private val positionCalculator: PositionCalculator,
    ) : TrackPositionEmitter {

        override fun start(configuration: TrackConfiguration): Flow<Position> =
            progressEmitter.start(configuration)
                .map { progress ->
                    val point = positionCalculator.calculate(configuration.points, progress)
                    Position(progress, point)
                }.onEach { position ->
                    if (position.progress >= 1.0) {
                        mockTrackRepository.setMockLocation(null)
                    } else {
                        mockTrackRepository.setMockLocation(position.point)
                    }
                }
    }

    class Position(val progress: Double, val point: GeoPoint)
}

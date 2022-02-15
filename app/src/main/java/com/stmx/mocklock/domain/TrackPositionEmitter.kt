package com.stmx.mocklock.domain

import com.stmx.mocklock.domain.entity.GeoPoint
import com.stmx.mocklock.domain.entity.TrackConfiguration
import com.stmx.mocklock.domain.entity.calculator.PositionCalculator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface TrackPositionEmitter {
    fun start(configuration: TrackConfiguration): Flow<Position>

    class Polyline(
        private val progressEmitter: TimeProgressEmitter,
        private val positionCalculator: PositionCalculator,
    ) : TrackPositionEmitter {

        override fun start(configuration: TrackConfiguration): Flow<Position> =
            progressEmitter.start(configuration)
                .map { progress ->
                    val point = positionCalculator.calculate(configuration.points, progress)
                    Position(progress, point)
                }
    }

    class Position(val progress: Double, val point: GeoPoint)
}
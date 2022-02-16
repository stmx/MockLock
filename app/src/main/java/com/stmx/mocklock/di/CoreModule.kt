package com.stmx.mocklock.di

import com.stmx.mocklock.domain.TimeProgressEmitter
import com.stmx.mocklock.domain.TrackPositionEmitter
import com.stmx.mocklock.domain.entity.calculator.DistanceCalculator
import com.stmx.mocklock.domain.entity.calculator.PositionCalculator
import com.stmx.mocklock.domain.entity.calculator.PositionEquationCalculator
import com.stmx.mocklock.domain.entity.calculator.TimeCalculator
import com.stmx.mocklock.domain.entity.equation.DistanceEquation
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
interface CoreModule {

    companion object {
        @Provides
        @Named("DEFAULT_MIN_PERIOD")
        fun defaultPeriod(): Long {
            return TimeProgressEmitter.Linear.DEFAULT_MIN_PERIOD
        }
    }

    @Binds
    fun bindDistanceCalculator(impl: DistanceCalculator.Polyline): DistanceCalculator

    @Binds
    fun bindPositionCalculator(impl: PositionCalculator.Polyline): PositionCalculator

    @Binds
    fun bindPositionEquationCalculator(impl: PositionEquationCalculator.Parametric): PositionEquationCalculator

    @Binds
    fun bindTimeCalculator(impl: TimeCalculator.Polyline): TimeCalculator

    @Binds
    fun bindDistanceEquation(impl: DistanceEquation.Haversine): DistanceEquation

    @Binds
    fun bindTimeProgressEmitter(impl: TimeProgressEmitter.Linear): TimeProgressEmitter

    @Binds
    fun bindTrackPositionEmitter(impl: TrackPositionEmitter.Polyline): TrackPositionEmitter
}
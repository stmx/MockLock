package com.stmx.mocklock.di

import androidx.lifecycle.ViewModelProvider
import com.stmx.mocklock.data.MockTrackRepositoryImpl
import com.stmx.mocklock.domain.entity.calculator.DistanceCalculator
import com.stmx.mocklock.domain.entity.calculator.PositionCalculator
import com.stmx.mocklock.domain.entity.calculator.PositionEquationCalculator
import com.stmx.mocklock.domain.entity.calculator.TimeCalculator
import com.stmx.mocklock.domain.entity.emitter.TimeProgressEmitter
import com.stmx.mocklock.domain.entity.emitter.TrackPositionEmitter
import com.stmx.mocklock.domain.entity.equation.DistanceEquation
import com.stmx.mocklock.domain.repository.MockTrackRepository
import com.stmx.mocklock.ui.MainViewModel
import dagger.Binds
import dagger.Module

@Module
interface CoreModule {

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
    @AppScope
    fun bindTrackPositionEmitter(impl: TrackPositionEmitter.Polyline): TrackPositionEmitter

    @Binds
    @AppScope
    fun bindMockTrackRepository(impl: MockTrackRepositoryImpl): MockTrackRepository

    @Binds
    fun bindViewModelProviderFactory(impl: MainViewModel.Factory): ViewModelProvider.Factory
}

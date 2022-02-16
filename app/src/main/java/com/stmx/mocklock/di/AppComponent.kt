package com.stmx.mocklock.di

import com.stmx.mocklock.TrackEmitterService
import com.stmx.mocklock.ui.MainActivity
import dagger.Component

@AppScope
@Component(modules = [CoreModule::class, MapperModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(locationService: TrackEmitterService)
}

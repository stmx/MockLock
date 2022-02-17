package com.stmx.mocklock.di

import android.content.Context
import com.stmx.mocklock.TrackEmitterService
import com.stmx.mocklock.ui.MainActivity
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(modules = [CoreModule::class, MapperModule::class, LocationModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(locationService: TrackEmitterService)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun build(): AppComponent
    }
}

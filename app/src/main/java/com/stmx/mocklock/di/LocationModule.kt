package com.stmx.mocklock.di

import android.content.Context
import android.location.LocationManager
import com.stmx.mocklock.data.location.MockLocationProvider
import com.stmx.mocklock.data.location.MockLocationProviderImpl
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface LocationModule {

    companion object {
        @AppScope
        @Provides
        fun provideLocationManager(applicationContext: Context): LocationManager {
            return applicationContext.getSystemService(LocationManager::class.java)
        }
    }

    @AppScope
    @Binds
    fun bindLocationProvider(impl: MockLocationProviderImpl): MockLocationProvider
}

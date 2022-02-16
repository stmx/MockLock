package com.stmx.mocklock.di

import com.stmx.mocklock.ui.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [CoreModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
}
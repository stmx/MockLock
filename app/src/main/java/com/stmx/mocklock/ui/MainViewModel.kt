package com.stmx.mocklock.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.stmx.mocklock.domain.TrackPositionEmitter
import javax.inject.Inject

class MainViewModel(
    private val trackPositionEmitter: TrackPositionEmitter
) : ViewModel() {


    @Suppress("UNCHECKED_CAST")
    class MainFactory @Inject constructor(
        private val trackPositionEmitter: TrackPositionEmitter
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(trackPositionEmitter) as T
        }
    }
}
package com.stmx.mocklock.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.stmx.mocklock.domain.entity.GeoPoint
import com.stmx.mocklock.domain.entity.Mapper
import com.stmx.mocklock.domain.repository.MockTrackRepository
import com.stmx.mocklock.domain.utils.mapNullable
import com.stmx.mocklock.ui.models.GeoPointUI
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel(
    private val repository: MockTrackRepository,
    private val uiMapper: Mapper<GeoPoint, GeoPointUI>,
    private val domainMapper: Mapper<GeoPointUI, GeoPoint>
) : ViewModel() {
    private val _liveData: MutableLiveData<MapState> = MutableLiveData()

    val liveData: LiveData<MapState> = _liveData

    init {
        _liveData.value = MapState()
        viewModelScope.launch {
            repository.observePolyline()
                .map { if (it.isNullOrEmpty()) null else it }
                .map { points -> points?.map(uiMapper::map) }
                .collect { points ->
                    _liveData.value = liveData.value?.copy(
                        polyline = points,
                        needInvalidateCenter = false,
                        needInvalidateZoom = false
                    )
                }
        }
    }

    fun startTrack() {
        viewModelScope.launch {
            repository.observeMockLocation()
                .map { point -> uiMapper.mapNullable(point) }
                .collect { point ->
                    _liveData.value = liveData.value?.copy(
                        currentMockPoint = point,
                        needInvalidateCenter = false,
                        needInvalidateZoom = false
                    )
                }
        }
    }

    fun addPointToPolyline(point: GeoPointUI) {
        viewModelScope.launch {
            repository.addPointToPolyline(domainMapper.map(point))
        }
    }

    fun clearPolyline() {
        viewModelScope.launch {
            repository.clearPolyline()
        }
    }

    class Factory @Inject constructor(
        private val repository: MockTrackRepository,
        private val uiMapper: Mapper<GeoPoint, GeoPointUI>,
        private val domainMapper: Mapper<GeoPointUI, GeoPoint>,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(repository, uiMapper, domainMapper) as T
        }
    }
}

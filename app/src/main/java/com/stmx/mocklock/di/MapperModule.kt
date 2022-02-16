package com.stmx.mocklock.di

import com.stmx.mocklock.domain.entity.GeoPoint
import com.stmx.mocklock.domain.entity.Mapper
import com.stmx.mocklock.ui.models.GeoPointUI
import dagger.Binds
import dagger.Module

@Module
interface MapperModule {

    @Binds
    fun bindGeoPointMapperToUI(impl: GeoPointUI.UIMapper): Mapper<GeoPoint, GeoPointUI>

    @Binds
    fun bindGeoPointMapperToDomain(impl: GeoPointUI.DomainMapper): Mapper<GeoPointUI, GeoPoint>
}

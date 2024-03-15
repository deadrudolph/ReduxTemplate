package com.deadrudolph.home.di.module

import com.deadrudolph.home.mapper.HomeResultToActionMapper
import dagger.Module
import dagger.Provides

@Module
internal class MapperModule {

    @Provides
    fun getHomeResultToActionMapper(): HomeResultToActionMapper = HomeResultToActionMapper()
}

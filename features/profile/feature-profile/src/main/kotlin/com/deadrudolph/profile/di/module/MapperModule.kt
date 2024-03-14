package com.deadrudolph.profile.di.module

import com.deadrudolph.profile.mapper.FirstProfileResultToActionMapper
import com.deadrudolph.profile.mapper.SecondProfileResultToActionMapper
import dagger.Module
import dagger.Provides

@Module
internal class MapperModule {

    @Provides
    fun getFirstProfileResultToActionMapper(): FirstProfileResultToActionMapper {
        return FirstProfileResultToActionMapper()
    }

    @Provides
    fun getSecondProfileResultToActionMapper(): SecondProfileResultToActionMapper {
        return SecondProfileResultToActionMapper()
    }
}

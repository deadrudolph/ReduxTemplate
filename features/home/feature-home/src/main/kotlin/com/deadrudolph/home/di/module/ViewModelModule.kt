package com.deadrudolph.home.di.module

import androidx.lifecycle.ViewModel
import com.deadrudolph.commondi.util.ViewModelKey
import com.deadrudolph.home.redux.HomeStore
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeStore::class)
    fun bindHomeViewModel(store: HomeStore): ViewModel
}

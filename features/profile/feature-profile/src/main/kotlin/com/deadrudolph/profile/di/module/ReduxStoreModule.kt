package com.deadrudolph.profile.di.module

import androidx.lifecycle.ViewModel
import com.deadrudolph.commondi.util.ViewModelKey
import com.deadrudolph.profile.redux.first.FirstProfileStore
import com.deadrudolph.profile.redux.second.SecondProfileStore
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal interface ReduxStoreModule {

    @Binds
    @IntoMap
    @ViewModelKey(FirstProfileStore::class)
    fun bindFirstProfileStore(store: FirstProfileStore): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SecondProfileStore::class)
    fun bindSecondProfileStore(store: SecondProfileStore): ViewModel
}

package com.deadrudolph.home.redux

import com.deadrudolph.core.base.store.ReduxStore
import com.deadrudolph.home.redux.HomeAction.FetchData
import javax.inject.Inject

internal class HomeStore @Inject constructor(
    reducer: HomeReducer,
    middleWare: HomeMiddleware? = null,
) : ReduxStore<HomeState, HomeAction>(
    reducer,
    middleWare,
    HomeState()
) {

    fun fetchContent(defaultErrorMessage: String) {
        dispatchAction(
            FetchData(
                defaultErrorMessage = defaultErrorMessage
            )
        )
    }
}

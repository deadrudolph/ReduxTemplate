package com.deadrudolph.home.redux

import com.deadrudolph.core.base.store.BaseReduxStore
import com.deadrudolph.home.redux.HomeAction.FetchData
import javax.inject.Inject

internal class HomeStore @Inject constructor(
    reducer: HomeReducer,
    middleWare: HomeMiddleware? = null,
) : BaseReduxStore<HomeState, HomeAction>(
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

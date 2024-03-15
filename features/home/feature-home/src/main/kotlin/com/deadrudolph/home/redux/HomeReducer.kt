package com.deadrudolph.home.redux

import com.deadrudolph.core.base.reducer.Reducer
import com.deadrudolph.home.redux.HomeAction.CancelLoading
import com.deadrudolph.home.redux.HomeAction.Data
import com.deadrudolph.home.redux.HomeAction.Error
import com.deadrudolph.home.redux.HomeAction.Loading
import com.deadrudolph.uicomponents.ui_model.ErrorModel
import com.deadrudolph.uicomponents.ui_model.LoadingModel
import javax.inject.Inject

internal class HomeReducer @Inject constructor() :
    Reducer<HomeState, HomeAction>() {

    override fun updateState(
        action: HomeAction,
        currentState: HomeState?
    ): HomeState? {
        return when (action) {
            is Error -> {
                currentState?.copy(
                    error = ErrorModel(action.message),
                    loading = LoadingModel(isLoading = false),
                )
            }

            is Loading -> {
                currentState?.copy(
                    error = null,
                    loading = LoadingModel(isLoading = true),
                )
            }

            is CancelLoading -> {
                currentState?.copy(loading = LoadingModel(isLoading = false))
            }

            is Data -> {
                currentState?.copy(
                    error = null,
                    data = action.data,
                )
            }

            else -> throw IllegalArgumentException("Action $action must be processed in middleware")
        }
    }
}

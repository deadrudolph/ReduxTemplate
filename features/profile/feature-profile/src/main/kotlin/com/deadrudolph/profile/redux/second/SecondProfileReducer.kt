package com.deadrudolph.profile.redux.second

import com.deadrudolph.core.base.reducer.Reducer
import com.deadrudolph.profile.redux.ProfileState
import com.deadrudolph.profile.redux.second.SecondProfileAction.CancelLoading
import com.deadrudolph.profile.redux.second.SecondProfileAction.Data
import com.deadrudolph.profile.redux.second.SecondProfileAction.Error
import com.deadrudolph.profile.redux.second.SecondProfileAction.Loading
import com.deadrudolph.profile_domain.domain.model.response.User
import com.deadrudolph.uicomponents.ui_model.ErrorModel
import com.deadrudolph.uicomponents.ui_model.LoadingModel
import javax.inject.Inject

internal class SecondProfileReducer @Inject constructor() :
    Reducer<ProfileState<User>, SecondProfileAction>() {

    override fun updateState(
        action: SecondProfileAction,
        currentState: ProfileState<User>?
    ): ProfileState<User>? {
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

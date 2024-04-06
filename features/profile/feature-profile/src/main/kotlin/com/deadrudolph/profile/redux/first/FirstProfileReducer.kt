package com.deadrudolph.profile.redux.first

import com.deadrudolph.core.base.reducer.Reducer
import com.deadrudolph.profile.redux.ProfileState
import com.deadrudolph.profile.redux.first.FirstProfileAction.CancelLoading
import com.deadrudolph.profile.redux.first.FirstProfileAction.Data
import com.deadrudolph.profile.redux.first.FirstProfileAction.Error
import com.deadrudolph.profile.redux.first.FirstProfileAction.Loading
import com.deadrudolph.profile_domain.domain.model.response.User
import com.deadrudolph.uicomponents.ui_model.ErrorModel
import com.deadrudolph.uicomponents.ui_model.LoadingModel
import kotlinx.collections.immutable.ImmutableList
import javax.inject.Inject

internal class FirstProfileReducer @Inject constructor() :
    Reducer<ProfileState<ImmutableList<User>>, FirstProfileAction>() {

    override fun updateState(
        action: FirstProfileAction,
        currentState: ProfileState<ImmutableList<User>>?
    ): ProfileState<ImmutableList<User>>? {
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

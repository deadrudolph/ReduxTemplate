package com.deadrudolph.profile.mapper

import com.deadrudolph.common_domain.state.Result
import com.deadrudolph.common_domain.state.Result.Error
import com.deadrudolph.common_domain.state.Result.Failure
import com.deadrudolph.common_domain.state.Result.Loading
import com.deadrudolph.common_domain.state.Result.Success
import com.deadrudolph.profile.redux.first.FirstProfileAction
import com.deadrudolph.profile_domain.domain.model.response.User
import kotlinx.collections.immutable.ImmutableList

internal class FirstProfileResultToActionMapper {

    operator fun invoke(
        result: Result<ImmutableList<User>>,
        defaultErrorMessage: String
    ): FirstProfileAction {
        return when (result) {
            is Success -> {
                FirstProfileAction.Data(result.data)
            }
            is Failure -> {
                FirstProfileAction.Error(result.exception.message ?: defaultErrorMessage)
            }
            is Error -> {
                FirstProfileAction.Error(result.message)
            }
            is Loading -> {
                if (result.state) {
                    FirstProfileAction.Loading
                } else {
                    FirstProfileAction.CancelLoading
                }
            }
            else -> FirstProfileAction.CancelLoading
        }
    }
}

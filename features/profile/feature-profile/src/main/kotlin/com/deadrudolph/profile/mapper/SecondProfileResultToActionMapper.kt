package com.deadrudolph.profile.mapper

import com.deadrudolph.common_domain.state.Result
import com.deadrudolph.common_domain.state.Result.Error
import com.deadrudolph.common_domain.state.Result.Failure
import com.deadrudolph.common_domain.state.Result.Loading
import com.deadrudolph.common_domain.state.Result.Success
import com.deadrudolph.profile.redux.second.SecondProfileAction
import com.deadrudolph.profile_domain.domain.model.response.User

internal class SecondProfileResultToActionMapper {

    operator fun invoke(
        result: Result<User>,
        defaultErrorMessage: String
    ): SecondProfileAction {
        return when (result) {
            is Success -> {
                SecondProfileAction.Data(result.data)
            }
            is Failure -> {
                SecondProfileAction.Error(result.exception.message ?: defaultErrorMessage)
            }
            is Error -> {
                SecondProfileAction.Error(result.message)
            }
            is Loading -> {
                if (result.state) {
                    SecondProfileAction.Loading
                } else {
                    SecondProfileAction.CancelLoading
                }
            }
            else -> SecondProfileAction.CancelLoading
        }
    }
}

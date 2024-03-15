package com.deadrudolph.home.mapper

import com.deadrudolph.common_domain.state.Result
import com.deadrudolph.common_domain.state.Result.Error
import com.deadrudolph.common_domain.state.Result.Failure
import com.deadrudolph.common_domain.state.Result.Loading
import com.deadrudolph.common_domain.state.Result.Success
import com.deadrudolph.home.redux.HomeAction
import com.deadrudolph.home_domain.domain.model.response.User

internal class HomeResultToActionMapper {

    operator fun invoke(
        result: Result<List<User>>,
        defaultErrorMessage: String
    ): HomeAction {
        return when (result) {
            is Success -> {
                HomeAction.Data(result.data)
            }

            is Failure -> {
                HomeAction.Error(result.exception.message ?: defaultErrorMessage)
            }

            is Error -> {
                HomeAction.Error(result.message)
            }

            is Loading -> {
                if (result.state) {
                    HomeAction.Loading
                } else {
                    HomeAction.CancelLoading
                }
            }

            else -> HomeAction.CancelLoading
        }
    }
}

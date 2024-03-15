package com.deadrudolph.home.redux

import com.deadrudolph.core.base.action.Action
import com.deadrudolph.home_domain.domain.model.response.User

internal sealed class HomeAction : Action {
    data object Loading : HomeAction()
    data class Data(val data: List<User>) : HomeAction()
    data object CancelLoading : HomeAction()
    data class Error(val message: String) : HomeAction()
    data class FetchData(
        val defaultErrorMessage: String
    ) : HomeAction()
}

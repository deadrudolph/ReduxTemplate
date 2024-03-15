package com.deadrudolph.profile.redux.second

import com.deadrudolph.core.base.action.Action
import com.deadrudolph.profile_domain.domain.model.response.User

internal sealed class SecondProfileAction : Action {
    data object Loading : SecondProfileAction()
    data class Data(val data: User) : SecondProfileAction()
    data object CancelLoading : SecondProfileAction()
    data class Error(val message: String) : SecondProfileAction()
    data class FetchData(
        val userId: String,
        val defaultErrorMessage: String
    ) : SecondProfileAction()

    data object NavigateToHome : SecondProfileAction()
}

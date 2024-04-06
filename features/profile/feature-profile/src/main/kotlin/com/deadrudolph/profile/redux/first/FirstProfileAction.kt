package com.deadrudolph.profile.redux.first

import com.deadrudolph.core.base.action.Action
import com.deadrudolph.profile_domain.domain.model.request.UserPageRequest
import com.deadrudolph.profile_domain.domain.model.response.User
import kotlinx.collections.immutable.ImmutableList

internal sealed class FirstProfileAction : Action {
    data object Loading : FirstProfileAction()
    data class Data(val data: ImmutableList<User>) : FirstProfileAction()
    data object CancelLoading : FirstProfileAction()
    data class Error(val message: String) : FirstProfileAction()
    data class FetchData(
        val request: UserPageRequest,
        val defaultErrorMessage: String
    ) : FirstProfileAction()

    data class NavigateToSecondPage(val userId: String) : FirstProfileAction()
}

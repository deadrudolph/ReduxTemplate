package com.deadrudolph.profile.redux.first

import com.deadrudolph.core.base.store.BaseReduxStore
import com.deadrudolph.profile.redux.ProfileState
import com.deadrudolph.profile.redux.first.FirstProfileAction.FetchData
import com.deadrudolph.profile.redux.first.FirstProfileAction.NavigateToSecondPage
import com.deadrudolph.profile_domain.domain.model.request.UserPageRequest
import com.deadrudolph.profile_domain.domain.model.response.User
import kotlinx.collections.immutable.ImmutableList
import javax.inject.Inject

internal class FirstProfileStore @Inject constructor(
    reducer: FirstProfileReducer,
    middleWare: FirstProfileMiddleware? = null,
) : BaseReduxStore<ProfileState<ImmutableList<User>>, FirstProfileAction>(
    reducer,
    middleWare,
    ProfileState()
) {

    fun fetchContent(defaultErrorMessage: String) {
        dispatchAction(
            FetchData(
                request = UserPageRequest(page = 1, limit = 1),
                defaultErrorMessage = defaultErrorMessage
            )
        )
    }

    fun navigateToNextProfilePage(userId: String) {
        dispatchAction(NavigateToSecondPage(userId))
    }
}

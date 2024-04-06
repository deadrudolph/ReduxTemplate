package com.deadrudolph.profile.redux.second

import com.deadrudolph.core.base.store.BaseReduxStore
import com.deadrudolph.profile.redux.ProfileState
import com.deadrudolph.profile.redux.second.SecondProfileAction.FetchData
import com.deadrudolph.profile.redux.second.SecondProfileAction.NavigateToHome
import com.deadrudolph.profile_domain.domain.model.response.User
import javax.inject.Inject

internal class SecondProfileStore @Inject constructor(
    reducer: SecondProfileReducer,
    middleWare: SecondProfileMiddleware? = null,
) : BaseReduxStore<ProfileState<User>, SecondProfileAction>(
    reducer,
    middleWare,
    ProfileState()
) {

    fun fetchContent(userId: String, defaultErrorMessage: String) {
        dispatchAction(
            FetchData(
                userId = userId,
                defaultErrorMessage = defaultErrorMessage
            )
        )
    }

    fun navigateToHome() {
        dispatchAction(NavigateToHome)
    }
}

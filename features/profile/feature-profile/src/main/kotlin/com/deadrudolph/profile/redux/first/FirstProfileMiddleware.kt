package com.deadrudolph.profile.redux.first

import com.deadrudolph.core.base.middleWare.MiddleWare
import com.deadrudolph.navigation.NavTarget
import com.deadrudolph.navigation.Navigator
import com.deadrudolph.profile.mapper.FirstProfileResultToActionMapper
import com.deadrudolph.profile.navigation.ProfileNavTarget
import com.deadrudolph.profile.redux.first.FirstProfileAction.CancelLoading
import com.deadrudolph.profile.redux.first.FirstProfileAction.FetchData
import com.deadrudolph.profile.redux.first.FirstProfileAction.Loading
import com.deadrudolph.profile.redux.first.FirstProfileAction.NavigateToSecondPage
import com.deadrudolph.profile_domain.domain.usecase.userspage.GetUsersPageUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class FirstProfileMiddleware @Inject constructor(
    private val getUsersPageUseCase: GetUsersPageUseCase,
    private val navigator: Navigator,
    private val mapper: FirstProfileResultToActionMapper
) : MiddleWare<FirstProfileAction> {

    override fun processAction(
        action: FirstProfileAction
    ): Flow<FirstProfileAction> {
        return flow {
            when (action) {
                is FetchData -> {
                    emit(Loading)
                    val result = getUsersPageUseCase(
                        action.request
                    ).run { mapper(this, action.defaultErrorMessage) }
                    emit(CancelLoading)
                    emit(result)
                }

                is NavigateToSecondPage -> {
                    navigator.navigateTo(
                        NavTarget
                            .Builder()
                            .addDestination(ProfileNavTarget.ProfileSecondFeature.route.route)
                            .addArgument(action.userId)
                            .build()
                    )
                }

                else -> {
                    emit(action)
                }
            }
        }
    }
}

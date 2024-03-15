package com.deadrudolph.profile.redux.second

import com.deadrudolph.core.base.middleWare.MiddleWare
import com.deadrudolph.navigation.GlobalNavTarget
import com.deadrudolph.navigation.Navigator
import com.deadrudolph.profile.mapper.SecondProfileResultToActionMapper
import com.deadrudolph.profile.redux.second.SecondProfileAction.CancelLoading
import com.deadrudolph.profile.redux.second.SecondProfileAction.FetchData
import com.deadrudolph.profile.redux.second.SecondProfileAction.Loading
import com.deadrudolph.profile.redux.second.SecondProfileAction.NavigateToHome
import com.deadrudolph.profile_domain.domain.usecase.getuser.GetUserByIdUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class SecondProfileMiddleware @Inject constructor(
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val navigator: Navigator,
    private val mapper: SecondProfileResultToActionMapper
) : MiddleWare<SecondProfileAction> {

    override fun processAction(
        action: SecondProfileAction
    ): Flow<SecondProfileAction> {
        return flow {
            when (action) {
                is FetchData -> {
                    emit(Loading)
                    val result = getUserByIdUseCase(
                        action.userId
                    ).run {
                        mapper(this, action.defaultErrorMessage)
                    }
                    emit(CancelLoading)
                    emit(result)
                }

                is NavigateToHome -> {
                    navigator.navigateTo(GlobalNavTarget.HomeModule.target)
                }

                else -> {
                    emit(action)
                }
            }
        }
    }
}

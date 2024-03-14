package com.deadrudolph.home.redux

import com.deadrudolph.core.base.middleWare.MiddleWare
import com.deadrudolph.home.mapper.HomeResultToActionMapper
import com.deadrudolph.home.redux.HomeAction.CancelLoading
import com.deadrudolph.home.redux.HomeAction.FetchData
import com.deadrudolph.home.redux.HomeAction.Loading
import com.deadrudolph.home_domain.domain.usecase.users.GetAllUsersUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeMiddleware @Inject constructor(
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val mapper: HomeResultToActionMapper
) : MiddleWare<HomeAction> {

    override fun processAction(
        action: HomeAction
    ): Flow<HomeAction> {
        return flow {
            when (action) {
                is FetchData -> {
                    emit(Loading)
                    val result = getAllUsersUseCase()
                        .run { mapper(this, action.defaultErrorMessage) }
                    emit(CancelLoading)
                    emit(result)
                }

                else -> {
                    emit(action)
                }
            }
        }
    }
}

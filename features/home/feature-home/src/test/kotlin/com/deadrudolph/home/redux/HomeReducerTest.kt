package com.deadrudolph.home.redux

import com.deadrudolph.common_test.mustBe
import com.deadrudolph.common_test.mustNotBe
import com.deadrudolph.home_domain.domain.model.response.User
import com.deadrudolph.uicomponents.ui_model.ErrorModel
import com.deadrudolph.uicomponents.ui_model.LoadingModel
import kotlinx.collections.immutable.persistentListOf
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class HomeReducerTest {

    private val reducer = HomeReducer()
    private val state = HomeState()

    @Test
    fun onError() {
        val action: HomeAction = HomeAction.Error(DEFAULT_MESSAGE)
        val newState = reducer.updateState(action, state)
        newState mustNotBe null
        newState!!
        newState.error mustBe ErrorModel(DEFAULT_MESSAGE)
    }

    @Test
    fun onLoading() {
        val action = HomeAction.Loading
        val newState = reducer.updateState(action, state)
        newState mustNotBe null
        newState!!
        newState.loading mustBe LoadingModel(true)
    }

    @Test
    fun onCancelLoading() {
        val action = HomeAction.CancelLoading
        val newState = reducer.updateState(action, state)
        newState mustNotBe null
        newState!!
        newState.loading mustBe LoadingModel(false)
    }

    @Test
    fun onData() {
        val list = persistentListOf<User>()
        val action = HomeAction.Data(list)
        val newState = reducer.updateState(action, state)
        newState mustNotBe null
        newState!!
        newState.data mustBe list
    }

    @Test
    fun onErrorThrow() {
        val action = HomeAction.FetchData(DEFAULT_MESSAGE)
        assertThrows<IllegalArgumentException>("Action $action must be processed in middleware") {
            reducer.updateState(action, state)
        }
    }

    private companion object {
        const val DEFAULT_MESSAGE = "DefaultMessage"
    }
}

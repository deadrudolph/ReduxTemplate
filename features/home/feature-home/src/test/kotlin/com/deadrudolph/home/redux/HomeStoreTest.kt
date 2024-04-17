package com.deadrudolph.home.redux

import com.deadrudolph.common_domain.state.Result.Error
import com.deadrudolph.common_domain.state.Result.Failure
import com.deadrudolph.common_domain.state.Result.Success
import com.deadrudolph.common_test.gives
import com.deadrudolph.common_test.mustBe
import com.deadrudolph.common_test.testFlow
import com.deadrudolph.home.mapper.HomeResultToActionMapper
import com.deadrudolph.home_domain.domain.model.response.User
import com.deadrudolph.home_domain.domain.usecase.users.GetAllUsersUseCase
import com.deadrudolph.uicomponents.ui_model.ErrorModel
import com.deadrudolph.uicomponents.ui_model.LoadingModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock

@OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)
internal class HomeStoreTest {

    private val reducer = HomeReducer()
    private val mapper = HomeResultToActionMapper()
    private val useCase: GetAllUsersUseCase = mock()
    private val middleWare = HomeMiddleware(useCase, mapper)
    private val user: User = mock()
    private val usersList = persistentListOf(user)

    private val store: HomeStore = HomeStore(reducer, middleWare)

    @Test
    fun fetchContentSuccessfully() = runTest {
        Dispatchers.setMain(newSingleThreadContext("SingleThread"))
        useCase() gives Success(usersList)
        store.fetchContent(DEFAULT_ERROR_MESSAGE)
        store.getState().testFlow {
            awaitItem() mustBe HomeState(loading = LoadingModel(true))
            awaitItem() mustBe HomeState(loading = LoadingModel(false))
            awaitItem() mustBe HomeState(
                loading = LoadingModel(false), data = usersList
            )
        }
        Dispatchers.resetMain()
    }

    @Test
    fun fetchContentWithError() = runTest {
        Dispatchers.setMain(newSingleThreadContext("SingleThread"))
        useCase() gives Error(401, "")
        store.fetchContent(DEFAULT_ERROR_MESSAGE)
        store.getState().testFlow {
            awaitItem() mustBe HomeState(loading = LoadingModel(true))
            awaitItem() mustBe HomeState(loading = LoadingModel(false))
            awaitItem() mustBe HomeState(
                loading = LoadingModel(false), error = ErrorModel("")
            )
        }
        Dispatchers.resetMain()
    }

    @Test
    fun fetchContentWithFailure() = runTest {
        Dispatchers.setMain(newSingleThreadContext("SingleThread"))
        useCase() gives Failure(IllegalArgumentException())
        store.fetchContent(DEFAULT_ERROR_MESSAGE)
        store.getState().testFlow {
            awaitItem() mustBe HomeState(loading = LoadingModel(true))
            awaitItem() mustBe HomeState(loading = LoadingModel(false))
            awaitItem() mustBe HomeState(
                loading = LoadingModel(false), error = ErrorModel(DEFAULT_ERROR_MESSAGE)
            )
        }
        Dispatchers.resetMain()
    }

    private companion object {

        const val DEFAULT_ERROR_MESSAGE = "Some message"
    }
}

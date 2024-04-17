package com.deadrudolph.home.redux

import com.deadrudolph.common_domain.state.Result
import com.deadrudolph.common_test.gives
import com.deadrudolph.common_test.mustBe
import com.deadrudolph.common_test.testFlow
import com.deadrudolph.home.mapper.HomeResultToActionMapper
import com.deadrudolph.home.redux.HomeAction.CancelLoading
import com.deadrudolph.home.redux.HomeAction.Data
import com.deadrudolph.home.redux.HomeAction.Error
import com.deadrudolph.home.redux.HomeAction.FetchData
import com.deadrudolph.home.redux.HomeAction.Loading
import com.deadrudolph.home_domain.domain.model.response.User
import com.deadrudolph.home_domain.domain.usecase.users.GetAllUsersUseCase
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock

class HomeMiddlewareTest {

    private val useCase: GetAllUsersUseCase = mock()
    private val mapper = HomeResultToActionMapper()
    private val testUsers = getTestUsersList()
    private val homeMiddleWare = HomeMiddleware(useCase, mapper)

    @Test
    fun onLoading() {
        homeMiddleWare.processAction(Loading).testFlow {
            awaitItem() mustBe Loading
            awaitComplete()
        }
    }

    @Test
    fun onFetchDataSuccessfully() = runTest {
        useCase.invoke() gives Result.Success(testUsers)
        homeMiddleWare.processAction(FetchData(DEFAULT_ERROR_MESSAGE)).testFlow {
            awaitItem() mustBe Loading
            awaitItem() mustBe CancelLoading
            awaitItem() mustBe Data(testUsers)
            awaitComplete()
        }
    }

    @Test
    fun onFetchDataFailureWithMessage() = runTest {
        useCase.invoke() gives Result.Failure(Exception("TestError"))
        homeMiddleWare.processAction(FetchData(DEFAULT_ERROR_MESSAGE)).testFlow {
            awaitItem() mustBe Loading
            awaitItem() mustBe CancelLoading
            awaitItem() mustBe Error("TestError")
            awaitComplete()
        }
    }

    @Test
    fun onFetchDataFailureWithNoMessage() = runTest {
        useCase.invoke() gives Result.Failure(Exception())
        homeMiddleWare.processAction(FetchData(DEFAULT_ERROR_MESSAGE)).testFlow {
            awaitItem() mustBe Loading
            awaitItem() mustBe CancelLoading
            awaitItem() mustBe Error(DEFAULT_ERROR_MESSAGE)
            awaitComplete()
        }
    }

    @Test
    fun onFetchDataError() = runTest {
        useCase.invoke() gives Result.Error(401, "Unauthorized")
        homeMiddleWare.processAction(FetchData(DEFAULT_ERROR_MESSAGE)).testFlow {
            awaitItem() mustBe Loading
            awaitItem() mustBe CancelLoading
            awaitItem() mustBe Error("Unauthorized")
            awaitComplete()
        }
    }

    private fun getTestUsersList() = listOf(
        User(
            id = 123123L,
            avatar = "someAvatarUrl",
            email = "testUser@gmail.com",
            firstName = "TestFirstName",
            lastName = "TestLastName"
        )
    ).toImmutableList()

    private companion object {
        const val DEFAULT_ERROR_MESSAGE = "Error"
    }
}

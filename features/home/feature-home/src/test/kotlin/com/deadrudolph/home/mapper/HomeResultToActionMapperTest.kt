package com.deadrudolph.home.mapper

import com.deadrudolph.common_domain.state.Result.Error
import com.deadrudolph.common_domain.state.Result.Failure
import com.deadrudolph.common_domain.state.Result.Loading
import com.deadrudolph.common_domain.state.Result.Success
import com.deadrudolph.common_test.mustBe
import com.deadrudolph.home.redux.HomeAction
import com.deadrudolph.home.redux.HomeAction.CancelLoading
import com.deadrudolph.home_domain.domain.model.response.User
import kotlinx.collections.immutable.persistentListOf
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock

internal class HomeResultToActionMapperTest {

    private val mapper = HomeResultToActionMapper()
    private val mockUser: User = mock()

    @Test
    fun mapResults() {
        // Result success
        val resultSuccess = Success(persistentListOf(mockUser))
        mapper(resultSuccess, DEFAULT_ERROR_MESSAGE) mustBe HomeAction.Data(resultSuccess.data)

        // Result failure with no message
        val resultFailureNoMessage = Failure(IllegalArgumentException())
        mapper(resultFailureNoMessage, DEFAULT_ERROR_MESSAGE) mustBe HomeAction.Error(DEFAULT_ERROR_MESSAGE)

        // Result Failure with message
        val resultFailureWithMessage = Failure(IllegalArgumentException("Message"))
        mapper(
            resultFailureWithMessage,
            DEFAULT_ERROR_MESSAGE
        ) mustBe HomeAction.Error("Message")

        // Result Error
        val resultError = Error(401, "Unauthorized")
        mapper(
            resultError,
            DEFAULT_ERROR_MESSAGE
        ) mustBe HomeAction.Error("Unauthorized")

        // Result Loading = true
        val loadingTrue = Loading(true)
        mapper(loadingTrue, DEFAULT_ERROR_MESSAGE) mustBe HomeAction.Loading

        // Result Loading = false
        val loadingFalse = Loading(false)
        mapper(loadingFalse, DEFAULT_ERROR_MESSAGE) mustBe CancelLoading
    }

    private companion object {
        const val DEFAULT_ERROR_MESSAGE = "Some message"
    }
}

package com.deadrudolph.home.redux

import com.deadrudolph.home_domain.domain.model.response.User
import com.deadrudolph.uicomponents.ui_model.ErrorModel
import com.deadrudolph.uicomponents.ui_model.LoadingModel
import kotlinx.collections.immutable.ImmutableList

internal data class HomeState(
    val data: ImmutableList<User>? = null,
    val error: ErrorModel? = null,
    val loading: LoadingModel? = null
)

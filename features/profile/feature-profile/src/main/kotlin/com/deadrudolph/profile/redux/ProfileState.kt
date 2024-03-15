package com.deadrudolph.profile.redux

import com.deadrudolph.uicomponents.ui_model.ErrorModel
import com.deadrudolph.uicomponents.ui_model.LoadingModel

internal data class ProfileState<D>(
    val data: D? = null,
    val error: ErrorModel? = null,
    val loading: LoadingModel? = null
)

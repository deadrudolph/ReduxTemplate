package com.deadrudolph.navigation

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

/**
 * Represents a state flow holder for NavTarget
 * */
class Navigator {

    private val _targetsSharedFlow = MutableSharedFlow<NavTarget>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )
    val targetsSharedFlow = _targetsSharedFlow.asSharedFlow()

    fun navigateTo(navTarget: NavTarget) {
        _targetsSharedFlow.tryEmit(navTarget)
    }
}

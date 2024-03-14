package com.deadrudolph.core.base.middleWare

import com.deadrudolph.core.base.action.Action
import kotlinx.coroutines.flow.Flow

interface MiddleWare<A : Action> {

    fun processAction(
        action: A
    ): Flow<A>
}

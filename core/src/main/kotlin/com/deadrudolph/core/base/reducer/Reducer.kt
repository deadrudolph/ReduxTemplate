package com.deadrudolph.core.base.reducer

import com.deadrudolph.core.base.action.Action

abstract class Reducer<State, A : Action> {

    abstract fun updateState(action: A, currentState: State?): State?
}

package com.deadrudolph.core.base.store

import androidx.lifecycle.ViewModel
import com.deadrudolph.core.base.action.Action
import com.deadrudolph.core.base.middleWare.MiddleWare
import com.deadrudolph.core.base.middleWare.processActionOrDispatchImmediately
import com.deadrudolph.core.base.reducer.Reducer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

open class BaseReduxStore<S, A : Action>(
    private val reducer: Reducer<S, A>,
    private val middleWare: MiddleWare<A>? = null,
    initialState: S? = null
) : ViewModel() {

    private val state: MutableStateFlow<S?> = MutableStateFlow(initialState)

    fun dispatchAction(action: A) {
        middleWare.processActionOrDispatchImmediately(action) { processedAction ->
            state.value = reducer.updateState(processedAction, state.value)
        }
    }

    fun getState(): StateFlow<S?> = state
}

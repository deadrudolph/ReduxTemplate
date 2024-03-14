package com.deadrudolph.core.base.store

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deadrudolph.core.base.action.Action
import com.deadrudolph.core.base.middleWare.MiddleWare
import com.deadrudolph.core.base.reducer.Reducer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

open class ReduxStore<S, A : Action>(
    private val reducer: Reducer<S, A>,
    private val middleWare: MiddleWare<A>? = null,
    initialState: S? = null
) : ViewModel() {

    private val state: MutableStateFlow<S?> = MutableStateFlow(initialState)

    fun dispatchAction(action: A) {
        viewModelScope.launch {
            middleWare?.processAction(action)?.collect { action ->
                state.value = reducer.updateState(action, state.value)
            } ?: run {
                state.value = reducer.updateState(action, state.value)
            }
        }
    }

    fun getState(): StateFlow<S?> = state
}

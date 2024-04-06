package com.deadrudolph.core.base.middleWare

import androidx.lifecycle.viewModelScope
import com.deadrudolph.core.base.action.Action
import com.deadrudolph.core.base.store.BaseReduxStore
import kotlinx.coroutines.launch

context(BaseReduxStore<*, *>)
fun <T : Action>MiddleWare<T>?.processActionOrDispatchImmediately(
    action: T,
    onActionReady: (T) -> Unit
) {
    if (this == null) {
        onActionReady(action)
    } else {
        viewModelScope.launch {
            processAction(action).collect { processedAction ->
                onActionReady(processedAction)
            }
        }
    }
}

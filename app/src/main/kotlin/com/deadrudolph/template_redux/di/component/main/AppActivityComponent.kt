package com.deadrudolph.template_redux.di.component.main

import com.deadrudolph.template_redux.presentation.ui.activity.MainActivity

internal interface AppActivityComponent {

    fun inject(activity: MainActivity)
}

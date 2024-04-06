package com.deadrudolph.home.presentation.ui.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.deadrudolph.feature_home.R
import com.deadrudolph.home.redux.HomeStore
import com.deadrudolph.home_domain.domain.model.response.User
import com.deadrudolph.uicomponents.R.string
import com.deadrudolph.uicomponents.compose.components.DefaultErrorDialog
import com.deadrudolph.uicomponents.compose.components.DefaultLoading
import kotlinx.collections.immutable.ImmutableList

@Composable
internal fun HomeScreen(
    store: HomeStore
) {
    val currentState = store.getState().collectAsState()
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        store.fetchContent(
            context.getString(string.common_error)
        )
    }

    currentState.value?.loading?.let { loadingState ->
        if (loadingState.isLoading) {
            DefaultLoading()
        }
    }

    currentState.value?.error?.let { errorState ->
        DefaultErrorDialog(errorState.message) {
            store.fetchContent(
                context.getString(string.common_error)
            )
        }
    }

    currentState.value?.data?.let {
        ScreenContent(
            usersList = it
        )
    }
}

@Composable
private fun ScreenContent(usersList: ImmutableList<User>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Header()
        UsersList(
            modifier = Modifier
                .weight(1f)
                .padding(top = 20.dp),
            usersList = usersList
        )
    }
}

@Composable
private fun Header(
    modifier: Modifier = Modifier
) {
    Text(
        modifier = Modifier
            .then(modifier),
        text = stringResource(id = R.string.fragment_home_title)
    )
}

@Composable
private fun UsersList(
    modifier: Modifier = Modifier,
    usersList: ImmutableList<User>
) {
    LazyColumn(
        modifier = Modifier
            .then(modifier),
        content = {
            items(
                items = usersList,
                key = { item -> item.id }
            ) { data ->
                UserItem(
                    userName = "${data.firstName} ${data.lastName}",
                    email = data.email
                )
            }
        },
        verticalArrangement = Arrangement.spacedBy(24.dp)
    )
}

@Composable
private fun UserItem(
    modifier: Modifier = Modifier,
    userName: String,
    email: String
) {
    Column(
        modifier = Modifier
            .then(modifier)
    ) {
        Text(text = stringResource(id = R.string.user_name_template, userName))
        Text(
            modifier = Modifier
                .padding(top = 10.dp),
            text = stringResource(id = R.string.user_address_template, email)
        )
    }
}

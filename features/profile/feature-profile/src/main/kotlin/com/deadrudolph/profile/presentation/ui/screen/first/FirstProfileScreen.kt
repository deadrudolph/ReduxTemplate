package com.deadrudolph.profile.presentation.ui.screen.first

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.deadrudolph.feature_profile.R
import com.deadrudolph.profile.redux.first.FirstProfileStore
import com.deadrudolph.profile_domain.domain.model.response.User
import com.deadrudolph.uicomponents.compose.components.DefaultErrorDialog
import com.deadrudolph.uicomponents.compose.components.DefaultLoading
import kotlinx.collections.immutable.ImmutableList

@Composable
internal fun FirstProfileScreen(
    store: FirstProfileStore
) {
    val currentState = store.getState().collectAsState()
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        store.fetchContent(context.getString(com.deadrudolph.uicomponents.R.string.common_error))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        currentState.value?.data?.let {
            ScreenContent(
                usersList = it,
                onClickNext = store::navigateToNextProfilePage
            )
        }

        currentState.value?.loading?.let { loadingState ->
            if (loadingState.isLoading) {
                DefaultLoading()
            }
        }

        currentState.value?.error?.let { errorState ->
            DefaultErrorDialog(errorState.message) {
                store.fetchContent(context.getString(com.deadrudolph.uicomponents.R.string.common_error))
            }
        }
    }
}

@Composable
private fun ScreenContent(
    usersList: ImmutableList<User>,
    onClickNext: (userId: String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Header()
        UsersList(
            modifier = Modifier
                .weight(1f)
                .padding(top = 20.dp),
            usersList = usersList,
            onItemClick = onClickNext
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
        text = stringResource(id = R.string.fragment_profile_title)
    )
}

@Composable
private fun UsersList(
    modifier: Modifier = Modifier,
    usersList: List<User>,
    onItemClick: (userId: String) -> Unit
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
                    address = data.email,
                    userID = data.id.toString(),
                    userAvatar = data.avatar,
                    onItemClick = onItemClick
                )
            }
        },
        verticalArrangement = Arrangement.spacedBy(24.dp)
    )
}

@Composable
private fun UserItem(
    modifier: Modifier = Modifier,
    userID: String,
    userName: String,
    userAvatar: String,
    address: String,
    onItemClick: (userId: String) -> Unit
) {
    Row(
        modifier = Modifier
            .clickable { onItemClick(userID) }
            .then(modifier)
    ) {
        AsyncImage(
            modifier = Modifier
                .size(100.dp, 100.dp)
                .padding(10.dp),
            model = ImageRequest.Builder(LocalContext.current)
                .data(userAvatar)
                .transformations(listOf(CircleCropTransformation()))
                .build(),
            contentDescription = "Avatar"
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .align(Alignment.CenterVertically)
        ) {
            Text(text = stringResource(id = R.string.user_name_template, userName))
            Text(
                modifier = Modifier
                    .padding(top = 10.dp),
                text = stringResource(id = R.string.user_address_template, address)
            )
        }
    }
}

package com.ibrajix.sabinews.screens.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.ibrajix.sabinews.R
import com.ibrajix.sabinews.style.theme.textFieldOutlineColor
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchWidget(
    navigator: DestinationsNavigator,
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
){

    val focusRequester = FocusRequester()
    val keyboardController = LocalSoftwareKeyboardController.current

    DisposableEffect(Unit) {
        focusRequester.requestFocus()
        onDispose { }
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
    ) {

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .onFocusChanged {
                    if (it.isFocused) {
                        keyboardController?.show()
                    }
                },
            value = text,
            onValueChange = {
                onTextChange(it)
            },
            placeholder = {
                Text(
                    text = stringResource(id = R.string.search_news),
                    style = MaterialTheme.typography.h4
                )
            },
            textStyle = MaterialTheme.typography.h4,
            singleLine = true,
            leadingIcon = {
                IconButton(
                    onClick = {
                        navigator.popBackStack()
                    }
                ){
                    Icon(imageVector = Icons.Default.ArrowBack , contentDescription = stringResource(
                        id = R.string.icon
                    )
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        if (text.isNotEmpty()){
                            onTextChange("")
                        }
                        else{
                            onCloseClicked()
                        }
                    }
                ){
                    Icon(
                        imageVector = Icons.Default.Close, contentDescription = stringResource(
                            id = R.string.icon
                        )
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch ={
                    onSearchClicked(text)
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                cursorColor = MaterialTheme.colors.primary,
                focusedIndicatorColor = MaterialTheme.colors.textFieldOutlineColor,
                unfocusedIndicatorColor = MaterialTheme.colors.textFieldOutlineColor,
            ),
        )
    }
}
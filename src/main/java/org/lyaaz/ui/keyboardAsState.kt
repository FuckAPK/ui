package org.lyaaz.ui

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.runtime.*

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun keyboardAsState(): State<Boolean> {
    val isImeVisible = WindowInsets.isImeVisible
    return rememberUpdatedState(isImeVisible)
}

package org.lyaaz.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun TextFieldPreference(
    @StringRes title: Int,
    @StringRes summary: Int? = null,
    @StringRes unit: Int? = null,
    enabled: Boolean = true,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Text(
            text = stringResource(id = title),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(if (enabled) 1.0f else 0.6f)
        )
        if (summary != null) {
            Text(
                text = stringResource(id = summary),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(if (enabled) 0.6f else 0.4f)
            )
        }
        val focusManager = LocalFocusManager.current
        OutlinedTextField(
            enabled = enabled,
            value = value,
            onValueChange = onValueChange,
            singleLine = false,
            maxLines = 3,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = keyboardType,
                imeAction = ImeAction.Done
            ),
            suffix = unit?.let {
                @Composable {
                    Text(
                        text = stringResource(id = it),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary.copy(if (enabled) 1.0f else 0.6f)
                    )
                }
            },
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            textStyle = MaterialTheme.typography.bodyMedium
        )
    }
}

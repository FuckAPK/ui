package org.lyaaz.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun DropDownPreference(
    @StringRes title: Int,
    @StringRes summary: Int? = null,
    enabled: Boolean = true,
    @StringRes selected: Int,
    content: @Composable ColumnScope.(onItemSelected: () -> Unit) -> Unit
) {
    var expended by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .height(50.dp)
            .clickable { expended = true }) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = stringResource(id = title),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(if (enabled) 1.0f else 0.6f)
                )
                if (summary != null) {
                    Text(
                        text = stringResource(id = summary),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(if (enabled) 1.0f else 0.6f)
                    )
                }
            }
            Box(
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    text = stringResource(selected),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary.copy(if (enabled) 1.0f else 0.6f)
                )
                DropdownMenu(
                    expanded = expended,
                    onDismissRequest = { expended = false },
                ) {
                    if (enabled) {
                        content { expended = false }
                    }
                }
            }
        }
    }
}

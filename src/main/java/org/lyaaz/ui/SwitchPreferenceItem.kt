package org.lyaaz.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

/**
 * A flat row for [PreferenceGroup] or [PreferenceCategory].
 * Without [onClick], the entire row is one accessible toggle. With [onClick],
 * the row performs that action and only the separate switch changes [checked].
 * State and persistence remain the caller's responsibility.
 * Line constraints reserve summary space and truncate bounded text visually;
 * accessibility retains the complete title and summary.
 * [summaryVerticallyCentered] centers the actual summary within its reserved lines.
 */
@Composable
fun SwitchPreferenceItem(
    title: String,
    summary: String? = null,
    checked: Boolean,
    enabled: Boolean = true,
    onCheckedChange: (Boolean) -> Unit,
    onClick: (() -> Unit)? = null,
    leadingContent: (@Composable () -> Unit)? = null,
    modifier: Modifier = Modifier,
    titleMaxLines: Int = Int.MAX_VALUE,
    summaryMinLines: Int = 1,
    summaryMaxLines: Int = Int.MAX_VALUE,
    summaryVerticallyCentered: Boolean = false
) {
    val interactionModifier = if (onClick == null) {
        Modifier.toggleable(
            value = checked,
            enabled = enabled,
            role = Role.Switch,
            onValueChange = { if (enabled) onCheckedChange(it) }
        )
    } else {
        Modifier.clickable(enabled = enabled) {
            if (enabled) onClick()
        }
    }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 64.dp)
            .then(interactionModifier)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (leadingContent != null) {
            Box(
                modifier = Modifier.graphicsLayer { alpha = if (enabled) 1f else 0.38f },
                contentAlignment = Alignment.Center
            ) {
                leadingContent()
            }
        }
        PreferenceText(
            title = title,
            summary = summary,
            enabled = enabled,
            modifier = Modifier.weight(1f),
            titleMaxLines = titleMaxLines,
            summaryMinLines = summaryMinLines,
            summaryMaxLines = summaryMaxLines,
            summaryVerticallyCentered = summaryVerticallyCentered
        )
        if (onClick != null) {
            Box(
                modifier = Modifier
                    .width(1.dp)
                    .height(24.dp)
                    .background(MaterialTheme.colorScheme.outlineVariant)
            )
        }
        Switch(
            checked = checked,
            enabled = enabled,
            onCheckedChange = if (onClick == null) null else {
                { value -> if (enabled) onCheckedChange(value) }
            },
            modifier = Modifier
                .sizeIn(minWidth = 48.dp, minHeight = 48.dp)
                .semantics {
                    if (onClick != null) contentDescription = title
                }
        )
    }
}

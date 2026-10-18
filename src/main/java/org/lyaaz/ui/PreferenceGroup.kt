package org.lyaaz.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kotlin.math.ceil

/**
 * Clips preference rows to a shared card surface without adding inner insets.
 * Rows own their padding so their interaction and ripple cover the card bounds.
 */
@Composable
fun PreferenceGroup(
    modifier: Modifier = Modifier,
    highlighted: Boolean = false,
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = if (highlighted) {
            MaterialTheme.colorScheme.secondaryContainer
        } else {
            MaterialTheme.colorScheme.surfaceContainerLow
        }
    ) {
        Column(content = content)
    }
}

@Composable
internal fun PreferenceText(
    title: String,
    summary: String?,
    enabled: Boolean,
    modifier: Modifier = Modifier,
    titleMaxLines: Int = Int.MAX_VALUE,
    summaryMinLines: Int = 1,
    summaryMaxLines: Int = Int.MAX_VALUE,
    summaryVerticallyCentered: Boolean = false
) {
    Column(modifier) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = if (enabled) 1f else 0.38f),
            maxLines = titleMaxLines,
            overflow = if (titleMaxLines == Int.MAX_VALUE) TextOverflow.Clip else TextOverflow.Ellipsis
        )
        if (summary != null) {
            val summaryStyle = MaterialTheme.typography.bodyMedium
            Text(
                text = summary,
                modifier = if (summaryVerticallyCentered) {
                    Modifier
                        .heightIn(min = with(LocalDensity.current) {
                            val fontSizePx = summaryStyle.fontSize.toPx()
                            // Match Compose's font-relative line height under nonlinear scaling.
                            val lineHeightPx =
                                summaryStyle.lineHeight.value / fontSizePx.toSp().value * fontSizePx
                            (ceil(lineHeightPx) * summaryMinLines).toDp()
                        })
                        .wrapContentHeight(Alignment.CenterVertically)
                } else {
                    Modifier
                },
                style = summaryStyle,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = if (enabled) 1f else 0.38f),
                minLines = if (summaryVerticallyCentered) 1 else summaryMinLines,
                maxLines = summaryMaxLines,
                overflow = if (summaryMaxLines == Int.MAX_VALUE) TextOverflow.Clip else TextOverflow.Ellipsis
            )
        }
    }
}

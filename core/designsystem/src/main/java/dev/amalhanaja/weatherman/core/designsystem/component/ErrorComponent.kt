package dev.amalhanaja.weatherman.core.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import dev.amalhanaja.weatherman.core.designsystem.foundation.WMTheme


@Composable
fun ErrorComponent(
    title: String,
    actionText: String,
    onActionClick: () -> Unit,
    modifier: Modifier = Modifier,
    description: String? = null,
    illustration: (@Composable () -> Unit)? = null,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            illustration?.let {
                it.invoke()
                Spacer(modifier = Modifier.height(WMTheme.spacings.l))
            }
            Text(
                text = title,
                style = WMTheme.typography.labelLarge,
                maxLines = 1,
                overflow = TextOverflow.Clip,
            )
            description?.let {
                Spacer(modifier = Modifier.height(WMTheme.spacings.s))
                Text(
                    text = description,
                    style = WMTheme.typography.labelLarge,
                    modifier = Modifier.testTag("error-description")
                )
            }
            Spacer(modifier = Modifier.height(WMTheme.spacings.m))
            TextButton(
                onClick = onActionClick,
            ) {
                Text(text = actionText)
            }
        }
    }
}

package com.example.movplayv3.ui.components.chips

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movplayv3.ui.theme.spacing

@Composable
fun MovplaySelectableGenreChip(
    text: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    val backgroundColor by animateColorAsState(targetValue = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface)

    Box(
        modifier = modifier
            .background(shape = RoundedCornerShape(50f), color = backgroundColor)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary.copy(0.5f),
                shape = RoundedCornerShape(50f)
            )
            .padding(
                horizontal = MaterialTheme.spacing.medium,
                vertical = MaterialTheme.spacing.small
            )
            .clickable(enabled = onClick != null, onClick = { onClick?.invoke() })
    ) {
        Text(
            text = text,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}
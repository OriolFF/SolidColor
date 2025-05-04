package com.uriolus.solidlight

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * Candle button composable
 */
@Composable
fun CandleButton(
    active: Boolean,
    onTap: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(128.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(if (active) Color.Yellow.copy(alpha = 0.3f) else Color.White)
            .clickable { onTap() }
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_candle),
            contentDescription = "Candle mode",
            modifier = Modifier.size(64.dp)
        )
        
        if (active) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = Color.DarkGray,
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.BottomEnd)
                    .padding(4.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CandleButtonPreview() {
    Box(
        modifier = Modifier.background(Color.Gray.copy(alpha = 0.2f)),
        contentAlignment = Alignment.Center
    ) {
        CandleButton(
            active = true,
            onTap = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CandleButtonInactivePreview() {
    Box(
        modifier = Modifier.background(Color.Gray.copy(alpha = 0.2f)),
        contentAlignment = Alignment.Center
    ) {
        CandleButton(
            active = false,
            onTap = {}
        )
    }
}

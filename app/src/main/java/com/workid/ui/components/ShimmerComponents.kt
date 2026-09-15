package com.workid.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.workid.ui.theme.DarkSlate
import com.workid.ui.theme.DarkSlateLight
import com.workid.ui.theme.EmeraldGreen
import com.workid.ui.theme.MutedGrey

@Composable
fun ShimmerCard(
    modifier: Modifier = Modifier,
    height: Int = 120
) {
    androidx.compose.animation.core.animateFloatAsState(
        targetValue = 1f,
        label = "shimmer"
    )
    
    androidx.compose.foundation.layout.Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(DarkSlateLight)
    ) {
        // Shimmer effect placeholder
        androidx.compose.animation.core.Animatable(0f).let { anim ->
            androidx.compose.runtime.LaunchedEffect(Unit) {
                anim.animateTo(1f)
            }
        }
        
        androidx.compose.foundation.layout.Box(
            modifier = Modifier
                .matchParentSize()
                .clip(RoundedCornerShape(16.dp))
                .background(
                    androidx.compose.ui.graphics.Brush.horizontalGradient(
                        colors = listOf(
                            DarkSlateLight,
                            com.workid.ui.theme.ShimmerHighlight,
                            DarkSlateLight
                        )
                    )
                )
        )
    }
}

@Composable
fun ShimmerQuickActionGrid(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            repeat(2) {
                ShimmerCard(modifier = Modifier.size(80.dp), height = 80)
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            repeat(2) {
                ShimmerCard(modifier = Modifier.size(80.dp), height = 80)
            }
        }
    }
}

@Composable
fun ShimmerSummaryCards(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        ShimmerCard(height = 100)
        Spacer(modifier = Modifier.height(12.dp))
        ShimmerCard(height = 140)
        Spacer(modifier = Modifier.height(12.dp))
        ShimmerCard(height = 120)
    }
}

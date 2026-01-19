package com.kazz.cinetoo.presentation.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kazz.cinetoo.presentation.theme.PrimaryViolet
import org.koin.compose.koinInject

@Composable
fun SplashScreen(
    onNavigateToOnboarding: () -> Unit,
    onNavigateToHome: () -> Unit,
    viewModel: SplashViewModel = koinInject()
) {
    val alpha = remember { Animatable(0f) }
    val destination by viewModel.destination.collectAsState()

    LaunchedEffect(Unit) {
        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1000)
        )
    }

    LaunchedEffect(destination) {
        destination?.let {
            when (it) {
                SplashDestination.Onboarding -> onNavigateToOnboarding()
                SplashDestination.Home -> onNavigateToHome()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.alpha(alpha.value)
        ) {
            FilmReelLogo(
                modifier = Modifier.size(40.dp),
                color = PrimaryViolet
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Ciné too",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Composable
private fun FilmReelLogo(
    modifier: Modifier = Modifier,
    color: Color = PrimaryViolet
) {
    val darkColor = Color(0xFF0A0E27)

    Box(
        modifier = modifier
            .background(color, RoundedCornerShape(50))
    ) {
        Canvas(
            modifier = Modifier
                .matchParentSize()
        ) {
            val center = Offset(size.width / 2, size.height / 2)
            val radius = size.minDimension / 2

            // Film holes (4 holes arranged in a pattern)
            val holeRadius = radius * 0.18f
            val holeDistance = radius * 0.5f

            // Top-left hole
            drawCircle(
                color = darkColor,
                radius = holeRadius,
                center = Offset(center.x - holeDistance, center.y - holeDistance)
            )

            // Top-right hole
            drawCircle(
                color = darkColor,
                radius = holeRadius,
                center = Offset(center.x + holeDistance, center.y - holeDistance)
            )

            // Bottom-left hole
            drawCircle(
                color = darkColor,
                radius = holeRadius,
                center = Offset(center.x - holeDistance, center.y + holeDistance)
            )

            // Bottom-right hole
            drawCircle(
                color = darkColor,
                radius = holeRadius,
                center = Offset(center.x + holeDistance, center.y + holeDistance)
            )

            // Center hole (slightly larger)
            drawCircle(
                color = darkColor,
                radius = holeRadius * 1.2f,
                center = center
            )
        }
    }
}

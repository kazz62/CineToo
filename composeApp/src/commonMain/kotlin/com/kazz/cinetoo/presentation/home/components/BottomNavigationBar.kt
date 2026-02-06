package com.kazz.cinetoo.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kazz.cinetoo.presentation.theme.PrimaryViolet

enum class BottomNavTab {
    HOME, FAVORITES, DISCOVER, SETTINGS
}

@Composable
fun BottomNavigationBar(
    selectedTab: BottomNavTab,
    onTabSelected: (BottomNavTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFF0A0E27))
            .navigationBarsPadding()
            .padding(vertical = 12.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomNavItem(
            icon = "🏠",
            label = "Home",
            selected = selectedTab == BottomNavTab.HOME,
            onClick = { onTabSelected(BottomNavTab.HOME) }
        )

        BottomNavItem(
            icon = "❤️",
            label = "Favorites",
            selected = selectedTab == BottomNavTab.FAVORITES,
            onClick = { onTabSelected(BottomNavTab.FAVORITES) }
        )

        BottomNavItem(
            icon = "🔍",
            label = "Discover",
            selected = selectedTab == BottomNavTab.DISCOVER,
            onClick = { onTabSelected(BottomNavTab.DISCOVER) }
        )

        BottomNavItem(
            icon = "⚙️",
            label = "Settings",
            selected = selectedTab == BottomNavTab.SETTINGS,
            onClick = { onTabSelected(BottomNavTab.SETTINGS) }
        )
    }
}

@Composable
private fun BottomNavItem(
    icon: String,
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val textColor = if (selected) PrimaryViolet else Color.White.copy(alpha = 0.6f)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clip(CircleShape)
            .clickable(onClick = onClick)
            .padding(8.dp)
    ) {
        Text(
            text = icon,
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            color = textColor,
            fontSize = 12.sp,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
        )
    }
}

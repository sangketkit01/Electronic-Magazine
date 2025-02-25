package com.src.electronicmagazine.navigation

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun BottomBar(navController : NavHostController){
    val navigationItems = listOf(Screen.Home,Screen.Notification,Screen.Profile)
    var selectedScreen by remember { mutableIntStateOf(0) }

    NavigationBar(
        containerColor = Color(34, 34, 34, 255),
    ) {
        navigationItems.forEachIndexed { index, screen ->
            NavigationBarItem(
                icon = {
                    when (screen.icon) {
                        is Int -> Icon(
                            painter = painterResource(id = screen.icon),
                            contentDescription = null,
                            modifier = Modifier.size(33.dp),
                            tint = Color.White
                        )
                        is ImageVector -> Icon(
                            imageVector = screen.icon,
                            contentDescription = null,
                            modifier = Modifier.size(33.dp),
                            tint = Color.White
                        )
                    }
                },
                label = {
                    Text(
                        text = screen.name,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                selected = false,
                onClick = {
                    selectedScreen = index
                    navController.navigate(screen.route)
                },
            )
        }
    }

}
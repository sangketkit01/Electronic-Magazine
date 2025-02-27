package com.src.electronicmagazine.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    navController: NavHostController,
    onMenuClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = "Life Time",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        navigationIcon = {
            IconButton(onClick = onMenuClick) {
                Icon(Icons.Default.Menu, contentDescription = "Menu", tint = Color.White)
            }
        },
        actions = {
            IconButton(onClick = {
                navController.navigate(Screen.Search.route)
            }) {
                Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.White)
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(34, 34, 34, 255),
        )
    )
}

@Composable
fun SidebarMenu(
    onDismiss: () -> Unit,
    navController: NavHostController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 30.dp)
            .background(Color(0, 0, 0, 160))
    ) {

        Row {
            AnimatedVisibility(
                visible = true,
                enter = slideInHorizontally(initialOffsetX = { -it }),
                exit = slideOutHorizontally(targetOffsetX = { -it })
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(280.dp)
                        .background(Color(34, 34, 34, 255))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 24.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "MENU",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )

                            IconButton(onClick = onDismiss) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Close Menu",
                                    tint = Color.White
                                )
                            }
                        }

                        MenuItem("World", navController, onDismiss)
                        Divider(color = Color.DarkGray, thickness = 0.5.dp)

                        MenuCategory("Entertainment", listOf("Films", "Music"), navController, onDismiss)
                        Divider(color = Color.DarkGray, thickness = 0.5.dp)

                        MenuCategory("Guide", listOf("Event", "Menu", "Travel"), navController, onDismiss)
                        Divider(color = Color.DarkGray, thickness = 0.5.dp)

                        MenuCategory("Tech", listOf("Apps", "Cars", "Gadgets"), navController, onDismiss)
                        Divider(color = Color.DarkGray, thickness = 0.5.dp)

                        MenuCategory("Style", listOf("Design", "Fashion", "Grooming"), navController, onDismiss)
                        Divider(color = Color.DarkGray, thickness = 0.5.dp)

                        MenuItem("Business", navController, onDismiss)
                        Divider(color = Color.DarkGray, thickness = 0.5.dp)

                        MenuItem("Girls", navController, onDismiss)
                        Divider(color = Color.DarkGray, thickness = 0.5.dp)

                        MenuItem("Life", navController, onDismiss)
                        Divider(color = Color.DarkGray, thickness = 0.5.dp)
                    }
                }
            }
        }
    }
}

@Composable
fun MenuCategory(
    category: String,
    items: List<String>,
    navController: NavHostController,
    onDismiss: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = category,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .clickable {
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        "topic",category
                    )
                    navController.navigate(Screen.Topic.route)
                    onDismiss()
                }
        )

        Row(
            modifier = Modifier.padding(start = 16.dp)
        ) {
            items.forEachIndexed { index, item ->
                Text(
                    text = item,
                    color = Color.White,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .clickable {
                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                "topic",item
                            )
                            navController.navigate(Screen.Topic.route)
                            onDismiss()
                        }
                )

                if (index < items.size - 1) {
                    Text(
                        text = "|",
                        color = Color.White,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun MenuItem(
    text: String,
    navController: NavHostController,
    onDismiss: () -> Unit
) {
    Text(
        text = text,
        color = Color.White,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        modifier = Modifier
            .padding(vertical = 12.dp)
            .clickable {
                navController.currentBackStackEntry?.savedStateHandle?.set(
                    "topic",text
                )
                navController.navigate(Screen.Topic.route)
                onDismiss()
            }
    )
}
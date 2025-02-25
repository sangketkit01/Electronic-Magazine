package com.src.electronicmagazine

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.src.electronicmagazine.session.SharePreferencesManager

@Composable
fun NotificationScreen(navController : NavHostController){
    val context = LocalContext.current
    val sharePreferences = SharePreferencesManager(context)


}
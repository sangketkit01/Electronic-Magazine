package com.src.electronicmagazine

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.src.electronicmagazine.navigation.BottomBar
import com.src.electronicmagazine.navigation.NavGraph
import com.src.electronicmagazine.navigation.Screen
import com.src.electronicmagazine.navigation.SidebarMenu
import com.src.electronicmagazine.navigation.TopBar
import com.src.electronicmagazine.ui.theme.ElectronicMagazineTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ElectronicMagazineTheme {
                MyScaffold()
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyScaffold() {
    val navController = rememberNavController()

    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry.value?.destination?.route

    val showBars = currentRoute == Screen.Login.route || currentRoute == Screen.Register.route

    var showMenu by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            if (!showBars) {
                TopBar(
                    navController = navController,
                    onMenuClick = { showMenu = !showMenu }
                )
            }
        },
        bottomBar = {
            if (!showBars) {
                BottomBar(navController)
            }
        },
    ) { paddingValues ->

        Box {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                NavGraph(navController = navController)
            }
            if (showMenu) {
                SidebarMenu(
                    onDismiss = { showMenu = false },
                    navController = navController
                )
            }
        }
    }
}




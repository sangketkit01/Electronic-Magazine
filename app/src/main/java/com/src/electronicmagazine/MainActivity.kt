package com.src.electronicmagazine

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.src.electronicmagazine.navigation.BottomBar
import com.src.electronicmagazine.navigation.NavGraph
import com.src.electronicmagazine.navigation.Screen
import com.src.electronicmagazine.navigation.TopBar
import com.src.electronicmagazine.ui.theme.ElectronicMagazineTheme

class MainActivity : ComponentActivity() {
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

@Composable
fun MyScaffold(){
    val navController = rememberNavController()

    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry.value?.destination?.route

    val showBars = currentRoute == Screen.Login.route || currentRoute == Screen.Register.route
    Scaffold(
        topBar = {
            if(!showBars){
                TopBar(navController)
            }
        },
        bottomBar = {
            if(!showBars){
                BottomBar(navController)
            }
        }
    ) { paddingValues ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            NavGraph(navController = navController)
        }
    }
}




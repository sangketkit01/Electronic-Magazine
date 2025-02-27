package com.src.electronicmagazine.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.src.electronicmagazine.BannedListScreen
import com.src.electronicmagazine.EditProfileScreen
import com.src.electronicmagazine.FavoriteScreen
import com.src.electronicmagazine.HomeScreen
import com.src.electronicmagazine.LoginScreen
import com.src.electronicmagazine.MyMagazineListScreen
import com.src.electronicmagazine.NotificationScreen
import com.src.electronicmagazine.ProfileScreen
import com.src.electronicmagazine.ReadScreen
import com.src.electronicmagazine.RegisterScreen
import com.src.electronicmagazine.SearchScreen
import com.src.electronicmagazine.TopicScreen
import com.src.electronicmagazine.WriterRegistrationDetailScreen
import com.src.electronicmagazine.WriterRegistrationListScreen
import com.src.electronicmagazine.WriterRegistrationScreen
import com.src.electronicmagazine.WritingContScreen
import com.src.electronicmagazine.WritingScreen
import com.src.electronicmagazine.session.SharePreferencesManager

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(navController : NavHostController){
    val context = LocalContext.current
    val sharedPreferences = SharePreferencesManager(context)
    NavHost(
        navController = navController,
        startDestination = if(sharedPreferences.loggedIn) Screen.Home.route
                        else Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            LoginScreen(navController)
        }

        composable(Screen.Register.route) {
            RegisterScreen(navController)
        }

        composable(Screen.Home.route) {
            HomeScreen(navController)
        }

        composable(Screen.Notification.route){
            NotificationScreen(navController)
        }

        composable(Screen.Profile.route){
            ProfileScreen(navController)
        }

        composable(Screen.Topic.route) {
            TopicScreen(navController)
        }

        composable(Screen.Read.route) {
            ReadScreen(navController)
        }

        composable(Screen.Search.route) {
            SearchScreen(navController)
        }

        composable(Screen.WriterRegistration.route) {
            WriterRegistrationScreen(navController)
        }

        composable(Screen.Writing.route) {
            WritingScreen(navController)
        }

        composable(Screen.WritingCont.route){
            WritingContScreen(navController)
        }

        composable(Screen.WriterRegistrationList.route) {
            WriterRegistrationListScreen(navController)
        }

        composable(Screen.WriterRegistrationDetail.route){
            WriterRegistrationDetailScreen(navController)
        }

        composable(Screen.EditProfile.route) {
            EditProfileScreen(navController)
        }

        composable(Screen.Favorite.route) {
            FavoriteScreen(navController)
        }

        composable(Screen.BannedList.route) {
            BannedListScreen(navController)
        }

        composable(Screen.MyMagazineList.route) {
            MyMagazineListScreen(navController)
        }
    }
}
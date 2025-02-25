package com.src.electronicmagazine.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications

sealed class Screen(val route : String , val name : String , val icon : Any?){
    data object Login:Screen("login_screen", name = "Login",null)
    data object Register:Screen("register_screen", name = "Register",null)

    data object Home:Screen("home_screen","Home",Icons.Default.Home)
    data object Notification:Screen("notification_screen","Notification",Icons.Default.Notifications)
    data object Profile:Screen("profile_screen","Profile",Icons.Default.AccountCircle)
    data object Topic:Screen("topic_screen","Topic",null)
    data object Read:Screen("read_screen","Read",null)
    data object Search:Screen("search_screen","Search",null)

    data object EditProfile:Screen("edit_profile_screen","Edit Profile",null)
    data object Favorite:Screen("favorite_screen","Favorite Screen",null)

    data object WriterRegistrationList:Screen("writer_registration_list_screen","Writer Registration List",null)
    data object WriterRegistration:Screen("writer_registration_screen","Writer Registration",null)
    data object WriterRegistrationDetail:Screen("writer_registration_detail_screen","Writer Registration Detail Screen",null)
    data object Writing:Screen("writing_screen","Writing",null)
}
package com.src.electronicmagazine

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.src.electronicmagazine.navigation.Screen
import com.src.electronicmagazine.session.SharePreferencesManager
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.util.UUID

@Composable
fun ProfileScreen(navController : NavHostController){
    val context = LocalContext.current
    val sharePreferences = SharePreferencesManager(context)

    var logoutAlert by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    Column (
        modifier = Modifier.fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier = Modifier.height(20.dp))

        Row (
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ){
        if (sharePreferences.user?.profilePath != null) {
                AsyncImage(
                    model = sharePreferences.user?.profilePath!!
                        .replace("../uploads","http://10.0.2.2:3000/uploads"),
                    contentDescription = "Current Profile Picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                )

                Log.e("path", sharePreferences.user?.profilePath ?: "No profile path")
        } else {
            Image(
                imageVector = Icons.Default.AccountCircle,
                contentScale = ContentScale.Fit,
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .padding(bottom = 5.dp)
            )
        }

            Column (
                horizontalAlignment = Alignment.Start,
            ){
                Text(
                    text = sharePreferences.user?.name ?: "",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = sharePreferences.user?.email ?: "",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(118, 118, 118, 255)
                )

                Spacer(modifier = Modifier.height(5.dp))

                Button(
                    onClick = {
                        navController.navigate(Screen.EditProfile.route)
                    },
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(217, 217, 217, 255)
                    )
                ) {
                    Text(
                        text = "Edit Profile",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Column (
            modifier = Modifier.fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 25.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Row (
                modifier = Modifier.fillMaxWidth()
                    .clickable {
                        navController.navigate(Screen.Favorite.route)
                    }
                    .clip(RoundedCornerShape(10.dp))
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Image(
                    painter = painterResource(R.drawable.favorite),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(40.dp)
                        .weight(1f)
                )

                Text(
                    text = "Favorite",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.weight(2f)
                )

                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    modifier = Modifier.size(35.dp).weight(1f),
                    contentDescription = null,
                )
            }
        }

        Column (
                modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 25.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Row (
                    modifier = Modifier.fillMaxWidth()
                        .clickable {
                            navController.navigate(Screen.WriterRegistration.route)
                        }
                        .clip(RoundedCornerShape(10.dp))
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Image(
                        painter = painterResource(R.drawable.registration),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(40.dp)
                            .weight(1f)
                    )

                    Text(
                        text = "Writer registration",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.weight(2f)
                    )

                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        modifier = Modifier.size(35.dp).weight(1f),
                        contentDescription = null,
                    )
                }
            }

        if(sharePreferences.user?.role == "writer"){
            Column (
                modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 25.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Row (
                    modifier = Modifier.fillMaxWidth()
                        .clickable {

                        }
                        .clip(RoundedCornerShape(10.dp))
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Image(
                        painter = painterResource(R.drawable.writing),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(40.dp)
                            .weight(1f)
                    )

                    Text(
                        text = "Write a magazine",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.weight(2f)
                    )

                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        modifier = Modifier.size(35.dp).weight(1f),
                        contentDescription = null,
                    )
                }
            }
        }

        if(sharePreferences.user?.role == "admin"){
            Column (
                modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 25.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Row (
                    modifier = Modifier.fillMaxWidth()
                        .clickable {
                            navController.navigate(Screen.WriterRegistrationList.route)
                        }
                        .clip(RoundedCornerShape(10.dp))
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Image(
                        painter = painterResource(R.drawable.list),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(40.dp)
                            .weight(1f)
                    )

                    Text(
                        text = "Registration list",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.weight(2f)
                    )

                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        modifier = Modifier.size(35.dp).weight(1f),
                        contentDescription = null,
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                logoutAlert = true
            },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            ),
            modifier = Modifier.width(165.dp).height(40.dp)
        ) {
            Text(
                text = "Logout",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }

    if(logoutAlert){
        AlertDialog(
            onDismissRequest = {logoutAlert = false},
            title = { Text("Logout") },
            text = { Text("Are you sure you want to logout ?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        sharePreferences.clear()
                        navController.navigate(Screen.Login.route)
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        logoutAlert = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}
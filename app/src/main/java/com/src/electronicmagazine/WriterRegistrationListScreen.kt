package com.src.electronicmagazine

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import com.src.electronicmagazine.api_util.allPendingRegistrationUtility
import com.src.electronicmagazine.api_util.updateReviewedAtUtility
import com.src.electronicmagazine.data.Registration
import com.src.electronicmagazine.navigation.Screen
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date
import java.util.Locale

@SuppressLint("SimpleDateFormat")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WriterRegistrationListScreen(navController : NavHostController){

    val context = LocalContext.current

    var registrationList = remember { mutableStateListOf<Registration>() }

    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycle by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()
    LaunchedEffect(lifecycle) {
        when(lifecycle){
            Lifecycle.State.DESTROYED -> {}
            Lifecycle.State.INITIALIZED -> {}
            Lifecycle.State.CREATED -> {}
            Lifecycle.State.STARTED -> {}
            Lifecycle.State.RESUMED -> {
                allPendingRegistrationUtility(
                    onResponse = { response->
                        registrationList.addAll(response)
                    },
                    onElse = {
                        Toast.makeText(context,"Data not found",Toast.LENGTH_SHORT).show()
                    },
                    onFailure = { t->
                        Toast.makeText(context,"Error onFailure",Toast.LENGTH_SHORT).show()
                        t.message?.let { Log.e("Error",it) }

                    }
                )
            }
        }
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(top = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ){

        Row (
            modifier = Modifier.fillMaxWidth()
                .padding(top = 15.dp, start = 15.dp),
            horizontalArrangement = Arrangement.Start
        ){
            Icon(
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = null,
                modifier = Modifier.size(60.dp)
                    .clickable {
                        navController.popBackStack()
                    }
            )
        }

        Text(
            text = "Writer Registration",
            fontSize = 25.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        registrationList.forEach { registration->
            val dateFormat = SimpleDateFormat("MMMM dd, yyyy",Locale.ENGLISH)
            val date = dateFormat.format(registration.appliedAt)

            Card (
                modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 20.dp, horizontal = 40.dp)
                    .clickable {
                        updateReviewedAtUtility(
                            registration.registrationId,
                            onResponse = {
                                navController.currentBackStackEntry?.savedStateHandle?.set(
                                    "registration_id" , registration.registrationId
                                )

                                navController.navigate(Screen.WriterRegistrationDetail.route)
                            },
                            onElse = { response->
                                Toast.makeText(context,"Something went wrong",Toast.LENGTH_SHORT)
                                    .show()
                                Log.e("Error",response.message())
                            },
                            onFailure = { t->
                                Toast.makeText(context,"Error onFailure",Toast.LENGTH_SHORT)
                                    .show()
                                t.message?.let { Log.e("Error", it) }
                            }
                        )

                    },
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(34, 34, 34, 255)
                )
            ){
                Column (
                    modifier = Modifier.fillMaxWidth()
                        .padding(10.dp),
                    horizontalAlignment = Alignment.Start
                ){
                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Icon(
                            imageVector = Icons.Default.AccountBox,
                            contentDescription = null,
                            modifier = Modifier.size(30.dp),
                            tint = Color(147, 147, 147, 255)
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Text(
                            text = registration.fullName,
                            fontSize = 18.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = registration.email,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(147, 147, 147, 255)
                    )

                    Text(
                        text = registration.phone,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(147, 147, 147, 255)
                    )


                    Spacer(modifier = Modifier.height(40.dp))

                    Text(
                        text = date,
                        fontSize = 12.sp,
                        color = Color(147, 147, 147, 255),
                        modifier = Modifier.align(Alignment.End)
                    )
                }
            }
        }
    }
}



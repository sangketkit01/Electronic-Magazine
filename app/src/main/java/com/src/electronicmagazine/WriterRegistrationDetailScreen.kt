package com.src.electronicmagazine

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.src.electronicmagazine.api_util.allPendingRegistrationUtility
import com.src.electronicmagazine.api_util.approveRegistrationUtility
import com.src.electronicmagazine.api_util.getRegistrationUtility
import com.src.electronicmagazine.api_util.rejectRegistrationUtility
import com.src.electronicmagazine.data.Registration

@Composable
fun WriterRegistrationDetailScreen(navController : NavHostController){
    val context = LocalContext.current

    val registrationId = navController.previousBackStackEntry?.savedStateHandle?.get<Int>("registration_id")
    var registration by remember { mutableStateOf<Registration?>(null) }

    var idCardAlert by remember { mutableStateOf(false) }
    var rejectAlert by remember { mutableStateOf(false) }
    var approveAlert by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycle by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()
    LaunchedEffect(lifecycle) {
        when(lifecycle){
            Lifecycle.State.DESTROYED -> {}
            Lifecycle.State.INITIALIZED -> {}
            Lifecycle.State.CREATED -> {}
            Lifecycle.State.STARTED -> {}
            Lifecycle.State.RESUMED -> {
                getRegistrationUtility(
                    registrationId!!,
                    onResponse = { response->
                        registration = response
                    },
                    onElse = { response->
                        Toast.makeText(context,"Data not found",Toast.LENGTH_SHORT).show()
                        Log.e("Error",response.message())
                    },
                    onFailure = { t->
                        Toast.makeText(context,"Error onFailure",Toast.LENGTH_SHORT).show()
                        t.message?.let { Log.e("Error",it) }
                    }
                )
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
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

        Column (
            modifier = Modifier.fillMaxWidth()
                .padding(vertical = 20.dp, horizontal = 30.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.Start
        ){
            Column{
                Text(
                    text = "Name",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(113, 113, 113, 255)
                )

                Text(
                    text = registration?.fullName ?: "",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Column{
                Text(
                    text = "ID Card",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(113, 113, 113, 255)
                )

                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = registration?.idCard ?: "",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Button(
                        onClick = {
                            idCardAlert = true
                        },
                        shape = RoundedCornerShape(15.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(217, 217, 217, 255),
                            contentColor = Color.Black
                        ),
                        contentPadding = PaddingValues(horizontal = 15.dp)
                    ) {
                        Text(
                            text = "ID Card Picture",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

            }

            Column{
                Text(
                    text = "Email",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(113, 113, 113, 255)
                )

                Text(
                    text = registration?.email ?: "",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Column{
                Text(
                    text = "Phone",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(113, 113, 113, 255)
                )

                Text(
                    text = registration?.phone ?: "",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Column{
                Text(
                    text = "Address",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(113, 113, 113, 255)
                )

                Text(
                    text = registration?.address ?: "",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
            }

            Column{
                Text(
                    text = "Bio",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(113, 113, 113, 255)
                )

                Text(
                    text = registration?.bio ?: "",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
            }
        }

        Row (
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 30.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ){
            Button(
                onClick = {
                    rejectAlert = true
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(202, 37, 37, 255),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Rejected",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Button(
                onClick = {
                    approveAlert = true
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Approve",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }

    if(idCardAlert){
        ImageDialog(registration?.idCardPath) {
            idCardAlert = false
        }
    }

    if(rejectAlert){
        AlertDialog(
            onDismissRequest = {
                rejectAlert = false
            },
            title = {
                Text("Rejecting")
            },
            text = {
                Text("Are you sure you want to reject ${registration?.fullName}")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        rejectRegistrationUtility(
                            registration?.registrationId!!,
                            onResponse = {
                                Toast.makeText(context,"Reject registration successfully",Toast.LENGTH_SHORT)
                                    .show()
                                navController.popBackStack()
                            },
                            onElse = { response->
                                Toast.makeText(context,"Reject registration failed",Toast.LENGTH_SHORT)
                                    .show()
                                Log.e("Error",response.message())
                            },
                            onFailure = { t->
                                Toast.makeText(context,"Error onFailure",Toast.LENGTH_SHORT).show()
                                t.message?.let { Log.e("Error", it) }
                            }
                        )
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {rejectAlert = false}
                ) {
                    Text("Cancel")
                }
            }
        )
    }

    if (approveAlert){
        AlertDialog(
            onDismissRequest = {
                approveAlert = false
            },
            title = {
                Text("Approving")
            },
            text = {
                Text("Are you sure you want to approve ${registration?.fullName}")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        approveRegistrationUtility(
                            registration?.registrationId!!,
                            registration?.userId!!,
                            onResponse = {
                                Toast.makeText(context,"Approve registration successfully",Toast.LENGTH_SHORT)
                                    .show()
                                navController.popBackStack()
                            },
                            onElse = { response->
                                Toast.makeText(context,"Approve registration failed",Toast.LENGTH_SHORT)
                                    .show()
                                Log.e("Error",response.message())
                            },
                            onFailure = { t->
                                Toast.makeText(context,"Error onFailure",Toast.LENGTH_SHORT).show()
                                t.message?.let { Log.e("Error", it) }
                            }
                        )
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {approveAlert = false}
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun ImageDialog(url: String?, onDismiss: () -> Unit) {
    val baseUrl = "http://10.0.2.2:3000"

    val path = url?.replace("../uploads","$baseUrl/uploads")


    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("ID Card") },
        text = {
            AsyncImage(
                model = path,
                contentDescription = "ID Card",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        },
        confirmButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("ปิด")
            }
        }
    )
}
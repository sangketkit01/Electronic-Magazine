package com.src.electronicmagazine

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.src.electronicmagazine.api_util.registerUtility
import com.src.electronicmagazine.navigation.Screen
import com.src.electronicmagazine.session.SharePreferencesManager

@Composable
fun RegisterScreen(navController : NavHostController){
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var confirm by remember { mutableStateOf("") }

    var passwordNotMatchAlert by remember { mutableStateOf(false) }

    val context = LocalContext.current

    val scrollState = rememberScrollState()

    Column (
        modifier = Modifier.fillMaxSize()
            .background(color = Color(26, 26, 46, 255))
    ){
        Column (
            modifier = Modifier.fillMaxSize()
                .padding(horizontal = 20.dp , vertical = 80.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ){
            Text(
                text = "Life Time\n" +
                        "Magazine",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Create an account",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(10.dp))

            Column (
                modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 15.dp),
                horizontalAlignment = Alignment.Start
            ){
                Text(
                    text = "Username",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(vertical = 5.dp),
                    color = Color.White
                )

                OutlinedTextField(
                    value = username,
                    onValueChange = {newValue->
                        username = newValue
                    },
                    label = {
                        Text(
                            text = "Username",
                            fontWeight = FontWeight.Bold,
                            color = Color(131, 131, 131, 255)
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp)),
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedTextColor = Color.Black,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        unfocusedLabelColor = Color.LightGray,
                        focusedLabelColor = Color.White
                    )
                )
            }

            Column (
                modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 15.dp),
                horizontalAlignment = Alignment.Start
            ){
                Text(
                    text = "Email",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(vertical = 5.dp),
                    color = Color.White
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = {newValue->
                        email = newValue
                    },
                    label = {
                        Text(
                            text = "email",
                            fontWeight = FontWeight.Bold,
                            color = Color(131, 131, 131, 255)
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp)),
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedTextColor = Color.Black,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        unfocusedLabelColor = Color.LightGray,
                        focusedLabelColor = Color.White
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )
            }

            Column (
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 15.dp,
                        bottom = 5.dp),
                horizontalAlignment = Alignment.Start
            ){
                Text(
                    text = "Password",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(vertical = 5.dp),
                    color = Color.White
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = {newValue->
                        password = newValue
                    },
                    label = {
                        Text(
                            text = "Password",
                            fontWeight = FontWeight.Bold,
                            color = Color(131, 131, 131, 255)
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp)),
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedTextColor = Color.Black,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        unfocusedLabelColor = Color.LightGray,
                        focusedLabelColor = Color.White
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(
                            onClick = {

                            }
                        ) {
                            Icon(
                                Icons.Default.Face,
                                contentDescription = null
                            )
                        }
                    }
                )

            }

            Column (
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 15.dp,
                        bottom = 5.dp),
                horizontalAlignment = Alignment.Start
            ){
                Text(
                    text = "Confirm Password",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(vertical = 5.dp),
                    color = Color.White
                )

                OutlinedTextField(
                    value = confirm,
                    onValueChange = {newValue->
                        confirm = newValue
                    },
                    label = {
                        Text(
                            text = "Confirm Password",
                            fontWeight = FontWeight.Bold,
                            color = Color(131, 131, 131, 255)
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp)),
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedTextColor = Color.Black,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        unfocusedLabelColor = Color.LightGray,
                        focusedLabelColor = Color.White
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(
                            onClick = {

                            }
                        ) {
                            Icon(
                                Icons.Default.Face,
                                contentDescription = null
                            )
                        }
                    }
                )

            }


            Spacer(modifier = Modifier.height(20.dp))

            FilledTonalButton(
                onClick = {
                    if(password != confirm){
                        passwordNotMatchAlert = true
                    }else{
                        registerUtility(
                            username = username,
                            email = email,
                            password = password,
                            onResponse = {
                                Toast.makeText(context,"Register Successfully",Toast.LENGTH_SHORT).show()
                                navController.navigate(Screen.Login.route)
                            },
                            onElse = { response->
                                Toast.makeText(context,"Register Failed",Toast.LENGTH_SHORT).show()
                                response.errorBody()?.toString()?.let { Log.e("Error", it) }
                            },
                            onFailure = { t->
                                Toast.makeText(context,"Error onFailure",Toast.LENGTH_SHORT).show()
                                t.message?.let { Log.e("Error", it) }
                            }
                        )
                    }
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = Color.Black
                ),
                modifier = Modifier.fillMaxWidth().height(45.dp),
                enabled = username.isNotEmpty() && password.isNotEmpty() && email.isNotEmpty()
                        && confirm.isNotEmpty()
            ) {
                Text(
                    text = "Create an Account",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "Already register?",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )

                TextButton(
                    onClick = {
                        navController.navigate(Screen.Login.route)
                    }
                ) {
                    Text(
                        text = "Sign in",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White,
                        textDecoration = TextDecoration.Underline
                    )
                }
            }
        }
    }

    if(passwordNotMatchAlert){
        AlertDialog(
            onDismissRequest = {passwordNotMatchAlert = false},
            title = {
                Text("Password does not match")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        passwordNotMatchAlert = false
                    }
                ) {
                    Text("OK")
                }
            },
        )
    }
}
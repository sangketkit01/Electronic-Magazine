package com.src.electronicmagazine

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.src.electronicmagazine.api_util.getAllMagazineUtility
import com.src.electronicmagazine.api_util.getCategoryByNameUtility
import com.src.electronicmagazine.api_util.getCategoryUtility
import com.src.electronicmagazine.api_util.getMagazinesByCategoryUtility
import com.src.electronicmagazine.api_util.getWriterUtility
import com.src.electronicmagazine.components.BadgeColor
import com.src.electronicmagazine.data.Category
import com.src.electronicmagazine.data.Magazine
import com.src.electronicmagazine.data.User
import com.src.electronicmagazine.navigation.Screen
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

@Composable
fun TopicScreen(navController: NavHostController) {
    val topic = navController.previousBackStackEntry?.savedStateHandle?.get<String>("topic")
    val context = LocalContext.current

    var magazineList by remember { mutableStateOf<List<Magazine>>(emptyList()) }
    var randomMagazine by remember { mutableStateOf<Magazine?>(null) }

    var isLoading by remember { mutableStateOf(true) }

    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycle by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()

    LaunchedEffect(lifecycle) {
        when(lifecycle) {
            Lifecycle.State.RESUMED -> {
                isLoading = true
                getCategoryByNameUtility(
                    name = topic ?: "",
                    onResponse = { response ->
                        getMagazinesByCategoryUtility(
                            response.categoryId,
                            onResponse = { magazineResponse ->
                                magazineList = magazineResponse
                                if(magazineResponse.isNotEmpty()) {
                                    randomMagazine = magazineResponse[Random.nextInt(magazineResponse.size)]
                                }
                                isLoading = false
                            },
                            onElse = { responseError ->
                                Toast.makeText(context, "Magazine not found", Toast.LENGTH_SHORT).show()
                                Log.e("Error", responseError.message())
                                isLoading = false
                            },
                            onFailure = { t ->
                                Toast.makeText(context, "Error onFailure", Toast.LENGTH_SHORT).show()
                                t.message?.let { Log.e("Error", it) }
                                isLoading = false
                            }
                        )

                    },
                    onElse = { responseError ->
                        Toast.makeText(context, "Category not found", Toast.LENGTH_SHORT).show()
                        Log.e("Error", responseError.message())
                        isLoading = false
                    },
                    onFailure = { t ->
                        Toast.makeText(context, "Error onFailure", Toast.LENGTH_SHORT).show()
                        t.message?.let { Log.e("Error", it) }
                        isLoading = false
                    }
                )
            }
            else -> { /* No action needed for other lifecycle states */ }
        }
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(450.dp)
                    .background(Color(26, 26, 26, 255)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Loading",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Please wait",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
            }
        } else if (magazineList.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .height(450.dp)
                    .background(Color.Black),
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = randomMagazine?.coverPath
                            ?.replace("../uploads","http://10.0.2.2:3000/uploads")
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .fillMaxWidth()
                        .height(450.dp)
                        .clickable {
                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                "magazine_id" , randomMagazine?.magazineId
                            )
                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                "writer_id" , randomMagazine?.writerId
                            )
                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                "category_id",randomMagazine?.categoryId
                            )
                            navController.navigate(Screen.Read.route)
                        }
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 10.dp)
                        .padding(horizontal = 20.dp, vertical = 15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = topic ?: "",
                        fontSize = 18.sp,
                        color = Color.White,
                        modifier = Modifier
                            .width(121.dp)
                            .background(BadgeColor(topic ?: "")),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = randomMagazine?.title ?: "",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.5.sp,
                        color = Color.White
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(74.dp)
                    .background(Color(26, 26, 26, 255)),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = topic ?: "",
                    fontSize = 30.sp,
                    color = Color.White
                )
            }

            magazineList.forEach{ magazine ->
                var writer by remember { mutableStateOf<User?>(null) }

                val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
                val outputFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH)

                val date: Date? = inputFormat.parse(magazine.createdAt.toString())
                val formattedCreatedAt = date?.let { outputFormat.format(it) }

                getWriterUtility(
                    magazine.writerId,
                    onResponse = { response->
                        writer = response
                    },
                    onElse = { responseError ->
                        Toast.makeText(context, "Category not found", Toast.LENGTH_SHORT).show()
                        Log.e("Error", responseError.message())
                    },
                    onFailure = { t ->
                        Toast.makeText(context, "Error onFailure", Toast.LENGTH_SHORT).show()
                        t.message?.let { Log.e("Error", it) }
                    }
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp, vertical = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier.width(391.dp)
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(
                                model = magazine.coverPath
                                    .replace("../uploads","http://10.0.2.2:3000/uploads")
                            ),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth()
                                .height(173.dp)
                                .align(Alignment.Center)
                                .background(Color(26, 26, 26, 255))
                        )

                        Text(
                            text = topic ?: "",
                            fontSize = 18.sp,
                            color = Color.White,
                            modifier = Modifier
                                .width(121.dp)
                                .background(BadgeColor(topic ?: ""))
                                .align(Alignment.BottomCenter),
                            textAlign = TextAlign.Center
                        )
                    }

                    Text(
                        text = magazine.title,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .width(276.dp)
                            .padding(10.dp)
                            .clickable {
                                navController.currentBackStackEntry?.savedStateHandle?.set(
                                    "magazine_id" , magazine.magazineId
                                )
                                navController.currentBackStackEntry?.savedStateHandle?.set(
                                    "writer_id" , magazine.writerId
                                )
                                navController.currentBackStackEntry?.savedStateHandle?.set(
                                    "category_id",magazine.categoryId
                                )

                                navController.navigate(Screen.Read.route)
                            },
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "By: ${writer?.name ?: ""} $formattedCreatedAt",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(196, 196, 196, 255),
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(450.dp)
                    .background(Color(26, 26, 26, 255)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "404 Not Found",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}
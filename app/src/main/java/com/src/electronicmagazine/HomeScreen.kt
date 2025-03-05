package com.src.electronicmagazine

import android.annotation.SuppressLint
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
import androidx.compose.runtime.mutableStateMapOf
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
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.src.electronicmagazine.api_util.allPendingRegistrationUtility
import com.src.electronicmagazine.api_util.getAllMagazineUtility
import com.src.electronicmagazine.api_util.getCategoryUtility
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

@SuppressLint("SimpleDateFormat")
@Composable
fun HomeScreen(navController: NavHostController){

    var magazineList  = remember { mutableStateListOf<Magazine>() }
    val context = LocalContext.current

    val scrollState = rememberScrollState()

    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycle by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()

    var randomMagazine by remember { mutableStateOf<Magazine?>(null) }
    var category by remember { mutableStateOf<Category?>(null) }
    LaunchedEffect(lifecycle) {
        when(lifecycle){
            Lifecycle.State.DESTROYED -> {}
            Lifecycle.State.INITIALIZED -> {}
            Lifecycle.State.CREATED -> {}
            Lifecycle.State.STARTED -> {}
            Lifecycle.State.RESUMED -> {
                getAllMagazineUtility(
                    onResponse = { response->
                        magazineList.addAll(response)
                        randomMagazine = magazineList[Random.nextInt(magazineList.size)]

                        getCategoryUtility(
                            randomMagazine?.categoryId ?: 1,
                            onResponse = { response2->
                                category = response2
                            },
                            onElse = { responseError->
                                Toast.makeText(context,"Data not found", Toast.LENGTH_SHORT).show()
                                Log.e("Error",responseError.message())
                            },
                            onFailure = { t->
                                Toast.makeText(context,"Error onFailure", Toast.LENGTH_SHORT).show()
                                t.message?.let { Log.e("Error",it) }
                            }
                        )
                    },
                    onElse = { responseError->
                        Toast.makeText(context,"Data not found", Toast.LENGTH_SHORT).show()
                        Log.e("Error",responseError.message())
                    },
                    onFailure = { t->
                        Toast.makeText(context,"Error onFailure", Toast.LENGTH_SHORT).show()
                        t.message?.let { Log.e("Error",it) }
                    }
                )
            }
        }
    }

    val categoryMap = remember { mutableStateMapOf<Int, Category?>() }
    val writerMap = remember { mutableStateMapOf<Int, User?>() }

    LaunchedEffect(magazineList) {
        magazineList.forEach { magazine ->
            if (!categoryMap.containsKey(magazine.categoryId)) {
                getCategoryUtility(
                    magazine.categoryId,
                    onResponse = { response -> categoryMap[magazine.categoryId] = response },
                    onElse = { responseError -> Log.e("Error", responseError.message()) },
                    onFailure = { t -> Log.e("Error", t.message ?: "Unknown error") }
                )
            }

            if (!writerMap.containsKey(magazine.writerId)) {
                getWriterUtility(
                    magazine.writerId,
                    onResponse = { response -> writerMap[magazine.writerId] = response },
                    onElse = { responseError -> Log.e("Error", responseError.message()) },
                    onFailure = { t -> Log.e("Error", t.message ?: "Unknown error") }
                )
            }
        }
    }


    Column (
        modifier = Modifier.fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Box(
            modifier = Modifier.height(450.dp)
                .background(Color.Black),
        ){
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
                    text = category?.name ?: "Fashion",
                    fontSize = 18.sp,
                    color = Color.White,
                    modifier = Modifier
                        .background(BadgeColor(category?.name ?: "Fashion"))
                        .padding(vertical = 3.dp, horizontal = 8.dp),
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

        Row (
            modifier = Modifier.fillMaxWidth()
                .height(74.dp)
                .background(Color(26, 26, 26, 255)),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "Just Arrive",
                fontSize = 30.sp,
                color = Color.White
            )
        }

        magazineList.forEach { magazine->

            var loopCategory by remember { mutableStateOf<Category?>(null) }
            var writer by remember { mutableStateOf<User?>(null) }

            val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
            val outputFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH)

            val date: Date? = inputFormat.parse(magazine.createdAt.toString())
            val formattedCreatedAt = date?.let { outputFormat.format(it) }

            getCategoryUtility(
                magazine.categoryId,
                onResponse = { response->
                    loopCategory = response
                },
                onElse = { responseError->
                    Toast.makeText(context,"Data not found", Toast.LENGTH_SHORT).show()
                    Log.e("Error",responseError.message())
                },
                onFailure = { t->
                    Toast.makeText(context,"Error onFailure", Toast.LENGTH_SHORT).show()
                    t.message?.let { Log.e("Error",it) }
                }
            )

            getWriterUtility(
                magazine.writerId,
                onResponse = { response->
                    writer = response
                },
                onElse = { responseError->
                    Toast.makeText(context,"Data not found", Toast.LENGTH_SHORT).show()
                    Log.e("Error",responseError.message())
                },
                onFailure = { t->
                    Toast.makeText(context,"Error onFailure", Toast.LENGTH_SHORT).show()
                    t.message?.let { Log.e("Error",it) }
                }
            )

            Column (
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 5.dp, vertical = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Box(
                    modifier = Modifier.width(391.dp)
                        .height(173.dp)
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
                        }
                ){
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = magazine.coverPath
                                .replace("../uploads","http://10.0.2.2:3000/uploads")
                        ),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.padding(10.dp)
                            .fillMaxWidth()
                            .align(Alignment.Center)
                            .background(Color(26, 26, 26, 255))
                    )

                    Text(
                        text = loopCategory?.name ?: "",
                        fontSize = 18.sp,
                        color = Color.White,
                        modifier = Modifier
                            .background(BadgeColor(loopCategory?.name ?: ""))
                            .padding(vertical = 3.dp, horizontal = 8.dp)
                            .align(Alignment.BottomCenter),
                        textAlign = TextAlign.Center
                    )
                }

                Text(
                    text = magazine.title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.width(276.dp)
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
                    text = "By: ${writer?.name} $formattedCreatedAt",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(196, 196, 196, 255),
                    textAlign = TextAlign.Center
                )
            }
        }


        Spacer(modifier = Modifier.height(20.dp))
    }
}
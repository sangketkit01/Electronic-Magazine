package com.src.electronicmagazine

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import coil.compose.rememberAsyncImagePainter
import com.src.electronicmagazine.api_util.getCategoryUtility
import com.src.electronicmagazine.api_util.getWriterUtility
import com.src.electronicmagazine.api_util.myFavoriteUtility
import com.src.electronicmagazine.api_util.myMagazineListUtility
import com.src.electronicmagazine.components.BadgeColor
import com.src.electronicmagazine.data.Category
import com.src.electronicmagazine.data.Magazine
import com.src.electronicmagazine.data.User
import com.src.electronicmagazine.navigation.Screen
import com.src.electronicmagazine.session.SharePreferencesManager

@Composable
fun FavoriteScreen(navController : NavHostController){

    var magazineList  = remember { mutableStateListOf<Magazine>() }
    val context = LocalContext.current
    val sharedPreferences = SharePreferencesManager(context)

    val scrollState = rememberScrollState()

    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycle by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()

    var category by remember { mutableStateOf<Category?>(null) }
    LaunchedEffect(lifecycle) {
        when(lifecycle){
            Lifecycle.State.DESTROYED -> {}
            Lifecycle.State.INITIALIZED -> {}
            Lifecycle.State.CREATED -> {}
            Lifecycle.State.STARTED -> {}
            Lifecycle.State.RESUMED -> {
                myFavoriteUtility(
                    sharedPreferences.user?.userId!!,
                    onResponse = { response->
                        magazineList.addAll(response)
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
            text = "My favorite magazines",
            fontSize = 25.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )

        magazineList.forEach { magazine->

            var loopCategory by remember { mutableStateOf<Category?>(null) }
            var writer by remember { mutableStateOf<User?>(null) }

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
                    text = "By: ${writer?.name} 01,2024",
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
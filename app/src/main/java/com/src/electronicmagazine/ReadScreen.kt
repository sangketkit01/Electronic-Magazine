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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.FavoriteBorder
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.src.electronicmagazine.api_util.banMagazineUtility
import com.src.electronicmagazine.api_util.deleteFavoriteUtility
import com.src.electronicmagazine.api_util.getAllMagazineUtility
import com.src.electronicmagazine.api_util.getArticleUtility
import com.src.electronicmagazine.api_util.getCategoryUtility
import com.src.electronicmagazine.api_util.getFavoriteUtility
import com.src.electronicmagazine.api_util.getMagazineUtility
import com.src.electronicmagazine.api_util.getWriterUtility
import com.src.electronicmagazine.api_util.insertFavoriteUtility
import com.src.electronicmagazine.api_util.unbannedMagazineUtility
import com.src.electronicmagazine.components.BadgeColor
import com.src.electronicmagazine.data.Article
import com.src.electronicmagazine.data.Category
import com.src.electronicmagazine.data.Favorite
import com.src.electronicmagazine.data.Magazine
import com.src.electronicmagazine.data.User
import com.src.electronicmagazine.navigation.Screen
import com.src.electronicmagazine.session.SharePreferencesManager
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

@Composable
fun ReadScreen(navController : NavHostController){
    val magazineId = navController.previousBackStackEntry?.savedStateHandle?.get<Int>("magazine_id")
    val writerId = navController.previousBackStackEntry?.savedStateHandle?.get<Int>("writer_id")
    val categoryId  = navController.previousBackStackEntry?.savedStateHandle?.get<Int>("category_id")

    val context = LocalContext.current
    val sharePreferences = SharePreferencesManager(context)

    var banAlert by remember { mutableStateOf(false) }
    var unbannedAlert by remember { mutableStateOf(false) }

    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycle by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()

    var magazine by remember { mutableStateOf<Magazine?>(null) }
    var writer by remember { mutableStateOf<User?>(null) }
    var category by remember { mutableStateOf<Category?>(null) }
    var article by remember { mutableStateOf<Article?>(null) }
    var favorite by remember { mutableStateOf<Favorite?>(null) }

    val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
    val outputFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH)

    var date: Date? by remember { mutableStateOf(null) }
    var formattedCreatedAt by remember { mutableStateOf("") }

    val (isFavorite, setFavorite) = remember { mutableStateOf(false) }
    LaunchedEffect(lifecycle) {
        when(lifecycle){
            Lifecycle.State.DESTROYED -> {}
            Lifecycle.State.INITIALIZED -> {}
            Lifecycle.State.CREATED -> {}
            Lifecycle.State.STARTED -> {}
            Lifecycle.State.RESUMED -> {
                getMagazineUtility(
                    magazineId!!,
                    onResponse = { response->
                        magazine = response

                        date = inputFormat.parse(magazine?.createdAt.toString())
                        formattedCreatedAt = date?.let { outputFormat.format(it) }.toString()
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
                    writerId!!,
                    onResponse = { response2->
                        writer = response2
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

                getCategoryUtility(
                    categoryId!!,
                    onResponse = { response->
                        category = response
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

                getArticleUtility(
                    magazineId,
                    onResponse = { response->
                        article = response
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

                getFavoriteUtility(
                    sharePreferences.user?.userId!!,
                    magazineId,
                    onResponse = { response->
                        favorite = response
                        setFavorite(true)
                    },
                    onElse = {

                    },
                    onFailure = {

                    }
                )
            }
        }
    }


    val scrollState = rememberScrollState()

    Column (
        modifier = Modifier.fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Column (
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Box(
                modifier = Modifier.fillMaxWidth()
            ){

                Image(
                    painter = rememberAsyncImagePainter(
                        model = magazine?.coverPath
                            ?.replace("../uploads","http://10.0.2.2:3000/uploads")
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                        .height(279.dp)

                )

                Text(
                    text = category?.name ?: "",
                    fontSize = 18.sp,
                    color = Color.White,
                    modifier = Modifier
                        .width(121.dp)
                        .background(BadgeColor(category?.name ?: ""))
                        .align(Alignment.BottomCenter),
                    textAlign = TextAlign.Center
                )

                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = null,
                    modifier = Modifier.size(60.dp)
                        .clickable {
                            navController.popBackStack()
                        }.align(Alignment.TopStart)
                )
            }

            Text(
                text = magazine?.title ?: "",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.width(276.dp)
                    .padding(10.dp),
                textAlign = TextAlign.Center
            )

                Text(
                    text = "By: ${writer?.name ?: ""} $formattedCreatedAt",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(196, 196, 196, 255),
                    textAlign = TextAlign.Center
                )


            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(0x80000000))
                    .clickable {
                        val newFavoriteState = !isFavorite
                        setFavorite(newFavoriteState)

                        if(newFavoriteState){
                            insertFavoriteUtility(
                                sharePreferences.user?.userId!!,
                                magazineId!!,
                                onResponse = { response->
                                    Log.e("Message",response.toString())
                                },
                                onElse = { responseError->
                                    Toast.makeText(context,"Ban magazine failed", Toast.LENGTH_SHORT).show()
                                    Log.e("Error",responseError.message())
                                },
                                onFailure = { t->
                                    Toast.makeText(context,"Error onFailure", Toast.LENGTH_SHORT).show()
                                    t.message?.let { Log.e("Error",it) }
                                }
                            )
                        }else{
                            deleteFavoriteUtility(
                                sharePreferences.user?.userId!!,
                                magazineId!!,
                                onResponse = { response->
                                    Log.e("Message",response.toString())
                                },
                                onElse = { responseError->
                                    Toast.makeText(context,"Ban magazine failed", Toast.LENGTH_SHORT).show()
                                    Log.e("Error",responseError.message())
                                },
                                onFailure = { t->
                                    Toast.makeText(context,"Error onFailure", Toast.LENGTH_SHORT).show()
                                    t.message?.let { Log.e("Error",it) }
                                }
                            )

                        }
                    }
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = if (isFavorite) "Unlike" else "Like",
                    tint = if (isFavorite) Color(0xFFFF4D4F) else Color.White
                )
            }

        }
        
        Text(
            text = article?.content ?: "",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
                .padding(10.dp)
        )

        Image(
            rememberAsyncImagePainter(
                model = article?.imagePath
                    ?.replace("../uploads","http://10.0.2.2:3000/uploads")
            ),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth()
                .height(200.dp)
                .padding(10.dp)
                .background(Color(68, 68, 68, 255)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(20.dp))

        if(sharePreferences.user?.role == "admin"){
            if(magazine?.status != "banned"){
                Button(
                    onClick = {
                        banAlert = true
                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(202, 37, 37, 255),
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        "Ban",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }else{
                Button(
                    onClick = {
                        unbannedAlert = true
                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(202, 37, 37, 255),
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        "Unbanned",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }

    if(banAlert){
        AlertDialog(
            onDismissRequest = {banAlert = false},
            title = { Text("Banning") },
            text = { Text("Do you confirm to ban ${magazine?.title}") },
            confirmButton = {
                TextButton(
                    onClick = {
                        banMagazineUtility(
                            magazine?.magazineId ?: 0,
                            onResponse = {
                                Toast.makeText(context,"Ban magazine successfully", Toast.LENGTH_SHORT).show()
                                navController.navigate(Screen.Home.route)
                            },
                            onElse = { responseError->
                                Toast.makeText(context,"Ban magazine failed", Toast.LENGTH_SHORT).show()
                                Log.e("Error",responseError.message())
                            },
                            onFailure = { t->
                                Toast.makeText(context,"Error onFailure", Toast.LENGTH_SHORT).show()
                                t.message?.let { Log.e("Error",it) }
                            }
                        )
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        banAlert = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }

    if(unbannedAlert){
        AlertDialog(
            onDismissRequest = {unbannedAlert = false},
            title = { Text("Banning") },
            text = { Text("Do you confirm to unbanned ${magazine?.title}") },
            confirmButton = {
                TextButton(
                    onClick = {
                        unbannedMagazineUtility(
                            magazine?.magazineId ?: 0,
                            onResponse = {
                                Toast.makeText(context,"Unbanned magazine successfully", Toast.LENGTH_SHORT).show()
                                navController.navigate(Screen.Home.route)
                            },
                            onElse = { responseError->
                                Toast.makeText(context,"Unbanned magazine failed", Toast.LENGTH_SHORT).show()
                                Log.e("Error",responseError.message())
                            },
                            onFailure = { t->
                                Toast.makeText(context,"Error onFailure", Toast.LENGTH_SHORT).show()
                                t.message?.let { Log.e("Error",it) }
                            }
                        )
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        unbannedAlert = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}
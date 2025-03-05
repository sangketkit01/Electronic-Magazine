package com.src.electronicmagazine

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import com.src.electronicmagazine.api_util.getCategoryUtility
import com.src.electronicmagazine.api_util.getWriterUtility
import com.src.electronicmagazine.api_util.myMagazineListUtility
import com.src.electronicmagazine.api_util.searchMagazineUtility
import com.src.electronicmagazine.components.BadgeColor
import com.src.electronicmagazine.data.Category
import com.src.electronicmagazine.data.Magazine
import com.src.electronicmagazine.data.User
import com.src.electronicmagazine.navigation.Screen
import com.src.electronicmagazine.session.SharePreferencesManager
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun SearchScreen(navController : NavHostController){
    val scrollState = rememberScrollState()

    var searchContext by remember { mutableStateOf("") }

    var magazineList by remember { mutableStateOf<List<Magazine>>(emptyList()) }
    val context = LocalContext.current
    val sharedPreferences = SharePreferencesManager(context)


    Column (
        modifier = Modifier.fillMaxSize()
            .verticalScroll(scrollState)
            .background(Color(26, 26, 26, 255)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ){
        OutlinedTextField(
            value = searchContext,
            onValueChange = {newValue->
                searchContext = newValue
            },
            label = { Text("Search") },
            modifier = Modifier.fillMaxWidth()
                .padding(20.dp),
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            trailingIcon = {
                IconButton(
                    onClick = {
                        searchMagazineUtility(
                            searchContext,
                            onResponse = { response->
                                magazineList  = response
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
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                }
            }
        )

        if(searchContext.isNotEmpty() && magazineList.isNotEmpty()){
            Text(
                text = "The result of '$searchContext'",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }

        magazineList.forEach{ magazine->
            var category by remember { mutableStateOf<Category?>(null) }
            var writer by remember { mutableStateOf<User?>(null) }

            val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
            val outputFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH)

            val date: Date? = inputFormat.parse(magazine.createdAt.toString())
            val formattedCreatedAt = date?.let { outputFormat.format(it) }

            getCategoryUtility(
                magazine.categoryId,
                onResponse = { response->
                    category  = response
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
                    writer  = response
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
                    .padding(20.dp)
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
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Image(
                    rememberAsyncImagePainter(
                        model = magazine.coverPath
                            .replace("../uploads","http://10.0.2.2:3000/uploads")
                    ),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth()
                        .height(173.dp)
                        .background(Color(68, 68, 68, 255)),
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier.fillMaxWidth()
                        .padding(vertical = 10.dp),
                    horizontalAlignment = Alignment.Start
                ){
                    Text(
                        text = category?.name ?: "",
                        fontSize = 14.sp,
                        color = Color.White,
                        modifier = Modifier
                            .background(BadgeColor(category?.name ?: ""))
                            .padding(vertical = 3.dp, horizontal = 8.dp),
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "By: ${writer?.name ?: ""} $formattedCreatedAt",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(140, 140, 140, 255),
                    )
                }

                Text(
                    text = magazine.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    color = Color.White,
                    modifier = Modifier.padding(top = 10.dp).align(Alignment.Start),
                )
            }
        }

        Spacer(modifier = Modifier.height(80.dp))
    }
}
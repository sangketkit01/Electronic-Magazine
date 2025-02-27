package com.src.electronicmagazine

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.google.gson.JsonObject
import com.src.electronicmagazine.api_util.insertArticleUtility
import com.src.electronicmagazine.api_util.insertMagazineUtility
import com.src.electronicmagazine.navigation.Screen
import com.src.electronicmagazine.session.SharePreferencesManager
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.util.UUID

@Composable
fun WritingContScreen(navController : NavHostController){
    val cover = navController.previousBackStackEntry?.savedStateHandle?.get<String>("cover")
    val name = navController.previousBackStackEntry?.savedStateHandle?.get<String>("name")
    val category = navController.previousBackStackEntry?.savedStateHandle?.get<Int>("category")
    val description = navController.previousBackStackEntry?.savedStateHandle?.get<String>("description")

    var article by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    fun Context.getImagePart(uri: Uri,name : String): MultipartBody.Part {
        val stream = contentResolver.openInputStream(uri)
        val mimeType = contentResolver.getType(uri) ?: "image/jpeg"
        val request = stream?.let {
            RequestBody.create(mimeType.toMediaTypeOrNull(), it.readBytes())
        }

        val extension = when(mimeType) {
            "image/jpeg", "image/jpg" -> ".jpg"
            "image/png" -> ".png"
            else -> ".jpg"
        }

        val uuid = UUID.randomUUID().toString()
        val filename = "image_${uuid}${extension}"

        return MultipartBody.Part.createFormData(
            name,
            filename,
            request!!
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            selectedImageUri = it
        }
    }

    val scrollState = rememberScrollState()
    val context = LocalContext.current

    val sharePreferences  = SharePreferencesManager(context)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(top = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ){
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp, start = 15.dp),
            horizontalArrangement = Arrangement.Start
        ){
            Icon(
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
                    .clickable {
                        navController.popBackStack()
                    }
            )
        }

        Column (
            modifier = Modifier.fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ){
            OutlinedTextField(
                value = article,
                onValueChange = {newValue->
                    article = newValue
                },
                label = { Text("Article") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
            )

            OutlinedTextField(
                value = content,
                onValueChange = {newValue->
                    content = newValue
                },
                label = { Text("Content") },
                modifier = Modifier.fillMaxWidth()
                    .height(300.dp),
                shape = RoundedCornerShape(10.dp),
            )

            if(selectedImageUri != null){
                Image(
                    painter = rememberAsyncImagePainter(selectedImageUri),
                    contentDescription = "Selected Image",
                    modifier = Modifier.fillMaxWidth()
                        .height(300.dp),
                    contentScale = ContentScale.Fit
                )

            }

            Button(
                onClick = {
                    launcher.launch("image/*")
                }
            ) {
                Text("Select image")
            }

            Button(
                onClick = {
                    val coverPath = context.getImagePart(Uri.parse(cover),"cover_path")
                    val imagePath = selectedImageUri?.let { context.getImagePart(it,"image_path") }

                    insertMagazineUtility(
                        sharePreferences.user?.userId!!,
                        name!!,
                        description!!,
                        coverPath,
                        category!!,
                        onResponse = { response->
                            val magazineId =  response.get("insertId").asInt
                            insertArticleUtility(
                                magazineId,
                                article,
                                content,
                                imagePath!!,
                                onResponse = { response2->
                                    Toast.makeText(context,"Write magazine successfully.\n" +
                                            "Progress is in pending",Toast.LENGTH_SHORT).show()

                                    navController.navigate(Screen.Profile.route)
                                },
                                onElse = { err->
                                    Toast.makeText(context,"Write magazine failed",Toast.LENGTH_SHORT)
                                        .show()

                                    Log.e("Error",err.message())
                                },
                                onFailure = { t->
                                    Toast.makeText(context,"Error onFailure",Toast.LENGTH_SHORT)
                                        .show()

                                    t.message?.let { Log.e("Error", it) }
                                }
                            )
                        },
                        onElse = { err->
                            Toast.makeText(context,"Write magazine failed",Toast.LENGTH_SHORT)
                                .show()

                            Log.e("Error",err.message())
                        },
                        onFailure = { t->
                            Toast.makeText(context,"Error onFailure",Toast.LENGTH_SHORT)
                                .show()

                            t.message?.let { Log.e("Error", it) }
                        }
                    )
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                ),
                enabled = article.isNotEmpty() && content.isNotEmpty()
                        && selectedImageUri != null
            ) {
                Text(
                    text = "Submit",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

        }
    }
}
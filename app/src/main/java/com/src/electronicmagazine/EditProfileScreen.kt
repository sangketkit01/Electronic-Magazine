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
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.android.volley.toolbox.ImageRequest
import com.src.electronicmagazine.api_util.updateProfileUtility
import com.src.electronicmagazine.session.SharePreferencesManager
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.util.UUID

@Composable
fun EditProfileScreen(navController : NavHostController){
    val context = LocalContext.current
    val sharePreferences = SharePreferencesManager(context)

    var username by remember { mutableStateOf(sharePreferences.user?.username ?: "") }
    var email by remember { mutableStateOf(sharePreferences.user?.email ?: "") }
    var name by remember { mutableStateOf(sharePreferences.user?.name ?: "") }

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    fun Context.getImagePart(uri: Uri): MultipartBody.Part {
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
            "profile_path",
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

        if (selectedImageUri != null) {
            AsyncImage(
                model = selectedImageUri,
                contentDescription = "Preview Profile Picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
            )
        } else if (sharePreferences.user?.profilePath != null) {
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
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                launcher.launch("image/*")
            },
            shape = RoundedCornerShape(10.dp),
            contentPadding = PaddingValues(15.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(217, 217, 217, 255),
                contentColor = Color.Black
            )
        ) {
            Text(
                text = "Change image",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Column (
            modifier = Modifier.padding(vertical = 20.dp, horizontal = 0.dp),
            verticalArrangement = Arrangement.spacedBy(40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            OutlinedTextField(
                value = username,
                onValueChange = {newValue->
                    username = newValue
                },
                label = { Text("Username") },
                shape = RoundedCornerShape(10.dp)
            )

            OutlinedTextField(
                value = email,
                onValueChange = {newValue->
                    email = newValue
                },
                label = { Text("Email") },
                shape = RoundedCornerShape(10.dp)
            )

            OutlinedTextField(
                value = name,
                onValueChange = {newValue->
                    name = newValue
                },
                label = { Text("Name") },
                shape = RoundedCornerShape(10.dp)
            )
        }

        Button(
            onClick = {
                val imagePart = selectedImageUri?.let { context.getImagePart(it) }
                updateProfileUtility(
                    sharePreferences.user?.userId!!,
                    username,
                    email,
                    name,
                    imagePart,
                    onResponse = {
                        Toast.makeText(context,"Update profile successfully",Toast.LENGTH_SHORT)
                            .show()

                        val updatedUser = sharePreferences.user?.copy(
                                username = username,
                                email = email,
                                name = name,
                                profilePath = if(selectedImageUri != null) selectedImageUri.toString()
                                else sharePreferences.user?.profilePath
                            )


                        sharePreferences.user = updatedUser

                        navController.popBackStack()
                    },
                    onElse = { response->
                        Toast.makeText(context,"Update profile failed",Toast.LENGTH_SHORT).show()
                        response.body()?.toString()?.let { Log.e("Error", it) }
                    },
                    onFailure = { t->
                        Toast.makeText(context,"Error onFailure",Toast.LENGTH_SHORT).show()
                        t.message?.let { Log.e("Error",it) }
                    }
                )
            },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Color.Black
            ),
            modifier = Modifier.width(165.dp)
        ) {
            Text(
                text = "Submit",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
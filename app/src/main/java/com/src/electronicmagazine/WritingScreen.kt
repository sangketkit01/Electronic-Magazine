package com.src.electronicmagazine

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.src.electronicmagazine.api_util.getAllCategoryUtility
import com.src.electronicmagazine.api_util.getAllMagazineUtility
import com.src.electronicmagazine.api_util.getCategoryUtility
import com.src.electronicmagazine.data.Category
import com.src.electronicmagazine.data.Magazine
import com.src.electronicmagazine.navigation.Screen
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.util.UUID
import kotlin.random.Random

@Composable
fun WritingScreen(navController: NavHostController) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycle by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()

    var categories = remember { mutableStateListOf<Category>() }
    var categoryIndex by remember { mutableIntStateOf(0) }
    LaunchedEffect(lifecycle) {
        when(lifecycle){
            Lifecycle.State.DESTROYED -> {}
            Lifecycle.State.INITIALIZED -> {}
            Lifecycle.State.CREATED -> {}
            Lifecycle.State.STARTED -> {}
            Lifecycle.State.RESUMED -> {
                categories.clear()
                getAllCategoryUtility(
                    onResponse = { response->
                        categories.addAll(response)
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

    var name by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("Entertainment") }
    var description by remember { mutableStateOf("") }

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
            "id_card_path",
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(top = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
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

        Text(
            text = "Create a Magazine",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
        )

        Spacer(Modifier.height(10.dp))

        Card(
            modifier = Modifier
                .width(273.dp)
                .height(147.dp)
                .clickable {
                    launcher.launch("image/*")
                },
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(242, 242, 242, 255)
            )
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (selectedImageUri != null) {
                    Image(
                        painter = rememberAsyncImagePainter(selectedImageUri),
                        contentDescription = "Selected Image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(
                        painter = painterResource(R.drawable.book),
                        contentDescription = null,
                        modifier = Modifier.size(80.dp),
                        tint = Color.LightGray
                    )
                }
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(5.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.upload),
                contentDescription = null,
                modifier = Modifier.size(25.dp),
                tint = Color.LightGray
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                if (selectedImageUri != null) "Change Cover" else "Upload Cover",
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Name",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 10.dp)
            )

            OutlinedTextField(
                value = name,
                onValueChange = { newValue -> name = newValue },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedLabelColor = Color(131, 131, 131, 255),
                    unfocusedLabelColor = Color(131, 131, 131, 255),
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color(113, 113, 113, 255),
                    unfocusedIndicatorColor = Color(113, 113, 113, 255),
                    focusedPlaceholderColor = Color(113, 113, 113, 255),
                    unfocusedPlaceholderColor = Color(113, 113, 113, 255)
                ),
                shape = RoundedCornerShape(10.dp),
                placeholder = {
                    Text(
                        text = "Magazine Name",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Category",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 10.dp)
            )

            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedButton(
                    onClick = { expanded = true },
                    modifier = Modifier,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.Black
                    )
                ) {
                    Text(selectedItem, fontWeight = FontWeight.Bold)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown Icon")
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .wrapContentSize()
                        .background(Color.White, shape = RoundedCornerShape(10.dp))
                ) {
                    categories.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(item.name) },
                            onClick = {
                                selectedItem = item.name
                                categoryIndex = item.categoryId
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Description",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 10.dp)
            )

            OutlinedTextField(
                value = description,
                onValueChange = { newValue -> description = newValue },
                modifier = Modifier.fillMaxWidth()
                    .height(300.dp),
                colors = TextFieldDefaults.colors(
                    focusedLabelColor = Color(131, 131, 131, 255),
                    unfocusedLabelColor = Color(131, 131, 131, 255),
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color(113, 113, 113, 255),
                    unfocusedIndicatorColor = Color(113, 113, 113, 255),
                    focusedPlaceholderColor = Color(113, 113, 113, 255),
                    unfocusedPlaceholderColor = Color(113, 113, 113, 255)
                ),
                shape = RoundedCornerShape(10.dp),
                placeholder = {
                    Text(
                        text = "Description",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            )
        }

        TextButton(
            onClick = {
                navController.currentBackStackEntry?.savedStateHandle?.set(
                    "cover" , selectedImageUri.toString()
                )

                navController.currentBackStackEntry?.savedStateHandle?.set(
                    "name" , name
                )

                navController.currentBackStackEntry?.savedStateHandle?.set(
                    "category",categoryIndex
                )

                navController.currentBackStackEntry?.savedStateHandle?.set(
                    "description" , description
                )

                navController.navigate(Screen.WritingCont.route)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.Black),
            enabled = name.isNotEmpty() && selectedImageUri != null
                    && description.isNotEmpty()
        ) {
            Text(
                text = "Next",
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(70.dp))
    }
}
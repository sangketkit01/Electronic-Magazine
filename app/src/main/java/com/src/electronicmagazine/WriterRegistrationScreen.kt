package com.src.electronicmagazine

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.src.electronicmagazine.api_util.writerRegistrationUtility
import com.src.electronicmagazine.session.SharePreferencesManager
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.util.UUID

@Composable
fun WriterRegistrationScreen(navController : NavHostController){
    val scrollState = rememberScrollState()

    val context = LocalContext.current
    val sharePreferences = SharePreferencesManager(context)

    var name by remember { mutableStateOf("") }
    var idCard by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var addressCont by remember { mutableStateOf("") }
    var about by remember { mutableStateOf("") }

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


    Column (
        modifier = Modifier.fillMaxSize()
            .verticalScroll(scrollState)
            .padding(top = 40.dp),
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

        Text(
            text = "Writer Registration",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
        )

        Spacer(Modifier.height(10.dp))

        Card (
            modifier = Modifier.width(273.dp).height(147.dp)
                .clickable { launcher.launch("image/*") },
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(242, 242, 242, 255)
            )
        ){
            Column (
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                Icon(
                    painter = painterResource(R.drawable.card_number),
                    contentDescription = null,
                    modifier = Modifier.size(80.dp),
                    tint = Color.LightGray
                )

                if(selectedImageUri != null){
                    Text(
                        text = "เลือกรูปแล้ว"
                    )
                }
            }
        }

        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(5.dp)
        ){
            Icon(
                painter = painterResource(R.drawable.upload),
                contentDescription = null,
                modifier = Modifier.size(25.dp),
                tint = Color.LightGray
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                "Upload ID Card",
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Column (
            modifier = Modifier.fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.Start
        ){
            Text(
                text = "Name",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(
                    bottom = 10.dp
                )
            )

            OutlinedTextField(
                value = name,
                onValueChange = {newValue->
                    name = newValue
                },
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
                        text = "Name",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            )
        }

        Column (
            modifier = Modifier.fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.Start
        ){
            Text(
                text = "ID Card",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(
                    bottom = 10.dp
                )
            )

            OutlinedTextField(
                value = idCard,
                onValueChange = {newValue->
                    idCard = newValue
                },
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
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                placeholder = {
                    Text(
                        text = "0 1234 56789 10 1",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            )
        }

        Column (
            modifier = Modifier.fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.Start
        ){
            Text(
                text = "Email",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(
                    bottom = 10.dp
                )
            )

            OutlinedTextField(
                value = email,
                onValueChange = {newValue->
                    email = newValue
                },
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
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                placeholder = {
                    Text(
                        text = "example@email.com",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            )
        }

        Column (
            modifier = Modifier.fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.Start
        ){
            Text(
                text = "Phone",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(
                    bottom = 10.dp
                )
            )

            OutlinedTextField(
                value = phone,
                onValueChange = {newValue->
                    phone = newValue
                },
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
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                placeholder = {
                    Text(
                        text = "012-345-6789",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            )
        }

        Column (
            modifier = Modifier.fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.Start
        ){
            Text(
                text = "Address",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(
                    bottom = 10.dp
                )
            )

            OutlinedTextField(
                value = address,
                onValueChange = {newValue->
                    address = newValue
                },
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
                        text = "House number, Street name",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            )
        }

        Column (
            modifier = Modifier.fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.Start
        ){
            Text(
                text = "Address (Cont.)",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(
                    bottom = 10.dp
                )
            )

            OutlinedTextField(
                value = addressCont,
                onValueChange = {newValue->
                    addressCont = newValue
                },
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
                        text = "City, State, ZIP Code",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            )
        }

        Column (
            modifier = Modifier.fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.Start
        ){
            Text(
                text = "About you",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(
                    bottom = 10.dp
                )
            )

            OutlinedTextField(
                value = about,
                onValueChange = {newValue->
                    about = newValue
                },
                modifier = Modifier.fillMaxWidth()
                    .height(195.dp),
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
                        text = "Tell us about you",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            )
        }

        TextButton(
            onClick = {
                if(selectedImageUri != null){
                    writerRegistrationUtility(
                        userId = sharePreferences.user?.userId ?: 0,
                        fullName = name,
                        idCard = idCard,
                        idCardPath = context.getImagePart(selectedImageUri!!),
                        email = email,
                        phone = phone,
                        address = "$address $addressCont",
                        bio = about,
                        onResponse = {
                            Toast.makeText(context,"Register successfully",Toast.LENGTH_SHORT)
                                .show()
                            navController.popBackStack()
                        },
                        onElse = { response->
                            Toast.makeText(context,"Register Failed",Toast.LENGTH_SHORT).show()
                            Log.e("Error",response.message().toString())
                        },
                        onFailure = { t->
                            Toast.makeText(context,"Error onFailure",Toast.LENGTH_SHORT).show()
                            t.message?.let { Log.e("Error",it) }
                        }
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
                .padding(20.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.Black),
            enabled = name.isNotEmpty() && idCard.isNotEmpty() && email.isNotEmpty()
                    && phone.isNotEmpty() && address.isNotEmpty() && about.isNotEmpty()
                    && selectedImageUri != null
        ) {
            Text(
                text = "Submit",
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
        }
    }
}
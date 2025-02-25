package com.src.electronicmagazine

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun SearchScreen(navController : NavHostController){
    val scrollState = rememberScrollState()

    var searchContext by remember { mutableStateOf("") }

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
            )
        )

        Text(
            text = "The Result of “THE STORY OF”",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color.White
        )

        Column (
            modifier = Modifier.fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Image(
                painterResource(R.drawable.image5),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop
            )

            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ){
                Text(
                    text = "Car",
                    fontSize = 14.sp,
                    color = Color.White,
                    modifier = Modifier
                        .width(67.dp)
                        .background(Color(52, 51, 51, 255)),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = "By: Thiraphat December 01,2024",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(140, 140, 140, 255),
                )
            }

            Text(
                text = "The story of the 1980 Ferrari Pinin – " +
                        "the first four-door Ferrari Sports Saloon concept",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                color = Color.White,
                modifier = Modifier.padding(top = 10.dp)
            )
        }

        Column (
            modifier = Modifier.fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Image(
                painterResource(R.drawable.image5),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop
            )

            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ){
                Text(
                    text = "Car",
                    fontSize = 14.sp,
                    color = Color.White,
                    modifier = Modifier
                        .width(67.dp)
                        .background(Color(52, 51, 51, 255)),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = "By: Thiraphat December 01,2024",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(140, 140, 140, 255),
                )
            }

            Text(
                text = "The story of the 1980 Ferrari Pinin – " +
                        "the first four-door Ferrari Sports Saloon concept",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                color = Color.White,
                modifier = Modifier.padding(top = 10.dp , bottom = 20.dp)
            )
        }
        Spacer(modifier = Modifier.height(80.dp))
    }
}
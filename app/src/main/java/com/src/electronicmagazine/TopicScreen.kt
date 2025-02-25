package com.src.electronicmagazine

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
import com.src.electronicmagazine.navigation.Screen

@Composable
fun TopicScreen(navController : NavHostController){
    val scrollState = rememberScrollState()

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
                painter = painterResource(R.drawable.image3),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .height(450.dp)
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
                    text = "Tech",
                    fontSize = 18.sp,
                    color = Color.White,
                    modifier = Modifier
                        .width(121.dp)
                        .background(Color(113, 113, 113, 255)),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "2025 Aston Martin Valhalla – 1,064 HP Mid-engine Hybird " +
                            "คันแรกที่ใช้เวลาพัฒนาถึง 5 ปี",
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
                text = "Tech",
                fontSize = 30.sp,
                color = Color.White
            )
        }

        Column (
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 5.dp, vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Box(
                modifier = Modifier.width(391.dp)
            ){
                Image(
                    painter = painterResource(R.drawable.image5),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.padding(10.dp)
                        .fillMaxWidth()
                        .align(Alignment.Center)
                )

                Text(
                    text = "Tech",
                    fontSize = 18.sp,
                    color = Color.White,
                    modifier = Modifier.width(121.dp)
                        .background(Color(113, 113, 113, 255))
                        .align(Alignment.BottomCenter),
                    textAlign = TextAlign.Center
                )
            }

            Text(
                text = "The story of the 1980 Ferrari Pinin – the first four-door Ferrari Sports Saloon concept",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.width(276.dp)
                    .padding(10.dp)
                    .clickable {
                        navController.navigate(Screen.Read.route)
                    },
                textAlign = TextAlign.Center
            )

            Text(
                text = "By: Thiraphat December 01,2024",
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = Color(196, 196, 196, 255),
                textAlign = TextAlign.Center
            )
        }

        Column (
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 5.dp, vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Box(
                modifier = Modifier.width(391.dp)
            ){
                Image(
                    painter = painterResource(R.drawable.image5),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.padding(10.dp)
                        .fillMaxWidth()
                        .align(Alignment.Center)
                )

                Text(
                    text = "Tech",
                    fontSize = 18.sp,
                    color = Color.White,
                    modifier = Modifier.width(121.dp)
                        .background(Color(113, 113, 113, 255))
                        .align(Alignment.BottomCenter),
                    textAlign = TextAlign.Center
                )
            }

            Text(
                text = "The story of the 1980 Ferrari Pinin – the first four-door Ferrari Sports Saloon concept",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.width(276.dp)
                    .padding(10.dp),
                textAlign = TextAlign.Center
            )

            Text(
                text = "By: Thiraphat December 01,2024",
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = Color(196, 196, 196, 255),
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(80.dp))
    }
}
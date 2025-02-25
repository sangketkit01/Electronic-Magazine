package com.src.electronicmagazine

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
fun FavoriteScreen(navController : NavHostController){
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

        Text(
            text = "Favorite",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )

        Card (
            modifier = Modifier.fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 25.dp),
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(
                width = 1.dp,
                color = Color.LightGray
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ){
            Row (
                modifier = Modifier.fillMaxWidth()
                    .padding(15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Image(
                    painter = painterResource(R.drawable.image2),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.width(150.dp).height(95.dp)
                )

                Column (
                    horizontalAlignment = Alignment.Start
                ){
                    Text(
                        text = "Business",
                        fontSize = 14.sp,
                        color = Color.White,
                        modifier = Modifier.width(102.dp)
                            .background(Color(211, 185, 100, 255)),
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "รวม 2025 เทรนด์ AI สำคัญ By Microsoft ที่ทุกธุรกิจต้องจับตามอง !",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Start
                    )
                }
            }


            Button(
                onClick = {

                },
                shape = CircleShape,
                contentPadding = PaddingValues(5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(217, 217, 217, 255),
                    contentColor = Color.Black
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}
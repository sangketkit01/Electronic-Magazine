package com.src.electronicmagazine

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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

@Composable
fun ReadScreen(navController : NavHostController){
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
                    painter = painterResource(R.drawable.image3),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                        .height(279.dp)
                )

                Text(
                    text = "Car",
                    fontSize = 18.sp,
                    color = Color.White,
                    modifier = Modifier
                        .width(121.dp)
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

        Text(
            text = "ตั้งแต่ช่วงปี 60s – 80s สำนักออกแบบ Pininfarina ทำงานคู่กับ Ferrari สร้างผลงานระดับ iconic " +
                    "ออกมามากมาย ทำให้ทั้งสองบริษัทมีความสัมพันธ์ที่ดีในระดับซี้ปึ้ก และในโอกาสฉลองครบรอบ 50 ปีของ " +
                    "Pininfarina ตอนนั้น Sergio Pininfarina มีความฝันที่จะทำ high-performance Ferrari 4 ประตู" +
                    " เพื่อต่อสู้กับคู่แข่งที่กำลังมาแรงอย่าง Aston Martin Lagonda, Mercedes 450 SEL 6.9 และ Maserati" +
                    " Quattroporte จึงตัดสินใจสร้าง concept car ขึ้นมาเป็นของขวัญให้บริษัทตัวเอง นั่นก็คือ 1980 Ferrari " +
                    "Pinin เป็นครั้งแรกที่โลกได้พบกับ Ferrai ในตัวถัง 4 ประตู และถูกตั้งชื่อเพื่อเป็นเกียรติให้กับผู้ก่อตั้ง Mr. Battista " +
                    "‘Pinin’ Farina",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
                .padding(10.dp)
        )

        Image(
            painterResource(R.drawable.image5),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth()
                .padding(10.dp),
            contentScale = ContentScale.Crop
        )

        Text(
            text = "ดีไซน์เนอร์ผู้ออกแบบ Ferrari Pinin ก็คือ Diego Ottina ผู้ร่วมออกแบบ Testarossa และ " +
                    "348 ภายใต้การดูแลของ Leonardo Fioravanti จึงไม่แปลกเลยที่เส้นสายของรถคันนี้จะดูคล้ายกับ " +
                    "Ferrari 400i ทั้งในด้านมิติและดีไซน์ แต่ไม่ใช่การยกสเปกมาแน่นอน เพราะ Ferrari Pinin " +
                    "ใช้เครื่องยนต์ 5.0 ลิตร flat-12 สูบ boxer engine จาก Berlinetta " +
                    "ซึ่งเป็นรถรุ่นเดียวที่นำเครื่องบล็อกนี้มาวางใต้ฝากระโปรงหน้า ทำให้ส่วนหน้าดูยาวกว่ารถ saloon ทั่วไป",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
                .padding(10.dp)
        )

        Spacer(modifier = Modifier.height(80.dp))
    }
}
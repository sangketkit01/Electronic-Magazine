package com.src.electronicmagazine

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import com.src.electronicmagazine.api_util.getAllMagazineUtility
import com.src.electronicmagazine.api_util.getCategoryUtility
import com.src.electronicmagazine.api_util.getNotificationUtility
import com.src.electronicmagazine.data.Category
import com.src.electronicmagazine.data.Magazine
import com.src.electronicmagazine.data.Notification
import com.src.electronicmagazine.session.SharePreferencesManager
import kotlin.random.Random

@Composable
fun NotificationScreen(navController : NavHostController){
    val context = LocalContext.current
    val sharePreferences = SharePreferencesManager(context)

    var notificationList  by remember { mutableStateOf<List<Notification>>(emptyList()) }
    val scrollState = rememberScrollState()

    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycle by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()
    LaunchedEffect(lifecycle) {
        when(lifecycle){
            Lifecycle.State.DESTROYED -> {}
            Lifecycle.State.INITIALIZED -> {}
            Lifecycle.State.CREATED -> {}
            Lifecycle.State.STARTED -> {}
            Lifecycle.State.RESUMED -> {
                getNotificationUtility(
                    sharePreferences.user?.userId ?: 0,
                    onResponse = { response->
                        notificationList = response
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

    Column (
        modifier = Modifier.fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        if (notificationList.isNotEmpty()){
            notificationList.forEach { notification->
                Column (
                    modifier = Modifier.fillMaxWidth()
                        .padding(15.dp)
                        .background(Color(220, 234, 91, 255))
                        .padding(15.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    horizontalAlignment = Alignment.Start
                ){
                    Text(
                        text = notification.notificationTitle,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start
                    )

                    Text(
                        text = notification.notificationDetail,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Start,
                        color = Color(131, 131, 131, 255)
                    )
                }

                HorizontalDivider(thickness = 0.5.dp , color = Color.LightGray)
            }
        }else{

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Empty notification",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
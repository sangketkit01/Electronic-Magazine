package com.src.electronicmagazine.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun BadgeColor(type : String) : Color{
    when(type){
        "World" -> {return Color(255, 0, 0, 255)}
        "Entertainment" -> {return Color(148, 52, 81, 255) }
        "Guide" -> {return Color(108, 74, 94, 255) }
        "Tech" -> {return Color(78, 77, 78, 255) }
        "Style" -> {return Color(236, 84, 127, 255) }
        "Business" -> {return Color(250, 184, 74, 255) }
        "Girls" -> {return Color(236, 84, 127, 255) }
        "Life" -> {return Color(234, 56, 62, 255) }
        "Films" -> {return Color(96, 46, 61, 255) }
        "Event" -> {return Color(98, 54, 81, 255) }
        "App" -> {return Color(255, 0, 0, 255) }
        "Design" -> {return Color(101, 61, 124, 255) }
        "Music" -> {return Color(111, 45, 68, 255) }
        "Menu" -> {return Color(155, 79, 126, 255) }
        "Cars" -> {return Color(43, 43, 43, 255) }
        "Fashion" -> {return Color(184, 115, 158, 255) }
        "Travel" -> {return Color(184, 115, 158, 255) }
        "Gadgets" -> {return Color(111, 104, 111, 255) }
        else -> {return Color(255, 0, 0, 255)
        }
    }
}
package com.example.timetable.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import com.example.timetable.R

@Composable
fun ViewLogin() {
    Box(
        modifier = Modifier.fillMaxSize().paint(painter = painterResource(id = R.drawable.bg), contentScale = ContentScale.FillBounds)
    )
    {
        Column()
        {
            Text("Ваше расписание", style = MaterialTheme.typography.titleLarge)
            Text("Пожалуйста, выберите ВУЗ и группу", style = MaterialTheme.typography.titleMedium, color = Color.Gray)
        }
    }
}
package com.example.timetable.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.ui.Modifier

@Composable
fun ViewProfile(navController: NavHostController) {
    Button(onClick = { navController.navigate("login") }, modifier = Modifier.padding(200.dp)) {
    }
    Text("Your profile")
}
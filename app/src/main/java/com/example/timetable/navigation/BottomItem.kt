package com.example.timetable.navigation

import com.example.timetable.R

sealed class BottomItem(val title: String, val iconId: Int, val route: String){
    object HomeScreen: BottomItem("", R.drawable.calendar, "home")
    object SearchScreen: BottomItem("", R.drawable.iconlike, "search")
    object ProfileScreen: BottomItem("", R.drawable.iconperson, "profile")

}